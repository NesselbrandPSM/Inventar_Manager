package Main;

import GUI.MainInputGui;
import GUI.MainGui;
import SQL.SQLConnector;
import SQL.Statements.SQLInsertStatements;
import SQL.Statements.SQLSelectStatements;

public class Main {
    private SQLConnector connector;
    public SQLSelectStatements sqlSelectStatements;
    public MainGui mainGui;
    public MainInputGui mainForm;

    private static final String permission_level = "admin";

    public static Main m;

    public static void main(String[] args) {
        m = new Main();
        m.init();
    }

    private void init(){
        switch (permission_level){
            case "admin" -> {
                connector = new SQLConnector();
                sqlSelectStatements = new SQLSelectStatements(connector);
                mainGui= new MainGui(connector, sqlSelectStatements);
                mainGui.init();
            }
            case "user" -> {
            }
            case "testing" -> {
                mainForm = new MainInputGui();
                mainForm.init();
            }
        }
    }
}