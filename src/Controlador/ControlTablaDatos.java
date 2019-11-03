/*
 * Control para el panel que muestra los empleados o admin registrado con su respectiva
 * area de trabajo y horarios
 */
package Controlador;

import Modelo.ClaseConsultar;
import Modelo.ClaseEliminar;
import Modelo.Conexion;
import Modelo.Personal;
import Vista.PnlTablaDatos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jaasiel Guerra
 */
public class ControlTablaDatos extends PnlTablaDatos implements ActionListener, KeyListener {

    private final String DEFAULT_SELECTED = "- Todos -";
    private Map<String, String> busqueda;
    private String privilegioMostrar;
    private ControlRegistroEmpleado controlRegEmp = null;
    private ControlPrincipal controlMain = null;
    private CambiarPanel cambiar = null;

    public ControlTablaDatos(ControlPrincipal main) {

        this.busqueda = new HashMap<String, String>();
        this.controlMain = main;
        this.cambiar = new CambiarPanel();

        rellenarComponentes();

        //escucha de acciones
        this.TxtBusquedaPor.addActionListener(this);
        this.TxtBuscar.addKeyListener(this);
        this.BtnEditar.addActionListener(this);
        this.BtnEliminar.addActionListener(this);
        

    }

    //actualiza la tabla con el parametro recibida en su argumento
    public void actualizarTabla(String privilegioMostrar) {

        this.privilegioMostrar = privilegioMostrar;//salvar el parametro

        Conexion conectarse = new Conexion("datos/registro");
        ClaseConsultar consulta = new ClaseConsultar(conectarse.conectar(), "Empleados,AreasTrabajo,Usuarios");

        if (TxtBusquedaPor.getSelectedItem().equals(DEFAULT_SELECTED)) {
            consulta.consultar("Empleados.Usuarios_DPI,Empleados.Nombre,Empleados.Apellidos,"
                    + "AreasTrabajo.NombreArea,AreasTrabajo.HoraEntrada,AreasTrabajo.HoraSalida",
                    "Empleados.AreasTrabajo_id", "=", "AreasTrabajo.id AND Empleados.Usuarios_DPI = Usuarios.DPI"
                    + " AND Usuarios.Privilegio = '" + privilegioMostrar + "'");//consulta personalizada
        } else {

            consulta.consultar("Empleados.Usuarios_DPI,Empleados.Nombre,Empleados.Apellidos,"
                    + "AreasTrabajo.NombreArea,AreasTrabajo.HoraEntrada,AreasTrabajo.HoraSalida",
                    "Empleados.AreasTrabajo_id = AreasTrabajo.id AND Empleados.Usuarios_DPI = Usuarios.DPI"
                    + " AND Usuarios.Privilegio = '" + privilegioMostrar + "'"
                    + " AND Empleados." + busqueda.get(TxtBusquedaPor.getSelectedItem()), TxtBuscar.getText());
        }

        RellenarTabla rellenar = new RellenarTabla(consulta.getResultadoConsulta(), this.Datos);
        rellenar.llenar();

        conectarse.cerrar();//cerrar conexion
        conectarse = null;
        consulta = null;
        System.gc();

    }

    private void rellenarComponentes() {

        //iniciar los item del combobox
        for (int i = 0; i < 3; i++) {

            this.TxtBusquedaPor.removeAllItems();//limpiar el combobox
            this.TxtBusquedaPor.addItem(DEFAULT_SELECTED);
            this.TxtBusquedaPor.addItem("DPI");
            busqueda.put("DPI", "Usuarios_DPI");
            this.TxtBusquedaPor.addItem("NOMBRE");
            busqueda.put("NOMBRE", "Nombre");
        }
        this.TxtBuscar.setEditable(false);
    }

