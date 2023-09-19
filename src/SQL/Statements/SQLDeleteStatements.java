package SQL.Statements;

import SQL.SQLConnector;
import SQL.util.SQLStatement;

public class SQLDeleteStatements {
    public SQLDeleteStatements(SQLConnector connector) {
        this.connector = connector;
    }

    private SQLConnector connector;

    public void deleteTable(String table){
        connector.query(new SQLStatement("delete from psm_inventory." + table));
    }

    public void deleteUser(String name){
        connector.query(new SQLStatement("update user SET active = 0 where name = '" + name + "'"));
    }
}
