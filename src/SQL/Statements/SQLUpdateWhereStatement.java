package SQL.Statements;

import SQL.util.IllegalSQLStatementException;

public class SQLUpdateWhereStatement extends SQLStatement {
    public SQLUpdateWhereStatement(String[] updateAttributes, String[] newValues, String[] targetAttributes, String[] conditions, String[] conditionValues, String concatinationtype) throws IllegalSQLStatementException {
        super("");
        StringBuilder sb = new StringBuilder();
        sb.append("update main set ");
        if (checkGreaterThan0(updateAttributes, newValues) || updateAttributes.length != newValues.length || targetAttributes.length != conditions.length || targetAttributes.length != conditionValues.length || conditions.length != conditionValues.length) {
            throw new IllegalSQLStatementException("parameter haben unterschiedliche länge");
        }
        if (updateAttributes.length > 1) {
            sb.append(updateAttributes[0]).append(" = \"").append(newValues[0]).append("\"");
            for (int i = 1; i < updateAttributes.length; i++) {
                sb.append(", ").append(updateAttributes[i]).append(" = \"").append(newValues[i]).append("\"");
            }
        } else {
            sb.append(updateAttributes[0]).append(" = \"").append(newValues[0]).append("\"");
        }
        if (targetAttributes.length > 0){
            sb.append(" where ");
            if (targetAttributes.length == 1){
                sb.append(targetAttributes[0]).append(" ").append(conditions[0]).append(" \"").append(conditionValues[0]).append("\"");
            } else {
                sb.append(targetAttributes[0]).append(" ").append(conditions[0]).append(" \"").append(conditionValues[0]).append("\"");
                for (int i = 1; i < targetAttributes.length; i++) {
                    sb.append(" ").append(concatinationtype).append(" ") .append(targetAttributes[i]).append(" ").append(conditions[i]).append(" \"").append(conditionValues[i]).append("\"");
                }
            }
        }
        super.setStatement(sb.toString());
    }

    private boolean checkGreaterThan0(String[] s1, String[] s2){
        if (s1.length == 0){
            return true;
        }
        return s2.length == 0;
    }
}