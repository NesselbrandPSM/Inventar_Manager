package Main.utility;

import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

public class Constants {

    public static String[] statusList;
    public static String[] conditionList;
    public static String[] PRINT_paragraphsUeberlassung;
    public static String[][] PRINT_paragraphsHomeOffice;

    public static String[] paragraphsUeberlassung;
    public static String[][] paragraphsHomeOffice;

    public static String[] typs;

    public static boolean printable = false;

    public static void init(){
        SQLSelectStatements sqlSelectStatements = new SQLSelectStatements(new SQLConnector());

        statusList = sqlSelectStatements.getStatusList();
        conditionList = sqlSelectStatements.getConditionList();
        paragraphsUeberlassung = sqlSelectStatements.getParagraphsList0();
        typs = sqlSelectStatements.getTyps();
        paragraphsHomeOffice = sqlSelectStatements.getParagraphsList1();
        resetParagraphsHome();
        resetParagraphsUeberlassung();
    }

    public static void refreshTyp(){
        SQLSelectStatements sqlSelectStatements = new SQLSelectStatements(new SQLConnector());
        typs = sqlSelectStatements.getTyps();
    }

    public static void resetParagraphsHome(){
        SQLSelectStatements sqlSelectStatements = new SQLSelectStatements(new SQLConnector());
        PRINT_paragraphsHomeOffice = sqlSelectStatements.getParagraphsList1();
    }

    public static void resetParagraphsUeberlassung(){
        SQLSelectStatements sqlSelectStatements = new SQLSelectStatements(new SQLConnector());
        PRINT_paragraphsUeberlassung = sqlSelectStatements.getParagraphsList0();
    }
}