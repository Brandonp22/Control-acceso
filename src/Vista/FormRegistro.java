
package Vista;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Jaasiel Guerra
 */
public class FormRegistro extends javax.swing.JFrame {


    CambiarPanel cambiar;
    public PnlRegCredenciales registroCred;
    public PnlRegHuella registroHue;
    
    public FormRegistro() {
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Imposible modificar el tema visual", "Lookandfeel inválido.",
            JOptionPane.ERROR_MESSAGE);
        }

        
        initComponents();
        this.setSize(340, 480);
        this.setLocationRelativeTo(null);
        this.registroCred = new PnlRegCredenciales();
        this.registroHue = new PnlRegHuella();        
        cambiar = new CambiarPanel(PnlCentral, registroCred);
        
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

        PnlEncabezado.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Registro");
        PnlEncabezado.add(jLabel1, java.awt.BorderLayout.CENTER);

        PnlPrincipal.add(PnlEncabezado, java.awt.BorderLayout.PAGE_START);

        BtnSiguiente.setText("Siguiente");
        BtnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSiguienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PnlInferiorLayout = new javax.swing.GroupLayout(PnlInferior);
        PnlInferior.setLayout(PnlInferiorLayout);
        PnlInferiorLayout.setHorizontalGroup(
            PnlInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlInferiorLayout.createSequentialGroup()
                .addContainerGap(254, Short.MAX_VALUE)
                .addComponent(BtnSiguiente)
                .addContainerGap())
        );
        PnlInferiorLayout.setVerticalGroup(
            PnlInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlInferiorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BtnSiguiente)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PnlPrincipal.add(PnlInferior, java.awt.BorderLayout.PAGE_END);

        PnlCentral.setLayout(new java.awt.BorderLayout());
        PnlPrincipal.add(PnlCentral, java.awt.BorderLayout.CENTER);

        getContentPane().add(PnlPrincipal, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSiguienteActionPerformed
        
        cambiar = new CambiarPanel(PnlCentral, registroHue);
        
    }//GEN-LAST:event_BtnSiguienteActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton BtnSiguiente;
    public javax.swing.JPanel PnlCentral;
    private javax.swing.JPanel PnlEncabezado;
    public javax.swing.JPanel PnlInferior;
    private javax.swing.JPanel PnlPrincipal;
    public javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
