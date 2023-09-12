package GUI;

import GUI.InputForms.*;
import SQL.SQLConnector;
import SQL.Statements.SQLInsertStatements;
import SQL.Statements.SQLSelectStatements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

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

    private Dockingstation_Form dockingstationForm;
    private Headset_Form headsetForm;
    private Monitor_Form monitorForm;
    private PC_Form pcForm;
    private Printer_Form printerForm;
    private Scanner_Form scannerForm;
    private Telephone_Form telephoneForm;
    private Desk_Form deskForm;

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
                case "PC" -> show(pcForm.getPcPanel());
                case "Printer" -> show(printerForm.getPrinterPanel());
                case "Scanner" -> show(scannerForm.getScannerPanel());
                case "Dockingstation" -> show(dockingstationForm.getDockingPanel());
                case "Headset" -> show(headsetForm.getHeadsetPanel());
                case "Monitor" -> show(monitorForm.getMonitorPanel());
                case "Telephone" -> show(telephoneForm.getTelephonePanel());
                case "Desk" -> show(deskForm.getDeskPanel());
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

        einfuegenButton.addActionListener(e -> inputEntry());
        abbrechenButton.addActionListener(e -> close());
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
                case "pc" -> sqlInsertStatements.inputPCEntry(pcForm.getArgs(currentIVNumber));
                case "scanner" -> {
                }
                case "printer" -> {
                }
                case "dockingstation" -> {
                }
                case "headset" -> {
                }
                case "monitor" -> {
                }
                case "telephone" -> {
                }
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
    }

    public void init() {
        try {
            frame = new JFrame("Einfügen");
            frame.setContentPane(new MainInputGui().panel1);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        } catch (Exception e){
            e.printStackTrace();
        }
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
    }

    private void createUIComponents() {
        inputPanel = new JPanel();
    }

    private void close() {
        try {
            frame.dispose();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}