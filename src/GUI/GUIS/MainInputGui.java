package GUI.GUIS;

import GUI.InputForms.*;
import Main.utility.Printer.ArbeitsmittelPrinter;
import Main.utility.Printer.LabelPrinter;
import SQL.SQLConnector;
import SQL.Statements.SQLInsertStatements;
import SQL.Statements.SQLSelectStatements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainInputGui {
    private JCheckBox PCCheckBox;
    private JPanel panel1;
    private JCheckBox PrinterCheckBox;
    private JCheckBox ScannerCheckBox;
    private JCheckBox DockingStationCheckBox;
    private JCheckBox HeadsetCheckBox;
    private JCheckBox MonitorCheckBox;
    private JCheckBox TelephoneCheckBox;
    private JButton einfuegenButton;
    private JButton abbrechenButton;
    private JPanel inputPanel;
    private JCheckBox DeskCheckBox;
    private JCheckBox miscCheckBox;
    private JButton einfuegenLabelDruckenButton;
    private JCheckBox tvCheckBox;
    private JCheckBox routerCheckBox;

    private Dockingstation_Form dockingstationForm;
    private Headset_Form headsetForm;
    private Monitor_Form monitorForm;
    private PC_Form pcForm;
    private Printer_Form printerForm;
    private Scanner_Form scannerForm;
    private Telephone_Form telephoneForm;
    private Desk_Form deskForm;
    private Miscellaneous_Form miscellaneousForm;
    private TV_Form tvForm;
    private Router_Form routerForm;

    private SQLSelectStatements sqlSelectStatements;
    private SQLInsertStatements sqlInsertStatements;

    private static JFrame frame;

    public MainInputGui() {
        sqlSelectStatements = new SQLSelectStatements(new SQLConnector());
        sqlInsertStatements = new SQLInsertStatements(new SQLConnector());

        initForms();

        ActionListener listener = e -> {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            uncheckBoxes();
            checkBox.setSelected(true);

            switch (checkBox.getText()) {
                case "PC":
                    show(pcForm.getPcPanel());
                    break;
                case "Printer":
                    show(printerForm.getPrinterPanel());
                    break;
                case "Scanner":
                    show(scannerForm.getScannerPanel());
                    break;
                case "Dockingstation":
                    show(dockingstationForm.getDockingPanel());
                    break;
                case "Headset":
                    show(headsetForm.getHeadsetPanel());
                    break;
                case "Monitor":
                    show(monitorForm.getMonitorPanel());
                    break;
                case "Telephone":
                    show(telephoneForm.getTelephonePanel());
                    break;
                case "Desk":
                    show(deskForm.getDeskPanel());
                    break;
                case "Misc":
                    show(miscellaneousForm.getPanel());
                    break;
                case "Router":
                    show(routerForm.getRtPanel());
                    break;
                case "TV":
                    show(tvForm.getTVPanel());
            }
        };

        PCCheckBox.addActionListener(listener);
        PrinterCheckBox.addActionListener(listener);
        ScannerCheckBox.addActionListener(listener);
        DockingStationCheckBox.addActionListener(listener);
        HeadsetCheckBox.addActionListener(listener);
        MonitorCheckBox.addActionListener(listener);
        TelephoneCheckBox.addActionListener(listener);
        DeskCheckBox.addActionListener(listener);
        miscCheckBox.addActionListener(listener);
        tvCheckBox.addActionListener(listener);
        routerCheckBox.addActionListener(listener);

        einfuegenButton.addActionListener(e -> inputEntry());
        abbrechenButton.addActionListener(e -> close());
        einfuegenLabelDruckenButton.addActionListener(e -> inputEntryLabel());
    }

    private void inputEntry() {
        String currentTable = inputPanel.getComponent(0).getName();
        String currentIVNumber = sqlSelectStatements.getCurrentIV_number(currentTable);
        if (currentIVNumber == null) {
            return;
        }
        String[] options = {"JA", "NEIN"};
        int i = JOptionPane.showOptionDialog(null, "Einfügen des Datensatztes mit Inventar Nummer: " + currentIVNumber,
                "Einfügen",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                0);
        if (i == 0) {
            switch (currentTable) {
                case "pc":
                    sqlInsertStatements.inputPCEntry(pcForm.getArgs(currentIVNumber));
                    break;
                case "scanner":
                    sqlInsertStatements.inputSCEntry(scannerForm.getArgs(currentIVNumber));
                    break;
                case "printer":
                    sqlInsertStatements.inputPREntry(printerForm.getArgs(currentIVNumber));
                    break;
                case "dockingstation":
                    sqlInsertStatements.inputDSEntry(dockingstationForm.getArgs(currentIVNumber));
                    break;
                case "headset":
                    sqlInsertStatements.inputHDEntry(headsetForm.getArgs(currentIVNumber));
                    break;
                case "monitor":
                    sqlInsertStatements.inputMOEntry(monitorForm.getArgs(currentIVNumber));
                    break;
                case "telephone":
                    sqlInsertStatements.inputTEEntry(telephoneForm.getArgs(currentIVNumber));
                    break;
                case "desk":
                    sqlInsertStatements.inputDKEntry(deskForm.getArgs(currentIVNumber));
                    break;
                case "miscellaneous":
                    sqlInsertStatements.inputMCEntry(miscellaneousForm.getArgs(currentIVNumber));
                    break;
                case "tv":
                    sqlInsertStatements.inputTVEntry(tvForm.getArgs(currentIVNumber));
                    break;
                case "router":
                    sqlInsertStatements.inputRTEntry(routerForm.getArgs(currentIVNumber));
                    break;
            }
        }
    }

    private void inputEntryLabel() {
        String currentTable = inputPanel.getComponent(0).getName();
        String currentIVNumber = sqlSelectStatements.getCurrentIV_number(currentTable);
        if (currentIVNumber == null) {
            return;
        }
        String[] options = {"JA", "NEIN"};
        int i = JOptionPane.showOptionDialog(null, "Einfügen des Datensatztes mit Inventar Nummer: " + currentIVNumber,
                "Einfügen",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                0);
        if (i == 0) {
            LabelPrinter.print(currentIVNumber);
            switch (currentTable) {
                case "pc":
                    sqlInsertStatements.inputPCEntry(pcForm.getArgs(currentIVNumber));
                    break;
                case "scanner":
                    sqlInsertStatements.inputSCEntry(scannerForm.getArgs(currentIVNumber));
                    break;
                case "printer":
                    sqlInsertStatements.inputPREntry(printerForm.getArgs(currentIVNumber));
                    break;
                case "dockingstation":
                    sqlInsertStatements.inputDSEntry(dockingstationForm.getArgs(currentIVNumber));
                    break;
                case "headset":
                    sqlInsertStatements.inputHDEntry(headsetForm.getArgs(currentIVNumber));
                    break;
                case "monitor":
                    sqlInsertStatements.inputMOEntry(monitorForm.getArgs(currentIVNumber));
                    break;
                case "telephone":
                    sqlInsertStatements.inputTEEntry(telephoneForm.getArgs(currentIVNumber));
                    break;
                case "desk":
                    sqlInsertStatements.inputDKEntry(deskForm.getArgs(currentIVNumber));
                    break;
                case "miscellaneous":
                    sqlInsertStatements.inputMCEntry(miscellaneousForm.getArgs(currentIVNumber));
                    break;
                case "tv":
                    sqlInsertStatements.inputTVEntry(tvForm.getArgs(currentIVNumber));
                    break;
                case "router":
                    sqlInsertStatements.inputRTEntry(routerForm.getArgs(currentIVNumber));
                    break;
            }
        }
    }


    private void show(JPanel panel) {
        inputPanel.removeAll();
        inputPanel.add(panel);
        inputPanel.revalidate();
        inputPanel.repaint();
    }

    private void initForms() {
        dockingstationForm = new Dockingstation_Form();
        dockingstationForm.getDockingPanel().setName("dockingstation");
        headsetForm = new Headset_Form();
        headsetForm.getHeadsetPanel().setName("headset");
        monitorForm = new Monitor_Form();
        monitorForm.getMonitorPanel().setName("monitor");
        pcForm = new PC_Form();
        pcForm.getPcPanel().setName("pc");
        printerForm = new Printer_Form();
        printerForm.getPrinterPanel().setName("printer");
        scannerForm = new Scanner_Form();
        scannerForm.getScannerPanel().setName("scanner");
        telephoneForm = new Telephone_Form();
        telephoneForm.getTelephonePanel().setName("telephone");
        deskForm = new Desk_Form();
        deskForm.getDeskPanel().setName("desk");
        miscellaneousForm = new Miscellaneous_Form();
        miscellaneousForm.getPanel().setName("miscellaneous");
        tvForm = new TV_Form();
        tvForm.getTVPanel().setName("tv");
        routerForm = new Router_Form();
        routerForm.getRtPanel().setName("router");
    }

    public void init() {
        try {
            frame = new JFrame("Einfügen");
            frame.setContentPane(new MainInputGui().panel1);
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.pack();
            frame.setVisible(false);
            frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show(boolean bool){
        frame.setVisible(bool);
    }

    private void uncheckBoxes() {
        PCCheckBox.setSelected(false);
        PrinterCheckBox.setSelected(false);
        ScannerCheckBox.setSelected(false);
        DockingStationCheckBox.setSelected(false);
        HeadsetCheckBox.setSelected(false);
        MonitorCheckBox.setSelected(false);
        TelephoneCheckBox.setSelected(false);
        DeskCheckBox.setSelected(false);
        miscCheckBox.setSelected(false);
        tvCheckBox.setSelected(false);
        routerCheckBox.setSelected(false);
    }

    private void createUIComponents() {
        inputPanel = new JPanel();
    }

    private void close() {
        try {
            frame.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}