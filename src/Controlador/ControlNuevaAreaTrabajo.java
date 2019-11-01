/*
 * Controlador para el registro de nuevas areas de trabajo
 */
package Controlador;

import Modelo.AreaTrabajo;
import Modelo.ClaseConsultar;
import Modelo.ClaseInsertar;
import Modelo.ClaseModificar;
import Modelo.Conexion;
import Vista.PnlBarraBotones;
import Vista.PnlNuevaAreaTrabajo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Jaasiel Guerra
 */
public class ControlNuevaAreaTrabajo extends PnlNuevaAreaTrabajo implements ActionListener {

    //atributos
    private ControlPrincipal controlMain = null;
    private ControlAreasTrabajo controlAreasT = null;
    private AreaTrabajo area = null;
    private CambiarPanel cambiar = null;
    private boolean activarActualizacionArea = false;
    private int idAreaActualizar = 0;

    public ControlNuevaAreaTrabajo(ControlPrincipal main, ControlAreasTrabajo controlAT) {

        this.controlMain = main;
        this.controlAreasT = controlAT;
        this.area = new AreaTrabajo();
        this.cambiar = new CambiarPanel();

        this.BtnGuardar.addActionListener(this);

    }

    private void guardarArea() {

        //validar formulario
        if (!TxtId.getText().isEmpty() && !TxtAreaTrabajo.getText().isEmpty()
                && !TxtHoraEntrada.getText().isEmpty() && !TxtHoraSalida.getText()
                .isEmpty()) {

            //llenar el objeto area
            area.setId(Integer.parseInt(TxtId.getText()));
            area.setNombreArea(TxtAreaTrabajo.getText());
            area.setHoraEntrada(TxtHoraEntrada.getText());
            area.setHoraSalida(TxtHoraSalida.getText());

            boolean validarID = true;
            boolean primerAreaT = false;

            /*
             *  comprobar si se pulso boton de admin o empleado, y no existe ningun area
             *  de trabajo, entonces el programa llevara hasta aca al usuario a registrar
             *  la primera area de trabajo, y una vez registrada el area lo llevara a registrar
             * al nuevo emplado.
             */
            if (!controlMain.getBarraBotones().getBotonPulsado().equals("AreaTrabajo")) {
                if (controlMain.getBarraBotones().getBotonPulsado().equals("Admin")
                        || controlMain.getBarraBotones().getBotonPulsado().equals("Propietario")
                        || new ClaseContarRegistros().contarReg("datos/registro", "AreasTrabajo") > 0) {
                    primerAreaT = true;
                }
            }

            Conexion conec = new Conexion("datos/registro");
            Connection objConector = conec.conectar();
            ClaseConsultar consulta = new ClaseConsultar(objConector, "AreasTrabajo");

            consulta.consultar("id");//consultar id del area trabajo

            try {
                //validando que no existe el id que se esta ingresando
                while (consulta.getResultadoConsulta().next()) {//recorrer los registros
                    if (consulta.getResultadoConsulta().getInt("id") == area.getId()) {
                        validarID = false;
                        JOptionPane.showMessageDialog(this, "ID Existente.", "Control Acceso",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    } else {
                        validarID = true;
                    }
                }

            } catch (SQLException ex) {
                System.out.println("Error al consultar el id AreasTrabajo: " + ex.getMessage());
            }

            if (validarID) {
                ClaseInsertar insert = new ClaseInsertar(objConector, "AreasTrabajo");
                insert.agregarValor("id", area.getId(), "I");
                insert.agregarValor("NombreArea", area.getNombreArea(), "S");
                insert.agregarValor("HoraEntrada", area.getHoraEntrada(), "S");
                insert.agregarValor("HoraSalida", area.getHoraSalida(), "S");

                if (insert.ejecutarSQL()) {
                    JOptionPane.showMessageDialog(this, "Area Registrada correctamente.",
                            "Control Acceso", JOptionPane.INFORMATION_MESSAGE);

                    if (primerAreaT) {
                        controlMain.getBarraBotones().btnAddUser.doClick();
                    } else {
                        controlMain.getBarraBotones().btnAreaTrabajo.doClick();
                    }

                }
            }

            conec.cerrar();
            conec = null;
            objConector = null;
            consulta = null;
            System.gc();//ejecutar recolector de basura
        } else {
            JOptionPane.showMessageDialog(this, "Algunos campos están vacíos o están incompletos."
                    + "\nPor favor Verifique.",
                    "Control Acceso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actualizarArea() {
        //validar formulario
        if (!TxtId.getText().isEmpty() && !TxtAreaTrabajo.getText().isEmpty()
                && !TxtHoraEntrada.getText().isEmpty() && !TxtHoraSalida.getText()
                .isEmpty()) {

            //llenar el objeto area
            area.setId(Integer.parseInt(TxtId.getText()));
            area.setNombreArea(TxtAreaTrabajo.getText());
            area.setHoraEntrada(TxtHoraEntrada.getText());
            area.setHoraSalida(TxtHoraSalida.getText());

            boolean validarID = false;

            Conexion conec = new Conexion("datos/registro");
            Connection objConector = conec.conectar();
            ClaseConsultar consulta = new ClaseConsultar(objConector, "AreasTrabajo");

            consulta.consultar("id");//consultar id del area trabajo

            try {
                //validando el id
                while (consulta.getResultadoConsulta().next()) {//recorrer los registros
                    if (idAreaActualizar != area.getId() && consulta.getResultadoConsulta().getInt("id") == area.getId()) {
                        validarID = false;
                        System.out.println("ID invalido");
                        
                         JOptionPane.showMessageDialog(this, "ID Existente.", "Control Acceso",
                                JOptionPane.ERROR_MESSAGE);

                        break;
                    } else {
                        validarID = true;
                       
                    }
                }

            } catch (SQLException ex) {
                System.out.println("Error al consultar el id AreasTrabajo: " + ex.getMessage());
            }

            if (validarID) {

                ClaseModificar mod = new ClaseModificar(objConector, "Empleados");
                mod.agregarValor("AreasTrabajo_id", area.getId(), "I");
                mod.setDonde("AreasTrabajo_id", String.valueOf(idAreaActualizar));
                if (!mod.ejecutarSQL()) {
                    return;
                }

                mod = new ClaseModificar(objConector, "AreasTrabajo");
                mod.agregarValor("id", area.getId(), "I");
                mod.agregarValor("NombreArea", area.getNombreArea(), "S");
                mod.agregarValor("HoraEntrada", area.getHoraEntrada(), "S");
                mod.agregarValor("HoraSalida", area.getHoraSalida(), "S");
                mod.setDonde("id", String.valueOf(idAreaActualizar));
                if (mod.ejecutarSQL()) {
                    JOptionPane.showMessageDialog(this, "Area Actualizada correctamente.",
                            "Control Acceso", JOptionPane.INFORMATION_MESSAGE);
                    controlMain.getBarraBotones().btnAreaTrabajo.doClick();
                }
            }

            conec.cerrar();
            conec = null;
            objConector = null;
            // consulta = null;
            System.gc();//ejecutar recolector de basura

        } else {
            JOptionPane.showMessageDialog(this, "Algunos campos están vacíos o están incompletos."
                    + "\nPor favor Verifique.",
                    "Control Acceso", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void activarActualizarArea(boolean activar, int id) {

        this.activarActualizacionArea = activar;
        this.idAreaActualizar = id;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(BtnGuardar)) {
            if (activarActualizacionArea) {
                actualizarArea();
            } else {
                guardarArea();
            }
        }

    }

}
