package Main.utility;

import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

import java.util.Arrays;

public class Constants {

    public static String[] statusList;
    public static String[] conditionList;

    public static void init(){
        SQLSelectStatements sqlSelectStatements = new SQLSelectStatements(new SQLConnector());

        statusList = sqlSelectStatements.getStatusList();
        conditionList = sqlSelectStatements.getConditionList();

        System.out.println(Arrays.toString(conditionList));
    }
}