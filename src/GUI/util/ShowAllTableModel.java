package GUI.util;

import SQL.SQLConnector;
import SQL.Statements.SQLSequenzStatements;

import javax.swing.table.AbstractTableModel;

public class ShowAllTableModel extends AbstractTableModel {

    //TODO array in dem die primary keys mit passendem index zu data + welche tabelle für den zugriff falls man ein element auswählen will
    String[] columnNames;
    Object[][] data;

    final SQLConnector connector;
    final SQLSequenzStatements sqlSequenzStatements;

    public ShowAllTableModel(SQLConnector connector, SQLSequenzStatements sqlSequenzStatements) {
        this.connector = connector;
        this.sqlSequenzStatements = sqlSequenzStatements;
        columnNames = ColumNames.columnNamesStandardView;
        data = sqlSequenzStatements.getDefaultView();
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