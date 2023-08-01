package SQL.Statements;

import SQL.util.IllegalSQLStatementException;

public class SQLSelectAllWhereStatement extends SQLStatement {
    public SQLSelectAllWhereStatement(String targetAttribute, String condition, String conditionValue) throws IllegalSQLStatementException {
        super("");
        if (targetAttribute == null || condition == null|| conditionValue == null){
            throw new IllegalSQLStatementException("Parameter war null");
        }
        super.setStatement("select * from main where main." + targetAttribute + " " + condition + "\"" + conditionValue + "\"");
    }

    public SQLSelectAllWhereStatement(String targetAttribute, String condition, int conditionValue) throws IllegalSQLStatementException {
        super("");
        if (targetAttribute == null || condition == null){
            throw new IllegalSQLStatementException("Parameter war null");
        }
        super.setStatement("select * from main where main." + targetAttribute + " " + condition + "\"" + conditionValue + "\"");
    }
}
