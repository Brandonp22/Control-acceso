
package Vista;

/**
 *
 * @author Jaasiel Guerra
 */
public class FormInicioSesion extends javax.swing.JFrame {

    CambiarPanel cambiar;
    public PnlCredenciales credenciales;
    public PnlHuella huella;
    boolean pulso = true;
    
    
    public FormInicioSesion() {
        initComponents();
        
        
        /*Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        
        int x = (dim.width/8)*2;
        int y = (dim.height/8)*5;
        
        System.out.println("Tamanio Login: "+x+ "," + y);*/
        
        this.setSize(340,480);
        this.setLocationRelativeTo(null);
        this.credenciales = new PnlCredenciales();
        this.huella = new PnlHuella();
        cambiar = new CambiarPanel(this.Contenedor, huella);//cambiar panel
        
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelPrincipal = new javax.swing.JPanel();
        Encabezado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Contenedor = new javax.swing.JPanel();
        PnlInferior = new javax.swing.JPanel();
        btnAccesoAlter = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        PanelPrincipal.setLayout(new java.awt.BorderLayout());

        Encabezado.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Control de Acceso");
        Encabezado.add(jLabel1, java.awt.BorderLayout.CENTER);

        PanelPrincipal.add(Encabezado, java.awt.BorderLayout.PAGE_START);

        Contenedor.setLayout(new java.awt.BorderLayout());
        PanelPrincipal.add(Contenedor, java.awt.BorderLayout.CENTER);

        btnAccesoAlter.setText("Acceso Alternativo");
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
                .addContainerGap(207, Short.MAX_VALUE)
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
            cambiar = new CambiarPanel(this.Contenedor, credenciales);
            this.btnAccesoAlter.setText("<= Regresar");
            pulso = false;
        }else{
            cambiar = new CambiarPanel(this.Contenedor, huella);
            this.btnAccesoAlter.setText("Acceso Alternativo");
            pulso = true;
        }
        
    }//GEN-LAST:event_btnAccesoAlterActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Contenedor;
    private javax.swing.JPanel Encabezado;
    private javax.swing.JPanel PanelPrincipal;
    private javax.swing.JPanel PnlInferior;
    private javax.swing.JButton btnAccesoAlter;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
