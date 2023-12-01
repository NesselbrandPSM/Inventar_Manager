package GUI.InputForms;

import GUI.util.ComboBoxItem;
import Main.utility.Constants;

import javax.swing.*;
import java.util.ArrayList;

public class Router_Form {
    private JPanel rtPanel;
    private JTextArea note;
    private JTextArea conditionNote;
    private JComboBox status;
    private JComboBox condition;
    private JComboBox companys;
    private JTextField manufacturer;
    private JTextField modell;
    private JTextField s_number;
    private JTextField ip;
    private JTextField patchBoxNbr;
    private JTextField floor;
    private JTextField roomNb;
    private JTextField dguv;
    private JTextField purchaseDate;
    private JTextField purchasePrice;
    private JTextField warranty;

    private String[][] companySet;

    public Router_Form() {
        status.removeAllItems();
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
    }

    public JPanel getRtPanel() {
        return rtPanel;
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

        args.add(s_number.getText());
        args.add(roomNb.getText());
        args.add(floor.getText());
        args.add(patchBoxNbr.getText());
        args.add(status.getSelectedItem().toString());
        args.add(dguv.getText());
        args.add(note.getText());
        args.add(manufacturer.getText());
        args.add(modell.getText());
        args.add(ip.getText());
        args.add(purchaseDate.getText());
        args.add(purchasePrice.getText());
        args.add(warranty.getText());
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
        ip.setText("");
        purchaseDate.setText("");
        purchasePrice.setText("");
        warranty.setText("");
        note.setText("");
        dguv.setText("");
        patchBoxNbr.setText("");
        floor.setText("");
        roomNb.setText("");
        conditionNote.setText("");
    }

    public void initData(String[] data) {
        int i;
        for (i = 0; i < companySet[0].length; i++) {
            if (data[2].equals(companySet[0][i])){
                break;
            }
        }
        companys.setSelectedIndex(i);

        manufacturer.setText(data[3]);
        modell.setText(data[4]);
        s_number.setText(data[5]);
        ip.setText(data[6]);
        patchBoxNbr.setText(data[7]);
        roomNb.setText(data[8]);
        floor.setText(data[9]);

        for (i = 0; i < Constants.statusList.length; i++) {
            if (data[10].equals(Constants.statusList[i])){
                break;
            }
        }
        status.setSelectedIndex(i);

        dguv.setText(data[11]);
        note.setText(data[12]);
        purchaseDate.setText(data[13]);
        purchasePrice.setText(data[14]);
        warranty.setText(data[15]);

        for (i = 0; i < Constants.conditionList.length; i++) {
            if (data[16].equals(Constants.conditionList[i])){
                break;
            }
        }
        condition.setSelectedIndex(i);

        conditionNote.setText(data[17]);
    }
}
