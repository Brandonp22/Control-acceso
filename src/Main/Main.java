
/*
* Programa para control de acceso biometrico
* Proyecto Final de Programacion II UMG Zacapa 2019
*/

package Main;

import Controlador.ControlRegistro;
import Vista.FrameInicioSesion;
import Vista.FrameRegistro;

public class Main {
    
    public static void main(String[] arg) throws ClassNotFoundException
    {
        //InicioSesion inicio = new InicioSesion();
        //inicio.setVisible(true);
        
        
        FrameRegistro Vregistro = new FrameRegistro();
        Vregistro.setVisible(true);
        ControlRegistro registro = new ControlRegistro(Vregistro);
        
        
        /*FrameInicioSesion Vregistro = new FrameInicioSesion();
        Vregistro.setVisible(true);*/
    }  
}
