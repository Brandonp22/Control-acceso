/*
 * Control para el panel que muestra los empleados o admin registrado con su respectiva
 * area de trabajo y horarios
 */
package Controlador;

import Modelo.ClaseConsultar;
import Modelo.Conexion;
import Vista.PnlTablaDatos;

/**
 *
 * @author Jaasiel Guerra
 */
public class ControlTablaDatos extends PnlTablaDatos {

    public ControlTablaDatos() {

    }

    //actualiza la tabla con el parametro recibida en su argumento
    public void actualizarTabla(String privilegioMostrar) {

        Conexion conectarse = new Conexion("datos/registro");
        ClaseConsultar consulta = new ClaseConsultar(conectarse.conectar(), "Empleados,AreasTrabajo,Usuarios");

        consulta.consultar("Empleados.Usuarios_DPI,Empleados.Nombre,Empleados.Apellidos,"
                + "AreasTrabajo.NombreArea,AreasTrabajo.HoraEntrada,AreasTrabajo.HoraSalida",
                "Empleados.AreasTrabajo_id", "=", "AreasTrabajo.id AND Empleados.Usuarios_DPI = Usuarios.DPI"
                + " AND Usuarios.Privilegio = '" + privilegioMostrar + "'");//consulta personalizada

        RellenarTabla rellenar = new RellenarTabla(consulta.getResultadoConsulta(), this.Datos);
        rellenar.llenar();

        conectarse.cerrar();//cerrar conexion
        conectarse = null;
        consulta = null;
        System.gc();

    }

}
