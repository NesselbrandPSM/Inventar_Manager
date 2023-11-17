package GUI.util;

import SQL.Statements.SQLSelectStatements;

import javax.swing.table.AbstractTableModel;

public class ShowAllTableModel extends AbstractTableModel {

    String[] columnNames;
    Object[][] data;

    final SQLSelectStatements sqlSelectStatements;

    public ShowAllTableModel(SQLSelectStatements sqlSelectStatements) {
        this.sqlSelectStatements = sqlSelectStatements;
        columnNames = ColumNames.columnNamesStandardView;
        data = sqlSelectStatements.getDefaultView();
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
        try {
            return data[rowIndex][columnIndex];
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }


}