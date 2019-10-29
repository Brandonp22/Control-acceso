/*
 * Controlador para el panel de las areas, que permite ver las areas de trabajo
 *  registradas en la base de datos
 */
package Controlador;

import Modelo.ClaseConsultar;
import Modelo.Conexion;
import Vista.PnlAreaTrabajo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Jaasiel Guerra
 */
public class ControlAreasTrabajo extends PnlAreaTrabajo implements ActionListener {

    //atributos
    private ControlPrincipal controlMain = null;
    private CambiarPanel cambiar = null;
    private ControlNuevaAreaTrabajo controlNAT = null;
    private final String DEFAULT_SELECTED = "- Todas -";

    public ControlAreasTrabajo(ControlPrincipal ventanaMain) {

        this.controlMain = ventanaMain;
        this.cambiar = new CambiarPanel();

        //activar la escucha de acciones
        this.BtnNuevo.addActionListener(this);

        initComponentes();
        actualizarTabla();

    }

    //servira para actualizar los registros de la tabla
    public void actualizarTabla() {

        Conexion conectarse = new Conexion("datos/registro");
        ClaseConsultar consulta = new ClaseConsultar(conectarse.conectar(), "AreasTrabajo");

        consulta.consultar("*");//consultar todo

        RellenarTabla rellenar = new RellenarTabla(consulta.getResultadoConsulta(), Datos);
        rellenar.llenar();

        conectarse.cerrar();//cerrar conexion
        conectarse = null;
        consulta = null;
        System.gc();

    }

    private void initComponentes() {

        //iniciar los item del combobox
        for (int i = 0; i < 3; i++) {

            this.TxtBusquedaPor.removeAllItems();//limpiar el combobox
            this.TxtBusquedaPor.addItem(DEFAULT_SELECTED);
            this.TxtBusquedaPor.addItem("id");
            this.TxtBusquedaPor.addItem("Nombre");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //////////boton de nueva area de trabajo///////////////
        if (e.getSource().equals(BtnNuevo)) {
            
            //oculta boton de imprimir de la barra botones
            this.controlMain.getBarraBotones().btnImprimir.setVisible(false);

            this.controlNAT = new ControlNuevaAreaTrabajo(controlMain, this);
            cambiar.cambiarPNL(controlMain.getVentanaPrincipal().panelContenedor, controlNAT);

        }

    }

}
