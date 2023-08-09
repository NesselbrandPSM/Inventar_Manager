package SQL.Statements;

import Main.utility.Utils;
import SQL.SQLConnector;
import SQL.util.SQLStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class SQLSequenzStatements {
    private SQLConnector connector;

    public SQLSequenzStatements(SQLConnector connector) {
        this.connector = connector;
    }

    public String[][] getDefaultView() {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();
        try {
            final int attributeNumber = 3; //Number of attributes to get
            //region PC
            ResultSet resultSet = connector.query(new SQLStatement("select pc.iv_number, company.company, pc.pc_key " +
                    "from company " +
                    "join pc on " +
                    "pc.inventory_company_key = company.company_key " +
                    "where pc.active = 1"));
            int i = 0;
            while (resultSet.next()){
                resultList.add(new ArrayList<>());
                for (int attr_number = 1; attr_number <= attributeNumber; attr_number++) {
                    resultList.get(i).add(String.valueOf(resultSet.getObject(attr_number)));
                }
                i++;
            }
            //endregion
            //region Printer
            resultSet = connector.query(new SQLStatement("select printer.iv_number, company.company, printer.pr_key " +
                    "from company " +
                    "join printer " +
                    "on printer.inventory_company_key = company.company_key " +
                    "where printer.active = 1"));
            while (resultSet.next()){
                resultList.add(new ArrayList<>());
                for (int attr_number = 1; attr_number <= attributeNumber; attr_number++) {
                    resultList.get(i).add(String.valueOf(resultSet.getObject(attr_number)));
                }
                i++;
            }
            //endregion
            //region Scanner
            resultSet = connector.query(new SQLStatement("select scanner.iv_number, company.company, scanner.sc_key " +
                    "from company " +
                    "join scanner " +
                    "on scanner.inventory_company_key = company.company_key " +
                    "where scanner.active = 1"));
            while (resultSet.next()){
                resultList.add(new ArrayList<>());
                for (int attr_number = 1; attr_number <= attributeNumber; attr_number++) {
                    resultList.get(i).add(String.valueOf(resultSet.getObject(attr_number)));
                }
                i++;
            }
            //endregion
            //region Monitor
            resultSet = connector.query(new SQLStatement("select monitor.iv_number, company.company, monitor.mo_key " +
                    "from company " +
                    "join monitor " +
                    "on monitor.inventory_company_key = company.company_key " +
                    "where monitor.active = 1"));
            while (resultSet.next()){
                resultList.add(new ArrayList<>());
                for (int attr_number = 1; attr_number <= attributeNumber; attr_number++) {
                    resultList.get(i).add(String.valueOf(resultSet.getObject(attr_number)));
                }
                i++;
            }
            //endregion
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Utils.convertArrayList_ArrayList_StringTo2DArray(resultList);
    }
}