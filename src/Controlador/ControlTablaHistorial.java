/*
 * Este control se encarga de mostrar el historial de las horas de entrada y salida de los empleados
 */
package Controlador;

import Modelo.ClaseConsultar;
import Modelo.Conexion;
import Vista.PnlTablaDatosHistorial;

/**
 *
 * @author Jaasiel Guerra
 */
public class ControlTablaHistorial extends PnlTablaDatosHistorial{
    
    public  ControlTablaHistorial(){
        
    }
    
    //actualiza la tabla con el parametro recibida en su argumento
    public void actualizarTabla(String privilegioMostrar) {

        Conexion conectarse = new Conexion("datos/registro");
        ClaseConsultar consulta = new ClaseConsultar(conectarse.conectar(), "Empleados,AreasTrabajo,Usuarios,EntradasSalidas");

        consulta.consultar("Empleados.Usuarios_DPI,Empleados.Nombre,Empleados.Apellidos,"
                + "AreasTrabajo.NombreArea,EntradasSalidas.Fecha,EntradasSalidas.Entrada,EntradasSalidas.Salida",
                "Empleados.AreasTrabajo_id", "=", "AreasTrabajo.id AND Empleados.Usuarios_DPI = Usuarios.DPI"
                + " AND Usuarios.Privilegio = '" + privilegioMostrar + "'"+ " AND EntradasSalidas.Empleados_Usuarios_DPI"
                        + " = Empleados.Usuarios_DPI");//consulta personalizada

        RellenarTabla rellenar = new RellenarTabla(consulta.getResultadoConsulta(), this.Datos);
        rellenar.llenar();

        conectarse.cerrar();//cerrar conexion
        conectarse = null;
        consulta = null;
        System.gc();

    }
    
}
