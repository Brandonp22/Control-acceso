
package Vista;

/**
 *
 * @author Jaasiel Guerra
 */
public class PnlRegCredenciales extends javax.swing.JPanel {

    public PnlRegCredenciales() {
        initComponents();  
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        TxtNombre = new javax.swing.JTextField();
        TxtApellido = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        IntDPI = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        TxtUsuario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        TxtContra = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(38, 50, 56));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Nombre :");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, 30));

        TxtNombre.setBackground(new java.awt.Color(215, 217, 227));
        TxtNombre.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        TxtNombre.setForeground(new java.awt.Color(0, 0, 0));
        add(TxtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 220, 30));

        TxtApellido.setBackground(new java.awt.Color(215, 217, 227));
        TxtApellido.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        TxtApellido.setForeground(new java.awt.Color(0, 0, 0));
        add(TxtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 220, 30));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Apellido :");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, 30));

        IntDPI.setBackground(new java.awt.Color(215, 217, 227));
        IntDPI.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        IntDPI.setForeground(new java.awt.Color(0, 0, 0));
        add(IntDPI, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 220, 30));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("DPI :");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, -1, 30));

        TxtUsuario.setBackground(new java.awt.Color(215, 217, 227));
        TxtUsuario.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        TxtUsuario.setForeground(new java.awt.Color(0, 0, 0));
        add(TxtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 220, 30));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Usuario :");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 60, 30));

        TxtContra.setBackground(new java.awt.Color(215, 217, 227));
        TxtContra.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        TxtContra.setForeground(new java.awt.Color(0, 0, 0));
        TxtContra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtContraActionPerformed(evt);
            }
        });
        add(TxtContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 220, 30));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Contraseña :");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, 30));
    }// </editor-fold>//GEN-END:initComponents

    private void TxtContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtContraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtContraActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField IntDPI;
    public javax.swing.JTextField TxtApellido;
    public javax.swing.JTextField TxtContra;
    public javax.swing.JTextField TxtNombre;
    public javax.swing.JTextField TxtUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
