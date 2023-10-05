package Main.utility;

import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

public class Constants {

    public static String[] statusList;
    public static String[] conditionList;
    public static String[] paragraphs;
    public static String[] typs;

    public static void init(){
        SQLSelectStatements sqlSelectStatements = new SQLSelectStatements(new SQLConnector());

        statusList = sqlSelectStatements.getStatusList();
        conditionList = sqlSelectStatements.getConditionList();
        paragraphs = sqlSelectStatements.getParagraphsList0();
        typs = sqlSelectStatements.getTyps();
    }

    public static void refreshTyp(){
        SQLSelectStatements sqlSelectStatements = new SQLSelectStatements(new SQLConnector());
        typs = sqlSelectStatements.getTyps();
    }
}