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

    private Personal personal = null;

    private ClaseContarRegistros contador = null;
    //private PnlTablaDatos tablaDatos = null;
    private ControlRegistroEmpleado registroEmp = null;

    private CambiarPanel cambiar = null;

    public ControlPrincipal() {

        this.ventanaPrincipal = new FramePrincipal();
        this.barraBotones = new PnlBarraBotones();

        this.personal = new Personal();

        this.contador = new ClaseContarRegistros();

        this.cambiar = new CambiarPanel();

        this.ventanaPrincipal.btnLogout.addActionListener(this);
        this.barraBotones.btnEmpleado.addActionListener(this);
        this.barraBotones.btnAreaTrabajo.addActionListener(this);
        this.barraBotones.btnAdmin.addActionListener(this);
        this.barraBotones.btnHistorial.addActionListener(this);
        this.barraBotones.btnImprimir.addActionListener(this);
        this.barraBotones.btnAddUser.addActionListener(this);

        comprobarExisteUsuario();
    }

    //este metodo prepara todo para mostrar el frame principal
    //una vez llenado personal en login o registro
    public void starVentanaPrincipal() {

        this.barraBotones.btnAdmin.setVisible(
                this.personal.getPrivilegio().equals("Propietario")
        );//si es el propietario, boton admin es visible

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
        this.registroEmp = new ControlRegistroEmpleado(this.personal);

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

    @Override
    public void actionPerformed(ActionEvent e) {

        //boton de cerrar sesion
        if (e.getSource().equals(this.ventanaPrincipal.btnLogout)) {
            this.ventanaPrincipal.setVisible(false);

            this.ctrlLogin.resetInisioSesion();
        }

        //boton agregar empleado
        if (e.getSource().equals(this.barraBotones.btnAddUser)) {

            if (this.contador.contarReg("datos/registro", "AreasTrabajo") > 0) {
                this.cambiar.cambiarPNL(this.ventanaPrincipal.panelContenedor,
                        this.registroEmp.getPnlRegistroPer());
            } else {
                JOptionPane.showMessageDialog(null, "Aun no existe ningún área de trabajo."
                        + "\nPor favor registre al menos una en el apartado de"
                        + " <Área de Trabajo> para poder registar empleados.", "Control Acceso",
                        JOptionPane.WARNING_MESSAGE);
            }

        }

    }

}
