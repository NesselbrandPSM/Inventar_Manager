package SQL;

import GUI.GUIS.LoadingScreen;
import Main.Main;
import Main.utility.Settings;
import Main.utility.Utils;
import SQL.util.SQLStatement;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

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
                    Utils.systemOutYelPrintln(query.getStatement());
                    result = statement.executeQuery(query.getStatement());
                    break;
                case "insert":
                case "delete":
                case "update":
                    if (Main.startup) {
                        LoadingScreen.print(query.getStatement());
                    }
                    Utils.systemOutYelPrintln(query.getStatement());
                    statement.executeUpdate(query.getStatement());
                    break;
            }
        } catch (CommunicationsException e) {
            init();
            return queryAfterReinitiated(query);
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException();
        }
        return result;
    }

    private ResultSet queryAfterReinitiated(SQLStatement query) {
        ResultSet result = null;
        try {
            switch (query.getStatement().substring(0, query.getStatement().indexOf(" "))) {
                case "select":
                    if (Main.startup) {
                        LoadingScreen.print(query.getStatement());
                    }
                    Utils.systemOutYelPrintln(query.getStatement());
                    result = statement.executeQuery(query.getStatement());
                    break;
                case "insert":
                case "delete":
                case "update":
                    if (Main.startup) {
                        LoadingScreen.print(query.getStatement());
                    }
                    Utils.systemOutYelPrintln(query.getStatement());
                    statement.executeUpdate(query.getStatement());
                    break;
            }
        } catch (CommunicationsException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException();
        }
        return result;
    }
}