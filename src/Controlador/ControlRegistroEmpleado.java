/*
 * Controlador para el registro de los empleados
 */
package Controlador;

import Modelo.ClaseConsultar;
import Modelo.ClaseInsertar;
import Modelo.ClaseModificar;
import Modelo.Conexion;
import Modelo.Personal;
import Vista.FrameRegistro;
import Vista.PnlFoto;
import Vista.PnlRegHuella;
import Vista.PnlRegistroPersona;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Jaasiel Guerra
 */
public class ControlRegistroEmpleado extends ClaseLector implements ActionListener, MouseListener {

    //variables de la vista
    private FrameRegistro formularioHuella = null;
    private PnlRegHuella pnlHuella = null;
    private PnlRegistroPersona pnlRegistroPer = null;
    private PnlFoto pnlFoto = null;
    private CambiarPanel cambiarPanel = null;
    private Personal personal = null;
    private ControlPrincipal controlMain = null;
    private Map<String, Integer> areas = null;
    private final String DEFAULT_SELECTED_COMBO = "<Seleccione Area>";
    private boolean activarActualizarEmp = false;
    private long DPIActualizar = 0;
    private short lblFotoClik = 0;

    /////constructor//////////
    public ControlRegistroEmpleado(ControlPrincipal ventanaMain, Personal per) {

        super();//ejecutar constructor de clase base

        this.controlMain = ventanaMain;
        //this.pnlRegistroPer.TxtUsuario.

        this.formularioHuella = new FrameRegistro();
        this.pnlHuella = new PnlRegHuella();
        this.pnlFoto = new PnlFoto();
        this.pnlRegistroPer = new PnlRegistroPersona();
        this.personal = new Personal();
        this.cambiarPanel = new CambiarPanel();
        this.areas = new HashMap<String, Integer>();//mapa para las areas de trabajo

        //tamanio de los botones
        this.pnlRegistroPer.BtnFoto.setSize(140, 120);
        this.pnlRegistroPer.BtnHuella.setSize(140, 120);
        
        //desactivar el boton para aceptar la foto
        this.pnlFoto.BtnAceptarFoto.setEnabled(false);
        this.pnlFoto.LblFoto.setImagen("/Img/BtnIniciarCamara.png");

        /*
        * aca se valida si el privilegio del usuario es propietario
        * de ser asi, se hacen visibles los componenetes
         */
        this.pnlRegistroPer.LblAdmin.setVisible(per.getPrivilegio().equals("Propietario"));
        this.pnlRegistroPer.CheckAdmin.setVisible(per.getPrivilegio().equals("Propietario"));

        this.pnlRegistroPer.BtnGuardar.addActionListener(this);
        this.pnlRegistroPer.BtnHuella.addActionListener(this);
        this.pnlRegistroPer.BtnFoto.addActionListener(this);
        this.pnlFoto.BtnAceptarFoto.addActionListener(this);

        this.pnlFoto.LblFoto.addMouseListener(this);

        //this.pnlRegistroPer.BtnHuella.setText(null);
        //this.pnlRegistroPer.BtnFoto.setText(null);
        iniciarEventosTextField();
        insertAreasTrabajo();
        
        this.formularioHuella.setIconImage(new ImageIcon(getClass().getResource("/Img/Logo.png")).getImage());

    }

