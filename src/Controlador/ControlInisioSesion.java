/*
 * Esta clase es encargada de la validacion de inicio de sesion
 *por medio de la huella
 */
package Controlador;

import Modelo.ClaseConsultar;
import Modelo.ClaseInsertar;
import Modelo.ClaseModificar;
import Modelo.Conexion;
import Modelo.Personal;
import Vista.FrameInicioSesion;
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
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class ControlInisioSesion extends ClaseLector implements ActionListener {

    //attributos para la vista
    private FrameInicioSesion formulario = null;

    private PnlCredenciales pnlCredenciales = null;
    private PnlHuella pnlHuella = null;

    private ControlPrincipal CtrlMain = null;

    private CambiarPanel cambiar = null;
    private boolean pulso = true;//para el pulso del boton acceso alternativo

    //encapsulamiento de datos de usuario
    private Personal personal = null;

    private final long TIEMPO_ESPERA = 2500;
    
    private ControlCerrarMinimizar ctrlCM = null;

    /////////cobstructor/////////
    public ControlInisioSesion(ControlPrincipal ventanaMain, Personal personal) {
        super();//llamar al constructor de la clase padre

        this.formulario = new FrameInicioSesion();
        this.pnlCredenciales = new PnlCredenciales();
        this.pnlHuella = new PnlHuella();
        this.personal = personal;
        this.CtrlMain = ventanaMain;

        this.pnlHuella.TxtNombreUsuario.setText("- - - -");//poner texto inicial
        this.pnlHuella.LblHuella.setIcon(new ImageIcon(getClass()
                .getResource("/Img/Huella.png")));
        this.pnlHuella.TxtIndicador.setText(null);
        this.pnlCredenciales.TxtIndicadorCred.setText(null);

        this.formulario.btnAccesoAlter.addActionListener(this);
        this.formulario.BtnAccesoSistema.addActionListener(this);
        this.formulario.BtnAccesoSistema.setVisible(false);
        this.pnlCredenciales.BtbEntrar.addActionListener(this);

        //insertar el panel de la huella
        cambiar = new CambiarPanel();
        cambiar.cambiarPNL(formulario.PnlCentral, pnlHuella);

        iniciarEventosLector();
        super.start();

        this.formulario.RBtnMarcarIO.setSelected(true);
        this.formulario.BtnGrupo.add(formulario.RBtnMarcarIO);
        this.formulario.BtnGrupo.add(formulario.RBtnAccesoSistema);

        this.formulario.setLocationRelativeTo(null);
        this.formulario.setVisible(true);

        this.formulario.setIconImage(new ImageIcon(getClass().getResource("/Img/Logo.png")).getImage());
        this.ctrlCM = new ControlCerrarMinimizar(this);
        cambiar.cambiarPNL(formulario.PanelMinCerrar, ctrlCM.getPnlMC());
    }

    private void iniciarEventosLector() {

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
                        pnlHuella.LblHuella.setIcon(new ImageIcon(getClass()
                                .getResource("/Img/Huella.png")));
                    }
                });
            }

            @Override
            public void readerDisconnected(final DPFPReaderStatusEvent e) {//si el lector esta desactivado
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("El sensor se encuentra desactivado");
                        pnlHuella.LblHuella.setIcon(new ImageIcon(getClass()
                                .getResource("/Img/LectorDesactivadoDos.png")));
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

    private void iniciarVentanaPrincipal() {

        this.formulario.setVisible(false);
        this.CtrlMain.getVentanaPrincipal().setLocationRelativeTo(null);
        this.CtrlMain.getVentanaPrincipal().setVisible(true);
        this.CtrlMain.starVentanaPrincipal();
    }

    private void Marcaje_O_Acceso() {//para validar marcaje o acceso al sistema
        if (formulario.RBtnMarcarIO.isSelected()) {//si se quiere marcar IO
            if (personal.getPrivilegio().equals("Admin") || personal.getPrivilegio().equals("Empleado")) {

                this.formulario.btnAccesoAlter.setEnabled(false);//desactivar el boton
                super.stop();//detener el lector
                marcarIO();
                Timer timer = new Timer();
                TimerTask procesos = new TimerTask() {
                    @Override
                    public void run() {//subProceso aca
                        resetInisioSesion();
                    }
                };
                timer.schedule(procesos, TIEMPO_ESPERA);

            } else {
                this.pnlHuella.TxtIndicador.setForeground(Color.ORANGE);
                this.pnlHuella.TxtIndicador.setText("Usted es un propetario");
                this.pnlCredenciales.TxtIndicadorCred.setForeground(Color.ORANGE);
                this.pnlCredenciales.TxtIndicadorCred.setText("Usted es un propietario");

                super.stop();//detener el lector

                Timer timer = new Timer();
                TimerTask procesos = new TimerTask() {
                    @Override
                    public void run() {//subProceso aca
                        resetInisioSesion();
                    }
                };
                timer.schedule(procesos, TIEMPO_ESPERA);
            }
        } else if (formulario.RBtnAccesoSistema.isSelected()) {//si se quiere acceder al sistema

            if (personal.getPrivilegio().equals("Admin") || personal.getPrivilegio().equals("Propietario")) {

                this.formulario.btnAccesoAlter.setEnabled(false);//desactivar el boton
                super.stop();//detener el lector

                this.pnlHuella.TxtIndicador.setForeground(Color.GREEN);
                this.pnlHuella.TxtIndicador.setText("Acceso Concedido");
                this.pnlCredenciales.TxtIndicadorCred.setForeground(Color.GREEN);
                this.pnlCredenciales.TxtIndicadorCred.setText("Acceso Concedido");

                Timer timer = new Timer();
                TimerTask procesos = new TimerTask() {
                    @Override
                    public void run() {//subProceso aca
                        iniciarVentanaPrincipal();//iniciar la ventana segun privilegio

                    }
                };
                timer.schedule(procesos, TIEMPO_ESPERA);

            } else {
                this.pnlHuella.TxtIndicador.setForeground(Color.RED);
                this.pnlHuella.TxtIndicador.setText("Acceso denegado");
                this.pnlCredenciales.TxtIndicadorCred.setForeground(Color.RED);
                this.pnlCredenciales.TxtIndicadorCred.setText("Acceso denegado");

                Timer timer = new Timer();
                TimerTask procesos = new TimerTask() {
                    @Override
                    public void run() {//subProceso aca
                        resetInisioSesion();
                    }
                };
                timer.schedule(procesos, TIEMPO_ESPERA);
            }

        }
    }

    private void identificarPersona(DPFPSample muestra)//para buscar la huella que se capturo
    {
        //guardar la plantilla capturada
        featureSetVerificacion = extraerCaracteristicas(muestra,
                DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

        Conexion conectar = new Conexion("datos/registro");

        ClaseConsultar consulta = new ClaseConsultar(conectar.conectar(), "Usuarios,Empleados");

        consulta.consultar("Usuarios.DPI,Usuarios.Contrasenia, Usuarios.Privilegio, Usuarios.Huella, Empleados.Nombre, "
                + "Empleados.Apellidos", "Usuarios.DPI", "=", "Empleados.Usuarios_DPI");

        try {

            while (consulta.getResultadoConsulta().next()) {//iterear en cada resultado

                //compara la plantilla capturada actualmente (Plantilla vs huella en la BD)
                if (verificarHuella(featureSetVerificacion, consulta.getResultadoConsulta()
                        .getBytes("Huella"))) {//si se encontro entra aca

                    //rellenar los datos
                    this.personal.setDPI(consulta.getResultadoConsulta().getLong("DPI"));
                    this.personal.setContrasenia(consulta.getResultadoConsulta().getString("Contrasenia"));
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
                    conectar.cerrar();
                    Marcaje_O_Acceso();

                    return;//romper el ciclo 

                } else {

                    System.out.println("No se encuentra la huella");
                    //poner imagen 
                    this.pnlHuella.LblHuella.setIcon(new ImageIcon(getClass()
                            .getResource("/Img/HuellaDesconocida.png")));
                    this.pnlHuella.TxtNombreUsuario.setText("- - - -");

                }
            }

            //si no se encuentran datos que coincidan, entonces llega hasta aca
            //y se hace la consulta en la tabla propietarios
            //conectar = new Conexion("datos/registro");
            consulta = new ClaseConsultar(conectar.conectar(), "Usuarios,Propietarios");

            consulta.consultar("Usuarios.DPI, Usuarios.Contrasenia,Usuarios.Privilegio, Usuarios.Huella, Propietarios.Nombre, "
                    + "Propietarios.Apellidos", "Usuarios.DPI", "=", "Propietarios.Usuarios_DPI");//obtener 

            while (consulta.getResultadoConsulta().next()) {//iterear en cada resultado

                //compara la plantilla capturada actualmente (Plantilla vs huella en la BD)
                if (verificarHuella(featureSetVerificacion, consulta.getResultadoConsulta()
                        .getBytes("Huella"))) {//si se encontro entra aca

                    //rellenar los datos
                    this.personal.setDPI(consulta.getResultadoConsulta().getLong("DPI"));
                    this.personal.setContrasenia(consulta.getResultadoConsulta().getString("Contrasenia"));
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
                    conectar.cerrar();
                    Marcaje_O_Acceso();

                    //se activa boton de acceso al sistema
                    // this.formulario.BtnAccesoSistema.setVisible(true);
                    return;//romper el ciclo

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
            conectar.cerrar();
            conectar = null;
            consulta = null;
            System.gc();
        }

    }

    private void identificarCredenciales() {

        Conexion conectar = new Conexion("datos/registro");
        ClaseConsultar consulta = new ClaseConsultar(conectar.conectar(), "Usuarios,Empleados");

        consulta.consultar("Usuarios.DPI, Usuarios.NombreUsuario, Usuarios.Contrasenia,"
                + "Usuarios.Privilegio, Empleados.Nombre, Empleados.Apellidos",
                "Usuarios.DPI", " = ", "Empleados.Usuarios_DPI");//obtener todo esto de la tabla

        try {

            while (consulta.getResultadoConsulta().next())//recorrer los datos
            {
                if (consulta.getResultadoConsulta().getString("NombreUsuario")
                        .equals(pnlCredenciales.txtUser.getText().toUpperCase())
                        && consulta.getResultadoConsulta().getString("Contrasenia")
                                .equals(pnlCredenciales.txtPassword.getText())) {

                    System.out.println("Acceso concedido");

                    //rellenar los datos
                    this.personal.setDPI(consulta.getResultadoConsulta().getLong("DPI"));
                    this.personal.setPrivilegio(consulta.getResultadoConsulta().getString("Privilegio"));
                    this.personal.setNombre(consulta.getResultadoConsulta().getString("Nombre"));
                    this.personal.setApellidos(consulta.getResultadoConsulta().getString("Apellidos"));

                    conectar.cerrar();
                    Marcaje_O_Acceso();

                    return;//romper el ciclo

                } else {
                    this.pnlCredenciales.TxtIndicadorCred.setForeground(Color.RED);
                    this.pnlCredenciales.TxtIndicadorCred.setText("Credenciales Incorrectos");
                    System.out.println("Acceso denegado");
                }
            }

            //si no se encuentraron datos en la primera consulta entoncs llega hasta aca
            consulta = new ClaseConsultar(conectar.conectar(), "Usuarios,Propietarios");

            consulta.consultar("Usuarios.DPI, Usuarios.NombreUsuario, Usuarios.Contrasenia,"
                    + "Usuarios.Privilegio, Propietarios.Nombre, Propietarios.Apellidos",
                    "Usuarios.DPI", " = ", "Propietarios.Usuarios_DPI");//obtener todo esto de la tabla

            while (consulta.getResultadoConsulta().next())//recorrer los datos
            {
                if (consulta.getResultadoConsulta().getString("NombreUsuario")
                        .equals(pnlCredenciales.txtUser.getText().toUpperCase())
                        && consulta.getResultadoConsulta().getString("Contrasenia")
                                .equals(pnlCredenciales.txtPassword.getText())) {

                    System.out.println("Acceso concedido");

                    //rellenar los datos
                    this.personal.setDPI(consulta.getResultadoConsulta().getLong("DPI"));
                    this.personal.setPrivilegio(consulta.getResultadoConsulta().getString("Privilegio"));
                    this.personal.setNombre(consulta.getResultadoConsulta().getString("Nombre"));
                    this.personal.setApellidos(consulta.getResultadoConsulta().getString("Apellidos"));

                    conectar.cerrar();
                    Marcaje_O_Acceso();

                    return;//romper el ciclo

                } else {
                    this.pnlCredenciales.TxtIndicadorCred.setForeground(Color.RED);
                    this.pnlCredenciales.TxtIndicadorCred.setText("Credenciales Incorrectos");
                    System.out.println("Acceso denegado");
                }
            }

        } catch (SQLException ex) {
            System.err.print("Error al consultar los datos: " + ex.getMessage());
        } finally {
            conectar.cerrar();//cerramos conexion
            conectar = null;
            consulta = null;
            System.gc();

        }
    }

    public FrameInicioSesion getFormulario() {
        return formulario;
    }

    public void resetInisioSesion() {//para reiniciar el login

        ///////////reset lo de huella///////////////
        this.formulario.btnAccesoAlter.setEnabled(true);
        this.formulario.BtnAccesoSistema.setVisible(false);
        this.pnlHuella.TxtNombreUsuario.setText("- - - -");//poner texto inicial
        this.pnlHuella.LblHuella.setIcon(new ImageIcon(getClass()
                .getResource("/Img/Huella.png")));
        this.pnlHuella.TxtIndicador.setText(null);
        this.pnlCredenciales.TxtIndicadorCred.setText(null);

        //pasar al panel de huella
        this.cambiar.cambiarPNL(formulario.PnlCentral, pnlHuella);
        super.start();

        //////////////reset lo de credenciales
        this.pnlCredenciales.txtUser.setText(null);
        this.pnlCredenciales.txtPassword.setText(null);
        this.pulso = true;
        this.pnlCredenciales.BtbEntrar.setEnabled(true);

        ////////volver a hacer visible///////////
        this.formulario.setVisible(true);

        this.formulario.RBtnMarcarIO.setSelected(true);//seleccion por defecto
    }

    private void marcarIO() {//servira para marcar las entradas y salidas

        System.out.println("Marcar IO");
        Date date = new Date();
        DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        String Fecha = fecha.format(date);//obtener la fecha actual

        Conexion con = new Conexion("datos/registro");
        ClaseConsultar consulta = new ClaseConsultar(con.conectar(), "EntradasSalidas");
        consulta.consultar("Fecha,Estado,Empleados_Usuarios_DPI", "Empleados_Usuarios_DPI",
                "=", String.valueOf(personal.getDPI()));

        try {
            boolean esEntrada = true;

            while (consulta.getResultadoConsulta().next()) {

                /*
                 *  se comprueba si es la fecha actual, de serla
                 * entonces se dice que es una registro de SALIDA
                 * y se hace un UPDATE
                 */
                if (consulta.getResultadoConsulta().getString("Fecha").equals(Fecha)
                        && consulta.getResultadoConsulta().getInt("Estado") != 2) {

                    con.cerrar();
                    con = new Conexion("datos/registro");
                    //REGISTRO DE SALIDA
                    esEntrada = false;
                    DateFormat hora = new SimpleDateFormat("HH:mm");
                    String horaActual = hora.format(date);//hora actual

                    ClaseModificar mod = new ClaseModificar(con.conectar(), "EntradasSalidas");
                    mod.agregarValor("Salida", horaActual, "S");
                    mod.agregarValor("Estado", 2, "S");
                    mod.setDonde("Fecha", "'" + Fecha + "'" + " AND Empleados_Usuarios_DPI = "
                            + String.valueOf(personal.getDPI()));
                    if (mod.ejecutarSQL()) {//ejecutar UPDATE
                        this.pnlHuella.TxtIndicador.setForeground(Color.ORANGE);
                        this.pnlHuella.TxtIndicador.setText("Hasta Pronto...");
                        this.pnlCredenciales.TxtIndicadorCred.setForeground(Color.ORANGE);
                        this.pnlCredenciales.TxtIndicadorCred.setText("Hasta Pronto...");
                    }
                    break;
                } else {
                    esEntrada = false;
                    this.pnlHuella.TxtIndicador.setForeground(Color.ORANGE);
                    this.pnlHuella.TxtIndicador.setText("Ya marcó I/O");
                    this.pnlCredenciales.TxtIndicadorCred.setForeground(Color.ORANGE);
                    this.pnlCredenciales.TxtIndicadorCred.setText("Ya marcó I/O");
                }
            }//termina while

            con.cerrar();

            if (esEntrada) {
                //REGISTRO DE ENTRADA
                DateFormat hora = new SimpleDateFormat("HH:mm");
                String horaActual = hora.format(date);//hora actual

                //es un registro de ENTRADA entonces se hace in INSERT
                con = new Conexion("datos/registro");
                ClaseInsertar ins = new ClaseInsertar(con.conectar(), "EntradasSalidas");
                ins.agregarValor("Fecha", Fecha, "S");
                ins.agregarValor("Entrada", horaActual, "S");
                ins.agregarValor("Salida", "--:--", "S");//aun no se marca la salida
                ins.agregarValor("Estado", 1, "I");
                ins.agregarValor("Empleados_Usuarios_DPI", personal.getDPI(), "L");
                if (ins.ejecutarSQL()) {
                    this.pnlHuella.TxtIndicador.setForeground(Color.GREEN);
                    this.pnlHuella.TxtIndicador.setText("¡Bienvenido!");
                    this.pnlCredenciales.TxtIndicadorCred.setForeground(Color.GREEN);
                    this.pnlCredenciales.TxtIndicadorCred.setText("¡Bienvenido!");

                }
                con.cerrar();
            }
        } catch (SQLException e) {
            System.err.println("Error al operar en EntradasSalidas: " + e.getMessage());
        } finally {
            con.cerrar();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {//eventos de la vista

        if (e.getSource() == formulario.btnAccesoAlter) {//boton acceso alternativo

            if (pulso) {

                //pasar al panel de credenciales
                this.cambiar.cambiarPNL(formulario.PnlCentral, pnlCredenciales);
                super.stop();//para el lector
                pulso = false;

            } else if (!pulso) {
                //pasar al panel de huella
                this.cambiar.cambiarPNL(formulario.PnlCentral, pnlHuella);
                super.start();//iniciar el lector
                pulso = true;
            }

        }//fin if

        if (e.getSource() == pnlCredenciales.BtbEntrar) {//boton de ingresar

            identificarCredenciales();
        }//fin if

        if (e.getSource() == formulario.BtnAccesoSistema) {//boton de acceso al sistema

            //iniciarVentanaPrincipal();//iniciar la ventana segun privilegio
        }

    }

}
