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
    public JTextField note;
    private JComboBox users;
    private JComboBox companys;
    public JTextField dguv;
    private JComboBox status;
    private JComboBox condition;
    private JTextField conditionNote;

    private String pcType = "";

    private SQLConnector sqlConnector;
    private SQLSelectStatements sqlSelectStatements;

    private String[][] companySet;
    private String[][] userSet;

    public PC_Form() {
        sqlConnector = new SQLConnector();
        sqlSelectStatements = new SQLSelectStatements(sqlConnector);

        for (String s : Constants.statusList) {
            status.addItem(new ComboBoxItem(s));
        }
        companys.removeAllItems();
        companySet = sqlSelectStatements.getAllCompanys();
        String[] companysArr = companySet[0];
        for (String s : companysArr) {
            companys.addItem(new ComboBoxItem(s));
        }
        users.removeAllItems();
        userSet = sqlSelectStatements.getAllUsers();
        String[] usersArr = userSet[0];
        users.addItem(new ComboBoxItem(" - "));
        for (String s : usersArr) {
            users.addItem(new ComboBoxItem(s));
        }

        for (String s : Constants.conditionList){
            condition.addItem(new ComboBoxItem(s));
        }

        ActionListener listener = e -> {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            uncheckBoxes();
            checkBox.setSelected(true);
            switch (checkBox.getText()){
                case "PC" -> pcType = "PC";
                case "Laptop" -> pcType = "NB";
                case "Tablet" -> pcType = "TB";
                case "ThinClient" -> pcType = "TC";
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
        users.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                users.removeAllItems();
                String[] usersArr = userSet[0];
                users.addItem(new ComboBoxItem(""));
                for (String s : usersArr) {
                    users.addItem(new ComboBoxItem(s));
                }
            }
        });
    }

    private void uncheckBoxes(){
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
            if (currentComp.equals(companySet[0][i])){
                args.add(companySet[1][i]);
            }
        }
        boolean hasUser = false;
        String currentUser = users.getSelectedItem().toString();
        for (int x = 0; x < userSet[0].length; x++) {
            if (currentUser.equals(userSet[0][x])){
                args.add(userSet[0][x]);
                hasUser = true;
            }
        }
        if (!hasUser){
            args.add("-1");
        }

        args.add(purchaseDate.getText());
        args.add(purchasePrice.getText());
        args.add(warranty.getText());
        args.add(condition.getSelectedItem().toString());
        args.add(conditionNote.getText());

        String[] arguments = new String[args.size()];
        for (int j = 0; j < arguments.length; j++) {
            arguments[j] = args.get(j);
            if (arguments[j].equals("")){
                arguments[j] = " - ";
            }
        }

        resetInputFields();
        return arguments;
    }

    private void resetInputFields(){
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
    }
}