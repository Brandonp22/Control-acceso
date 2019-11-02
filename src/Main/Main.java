
/*
* Programa para control de acceso biometrico
* Proyecto Final de Programacion II UMG Zacapa 2019
 */
package Main;

import Controlador.ControlPrincipal;
import Controlador.ControlRegistro;
import Modelo.ClaseInsertar;
import Modelo.Conexion;
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
       
       //es un registro de ENTRADA entonces se hace in INSERT
           //     Conexion con = new Conexion("datos/registro");
             //   ClaseInsertar ins = new ClaseInsertar(con.conectar(), "EntradasSalidas");
               // ins.agregarValor("Fecha", "12", "S");
              //  ins.agregarValor("Entrada", "12", "S");
              //  ins.agregarValor("Salida", "--:--", "S");//aun no se marca la salida
              //  ins.agregarValor("Empleados_Usuarios_DPI", 888, "L");
              //  ins.ejecutarSQL();
        
     
        
        
    }
}
