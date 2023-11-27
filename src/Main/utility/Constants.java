package Main.utility;

import Main.utility.UtilPrintables.IVObject;
import Main.utility.UtilPrintables.IVObjectRet;
import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

import java.util.ArrayList;

public class Constants {

    public static String[] statusList;
    public static String[] conditionList;
    public static String[] PRINT_paragraphsUeberlassung;
    public static String[][] PRINT_paragraphsHomeOffice;

    public static String[] paragraphsUeberlassung;
    public static String[][] paragraphsHomeOffice;

    public static String[] typs;

    public static boolean printable = false;
    public static boolean setActive = false;

    public static ArrayList<IVObjectRet> returnList;
    private static String[][] companySet;

    public static void init(){
        SQLSelectStatements sqlSelectStatements = new SQLSelectStatements(new SQLConnector());

        statusList = sqlSelectStatements.getStatusList();
        conditionList = sqlSelectStatements.getConditionList();
        paragraphsUeberlassung = sqlSelectStatements.getParagraphsList0();
        typs = sqlSelectStatements.getTyps();
        paragraphsHomeOffice = sqlSelectStatements.getParagraphsList1();
        resetParagraphsHome();
        resetParagraphsUeberlassung();
        companySet = sqlSelectStatements.getAllCompanys();
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

    public static String[][] getCompanySet(){
        String[][] set = new String[companySet.length][companySet[0].length];
        for (int i = 0; i < companySet.length; i++) {
            System.arraycopy(companySet[i], 0, set[i], 0, companySet[0].length);
        }
        return set;
    }
}