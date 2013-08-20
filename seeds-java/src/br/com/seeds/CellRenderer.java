package br.com.seeds;


import javax.swing.table.TableCellRenderer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Guilherme
 */
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

 public class CellRenderer extends DefaultTableCellRenderer {

     public CellRenderer() {
         super();
     }

     public Component getTableCellRendererComponent(JTable table, Object value,
             boolean isSelected, boolean hasFocus, int row, int column) {
         this.setHorizontalAlignment(CENTER);

         return super.getTableCellRendererComponent(table, value, isSelected,
                 hasFocus, row, column);

     }}