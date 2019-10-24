/*
 * Controlador para el registro inicial de un usuarios
 */
package Controlador;

import Modelo.ClaseInsertar;
import Modelo.Conexion;
import Modelo.Personal;


import Vista.FrameRegistro;
import Vista.PnlFoto;
import Vista.PnlRegCredenciales;
import Vista.PnlRegHuella;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.capture.event.*;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;

//implementa ActionListener, se usa para detectar y manejar eventos de acción, 
//osea, los que tienen lugar cuando se produce una acción sobre un elemento del programa
public class ControlRegistro extends ClaseLector implements ActionListener {

    //Para el encapsulamiento
    private Personal propietario = null;
    private ControlPrincipal ctrlMain = null;

    //variables de la vista
    private FrameRegistro formulario = null;
    private PnlRegCredenciales pnlCredenciales = null;
    private PnlRegHuella pnlHuella = null;
    private PnlFoto pnlFoto = null;
    private CambiarPanel cambiarPanel = null;

    private int paso = 1;

    //cosntructor
    public ControlRegistro(ControlPrincipal ventanaMain, Personal personal) {

        super();//ejecutar el constructor de la clase base

        this.formulario = new FrameRegistro();
        this.pnlCredenciales = new PnlRegCredenciales();
        this.pnlHuella = new PnlRegHuella();
        this.pnlFoto = new PnlFoto();

        this.ctrlMain = ventanaMain;
        this.propietario = personal;

        this.formulario.BtnSiguiente.addActionListener(this);//activar eventos al boton

        //insertar el panel de credenciales en el formulario 
        this.cambiarPanel = new CambiarPanel();
        this.cambiarPanel.cambiarPNL(formulario.PnlCentral, pnlCredenciales);

        this.formulario.setLocationRelativeTo(null);
        this.formulario.setVisible(true);

        iniciarEventosTextField();
    }

    private void iniciarEventosTextField() {

        //este evento es para validar el campo DPI em tiempo real
        this.pnlCredenciales.IntDPI.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {

                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada es una letra
                if (((caracter < '0')
                        || (caracter > '9'))
                        && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {

                    e.consume();  //si es letra se ignora el evento
                    //JOptionPane.showMessageDialog(null, "Campo DPI inválido.",
                    //      "Control Acceso", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
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
                        //mandar la img de la huella al label
                        DibujarHuella(CrearImagenHuella(e.getSample()), pnlHuella.LblHuella);

                        ProcesarCaptura(e.getSample());
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
                                .getResource("/Img/LectorConectado.png")));
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
                                .getResource("/Img/LectorDesactivado.png")));
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

    //cambiar de imagen en cada paso de captura de la huella
    public void marcaPasos(int paso, JLabel LblPaso) {
        LblPaso.setIcon(new ImageIcon(getClass().getResource("/Img/Paso" + paso + ".png")));
    }

    //mostrar la cantidad de huellas que faltan para completar plantilla
    public void EstadoHuellas() {
        System.out.println("Muestra de huellas necesarias para guardar plantilla: "
                + Reclutador.getFeaturesNeeded());

        int paso = 4 - Reclutador.getFeaturesNeeded();
        marcaPasos(paso, pnlHuella.LblPasos);//marcar el paso

    }

    public void GuardarHuellaDB() {

        Conexion con = new Conexion("datos/registro");
        ClaseInsertar insertar = new ClaseInsertar(con.conectar(), "Usuarios");

        insertar.agregarValor("DPI", propietario.getDPI(), "L");
        insertar.agregarValor("NombreUsuario", propietario.getNombreUsuario(), "S");
        insertar.agregarValor("Contrasenia", propietario.getContrasenia(), "S");
        insertar.agregarValor("Privilegio", propietario.getPrivilegio(), "S");
        insertar.agregarValor("Huella", propietario.getHuella(), "B");

        if (!insertar.ejecutarSQL()) {
            return;
        }

        insertar = new ClaseInsertar(con.conectar(), "Propietarios");

        insertar.agregarValor("Usuarios_DPI", propietario.getDPI(), "L");
        insertar.agregarValor("Nombre", propietario.getNombre(), "S");
        insertar.agregarValor("Apellidos", propietario.getApellidos(), "S");

        if (insertar.ejecutarSQL()) {

            JOptionPane.showMessageDialog(null, "Datos registrados correctamente "
                    + "\nPresione OK para continuar a la Ventana Principal",
                    "Control Acceso", JOptionPane.INFORMATION_MESSAGE);

            this.formulario.dispose();
            this.ctrlMain.getVentanaPrincipal().setVisible(true);
            this.ctrlMain.starVentanaPrincipal();
        }

        con.cerrar();//cerrar conexion
        con = null;
        insertar = null;
        System.gc();

    }

