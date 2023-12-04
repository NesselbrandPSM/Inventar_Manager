package GUI.InputForms;

import GUI.util.ComboBoxItem;
import Main.utility.Constants;
import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

public class Headset_Form {
    private JPanel headsetPanel;
    private JComboBox companys;
    private JTextField manufacturer;
    private JTextField modell;
    private JTextField s_number;
    private JTextField purchaseDate;
    private JTextField purchasePrice;
    private JTextField warranty;
    private JTextField dguv;
    private JComboBox status;
    private JComboBox condition;
    private JTextArea note;
    private JTextArea conditionNote;

    private SQLSelectStatements sqlSelectStatements;

    private String[][] companySet;

    public Headset_Form() {
        sqlSelectStatements = new SQLSelectStatements(new SQLConnector());

        for (String s : Constants.conditionList) {
            condition.addItem(new ComboBoxItem(s));
        }
        for (String s : Constants.statusList) {
            status.addItem(new ComboBoxItem(s));
        }
        companys.removeAllItems();
        companySet = Constants.getCompanySet();
        String[] companysArr = companySet[0];
        for (String s : companysArr) {
            companys.addItem(new ComboBoxItem(s));
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
    }

    public JPanel getHeadsetPanel() {
        return headsetPanel;
    }

    public String[] getArgs(String currentIVNumber) {
        ArrayList<String> args = new ArrayList<>();

        args.add(currentIVNumber);
        args.add(manufacturer.getText());
        args.add(s_number.getText());
        args.add(status.getSelectedItem().toString());
        args.add(modell.getText());
        args.add(dguv.getText());

        String currentComp = companys.getSelectedItem().toString();
        for (int i = 0; i < companySet[0].length; i++) {
            if (currentComp.equals(companySet[0][i])) {
                args.add(companySet[1][i]);
            }
        }

        args.add(purchaseDate.getText());
        args.add(purchasePrice.getText());
        args.add(warranty.getText());

        args.add("-1");

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

    private void resetInputFields() {
        manufacturer.setText("");
        modell.setText("");
        s_number.setText("");
        purchaseDate.setText("");
        purchasePrice.setText("");
        warranty.setText("");
        dguv.setText("");
        note.setText("");
    }

    public void initData(String[] data) {
        manufacturer.setText(data[2]);

        int i;
        for (i = 0; i < companySet[0].length; i++) {
            if (data[3].equals(companySet[0][i])){
                break;
            }
        }
        companys.setSelectedIndex(i);

        modell.setText(data[4]);

        purchaseDate.setText(data[7]);
        purchasePrice.setText(data[8]);
        warranty.setText(data[9]);

        for (i = 0; i < Constants.statusList.length; i++) {
            if (data[10].equals(Constants.statusList[i])){
                break;
            }
        }
        status.setSelectedIndex(i);

        dguv.setText(data[11]);
        s_number.setText(data[12]);
        note.setText(data[13]);

        for (i = 0; i < Constants.conditionList.length; i++) {
            if (data[14].equals(Constants.conditionList[i])){
                break;
            }
        }
        condition.setSelectedIndex(i);

        conditionNote.setText(data[15]);
    }
}
