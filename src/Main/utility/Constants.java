package Main.utility;

import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

public class Constants {

    public static String[] statusList;
    public static String[] conditionList;
    public static String[] paragraphs;

    public static int printHelper;
    public static int printHelper2;

    // 1 => Paragraphen auf nächste Seite
    public static int printStatus;

    public static void init(){
        SQLSelectStatements sqlSelectStatements = new SQLSelectStatements(new SQLConnector());

        statusList = sqlSelectStatements.getStatusList();
        conditionList = sqlSelectStatements.getConditionList();
        paragraphs = sqlSelectStatements.getParagraphsList();

        printHelper = -1;
        printHelper2 = -1;
        printStatus = -1;
    }
}