    private void editarRegistro() {

        if (Datos.getSelectedRow() != -1) {//validar que este selccionada una fila

            DefaultTableModel modelo = (DefaultTableModel) this.Datos.getModel();//cast el modelo de jtable

            Personal personal = new Personal();

            Conexion con = new Conexion("datos/registro");
            ClaseConsultar consulta = new ClaseConsultar(con.conectar(), "Empleados,Usuarios");

            consulta.consultar("*", "Usuarios.DPI", "=",
                    String.valueOf(modelo.getValueAt(Datos.getSelectedRow(), 0)) + " AND Empleados.Usuarios_DPI"
                    + " = " + String.valueOf(modelo.getValueAt(Datos.getSelectedRow(), 0)));

            try {
                while (consulta.getResultadoConsulta().next()) {
                    personal.setDPI(consulta.getResultadoConsulta().getLong("DPI"));
                    personal.setNombreUsuario(consulta.getResultadoConsulta().getString("NombreUsuario"));
                    personal.setContrasenia(consulta.getResultadoConsulta().getString("Contrasenia"));
                    personal.setPrivilegio(consulta.getResultadoConsulta().getString("Privilegio"));
                    personal.setHuella(consulta.getResultadoConsulta().getBytes("Huella"));
                    personal.setFotoHuella(consulta.getResultadoConsulta().getBytes("FotoHuella"));
                    personal.setFoto(consulta.getResultadoConsulta().getBytes("Foto"));
                    personal.setNombre(consulta.getResultadoConsulta().getString("Nombre"));
                    personal.setApellidos(consulta.getResultadoConsulta().getString("Apellidos"));
                    personal.setAreaTrabajo(consulta.getResultadoConsulta().getInt("AreasTrabajo_id"));
                }
            } catch (Exception e) {
                System.err.println("Error al consultar para editar empleado: " + e.getMessage());
            }

            this.controlRegEmp = new ControlRegistroEmpleado(this.controlMain, this.controlMain.getPersonal());

            this.controlRegEmp.prepararActualizacion(personal);

            this.controlRegEmp.setActivarActualizarEmp(true);//activar la actualizacion de empleados

            cambiar.cambiarPNL(this.controlMain.getVentanaPrincipal().panelContenedor, controlRegEmp.getPnlRegistroPer());

        }

    }

    private void eliminarRegistro() {
        if (Datos.getSelectedRow() != -1) {//validar que este selccionada una fila

            DefaultTableModel modelo = (DefaultTableModel) this.Datos.getModel();//cast el modelo de jtable

            long DPI = (long)modelo.getValueAt(Datos.getSelectedRow(), 0);//obtener el DPI

            JPanel panel = new JPanel();
            JLabel label = new JLabel("Ingrese su Contraseña:");
            JPasswordField pass = new JPasswordField(10);
            panel.add(label);
            panel.add(pass);
            String[] options = new String[]{"OK", "Cancel"};

            int opcion = JOptionPane.showOptionDialog(this, panel, "Control Acceso",
                    JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);

            if (opcion == 0) {//presiono OK
                if (pass.getText().equals(controlMain.getPersonal().getContrasenia())) {//si es contra correcta

                    Conexion con = new Conexion("datos/registro");
                    
                    ClaseEliminar elim = new ClaseEliminar(con.conectar(), "Empleados");
                    elim.borrar("Usuarios_DPI", String.valueOf(DPI));//eliminar el empleado
                    
                    elim = new ClaseEliminar(con.conectar(), "Usuarios");
                    elim.borrar("DPI", String.valueOf(DPI));//eliminar el usuario
                    
                    elim = new ClaseEliminar(con.conectar(), "EntradasSalidas");
                    elim.borrar("Empleados_Usuarios_DPI", String.valueOf(DPI));//eliminar el historial de horarios
                    
                    actualizarTabla(privilegioMostrar);//actualizar la tabla

                } else {
                    JOptionPane.showMessageDialog(this, "Contraseña incorrecta.",
                            "Control Acceso", JOptionPane.ERROR_MESSAGE);

                }
            }

        }
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

        ///////////////combobox de buscar por////////////////
        if (e.getSource().equals(TxtBusquedaPor)) {
            TxtBuscar.setText(null);
            if (TxtBusquedaPor.getSelectedItem().equals(DEFAULT_SELECTED)) {

                TxtBuscar.setEditable(false);
                actualizarTabla(privilegioMostrar);

            } else {
                TxtBuscar.setEditable(true);
            }
        }

        ///////////////boton de editar////////////
        if (e.getSource().equals(BtnEditar)) {
            editarRegistro();
        }
        
        if(e.getSource().equals(BtnEliminar)){
            eliminarRegistro();
        }
    }

}
