/*
 * Encapsulamiento de empleados y propietarios
 */
package Modelo;

/**
 *
 * @author Jaasiel Guerra
 */
public class Personal {
    
    protected long DPI;
    protected String Nombre;
    protected String Apellidos;
    protected int AreaTrabajo;
    private String NombreUsuario;
    private String Contrasenia;
    private String Privilegio;
    private byte Huella[];
    private byte[] Foto;
    private byte[] FotoHuella;
    public Personal() {
        this.DPI = 0;
        this.Nombre = "";
        this.Apellidos = "";
        this.AreaTrabajo = 0;
         this.NombreUsuario = "";
        this.Contrasenia = "";
        this.Privilegio = "";
        this.Huella = null;
        this.Foto = null;
    }

    public long getDPI() {
        return DPI;
    }

    public void setDPI(long DPI) {
        this.DPI = DPI;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    public int getAreaTrabajo() {
        return AreaTrabajo;
    }

    public void setAreaTrabajo(int AreaTrabajo) {
        this.AreaTrabajo = AreaTrabajo;
    }
    
    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String NombreUsuario) {
        this.NombreUsuario = NombreUsuario;
    }

    public String getContrasenia() {
        return Contrasenia;
    }

    public void setContrasenia(String Contrasenia) {
        this.Contrasenia = Contrasenia;
    }

    public String getPrivilegio() {
        return Privilegio;
    }

    public void setPrivilegio(String Privilegio) {
        this.Privilegio = Privilegio;
    }

    public byte[] getHuella() {
        return Huella;
    }

    public void setHuella(byte[] Huella) {
        this.Huella = Huella;
    }

    public byte[] getFoto() {
        return Foto;
    }

    public void setFoto(byte[] Foto) {
        this.Foto = Foto;
    }

    public byte[] getFotoHuella() {
        return FotoHuella;
    }

    public void setFotoHuella(byte[] FotoHuella) {
        this.FotoHuella = FotoHuella;
    }
    
    
    
    
}
