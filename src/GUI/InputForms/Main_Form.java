package GUI.InputForms;

import GUI.MainGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main_Form {
    private JCheckBox PCCheckBox;
    private JPanel panel1;
    private JCheckBox PrinterCheckBox;
    private JCheckBox ScannerCheckBox;
    private JCheckBox DockingStationCheckBox;
    private JCheckBox HeadsetCheckBox;
    private JCheckBox MonitorCheckBox;
    private JCheckBox TelephoneCheckBox;

    public Main_Form(){

        ActionListener listener = e -> {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            System.out.println(checkBox.getText());
        };

        PCCheckBox.addActionListener(listener);
        PrinterCheckBox.addActionListener(listener);
        ScannerCheckBox.addActionListener(listener);
        DockingStationCheckBox.addActionListener(listener);
        HeadsetCheckBox.addActionListener(listener);
        MonitorCheckBox.addActionListener(listener);
        TelephoneCheckBox.addActionListener(listener);
    }

    public void init(){
        JFrame frame = new JFrame("Einfügen");
        frame.setContentPane(new Main_Form().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
}
