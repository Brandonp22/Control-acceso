/*
 * Control principal del programa
 */
package Controlador;

import Modelo.Conexion;
import Modelo.Personal;
import Vista.FramePrincipal;
import Vista.PnlBarraBotones;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Jaasiel Guerra
 */
public class ControlPrincipal implements ActionListener {

    //instancias
    private FramePrincipal ventanaPrincipal = null;
    //private PnlBarraBotones barraBotones = null;
    private ControlInisioSesion ctrlLogin;
    private ControlRegistro ctrlRegistro;
    private ControlBarraBotones CtrlBarraBTN = null;

    private Personal personal = null;

    private ClaseContarRegistros contador = null;
    //private PnlTablaDatos tablaDatos = null;
    private ControlRegistroEmpleado registroEmp = null;
    private ControlTablaDatos ctrlTablaDat = null;
    private ControlAreasTrabajo ctrlAreasT = null;
    private ControlNuevaAreaTrabajo ctrlNAT = null;
    private ControlTablaHistorial ctrlTDH = null;

    private final String EMPLEADO = "Empleado";
    private final String ADMINISTRADOR = "Admin";

    private CambiarPanel cambiar = null;

    public ControlPrincipal() {

        this.ventanaPrincipal = new FramePrincipal();

        this.personal = new Personal();

        this.contador = new ClaseContarRegistros();

        this.cambiar = new CambiarPanel();

        this.ventanaPrincipal.btnLogout.addActionListener(this);

        //poner panel de fecha y hora
        this.cambiar.cambiarPNL(ventanaPrincipal.PanelFechaHora,
                new ControlFechaHora().getFechaHora());

        this.cambiar.cambiarPNL(ventanaPrincipal.PanelMinCerrar,
                new ControlCerrarMinimizar(ventanaPrincipal).getPnlMC());

        comprobarExisteUsuario();
    }

    //este metodo prepara todo para mostrar el frame principal
    //una vez llenado personal en login o registro
    public void starVentanaPrincipal() {

        //this.barraBotones = new PnlBarraBotones();
        this.CtrlBarraBTN = new ControlBarraBotones();

        this.CtrlBarraBTN.btnAdmin.setVisible(
                this.personal.getPrivilegio().equals("Propietario")
        );//si es el propietario, boton admin es visible

        this.CtrlBarraBTN.btnEmpleado.addActionListener(this);
        this.CtrlBarraBTN.btnAreaTrabajo.addActionListener(this);
        this.CtrlBarraBTN.btnAdmin.addActionListener(this);
        this.CtrlBarraBTN.btnHistorial.addActionListener(this);
        this.CtrlBarraBTN.btnImprimir.addActionListener(this);
        this.CtrlBarraBTN.btnAddUser.addActionListener(this);

        //setear el panel
        this.cambiar.cambiarPNL(ventanaPrincipal.panelBarra, CtrlBarraBTN);

        this.ventanaPrincipal.LblPrivilegio.setIcon(new ImageIcon(getClass()
                .getResource("/Img/ControlAcceso" + this.personal.getPrivilegio() + ".png")));

        this.ventanaPrincipal.panelContenedor.removeAll();//remover el contenido del panel 
        //this.ventanaPrincipal.panelContenedor.revalidate();
        //this.ventanaPrincipal.panelContenedor.repaint();

        /*
        * instancia del controlador de registro de emplados, 
        * se le envia los datos del personal para validar que 
        * componentes son visibles dependiendo del privilegio
         */
        //this.registroEmp = new ControlRegistroEmpleado(this,this.personal);
        this.ventanaPrincipal.setLocationRelativeTo(null);
        this.ventanaPrincipal.setVisible(true);

    }

    /*
    * aca se comprueba si existe al menos un usuario, si existe 
    * entonces se inicia el login, si no existe se inicia la ventnana
    * de registro para insertar el primer usuario y por ende propietario
     */
    private void comprobarExisteUsuario() {

        //si existen usuarios
        if (this.contador.contarReg("datos/registro", "Usuarios") > 0) {

            this.ctrlLogin = new ControlInisioSesion(this, personal);

        } else {//si no existe nigun usuario

            this.ctrlRegistro = new ControlRegistro(this, personal);
        }

    }

    public FramePrincipal getVentanaPrincipal() {
        return ventanaPrincipal;
    }

