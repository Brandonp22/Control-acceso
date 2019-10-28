/*
 * Sirve para contar los registros contenidos en una tabla de la base de datos
 */
package Controlador;

import Modelo.ClaseConsultar;
import Modelo.Conexion;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jaasiel Guerra
 */
public class ClaseContarRegistros {

    private Conexion conec = null;
    private ClaseConsultar con = null;

    public ClaseContarRegistros() {
        this.con = null;
        this.conec = null;
    }

    public int contarReg(String nombreDB, String nomTab) {

        int contador = 0;
        this.conec = new Conexion(nombreDB);
        this.con = new ClaseConsultar(conec.conectar(), nomTab);

        //fufncion COUTN devuelve el numero de veces que se repite X registro
        con.consultar("COUNT(*) As total");

        try {
            contador = con.getResultadoConsulta().getInt("total");
        } catch (SQLException ex) {
            Logger.getLogger(ClaseContarRegistros.class.getName()).log(Level.SEVERE, null, ex);
            contador = -1;
        } finally {
            this.conec.cerrar();
            this.con = null;
            this.conec = null;
            System.gc();
        }
        return contador;
    }
    
    public int contarReg(String nombreDB, String nomTab, String campo,String valContar) {

        int contador = 0;
        this.conec = new Conexion(nombreDB);
        this.con = new ClaseConsultar(conec.conectar(), nomTab);

        //fufncion COUTN devuelve el numero de veces que se repite X registro
        con.consultar("COUNT(*) As total",campo,"=","'"+valContar+"'");

        try {
            contador = con.getResultadoConsulta().getInt("total");
        } catch (SQLException ex) {
            Logger.getLogger(ClaseContarRegistros.class.getName()).log(Level.SEVERE, null, ex);
            contador = -1;
        } finally {
            this.conec.cerrar();
            this.con = null;
            this.conec = null;
            System.gc();
        }
        return contador;
    }

}