    public void ProcesarCaptura(DPFPSample muestra) {

        //para guardar las plantillas, que se obtienen en la funcion extraerCaracteristicas
        featureSetInscripcion = extraerCaracteristicas(muestra, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

        if (featureSetInscripcion != null) {//asegurarse de que la huella este bien leida

            try {

                System.out.println("Las características de la huella han sido creadas");
                Reclutador.addFeatures(featureSetInscripcion);//aniadir la plantilla de la huella al reclutador

            } catch (DPFPImageQualityException e) {
                System.err.println("Error al extreaer caracteristicas: " + e.getMessage());
            } finally {

                EstadoHuellas();
                switch (Reclutador.getTemplateStatus()) {

                    case TEMPLATE_STATUS_READY://huella lista!!!

                        marcaPasos(5, pnlHuella.LblPasos);//marca el ultimo paso

                        stop();//detiene el lector de huella

                        setTemplate(Reclutador.getTemplate());//se establece le huella 

                        propietario.setHuella(getPlantillaHuella().serialize());//llenamos el ultimo dato del usuario

                        GuardarHuellaDB();//y se guarda la huella

                        this.Reclutador.clear();//limpiar el reclutador

                        break;

                    case TEMPLATE_STATUS_FAILED://si falla el proceso 

                        Reclutador.clear();//se limpia el reclutador
                        stop();//deniene el lector 

                        setTemplate(null);//poner en null para evitar errores

                        System.out.println("La Plantilla de la huella no pudo ser creada. Por favor repita el proceso.");
                        JOptionPane.showMessageDialog(null, "La Plantilla de la huella no pudo ser creada.\nPor favor repita el proceso.",
                                "Control Acceso", JOptionPane.ERROR_MESSAGE);
                        start();

                        EstadoHuellas();

                        break;
                }//termina switch
            }//termina finally
        }//termina if
    }//fin metodo

    @Override
    public void actionPerformed(ActionEvent e) {//sobreescribir el metodo actionPerformed, control de los eventos

        if (e.getSource() == formulario.BtnSiguiente) {//si se pulso boton siguiente

            //validar el formulario
            if (pnlCredenciales.TxtNombre.getText().isEmpty()
                    || pnlCredenciales.TxtApellido.getText().isEmpty()
                    || pnlCredenciales.IntDPI.getText().isEmpty()
                    || pnlCredenciales.TxtUsuario.getText().isEmpty()
                    || pnlCredenciales.TxtPassword.getText().isEmpty()) {

                JOptionPane.showMessageDialog(this, "Por favor complete el formulario.",
                        "Control Acceso", JOptionPane.INFORMATION_MESSAGE);

            } else {

                this.paso++;

                if (paso == 2) {

                    //rellenar usuario
                    propietario.setNombre(pnlCredenciales.TxtNombre.getText().toUpperCase());
                    propietario.setApellidos(pnlCredenciales.TxtApellido.getText().toUpperCase());
                    propietario.setDPI(Long.parseLong(pnlCredenciales.IntDPI.getText()));
                    propietario.setNombreUsuario(pnlCredenciales.TxtUsuario.getText().toUpperCase());
                    propietario.setContrasenia(pnlCredenciales.TxtPassword.getText());
                    propietario.setPrivilegio("Propietario");

                    //cambiar a panel de foto
                    this.cambiarPanel.cambiarPNL(formulario.PnlCentral,
                            pnlFoto);

                }

                if (paso == 3) {
                    
                    this.iniciarEventosLector();
                    this.start();
                    this.EstadoHuellas();
                    //cambiar a panel de huella
                    this.cambiarPanel.cambiarPNL(formulario.PnlCentral,
                            pnlHuella);
                    this.formulario.BtnSiguiente.setEnabled(false);
                }

            }

        }

    }

}//termina la clase
