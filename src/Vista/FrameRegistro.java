
package Vista;

import com.sun.awt.AWTUtilities;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;


/**
 *
 * @author Jaasiel Guerra
 */
public class FrameRegistro extends javax.swing.JFrame {


    
    
    public FrameRegistro() {
        
        
        
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

        PnlPrincipal = new javax.swing.JPanel();
        PnlEncabezado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PnlInferior = new javax.swing.JPanel();
        BtnSiguiente = new javax.swing.JButton();
        PnlCentral = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        PnlPrincipal.setLayout(new java.awt.BorderLayout());

        PnlEncabezado.setBackground(new java.awt.Color(38, 50, 56));
        PnlEncabezado.setLayout(new java.awt.BorderLayout());

        jLabel1.setBackground(new java.awt.Color(38, 50, 56));
        jLabel1.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/LogoRegistro.png"))); // NOI18N
        PnlEncabezado.add(jLabel1, java.awt.BorderLayout.CENTER);

        PnlPrincipal.add(PnlEncabezado, java.awt.BorderLayout.PAGE_START);

        PnlInferior.setBackground(new java.awt.Color(38, 50, 56));

        BtnSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BtnSiguiente2.png"))); // NOI18N
        BtnSiguiente.setBorder(null);
        BtnSiguiente.setBorderPainted(false);
        BtnSiguiente.setContentAreaFilled(false);
        BtnSiguiente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnSiguiente.setFocusable(false);
        BtnSiguiente.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BtnSiguiente3.png"))); // NOI18N
        BtnSiguiente.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BtnSiguiente1.png"))); // NOI18N

        javax.swing.GroupLayout PnlInferiorLayout = new javax.swing.GroupLayout(PnlInferior);
        PnlInferior.setLayout(PnlInferiorLayout);
        PnlInferiorLayout.setHorizontalGroup(
            PnlInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlInferiorLayout.createSequentialGroup()
                .addContainerGap(375, Short.MAX_VALUE)
                .addComponent(BtnSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PnlInferiorLayout.setVerticalGroup(
            PnlInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlInferiorLayout.createSequentialGroup()
                .addComponent(BtnSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        PnlPrincipal.add(PnlInferior, java.awt.BorderLayout.PAGE_END);

        PnlCentral.setBackground(new java.awt.Color(38, 50, 56));
        PnlCentral.setLayout(new java.awt.BorderLayout());
        PnlPrincipal.add(PnlCentral, java.awt.BorderLayout.CENTER);

        getContentPane().add(PnlPrincipal, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents



    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton BtnSiguiente;
    public javax.swing.JPanel PnlCentral;
    private javax.swing.JPanel PnlEncabezado;
    public javax.swing.JPanel PnlInferior;
    private javax.swing.JPanel PnlPrincipal;
    public javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
