
package Vista;

/**
 *
 * @author Jaasiel Guerra
 */
public class PnlRegHuella extends javax.swing.JPanel {

    public PnlRegHuella() {
        initComponents();
       
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LblPasos = new javax.swing.JLabel();
        LblHuella = new javax.swing.JLabel();

        setBackground(new java.awt.Color(38, 50, 56));
        setLayout(new java.awt.BorderLayout());

        LblPasos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LblPasos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Paso0.png"))); // NOI18N
        add(LblPasos, java.awt.BorderLayout.PAGE_START);

        LblHuella.setBackground(new java.awt.Color(38, 50, 56));
        LblHuella.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LblHuella.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/LectorConectado.png"))); // NOI18N
        add(LblHuella, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel LblHuella;
    public javax.swing.JLabel LblPasos;
    // End of variables declaration//GEN-END:variables
}
