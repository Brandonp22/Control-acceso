/*
 * Clase encargada para cambiar de paneles
 */
package Vista;

import javax.swing.JPanel;


public class CambiarPanel {
    private JPanel PnlDestino;
    private JPanel Contenedor;

    //Constructor recibe panel de destino y panel a colocar en el panel de destino
    public CambiarPanel(JPanel PnlDestino, JPanel Contenedor) {
        this.PnlDestino = PnlDestino;
        this.Contenedor = Contenedor;
        this.PnlDestino.removeAll();
        this.PnlDestino.revalidate();
        this.PnlDestino.repaint();        
        this.PnlDestino.add(this.Contenedor);
        this.PnlDestino.revalidate();
        this.PnlDestino.repaint();
    }
    
    
}
