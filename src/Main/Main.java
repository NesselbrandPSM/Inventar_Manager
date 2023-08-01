package Main;

import GUI.MainGui;
import SQL.SQLConnector;
import SQL.Statements.SQLSequenzStatements;

public class Main {
    private SQLConnector connector;
    private SQLSequenzStatements sqlSequenzStatements;
    public MainGui mainGui;

    public static Main m;

    public static void main(String[] args) {
        m = new Main();
        m.init();
    }

    private void init(){
        connector = new SQLConnector();
        sqlSequenzStatements = new SQLSequenzStatements(connector);
        mainGui= new MainGui(connector, sqlSequenzStatements);
        mainGui.init();
    }
}