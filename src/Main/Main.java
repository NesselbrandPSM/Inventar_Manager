package Main;

import GUI.MainInputGui;
import GUI.MainGui;
import GUI.UserEditGui;
import Main.utility.ADWrapper;
import Main.utility.Constants;
import Main.utility.Printer;
import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

public class Main {
    private SQLConnector connector;
    public SQLSelectStatements sqlSelectStatements;
    public MainGui mainGui;

    private static final String permission_level = "admin";

    public static Main m;

    public static void main(String[] args) {
        m = new Main();
        m.init();
    }

    private void init(){
        switch (permission_level){
            case "admin" -> {
                ADWrapper.init();
                Constants.init();
                connector = new SQLConnector();
                sqlSelectStatements = new SQLSelectStatements(connector);
                mainGui= new MainGui(connector, sqlSelectStatements);
                mainGui.init();
            }
            case "user" -> {
            }
            case "testing1" -> {
                Printer.print();
            }
            case "testing2" -> {
                ADWrapper.init();
                UserEditGui gui = new UserEditGui();
                gui.init();
            }
        }
    }
}