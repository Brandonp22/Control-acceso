/*
 * Controlador para el registro inicial de un usuarios
 */
package Controlador;
import Modelo.ClaseInsertar;
import Modelo.Conexion;
import Modelo.Usuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.*;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
 
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;


//implementa ActionListener, se usa para detectar y manejar eventos de acción, 
//osea, los que tienen lugar cuando se produce una acción sobre un elemento del programa
public class ControlRegistro extends javax.swing.JFrame implements ActionListener {
    
   private Vista.FrameRegistro formulario;//para recibir el formulario de vista
   
   //Nos sirve para identificar al dispositivo
    private DPFPCapture Lector;
    
    //Nos sirve para leer a modo de enrrolar, y crear una plantilla nueva, a base de 4 huellas.
    private DPFPEnrollment Reclutador;
    
    //La plantilla, nueva o rescatada
    private DPFPTemplate PlantillaHuella;
    
    //A modo de CONSTANTE para crear plantillas
    public String TEMPLATE_PROPERTY;
    
    //Para leer la huella, y definirla como un enrrolamiento
    public DPFPFeatureSet featureSetInscripcion;
    
    //Para leer la huella, y definirla como una verificación
    public DPFPFeatureSet featureSetVerificacion;
    
    //Nos sirve para leer a modo de verificar o comparar, a base de una plantilla creada anteriormente
    private DPFPVerification Verificador;
    
    //Nos permite verificar las huellas, nos da el resultado de la operacion de verificacion
    private DPFPVerificationResult resultado;
    
    
    //Para guardar la huella convertida en arreglo de bytes
    private byte[] huellaByte;

   
   //Este constructor recibe como parametro el formulario de registro
    public ControlRegistro(Vista.FrameRegistro formulario) {
        
        this.formulario = formulario;
        this.formulario.BtnSiguiente.addActionListener(this);//activar eventos al boton
        this.Lector = DPFPGlobal.getCaptureFactory().createCapture();
        this.Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
        this.Verificador = DPFPGlobal.getVerificationFactory().createVerification();
        this.TEMPLATE_PROPERTY = "PlantillaHuella";
        this.huellaByte = null;
        this.resultado = null;
        //this.IniciarEventos();
        //this.start();
        //this.EstadoHuellas();
        
    }
    
