package Main;

import GUI.MainGui;
import SQL.SQLConnector;
import SQL.Statements.SQLStatements;

public class Main {
    private SQLConnector connector;
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
        sqlStatements = new SQLStatements(connector);
        switch (permission_level){
            case "admin": {
                mainGui= new MainGui(connector, sqlStatements);
                mainGui.init();
            }
            case "user":{
                //TODO
            }
        }
    }
}