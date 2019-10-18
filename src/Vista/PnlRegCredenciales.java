
package Vista;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Jaasiel Guerra
 */
public class PnlRegCredenciales extends javax.swing.JPanel {

    public PnlRegCredenciales() {
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        TxtNombre = new javax.swing.JTextField();
        TxtApellido = new javax.swing.JTextField();
        IntDPI = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        TxtUsuario = new javax.swing.JTextField();
        TxtPassword = new javax.swing.JPasswordField();

        setBackground(new java.awt.Color(38, 50, 56));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(38, 50, 56));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 12), new java.awt.Color(97, 203, 255))); // NOI18N

        TxtNombre.setBackground(new java.awt.Color(215, 217, 227));
        TxtNombre.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        TxtNombre.setBorder(null);

        TxtApellido.setBackground(new java.awt.Color(215, 217, 227));
        TxtApellido.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        TxtApellido.setBorder(null);

        IntDPI.setBackground(new java.awt.Color(215, 217, 227));
        IntDPI.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        IntDPI.setBorder(null);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(IntDPI, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                    .addComponent(TxtNombre)
                    .addComponent(TxtApellido))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(TxtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(IntDPI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 460, 230));

        jPanel2.setBackground(new java.awt.Color(38, 50, 56));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de Usuario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 12), new java.awt.Color(97, 203, 255))); // NOI18N

        TxtUsuario.setBackground(new java.awt.Color(215, 217, 227));
        TxtUsuario.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        TxtUsuario.setBorder(null);

        TxtPassword.setBackground(new java.awt.Color(215, 217, 227));
        TxtPassword.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        TxtPassword.setBorder(null);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TxtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                    .addComponent(TxtUsuario))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(TxtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(TxtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 460, 170));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField IntDPI;
    public javax.swing.JTextField TxtApellido;
    public javax.swing.JTextField TxtNombre;
    public javax.swing.JPasswordField TxtPassword;
    public javax.swing.JTextField TxtUsuario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
