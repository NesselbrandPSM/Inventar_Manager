package SQL;

import GUI.GUIS.LoadingScreen;
import Main.Main;
import Main.utility.Utils;
import SQL.util.SQLStatement;

import java.sql.*;
import java.util.HashMap;

public class SQLConnector {

    private static final String URL = "jdbc:mysql://db1620.mydbserver.com/usr_p530504_3";
    private static final String USER = "p530504d1";
    private static final String PWD = "4*,mxzi8maGquf";

    private Connection connection;
    private Statement statement;

    public SQLConnector() {
        init();
    }

    private void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PWD);
            statement = connection.createStatement();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet query(SQLStatement query) {
        ResultSet result = null;
        try {
            switch (query.getStatement().substring(0, query.getStatement().indexOf(" "))) {
                case "select":
                    if (Main.startup) {
                        LoadingScreen.print(query.getStatement());
                    }
                    Utils.systemOutPrintln(query.getStatement());
                    result = statement.executeQuery(query.getStatement());
                    break;
                case "insert":
                case "delete":
                case "update":
                    if (Main.startup) {
                        LoadingScreen.print(query.getStatement());
                    }
                    Utils.systemOutPrintln(query.getStatement());
                    statement.executeUpdate(query.getStatement());
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}