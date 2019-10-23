/*
 * Clase encargada para cambiar de paneles
 */
package Controlador;

import javax.swing.JPanel;

public class CambiarPanel {

    private JPanel PnlContenedor;
    private JPanel PnlInsertar;

    //Constructor 
    public CambiarPanel() {

    }

    //recibe panel de destino y panel a colocar en el panel de destino
    public void cambiarPNL(JPanel PnlContenedor, JPanel PnlInsertar) {
        this.PnlContenedor = PnlContenedor;
        this.PnlInsertar = PnlInsertar;
        this.PnlContenedor.removeAll();
        this.PnlContenedor.revalidate();
        this.PnlContenedor.repaint();
        this.PnlContenedor.add(this.PnlInsertar);
        this.PnlContenedor.revalidate();
        this.PnlContenedor.repaint();
    }

}
