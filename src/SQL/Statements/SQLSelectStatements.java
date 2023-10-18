package SQL.Statements;

import GUI.util.ColumNames;
import Main.utility.UtilPrintables.IVObject;
import Main.utility.UtilPrintables.IVObjectType;
import Main.utility.Utils;
import SQL.SQLConnector;
import SQL.util.SQLStatement;
import org.apache.commons.collections4.iterators.SingletonIterator;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SQLSelectStatements {
    public SQLSelectStatements(SQLConnector connector) {
        this.connector = connector;
    }

    private SQLConnector connector;
    private final int attributeNumber = 3; //Number of attributes to get

    public String[][] getDefaultView() {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();
        try {
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
            e.printStackTrace();
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
                                "where pc.active = 1 and pc.iv_number = \"" + iv_number + "\""));
                case "pr" -> resultSet = connector.query(new SQLStatement(
                        "select * from printer " +
                                "join company on printer.inventory_company_key=company.company_key " +
                                "where printer.active = 1 and printer.iv_number = \"" + iv_number + "\""));
                case "sc" -> resultSet = connector.query(new SQLStatement(
                        "select * from scanner " +
                                "join company on scanner.inventory_company_key=company.company_key " +
                                "where scanner.active = 1 and scanner.iv_number = \"" + iv_number + "\""));
                case "mo" -> resultSet = connector.query(new SQLStatement(
                        "select * from monitor " +
                                "join company on monitor.inventory_company_key=company.company_key " +
                                "where monitor.active = 1 and monitor.iv_number = \"" + iv_number + "\""));
                case "te" -> resultSet = connector.query(new SQLStatement(
                        "select * from telephone " +
                                "join company on telephone.inventory_company_key=company.company_key " +
                                "where telephone.active = 1 and telephone.iv_number = \"" + iv_number + "\""));
                case "hd" -> resultSet = connector.query(new SQLStatement(
                        "select * from headset " +
                                "join company on headset.inventory_company_key=company.company_key " +
                                "where headset.active = 1 and headset.iv_number = \"" + iv_number + "\""));
                case "ds" -> resultSet = connector.query(new SQLStatement(
                        "select * from dockingstation " +
                                "join company on dockingstation.inventory_company_key=company.company_key " +
                                "where dockingstation.active = 1 and dockingstation.iv_number = \"" + iv_number + "\""));
                case "dk" -> resultSet = connector.query(new SQLStatement(
                        "select * from desk " +
                                "join company on desk.inventory_company_key=company.company_key " +
                                "where desk.active = 1 and desk.iv_number = \"" + iv_number + "\""));
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

    public String[][] getSelectViewCompany(String company, String table) {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();

        ResultSet resultSet = null;
        try {
            resultSet = connector.query(new SQLStatement("select " + table + ".iv_number, company.company, " + table + "." + Utils.getShortCutFromTable(table) + "_key " +
                    "from company " +
                    "join " + table + " on " +
                    table + ".inventory_company_key = company.company_key " +
                    "where " + table + ".active = 1 and company.company = \"" + company + "\""));
            int i = 0;
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

    public String[][] getSelectViewType(String table) {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();
        try {
            ResultSet resultSet = connector.query(new SQLStatement("select " + table + ".iv_number, company.company, " + table + "." + Utils.getShortCutFromTable(table) + "_key " +
                    "from " + table + " " +
                    "join company on " + table +
                    ".inventory_company_key = company.company_key " +
                    "where " + table + ".active = 1"));
            int i = 0;
            while (resultSet.next()) {
                resultList.add(new ArrayList<>());
                for (int attr_number = 1; attr_number <= attributeNumber; attr_number++) {
                    resultList.get(i).add(String.valueOf(resultSet.getObject(attr_number)));
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Utils.convertArrayList_ArrayList_StringTo2DArray(resultList);
    }

    public String[][] getAllFromPCView(int key) {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();

        ResultSet resultSet = connector.query(new SQLStatement(
                "select * from pc " +
                        "join company on pc.inventory_company_key=company.company_key " +
                        "join user on pc.inventory_user_key=user.name " +
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
            e.printStackTrace();
        }

        return Utils.convertArrayList_ArrayList_StringTo2DArray(resultList);
    }

    public String[][] getAllFromPRView(int key) {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();

        ResultSet resultSet = connector.query(new SQLStatement(
                "select * from printer " +
                        "join company on printer.inventory_company_key=company.company_key " +
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
                        "join user on scanner.inventory_user_key=user.name " +
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
                        "join user on monitor.inventory_user_key=user.name " +
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
                        "join user on telephone.inventory_user_key=user.name " +
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
                        "join user on headset.inventory_user_key=user.name " +
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
                        "join user on dockingstation.inventory_user_key=user.name " +
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
                        "join user on desk.inventory_user_key=user.name " +
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
                "select company, company_key from company where active = 1"
        ));
        try {
            resultList.add(new ArrayList<>());
            resultList.add(new ArrayList<>());
            while (resultSet.next()) {
                resultList.get(0).add(String.valueOf(resultSet.getObject("company")));
                resultList.get(1).add(String.valueOf(resultSet.getObject("company_key")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Utils.convertArrayList_ArrayList_StringTo2DArray(resultList);
    }

    public String[][] getAllUsers() {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();

        ResultSet resultSet = connector.query(new SQLStatement(
                "select name, user_key from user where active = 1 and current_status = 1"
        ));
        try {
            resultList.add(new ArrayList<>());
            resultList.add(new ArrayList<>());
            while (resultSet.next()) {
                resultList.get(0).add(String.valueOf(resultSet.getObject("name")));
                resultList.get(1).add(String.valueOf(resultSet.getObject("user_key")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Utils.convertArrayList_ArrayList_StringTo2DArray(resultList);
    }

    public String[][] getAllUsersTableModel(int status) {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();

        SQLStatement statement = new SQLStatement("");

        switch (status) {
            case -1: // inaktiv
                statement.setStatement("select name, mail, current_status from user where active = 1 and current_status = -1");
                break;
            case 0: // neu
                statement.setStatement("select name, mail, current_status from user where active = 1 and current_status = 0");
                break;
            case 1: // aktiv
                statement.setStatement("select name, mail, current_status from user where active = 1 and current_status = 1");
                break;
            case 2: // all
                statement.setStatement("select name, mail, current_status from user where active = 1");
                break;
        }

        ResultSet resultSet = connector.query(statement);
        try {
            while (resultSet.next()) {
                ArrayList<String> temp = new ArrayList<>();
                temp.add(String.valueOf(resultSet.getObject("name")));
                temp.add(String.valueOf(resultSet.getObject("mail")));
                temp.add(String.valueOf(resultSet.getObject("current_status")));
                resultList.add(temp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Utils.convertArrayList_ArrayList_StringTo2DArray(resultList);
    }

    public String[][] getAllUsersActive0() {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();

        ResultSet resultSet = connector.query(new SQLStatement(
                "select name, user_key, current_status, mail, active, address, working_hours, working_days, homeoffice, entrytransfer from user"
        ));
        try {
            resultList.add(new ArrayList<>());
            resultList.add(new ArrayList<>());
            resultList.add(new ArrayList<>());
            resultList.add(new ArrayList<>());
            resultList.add(new ArrayList<>());
            while (resultSet.next()) {
                resultList.get(0).add(String.valueOf(resultSet.getObject("name")));
                resultList.get(1).add(String.valueOf(resultSet.getObject("user_key")));
                resultList.get(2).add(String.valueOf(resultSet.getObject("current_status")));
                resultList.get(3).add(String.valueOf(resultSet.getObject("mail")));
                resultList.get(4).add(String.valueOf(resultSet.getObject("active")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Utils.convertArrayList_ArrayList_StringTo2DArray(resultList);
    }

    public String getCurrentIV_number(String table) {
        String[] allIV_numbers = getAllIV_Numbers(table);
        int current = 0;
        for (String s : allIV_numbers) {
            s = s.substring(s.indexOf("-") + 1);
            if (Integer.parseInt(s) > current) {
                current = Integer.parseInt(s);
            }
        }
        current++;
        StringBuilder ret = new StringBuilder(String.valueOf(current));
        if (ret.length() > 4) {
            JOptionPane.showConfirmDialog(null, "Es wurde alle 9999 iv_nummern ausgenutzt");
            return null;
        }
        while (ret.length() < 4) {
            ret.insert(0, "0");
        }
        switch (table) {
            case "pc" -> ret.insert(0, "PC-");
            case "scanner" -> ret.insert(0, "SC-");
            case "printer" -> ret.insert(0, "PR-");
            case "dockingstation" -> ret.insert(0, "DS-");
            case "headset" -> ret.insert(0, "HD-");
            case "monitor" -> ret.insert(0, "MO-");
            case "telephone" -> ret.insert(0, "TE-");
            case "desk" -> ret.insert(0, "DK-");
            case "miscellaneous" -> ret.insert(0, "MC-");
        }
        return ret.toString();
    }

    public String[] getAllIV_Numbers(String table) {
        ResultSet resultSet = connector.query(new SQLStatement(
                "select iv_number from " + table
        ));

        return getStrings(resultSet);
    }

    public Collection<? extends String> getAllIV_NumbersActiveAndGiveable(String table) {
        ResultSet resultSet = connector.query(new SQLStatement(
                "select iv_number from " + table + " where active = 1 and current_status = 'edv eingelagert'"
        ));
        return List.of(getStrings(resultSet));
    }

    private String[] getStrings(ResultSet resultSet) {
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

    public String[] getTyps() {
        ResultSet res = connector.query(new SQLStatement(
                "select typ, p_key from misctype"
        ));
        ArrayList<String> result = new ArrayList<>();
        try {
            while (res.next()) {
                result.add(res.getString("typ"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result.toArray(new String[0]);
    }

    public String[] getStatusList() {
        ResultSet res = connector.query(new SQLStatement(
                "select statustext, p_key from statuslist"
        ));
        ArrayList<String> result = new ArrayList<>();
        try {
            while (res.next()) {
                result.add(res.getString("statustext"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result.toArray(new String[0]);
    }

    public String[] getConditionList() {
        ResultSet res = connector.query(new SQLStatement(
                "select conditions, p_key from conditionlist"
        ));
        ArrayList<String> result = new ArrayList<>();
        try {
            while (res.next()) {
                result.add(res.getString("conditions"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result.toArray(new String[0]);
    }

    public String[] getParagraphsList0() {
        ResultSet res = connector.query(new SQLStatement(
                "select paragraph from paragraphen where typ = '0'"
        ));
        ArrayList<String> result = new ArrayList<>();
        try {
            while (res.next()) {
                result.add(res.getString("paragraph"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result.toArray(new String[0]);
    }

    public String[][] getParagraphsList1() {
        ResultSet res = connector.query(new SQLStatement(
                "select * from paragraphen where typ = '1'"
        ));
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        try {
            while (res.next()) {
                ArrayList<String> temp = new ArrayList<>();
                temp.add(res.getString("title"));
                temp.add(res.getString("paragraph"));
                result.add(temp);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Utils.convertArrayList_ArrayList_StringTo2DArray(result);
    }

    public String[][] getCorospondingDesk(String iv_number) {
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();

        String rest = "";
        switch (iv_number.substring(0, 2).toLowerCase()) {
            case "mo" -> {
                rest = "mo_iv_number_1 = '" + iv_number + "' or mo_iv_number_2 = '" + iv_number + "'";
            }
            case "sc", "dk", "hd", "te", "ds", "pc" ->
                    rest = iv_number.substring(0, 2).toLowerCase() + "_iv_number = " + "'" + iv_number + "'";
        }

        SQLStatement s = new SQLStatement(
                "select iv_number from desk where " + rest
        );
        ResultSet resultSet = connector.query(s);
        try {
            while (resultSet.next()) {
                ArrayList<String> temp = new ArrayList<>();
                temp.add(resultSet.getString("iv_number"));
                resultList.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Utils.convertArrayList_ArrayList_StringTo2DArray(resultList);
    }

    //@param name = benutzername eines users (z.B.: l.schmidt)
    public ArrayList<IVObject> getUserObjects(String name) {
        ArrayList<IVObject> objects = new ArrayList<>();

        for (IVObjectType type : IVObjectType.values()) {
            ResultSet res = connector.query(new SQLStatement(
                    "select * from " + type.toString() + " where active = 1 and inventory_user_key = '" + name + "'"
            ));

            try {
                while (res.next()) {
                    ArrayList<String> resList = new ArrayList<>();
                    for (String s : type.getAttributes()) {
                        resList.add(String.valueOf(res.getObject(s)));
                    }
                    objects.add(new IVObject(resList.toArray(new String[0]), type));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return objects;
    }

    public String[] getUserInfos(String userName) {
        ResultSet res = connector.query(new SQLStatement(
                "select * from user where name = '" + userName + "'"
        ));

        ArrayList<String> result = new ArrayList<>();
        try {
            res.next();

            result.add(res.getString(2)); //username
            result.add(res.getString(3)); //email
            result.add(res.getString(6)); //address
            result.add(res.getString(7)); //working_hours
            result.add(res.getString(8)); //working_days
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result.toArray(new String[0]);
    }

    public String[] getAllFreeEntrys() {
        ArrayList<String> iv_numberList = new ArrayList<>();

        for (IVObjectType type : IVObjectType.values()) {
            iv_numberList.addAll(getAllIV_NumbersActiveAndGiveable(type.toString()));
        }

        return iv_numberList.toArray(new String[0]);
    }

    public String[] getUserAttributes(String userName){
        ArrayList<String> attr = new ArrayList<>();

        ResultSet res = connector.query(new SQLStatement(
                "select * from user where name = '" + userName + "'"
        ));

        try {
            res.next();
            attr.add(res.getString("address"));
            attr.add(res.getString("working_hours"));
            attr.add(res.getString("working_days"));
            attr.add(res.getString("homeoffice"));
            attr.add(res.getString("entrytransfer"));
        } catch (SQLException e) {
            throw new RuntimeException();
        }

        return attr.toArray(new String[0]);
    }

    public String[] getConditions(String iv_number){
        ResultSet res = connector.query(new SQLStatement(
                "select c_status, c_note from " + Utils.getTableFromShortCut(iv_number.substring(0, 2)) + " where iv_number = '" + iv_number + "'"
        ));
        String[] data = new String[2];
        try {
            res.next();
            data[0] = res.getString(1);
            data[1] = res.getString(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}