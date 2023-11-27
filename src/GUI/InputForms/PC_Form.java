package GUI.InputForms;

import GUI.util.ComboBoxItem;
import Main.utility.Constants;
import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

public class PC_Form {
    private JPanel pcPanel;
    private JCheckBox PCCheckBox;
    private JCheckBox laptopCheckBox;
    private JCheckBox tabletCheckBox;
    private JCheckBox thinclientCheckBox;
    public JTextField manufacturer;
    public JTextField modell;
    public JTextField s_number;
    public JTextField cpu;
    public JTextField ram;
    public JTextField rom;
    public JTextField os;
    public JTextField ip;
    public JTextField lastUpdate;
    private JTextField purchaseDate;
    private JTextField purchasePrice;
    private JTextField warranty;
    private JComboBox companys;
    public JTextField dguv;
    private JComboBox status;
    private JComboBox condition;
    private JTextArea note;
    private JTextArea conditionNote;
    private JTextField eSimNumber;
    private JTextField eSimPin;

    private String pcType = "";

    private String[][] companySet;

    public PC_Form() {
        for (String s : Constants.statusList) {
            status.addItem(new ComboBoxItem(s));
        }
        companys.removeAllItems();
        companySet = Constants.getCompanySet();
        String[] companysArr = companySet[0];
        for (String s : companysArr) {
            companys.addItem(new ComboBoxItem(s));
        }

        for (String s : Constants.conditionList) {
            condition.addItem(new ComboBoxItem(s));
        }

        ActionListener listener = e -> {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            uncheckBoxes();
            checkBox.setSelected(true);
            switch (checkBox.getText()) {
                case "PC":
                    pcType = "PC";
                    break;
                case "Laptop":
                    pcType = "NB";
                    break;
                case "Tablet":
                    pcType = "TB";
                    break;
                case "ThinClient":
                    pcType = "TC";
                    break;
            }
        };

        PCCheckBox.addActionListener(listener);
        laptopCheckBox.addActionListener(listener);
        tabletCheckBox.addActionListener(listener);
        thinclientCheckBox.addActionListener(listener);
        companys.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                companys.removeAllItems();
                String[] companysArr = companySet[0];
                for (String s : companysArr) {
                    companys.addItem(new ComboBoxItem(s));
                }
            }
        });
    }

    private void uncheckBoxes() {
        laptopCheckBox.setSelected(false);
        PCCheckBox.setSelected(false);
        tabletCheckBox.setSelected(false);
        thinclientCheckBox.setSelected(false);
    }

    public JPanel getPcPanel() {
        return pcPanel;
    }

    public String getPcType() {
        return pcType;
    }

    public String[] getArgs(String currentIVNumber) {
        ArrayList<String> args = new ArrayList<>();
        args.add(currentIVNumber);
        args.add(getPcType());
        args.add(status.getSelectedItem().toString());
        args.add("");
        args.add("");
        args.add(manufacturer.getText());
        args.add(modell.getText());
        args.add(s_number.getText());
        args.add(cpu.getText());
        args.add(ram.getText());
        args.add(rom.getText());
        args.add(os.getText());
        args.add(ip.getText());
        args.add(lastUpdate.getText());
        args.add("");
        args.add("");
        args.add("");
        args.add("");
        args.add(dguv.getText());
        args.add(note.getText());

        String currentComp = companys.getSelectedItem().toString();
        for (int i = 0; i < companySet[0].length; i++) {
            if (currentComp.equals(companySet[0][i])) {
                args.add(companySet[1][i]);
            }
        }

        args.add("-1");

        args.add(purchaseDate.getText());
        args.add(purchasePrice.getText());
        args.add(warranty.getText());
        args.add(condition.getSelectedItem().toString());
        args.add(conditionNote.getText());
        args.add(eSimNumber.getText());
        args.add(eSimPin.getText());

        String[] arguments = new String[args.size()];
        for (int j = 0; j < arguments.length; j++) {
            arguments[j] = args.get(j);
            if (arguments[j].equals("")) {
                arguments[j] = " - ";
            }
        }

        resetInputFields();
        return arguments;
    }

    private void resetInputFields() {
        uncheckBoxes();
        manufacturer.setText("");
        modell.setText("");
        s_number.setText("");
        cpu.setText("");
        ram.setText("");
        rom.setText("");
        os.setText("");
        ip.setText("");
        lastUpdate.setText("");
        purchaseDate.setText("");
        purchasePrice.setText("");
        warranty.setText("");
        note.setText("");
        dguv.setText("");
        eSimNumber.setText("");
        eSimPin.setText("");
        conditionNote.setText("");
    }
}