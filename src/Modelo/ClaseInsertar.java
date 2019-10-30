/*
 * Esta clase se encarga de construir la sentencia SQL para insertar
 * cualquier registro en la DB
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jassiel Guerra
 */
public class ClaseInsertar {

    /**
     * Variables que se utilizaran en el proceso de insercion
     */
    private Connection objConector = null;
    private String nombreTab;
    private PreparedStatement preInsert;//objeto de preparion de consulta SQL
    private ArrayList<Object> valores;//lista de tipo objeto
    private StringBuilder SQL;
    private ArrayList<String> tipoCampo;
    //private byte[] dato;
    //short auxNumDatos; //variable auxilirias para el numero de datos a insertar

    public ClaseInsertar(Connection objConector, String nombreTab_) {//recibe el objeto de conexion y nombre de la tabla

        /**
         * Inicializar todos los objetos que se van a utilizar
         */
        System.out.println("Conectado desde: " + this.getClass().getName());
        this.objConector = objConector;
        this.nombreTab = nombreTab_;

        initVariables();
        //this.auxNumDatos = 0; //imicializar en cero
    }

    //este metodo sirve para insertar datos primitivos 
    public void agregarValor(String campo, Object valor, String tipoCampo_) {

        //==========CONSTRUYENDO SENTENCIA SQL=====================================
        if (valores.size() < 1)//comprobar que es el primer campo que se va agregar
        {
            this.SQL.append(campo);//se agrega el campo sin coma
        } else if (valores.size() >= 1)//comprobar que ya existe un campo
        {
            this.SQL.append(",").append(campo);//y se agrega el campo precedido de una coma
        }        //=========================================================================

        this.valores.add(valor); // agregar valor al ArrayList

        this.tipoCampo.add(tipoCampo_);  //agregar el tipo de campo    

    }

    //este metodo es especial para agregar arreglos de bytes
    public void agregarValor(String campo, byte[] valor, String tipoCampo_) {

        //==========CONSTRUYENDO SENTENCIA SQL=====================================
        if (valores.size() < 1)//comprobar que es el primer campo que se va agregar
        {
            this.SQL.append(campo);//se agrega el campo sin coma
        } else if (valores.size() >= 1)//comprobar que ya existe un campo
        {
            this.SQL.append(",").append(campo);//y se agrega el campo precedido de una coma
        }        //=========================================================================

        //this.dato = valor;//agregar el dato tipo arreglo de bytes
        this.valores.add(valor);//agregar valos al arraylist

        this.tipoCampo.add(tipoCampo_);  //agregar el tipo de campo   

        //this.auxNumDatos++;//usamos el numero auxiliar para numero de datos
    }

    public boolean ejecutarSQL() {
        boolean resultado = false;

        this.SQL.append(") values (");//cerrar el parentesis de campos y abrir el de valores

        //recorrer la lista de elementos para agregar los signos ?
        for (int i = 0; i < tipoCampo.size(); i++) {

            if (i == 0)//primera vuelta
            {
                this.SQL.append("?");//colocamos el signo sin coma
            } else if (i >= 1)//si es mas de una vuelta
            {
                this.SQL.append(",?");// colocamos signo precedido por coma
            }
        }

        this.SQL.append(");");//cerramos el parentesis dejando listo la sentencia SQL

        System.out.println("SQL Construido: " + SQL.toString());//imprimir SQL

        try {

            this.preInsert = objConector.prepareStatement(SQL.toString());//agregar SQL

            //clasificador de variables
            for (int i = 0; i < valores.size(); i++) {//recorrer la lista de campos

                this.preInsert.setObject(i + 1, this.valores.get(i));

                //validamos que el arreglo no recorra un valor mayor que el tamanio del mismo
                // if(i <= campos.size())
                //{
                /*
                if ("I".equals(this.tipoCampo.get(i)))//tipo Int
                {
                    this.preInsert.setObject(i + 1, this.valores.get(i));
                }

                if ("L".equals(this.tipoCampo.get(i)))//tipo Long
                {
                    this.preInsert.setObject(i + 1, this.valores.get(i));
                }

                if ("F".equals(this.tipoCampo.get(i)))//tipo Float
                {
                    this.preInsert.setObject(i + 1, this.valores.get(i));
                }

                if ("D".equals(this.tipoCampo.get(i)))//tipo Double
                {
                    this.preInsert.setObject(i + 1, this.valores.get(i));
                }

                if ("S".equals(this.tipoCampo.get(i)))//tipo String
                {
                    this.preInsert.setObject(i + 1, this.valores.get(i));
                }
                //}

                if ("B".equals(this.tipoCampo.get(i)))//tipo Bytes
                {
                    this.preInsert.setObject(i + 1, this.valores.get(i));
                }
                 */
            }//termina for

            this.preInsert.execute();//ejecutar SQL
            resultado = true;
            System.out.println("Datos insertados correctamente en la tabla: " + nombreTab);
            initVariables();//limpiar para una nueva insercion

        } catch (SQLException ex) {
            System.err.print("No se pudo insertar el registro: " + ex.getMessage());
            resultado = false;
        }

        return resultado;
    }

    private void initVariables() {//limpiar todas las variables dejando listo para nueva insercion

        this.preInsert = null;
        this.valores = new ArrayList<Object>();
        this.SQL = new StringBuilder();
        this.tipoCampo = new ArrayList<String>();
        //this.dato = null;
        this.SQL.append("INSERT INTO ").append(nombreTab).append("(");//inicializar sentencia SQL

    }
}
