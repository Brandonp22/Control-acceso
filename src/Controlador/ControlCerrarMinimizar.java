/*
 * se encarga del control para cerrar o minizar la aplicacion
 */
package Controlador;

import Vista.PnlMinCerrar;
import static java.awt.Frame.ICONIFIED;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Jaasiel Guerra
 */
public class ControlCerrarMinimizar implements ActionListener {

    private JFrame ventana = null;
    private PnlMinCerrar pnlMC = null;

    public ControlCerrarMinimizar(JFrame vent) {

        this.ventana = vent;
        this.pnlMC = new PnlMinCerrar();
        this.pnlMC.BtnCerrar.addActionListener(this);
        this.pnlMC.BtnMinimizar.addActionListener(this);
    }

    public PnlMinCerrar getPnlMC() {
        return pnlMC;
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(pnlMC.BtnMinimizar)) {

            this.ventana.setExtendedState(ventana.ICONIFIED);

        }

        if (e.getSource().equals(pnlMC.BtnCerrar)) {

            int option = JOptionPane.showConfirmDialog(
                    ventana,
                    "¿Está seguro de que quiere cerrar la aplicación?",
                    "Confirmación de cierre",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            
            if (option == JOptionPane.YES_OPTION) {
                System.exit(0);

            }
        }

    }
    
    
    
}
