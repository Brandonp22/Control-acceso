
/*
* Prueba No. 1 para control acceso biometrico UMG
*/

package Main;

import Modelo.Conexion;
import Vistas.PruebaVisual;

public class Main {
    
    public static void main(String[] arg) throws ClassNotFoundException
    {
        
        
      Conexion con  = new Conexion();
      con.conexion_db();
      con.close();
        
      PruebaVisual pv=new PruebaVisual();
      pv.setVisible(true);
    }  
}
