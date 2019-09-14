/*
 * Clase encargada de la conexion con la DB
 */
package Modelo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion 
{
    
    /**
     * Variable de tipo conexion para la base de datos
     */
   private Connection  con;
   private String nomDb;
    
    public Conexion(String nomDB)//recibe nombre de la DB
    {
        this.nomDb = nomDB;
        
    }
    public Connection conectar() 
    {
        // con la funcion property obtenemos la ubcación del archivo ejecutable.
        String url= System.getProperty("user.dir");   
        try
        {              
            
            //Cargamos la conexion pasando la ruta de la base de datos
            con = DriverManager.getConnection("jdbc:sqlite:"+url+"/dist/datos/"+this.nomDb+".db");      

            
            //Validamos si la conexión es nula
            if (con!=null) 
            {
                // solo por temas de control mostramos el estado de la conexión
                System.out.println("Conectado");
                return con;
            }
        }
    
        // control de errores de tipo slq
        catch (SQLException ex) 
        {
            // error de conexión mostrando el mensaje de error
            System.err.println("No se ha podido conectar a la base de datos\n"+ex.getMessage());
            return null;//retornamos null
        }
        return null;
    }
    
    
    //Metodo publico para cerrar la base de datos
    public void cerrar(){
         
        //lo cargamos en un try por temas de seguridad del cierre de la conexión   
        try {
            con.close();
        } catch (SQLException ex) 
        {
            // trazas de aplicaciones y con la clase de excepción y el nivel de excepción.
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
 }


