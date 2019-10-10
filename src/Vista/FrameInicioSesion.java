
package Vista;

import com.sun.awt.AWTUtilities;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Jaasiel Guerra
 */
public class FrameInicioSesion extends javax.swing.JFrame {

    CambiarPanel cambiar;
    public PnlCredenciales credenciales;
    public PnlHuella huella;
    boolean pulso = true;
    
    
    public FrameInicioSesion() {
         try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Imposible modificar el tema visual", "Lookandfeel inválido.",
            JOptionPane.ERROR_MESSAGE);
        }
       
        this.setUndecorated(true);
        initComponents();
        this.setSize(480, 540);
        this.setLocationRelativeTo(null);
        this.credenciales = new PnlCredenciales();
        this.huella = new PnlHuella();       
        cambiar = new CambiarPanel(this.PnlCentral, huella);
        
        this.setBackground(new Color(38,50,56,1));
        this.setOpacity(0.96f);
        this.setLocationRelativeTo(null);
        Shape forma = new RoundRectangle2D.Double(0, 0, this.getBounds().width, this.getBounds().height, 27, 27);
        AWTUtilities.setWindowShape(this, forma);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelPrincipal = new javax.swing.JPanel();
        Encabezado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PnlCentral = new javax.swing.JPanel();
        PnlInferior = new javax.swing.JPanel();
        btnAccesoAlter = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        PanelPrincipal.setBackground(new java.awt.Color(38, 50, 56));
        PanelPrincipal.setLayout(new java.awt.BorderLayout());

        Encabezado.setBackground(new java.awt.Color(38, 50, 56));
        Encabezado.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/LogoControlAcceso.png"))); // NOI18N
        Encabezado.add(jLabel1, java.awt.BorderLayout.CENTER);

        PanelPrincipal.add(Encabezado, java.awt.BorderLayout.PAGE_START);

        PnlCentral.setBackground(new java.awt.Color(38, 50, 56));
        PnlCentral.setLayout(new java.awt.BorderLayout());
        PanelPrincipal.add(PnlCentral, java.awt.BorderLayout.CENTER);

        PnlInferior.setBackground(new java.awt.Color(38, 50, 56));

        btnAccesoAlter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BtnAccesoAlternativo2.png"))); // NOI18N
        btnAccesoAlter.setBorder(null);
        btnAccesoAlter.setBorderPainted(false);
        btnAccesoAlter.setContentAreaFilled(false);
        btnAccesoAlter.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAccesoAlter.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BtnAccesoAlternativo3.png"))); // NOI18N
        btnAccesoAlter.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BtnAccesoAlternativo1.png"))); // NOI18N
        btnAccesoAlter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccesoAlterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PnlInferiorLayout = new javax.swing.GroupLayout(PnlInferior);
        PnlInferior.setLayout(PnlInferiorLayout);
        PnlInferiorLayout.setHorizontalGroup(
            PnlInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlInferiorLayout.createSequentialGroup()
                .addContainerGap(339, Short.MAX_VALUE)
                .addComponent(btnAccesoAlter)
                .addContainerGap())
        );
        PnlInferiorLayout.setVerticalGroup(
            PnlInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlInferiorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAccesoAlter)
                .addContainerGap())
        );

        PanelPrincipal.add(PnlInferior, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(PanelPrincipal, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAccesoAlterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccesoAlterActionPerformed
        //boton acceso alternativo
        
        if(pulso){
            cambiar = new CambiarPanel(this.PnlCentral, credenciales);
            //this.btnAccesoAlter.setText("<= Regresar");
            pulso = false;
        }else{
            cambiar = new CambiarPanel(this.PnlCentral, huella);
            //this.btnAccesoAlter.setText("Acceso Alternativo");
            pulso = true;
        }
        
    }//GEN-LAST:event_btnAccesoAlterActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Encabezado;
    private javax.swing.JPanel PanelPrincipal;
    private javax.swing.JPanel PnlCentral;
    private javax.swing.JPanel PnlInferior;
    private javax.swing.JButton btnAccesoAlter;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
