package Main;

import GUI.MainInputGui;
import GUI.MainGui;
import SQL.SQLConnector;
import SQL.Statements.SQLStatements;

public class Main {
    private SQLConnector connector;
    public SQLStatements sqlStatements;
    public MainGui mainGui;
    public MainInputGui mainForm;

    private static final String permission_level = "testing";

    public static Main m;

    public static void main(String[] args) {
        m = new Main();
        m.init();
    }

    private void init(){
        //Test
        connector = new SQLConnector();
        sqlStatements = new SQLStatements(connector);
        switch (permission_level){
            case "admin" -> {
                mainGui= new MainGui(connector, sqlStatements);
                mainGui.init();
            }
            case "user" -> {
                //TODO
            }
            case "testing" -> {
                mainForm = new MainInputGui();
                mainForm.init();
            }
        }
    }
}