    public PnlBarraBotones getBarraBotones() {
        return CtrlBarraBTN;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //this.ventanaPrincipal.panelContenedor.removeAll();//limpiar el panel principal
        /////////////////boton de cerrar sesion/////////////
        if (e.getSource().equals(this.ventanaPrincipal.btnLogout)) {
            this.ventanaPrincipal.setVisible(false);

            this.ctrlLogin.resetInisioSesion();
        }

        ///////////////boton agregar empleado////////////////
        if (e.getSource().equals(this.CtrlBarraBTN.btnAddUser)) {

            //comprobar si existen areas de trabajo
            if (this.contador.contarReg("datos/registro", "AreasTrabajo") > 0) {

                this.registroEmp = new ControlRegistroEmpleado(this, this.personal);
                this.cambiar.cambiarPNL(this.ventanaPrincipal.panelContenedor,
                        this.registroEmp.getPnlRegistroPer());
            } else {

                int option = JOptionPane.showConfirmDialog(null,
                        "Para registrar Empleados debe existir al menos un Área de Trabajo."
                        + "\n¿Desea registrar una nueva?", "Control Acceso",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION) {

                    this.ctrlAreasT = new ControlAreasTrabajo(this);
                    this.ctrlNAT = new ControlNuevaAreaTrabajo(this, ctrlAreasT);
                    this.cambiar.cambiarPNL(this.ventanaPrincipal.panelContenedor,
                            this.ctrlNAT);

                }

            }

        }

        //////////////////boton ver empleados//////////////
        if (e.getSource().equals(this.CtrlBarraBTN.btnEmpleado)) {

            //comprobar si existen empleados
            if (this.contador.contarReg("datos/registro", "Usuarios", "Privilegio", "Empleado") > 0) {

                this.CtrlBarraBTN.setActivarDesactivarBtn(true);
                this.ctrlTablaDat = new ControlTablaDatos();
                this.ctrlTablaDat.actualizarTabla(EMPLEADO);
                this.cambiar.cambiarPNL(this.ventanaPrincipal.panelContenedor,
                        this.ctrlTablaDat);
            } else {

                int option = JOptionPane.showConfirmDialog(
                        null,
                        "Aún no existe ningún empleado en el registro."
                        + "\n¿Desea registrar un nuevo empleado?",
                        "Control Acceso",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION) {
                    this.CtrlBarraBTN.btnAddUser.doClick();//accion de clic al boton de add usuario
                    this.CtrlBarraBTN.setActivarDesactivarBtn(false);
                } else {
                    this.CtrlBarraBTN.setActivarDesactivarBtn(false);
                }
            }

        }

        //////////////////boton ver AreasTRabajo//////////////
        if (e.getSource().equals(this.CtrlBarraBTN.btnAreaTrabajo)) {

            //comprobar si existen areas
            if (this.contador.contarReg("datos/registro", "AreasTrabajo") > 0) {
                
                this.CtrlBarraBTN.setActivarDesactivarBtn(true);
                this.ctrlAreasT = new ControlAreasTrabajo(this);
                this.cambiar.cambiarPNL(this.ventanaPrincipal.panelContenedor,
                        this.ctrlAreasT);

                this.ctrlNAT = null;//colgar el objeto para que java lo elimine en algun momento
            } else {

                int option = JOptionPane.showConfirmDialog(
                        null,
                        "Todavía no existe ningún Área de Trabajo."
                        + "\n¿Desea registrar una nueva?",
                        "Control Acceso",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION) {

                    this.ctrlAreasT = new ControlAreasTrabajo(this);
                    this.ctrlNAT = new ControlNuevaAreaTrabajo(this, ctrlAreasT);
                    this.cambiar.cambiarPNL(this.ventanaPrincipal.panelContenedor,
                            this.ctrlNAT);
                    this.CtrlBarraBTN.setActivarDesactivarBtn(false);
                } else {
                    this.CtrlBarraBTN.setActivarDesactivarBtn(false);
                }
            }

        }

        //////////////////boton ver administradoress//////////////
        if (e.getSource().equals(this.CtrlBarraBTN.btnAdmin)) {

            //comprobar si existen empleados
            if (this.contador.contarReg("datos/registro", "Usuarios", "Privilegio", "Admin") > 0) {

                this.CtrlBarraBTN.setActivarDesactivarBtn(true);
                this.ctrlTablaDat = new ControlTablaDatos();
                this.ctrlTablaDat.actualizarTabla(ADMINISTRADOR);
                this.cambiar.cambiarPNL(this.ventanaPrincipal.panelContenedor,
                        this.ctrlTablaDat);
            } else {

                int option = JOptionPane.showConfirmDialog(
                        null,
                        "Aún no existe ningún administrador en el registro."
                        + "\n¿Desea registrar un nuevo administrador?",
                        "Control Acceso",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION) {
                    this.CtrlBarraBTN.btnAddUser.doClick();//accion de clic al boton de add usuario
                    this.CtrlBarraBTN.setActivarDesactivarBtn(false);
                } else {
                    this.CtrlBarraBTN.setActivarDesactivarBtn(false);
                }
            }

        }

        //////////////////boton ver historial//////////////
        if (e.getSource().equals(this.CtrlBarraBTN.btnHistorial)) {

            //comprobar si hay historial
            if (this.contador.contarReg("datos/registro", "EntradasSalidas") > 0) {

                this.ctrlTDH = new ControlTablaHistorial();

                this.ctrlTDH.actualizarTabla(this.CtrlBarraBTN.getBotonPulsado());

                this.cambiar.cambiarPNL(this.ventanaPrincipal.panelContenedor,
                        this.ctrlTDH);
            } else {

                JOptionPane.showMessageDialog(null,
                        "El historial de horarios esta vacío.", "Control Acceso",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        }

        //////////////////boton imprimir Reporte//////////////
        if (e.getSource().equals(this.CtrlBarraBTN.btnImprimir)) {

            try {

                Conexion conec = new Conexion("datos/registro");

                JasperReport reporte = null;//creamos la variable Jasreport reporte
                String path = "src/Reportes/ReporteEmpleados.jasper";//ruta del reporte

                reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y le enviamos el path

                JasperPrint jprint = JasperFillManager.fillReport(reporte, null, conec.conectar());//caste y lo hacemos a jaspereport

                JasperViewer view = new JasperViewer(jprint, false);//llenado del reporte

                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//habilitmos la x para salir

                view.setTitle("Reportes de Empleados");

                view.setVisible(true);

            } catch (JRException ex) {
                System.out.println("No se pudo imprimir el reporte: " + ex.getMessage());
            }

        }

    }

}
