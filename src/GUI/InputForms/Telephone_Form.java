package GUI.InputForms;

import GUI.util.ComboBoxItem;
import Main.utility.Constants;
import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

public class Telephone_Form {
    private JPanel telephonePanel;
    private JComboBox companys;
    private JTextField manufacturer;
    private JTextField modell;
    private JTextField s_number;
    private JTextField ip;
    private JTextField purchaseDate;
    private JTextField purchaseNumber;
    private JTextField purchasePrice;
    private JTextField warranty;
    private JTextField dguv;
    private JComboBox status;
    private JComboBox condition;
    private JTextArea note;
    private JTextArea conditionNote;
    private JTextField rufNummer;
    private JTextField tarif;
    private JTextField dpPin;
    private JTextField simPin;
    private JTextField simNummer;

    private SQLSelectStatements sqlSelectStatements;

    private String[][] companySet;

    public Telephone_Form() {
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

    public String[] getArgs(String currentIVNumber) {
        ArrayList<String> args = new ArrayList<>();

        args.add(currentIVNumber);

        String currentComp = companys.getSelectedItem().toString();
        for (int i = 0; i < companySet[0].length; i++) {
            if (currentComp.equals(companySet[0][i])) {
                args.add(companySet[1][i]);
            }
        }

        args.add("-1");

        args.add(manufacturer.getText());
        args.add(s_number.getText());
        args.add(status.getSelectedItem().toString());
        args.add(dguv.getText());
        args.add(modell.getText());
        args.add(ip.getText());
        args.add(purchaseDate.getText());
        args.add(purchasePrice.getText());
        args.add(warranty.getText());
        args.add(note.getText());
        args.add(condition.getSelectedItem().toString());
        args.add(conditionNote.getText());
        args.add(rufNummer.getText());
        args.add(simNummer.getText());
        args.add(simPin.getText());
        args.add(dpPin.getText());
        args.add(tarif.getText());

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
        ip.setText("");
        purchaseDate.setText("");
        purchaseNumber.setText("");
        purchasePrice.setText("");
        warranty.setText("");
        note.setText("");
        dguv.setText("");
        rufNummer.setText("");
        tarif.setText("");
        dpPin.setText("");
        simPin.setText("");
        simNummer.setText("");
    }

    public JPanel getTelephonePanel() {
        return telephonePanel;
    }

    public void initData(String[] data) {
        manufacturer.setText(data[2]);

        int i;
        for (i = 0; i < companySet[0].length; i++) {
            System.out.println(companySet[0][i]);
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
        ip.setText(data[13]);
        rufNummer.setText(data[14]);
        tarif.setText(data[15]);
        dpPin.setText(data[16]);
        simNummer.setText(data[17]);
        simPin.setText(data[18]);
        note.setText(data[19]);

        for (i = 0; i < Constants.conditionList.length; i++) {
            if (data[20].equals(Constants.conditionList[i])){
                break;
            }
        }
        condition.setSelectedIndex(i);
        condition.setToolTipText(data[21]);
    }
}
