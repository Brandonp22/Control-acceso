/*
 * Clase encargada de la objConectorexion con la DB
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jaasiel Guerra
 */
public class Conexion {

    /**
     * Variable de tipo conexion para la base de datos
     */
    private Connection objConector = null;
    private String nomDb;

    public Conexion(String nomDB)//recibe nombre de la DB
    {
        this.nomDb = nomDB;

    }

    public Connection conectar() {
        // con la funcion property obtenemos la ubcación del archivo ejecutable.
        String url = System.getProperty("user.dir");
        try {

            //Cargamos la conexion pasando la ruta de la base de datos
            objConector = DriverManager.getConnection("jdbc:sqlite:" + url + "/" + this.nomDb + ".db");

            //Validamos si la objConectorexión es nula
            if (objConector != null) {
                // solo por temas de control mostramos el estado de la conexión
                System.out.println("Base de datos en: " + url + "/" + this.nomDb + ".db");
                //return objConector;
            }
        } // control de errores de tipo slq
        catch (SQLException ex) {
            // error de conexión mostrando el mensaje de error
            System.err.println("No se ha podido conectar a la base de datos " + ex.getMessage());
            //return null;//retornamos null
            objConector = null;//entonces es null
        }
        return objConector;
    }

    //Metodo publico para cerrar la base de datos
    public void cerrar() {

        //lo cargamos en un try por temas de seguridad del cierre de la conexión   
        try {
            objConector.close();
        } catch (SQLException ex) {
            // trazas de aplicaciones y objConector la clase de excepción y el nivel de excepción.
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
