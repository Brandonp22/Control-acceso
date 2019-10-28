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
public class ControlBarraBotones extends PnlBarraBotones implements ActionListener {

    //private PnlBarraBotones botones = null;
    //private FramePrincipal ventanaMain = null;
    private CambiarPanel cambiar = null;
    private String botonPulsado;

    public ControlBarraBotones() {

        //this.botones = main.getBarraBotones();
        //this.ventanaMain = ventanaMain = main.getVentanaPrincipal();
        //this.cambiar = new CambiarPanel();
        this.btnAddUser.addActionListener(this);
        this.btnAdmin.addActionListener(this);
        this.btnAreaTrabajo.addActionListener(this);
        this.btnEmpleado.addActionListener(this);
        this.btnHistorial.addActionListener(this);
        this.btnImprimir.addActionListener(this);

        this.btnAddUser.setVisible(false);
        this.btnHistorial.setVisible(false);
        this.btnImprimir.setVisible(false);
    }

    //servira para retornar el boton que se pulso
    public String getBotonPulsado() {
        return botonPulsado;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /*
        //la funcion .equals devuelve true o false dependiendo si son o no iguales los objetos
        this.btnAddUser.setVisible(e.getSource().equals(btnEmpleado)
                || e.getSource().equals(btnAdmin) || e.getSource().equals(btnAddUser)
                || e.getSource().equals(btnImprimir));

        this.btnAddUser.setEnabled(!e.getSource().equals(btnAddUser));
        */
        
        this.btnAdmin.setEnabled(!e.getSource().equals(btnAdmin));

        this.btnAreaTrabajo.setEnabled(!e.getSource().equals(btnAreaTrabajo));

        this.btnEmpleado.setEnabled(!e.getSource().equals(btnEmpleado));

        /*
        this.btnHistorial.setVisible(e.getSource().equals(btnEmpleado)
                || e.getSource().equals(btnAdmin) || e.getSource().equals(btnImprimir));

        this.btnHistorial.setEnabled(!e.getSource().equals(btnHistorial));

        this.btnImprimir.setVisible(e.getSource().equals(btnEmpleado)
                || e.getSource().equals(btnAdmin) || e.getSource().equals(btnAreaTrabajo)
                || e.getSource().equals(btnHistorial) || e.getSource().equals(btnImprimir));
        
        */

        if (e.getSource().equals(btnEmpleado)) {
            this.botonPulsado = "Empleado";
            this.btnHistorial.setVisible(true);
            this.btnImprimir.setVisible(true);
            this.btnAddUser.setVisible(true);
        }

        if (e.getSource().equals(btnAreaTrabajo)) {
            this.btnHistorial.setVisible(false);
            this.btnImprimir.setVisible(true);
            this.btnAddUser.setVisible(false);
        }

        if (e.getSource().equals(btnAdmin)) {

            this.botonPulsado = "Admin";
            this.btnHistorial.setVisible(true);
            this.btnImprimir.setVisible(true);
            this.btnAddUser.setVisible(true);
        }

        if (e.getSource().equals(btnHistorial)) {
            this.btnHistorial.setVisible(false);
            this.btnAddUser.setVisible(false);
            
            
            if(botonPulsado.equals("Empleado"))
                botonPulsado = "EmpleadoHistorial";
            else if(botonPulsado.equals("Admin"))
                botonPulsado = "AdminHistorial";
               
        }

        if (e.getSource().equals(btnImprimir)) {
            
        }

        if (e.getSource().equals(btnAddUser)) {
            this.btnAddUser.setVisible(false);
            this.btnHistorial.setVisible(false);
            this.btnImprimir.setVisible(false);
        }

    }

}
