package GUI.InputForms;

import GUI.util.ComboBoxItem;
import Main.utility.Constants;

import javax.swing.*;
import java.util.ArrayList;

public class TV_Form {
    private JPanel tvPanel;
    private JTextArea note;
    private JTextArea conditionNote;
    private JComboBox status;
    private JComboBox condition;
    private JComboBox companys;
    private JTextField manufacturer;
    private JTextField modell;
    private JTextField s_number;
    private JTextField device_name;
    private JTextField ram;
    private JTextField rom;
    private JTextField resolution;
    private JTextField s_version;
    private JTextField tframe_version;
    private JTextField a_version;
    private JTextField floor;
    private JTextField room_nb;
    private JTextField purchase_date;
    private JTextField price;
    private JTextField warranty;
    private JTextField dguv;

    private String[][] companySet;

    public TV_Form() {
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

    public JPanel getTVPanel() {
        return tvPanel;
    }

    public String[] getArgs(String currentIVNumber) {
        ArrayList<String> args = new ArrayList<>();

        args.add(currentIVNumber);
        args.add(tframe_version.getText());
        args.add(s_version.getText());
        args.add(a_version.getText());
        args.add(device_name.getText());
        args.add(s_number.getText());
        args.add(ram.getText());
        args.add(rom.getText());
        args.add(resolution.getText());
        args.add(purchase_date.getText());
        args.add(price.getText());
        args.add(note.getText());
        args.add(condition.getSelectedItem().toString());
        args.add(conditionNote.getText());

        String currentComp = companys.getSelectedItem().toString();
        for (int i = 0; i < companySet[0].length; i++) {
            if (currentComp.equals(companySet[0][i])) {
                args.add(companySet[1][i]);
            }
        }

        args.add(manufacturer.getText());
        args.add(modell.getText());
        args.add(status.getSelectedItem().toString());
        args.add(dguv.getText());
        args.add(warranty.getText());
        args.add(room_nb.getText());
        args.add(floor.getText());


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
        note.setText("");
        conditionNote.setText("");
        manufacturer.setText("");
        modell.setText("");
        s_number.setText("");
        device_name.setText("");
        ram.setText("");
        rom.setText("");
        resolution.setText("");
        s_version.setText("");
        tframe_version.setText("");
        a_version.setText("");
        floor.setText("");
        room_nb.setText("");
        purchase_date.setText("");
        price.setText("");
        warranty.setText("");
        dguv.setText("");
    }
}