    private void iniciarEventosTextField() {

        //este evento es para validar el campo DPI em tiempo real
        this.pnlRegistroPer.IntDPI.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {

                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada es una letra
                if (((caracter < '0')
                        || (caracter > '9'))
                        && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {

                    e.consume();  //si es letra se ignora el evento
                    //JOptionPane.showMessageDialog(null, "Campo DPI inválido.",
                    //      "Control Acceso", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
    }

    //esto inserta las areas de trabajo existentees en el jcombobox
    private void insertAreasTrabajo() {
        Conexion conector = new Conexion("datos/registro");
        ClaseConsultar consulta = new ClaseConsultar(conector.conectar(), "AreasTrabajo");

        consulta.consultar("id,NombreArea");

        try {
            this.pnlRegistroPer.CombAreasT.removeAllItems();//limpiar
            this.pnlRegistroPer.CombAreasT.addItem(DEFAULT_SELECTED_COMBO);
            while (consulta.getResultadoConsulta().next()) {

                //poner las areas en el combobox
                this.pnlRegistroPer.CombAreasT.addItem(consulta.getResultadoConsulta()
                        .getString("NombreArea"));

                //meter las areas en el mapa
                this.areas.put(consulta.getResultadoConsulta().getString("NombreArea"),
                        consulta.getResultadoConsulta().getInt("id"));
            }
        } catch (SQLException ex) {
            System.err.print("Error al insertar areas: " + ex.getMessage());
        } finally {
            conector.cerrar();
            conector = null;
            consulta = null;
            System.gc();
        }
    }

    public PnlRegistroPersona getPnlRegistroPer() {
        return pnlRegistroPer;
    }

    private void iniciarEventosLector() {

        /*
        EL lector tiene sus propios eventos, los cuales se activan desde esta
        funcion, aca se activan solamente cuatro
         */
        //----------------Metodo para capturar la huella--------------------------------------------
        Lector.addDataListener(new DPFPDataAdapter() {//agregar el detector de eventos de datos

            @Override
            public void dataAcquired(final DPFPDataEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                        System.out.println("La huella ha sido capturada");
                        //mandar la img de la huella al label
                        DibujarImagen(CrearImagenHuella(e.getSample()), pnlHuella.LblHuella);

                        ProcesarCaptura(e.getSample());

                    }
                });
            }

        });

        //------------------Metodo para saber el estado del lector---------------------------------------
        Lector.addReaderStatusListener(new DPFPReaderStatusAdapter() {//agrega el evento de estado del lector
            @Override
            public void readerConnected(final DPFPReaderStatusEvent e) {//si el lector esta activado
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                        System.out.println("EL sensor de huella se encuentra activado");
                        pnlHuella.LblHuella.setIcon(new ImageIcon(getClass()
                                .getResource("/Img/LectorConectado.png")));
                    }
                });
            }

