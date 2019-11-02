
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
        TxtIndicador = new javax.swing.JLabel();

        setBackground(new java.awt.Color(38, 50, 56));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LblHuella.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Huella.png"))); // NOI18N
        add(LblHuella, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, -1));

        TxtNombreUsuario.setEditable(false);
        TxtNombreUsuario.setBackground(new java.awt.Color(222, 222, 222));
        TxtNombreUsuario.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        TxtNombreUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TxtNombreUsuario.setBorder(null);
        add(TxtNombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 430, 53));

        TxtIndicador.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        TxtIndicador.setForeground(new java.awt.Color(255, 255, 255));
        TxtIndicador.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TxtIndicador.setText("INDICADOR");
        add(TxtIndicador, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 430, -1));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel LblHuella;
    public javax.swing.JLabel TxtIndicador;
    public javax.swing.JTextField TxtNombreUsuario;
    // End of variables declaration//GEN-END:variables
}
