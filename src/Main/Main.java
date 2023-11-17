package Main;

import GUI.GUIS.Dialogs.PrinterDialog;
import GUI.GUIS.MainGui;
import GUI.GUIS.UserManagmantGui;
import Main.utility.ADWrapper;
import Main.utility.Constants;
import Main.utility.Printer.ArbeitsmittelPrinter;
import Main.utility.Printer.LabelPrinter;
import SQL.SQLConnector;
import SQL.Statements.SQLDeleteStatements;
import SQL.Statements.SQLSelectStatements;
import SQL.util.SQLStatement;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    private SQLConnector connector;
    public SQLSelectStatements sqlSelectStatements;
    private SQLDeleteStatements sqlDeleteStatements;
    public MainGui mainGui;

    private static final String version_code = "0.1";

    private static final String startup_configuration = "main";

    public static Main m;

    public static void main(String[] args) {
        m = new Main();
        m.init();
    }

    private void init() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        if (maintainenceTest()) {
            JOptionPane.showMessageDialog(null, "Die Datenbank befindet sich gerade in Wartungsarbeiten.\nBitte probieren sie es später " + "noch einmal oder\nwenden sie sich an ihren Systemadministrator.");
            return;
        }

        if (versionTest()) {
            return;
        }

        Constants.init();
        ADWrapper.init();

        connector = new SQLConnector();
        sqlSelectStatements = new SQLSelectStatements(connector);
        sqlDeleteStatements = new SQLDeleteStatements(connector);

        switch (startup_configuration) {
            case "main": {
                mainGui = new MainGui(connector, sqlSelectStatements, sqlDeleteStatements);
                mainGui.init();
                break;
            }
            case "1": {
                ArbeitsmittelPrinter.print("l.schmidt", 1);
                break;
            }
            case "2": {
                UserManagmantGui gui = new UserManagmantGui();
                gui.init();
                break;
            }
            case "3": {
                try {
                    URI uri = new URL("https://example.com").toURI();
                    java.awt.Desktop.getDesktop().browse(uri);
                } catch (IOException | URISyntaxException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            case "4": {
                LabelPrinter.print("NB-0001");
                break;
            }
            case "5": {
                System.out.println(ADWrapper.getFullName("m.wolf"));
                break;
            }
            case "6": {
                String s = "askdfj asdfjals aslkjfdjjdj<<ret>>";
                if (s.substring(s.length() - 7).equals("<<ret>>")) {
                }
                break;
            }
            case "7": {
                PrinterDialog.start("l.schmidt", sqlSelectStatements.getUserAttributes("l.schmidt"));
                break;
            }
        }
    }

    private boolean versionTest() {
        String s;
        try {
            SQLConnector con = new SQLConnector();
            ResultSet res = con.query(new SQLStatement("select p_value from properties where p_name = 'version'"));
            res.next();
            s = res.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!s.equals(version_code)){
            JOptionPane.showMessageDialog(null, "Sie verwenden eine alte Version der Inventarisierungssoftware: "
                    + version_code + "\n" + "Es wird Version " + s + " benötigt, um auf die Datenbank zugreifen zu können.");
        }
        return !s.equals(version_code);
    }

    private boolean versionTestDummy() {
        return false;
    }

    private boolean maintainenceTest() {
        File f = new File("\\\\Apocare-data\\it\\Inventarisierung\\maintenance - true");
        return f.exists();
    }

    private boolean maintainenceTestDummy() {
        return false;
    }
}