package SQL;

import SQL.util.SQLStatement;

import java.sql.*;

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
                    result = statement.executeQuery(query.getStatement());
                    break;
                case "insert":
                case "delete":
                case "update":
                    statement.executeUpdate(query.getStatement());
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}