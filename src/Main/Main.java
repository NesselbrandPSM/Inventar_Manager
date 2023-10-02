package Main;

import GUI.MainInputGui;
import GUI.MainGui;
import GUI.UserEditGui;
import Main.utility.ADWrapper;
import Main.utility.Constants;
import Main.utility.ArbeitsmittelPrinter;
import Main.utility.LabelPrinter;
import SQL.SQLConnector;
import SQL.Statements.SQLDeleteStatements;
import SQL.Statements.SQLSelectStatements;

import javax.swing.*;

public class Main {
    private SQLConnector connector;
    public SQLSelectStatements sqlSelectStatements;
    private SQLDeleteStatements sqlDeleteStatements;
    public MainGui mainGui;

    private static final String startup_configuration = "main";

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
                sqlDeleteStatements = new SQLDeleteStatements(connector);
                mainGui= new MainGui(connector, sqlSelectStatements, sqlDeleteStatements);
                mainGui.init();
            }
            case "testing1" -> {
                ArbeitsmittelPrinter.print("Leonard Schmidt");
            }
            case "testing2" -> {
                UserEditGui gui = new UserEditGui();
                gui.init();
            }
            case "testing3" -> {
                MainInputGui gui = new MainInputGui();
                gui.init();
            }
            case "testing4" -> {
                LabelPrinter.print("NB-0001");
            }
        }
    }
}