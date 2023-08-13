package SQL.Statements;

import GUI.util.ColumNames;
import Main.utility.Utils;
import SQL.SQLConnector;
import SQL.util.SQLStatement;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class SQLSelectStatements {
    public SQLSelectStatements(SQLConnector connector) {
        this.connector = connector;
    }

    private SQLConnector connector;

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
            while (resultSet.next()) {
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
            while (resultSet.next()) {
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
            while (resultSet.next()) {
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
            while (resultSet.next()) {
                resultList.add(new ArrayList<>());
                for (int attr_number = 1; attr_number <= attributeNumber; attr_number++) {
                    resultList.get(i).add(String.valueOf(resultSet.getObject(attr_number)));
                }
                i++;
            }
            //endregion
            //region Telephone
            resultSet = connector.query(new SQLStatement("select telephone.iv_number, company.company, telephone.te_key " +
                    "from company " +
                    "join telephone " +
                    "on telephone.inventory_company_key = company.company_key " +
                    "where telephone.active = 1"));
            while (resultSet.next()) {
                resultList.add(new ArrayList<>());
                for (int attr_number = 1; attr_number <= attributeNumber; attr_number++) {
                    resultList.get(i).add(String.valueOf(resultSet.getObject(attr_number)));
                }
                i++;
            }
            //endregion
            //region Headset
            resultSet = connector.query(new SQLStatement("select headset.iv_number, company.company, headset.hd_key " +
                    "from company " +
                    "join headset " +
                    "on headset.inventory_company_key = company.company_key " +
                    "where headset.active = 1"));
            while (resultSet.next()) {
                resultList.add(new ArrayList<>());
                for (int attr_number = 1; attr_number <= attributeNumber; attr_number++) {
                    resultList.get(i).add(String.valueOf(resultSet.getObject(attr_number)));
                }
                i++;
            }
            //endregion
            //region Dockingstation
            resultSet = connector.query(new SQLStatement("select dockingstation.iv_number, company.company, dockingstation.ds_key " +
                    "from company " +
                    "join dockingstation " +
                    "on dockingstation.inventory_company_key = company.company_key " +
                    "where dockingstation.active = 1"));
            while (resultSet.next()) {
                resultList.add(new ArrayList<>());
                for (int attr_number = 1; attr_number <= attributeNumber; attr_number++) {
                    resultList.get(i).add(String.valueOf(resultSet.getObject(attr_number)));
                }
                i++;
            }
            //endregion
            // region Desk
            resultSet = connector.query(new SQLStatement("select desk.iv_number, company.company, desk.dk_key " +
                    "from company " +
                    "join desk " +
                    "on desk.inventory_company_key = company.company_key " +
                    "where desk.active = 1"));
            while (resultSet.next()) {
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

    public String[][] getSelectViewIV_Number(String iv_number) {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();
        String[] columnList = new String[]{"Inventar Nummer", "Firma", "Primärschlüssel"};

        ResultSet resultSet = null;
        try {
            switch (iv_number.substring(0, 2).toLowerCase()) {
                case "pc" -> resultSet = connector.query(new SQLStatement(
                        "select * from pc " +
                                "join company on pc.inventory_company_key=company.company_key " +
                                "join purchases on pc.inventory_purchase_key=purchases.purchase_key " +
                                "join user on pc.inventory_user_key=user.user_key " +
                                "where pc.iv_number = \"" + iv_number + "\""));
                case "pr" -> resultSet = connector.query(new SQLStatement(
                        "select * from printer " +
                                "join company on printer.inventory_company_key=company.company_key " +
                                "join purchases on printer.inventory_purchase_key=purchases.purchase_key " +
                                "where printer.iv_number = \"" + iv_number + "\""));
                case "sc" -> resultSet = connector.query(new SQLStatement(
                        "select * from scanner " +
                                "join company on scanner.inventory_company_key=company.company_key " +
                                "join purchases on scanner.inventory_purchase_key=purchases.purchase_key " +
                                "where scanner.iv_number = \"" + iv_number + "\""));
                case "mo" -> resultSet = connector.query(new SQLStatement(
                        "select * from monitor " +
                                "join company on monitor.inventory_company_key=company.company_key " +
                                "join purchases on monitor.inventory_purchase_key=purchases.purchase_key " +
                                "where monitor.iv_number = \"" + iv_number + "\""));
                case "te" -> resultSet = connector.query(new SQLStatement(
                        "select * from telephone \"" +
                                "join company on telephone.inventory_company_key=company.company_key " +
                                "join purchases on telephone.inventory_purchase_key=purchases.purchase_key " +
                                "where telephone.iv_number = \"" + iv_number + "\""));
                case "hd" -> resultSet = connector.query(new SQLStatement(
                        "select * from headset " +
                                "join company on headset.inventory_company_key=company.company_key " +
                                "join purchases on headset.inventory_purchase_key=purchases.purchase_key " +
                                "where headset.iv_number = \"" + iv_number + "\""));
                case "ds" -> resultSet = connector.query(new SQLStatement(
                        "select * from dockingstation " +
                                "join company on dockingstation.inventory_company_key=company.company_key " +
                                "join purchases on dockingstation.inventory_purchase_key=purchases.purchase_key " +
                                "where dockingstation.iv_number = \"" + iv_number + "\""));
                case "dk" -> resultSet = connector.query(new SQLStatement(
                        "select * from desk " +
                                "join company on desk.inventory_company_key=company.company_key " +
                                "join purchases on desk.inventory_purchase_key=purchases.purchase_key " +
                                "where desk.iv_number = \"" + iv_number + "\""));
            }

            if (resultSet == null) {
                return null;
            }

            while (resultSet.next()) {
                resultList.add(new ArrayList<>());
                for (String s : columnList) {
                    if (s.equals("Primärschlüssel")) {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s, iv_number.substring(0, 2).toLowerCase()))));
                    } else {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s))));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Utils.convertArrayList_ArrayList_StringTo2DArray(resultList);
    }

    public String[][] getSelectViewCompany(String company) {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();

        ResultSet resultSet = null;
        try {

            final int attributeNumber = 3; //Number of attributes to get
            //region PC
            resultSet = connector.query(new SQLStatement("select pc.iv_number, company.company, pc.pc_key " +
                    "from company " +
                    "join pc on " +
                    "pc.inventory_company_key = company.company_key " +
                    "where pc.active = 1 and company.company = \"" + company + "\""));
            int i = 0;
            while (resultSet.next()) {
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
                    "where printer.active = 1 and company.company = \"" + company + "\""));
            while (resultSet.next()) {
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
                    "where scanner.active = 1 and company.company = \"" + company + "\""));
            while (resultSet.next()) {
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
                    "where monitor.active = 1 and company.company = \"" + company + "\""));
            while (resultSet.next()) {
                resultList.add(new ArrayList<>());
                for (int attr_number = 1; attr_number <= attributeNumber; attr_number++) {
                    resultList.get(i).add(String.valueOf(resultSet.getObject(attr_number)));
                }
                i++;
            }
            //endregion
            //region Telephone
            resultSet = connector.query(new SQLStatement("select telephone.iv_number, company.company, telephone.te_key " +
                    "from company " +
                    "join telephone " +
                    "on telephone.inventory_company_key = company.company_key " +
                    "where telephone.active = 1 and company.company = \"" + company + "\""));
            while (resultSet.next()) {
                resultList.add(new ArrayList<>());
                for (int attr_number = 1; attr_number <= attributeNumber; attr_number++) {
                    resultList.get(i).add(String.valueOf(resultSet.getObject(attr_number)));
                }
                i++;
            }
            //endregion
            //region Headset
            resultSet = connector.query(new SQLStatement("select headset.iv_number, company.company, headset.hd_key " +
                    "from company " +
                    "join headset " +
                    "on headset.inventory_company_key = company.company_key " +
                    "where headset.active = 1 and company.company = \"" + company + "\""));
            while (resultSet.next()) {
                resultList.add(new ArrayList<>());
                for (int attr_number = 1; attr_number <= attributeNumber; attr_number++) {
                    resultList.get(i).add(String.valueOf(resultSet.getObject(attr_number)));
                }
                i++;
            }
            //endregion
            //region Dockingstation
            resultSet = connector.query(new SQLStatement("select dockingstation.iv_number, company.company, dockingstation.ds_key " +
                    "from company " +
                    "join dockingstation " +
                    "on dockingstation.inventory_company_key = company.company_key " +
                    "where dockingstation.active = 1 and company.company = \"" + company + "\""));
            while (resultSet.next()) {
                resultList.add(new ArrayList<>());
                for (int attr_number = 1; attr_number <= attributeNumber; attr_number++) {
                    resultList.get(i).add(String.valueOf(resultSet.getObject(attr_number)));
                }
                i++;
            }
            //endregion
            // region Desk
            resultSet = connector.query(new SQLStatement("select desk.iv_number, company.company, desk.dk_key " +
                    "from company " +
                    "join desk " +
                    "on desk.inventory_company_key = company.company_key " +
                    "where desk.active = 1 and company.company = \"" + company + "\""));
            while (resultSet.next()) {
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

    public String[][] getAllFromPCView(int key) {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();

        ResultSet resultSet = connector.query(new SQLStatement(
                "select * from pc " +
                        "join company on pc.inventory_company_key=company.company_key " +
                        "join purchases on pc.inventory_purchase_key=purchases.purchase_key " +
                        "join user on pc.inventory_user_key=user.user_key " +
                        "where pc.pc_key = " + key
        ));
        try {
            while (resultSet.next()) {
                resultList.add(new ArrayList<>());
                for (String s : ColumNames.allAttributesPC) {
                    if (s.equals("Primärschlüssel")) {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s, "pc"))));
                    } else {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s))));

                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
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
                    if (s.equals("Primärschlüssel")) {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s, "pr"))));
                    } else {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s))));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
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
                    if (s.equals("Primärschlüssel")) {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s, "sc"))));
                    } else {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s))));

                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
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
                for (String s : ColumNames.allAttributesMO) {
                    if (s.equals("Primärschlüssel")) {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s, "mo"))));
                    } else {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s))));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
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
                for (String s : ColumNames.allAttributesTE) {
                    if (s.equals("Primärschlüssel")) {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s, "te"))));
                    } else {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s))));

                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Utils.convertArrayList_ArrayList_StringTo2DArray(resultList);
    }

    public String[][] getAllFromHDView(int key) {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();

        ResultSet resultSet = connector.query(new SQLStatement(
                "select * from headset " +
                        "join company on headset.inventory_company_key=company.company_key " +
                        "join purchases on headset.inventory_purchase_key=purchases.purchase_key " +
                        "where headset.hd_key = " + key
        ));
        try {
            while (resultSet.next()) {
                resultList.add(new ArrayList<>());
                for (String s : ColumNames.allAttributesHD) {
                    if (s.equals("Primärschlüssel")) {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s, "hd"))));
                    } else {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s))));

                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Utils.convertArrayList_ArrayList_StringTo2DArray(resultList);
    }

    public String[][] getAllFromDSView(int key) {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();

        ResultSet resultSet = connector.query(new SQLStatement(
                "select * from dockingstation " +
                        "join company on dockingstation.inventory_company_key=company.company_key " +
                        "join purchases on dockingstation.inventory_purchase_key=purchases.purchase_key " +
                        "where dockingstation.ds_key = " + key
        ));
        try {
            while (resultSet.next()) {
                resultList.add(new ArrayList<>());
                for (String s : ColumNames.allAttributesDS) {
                    if (s.equals("Primärschlüssel")) {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s, "ds"))));
                    } else {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s))));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Utils.convertArrayList_ArrayList_StringTo2DArray(resultList);
    }

    public String[][] getAllFromDKView(int key) {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();

        ResultSet resultSet = connector.query(new SQLStatement(
                "select * from desk " +
                        "join company on desk.inventory_company_key=company.company_key " +
                        "join purchases on desk.inventory_purchase_key=purchases.purchase_key " +
                        "where desk.dk_key = " + key
        ));
        try {
            while (resultSet.next()) {
                resultList.add(new ArrayList<>());
                for (String s : ColumNames.allAttributesDK) {
                    if (s.equals("Primärschlüssel")) {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s, "dk"))));
                    } else {
                        resultList.get(0).add(String.valueOf(resultSet.getObject(Utils.toDataBaseAttributeName(s))));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Utils.convertArrayList_ArrayList_StringTo2DArray(resultList);
    }

    public String[][] getAllCompanys() {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();

        ResultSet resultSet = connector.query(new SQLStatement(
                "select company from company where active = 1"
        ));
        try {
            resultList.add(new ArrayList<>());
            while (resultSet.next()) {
                resultList.get(0).add(String.valueOf(resultSet.getObject("company")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Utils.convertArrayList_ArrayList_StringTo2DArray(resultList);
    }

    public String[][] getAllUsers() {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();

        ResultSet resultSet = connector.query(new SQLStatement(
                "select name from user where active = 1"
        ));
        try {
            resultList.add(new ArrayList<>());
            while (resultSet.next()) {
                resultList.get(0).add(String.valueOf(resultSet.getObject("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Utils.convertArrayList_ArrayList_StringTo2DArray(resultList);
    }

    public String getCurrentIV_number(String table) {
        String[] allIV_numbers = getAllIV_Numbers(table);
        int current = 0;
        System.out.println(Arrays.toString(allIV_numbers));
        for (String s : allIV_numbers) {
            s = s.substring(s.indexOf("-") + 1);
            System.out.println(s);
            if (Integer.parseInt(s) > current){
                current = Integer.parseInt(s);
            }
        }
        current++;
        StringBuilder ret = new StringBuilder(String.valueOf(current));
        if (ret.length() > 4){
            JOptionPane.showConfirmDialog(null, "Es wurde alle 9999 iv_nummern ausgenutzt");
        }
        while (ret.length() < 4){
            ret.insert(0, "0");
        }
        return ret.toString();
    }

    private String[] getAllIV_Numbers(String table) {
        ResultSet resultSet = connector.query(new SQLStatement(
                "select iv_number from " + table
        ));

        ArrayList<String> iv_numbers = new ArrayList<>();
        try {
            while (resultSet.next()) {
                iv_numbers.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return iv_numbers.toArray(new String[]{});
    }
}