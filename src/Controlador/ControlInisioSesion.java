/*
 * Esta clase es encargada de la validacion de inicio de sesion
 *por medio de la huella
 */
package Controlador;

import Modelo.ClaseConsultar;
import Modelo.Conexion;
import Modelo.Personal;
import Vista.FramePrincipal;
import Vista.FrameInicioSesion;
import Vista.PnlBarraBotones;
import Vista.PnlCredenciales;
import Vista.PnlHuella;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

public class ControlInisioSesion extends ClaseLector implements ActionListener {

    //attributos para la vista
    private FrameInicioSesion formulario = null;
    private PnlCredenciales pnlCredenciales = null;
    private PnlHuella pnlHuella = null;

    private CambiarPanel cambiar = null;
    private boolean pulso = true;//para el pulso del boton acceso alternativo

    //encapsulamiento de datos de usuario
    private Personal personal = null;

    public ControlInisioSesion() {
        super();//llamar al constructor de la clase padre

        this.formulario = new FrameInicioSesion();
        this.pnlCredenciales = new PnlCredenciales();
        this.pnlHuella = new PnlHuella();
        this.personal = new Personal();

        this.pnlHuella.TxtNombreUsuario.setText("- - - -");//poner texto inicial
        this.pnlHuella.LblHuella.setIcon(new ImageIcon(getClass()
                .getResource("/Img/Huella.png")));

        this.formulario.btnAccesoAlter.addActionListener(this);
        this.formulario.BtnAccesoSistema.addActionListener(this);
        this.formulario.BtnAccesoSistema.setVisible(false);
        this.pnlCredenciales.BtbEntrar.addActionListener(this);

        //insertar el panel de la huella
        cambiar = new CambiarPanel(formulario.PnlCentral, pnlHuella);

        this.formulario.setLocationRelativeTo(null);
        this.formulario.setVisible(true);

        iniciarEventosLector();
        super.start();
    }

