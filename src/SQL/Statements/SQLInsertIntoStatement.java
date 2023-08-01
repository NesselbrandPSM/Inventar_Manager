package SQL.Statements;

import SQL.util.IllegalSQLStatementException;
import SQL.util.TableAttributes;

public class SQLInsertIntoStatement extends SQLStatement {
    public SQLInsertIntoStatement(TableAttributes[] attr, String[] values) throws IllegalSQLStatementException {
        super("");
        if (attr.length != values.length || attr.length  < 1 || values.length < 1){
            throw new IllegalSQLStatementException("Parametar Numbers differ or are less than 1");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("insert into main (");

        if (attr.length > 1){
            sb.append(attr[0]);
            for (int i = 1; i < attr.length; i++) {
                sb.append(", ").append(attr[i]);
            }
        } else {
            sb.append(attr[0]);
        }
        sb.append(")").append(" values (");

        if (values.length > 1){
            sb.append("\"").append(values[0]).append("\"");
            for (int i = 1; i < attr.length; i++) {
                sb.append(", \"").append(values[i]).append("\"");
            }
        } else {
            sb.append("\"").append(values[0]).append("\"");
        }

        sb.append(")");
        super.setStatement(sb.toString());
    }
}