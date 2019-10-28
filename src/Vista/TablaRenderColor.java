/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.Color;
import java.awt.Component;
import java.util.Enumeration;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author brand
 */
public class TablaRenderColor extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        //Constructor de la clase DefaultTableCellRenderer
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        //Establecemos las filas que queremos cambiar el color. == 0 para pares y != 0 para impares
        boolean oddRow = (row % 2 == 0);

        //Si las filas son pares, se cambia el color a gris
        if (oddRow) {
            setBackground(new Color(61, 61, 61));
            setForeground(new Color(255, 255, 255));
        } else {
            setBackground(new Color(75, 75, 75));
            setForeground(new Color(255, 255, 255));
        }
        
        //si esta seleccionada
        if(isSelected){
            setBackground(new Color(97,203,255));
            setForeground(new Color(0, 0, 0));
        }
        return this;
    }

    public void setCellRender(JTable table) {

        Enumeration<TableColumn> en = table.getColumnModel().getColumns();
        while (en.hasMoreElements()) {
            TableColumn tc = en.nextElement();
            tc.setCellRenderer(new TablaRenderColor());
        }
    }

}
