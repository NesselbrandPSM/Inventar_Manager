package SQL.Statements;

public class SQLSelect_inventory_scStatement extends SQLStatement {
    public SQLSelect_inventory_scStatement() {
        super("select * from inventory_company join inventory_sc " +
                "where inventory_company_key = company_key");
    }
}