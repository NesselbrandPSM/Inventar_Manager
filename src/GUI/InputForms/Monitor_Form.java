package GUI.InputForms;

import GUI.util.ComboBoxItem;
import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

public class Monitor_Form {
    private JPanel monitorPanel;
    private JTextField currentStatus;
    private JComboBox companys;
    private JTextField manufacturer;
    private JTextField modell;
    private JTextField s_number;
    private JTextField resolution;
    private JTextField purchaseDate;
    private JTextField purchasePrice;
    private JTextField warranty;
    private JComboBox users;
    private JTextField hdmi;
    private JTextField dp;
    private JTextField dvi;
    private JTextField vga;
    private JTextField dguv;
    private JTextField note;
    private JComboBox status;

    private SQLSelectStatements sqlSelectStatements;

    private String[][] companySet;
    private String[][] userSet;

    public Monitor_Form() {
        sqlSelectStatements = new SQLSelectStatements(new SQLConnector());

        for (String s : sqlSelectStatements.getStatusList()) {
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
        users.addItem(new ComboBoxItem(""));
        for (String s : usersArr) {
            users.addItem(new ComboBoxItem(s));
        }

        companys.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                companys.removeAllItems();
                companySet = sqlSelectStatements.getAllCompanys();
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
                userSet = sqlSelectStatements.getAllUsers();
                String[] usersArr = userSet[0];
                users.addItem(new ComboBoxItem(""));
                for (String s : usersArr) {
                    users.addItem(new ComboBoxItem(s));
                }
            }
        });
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

        args.add(manufacturer.getText());
        args.add(s_number.getText());
        args.add(currentStatus.getText());
        args.add(dguv.getText());
        args.add(resolution.getText());
        args.add(currentIVNumber);
        args.add(modell.getText());
        args.add(purchaseDate.getText());
        args.add(purchasePrice.getText());
        args.add(warranty.getText());
        args.add(hdmi.getText());
        args.add(dp.getText());
        args.add(vga.getText());
        args.add(dvi.getText());
        args.add(note.getText());

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
        currentStatus.setText("");
        manufacturer.setText("");
        modell.setText("");
        s_number.setText("");
        resolution.setText("");
        purchaseDate.setText("");
        purchasePrice.setText("");
        warranty.setText("");
        hdmi.setText("");
        dp.setText("");
        dvi.setText("");
        vga.setText("");
        dguv.setText("");
        note.setText("");
    }

    public JPanel getMonitorPanel() {
        return monitorPanel;
    }
}
