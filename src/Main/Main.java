package Main;

import GUI.GUIS.Dialogs.PrinterDialog;
import GUI.GUIS.MainGui;
import GUI.GUIS.Dialogs.UserEntryDialog;
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

    private static final String startup_configuration = "7";

    public static Main m;

    public static void main(String[] args) {
        m = new Main();
        m.init();
    }

    private void init() {
        Constants.init();
        //ADWrapper.init();

        connector = new SQLConnector();
        sqlSelectStatements = new SQLSelectStatements(connector);
        sqlDeleteStatements = new SQLDeleteStatements(connector);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        switch (startup_configuration) {
            case "main" -> {
                mainGui = new MainGui(connector, sqlSelectStatements, sqlDeleteStatements);
                mainGui.init();
            }
            case "1" -> {
                ArbeitsmittelPrinter.print("l.schmidt", 1);
            }
            case "2" -> {
                UserManagmantGui gui = new UserManagmantGui();
                gui.init();
            }
            case "3" -> {
                try {
                    URI uri = new URL("https://example.com").toURI();
                    java.awt.Desktop.getDesktop().browse(uri);
                } catch (IOException | URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
            case "4" -> {
                LabelPrinter.print("NB-0001");
            }
            case "5" -> {
                System.out.println(ADWrapper.getFullName("d.vitale"));
            }
            case "6" -> {
                String s = "askdfj asdfjals aslkjfdjjdj<<ret>>";
                if (s.substring(s.length() - 7).equals("<<ret>>")){
                }
            }
            case "7" -> {
                PrinterDialog.start("l.schmidt", sqlSelectStatements.getUserAttributes("l.schmidt"));
            }
            case "8" -> {
         }
        }
    }
}