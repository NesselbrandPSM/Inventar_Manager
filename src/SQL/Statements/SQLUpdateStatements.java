package SQL.Statements;

import SQL.SQLConnector;
import SQL.util.SQLStatement;

import java.util.ArrayList;

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

    public void updateUserData(String userName, String[] data){
        StringBuilder sb = new StringBuilder();

        sb.append("update user set ");

        sb.append("address = '");
        sb.append(data[0]);
        sb.append("', ");

        sb.append("working_hours = '");
        sb.append(data[1]);
        sb.append("', ");

        sb.append("working_days = '");
        sb.append(data[2]);
        sb.append("', ");

        sb.append("homeoffice = ");
        sb.append(data[3]);
        sb.append(", ");

        sb.append("contractDate = '");
        sb.append(data[5]);
        sb.append("', ");

        sb.append("entrytransfer = ");
        sb.append(data[4]);

        sb.append(" where name = ");
        sb.append("'");
        sb.append(userName);
        sb.append("'");
        System.out.println(sb);

        connector.query(new SQLStatement(
           sb.toString()
        ));
    }

    public void updateEntryUserAndStatus(String userName, String iv_number, String status, String table){
        connector.query(new SQLStatement(
                "update " + table + " SET inventory_user_key = '" + userName + "', current_status = '" + status + "' where iv_number = '" + iv_number + "'"
        ));
    }
}
