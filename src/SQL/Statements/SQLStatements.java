package SQL.Statements;

import GUI.util.ColumNames;
import Main.utility.Utils;
import SQL.SQLConnector;
import SQL.util.SQLStatement;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

public class SQLStatements {
    public SQLStatements(SQLConnector connector) {
        this.connector = connector;
    }

    private SQLConnector connector;

    public String[][] getAllFromPCView(int key) {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();


        ResultSet resultSet = connector.query(new SQLStatement(
                "select * from pc " +
                        "join company on pc.inventory_company_key=company.company_key " +
                        "join purchases on pc.inventory_purchase_key=purchases.purchase_key " +
                        "join pc_specs on pc.inventory_pc_specs_key=pc_specs.pc_specs_key " +
                        "join user on pc.inventory_user_key=user.user_key " +
                        "where pc.pc_key = " + key
        ));
        try {
            while (resultSet.next()) {
                resultList.add(new ArrayList<>());
                for (String s : ColumNames.allAttributesPC) {
                    if (s.equals("Primärschlüssel")){
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s, "pc"))));
                    } else {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s))));

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Utils.convertArrayList_ArrayList_StringTo2DArray(resultList);
    }

    public String[][] getAllFromPRView(int key) {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();


        ResultSet resultSet = connector.query(new SQLStatement(
                "select * from printer " +
                        "join company on printer.inventory_company_key=company.company_key " +
                        "join purchases on printer.inventory_purchase_key=purchases.purchase_key " +
                        "where printer.pr_key = " + key
        ));
        try {
            while (resultSet.next()) {
                resultList.add(new ArrayList<>());
                for (String s : ColumNames.allAttributesPR) {
                    if (s.equals("Primärschlüssel")){
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s, "pr"))));
                    } else {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s))));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Utils.convertArrayList_ArrayList_StringTo2DArray(resultList);
    }

    public String[][] getAllFromSCView(int key) {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();


        ResultSet resultSet = connector.query(new SQLStatement(
                "select * from scanner " +
                        "join company on scanner.inventory_company_key=company.company_key " +
                        "join purchases on scanner.inventory_purchase_key=purchases.purchase_key " +
                        "where scanner.sc_key = " + key
        ));
        try {
            while (resultSet.next()) {
                resultList.add(new ArrayList<>());
                for (String s : ColumNames.allAttributesSC) {
                    if (s.equals("Primärschlüssel")){
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s, "sc"))));
                    } else {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s))));

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Utils.convertArrayList_ArrayList_StringTo2DArray(resultList);
    }

    public String[][] getAllFromMOView(int key) {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();


        ResultSet resultSet = connector.query(new SQLStatement(
                "select * from monitor " +
                        "join company on monitor.inventory_company_key=company.company_key " +
                        "join purchases on monitor.inventory_purchase_key=purchases.purchase_key " +
                        "where monitor.mo_key = " + key
        ));
        try {
            while (resultSet.next()) {
                resultList.add(new ArrayList<>());
                for (String s : ColumNames.allAttributesSC) {
                    if (s.equals("Primärschlüssel")){
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s, "mo"))));
                    } else {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s))));
                        System.out.println(Arrays.toString(resultList.get(0).toArray()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Utils.convertArrayList_ArrayList_StringTo2DArray(resultList);
    }

    public String[][] getAllFromTEView(int key) {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();


        ResultSet resultSet = connector.query(new SQLStatement(
                "select * from telephone " +
                        "join company on telephone.inventory_company_key=company.company_key " +
                        "join purchases on telephone.inventory_purchase_key=purchases.purchase_key " +
                        "where telephone.te_key = " + key
        ));
        try {
            while (resultSet.next()) {
                resultList.add(new ArrayList<>());
                for (String s : ColumNames.allAttributesSC) {
                    if (s.equals("Primärschlüssel")){
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s, "te"))));
                    } else {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s))));

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Utils.convertArrayList_ArrayList_StringTo2DArray(resultList);
    }

}
