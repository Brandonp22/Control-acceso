
package Vista;

public class PnlCredenciales extends javax.swing.JPanel {

    public PnlCredenciales() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        BtbEntrar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtUser = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtPassword = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(38, 50, 56));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("INGRESE SUS CREDENCIALES");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, -1, -1));

        BtbEntrar.setBackground(new java.awt.Color(33, 33, 33));
        BtbEntrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BtnIniciarSesion2.png"))); // NOI18N
        BtbEntrar.setBorder(null);
        BtbEntrar.setBorderPainted(false);
        BtbEntrar.setContentAreaFilled(false);
        BtbEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtbEntrar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BtnIniciarSesion3.png"))); // NOI18N
        BtbEntrar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BtnIniciarSesion1.png"))); // NOI18N
        BtbEntrar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BtnIniciarSesion1.png"))); // NOI18N
        add(BtbEntrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 190, 30));

        jPanel1.setBackground(new java.awt.Color(215, 217, 227));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtUser.setBackground(new java.awt.Color(215, 217, 227));
        txtUser.setBorder(null);
        txtUser.setOpaque(false);
        jPanel1.add(txtUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 160, 30));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/User.png"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 30, 30));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 190, 30));

        jPanel2.setBackground(new java.awt.Color(215, 217, 227));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtPassword.setBackground(new java.awt.Color(215, 217, 227));
        txtPassword.setBorder(null);
        txtPassword.setOpaque(false);
        jPanel2.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 160, 30));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Pass.png"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 30, 30));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 190, 30));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton BtbEntrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JPasswordField txtPassword;
    public javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
