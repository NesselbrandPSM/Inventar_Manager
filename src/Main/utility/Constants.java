package Main.utility;

import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

import java.util.Arrays;

public class Constants {

    public static String[] statusList;
    public static String[] conditionList;
    public static String[] paragraphsUeberlassung;
    public static String[][] paragraphsHomeOffice;
    public static String[] typs;

    public static void init(){
        SQLSelectStatements sqlSelectStatements = new SQLSelectStatements(new SQLConnector());

        statusList = sqlSelectStatements.getStatusList();
        conditionList = sqlSelectStatements.getConditionList();
        paragraphsUeberlassung = sqlSelectStatements.getParagraphsList0();
        typs = sqlSelectStatements.getTyps();
        paragraphsHomeOffice = sqlSelectStatements.getParagraphsList1();
    }

    public static void refreshTyp(){
        SQLSelectStatements sqlSelectStatements = new SQLSelectStatements(new SQLConnector());
        typs = sqlSelectStatements.getTyps();
    }

    public static void refreshParagraphsHome(){
        SQLSelectStatements sqlSelectStatements = new SQLSelectStatements(new SQLConnector());
        paragraphsHomeOffice = sqlSelectStatements.getParagraphsList1();
    }
}