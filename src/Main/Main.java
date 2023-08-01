package Main;

import GUI.MainGui;
import SQL.SQLConnector;

public class Main {
    private SQLConnector connector;
    public MainGui mainGui;

    public static Main m;

    public static void main(String[] args) {
        m = new Main();
        m.init();
        //this is the master
    }

    private void init(){
        connector = new SQLConnector();
        mainGui= new MainGui(connector);
        mainGui.init();
    }
}