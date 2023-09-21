package GUI.InputForms;

import GUI.util.ComboBoxItem;
import Main.utility.Constants;
import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

public class Scanner_Form {
    private JPanel scannerPanel;
    private JComboBox companys;
    private JTextField manufacturer;
    private JTextField modell;
    private JTextField s_number;
    private JTextField ip;
    private JTextField purchaseDate;
    private JTextField purchasePrice;
    private JTextField warranty;
    private JComboBox users;
    private JTextField dguv;
    private JComboBox status;
    private JComboBox condition;
    private JTextArea note;
    private JTextArea conditionNote;

    private SQLSelectStatements sqlSelectStatements;

    private String[][] companySet;
    private String[][] userSet;

    public Scanner_Form() {
        sqlSelectStatements = new SQLSelectStatements(new SQLConnector());

        for (String s : Constants.conditionList){
            condition.addItem(new ComboBoxItem(s));
        }

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

    public JPanel getScannerPanel() {
        return scannerPanel;
    }

    public String[] getArgs(String currentIVNumber) {
        ArrayList<String> args = new ArrayList<>();

        args.add(currentIVNumber);

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
        args.add(dguv.getText());
        args.add(s_number.getText());
        args.add(manufacturer.getText());
        args.add(modell.getText());
        args.add(ip.getText());
        args.add(purchaseDate.getText());
        args.add(purchasePrice.getText());
        args.add(warranty.getText());
        args.add(note.getText());
        args.add(condition.getSelectedItem().toString());
        args.add(conditionNote.getText());

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

    private void resetInputFields(){
        manufacturer.setText("");
        modell.setText("");
        s_number.setText("");
        ip.setText("");
        purchaseDate.setText("");
        purchasePrice.setText("");
        warranty.setText("");
        dguv.setText("");
        note.setText("");
    }
}
