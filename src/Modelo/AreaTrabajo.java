/*
 * ENcapsulamiento de los datos de araeas de trabajo
 */
package Modelo;

/**
 *
 * @author Jaasiel Guerra
 */
public class AreaTrabajo {
  
    
    private int id ;
    private String NombreArea;
    private String HoraEntrada;
    private String HoraSalida;

    public AreaTrabajo() {
        this.id = 0;
        this.NombreArea = "";
        this.HoraEntrada = "";
        this.HoraSalida = "";
    }

    //============SETERS AND GETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreArea() {
        return NombreArea;
    }

    public void setNombreArea(String NombreArea) {
        this.NombreArea = NombreArea;
    }

    public String getHoraEntrada() {
        return HoraEntrada;
    }

    public void setHoraEntrada(String HoraEntrada) {
        this.HoraEntrada = HoraEntrada;
    }

    public String getHoraSalida() {
        return HoraSalida;
    }

    public void setHoraSalida(String HoraSalida) {
        this.HoraSalida = HoraSalida;
    }
    
    
    
    
}
