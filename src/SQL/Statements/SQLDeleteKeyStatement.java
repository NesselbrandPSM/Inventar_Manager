package SQL.Statements;

public class SQLDeleteKeyStatement extends SQLStatement{
    public SQLDeleteKeyStatement(int key) {
        super("delete from main where main.key = " + key);
    }
}