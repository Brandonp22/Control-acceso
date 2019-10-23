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
public class PnlNuevaAreaTrabajo extends javax.swing.JPanel {

    
    /**
     * Creates new form PnlTablaDatos
     */
    public PnlNuevaAreaTrabajo() {
        initComponents();
        
        TextPrompt ar = new TextPrompt("Nombre area de trabajo", TxtArea);
        ar.setForeground(new Color(117,117,117 ,1));
        ar.changeAlpha(0.8f);
        ar.changeStyle(Font.ITALIC);
        
        TextPrompt he = new TextPrompt("Establece hora de entrada", TxtHoraEntrada);
        he.setForeground(new Color(117,117,117 ,1));
        he.changeAlpha(0.8f);
        he.changeStyle(Font.ITALIC);
        
        TextPrompt hs = new TextPrompt("Establece hora de salida", TxtHoraSalida);
        hs.setForeground(new Color(117,117,117 ,1));
        hs.changeAlpha(0.8f);
        hs.changeStyle(Font.ITALIC);
        
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
        TxtArea = new javax.swing.JTextField();
        TxtHoraEntrada = new javax.swing.JPasswordField();
        TxtHoraSalida = new javax.swing.JTextField();
        BtnGuardar = new javax.swing.JButton();

        setBackground(new java.awt.Color(38, 50, 56));
        setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(38, 50, 56));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "REGISTRAR AREA DE TRABAJO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 0, 12), new java.awt.Color(97, 203, 255))); // NOI18N
        jPanel2.setLayout(new java.awt.GridBagLayout());

        TxtArea.setBackground(new java.awt.Color(222, 222, 222));
        TxtArea.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        TxtArea.setForeground(new java.awt.Color(0, 0, 0));
        TxtArea.setBorder(null);
        TxtArea.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        TxtArea.setName(""); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 440;
        gridBagConstraints.ipady = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(60, 20, 0, 21);
        jPanel2.add(TxtArea, gridBagConstraints);

        TxtHoraEntrada.setBackground(new java.awt.Color(222, 222, 222));
        TxtHoraEntrada.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        TxtHoraEntrada.setForeground(new java.awt.Color(0, 0, 0));
        TxtHoraEntrada.setBorder(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 440;
        gridBagConstraints.ipady = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(35, 20, 0, 21);
        jPanel2.add(TxtHoraEntrada, gridBagConstraints);

        TxtHoraSalida.setBackground(new java.awt.Color(222, 222, 222));
        TxtHoraSalida.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        TxtHoraSalida.setForeground(new java.awt.Color(0, 0, 0));
        TxtHoraSalida.setBorder(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 440;
        gridBagConstraints.ipady = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(35, 20, 0, 21);
        jPanel2.add(TxtHoraSalida, gridBagConstraints);

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
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 210, 28, 0);
        jPanel2.add(BtnGuardar, gridBagConstraints);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton BtnGuardar;
    public javax.swing.JTextField TxtArea;
    public javax.swing.JPasswordField TxtHoraEntrada;
    public javax.swing.JTextField TxtHoraSalida;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
