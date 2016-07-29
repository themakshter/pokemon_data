package com.happel.pokemon;

import javax.swing.table.AbstractTableModel;

/**
 * Created by happel on 7/29/2016.
 */
public class UpdatableTableModel extends AbstractTableModel {
    private String[] columnNames;
    private Object[][] rowData;

    public UpdatableTableModel(String[] columnNames, Object[][] rowData) {
        this.columnNames = columnNames;
        this.rowData = rowData;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public int getRowCount() {
        return rowData.length;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int row, int col) {
        return rowData[row][col];
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setValueAt(Object value, int row, int col) {
        rowData[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    public void updateData(Object[][] rowData) {
        this.rowData = rowData;
        fireTableDataChanged();
    }
}
