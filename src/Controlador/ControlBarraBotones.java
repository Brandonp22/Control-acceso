/*
 * Se usara para desactivar y activar los botones de la barra principal
 */
package Controlador;

import Vista.FramePrincipal;
import Vista.PnlBarraBotones;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Jaasiel Guerra
 */
public class ControlBarraBotones implements ActionListener {

    private PnlBarraBotones botones = null;
    private FramePrincipal ventanaMain = null;
    private CambiarPanel cambiar = null;

    public ControlBarraBotones(ControlPrincipal main) {

        this.botones = main.getBarraBotones();//castear el objeto
        this.ventanaMain = ventanaMain = main.getVentanaPrincipal();
        this.cambiar = new CambiarPanel();

        this.botones.btnAddUser.addActionListener(this);
        this.botones.btnAdmin.addActionListener(this);
        this.botones.btnAreaTrabajo.addActionListener(this);
        this.botones.btnEmpleado.addActionListener(this);
        this.botones.btnHistorial.addActionListener(this);
        this.botones.btnImprimir.addActionListener(this);
    }

    /*
    private void activaDesactivaBTN(ActionEvent pulso){//verlficar pulso para activar o desactivar
        
        //la funcion .equals devuelve true o false dependiendo si son o no iguales los objetos
        this.botones.btnAddUser.setEnabled(pulso.equals(botones.btnAddUser));
        this.botones.btnAdmin.setEnabled(pulso.equals(botones.btnAdmin));
        this.botones.btnAreaTrabajo.setEnabled(pulso.equals(botones.btnAreaTrabajo));
        this.botones.btnEmpleado.setEnabled(pulso.equals(botones.btnEmpleado));
        this.botones.btnHistorial.setEnabled(pulso.equals(botones.btnHistorial));
        this.botones.btnImprimir.setEnabled(pulso.equals(botones.btnImprimir));
    }
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        //la funcion .equals devuelve true o false dependiendo si son o no iguales los objetos
        this.botones.btnAddUser.setEnabled(!e.getSource().equals(botones.btnAddUser));
        this.botones.btnAdmin.setEnabled(!e.getSource().equals(botones.btnAdmin));
        this.botones.btnAreaTrabajo.setEnabled(!e.getSource().equals(botones.btnAreaTrabajo));
        this.botones.btnEmpleado.setEnabled(!e.getSource().equals(botones.btnEmpleado));
        this.botones.btnHistorial.setEnabled(!e.getSource().equals(botones.btnHistorial));
        this.botones.btnImprimir.setEnabled(!e.getSource().equals(botones.btnImprimir));

        //this.ventanaMain.panelContenedor.removeAll();//limpiar panel de vista principal

        /*
        if(e.getSource().equals(botones.btnAddUser)){
            //activaDesactivaBTN(e);
        }
        
        if(e.getSource().equals(botones.btnAdmin)){
           // activaDesactivaBTN(e);
            this.botones.btnAdmin.setEnabled(false);

        }
        if(e.getSource().equals(botones.btnAreaTrabajo)){
           // activaDesactivaBTN(e);
        }
        if(e.getSource().equals(botones.btnEmpleado)){
            //activaDesactivaBTN(e);
        }
        if(e.getSource().equals(botones.btnHistorial)){
            //activaDesactivaBTN(e);
        }
        if(e.getSource().equals(botones.btnImprimir)){
            //activaDesactivaBTN(e);
        }
         */
    }

}
