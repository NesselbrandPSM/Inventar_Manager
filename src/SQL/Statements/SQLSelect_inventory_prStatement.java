package SQL.Statements;

public class SQLSelect_inventory_prStatement extends SQLStatement {
    public SQLSelect_inventory_prStatement() {
        super("select * from inventory_company join inventory_pr " +
                "where inventory_company_key = company_key");
    }
}