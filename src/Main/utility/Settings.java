package Main.utility;

import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

public class Settings {
    public static String standardStatusAfterDeviceLoan;

    public static void init(){
        SQLSelectStatements sqlSelectStatements = new SQLSelectStatements(new SQLConnector());
        standardStatusAfterDeviceLoan = sqlSelectStatements.getSetting("standardStatusAfterDeviceLoan");
    }
}
