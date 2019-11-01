/*
 * Controlador para el panel de las areas, que permite ver las areas de trabajo
 *  registradas en la base de datos
 */
package Controlador;

import Modelo.ClaseConsultar;
import Modelo.ClaseEliminar;
import Modelo.Conexion;
import Vista.PnlAreaTrabajo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jaasiel Guerra
 */
public class ControlAreasTrabajo extends PnlAreaTrabajo implements ActionListener, KeyListener {

    //atributos
    private ControlPrincipal controlMain = null;
    private CambiarPanel cambiar = null;
    private ControlNuevaAreaTrabajo controlNAT = null;
    private final String DEFAULT_SELECTED = "- Todos -";
    private Map<String, String> busqueda;

    public ControlAreasTrabajo(ControlPrincipal ventanaMain) {

        this.controlMain = ventanaMain;
        this.cambiar = new CambiarPanel();
        this.busqueda = new HashMap<String, String>();

        rellenarComponentes();
        actualizarTabla();

        //activar la escucha de acciones
        this.BtnNuevo.addActionListener(this);
        this.TxtBuscar.addKeyListener(this);
        this.TxtBusquedaPor.addActionListener(this);
        this.BtnEditar.addActionListener(this);
        this.BtnEliminar.addActionListener(this);

    }

    //servira para actualizar los registros de la tabla
    public void actualizarTabla() {

        Conexion conectarse = new Conexion("datos/registro");
        ClaseConsultar consulta = new ClaseConsultar(conectarse.conectar(), "AreasTrabajo");

        if (TxtBusquedaPor.getSelectedItem().equals(DEFAULT_SELECTED)) {
            consulta.consultar("*");//consultar todo
        } else {
            consulta.consultar("*", busqueda.get(TxtBusquedaPor.getSelectedItem()),
                    TxtBuscar.getText());//busqueda por subcadena
        }

        RellenarTabla rellenar = new RellenarTabla(consulta.getResultadoConsulta(), Datos);
        rellenar.llenar();

        conectarse.cerrar();//cerrar conexion
        conectarse = null;
        consulta = null;
        System.gc();

    }

    private void rellenarComponentes() {

        this.TxtBuscar.setEditable(false);

        //iniciar los item del combobox
        for (int i = 0; i < 3; i++) {

            this.TxtBusquedaPor.removeAllItems();//limpiar el combobox
            this.TxtBusquedaPor.addItem(DEFAULT_SELECTED);
            this.TxtBusquedaPor.addItem("ID");
            busqueda.put("ID", "id");
            this.TxtBusquedaPor.addItem("NOMBRE AREA");
            busqueda.put("NOMBRE AREA", "NombreArea");
        }
    }

    private void editarRegistro() {

        if (Datos.getSelectedRow() != -1) {//validar que este selccionada una fila

            DefaultTableModel modelo = (DefaultTableModel) this.Datos.getModel();//cast el modelo de jtable

            int ID = (int) modelo.getValueAt(Datos.getSelectedRow(), 0);//obtener el ID 
            String NombreArea = (String) modelo.getValueAt(Datos.getSelectedRow(), 1);//obtener nombre
            String Entrada = (String) modelo.getValueAt(Datos.getSelectedRow(), 2);//obtener hora entrada
            String Salida = (String) modelo.getValueAt(Datos.getSelectedRow(), 3);//obtener hora entrada

            this.BtnNuevo.doClick();

            this.controlNAT.activarActualizarArea(true, ID);//activar la actualizacion
            this.controlNAT.TxtId.setText(String.valueOf(ID));
            this.controlNAT.TxtAreaTrabajo.setText(NombreArea);
            this.controlNAT.TxtHoraEntrada.setText(Entrada);
            this.controlNAT.TxtHoraSalida.setText(Salida);

        }

    }

    private void eliminarRegistro() {
        if (Datos.getSelectedRow() != -1) {//validar que este selccionada una fila

            DefaultTableModel modelo = (DefaultTableModel) this.Datos.getModel();//cast el modelo de jtable

            int ID = (int) modelo.getValueAt(Datos.getSelectedRow(), 0);//obtener el ID 
           // String NombreArea = (String) modelo.getValueAt(Datos.getSelectedRow(), 1);//obtener nombre
          //  String Entrada = (String) modelo.getValueAt(Datos.getSelectedRow(), 2);//obtener hora entrada
          //  String Salida = (String) modelo.getValueAt(Datos.getSelectedRow(), 3);//obtener hora entrada

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

                    ClaseContarRegistros contar = new ClaseContarRegistros();
                    if (contar.contarReg("datos/registro", "Empleados", "AreasTrabajo_id", String.valueOf(ID)) > 0) {//comprobar si esta asignada
                        JOptionPane.showMessageDialog(this, "Uno o más empleados tienen asignada ésta Área."
                                + "\nPara poderla eliminar deberá asignarles otra Área a los empleados"
                                + "\nque esten en ésta Área.",
                                "Control Acceso", JOptionPane.ERROR_MESSAGE);
                    } else {//sino pues se elimina
                        
                        Conexion con = new Conexion("datos/registro");
                        ClaseEliminar elim = new ClaseEliminar(con.conectar(), "AreasTrabajo");
                        elim.borrar("id", String.valueOf(ID));//eliminar el area
                        actualizarTabla();//actualizar la tabla
                        //JOptionPane.showMessageDialog(this, "Area eliminada correctamente.",
                          //  "Control Acceso", JOptionPane.INFORMATION_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Contraseña incorrecta.",
                            "Control Acceso", JOptionPane.ERROR_MESSAGE);

                }
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //////////boton de nueva area de trabajo///////////////
        if (e.getSource().equals(BtnNuevo)) {

            controlMain.getBarraBotones().btnAreaTrabajo.setEnabled(true);

            //oculta boton de imprimir de la barra botones
            this.controlMain.getBarraBotones().btnImprimir.setVisible(false);

            this.controlNAT = new ControlNuevaAreaTrabajo(controlMain, this);
            cambiar.cambiarPNL(controlMain.getVentanaPrincipal().panelContenedor, controlNAT);

        }

        /////////////combobox de busqueda por///////////////
        if (e.getSource().equals(TxtBusquedaPor)) {
            TxtBuscar.setText(null);
            if (TxtBusquedaPor.getSelectedItem().equals(DEFAULT_SELECTED)) {

                this.TxtBuscar.setEditable(false);
                actualizarTabla();

            } else {
                this.TxtBuscar.setEditable(true);
            }
        }

        /////////////boton de editar////////////////
        if (e.getSource().equals(BtnEditar)) {
            editarRegistro();
        }

        if (e.getSource().equals(BtnEliminar)) {
            eliminarRegistro();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

        if (TxtBusquedaPor.getSelectedItem().equals("ID")) {

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
        actualizarTabla();
    }

}
