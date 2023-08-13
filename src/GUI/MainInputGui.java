package GUI;

import GUI.InputForms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class MainInputGui {
    private JCheckBox PCCheckBox;
    private JPanel panel1;
    private JCheckBox PrinterCheckBox;
    private JCheckBox ScannerCheckBox;
    private JCheckBox DockingStationCheckBox;
    private JCheckBox HeadsetCheckBox;
    private JCheckBox MonitorCheckBox;
    private JCheckBox TelephoneCheckBox;
    private JButton einfeugenButton;
    private JButton abbrechenButton;
    private JPanel inputPanel;

    private HashMap<String, JPanel> forms;

    public MainInputGui(){
        initForms();

        ActionListener listener = e -> {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            uncheckBoxes();
            checkBox.setSelected(true);
            inputPanel.removeAll();
            inputPanel.add(forms.get(checkBox.getText()));
        };

        PCCheckBox.addActionListener(listener);
        PrinterCheckBox.addActionListener(listener);
        ScannerCheckBox.addActionListener(listener);
        DockingStationCheckBox.addActionListener(listener);
        HeadsetCheckBox.addActionListener(listener);
        MonitorCheckBox.addActionListener(listener);
        TelephoneCheckBox.addActionListener(listener);
    }

    private void initForms() {
        forms = new HashMap<>();
        forms.put("Dockingstation", new Dockingstation_Form().getDockingPanel());
        forms.put("Headset", new Headset_Form().getHeadsetPanel());
        forms.put("Monitor", new Monitor_Form().getMonitorPanel());
        forms.put("PC", new PC_Form().getPcPanel());
        forms.put("Printer", new Printer_Form().getPrinterPanel());
        forms.put("Scanner", new Scanner_Form().getScannerPanel());
        forms.put("Telephone", new Telephone_Form().getTelephonePanel());
    }

    public void init(){

        JFrame frame = new JFrame("Einfügen");
        frame.setContentPane(new MainInputGui().panel1);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(1000, 700));
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
