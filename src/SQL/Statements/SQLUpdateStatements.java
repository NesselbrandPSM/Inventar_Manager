package SQL.Statements;

import SQL.SQLConnector;
import SQL.util.SQLStatement;

public class SQLUpdateStatements {
    private SQLConnector connector;

    public SQLUpdateStatements(SQLConnector connector) {
        this.connector = connector;
    }

    public void updateUserStatus(String name, int status) {
        connector.query(new SQLStatement(
                "update user SET current_status = " + status + " where name = " + "'" + name + "'"
        ));
    }
}
