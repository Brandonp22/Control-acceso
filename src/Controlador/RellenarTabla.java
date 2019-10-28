/*
 * Esta clase se encarga de rellenar un jtable dado los datos consultados
 * en una tabla de la base de datos
 */
package Controlador;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jaasiel Guerra
 */
public class RellenarTabla {

    //atributos privados
    private ResultSet datos;
    private DefaultTableModel modelo;
    private JTable tabla;
    private Object fila[];
    private ResultSetMetaData metaDatos;

    public RellenarTabla(ResultSet datos, JTable tabla) {

        this.datos = datos;
        this.tabla = tabla;
        this.metaDatos = null;
    }

    //para rellenar la tabla recibe los datos consultados
    public void llenar() {
        this.modelo = (DefaultTableModel)this.tabla.getModel();

        /*
         * Todo lo que se hace en DefaultModel se refresca automaticamente en el Jtable
         */
        try {

            this.metaDatos = datos.getMetaData();//obtener informacion de la tabla en la BD
           
            while (datos.next()) {

                //se crea un arreglo que sera una de las filas en la tabla
                this.fila = new Object[metaDatos.getColumnCount()];

                //se rellena cada pos del array con una de las columnas de la tabla en la BD
                for (int i = 0; i < metaDatos.getColumnCount(); i++) {

                    fila[i] = datos.getObject(i + 1);//el primer indice en datos es 1, no el 0, por eso se le suma

                }
                
                //se aniade al modelo la fila completa
                modelo.addRow(fila);

            }
        } catch (SQLException ex) {
            System.out.println("Error al llenar la tabla: " + ex.getMessage());
        }
    }

}
