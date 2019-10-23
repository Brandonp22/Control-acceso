
/*
* Programa para control de acceso biometrico
* Proyecto Final de Programacion II UMG Zacapa 2019
 */
package Main;

import Controlador.ControlPrincipal;
import Controlador.ControlRegistro;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] arg) throws ClassNotFoundException, SQLException {
        
        
        //cambiar el look del programa
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Imposible modificar el tema visual", "Lookandfeel inválido.",
            JOptionPane.ERROR_MESSAGE);
        }


        //ControlRegistro registrarse = new ControlRegistro();

        //ControlInisioSesion inicio = new ControlInisioSesion();
        
       // Conexion conec = new Conexion("datos/registro");
        //ClaseConsultar con = new ClaseConsultar(conec.conectar(), "Usuarios");
        
       // con.consultar("COUNT(*) As total");
        
        //System.out.println("Usuarios registrados "+con.getResultadoConsulta().getInt("total"));
        
         
        
        ControlPrincipal control;
        control = new ControlPrincipal();
        
     
        
        
    }
}
