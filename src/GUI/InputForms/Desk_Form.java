package GUI.InputForms;

import GUI.util.ComboBoxItem;
import Main.utility.Constants;
import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

import javax.swing.*;
import java.util.ArrayList;

public class Desk_Form {
    private JPanel deskPanel;
    private JComboBox status;
    private JComboBox companys;
    private JTextField floorTextField;
    private JTextField roomNBTextField;
    private JTextField manufacturer;
    private JTextField modell;
    private JTextField s_number;
    private JComboBox users;
    private JTextField purchaseDate;
    private JTextField purchasePrice;
    private JTextField warranty;
    private JComboBox pcIVNumberBox;
    private JCheckBox hasMouseCheckBox;
    private JCheckBox hasKeyboardCheckBox;
    private JCheckBox deskShareCheckBox;
    private JTextField note;
    private JComboBox dsIVNumberBox;
    private JComboBox hdIVNumberBox;
    private JComboBox scIVNumberBox;
    private JComboBox teIVNumberBox;
    private JComboBox mo1IVNumberBox;
    private JComboBox mo2IVNumberBox;
    private JComboBox condition;
    private JTextField conditionNote;

    private SQLConnector sqlConnector;
    private SQLSelectStatements sqlSelectStatements;

    private String[][] companySet;
    private String[][] userSet;

    public JPanel getDeskPanel() {
        return deskPanel;
    }

    public Desk_Form(){
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

        String[] ivNumbers = sqlSelectStatements.getAllIV_Numbers("pc");
        pcIVNumberBox.addItem(new ComboBoxItem(" - "));
        for (String s : ivNumbers) {
            pcIVNumberBox.addItem(new ComboBoxItem(s));
        }
        ivNumbers = sqlSelectStatements.getAllIV_Numbers("monitor");
        mo1IVNumberBox.addItem(new ComboBoxItem(" - "));
        mo2IVNumberBox.addItem(new ComboBoxItem(" - "));
        for (String s : ivNumbers) {
            mo1IVNumberBox.addItem(new ComboBoxItem(s));
            mo2IVNumberBox.addItem(new ComboBoxItem(s));
        }
        ivNumbers = sqlSelectStatements.getAllIV_Numbers("scanner");
        scIVNumberBox.addItem(new ComboBoxItem(" - "));
        for (String s : ivNumbers) {
            scIVNumberBox.addItem(new ComboBoxItem(s));
        }
        ivNumbers = sqlSelectStatements.getAllIV_Numbers("headset");
        hdIVNumberBox.addItem(new ComboBoxItem(" - "));
        for (String s : ivNumbers) {
            hdIVNumberBox.addItem(new ComboBoxItem(s));
        }
        ivNumbers = sqlSelectStatements.getAllIV_Numbers("telephone");
        teIVNumberBox.addItem(new ComboBoxItem(" - "));
        for (String s : ivNumbers) {
            teIVNumberBox.addItem(new ComboBoxItem(s));
        }
        ivNumbers = sqlSelectStatements.getAllIV_Numbers("dockingstation");
        dsIVNumberBox.addItem(new ComboBoxItem(" - "));
        for (String s : ivNumbers) {
            dsIVNumberBox.addItem(new ComboBoxItem(s));
        }

        for (String s : Constants.conditionList){
            condition.addItem(new ComboBoxItem(s));
        }
    }

    public String[] getArgs(String currentIVNumber) {
        ArrayList<String> args = new ArrayList<>();

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

        args.add(status.getSelectedItem().toString());
        args.add(roomNBTextField.getText());
        args.add(String.valueOf(deskShareCheckBox.isSelected()));
        args.add(floorTextField.getText());
        args.add(currentIVNumber);
        args.add(modell.getText());
        args.add(manufacturer.getText());
        args.add(s_number.getText());
        args.add(teIVNumberBox.getSelectedItem().toString());
        args.add(scIVNumberBox.getSelectedItem().toString());
        args.add(hdIVNumberBox.getSelectedItem().toString());
        args.add(dsIVNumberBox.getSelectedItem().toString());
        args.add(pcIVNumberBox.getSelectedItem().toString());
        args.add(String.valueOf(hasMouseCheckBox.isSelected()));
        args.add(String.valueOf(hasKeyboardCheckBox.isSelected()));
        args.add(mo1IVNumberBox.getSelectedItem().toString());
        args.add(mo2IVNumberBox.getSelectedItem().toString());
        args.add(purchaseDate.getText());
        args.add(purchasePrice.getText());
        args.add(warranty.getText());
        args.add(note.getText());
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

    private void uncheckBoxes(){
        hasKeyboardCheckBox.setSelected(false);
        hasMouseCheckBox.setSelected(false);
        deskShareCheckBox.setSelected(false);
    }

    private void resetInputFields(){
        uncheckBoxes();
        status.setSelectedIndex(0);
        companys.setSelectedIndex(0);
        floorTextField.setText("");
        roomNBTextField.setText("");
        manufacturer.setText("");
        modell.setText("");
        s_number.setText("");
        users.setSelectedIndex(0);
        purchaseDate.setText("");
        purchasePrice.setText("");
        warranty.setText("");
        pcIVNumberBox.setSelectedIndex(0);
        note.setText("");
        dsIVNumberBox.setSelectedIndex(0);
        hdIVNumberBox.setSelectedIndex(0);
        scIVNumberBox.setSelectedIndex(0);
        teIVNumberBox.setSelectedIndex(0);
        mo1IVNumberBox.setSelectedIndex(0);
        mo2IVNumberBox.setSelectedIndex(0);
    }
}