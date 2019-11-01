/*
 * Este control se encarga de mostrar el historial de las horas de entrada y salida de los empleados
 */
package Controlador;

import Modelo.ClaseConsultar;
import Modelo.Conexion;
import Vista.PnlTablaDatosHistorial;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

/**
 *
 * @author Jaasiel Guerra
 */
public class ControlTablaHistorial extends PnlTablaDatosHistorial implements KeyListener,
        ActionListener {

    private Map<String, Integer> areas;
    private Map<String, String> busqueda;
    private final String DEFAULT_SELECTED_COMBO = "- Todos -";
    private String privilegioMostrar;
    private String buscarFecha;
    private String buscarArea;
    private String buscarEmpleado;

    public ControlTablaHistorial() {

        this.areas = new HashMap<String, Integer>();
        this.busqueda = new HashMap<String, String>();

        rellenarComponentes();

        //activar escucha de eventos
        this.TxtBuscar.addKeyListener(this);
        this.TxtBusquedaPor.addActionListener(this);
        this.TxtBuscarArea.addActionListener(this);
        this.btnBuscar.addActionListener(this);

    }

    //actualiza la tabla con el parametro recibida en su argumento
    public void actualizarTabla(String privilegioMostrar) {

        this.privilegioMostrar = privilegioMostrar;//salvar el privilegio

        if (TxtBuscarArea.getSelectedItem().equals(DEFAULT_SELECTED_COMBO)
                && TxtBusquedaPor.getSelectedItem().equals(DEFAULT_SELECTED_COMBO)
                && Calendario.getDatoFecha() == null) {

            Conexion conectarse = new Conexion("datos/registro");
            ClaseConsultar consulta = new ClaseConsultar(conectarse.conectar(), "Empleados,AreasTrabajo,Usuarios,EntradasSalidas");

            consulta.consultar("Empleados.Usuarios_DPI,Empleados.Nombre,Empleados.Apellidos,"
                    + "AreasTrabajo.NombreArea,EntradasSalidas.Fecha,EntradasSalidas.Entrada,EntradasSalidas.Salida",
                    "Empleados.AreasTrabajo_id", "=", "AreasTrabajo.id AND Empleados.Usuarios_DPI = Usuarios.DPI"
                    + " AND Usuarios.Privilegio = '" + privilegioMostrar + "'" + " AND EntradasSalidas.Empleados_Usuarios_DPI"
                    + " = Empleados.Usuarios_DPI");//consulta personalizada

            RellenarTabla rellenar = new RellenarTabla(consulta.getResultadoConsulta(), this.Datos);
            rellenar.llenar();

            conectarse.cerrar();//cerrar conexion
            conectarse = null;
            consulta = null;
            System.gc();
        } else {

            if (Calendario.getDatoFecha() != null) {

                DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                System.out.println("Fecha: " + formato.format(Calendario.getDatoFecha()));
                this.buscarFecha = " AND EntradasSalidas.Fecha = '"
                        + formato.format(Calendario.getDatoFecha()).toString() + "'";
                Calendario.setDatoFecha(null);//limpiar la fecha
            } else {
                this.buscarFecha = "";

            }
            if (!this.TxtBuscarArea.getSelectedItem().equals(DEFAULT_SELECTED_COMBO)) {
                this.buscarArea = " AND Empleados.AreasTrabajo_id = "
                        + areas.get(TxtBuscarArea.getSelectedItem());
            } else {
                this.buscarArea = "";
                //Calendario.setDatoFecha(null);
            }
            if (!this.TxtBusquedaPor.getSelectedItem().equals(DEFAULT_SELECTED_COMBO)) {
                this.buscarEmpleado = " AND Empleados."
                        + busqueda.get(TxtBusquedaPor.getSelectedItem());
            } else {
                this.buscarEmpleado = " AND Empleados.Nombre";
                //Calendario.setDatoFecha(null);
            }

            Conexion conectarse = new Conexion("datos/registro");
            ClaseConsultar consulta = new ClaseConsultar(conectarse.conectar(), "Empleados,AreasTrabajo,Usuarios,EntradasSalidas");

            consulta.consultar("Empleados.Usuarios_DPI,Empleados.Nombre,Empleados.Apellidos,"
                    + "AreasTrabajo.NombreArea,EntradasSalidas.Fecha,EntradasSalidas.Entrada,EntradasSalidas.Salida",
                    "Empleados.AreasTrabajo_id = AreasTrabajo.id AND Empleados.Usuarios_DPI = Usuarios.DPI"
                    + " AND Usuarios.Privilegio = '" + privilegioMostrar + "'" + " AND EntradasSalidas.Empleados_Usuarios_DPI"
                    + " = Empleados.Usuarios_DPI" + buscarFecha + buscarArea + buscarEmpleado, TxtBuscar.getText());

            RellenarTabla rellenar = new RellenarTabla(consulta.getResultadoConsulta(), this.Datos);
            rellenar.llenar();

            conectarse.cerrar();//cerrar conexion
            conectarse = null;
            consulta = null;
            System.gc();

        }
    }

    private void rellenarComponentes() {

        Conexion conector = new Conexion("datos/registro");
        ClaseConsultar consulta = new ClaseConsultar(conector.conectar(), "AreasTrabajo");

        consulta.consultar("id,NombreArea");

        try {
            this.TxtBuscarArea.removeAllItems();//limpiar
            this.TxtBuscarArea.addItem(DEFAULT_SELECTED_COMBO);
            while (consulta.getResultadoConsulta().next()) {

                //poner las areas en el combobox
                this.TxtBuscarArea.addItem(consulta.getResultadoConsulta()
                        .getString("NombreArea"));

                //meter las areas en el mapa
                this.areas.put(consulta.getResultadoConsulta().getString("NombreArea"),
                        consulta.getResultadoConsulta().getInt("id"));
            }
        } catch (SQLException ex) {
            System.err.print("Error al insertar areas: " + ex.getMessage());
        } finally {
            conector.cerrar();
            conector = null;
            consulta = null;
            System.gc();
        }

        //llenar el combobox 
        this.TxtBusquedaPor.removeAllItems();
        this.TxtBusquedaPor.addItem(DEFAULT_SELECTED_COMBO);
        this.TxtBusquedaPor.addItem("DPI");
        busqueda.put("DPI", "Usuarios_DPI");
        this.TxtBusquedaPor.addItem("NOMBRE");
        busqueda.put("NOMBRE", "Nombre");
        this.TxtBuscar.setEditable(false);

    }

    @Override
    public void keyTyped(KeyEvent e) {

        if (TxtBusquedaPor.getSelectedItem().equals("DPI")) {

            char caracter = e.getKeyChar();

            // Verificar si la tecla pulsada es una letra   
            if (((caracter < '0')
                    || (caracter > '9'))
                    && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {

                e.consume();  //si es letra se ignora el evento

            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        actualizarTabla(privilegioMostrar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /////////////boton buscar//////////////
        if (e.getSource().equals(btnBuscar)) {
            actualizarTabla(privilegioMostrar);
        }

        ////////////////combobox de busqueda por DPI, NOMBRE//////////////////
        if (e.getSource().equals(TxtBusquedaPor)) {
            TxtBuscar.setText(null);
            if (TxtBusquedaPor.getSelectedItem().equals(DEFAULT_SELECTED_COMBO)) {//si esta el item por defecto

                TxtBuscar.setEditable(false);//se desactiva el campo de busqueda
                actualizarTabla(privilegioMostrar);
                // Calendario.setDatoFecha(null);//limpiar la fecha

            } else {
                TxtBuscar.setEditable(true);//sino se activa el campo de busqueda
            }
        }

        //////////////////combobox de busqueda po AreaTrabajo
        if (e.getSource().equals(TxtBuscarArea)) {
            actualizarTabla(privilegioMostrar);
            if (TxtBuscarArea.getSelectedItem().equals(DEFAULT_SELECTED_COMBO)) {
                // Calendario.setDatoFecha(null);//limpiar la fecha
            }
        }
    }

}
