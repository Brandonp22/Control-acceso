/*
 * Controlador para el registro inicial de un usuarios
 */
package Controlador;
import Vista.PnlRegistroCredenciales;
import Vista.PnlRegistroHuella;
import Vista.Registro;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//implementa ActionListener, se usa para detectar y manejar eventos de acción, 
//osea, los que tienen lugar cuando se produce una acción sobre un elemento del programa
public class ControlRegistro implements ActionListener {
    
    private PnlRegistroCredenciales pnlCredenciales; 
    private PnlRegistroHuella pnlHuella;
    private Registro formRegistro;
    private DPFPEnrollment enrolador;//enrolador para unir las captura de la huella
    private DPFPVerification verificador;//para verificar la huella
    private DPFPTemplate plantillaHuella; //sirve para crear una plantilla de la huella
    private DPFPVerificationResult resultado;//verifica que la huella este OK
    private DPFPFeatureSet caracteristicas;//Obtiene las caracteristicas de la huella
    private boolean BUSCAR;//sirve para ver si se esta buscando o se esta actualizando la huella
    
    
    

    //Este constructor recibe como parametro los formularios de registro
    public ControlRegistro(PnlRegistroCredenciales pnlCredenciales, PnlRegistroHuella pnlHuella, Registro formRegistro) {
        this.pnlCredenciales = pnlCredenciales;
        this.pnlHuella = pnlHuella;
        this.formRegistro = formRegistro;
        this.enrolador = null;
        this.verificador = DPFPGlobal.getVerificationFactory().createVerification();//crea globalmete una verificacion
        this.plantillaHuella = null;
        this.resultado = null;
        this.caracteristicas = null;
        this.BUSCAR = false;
        
        try 
        {
            
            enrolador = DPFPGlobal.getEnrollmentFactory().createEnrollment();            
        } catch (java.lang.UnsatisfiedLinkError | java.lang.NoClassDefFoundError e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public void procesarHuella(DPFPSample sample) 
    {
        
        
        
        
    }
   

    
    @Override 
    public void actionPerformed(ActionEvent e) {//sobreescribir el metodo actionPerformed, control de los eventos
        
    }
    
    
    
    
    
}
