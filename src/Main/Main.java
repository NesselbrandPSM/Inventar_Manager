package Main;

import GUI.GUIS.MainGui;
import GUI.GUIS.UserEntryDialog;
import GUI.GUIS.UserManagmantGui;
import Main.utility.ADWrapper;
import Main.utility.Constants;
import Main.utility.Printer.ArbeitsmittelPrinter;
import Main.utility.Printer.LabelPrinter;
import SQL.SQLConnector;
import SQL.Statements.SQLDeleteStatements;
import SQL.Statements.SQLSelectStatements;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Main {
    private SQLConnector connector;
    public SQLSelectStatements sqlSelectStatements;
    private SQLDeleteStatements sqlDeleteStatements;
    public MainGui mainGui;

    //private static final String startup_configuration = "main";
    //private static final String startup_configuration = "testing2";
    //private static final String startup_configuration = "testing1";
    //private static final String startup_configuration = "testing6";
    private static final String startup_configuration = "testing7";

    public static Main m;

    public static void main(String[] args) {
        m = new Main();
        m.init();
    }

    private void init() {
        Constants.init();
        //ADWrapper.init();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        switch (startup_configuration) {
            case "main" -> {
                connector = new SQLConnector();
                sqlSelectStatements = new SQLSelectStatements(connector);
                sqlDeleteStatements = new SQLDeleteStatements(connector);
                mainGui = new MainGui(connector, sqlSelectStatements, sqlDeleteStatements);
                mainGui.init();
            }
            case "testing1" -> {
                ArbeitsmittelPrinter.print("l.schmidt", 1);
            }
            case "testing2" -> {
                UserManagmantGui gui = new UserManagmantGui();
                gui.init();
            }
            case "testing3" -> {
                try {
                    URI uri = new URL("https://example.com").toURI();
                    java.awt.Desktop.getDesktop().browse(uri);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
            case "testing4" -> {
                LabelPrinter.print("NB-0001");
            }
            case "testing5" -> {
                System.out.println(ADWrapper.getFullName("d.vitale"));
            }
            case "testing6" -> {
                String s = "askdfj asdfjals aslkjfdjjdj<<ret>>";
                if (s.substring(s.length() - 7).equals("<<ret>>")){
                }
            }
            case "testing7" -> {
                UserEntryDialog.start("l.schmidt");
            }
        }
    }
}