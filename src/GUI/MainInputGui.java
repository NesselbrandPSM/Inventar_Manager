package GUI;

import GUI.InputForms.*;

import javax.swing.*;
import java.awt.*;
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

    private Dockingstation_Form dockingstationForm;
    private Headset_Form headsetForm;
    private Monitor_Form monitorForm;
    private PC_Form pcForm;
    private Printer_Form printerForm;
    private Scanner_Form scannerForm;
    private Telephone_Form telephoneForm;

    public MainInputGui(){
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
            }
        };

        PCCheckBox.addActionListener(listener);
        PrinterCheckBox.addActionListener(listener);
        ScannerCheckBox.addActionListener(listener);
        DockingStationCheckBox.addActionListener(listener);
        HeadsetCheckBox.addActionListener(listener);
        MonitorCheckBox.addActionListener(listener);
        TelephoneCheckBox.addActionListener(listener);
    }

    private void show(JPanel panel){
        inputPanel.removeAll();
        inputPanel.add(panel);
        inputPanel.revalidate();
        inputPanel.repaint();
    }

    private void initForms() {
        dockingstationForm = new Dockingstation_Form();
        headsetForm = new Headset_Form();
        monitorForm = new Monitor_Form();
        pcForm = new PC_Form();
        printerForm = new Printer_Form();
        scannerForm = new Scanner_Form();
        telephoneForm = new Telephone_Form();
    }

    public void init(){
        JFrame frame = new JFrame("Einfügen");
        frame.setContentPane(new MainInputGui().panel1);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(1000, 750));
    }

    private void uncheckBoxes(){
        PCCheckBox.setSelected(false);
        PrinterCheckBox.setSelected(false);
        ScannerCheckBox.setSelected(false);
        DockingStationCheckBox.setSelected(false);
        HeadsetCheckBox.setSelected(false);
        MonitorCheckBox.setSelected(false);
        TelephoneCheckBox.setSelected(false);
    }

    private void createUIComponents() {
        inputPanel = new JPanel();
    }
}
