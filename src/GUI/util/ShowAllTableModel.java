package GUI.util;

import SQL.SQLConnector;
import SQL.util.TableAttributes;

import javax.swing.table.AbstractTableModel;

public class ShowAllTableModel extends AbstractTableModel {

    String[] columnNames;
    Object[][] data;

    final SQLConnector connector;

    public ShowAllTableModel(SQLConnector connector) {
        this.connector = connector;
        columnNames = TableAttributes.toStringArray();
    }


    public void update(String[][] data){
        this.data = data;
        fireTableDataChanged();
    }

    public Object[] getRow(int index){
        return data[index];
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }


}