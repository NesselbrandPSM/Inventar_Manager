package GUI.InputForms;

import GUI.util.ComboBoxItem;
import Main.utility.Constants;
import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

public class Printer_Form {
    private JPanel printerPanel;
    private JComboBox companys;
    private JTextField manufacturer;
    private JTextField modell;
    private JTextField s_number;
    private JTextField eq_number;
    private JTextField ip;
    private JTextField purchaseDate;
    private JTextField purchasePrice;
    private JTextField warranty;
    private JTextField dguv;
    private JComboBox status;
    private JComboBox condition;
    private JTextArea conditionNote;
    private JTextArea note;
    private JTextField roomNBTextField;
    private JTextField floorTextField;

    private String[][] companySet;

    private SQLSelectStatements sqlSelectStatements;

    public Printer_Form() {
        sqlSelectStatements = new SQLSelectStatements(new SQLConnector());

        for (String s : Constants.conditionList){
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
                String[] companysArr = sqlSelectStatements.getAllCompanys()[0];
                for (String s : companysArr) {
                    companys.addItem(new ComboBoxItem(s));
                }
            }
        });
    }

    public JPanel getPrinterPanel() {
        return printerPanel;
    }

    public String[] getArgs(String currentIVNumber){
        ArrayList<String> args = new ArrayList<>();

        args.add(status.getSelectedItem().toString());

        String currentComp = companys.getSelectedItem().toString();
        for (int i = 0; i < companySet[0].length; i++) {
            if (currentComp.equals(companySet[0][i])){
                args.add(companySet[1][i]);
            }
        }

        args.add(manufacturer.getText());
        args.add(modell.getText());
        args.add(s_number.getText());
        args.add(eq_number.getText());
        args.add(ip.getText());
        args.add(purchaseDate.getText());
        args.add(purchasePrice.getText());
        args.add(warranty.getText());
        args.add(dguv.getText());
        args.add(currentIVNumber);
        args.add(note.getText());
        args.add(condition.getSelectedItem().toString());
        args.add(conditionNote.getText());
        args.add(roomNBTextField.getText());
        args.add(floorTextField.getText());

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
        manufacturer.setText("");
        modell.setText("");
        s_number.setText("");
        eq_number.setText("");
        ip.setText("");
        purchaseDate.setText("");
        purchasePrice.setText("");
        warranty.setText("");
        dguv.setText("");
        note.setText("");
        floorTextField.setText("");
        roomNBTextField.setText("");
    }

    public void initData(String[] data) {
        manufacturer.setText(data[2]);
        modell.setText(data[3]);

        int i;
        for (i = 0; i < companySet[0].length; i++) {
            System.out.println(companySet[0][i]);
            if (data[4].equals(companySet[0][i])){
                break;
            }
        }
        companys.setSelectedIndex(i);

        purchaseDate.setText(data[5]);
        purchasePrice.setText(data[6]);
        warranty.setText(data[7]);
        eq_number.setText(data[8]);
        s_number.setText(data[9]);

        for (i = 0; i < Constants.statusList.length; i++) {
            if (data[10].equals(Constants.statusList[i])){
                break;
            }
        }
        status.setSelectedIndex(i);

        dguv.setText(data[11]);
        ip.setText(data[12]);
        floorTextField.setText(data[13]);
        roomNBTextField.setText(data[14]);
        note.setText(data[15]);

        for (i = 0; i < Constants.conditionList.length; i++) {
            if (data[16].equals(Constants.conditionList[i])){
                break;
            }
        }
        condition.setSelectedIndex(i);

        conditionNote.setText(data[17]);
    }
}