            @Override
            public void readerDisconnected(final DPFPReaderStatusEvent e) {//si el lector esta desactivado
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("El sensor se encuentra desactivado");
                        pnlHuella.LblHuella.setIcon(new ImageIcon(getClass()
                                .getResource("/Img/LectorDesactivado.png")));
                    }
                });
            }
        });

        //-----------------Metodo para saber los errores del lector-----------------------------------------
        Lector.addErrorListener(new DPFPErrorAdapter() {//agrega el detector de eventos de errores
            public void errorReader(final DPFPErrorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Error: " + e.getError());

                    }
                });
            }
        });
    }

    //cambiar de imagen en cada paso de captura de la huella
    public void marcaPasos(int paso, JLabel LblPaso) {
        LblPaso.setIcon(new ImageIcon(getClass().getResource("/Img/Paso" + paso + ".png")));
    }

    //mostrar la cantidad de huellas que faltan para completar plantilla
    public void EstadoHuellas() {
        System.out.println("Muestra de huellas necesarias para guardar plantilla: "
                + Reclutador.getFeaturesNeeded());

        int paso = 4 - Reclutador.getFeaturesNeeded();
        marcaPasos(paso, pnlHuella.LblPasos);//marcar el paso

    }

    public void ProcesarCaptura(DPFPSample muestra) {

        //para guardar las plantillas, que se obtienen en la funcion extraerCaracteristicas
        featureSetInscripcion = extraerCaracteristicas(muestra, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

        if (featureSetInscripcion != null) {//asegurarse de que la huella este bien leida

            try {

                System.out.println("Las características de la huella han sido creadas");
                Reclutador.addFeatures(featureSetInscripcion);//aniadir la plantilla de la huella al reclutador

            } catch (DPFPImageQualityException e) {
                System.err.println("Error al extreaer caracteristicas: " + e.getMessage());
            } finally {

                EstadoHuellas();
                switch (Reclutador.getTemplateStatus()) {

                    case TEMPLATE_STATUS_READY://huella lista!!!

                        marcaPasos(5, pnlHuella.LblPasos);//marca el ultimo paso
                        super.stop();//detiene el lector de huella
                        setTemplate(Reclutador.getTemplate());//se establece le huella 
                        this.formularioHuella.setVisible(false);//oculta la ventanita
                        this.controlMain.getVentanaPrincipal().setVisible(true);//hablitar ventana main

                        //pone la imagen de la huella en el boton
                        super.DibujarImagen(CrearImagenHuella(muestra), this.pnlRegistroPer.BtnHuella);
                        personal.setFotoHuella(muestra.serialize());//se establece la img de la huella en bytes

                        //this.pnlRegistroPer.jButton2.setEnabled(false);//desactiva el boton
                        //this.pnlRegistroPer.jButton2.removeActionListener(this);//no escucha mas acciones el boton
                        this.personal.setHuella(getPlantillaHuella().serialize());//llena el dato huella
                        this.Reclutador.clear();//limpiar el reclutador

                        break;

                    case TEMPLATE_STATUS_FAILED://si falla el proceso 

                        Reclutador.clear();//se limpia el reclutador
                        stop();//deniene el lector 

                        setTemplate(null);//poner en null para evitar errores

                        System.out.println("La Plantilla de la huella no pudo ser creada. Por favor repita el proceso.");
                        JOptionPane.showMessageDialog(null, "La Plantilla de la huella no pudo ser creada.\nPor favor repita el proceso.",
                                "Control Acceso", JOptionPane.ERROR_MESSAGE);
                        start();

                        EstadoHuellas();

                        break;
                }//termina switch
            }//termina finally
        }//termina if
    }//fin metodo

    //inicia el formulario para la captura de la huella
    private void iniciarCapturaHuella() {

        //para evitar errores se quita la escucha de las acciones del boton
        this.pnlRegistroPer.BtnHuella.removeActionListener(this);//no escucha mas acciones el boton
        this.iniciarEventosLector();
        this.start();
        this.EstadoHuellas();

        this.controlMain.getVentanaPrincipal().setVisible(false);//deshabilita ventana main

        //insertar el panel huella
        this.cambiarPanel.cambiarPNL(formularioHuella.PnlCentral, pnlHuella);
        this.formularioHuella.BtnSiguiente.setVisible(false);
        this.formularioHuella.setLocationRelativeTo(null);
        //this.formularioHuella.setAlwaysOnTop(true);//para que sea modal
        this.formularioHuella.setVisible(true);
    }

    private void iniciarTomaFoto() {

        //remover la escucha de acciones
        this.pnlRegistroPer.BtnFoto.removeActionListener(this);

        this.controlMain.getVentanaPrincipal().setVisible(false);//deshabilita ventana main

        this.cambiarPanel.cambiarPNL(formularioHuella.PnlCentral, pnlFoto);
        this.formularioHuella.BtnSiguiente.setVisible(false);
        this.formularioHuella.setLocationRelativeTo(null);
        this.formularioHuella.setVisible(true);

    }

    private void capturarFoto() {

        this.personal.setFoto(this.pnlFoto.LblFoto.getBytes());
        this.formularioHuella.setVisible(false);

        super.DibujarImagen(pnlFoto.LblFoto.getImage(), pnlRegistroPer.BtnFoto);

        this.controlMain.getVentanaPrincipal().setVisible(true);
    }

    private void guardarEmpleado() {

        boolean validarDPI = true;

        //validar 
        if (this.personal.getHuella() != null && pnlRegistroPer.TxtUsuario.getText().length() > 0
                && pnlRegistroPer.TxtPassword.getText().length() > 0 && pnlRegistroPer.TxtNombre.getText().length() > 0
                && pnlRegistroPer.TxtApellido.getText().length() > 0 && pnlRegistroPer.IntDPI.getText().length() > 0
                && !pnlRegistroPer.CombAreasT.getSelectedItem().toString().equals(DEFAULT_SELECTED_COMBO)
                && this.personal.getFoto() != null) {

            //llenar el usuario
            this.personal.setNombreUsuario(pnlRegistroPer.TxtUsuario.getText().toUpperCase());
            this.personal.setContrasenia(pnlRegistroPer.TxtPassword.getText());
            this.personal.setNombre(pnlRegistroPer.TxtNombre.getText().toUpperCase());
            this.personal.setApellidos(pnlRegistroPer.TxtApellido.getText().toUpperCase());
            this.personal.setDPI(Long.parseLong(pnlRegistroPer.IntDPI.getText()));
            this.personal.setAreaTrabajo(this.areas.get(pnlRegistroPer.CombAreasT.getSelectedItem().toString()));

            //si esta seleccionado el check es admin
            if (pnlRegistroPer.CheckAdmin.isSelected()) {
                this.personal.setPrivilegio("Admin");
            } else {
                this.personal.setPrivilegio("Empleado");
            }

            Conexion con = new Conexion("datos/registro");
            ClaseConsultar consulta = new ClaseConsultar(con.conectar(), "Usuarios");

            consulta.consultar("DPI");

            try {
                //validando que no existe el DPI que se esta ingresando
                while (consulta.getResultadoConsulta().next()) {//recorrer los registros
                    if (consulta.getResultadoConsulta().getLong("DPI") == personal.getDPI()) {
                        validarDPI = false;
                        JOptionPane.showMessageDialog(this, "El campo DPI ya exíste.",
                                "Control Acceso", JOptionPane.ERROR_MESSAGE);
                        break;
                    } else {
                        validarDPI = true;
                    }
                }

            } catch (SQLException ex) {
                System.out.println("Error al consultar el DPI Usuarios: " + ex.getMessage());
            }

            if (validarDPI) {

                ClaseInsertar insertar = new ClaseInsertar(con.conectar(), "Usuarios");

                insertar.agregarValor("DPI", personal.getDPI(), "L");
                insertar.agregarValor("NombreUsuario", personal.getNombreUsuario(), "S");
                insertar.agregarValor("Contrasenia", personal.getContrasenia(), "S");
                insertar.agregarValor("Privilegio", personal.getPrivilegio(), "S");
                insertar.agregarValor("Huella", personal.getHuella(), "B");
                insertar.agregarValor("Foto", personal.getFoto(), "B");
                insertar.agregarValor("FotoHuella", personal.getFotoHuella(), "B");

                if (!insertar.ejecutarSQL()) {

                    return;//si no se inserto correctamente retornamos
                }

                insertar = new ClaseInsertar(con.conectar(), "Empleados");

                insertar.agregarValor("Usuarios_DPI", personal.getDPI(), "L");
                insertar.agregarValor("Nombre", personal.getNombre(), "S");
                insertar.agregarValor("Apellidos", personal.getApellidos(), "S");
                insertar.agregarValor("AreasTrabajo_id", personal.getAreaTrabajo(), "S");

                if (insertar.ejecutarSQL()) {

                    JOptionPane.showMessageDialog(null, "Empleado registrado correctamente.",
                            "Control Acceso", JOptionPane.INFORMATION_MESSAGE);

                    //listar resgitro
                    if (personal.getPrivilegio().equals("Empleado")) {
                        this.controlMain.getBarraBotones().btnEmpleado.doClick();
                    } else {
                        this.controlMain.getBarraBotones().btnAdmin.doClick();
                    }
                }
            }
            con.cerrar();//cerrar conexion
            con = null;
            System.gc();

        } else {
            JOptionPane.showMessageDialog(this, "Por favor llene todos los datos.",
                    "Control Acceso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void actualizarEmpleado() {

        //validar 
        if (this.personal.getHuella() != null && pnlRegistroPer.TxtUsuario.getText().length() > 0
                && pnlRegistroPer.TxtPassword.getText().length() > 0 && pnlRegistroPer.TxtNombre.getText().length() > 0
                && pnlRegistroPer.TxtApellido.getText().length() > 0 && pnlRegistroPer.IntDPI.getText().length() > 0
                && !pnlRegistroPer.CombAreasT.getSelectedItem().toString().equals(DEFAULT_SELECTED_COMBO)
                && this.personal.getFoto() != null) {

            //llenar el usuario
            this.personal.setNombreUsuario(pnlRegistroPer.TxtUsuario.getText().toUpperCase());
            this.personal.setContrasenia(pnlRegistroPer.TxtPassword.getText());
            this.personal.setNombre(pnlRegistroPer.TxtNombre.getText().toUpperCase());
            this.personal.setApellidos(pnlRegistroPer.TxtApellido.getText().toUpperCase());
            this.personal.setDPI(Long.parseLong(pnlRegistroPer.IntDPI.getText()));
            this.personal.setAreaTrabajo(this.areas.get(pnlRegistroPer.CombAreasT.getSelectedItem().toString()));

            //si esta seleccionado el check es admin
            if (pnlRegistroPer.CheckAdmin.isSelected()) {
                this.personal.setPrivilegio("Admin");
            } else {
                this.personal.setPrivilegio("Empleado");
            }

            boolean validarDPI = true;
            Conexion con = new Conexion("datos/registro");
            ClaseConsultar consulta = new ClaseConsultar(con.conectar(), "Usuarios");

            consulta.consultar("DPI");

            try {
                //validando que no existe el DPI que se esta ingresando
                while (consulta.getResultadoConsulta().next()) {//recorrer los registros
                    if (DPIActualizar != personal.getDPI() && consulta.getResultadoConsulta().getInt("DPI") == personal.getDPI()) {
                        validarDPI = false;
                        System.out.println("DPI invalido");
                        JOptionPane.showMessageDialog(this, "El campo DPI ya exíste.",
                                "Control Acceso", JOptionPane.ERROR_MESSAGE);
                        break;
                    } else {
                        validarDPI = true;
                    }
                }

            } catch (SQLException ex) {
                System.out.println("Error al consultar el DPI Usuarios: " + ex.getMessage());
            }

            if (validarDPI) {//si se valido el DPI

                //primero se actualiza el usuario
                ClaseModificar mod = new ClaseModificar(con.conectar(), "Usuarios");
                mod.agregarValor("DPI", personal.getDPI(), "L");
                mod.agregarValor("NombreUsuario", personal.getNombreUsuario(), "S");
                mod.agregarValor("Contrasenia", personal.getContrasenia(), "S");
                mod.agregarValor("Privilegio", personal.getPrivilegio(), "S");
                mod.agregarValor("Huella", personal.getHuella(), "B");
                mod.agregarValor("Foto", personal.getFoto(), "B");
                mod.agregarValor("FotoHuella", personal.getFotoHuella(), "B");
                mod.setDonde("DPI", String.valueOf(DPIActualizar));
                mod.ejecutarSQL();

                //segundo se actualiza el empleados
                mod = new ClaseModificar(con.conectar(), "Empleados");
                mod.agregarValor("Usuarios_DPI", personal.getDPI(), "L");
                mod.agregarValor("Nombre", personal.getNombre(), "S");
                mod.agregarValor("Apellidos", personal.getApellidos(), "S");
                mod.agregarValor("AreasTrabajo_id", personal.getAreaTrabajo(), "S");
                mod.setDonde("Usuarios_DPI", String.valueOf(DPIActualizar));
                mod.ejecutarSQL();

                //por ultimo se actualiza el historial de entradasSalidas
                mod = new ClaseModificar(con.conectar(), "EntradasSalidas");
                mod.agregarValor("Empleados_Usuarios_DPI", personal.getDPI(), "L");
                mod.setDonde("Empleados_Usuarios_DPI", String.valueOf(DPIActualizar));

                if (mod.ejecutarSQL()) {

                    JOptionPane.showMessageDialog(null, "Empleado Actualizado correctamente.",
                            "Control Acceso", JOptionPane.INFORMATION_MESSAGE);

                    //listar resgitro
                    if (personal.getPrivilegio().equals("Empleado")) {
                        this.controlMain.getBarraBotones().btnEmpleado.doClick();
                    } else {
                        this.controlMain.getBarraBotones().btnAdmin.doClick();
                    }
                }

            }
        } else {
            JOptionPane.showMessageDialog(this, "Algunos campos están vacíos o incompletos.",
                    "Control Acceso", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void prepararActualizacion(Personal p) {

        this.DPIActualizar = p.getDPI();//salvar el DPI
        this.personal = p;

        //dibjar la foto en el boton
        super.DibujarImagen(new ImageIcon(p.getFoto()).getImage(), pnlRegistroPer.BtnFoto);

        //dibujar la foto de la huella en el boton
        super.DibujarImagen(CrearImagenHuella(
                DPFPGlobal.getSampleFactory().createSample(p.getFotoHuella())),
                pnlRegistroPer.BtnHuella);

        this.pnlRegistroPer.TxtUsuario.setText(p.getNombreUsuario());
        this.pnlRegistroPer.TxtPassword.setText(p.getContrasenia());
        this.pnlRegistroPer.TxtNombre.setText(p.getNombre());
        this.pnlRegistroPer.TxtApellido.setText(p.getApellidos());
        this.pnlRegistroPer.IntDPI.setText(String.valueOf(p.getDPI()));
        this.pnlRegistroPer.CheckAdmin.setSelected(
                p.getPrivilegio().equals("Admin"));

    }

    //para activar una actualizacion de emplado al darle click al BTN guardar
    public void setActivarActualizarEmp(boolean activarActualizarEmp) {
        this.activarActualizarEmp = activarActualizarEmp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //boton de capturar huella
        if (e.getSource().equals(this.pnlRegistroPer.BtnHuella)) {

            iniciarCapturaHuella();

        }

        if (e.getSource().equals(this.pnlRegistroPer.BtnFoto)) {
            iniciarTomaFoto();
        }

        if (e.getSource().equals(this.pnlFoto.BtnAceptarFoto)) {
            capturarFoto();
        }

        //boton guardar
        if (e.getSource().equals(this.pnlRegistroPer.BtnGuardar)) {

            if (activarActualizarEmp) {
                actualizarEmpleado();
            } else {
                guardarEmpleado();
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        /////////click lblFoto///////////
        if (e.getSource().equals(pnlFoto.LblFoto)) {

            this.lblFotoClik++;
            //se activa cuando se halla pulsado por segunda vez para capturar la foto
            this.pnlFoto.BtnAceptarFoto.setEnabled(lblFotoClik == 2);

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
