/*
 * Clse que se encarga de insertar datos en la DB
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class ClaseInsertar {
   
    /**
     * Variables que se utilizaran en el proceso de insercion
     */
    private Connection con;
    PreparedStatement preInset;//objeto de preparion de consulta SQL
    ArrayList campos;
    StringBuilder SQL;
    ArrayList<String> tipoCampo;
    byte[] dato;
    //short auxNumDatos; //variable auxilirias para el numero de datos a insertar
    //String nombreTab;

    public ClaseInsertar(Connection con, String nombreTab) {//recibe el objeto de conexion y nombre de la tabla
       
        /**
         * Inicializar todos los objetos que se van a utilizar
         */
        this.con = con;
        
        this.preInset =  null;
        this.campos = new ArrayList();
        this.SQL = new StringBuilder();
        //this.nombreTab = nombreTab;
        
        this.SQL.append("INSERT INTO "+nombreTab+"(");//inicializar sentencia SQL
        //System.out.println("Va bien hasta aqui");
        this.tipoCampo = new ArrayList<String>();//array para los tipos de campos
        this.dato = null;
        
        //this.auxNumDatos = 0; //imicializar en cero
        
    }
    
   
    //este metodo sirve para insertar datos primitivos 
    public void agregar(String campo, Object valor, String tipoCampo_)
    {
        
        //==========CONSTRUYENDO SENTENCIA SQL=====================================
        if(campos.size() < 1)//comprobar que es el primer campo que se va agregar
            this.SQL.append(campo);//se agrega el campo sin coma
        
        else if(campos.size() >= 1)//comprobar que ya existe un campo
            this.SQL.append(","+campo);//y se agrega el campo precedido de una coma
        //=========================================================================
        
        this.campos.add(valor); // agregar valor al ArrayList
        
        this.tipoCampo.add(tipoCampo_);  //agregar el tipo de campo    
        
    }
    
    
    //este metodo es especial para agregar arreglos de bytes
    public void agregar(String campo, byte[] valor, String tipoCampo_)
    {
        
        //==========CONSTRUYENDO SENTENCIA SQL=====================================
        if(campos.size() < 1)//comprobar que es el primer campo que se va agregar
            this.SQL.append(campo);//se agrega el campo sin coma
        
        else if(campos.size() >= 1)//comprobar que ya existe un campo
            this.SQL.append(","+campo);//y se agrega el campo precedido de una coma
        //=========================================================================
        
        this.dato = valor;//agregar el dato tipo arreglo de bytes
        
        this.campos.add(null);//le metemos un valor quemado al arrayList en lugar del byte de la huella
        
        this.tipoCampo.add(tipoCampo_);  //agregar el tipo de campo   
        
        //this.auxNumDatos++;//usamos el numero auxiliar para numero de datos
        
    }
    
    
    public boolean ejecutar()
    {
        boolean resultado = false;
        
        
        this.SQL.append(") values (");//cerrar el parentesis de campos y abrir el de valores
        
        //recorrer la lista de elementos para agregar los signos ?
        for (int i = 0; i < tipoCampo.size(); i++) {
            
            if(i == 0)//primera vuelta
                this.SQL.append("?");//colocamos el signo sin coma
            
            else if(i >= 1)//si es mas de una vuelta
                this.SQL.append(",?");// colocamos signo precedido por coma
                
        }
        
        this.SQL.append(")");//cerramos el parentesis dejando listo la sentencia SQL
        
        System.out.println("SQL Construido: " + SQL.toString());//imprimir SQL
        
        try{
            
            this.preInset = con.prepareStatement(SQL.toString());//agregar SQL
            
            //clasificador de variables
            for (int i = 0; i < campos.size(); i++) {//recorrer la lista de campos
                
                //validamos que el arreglo no recorra un valor mayor que el tamanio del mismo
               // if(i <= campos.size())
                //{
                    
                
                if(this.tipoCampo.get(i) == "I")//tipo Int
                    this.preInset.setInt(i+1, Integer.parseInt(this.campos.get(i).toString()));

                if(this.tipoCampo.get(i) == "L")//tipo Long
                    this.preInset.setLong(i+1, Long.parseLong(this.campos.get(i).toString()));

                if(this.tipoCampo.get(i) == "F")//tipo Float
                    this.preInset.setFloat(i+1, Float.parseFloat(this.campos.get(i).toString()));

                if(this.tipoCampo.get(i) == "D")//tipo Double
                    this.preInset.setDouble(i+1, Double.parseDouble(this.campos.get(i).toString()));    
                    
                if(this.tipoCampo.get(i) == "S")//tipo String
                    this.preInset.setString(i+1, this.campos.get(i).toString());
                //}
                
                if(this.tipoCampo.get(i) == "B")//tipo Bytes
                    this.preInset.setBytes(i+1, dato);     
              
            }//termina for
            
            this.preInset.execute();//ejecutar SQL
            resultado  = true;
            
        }catch(SQLException ex){
            System.err.print("No se pudo insertar el registro: " + ex.getMessage());
            resultado = false;
        }
        
        
        return resultado;
    }
    
    /*
    
    public boolean insertar(String nombreTab, Usuario user)
    {
        boolean resultado = false;//servira para retornar el resultado
        String SQL = "INSERT INTO "+nombreTab+ "(nombre,apellidos,dpi,username,"
                + "password,huella,privilegio) values(?,?,?,?,?,?,?)";
        try{
            
            this.preInset = con.prepareStatement(SQL);//preparar SQL para insercion
            
            this.preInset.setString(1, user.getNombre());
            this.preInset.setString(2, user.getApellido());
            this.preInset.setLong(3, user.getDPI());
            this.preInset.setString(4, user.getUserName());
            this.preInset.setString(5, user.getPassword());
            this.preInset.setBytes(6, user.getHuella());
            this.preInset.setString(7, user.getPrivilegio());
            
            this.preInset.execute();//ejecutar SQL
            resultado = true;
            
        }catch(SQLException ex)
        {
            System.err.print("No se pudo insertar el registro: " + ex.getMessage());
            resultado = false;
        }
        
        
        return resultado;//retorna el resultado
    }
*/
}
