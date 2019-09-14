
/*
* Programa para control de acceso biometrico
* Proyecto Final de Programacion II UMG Zacapa 2019
*/

package Main;

import Controlador.ControlRegistro;
import Vista.FormInicioSesion;
import Vista.FormRegistro;

public class Main {
    
    public static void main(String[] arg) throws ClassNotFoundException
    {
        //InicioSesion inicio = new InicioSesion();
        //inicio.setVisible(true);
        FormRegistro Vregistro = new FormRegistro();
        Vregistro.setVisible(true);
        ControlRegistro registro = new ControlRegistro(Vregistro);
        
    }  
}
