package GUI.util;

import Main.Main;
import SQL.SQLConnector;
import SQL.Statements.SQLSequenzStatements;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;

public class ShowAllTableModel extends AbstractTableModel {

    String[] columnNames;
    Object[][] data;

    final SQLConnector connector;
    final SQLSequenzStatements sqlSequenzStatements;

    public ShowAllTableModel(SQLConnector connector, SQLSequenzStatements sqlSequenzStatements) {
        this.connector = connector;
        this.sqlSequenzStatements = sqlSequenzStatements;
        columnNames = ColumNames.columnNamesStandardView;
        initData();
    }

    private void initData() {
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