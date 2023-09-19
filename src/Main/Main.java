package Main;

import GUI.MainInputGui;
import GUI.MainGui;
import GUI.UserEditGui;
import Main.utility.ADWrapper;
import Main.utility.Constants;
import Main.utility.Printer;
import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

import javax.swing.*;

public class Main {
    private SQLConnector connector;
    public SQLSelectStatements sqlSelectStatements;
    public MainGui mainGui;

    private static final String startup_configuration = "testing1";

    public static Main m;

    public static void main(String[] args) {
        m = new Main();
        m.init();
    }

    private void init(){
        Constants.init();
        ADWrapper.init();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        switch (startup_configuration){
            case "main" -> {
                connector = new SQLConnector();
                sqlSelectStatements = new SQLSelectStatements(connector);
                mainGui= new MainGui(connector, sqlSelectStatements);
                mainGui.init();
            }
            case "testing1" -> {
                Printer.print();
            }
            case "testing2" -> {
                UserEditGui gui = new UserEditGui();
                gui.init();
            }
            case "testing3" -> {
                MainInputGui gui = new MainInputGui();
                gui.init();
            }
        }
    }
}