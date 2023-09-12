package SQL.Statements;

import SQL.SQLConnector;
import SQL.util.SQLStatement;

public class SQLDeleteStatements {
    public SQLDeleteStatements(SQLConnector connector) {
        this.connector = connector;
    }

    private SQLConnector connector;

    public void delete(String table){
        connector.query(new SQLStatement("delete from psm_inventory." + table));
    }
}
