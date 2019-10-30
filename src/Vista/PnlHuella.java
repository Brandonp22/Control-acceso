
package Vista;

import Controlador.CambiarPanel;

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

        LblHuella = new javax.swing.JLabel();
        TxtNombreUsuario = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(38, 50, 56));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LblHuella.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Huella.png"))); // NOI18N
        add(LblHuella, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, -1));

        TxtNombreUsuario.setEditable(false);
        TxtNombreUsuario.setBackground(new java.awt.Color(222, 222, 222));
        TxtNombreUsuario.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        TxtNombreUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TxtNombreUsuario.setBorder(null);
        add(TxtNombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 262, 53));

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("INDICADOR");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 260, 80, -1));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel LblHuella;
    public javax.swing.JTextField TxtNombreUsuario;
    public javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
