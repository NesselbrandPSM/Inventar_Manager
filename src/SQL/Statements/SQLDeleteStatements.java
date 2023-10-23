package SQL.Statements;

import SQL.SQLConnector;
import SQL.util.SQLStatement;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLDeleteStatements {
    public SQLDeleteStatements(SQLConnector connector) {
        this.connector = connector;
    }

    private SQLConnector connector;

    public void deleteTable(String table){
        connector.query(new SQLStatement("delete from usr_p530504_3." + table));
    }

    public void deleteUser(String name){
        connector.query(new SQLStatement("update user SET active = 0 where name = '" + name + "'"));
    }

    public void deleteEntry(String table, String iv_number){
        String[] tablesU = new String[]{"dockingstation", "headset", "miscellaneous", "monitor", "pc", "scanner", "telephone"};
        boolean canDelete = true;
        boolean checkDelete = false;
        for (String s : tablesU) {
            if (table.equals(s)) {
                checkDelete = true;
                break;
            }
        }

        if (checkDelete){
            ResultSet res = connector.query(new SQLStatement("select inventory_user_key from " + table + " where iv_number = '" + iv_number + "'"));
            try {
                res.next();
                if (!res.getString(1).equals("-1")){
                    canDelete = false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (canDelete){
            connector.query(new SQLStatement(
                    "update " + table + " set active = 0 where iv_number = '" + iv_number + "'"
            ));
        } else {
            JOptionPane.showMessageDialog(null, "Entry konnte nicht gelöscht werden, da ihm noch ein User hinzugefügt ist!");
        }
    }
}
