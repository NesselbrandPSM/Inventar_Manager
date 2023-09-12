package SQL.util;

public class SQLStatement {

    private String statement;

    public SQLStatement(String s) {
        statement = s;
    }

    public String getStatement(){
        return statement;
    }

    public void setStatement(String statement){
        this.statement = statement;
    }
}