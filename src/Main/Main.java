
/*
* Programa para control de acceso biometrico
* Proyecto Final de Programacion II UMG Zacapa 2019
 */
package Main;

import Controlador.ControlInisioSesion;
import Controlador.ControlRegistro;
import Vista.FrameInicioSesion;
import Vista.FrameRegistro;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] arg) throws ClassNotFoundException {
        
        
        //cambiar el look del programa
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Imposible modificar el tema visual", "Lookandfeel inválido.",
            JOptionPane.ERROR_MESSAGE);
        }


        //ControlRegistro registrarse = new ControlRegistro();

        ControlInisioSesion inicio = new ControlInisioSesion();

    }
}
