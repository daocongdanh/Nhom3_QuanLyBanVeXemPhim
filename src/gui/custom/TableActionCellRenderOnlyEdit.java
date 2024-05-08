/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.custom;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Hoang
 */
public class TableActionCellRenderOnlyEdit extends DefaultTableCellRenderer {

    TableActionEventOnlyEdit event;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        PanelActionOnlyEdit action = new PanelActionOnlyEdit();

        action.initEvent(event, row);
        if (isSelected == false && row % 2 == 0) {
            action.setBackground(Color.WHITE);
        } else {
            action.setBackground(new Color(242, 242, 242));
        }
        if (isSelected == true) {
            action.setBackground(new Color(54, 107, 177));
        }

        return action;
    }

}
