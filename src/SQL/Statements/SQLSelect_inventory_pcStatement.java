package SQL.Statements;

public class SQLSelect_inventory_pcStatement extends SQLStatement {
    public SQLSelect_inventory_pcStatement() {
        super("select * from inventory_company join inventory_pc " +
                "where inventory_company_key = company_key");
    }
}