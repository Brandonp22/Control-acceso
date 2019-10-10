
package Vista;

/**
 *
 * @author Jaasiel Guerra
 */
public class PnlHuella extends javax.swing.JPanel {

    CambiarPanel cambiar;
    public PnlHuella() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(38, 50, 56));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Huella.png"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, -1, -1));

        jTextField1.setEditable(false);
        add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 262, 53));

        jTextField2.setEditable(false);
        jTextField2.setBackground(new java.awt.Color(215, 217, 227));
        add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 300, 33, -1));

        jLabel3.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Intentos :");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, 70, 20));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