    protected void IniciarEventos(){
        
        /*
        EL lector tiene sus propios eventos, los cuales se activan desde esta
        funcion, aca se activan solamente cuatro
        */
        
        //----------------Metodo para capturar la huella--------------------------------------------
        Lector.addDataListener(new DPFPDataAdapter(){//agregar el detector de eventos de datos
            
            @Override 
            public void dataAcquired(final DPFPDataEvent e){
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        //EnviarTexto("La huella ha sido capturada");
                        System.out.println("La huella ha sido capturada");
                        ProcesarCaptura(e.getSample());
                    }
                });
            }
            
        });
        
        //------------------Metodo para saber el estado del lector---------------------------------------
        Lector.addReaderStatusListener(new DPFPReaderStatusAdapter(){//agrega el evento de estado del lector
            @Override 
            public void readerConnected(final DPFPReaderStatusEvent e){//si el lector esta activado
                SwingUtilities.invokeLater(new Runnable() {
                    @Override 
                    public void run() {
                        
                        System.out.println("EL sensor de huella se encuentra activado");
                        //EnviarTexto("El sensor de huella dactilar se encuentra Activado");
                    }
                });
            }

            @Override 
            public void readerDisconnected(final DPFPReaderStatusEvent e){//si el lector esta desactivado
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("El sensor se encuentra desactivado");
                        //EnviarTexto("El sensor de huella dactilar se encuentra Desactivado");
                    }
                });
            }
        });

        //-----------------Metodo para saber los errores del lector-----------------------------------------
        Lector.addErrorListener(new DPFPErrorAdapter(){//agrega el detector de eventos de errores
            public void errorReader(final DPFPErrorEvent e){
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Error: " + e.getError());
                        //EnviarTexto("Error: " + e.getError());
                    }
                });
            }
        });
    }
    
    public void start(){//metodo para iniciar la captura del lector
        Lector.startCapture();
        System.out.println("Utilizando lector de huella dactilar");
        //EnviarTexto("Utilizando lector de huella dactilar");
    }
    
    public void stop(){//detenemos la captura del lector de huella
        Lector.stopCapture();
        System.out.println("Lector detenido");
        //EnviarTexto("Lector detenido");
    }
    
    //mostrar un mensaje con la cantidad de huellas que faltan para completar plantilla
    public void EstadoHuellas(){
        System.out.println("Muestra de huellas necesarias para guardar plantilla: "
                           + Reclutador.getFeaturesNeeded());
        //EnviarTexto("Muestra de huellas necesarias para guardar plantilla: " + Reclutador.getFeaturesNeeded());
    }
    
    //se encarga crear la extraccion de las caracteristicas de la huella capturada
    public DPFPFeatureSet extraerCaracteristicas(DPFPSample muestra, DPFPDataPurpose motivo){
        
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
        
        try{
            return extractor.createFeatureSet(muestra, motivo);//retorna la extraccion
        }catch (DPFPImageQualityException e) {
            System.out.println(e.getMessage());
            return null;//si no se extrajo correctamente retorna null
        }
    }
    
    public Image CrearImagenHuella(DPFPSample muestra){//crea una imagen dada la plantilla de la huella
        return DPFPGlobal.getSampleConversionFactory().createImage(muestra);//crea una img a partir de una muestra
    }
    
    public void DibujarHuella(Image image, javax.swing.JLabel lblHuella ){//funcion para setear imagen en label
        
        lblHuella.setIcon( new ImageIcon(
                                
                image.getScaledInstance(lblHuella.getWidth(),lblHuella.getHeight(),Image.SCALE_DEFAULT)
            )
        );
    }
    
    public void EstablecerTemplate(DPFPTemplate PlantillaHuella) {
        DPFPTemplate antigua = this.PlantillaHuella;
        this.PlantillaHuella = PlantillaHuella;
        firePropertyChange(TEMPLATE_PROPERTY, antigua, PlantillaHuella);
    }

    public DPFPTemplate getPlantillaHuella() {
        return PlantillaHuella;
    }
    
    
    public boolean GuardarHuellaDB()
    {
        boolean resultado = false;
        Conexion con = new Conexion("registro");
        ClaseInsertar insertar = new ClaseInsertar(con.conectar(),"Usuarios");
        Usuario user = new Usuario();
        
        huellaByte = this.getPlantillaHuella().serialize();//obtener el array de bytes de la huella
        
        user.setNombre(formulario.registroCred.TxtNombre.getText());
        user.setApellido(formulario.registroCred.TxtApellido.getText());
        user.setDPI(Long.parseLong(formulario.registroCred.IntDPI.getText()));
        user.setUserName(formulario.registroCred.TxtUsuario.getText());
        user.setPassword(formulario.registroCred.TxtContra.getText());
        user.setHuella(huellaByte);
        user.setPrivilegio("Admin");
        
        insertar.agregar("nombre", user.getNombre(), "S");
        insertar.agregar("apellidos", user.getApellido(), "S");
        insertar.agregar("dpi", user.getDPI(), "L");
        insertar.agregar("username", user.getUserName(), "S");
        insertar.agregar("password", user.getPassword(), "S");
        insertar.agregar("huella", user.getHuella(),"B");
        insertar.agregar("privilegio", user.getPrivilegio(), "S");
        
        if(insertar.ejecutar())
            resultado = true;
       
        return resultado;
    }
    
    
    /*
    este metodo permite la comparacion de un conjunto de caracteristicas de la huella
    digital (extraido del lector de huella digital) con una platilla de huella digital
    almacenado posteriormente en la base de datos
    */
    public boolean verificarHuella(DPFPFeatureSet conjuntoCaracteristicas ,byte[] huella)
    {
        boolean verificacion = false;
        this.resultado = this.Verificador.verify(conjuntoCaracteristicas, 
                DPFPGlobal.getTemplateFactory().createTemplate(huella));
        
        if(this.resultado.isVerified()) verificacion = true;
                
        else verificacion = false;
 
        return verificacion;
        
    }
    
    public boolean compararrHuella()
    {
        boolean resultado = false;
        Conexion con = new Conexion("registro");
        try{
            
            PreparedStatement pre = con.conectar().prepareStatement(
                    "SELECT * FROM Usuarios"
            );
            
            ResultSet res =  pre.executeQuery();//ejecutar SQL
            
            
            while(res.next())
            {
                
                if(this.verificarHuella(featureSetVerificacion, res.getBytes("huella")))
                    System.out.println("Huella encontrada, pertenece a : " + res.getString("nombre"));
            }
            
            resultado = true;
            
        }catch(SQLException ex)
        {
            System.err.println("Error al consultar: " + ex.getMessage());
            resultado = false;
        }
       return resultado;
        
    }
    
    
    public void ProcesarCaptura(DPFPSample muestra){
        
        
        
        //para guardar las plantillas, que se obtienen en la funcion extraerCaracteristicas
        featureSetInscripcion = extraerCaracteristicas(muestra, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
        featureSetVerificacion = extraerCaracteristicas(muestra, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
        
       //this.compararrHuella();
        
        if(featureSetInscripcion != null){//asegurarse de que la huella este bien leida
            
            try {
                
                System.out.println("Las características de la huella han sido creadas");
                Reclutador.addFeatures(featureSetInscripcion);
                DibujarHuella(CrearImagenHuella(muestra), formulario.registroHue.LblHuella);//mandar la img de la huella al label
                //btnVerificar.setEnabled(true);
                //btnIdentificar.setEnabled(true);
                
            }catch (DPFPImageQualityException e) {
                System.err.println("Error: "+e.getMessage());
            }finally {
                
                EstadoHuellas();
                switch (Reclutador.getTemplateStatus()){
                    
                    case TEMPLATE_STATUS_READY:
                        stop();
                        EstablecerTemplate(Reclutador.getTemplate());
                        if(GuardarHuellaDB()) System.out.println("La huella se ha guardado");
                        
                        this.Reclutador.clear();//limpiar el reclutador
                        //formulario.setVisible(false);
                        
                        //if(JOptionPane.showConfirmDialog(this,"Registro Exitoso.") == JOptionPane.OK_OPTION) System.exit(0);
                        //EnviarTexto("La plantilla de huella ha sido creada. Puede Verificar o Identificarla");
                        //btnIdentificar.setEnabled(false);
                        //btnVerificar.setEnabled(false);
                        //btnGuardar.setEnabled(true);
                        //btnGuardar.grabFocus();
                        
                    break;
                    
                    case TEMPLATE_STATUS_FAILED:
                        
                        Reclutador.clear();
                        stop();
                        EstadoHuellas();
                        EstablecerTemplate(null);
                        JOptionPane.showMessageDialog(null,"La plantilla de la huella no pudo ser creada. Repita el proceso",
                                                      "Inscripción de Huellas Dactilares",
                        JOptionPane.ERROR_MESSAGE);
                        start();
                        
                    break;
                }//termina switch
            }//termina finally
        }//termina if
    }//fin metodo
   
    
    @Override 
    public void actionPerformed(ActionEvent e) {//sobreescribir el metodo actionPerformed, control de los eventos
        
        if(e.getSource() == formulario.BtnSiguiente)
        {
            this.IniciarEventos();
            this.start();
            this.EstadoHuellas();
            this.formulario.BtnSiguiente.setEnabled(false);
        }
        
    }
    
    
    
    
    
}//termina la clase
