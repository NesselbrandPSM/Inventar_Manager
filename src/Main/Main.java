package Main;

import GUI.InputForms.Main_Form;
import GUI.MainGui;
import SQL.SQLConnector;
import SQL.Statements.SQLStatements;

public class Main {
    private SQLConnector connector;
    public SQLStatements sqlStatements;
    public MainGui mainGui;
    public Main_Form mainForm;

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
            case "admin" -> {
                mainGui= new MainGui(connector, sqlStatements);
                mainGui.init();
            }
            case "user" -> {
                //TODO
            }
            case "testing" -> {
                mainForm = new Main_Form();
            }
        }
    }
}