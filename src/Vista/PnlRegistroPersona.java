/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Vista.TextPrompt;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author brand
 */
public class PnlRegistroPersona extends javax.swing.JPanel {

    
    /**
     * Creates new form PnlTablaDatos
     */
    public PnlRegistroPersona() {
        initComponents();
        
        TextPrompt nombre = new TextPrompt("Nombre", TxtNombre);
        nombre.setForeground(new Color(117,117,117 ,1));
        nombre.changeAlpha(0.8f);
        nombre.changeStyle(Font.ITALIC);
        
        TextPrompt apellido = new TextPrompt("Apellido", TxtApellido);
        apellido.setForeground(new Color(117,117,117 ,1));
        apellido.changeAlpha(0.8f);
        apellido.changeStyle(Font.ITALIC);
        
        TextPrompt dpi = new TextPrompt("DPI", IntDPI);
        dpi.setForeground(new Color(117,117,117 ,1));
        dpi.changeAlpha(0.8f);
        dpi.changeStyle(Font.ITALIC);
        
        TextPrompt user = new TextPrompt("Usuario", TxtUsuario);
        user.setForeground(new Color(117,117,117 ,1));
        user.changeAlpha(0.8f);
        user.changeStyle(Font.ITALIC);
        
        TextPrompt pass = new TextPrompt("Contraseña", TxtPassword);
        pass.setForeground(new Color(117,117,117 ,1));
        pass.changeAlpha(0.8f);
        pass.changeStyle(Font.ITALIC);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        TxtUsuario = new javax.swing.JTextField();
        TxtPassword = new javax.swing.JPasswordField();
        TxtNombre = new javax.swing.JTextField();
        TxtApellido = new javax.swing.JTextField();
        IntDPI = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        CheckAdmin = new javax.swing.JCheckBox();
        BtnGuardar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setBackground(new java.awt.Color(38, 50, 56));
        setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(38, 50, 56));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "REGISTRAR EMPLEADO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 0, 12), new java.awt.Color(97, 203, 255))); // NOI18N
        jPanel2.setLayout(new java.awt.GridBagLayout());

        TxtUsuario.setBackground(new java.awt.Color(222, 222, 222));
        TxtUsuario.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        TxtUsuario.setForeground(new java.awt.Color(0, 0, 0));
        TxtUsuario.setBorder(null);
        TxtUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        TxtUsuario.setName(""); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 440;
        gridBagConstraints.ipady = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 11);
        jPanel2.add(TxtUsuario, gridBagConstraints);

        TxtPassword.setBackground(new java.awt.Color(222, 222, 222));
        TxtPassword.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        TxtPassword.setForeground(new java.awt.Color(0, 0, 0));
        TxtPassword.setBorder(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 440;
        gridBagConstraints.ipady = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 20, 0, 11);
        jPanel2.add(TxtPassword, gridBagConstraints);

        TxtNombre.setBackground(new java.awt.Color(222, 222, 222));
        TxtNombre.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        TxtNombre.setForeground(new java.awt.Color(0, 0, 0));
        TxtNombre.setBorder(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 440;
        gridBagConstraints.ipady = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 20, 0, 11);
        jPanel2.add(TxtNombre, gridBagConstraints);

        TxtApellido.setBackground(new java.awt.Color(222, 222, 222));
        TxtApellido.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        TxtApellido.setForeground(new java.awt.Color(0, 0, 0));
        TxtApellido.setBorder(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 440;
        gridBagConstraints.ipady = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 20, 0, 11);
        jPanel2.add(TxtApellido, gridBagConstraints);

        IntDPI.setBackground(new java.awt.Color(222, 222, 222));
        IntDPI.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        IntDPI.setForeground(new java.awt.Color(0, 0, 0));
        IntDPI.setBorder(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 440;
        gridBagConstraints.ipady = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 20, 0, 11);
        jPanel2.add(IntDPI, gridBagConstraints);

        jComboBox1.setBackground(new java.awt.Color(222, 222, 222));
        jComboBox1.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AREA DE TRABAJO" }));
        jComboBox1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 287;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 20, 0, 11);
        jPanel2.add(jComboBox1, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("¿Es Administrador?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 0, 0);
        jPanel2.add(jLabel1, gridBagConstraints);

        CheckAdmin.setBackground(new java.awt.Color(38, 50, 56));
        CheckAdmin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CheckAdmin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 20, 0, 0);
        jPanel2.add(CheckAdmin, gridBagConstraints);

        BtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BtnGuardar2.png"))); // NOI18N
        BtnGuardar.setToolTipText("Guardar");
        BtnGuardar.setBorder(null);
        BtnGuardar.setBorderPainted(false);
        BtnGuardar.setContentAreaFilled(false);
        BtnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnGuardar.setFocusable(false);
        BtnGuardar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BtnGuardar3.png"))); // NOI18N
        BtnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BtnGuardar1.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 170, 2, 0);
        jPanel2.add(BtnGuardar, gridBagConstraints);

        jButton1.setText("FOTO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 138;
        gridBagConstraints.ipady = 105;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 0);
        jPanel2.add(jButton1, gridBagConstraints);

        jButton2.setText("HUELLA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 126;
        gridBagConstraints.ipady = 105;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 40, 0, 11);
        jPanel2.add(jButton2, gridBagConstraints);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton BtnGuardar;
    public javax.swing.JCheckBox CheckAdmin;
    public javax.swing.JTextField IntDPI;
    public javax.swing.JTextField TxtApellido;
    public javax.swing.JTextField TxtNombre;
    public javax.swing.JPasswordField TxtPassword;
    public javax.swing.JTextField TxtUsuario;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JComboBox<String> jComboBox1;
    public javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
