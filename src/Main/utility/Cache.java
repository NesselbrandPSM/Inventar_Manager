package Main.utility;

import java.util.HashMap;

public class Cache {
    private static HashMap<String, String[][]> selectStatementsC;

    public static void init() {
        selectStatementsC = new HashMap<>();
    }

    public static String[][] checkSelectStatement(String statement) {
        return selectStatementsC.getOrDefault(statement, null);
    }

    public static void putSelectStatement(String statement, String[][] result){
        selectStatementsC.put(statement, result);
    }

    public static void clearSelectStatementCache(){
        selectStatementsC.clear();
    }
}
