package GUI.util;

import Main.utility.Utils;
import SQL.SQLConnector;
import SQL.util.TableAttributes;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.util.Arrays;

public class ShowAllTableModel extends AbstractTableModel {

    String[] columnNames;
    Object[][] data;

    final SQLConnector connector;

    public ShowAllTableModel(SQLConnector connector) {
        this.connector = connector;
        columnNames = TableAttributes.toStringArray();
    }

//    public void update(int flag){
//        switch (flag){
//            case 0 -> {
//                ResultSet res;
//                ResultSet res2;
//                ResultSet res3;
//                synchronized (connector) {
//                    res = connector.query(new SQLSelect_inventory_pcStatement());
//                }
//                Object[][] d1 = Utils.convertResultSetTo2DArray(res);
//                synchronized (connector){
//                    res2 = connector.query(new SQLSelect_inventory_prStatement());
//                }
//                Object[][] d2 = Utils.convertResultSetTo2DArray(res2);
//                synchronized (connector){
//                    res3 = connector.query(new SQLSelect_inventory_scStatement());
//                }
//                Object[][] d3 = Utils.convertResultSetTo2DArray(res3);
//
//
//                Object[][] temp = new Object[d1.length + d2.length + d3.length][];
//
//                int i;
//                for (i = 0; i < d1.length; i++) {
//                    temp[i] = d1[i];
//                }
//
//                for (Object[] objects : d2) {
//                    temp[i] = objects;
//                    i++;
//                }
//
//                for (Object[] objects : d3) {
//                    temp[i] = objects;
//                    i++;
//                }
//                data = temp;
//            }
//            case 1 -> {
//                synchronized (ShowAllTableModel.class) {
//                    ResultSet res;
//                    synchronized (connector) {
//                        res = connector.query(new SQLSelect_inventory_pcStatement());
//                    }
//                    Object[][] d1 = Utils.convertResultSetTo2DArray(res);
//                    data = d1;
//                    fireTableDataChanged();
//                }
//            }
//        }
//    }

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