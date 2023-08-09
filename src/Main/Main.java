package Main;

import GUI.MainGui;
import SQL.SQLConnector;
import SQL.Statements.SQLSequenzStatements;
import SQL.Statements.SQLStatements;

public class Main {
    private SQLConnector connector;
    private SQLSequenzStatements sqlSequenzStatements;
    private SQLStatements sqlStatements;
    public MainGui mainGui;

    private static final String permission_level = "admin";

    public static Main m;

    public static void main(String[] args) {
        m = new Main();
        m.init();
    }

    private void init(){
        connector = new SQLConnector();
        sqlSequenzStatements = new SQLSequenzStatements(connector);
        sqlStatements = new SQLStatements(connector);
        switch (permission_level){
            case "admin": {
                mainGui= new MainGui(connector, sqlSequenzStatements, sqlStatements);
                mainGui.init();
            }
            case "user":{
                //TODO
            }
        }
    }
}