    protected void iniciarEventosLector() {

        /*
        EL lector tiene sus propios eventos, los cuales se activan desde esta
        funcion, aca se activan solamente cuatro
         */
        //----------------Metodo para capturar la huella--------------------------------------------
        Lector.addDataListener(new DPFPDataAdapter() {//agregar el detector de eventos de datos

            @Override
            public void dataAcquired(final DPFPDataEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("La huella ha sido capturada");

                        identificarPersona(e.getSample());//luego se busca la huella
                    }
                });
            }

        });

        //------------------Metodo para saber el estado del lector---------------------------------------
        Lector.addReaderStatusListener(new DPFPReaderStatusAdapter() {//agrega el evento de estado del lector
            @Override
            public void readerConnected(final DPFPReaderStatusEvent e) {//si el lector esta activado
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                        System.out.println("EL sensor de huella se encuentra activado");
                    }
                });
            }

            @Override
            public void readerDisconnected(final DPFPReaderStatusEvent e) {//si el lector esta desactivado
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("El sensor se encuentra desactivado");
                    }
                });
            }
        });

        //-----------------Metodo para saber los errores del lector-----------------------------------------
        Lector.addErrorListener(new DPFPErrorAdapter() {//agrega el detector de eventos de errores
            public void errorReader(final DPFPErrorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Error: " + e.getError());
                    }
                });
            }
        });
    }

    private void iniciarVentanaPrincipal(String privilegio) {

        //instancias
        FramePrincipal ventanaPrincipal = new FramePrincipal();
        PnlBarraBotones barraBotones = new PnlBarraBotones();

        if (privilegio.equals("Propietario")) {
            barraBotones.btnAdmin.setVisible(true);//si es el propietario, boton admin es visible
        } else {
            barraBotones.btnAdmin.setVisible(false);
        }

        //setear el panel
        cambiar = new CambiarPanel(ventanaPrincipal.panelBarra, barraBotones);
        ventanaPrincipal.LblPrivilegio.setIcon(new ImageIcon(getClass()
                .getResource("/Img/ControlAcceso" + privilegio + ".png")));

        //ocultar el login
        this.formulario.setVisible(false);

        //hacer visible la ventana principal del sistema
        ventanaPrincipal.setLocationRelativeTo(null);
        ventanaPrincipal.setVisible(true);

    }

    private void identificarPersona(DPFPSample muestra)//para buscar la huella que se capturo
    {
        //guardar la plantilla capturada
        featureSetVerificacion = extraerCaracteristicas(muestra,
                DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

        Conexion conectar = new Conexion("datos/registro");
        
        
        ClaseConsultar consulta = new ClaseConsultar(conectar.conectar(), "Usuarios,Empleados");

        consulta.consultar("Usuarios.DPI, Usuarios.Privilegio, Usuarios.Huella, Empleados.Nombre, "
                + "Empleados.Apellidos", "Usuarios.DPI", "=", "Empleados.Usuarios_DPI");

        try {

            while (consulta.getResultadoConsulta().next()) {//iterear en cada resultado

                //compara la plantilla capturada actualmente (Plantilla vs huella en la BD)
                if (verificarHuella(featureSetVerificacion, consulta.getResultadoConsulta()
                        .getBytes("Huella"))) {//si se encontro entra aca

                    //rellenar los datos
                    this.personal.setDPI(consulta.getResultadoConsulta().getLong("DPI"));
                    this.personal.setPrivilegio(consulta.getResultadoConsulta().getString("Privilegio"));
                    this.personal.setNombre(consulta.getResultadoConsulta().getString("Nombre"));
                    this.personal.setApellidos(consulta.getResultadoConsulta().getString("Apellidos"));

                    System.out.println("La huella es de: " + personal.getNombre());

                    //pone nombre del usuario en la vista
                    this.pnlHuella.TxtNombreUsuario.setText(personal.getNombre() + " "
                            + personal.getApellidos());

                    //poner imagen 
                    this.pnlHuella.LblHuella.setIcon(new ImageIcon(getClass()
                            .getResource("/Img/HuellaReconocida.png")));

                    super.stop();//detener el lector

                    this.formulario.btnAccesoAlter.setEnabled(false);//desactivar el boton

                    if (personal.getPrivilegio().equals("Admin")) {

                        //el boton de acceso se activa solo si es admin
                        this.formulario.BtnAccesoSistema.setVisible(true);
                    }

                    return;//romper el ciclo y retorna

                } else {

                    System.out.println("No se encuentra la huella");
                    //poner imagen 
                    this.pnlHuella.LblHuella.setIcon(new ImageIcon(getClass()
                            .getResource("/Img/HuellaDesconocida.png")));
                    this.pnlHuella.TxtNombreUsuario.setText("- - - -");

                }
            }

            //si no se encuentran datos que coincidan, entonces llega hasta aca
            consulta = new ClaseConsultar(conectar.conectar(), "Usuarios,Propietarios");

            consulta.consultar("Usuarios.DPI, Usuarios.Privilegio, Usuarios.Huella, Propietarios.Nombre, "
                    + "Propietarios.Apellidos", "Usuarios.DPI", "=", "Propietarios.Usuarios_DPI");//obtener 

            while (consulta.getResultadoConsulta().next()) {//iterear en cada resultado
                
                //compara la plantilla capturada actualmente (Plantilla vs huella en la BD)
                if (verificarHuella(featureSetVerificacion, consulta.getResultadoConsulta()
                        .getBytes("Huella"))) {//si se encontro entra aca

                    //rellenar los datos
                    this.personal.setDPI(consulta.getResultadoConsulta().getLong("DPI"));
                    this.personal.setPrivilegio(consulta.getResultadoConsulta().getString("Privilegio"));
                    this.personal.setNombre(consulta.getResultadoConsulta().getString("Nombre"));
                    this.personal.setApellidos(consulta.getResultadoConsulta().getString("Apellidos"));
                    
                    System.out.println("La huella es de: " + personal.getNombre());

                    //pone nombre del usuario en la vista
                    this.pnlHuella.TxtNombreUsuario.setText(personal.getNombre() + " "
                            + personal.getApellidos());

                    //poner imagen 
                    this.pnlHuella.LblHuella.setIcon(new ImageIcon(getClass()
                            .getResource("/Img/HuellaReconocida.png")));

                    super.stop();//detener el lector

                    this.formulario.btnAccesoAlter.setEnabled(false);//desactivar el boton

                    //se activa boton de acceso al sistema
                    this.formulario.BtnAccesoSistema.setVisible(true);

                    return;//romper el ciclo y retorna

                } else {

                    System.out.println("No se encuentra la huella");
                    //poner imagen 
                    this.pnlHuella.LblHuella.setIcon(new ImageIcon(getClass()
                            .getResource("/Img/HuellaDesconocida.png")));
                    this.pnlHuella.TxtNombreUsuario.setText("- - - -");

                }
            }

        } catch (SQLException ex) {
            System.err.print("Error al consultar los datos: " + ex.getMessage());
        } finally {
            conectar.cerrar();//cerrar la conexion
        }

    }

    private void identificarCredenciales() {

        Conexion conectar = new Conexion("datos/registro");
        ClaseConsultar consulta = new ClaseConsultar(conectar.conectar(), "Usuarios,Empleados");

        consulta.consultar("Usuarios.DPI, Usuarios.NombreUsuario, Usuarios.Contrasenia,"
                + "Usuarios.Privilegio, Empleados.Nombre, Empleados.Apellidos");//obtener todo esto de la tabla

        try {

            while (consulta.getResultadoConsulta().next())//recorrer los datos
            {
                if (consulta.getResultadoConsulta().getString("NombreUsuario")
                        .equals(pnlCredenciales.txtUser.getText())
                        && consulta.getResultadoConsulta().getString("Contrasenia")
                                .equals(pnlCredenciales.txtPassword.getText())) {

                    System.out.println("Acceso concedido");

                    //rellenar los datos
                    this.personal.setDPI(consulta.getResultadoConsulta().getLong("DPI"));
                    this.personal.setPrivilegio(consulta.getResultadoConsulta().getString("Privilegio"));
                    this.personal.setNombre(consulta.getResultadoConsulta().getString("Nombre"));
                    this.personal.setApellidos(consulta.getResultadoConsulta().getString("Apellidos"));

                    this.formulario.btnAccesoAlter.setEnabled(false);//desactivar el boton acceso alternativo

                    if (personal.getPrivilegio().equals("Admin")) {
                        //el boton de acceso se activa solo si es admin
                        this.formulario.BtnAccesoSistema.setVisible(true);
                    }

                    return;//romper el ciclo

                } else {
                    System.out.println("Acceso denegado");
                }
            }

            //si no se encuentraron datos en la primera consulta entoncs llega hasta aca
            consulta = new ClaseConsultar(conectar.conectar(), "Usuarios,Propietarios");

            consulta.consultar("Usuarios.DPI, Usuarios.NombreUsuario, Usuarios.Contrasenia,"
                    + "Usuarios.Privilegio, Propietarios.Nombre, Propietarios.Apellidos");//obtener todo esto de la tabla

            while (consulta.getResultadoConsulta().next())//recorrer los datos
            {
                if (consulta.getResultadoConsulta().getString("NombreUsuario")
                        .equals(pnlCredenciales.txtUser.getText())
                        && consulta.getResultadoConsulta().getString("Contrasenia")
                                .equals(pnlCredenciales.txtPassword.getText())) {

                    System.out.println("Acceso concedido");

                    //rellenar los datos
                    this.personal.setDPI(consulta.getResultadoConsulta().getLong("DPI"));
                    this.personal.setPrivilegio(consulta.getResultadoConsulta().getString("Privilegio"));
                    this.personal.setNombre(consulta.getResultadoConsulta().getString("Nombre"));
                    this.personal.setApellidos(consulta.getResultadoConsulta().getString("Apellidos"));
                    this.formulario.btnAccesoAlter.setEnabled(false);//desactivar el boton acceso alternativo

                    //el boton de acceso se activa solo si es admin
                    this.formulario.BtnAccesoSistema.setVisible(true);

                    return;//romper el ciclo

                } else {
                    System.out.println("Acceso denegado");
                }
            }

        } catch (SQLException ex) {
            System.err.print("Error al consultar los datos: " + ex.getMessage());
        } finally {
            conectar.cerrar();//cerramos conexion
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {//eventos de la vista

        if (e.getSource() == formulario.btnAccesoAlter) {//boton acceso alternativo

            if (pulso) {

                //pasar al panel de credenciales
                this.cambiar = new CambiarPanel(formulario.PnlCentral, pnlCredenciales);
                super.stop();//para el lector
                pulso = false;

            } else if (!pulso) {
                //pasar al panel de huella
                this.cambiar = new CambiarPanel(formulario.PnlCentral, pnlHuella);
                super.start();//iniciar el lector
                pulso = true;
            }

        }//fin if

        if (e.getSource() == pnlCredenciales.BtbEntrar) {//boton de ingresar

            identificarCredenciales();
        }//fin if

        if (e.getSource() == formulario.BtnAccesoSistema) {//boton de acceso al sistema

            iniciarVentanaPrincipal(personal.getPrivilegio());//iniciar la ventana segun privilegio
        }

    }

}
