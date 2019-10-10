/*
 * Esta clase es para eliminar registros de la DB
 */
package Modelo;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 *
 * @author Brandon Pineda
 */
public class ClaseEliminar {

    private Connection objConector = null;
    private String nombreTab = null;

    private StringBuilder SQL;
    private PreparedStatement preElim;

    public ClaseEliminar(Connection objConector_, String nombreTab_) {//constructor

        this.objConector = objConector_;
        this.nombreTab = nombreTab_;
        
        initVariables();
    }

    public void borrar(String campo, String valBuscar) {

        System.out.println("Conectado desde: " + this.getClass().getName());

        this.SQL.append(campo).append(" = ").append("'").append(valBuscar).append("'");

        try {
            if (objConector != null) {
                this.preElim = objConector.prepareStatement(SQL.toString());//agregar SQL
                this.preElim.executeUpdate();
                System.out.println("Registro borrado correctamente de la tabla: "+this.nombreTab);
                initVariables();//volver a inicializar

            } else {
                System.out.println("Error no es posible realizar la operacion");
                //JOptionPane.showMessageDialog(null, "Error no es posible realizar la operacion");

            }

        } catch (HeadlessException | SQLException ex) {
            System.out.println("Error al realizar la actualización por" + ex.getMessage());
            //JOptionPane.showMessageDialog(null, "Error al realizar la actualización por" + ex.getMessage());
        }
    }

    private void initVariables() {
        this.SQL = new StringBuilder();
        this.preElim = null;
        this.SQL.append("DELETE FROM ").append(nombreTab).append(" WHERE ");//inicializar sentencia SQL
    }
}
