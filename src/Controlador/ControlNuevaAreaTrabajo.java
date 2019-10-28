/*
 * Controlador para el registro de nuevas areas de trabajo
 */
package Controlador;

import Vista.PnlNuevaAreaTrabajo;

/**
 *
 * @author Jaasiel Guerra
 */
public class ControlNuevaAreaTrabajo extends PnlNuevaAreaTrabajo{
    
    //atributos
    private ControlPrincipal controlMain = null;
    private ControlAreasTrabajo controlAreasT = null;

    public ControlNuevaAreaTrabajo(ControlPrincipal main,ControlAreasTrabajo controlAT) {
        
        this.controlMain = main;
        this.controlAreasT = controlAT;
        
    }
    
    
    
    
    
}
