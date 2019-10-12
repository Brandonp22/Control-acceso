/*
 * Para encapsular los datos del usuario
 */
package Modelo;


public class Usuario {

    public Usuario() {//constructor
        this.nombre = "";
        this.apellido = "";
        this.DPI = 0;
        this.userName = "";
        this.password = "";
        this.huella = null;
        this.privilegio = "";
        this.area = "";
    }
   
    
    private String nombre;
    private String apellido;
    private long DPI;
    private String userName;
    private String password;
    private byte huella[];
    private byte[] foto;

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
    private String privilegio;
    private String area;

    //==================METODOS GETTERS AND SETTERS======================
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public long getDPI() {
        return DPI;
    }

    public void setDPI(long DPI) {
        this.DPI = DPI;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getHuella() {
        return huella;
    }

    public void setHuella(byte[] huella) {
        this.huella = huella;
    }

    public String getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(String privilegio) {
        this.privilegio = privilegio;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    
    
    
}
