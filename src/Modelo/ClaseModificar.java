/*
 * Clase encargada de actulizar los registros de la base de datos
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jaasiel Guerra
 */
public class ClaseModificar {

    //Atributos de la clase
    private Connection objConector = null;
    private String nombreTab;
    private PreparedStatement preMod;
    private ArrayList valores;
    private StringBuilder SQL;
    private ArrayList<String> tipoCampo;
    private byte[] dato;

    //constructor
    public ClaseModificar(Connection objConector_, String nombreTab_) {

        System.out.println("Conectado desde: " + this.getClass().getName());
        this.objConector = objConector_;
        this.nombreTab = nombreTab_;

        initVariables();

    }

    private void initVariables() {//limpiar todas las variables

        this.preMod = null;
        this.valores = new ArrayList();
        this.SQL = new StringBuilder();
        this.tipoCampo = new ArrayList<String>();
        this.dato = null;
        this.SQL.append("UPDATE ").append(nombreTab).append(" SET ");//inicializar sentencia SQL

    }

    //metodo para actualizar datos primitivos
    public void agregarValor(String campo, Object valor, String tipoCampo_) {

        //==========CONSTRUYENDO SENTENCIA SQL=====================================
        if (valores.size() < 1)//si es el primer campo
        {
            this.SQL.append(campo).append(" = ?");//se agrega el campo sin coma

        } else if (valores.size() >= 1)//comprobar que ya existe un campo
        {
            this.SQL.append(",").append(campo).append(" = ?");//y se agrega el campo precedido de una coma
        }
        //=========================================================================

        this.valores.add(valor);//agregar el valor

        this.tipoCampo.add(tipoCampo_);//tipo de campo

    }

    //metodo para actualizar datos de tipo arreglos de bytes
    public void agregarValor(String campo, byte[] valor, String tipoCampo_) {

        //==========CONSTRUYENDO SENTENCIA SQL=====================================
        if (valores.size() < 1)//primer campo
        {
            this.SQL.append(campo).append(" = ?");//se agrega el campo sin coma

        } else if (valores.size() >= 1)//comprobar que ya existe un campo
        {
            this.SQL.append(",").append(campo).append(" = ?");//y se agrega el campo precedido de una coma
        }
        //=========================================================================

        this.valores.add(null);//le metemos un valor quemado al arrayList en lugar del byte de la huella

        this.dato = valor;//agregar el dato tipo arreglo de bytes

        this.tipoCampo.add(tipoCampo_);//agregar el tipo de campo   

    }

    //este metodo setea los valores para el WHERE
    public void setDonde(String campo, String valBuscar) {

        this.SQL.append(" WHERE ").append(campo).append(" = ")
                .append("'").append(valBuscar).append("'");//completando Sentencia SQL

    }

    public boolean ejecutarSQL() {

        boolean resultado = false;

       
        System.out.println("SQL Construido: " + SQL.toString());//imprimir SQL

        try {

            this.preMod = objConector.prepareStatement(SQL.toString());//agregar SQL

            //clasificador de variables
            for (int i = 0; i < valores.size(); i++) {//recorrer la lista de valores

                
                if ("I".equals(this.tipoCampo.get(i)))//tipo Int
                {
                    this.preMod.setInt(i + 1, Integer.parseInt(this.valores.get(i).toString()));
                }

                if ("L".equals(this.tipoCampo.get(i)))//tipo Long
                {
                    this.preMod.setLong(i + 1, Long.parseLong(this.valores.get(i).toString()));
                }

                if ("F".equals(this.tipoCampo.get(i)))//tipo Float
                {
                    this.preMod.setFloat(i + 1, Float.parseFloat(this.valores.get(i).toString()));
                }

                if ("D".equals(this.tipoCampo.get(i)))//tipo Double
                {
                    this.preMod.setDouble(i + 1, Double.parseDouble(this.valores.get(i).toString()));
                }

                if ("S".equals(this.tipoCampo.get(i)))//tipo String
                {
                    this.preMod.setString(i + 1, this.valores.get(i).toString());
                }
           
                if ("B".equals(this.tipoCampo.get(i)))//tipo Bytes
                {
                    this.preMod.setBytes(i + 1, dato);
                }

            }//termina for

            this.preMod.executeUpdate();//ejecutar SQL
            resultado = true;
            System.out.println("Datos actualizados correctamente en la tabla: " + nombreTab);
            initVariables();//limpiar para una nueva actualizacion

        } catch (SQLException ex) {
            System.err.print("No se pudo actualizar el registro: " + ex.getMessage());
            resultado = false;
        }

        return resultado;
    }
}
