package SQL;

import SQL.util.SQLStatement;

import java.sql.*;

public class SQLConnector {

    private static final String URL = "jdbc:mysql://10.10.1.16:33063/psm_inventory";
    private static final String USER = "psm_inventory";
    private static final String PWD = "+e6.aYuqx4zzsz";

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
                case "select" -> result = statement.executeQuery(query.getStatement());
                case "insert", "delete", "update" -> statement.executeUpdate(query.getStatement());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}