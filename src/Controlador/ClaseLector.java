/*
 * Esta clase contiene los atributos necesarios para la captura
 * y verificacion de la huella dactilar
 */
package Controlador;

/**
 *
 * @author Jaasiel Guerra
 */
import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import java.awt.Image;
import javax.swing.ImageIcon;

import javax.swing.JFrame;

public class ClaseLector extends JFrame {

    //Nos sirve para identificar al dispositivo
    protected DPFPCapture Lector;

    //Nos sirve para leer a modo de enrrolar, y crear una plantilla nueva, a base de 4 huellas.
    protected DPFPEnrollment Reclutador;

    //La plantilla, nueva o rescatada
    protected DPFPTemplate PlantillaHuella;

    //A modo de CONSTANTE para crear plantillas
    protected String TEMPLATE_PROPERTY;

    //Para leer la huella, y definirla como un enrrolamiento
    protected DPFPFeatureSet featureSetInscripcion;

    //Para leer la huella, y definirla como una verificación
    protected DPFPFeatureSet featureSetVerificacion;

    //Nos sirve para leer a modo de verificar o comparar, a base de una plantilla creada anteriormente
    protected DPFPVerification Verificador;

    //Nos permite verificar las huellas, nos da el resultado de la operacion de verificacion
    protected DPFPVerificationResult resultado;

    public ClaseLector() {//constructor
        this.Lector = DPFPGlobal.getCaptureFactory().createCapture();
        this.Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
        //this.PlantillaHuella = null;
        this.TEMPLATE_PROPERTY = "PlantillaHuella";
        //this.featureSetInscripcion = null;
        //this.featureSetVerificacion = null;
        this.Verificador =  DPFPGlobal.getVerificationFactory().createVerification();
        this.resultado = null;
    }

    public void start() {//metodo para iniciar la captura del lector
        Lector.startCapture();
        System.out.println("Utilizando lector de huella dactilar");

    }

    public void stop() {//detenemos la captura del lector de huella
        Lector.stopCapture();
        System.out.println("Lector detenido");

    }

    //se encarga de la extraccion de las caracteristicas de la huella capturada
    public DPFPFeatureSet extraerCaracteristicas(DPFPSample muestra, DPFPDataPurpose motivo) {

        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();

        try {
            return extractor.createFeatureSet(muestra, motivo);//retorna la extraccion
        } catch (DPFPImageQualityException e) {
            System.out.println(e.getMessage());
            return null;//si no se extrajo correctamente retorna null
        }
    }

    public Image CrearImagenHuella(DPFPSample muestra) {//crea una imagen dada la plantilla de la huella
        return DPFPGlobal.getSampleConversionFactory().createImage(muestra);//crea una img a partir de una muestra
    }

    public void DibujarImagen(Image image, javax.swing.JLabel lblHuella) {//funcion para setear imagen en label

        lblHuella.setIcon(new ImageIcon(
                image.getScaledInstance(lblHuella.getWidth(), lblHuella.getHeight(), Image.SCALE_DEFAULT)
        )
        );
    }
    
    public void DibujarImagen(Image image, javax.swing.JButton Boton) {//funcion para setear imagen en label

        Boton.setIcon(new ImageIcon(
                image.getScaledInstance(Boton.getWidth(),Boton.getHeight() , Image.SCALE_DEFAULT)
        )
        );
    }

    //esblece un template dada la plantilla de la huella
    public void setTemplate(DPFPTemplate PlantillaHuella) {
        DPFPTemplate antigua = this.PlantillaHuella;
        this.PlantillaHuella = PlantillaHuella;
        firePropertyChange(TEMPLATE_PROPERTY, antigua, PlantillaHuella);
    }

    //retorna la plantilla de la huella
    public DPFPTemplate getPlantillaHuella() {
        return PlantillaHuella;
    }
    
     /*
    este metodo permite la comparacion de un conjunto de caracteristicas de la huella
    digital (extraido del lector de huella digital) con una platilla de huella digital
    almacenado posteriormente en la base de datos
    */
    public boolean verificarHuella(DPFPFeatureSet conjuntoCaracteristicas, byte[] huella) {
        boolean verificacion = false;
        this.resultado = this.Verificador.verify(conjuntoCaracteristicas,
                DPFPGlobal.getTemplateFactory().createTemplate(huella));

        if (this.resultado.isVerified()) {
            verificacion = true;
        } else {
            verificacion = false;
        }

        return verificacion;

    }
}
