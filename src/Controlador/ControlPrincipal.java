/*
 * Control principal del programa
 */
package Controlador;

import Modelo.Personal;
import Vista.FramePrincipal;
import Vista.PnlBarraBotones;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Jaasiel Guerra
 */
public class ControlPrincipal implements ActionListener {

    //instancias
    private FramePrincipal ventanaPrincipal = null;
    private PnlBarraBotones barraBotones = null;
    private ControlInisioSesion ctrlLogin;
    private ControlRegistro ctrlRegistro;
    private ControlBarraBotones CtrlBarraBTN = null;

    private Personal personal = null;

    private ClaseContarRegistros contador = null;
    //private PnlTablaDatos tablaDatos = null;
    private ControlRegistroEmpleado registroEmp = null;
    private ControlTablaDatos ctrlTablaD = null;
    private ControlAreasTrabajo ctrlAreasT = null;

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

        this.barraBotones = new PnlBarraBotones();

        this.barraBotones.btnAdmin.setVisible(
                this.personal.getPrivilegio().equals("Propietario")
        );//si es el propietario, boton admin es visible

        this.barraBotones.btnEmpleado.addActionListener(this);
        this.barraBotones.btnAreaTrabajo.addActionListener(this);
        this.barraBotones.btnAdmin.addActionListener(this);
        this.barraBotones.btnHistorial.addActionListener(this);
        this.barraBotones.btnImprimir.addActionListener(this);
        this.barraBotones.btnAddUser.addActionListener(this);

        this.CtrlBarraBTN = new ControlBarraBotones(this);

        //setear el panel
        this.cambiar.cambiarPNL(ventanaPrincipal.panelBarra, barraBotones);

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

        } else if (this.contador.contarReg("datos/registro", "Usuarios") == 0) {//si no existe nigun usuario

            this.ctrlRegistro = new ControlRegistro(this, personal);
        }

    }

    public FramePrincipal getVentanaPrincipal() {
        return ventanaPrincipal;
    }

    public PnlBarraBotones getBarraBotones() {
        return barraBotones;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /////////////////boton de cerrar sesion/////////////
        if (e.getSource().equals(this.ventanaPrincipal.btnLogout)) {
            this.ventanaPrincipal.setVisible(false);

            this.ctrlLogin.resetInisioSesion();
        }

        ///////////////boton agregar empleado////////////////
        if (e.getSource().equals(this.barraBotones.btnAddUser)) {

            //comprobar si existen areas de trabajo
            if (this.contador.contarReg("datos/registro", "AreasTrabajo") > 0) {

                this.registroEmp = new ControlRegistroEmpleado(this, this.personal);
                this.cambiar.cambiarPNL(this.ventanaPrincipal.panelContenedor,
                        this.registroEmp.getPnlRegistroPer());
            } else {

                JOptionPane.showMessageDialog(null, "Aun no existe ningún área de trabajo."
                        + "\nPor favor registre al menos una en el apartado de"
                        + " <Área de Trabajo> para poder registar empleados.", "Control Acceso",
                        JOptionPane.WARNING_MESSAGE);
            }

        }

        //////////////////boton empleados//////////////
        if (e.getSource().equals(this.barraBotones.btnEmpleado)) {

            //comprobar si existen empleados
            if (this.contador.contarReg("datos/registro", "Empleados") > 0) {

                this.ctrlTablaD = new ControlTablaDatos();
                this.cambiar.cambiarPNL(this.ventanaPrincipal.panelContenedor,
                        this.ctrlTablaD);
            } else {

                int option = JOptionPane.showConfirmDialog(
                        null,
                        "Aun no existe ningún empleado en el registro."
                        + "\n¿Desea registrar un nuevo empleado?",
                        "Control Acceso",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION) {
                    this.barraBotones.btnAddUser.doClick();//accion de clic al boton de add usuario
                }
            }

        }

    }

}
