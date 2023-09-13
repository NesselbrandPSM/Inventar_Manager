package GUI.util;

import SQL.Statements.SQLSelectStatements;

import javax.swing.table.AbstractTableModel;

public class UserTableModell extends AbstractTableModel {
    String[] columnNames;
    Object[][] data;

    final SQLSelectStatements sqlSelectStatements;

    public UserTableModell(SQLSelectStatements sqlSelectStatements) {
        columnNames = new String[]{"Name", "Email", "Status"};
        this.sqlSelectStatements = sqlSelectStatements;
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