package Main.utility;

import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

public class Constants {

    public static String[] statusList;
    public static String[] conditionList;

    public static void init(){
        SQLSelectStatements sqlSelectStatements = new SQLSelectStatements(new SQLConnector());

        statusList = sqlSelectStatements.getStatusList();
        conditionList = sqlSelectStatements.getConditionList();
    }
}