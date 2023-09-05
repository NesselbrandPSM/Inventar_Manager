package SQL.Statements;

import SQL.SQLConnector;
import SQL.util.SQLStatement;

public class SQLInsertStatements {
    public SQLInsertStatements(SQLConnector connector) {
        this.connector = connector;
    }

    private SQLConnector connector;

    public void inputPCEntry(String[] args){
        connector.query(new SQLStatement(
                "insert into pc (" +
                        "iv_number, s_number, current_status, dguv, note, category, manufacturer, modell, memory_ram_size_gb, memory_rom_size_gb, cpu, os, ip, last_update) " +
                        "values (" +
                        "'" + args[0] + "', " +
                        "'" + args[7] + "', " +
                        "'" + args[2] + "', " +
                        "'" + args[18] + "', " +
                        "'" + args[19] + "', " +
                        "'" + args[1] + "', " +
                        "'" + args[5] + "', " +
                        "'" + args[6] + "', " +
                        "'" + args[9] + "', " +
                        "'" + args[10] + "', " +
                        "'" + args[8] + "', " +
                        "'" + args[11] + "', " +
                        "'" + args[12] + "', " +
                        "'" + args[13] + "'" +
                        ")"
        ));
    }
}
