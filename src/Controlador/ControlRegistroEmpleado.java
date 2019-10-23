/*
 * Controlador para el registro de los empleados
 */
package Controlador;

import Modelo.ClaseConsultar;
import Modelo.Conexion;
import Modelo.Personal;
import Vista.FrameRegistro;
import Vista.PnlRegHuella;
import Vista.PnlRegistroPersona;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Jaasiel Guerra
 */
public class ControlRegistroEmpleado extends ClaseLector implements ActionListener {

    //variables de la vista
    private FrameRegistro formularioHuella = null;
    private PnlRegHuella pnlHuella = null;
    private PnlRegistroPersona pnlRegistroPer = null;
    private CambiarPanel cambiarPanel = null;
    private Personal personal = null;

    /////constructor//////////
    public ControlRegistroEmpleado(Personal per) {

        super();//ejecutar constructor de clase base

        this.formularioHuella = new FrameRegistro();
        this.pnlHuella = new PnlRegHuella();
        this.pnlRegistroPer = new PnlRegistroPersona();
        this.personal = new Personal();
        this.cambiarPanel = new CambiarPanel();

        /*
        * aca se valida si el privilegio del usuario es propietario
        * de ser asi, se hacen visibles los componenetes
         */
        this.pnlRegistroPer.jLabel1.setVisible(per.getPrivilegio().equals("Propietario"));
        this.pnlRegistroPer.CheckAdmin.setVisible(per.getPrivilegio().equals("Propietario"));

        this.pnlRegistroPer.BtnGuardar.addActionListener(this);
        this.pnlRegistroPer.jButton2.addActionListener(this);
        this.pnlRegistroPer.jButton2.setText(null);

        insertAreasTrabajo();
    }
    
    //esto inserta las areas de trabajo existentees en el jcombobox
    private void insertAreasTrabajo() {
        Conexion conector = new Conexion("datos/registro");
        ClaseConsultar consulta = new ClaseConsultar(conector.conectar(), "AreasTrabajo");

        consulta.consultar("id,Nombre");

        try {
            this.pnlRegistroPer.jComboBox1.removeAllItems();//limpiar
            this.pnlRegistroPer.jComboBox1.addItem("<Seleecione Area>");
            while (consulta.getResultadoConsulta().next()) {
                this.pnlRegistroPer.jComboBox1.addItem(consulta.getResultadoConsulta()
                        .getString("Nombre"));
            }
        } catch (SQLException ex) {
            System.err.print("Error al insertar areas: " + ex.getMessage());
        } finally {
            conector.cerrar();
            conector = null;
            consulta = null;
            System.gc();
        }
    }

    public PnlRegistroPersona getPnlRegistroPer() {
        return pnlRegistroPer;
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
                        super.stop();//detiene el lector de huella
                        setTemplate(Reclutador.getTemplate());//se establece le huella 
                        this.formularioHuella.setVisible(false);//oculta la ventanita

                        //pone la imagen de la huella en el boton
                        super.DibujarHuella(CrearImagenHuella(muestra), this.pnlRegistroPer.jButton2);
                        //this.pnlRegistroPer.jButton2.setEnabled(false);//desactiva el boton
                        this.pnlRegistroPer.jButton2.removeActionListener(this);//no escucha mas acciones el boton
                        this.personal.setHuella(getPlantillaHuella().serialize());//llena el dato huella
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

    //inicia el formulario para la captura de la huella
    private void iniciarCapturaHuella() {
        this.iniciarEventosLector();
        this.start();
        this.EstadoHuellas();

        //insertar el panel huella
        this.cambiarPanel.cambiarPNL(formularioHuella.PnlCentral, pnlHuella);
        this.formularioHuella.BtnSiguiente.setVisible(false);
        this.formularioHuella.setLocationRelativeTo(null);
        this.formularioHuella.setAlwaysOnTop(true);//para que sea modal
        this.formularioHuella.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //boton de capturar huella
        if (e.getSource().equals(this.pnlRegistroPer.jButton2)) {

            iniciarCapturaHuella();

        }

    }

}
