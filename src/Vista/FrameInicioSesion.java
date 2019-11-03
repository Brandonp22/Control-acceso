
package Vista;

import com.sun.awt.AWTUtilities;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author Jaasiel Guerra
 */
public class FrameInicioSesion extends javax.swing.JFrame {

    
    public FrameInicioSesion() {
                
        this.setUndecorated(true);
        initComponents();
        this.setSize(480, 540);
        
        this.setBackground(new Color(38,50,56,1));
        this.setOpacity(0.96f);
        
        Shape forma = new RoundRectangle2D.Double(0, 0, this.getBounds().width, this.getBounds().height, 27, 27);
        AWTUtilities.setWindowShape(this, forma);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BtnGrupo = new javax.swing.ButtonGroup();
        PanelPrincipal = new javax.swing.JPanel();
        Encabezado = new javax.swing.JPanel();
        PanelMinCerrar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PnlCentral = new javax.swing.JPanel();
        PnlInferior = new javax.swing.JPanel();
        btnAccesoAlter = new javax.swing.JButton();
        BtnAccesoSistema = new javax.swing.JButton();
        RBtnMarcarIO = new javax.swing.JRadioButton();
        RBtnAccesoSistema = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        PanelPrincipal.setBackground(new java.awt.Color(38, 50, 56));
        PanelPrincipal.setLayout(new java.awt.BorderLayout());

        Encabezado.setBackground(new java.awt.Color(38, 50, 56));
        Encabezado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelMinCerrar.setBackground(new java.awt.Color(38, 50, 56));
        PanelMinCerrar.setLayout(new java.awt.BorderLayout());
        Encabezado.add(PanelMinCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 100, 25));

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/LogoControlAcceso.png"))); // NOI18N
        Encabezado.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, -1));

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
        btnAccesoAlter.setFocusable(false);
        btnAccesoAlter.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BtnAccesoAlternativo3.png"))); // NOI18N
        btnAccesoAlter.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BtnAccesoAlternativo1.png"))); // NOI18N

        BtnAccesoSistema.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BtnAccesoAlSistema2.png"))); // NOI18N
        BtnAccesoSistema.setBorder(null);
        BtnAccesoSistema.setBorderPainted(false);
        BtnAccesoSistema.setContentAreaFilled(false);
        BtnAccesoSistema.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnAccesoSistema.setFocusable(false);
        BtnAccesoSistema.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BtnAccesoAlSistema3.png"))); // NOI18N
        BtnAccesoSistema.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BtnAccesoAlSistema1.png"))); // NOI18N

        RBtnMarcarIO.setBackground(new java.awt.Color(38, 50, 56));
        RBtnMarcarIO.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        RBtnMarcarIO.setForeground(new java.awt.Color(255, 255, 255));
        RBtnMarcarIO.setText("Marcar I/O");

        RBtnAccesoSistema.setBackground(new java.awt.Color(38, 50, 56));
        RBtnAccesoSistema.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        RBtnAccesoSistema.setForeground(new java.awt.Color(255, 255, 255));
        RBtnAccesoSistema.setText("Acceso al Sistema");

        javax.swing.GroupLayout PnlInferiorLayout = new javax.swing.GroupLayout(PnlInferior);
        PnlInferior.setLayout(PnlInferiorLayout);
        PnlInferiorLayout.setHorizontalGroup(
            PnlInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlInferiorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PnlInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RBtnAccesoSistema)
                    .addComponent(RBtnMarcarIO))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(BtnAccesoSistema)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAccesoAlter)
                .addContainerGap())
        );
        PnlInferiorLayout.setVerticalGroup(
            PnlInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlInferiorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PnlInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(BtnAccesoSistema, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAccesoAlter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(PnlInferiorLayout.createSequentialGroup()
                .addComponent(RBtnMarcarIO)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(RBtnAccesoSistema)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        PanelPrincipal.add(PnlInferior, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(PanelPrincipal, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton BtnAccesoSistema;
    public javax.swing.ButtonGroup BtnGrupo;
    private javax.swing.JPanel Encabezado;
    public javax.swing.JPanel PanelMinCerrar;
    private javax.swing.JPanel PanelPrincipal;
    public javax.swing.JPanel PnlCentral;
    private javax.swing.JPanel PnlInferior;
    public javax.swing.JRadioButton RBtnAccesoSistema;
    public javax.swing.JRadioButton RBtnMarcarIO;
    public javax.swing.JButton btnAccesoAlter;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
