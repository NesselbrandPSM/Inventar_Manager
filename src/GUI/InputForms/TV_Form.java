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

    public void initData(String[] data) {
        manufacturer.setText(data[3]);

        int i;
        for (i = 0; i < companySet[0].length; i++) {
            System.out.println(companySet[0][i]);
            if (data[2].equals(companySet[0][i])){
                break;
            }
        }
        companys.setSelectedIndex(i);

        modell.setText(data[4]);
        device_name.setText(data[5]);
        s_number.setText(data[6]);
        resolution.setText(data[7]);
        tframe_version.setText(data[8]);
        s_version.setText(data[9]);
        a_version.setText(data[10]);
        ram.setText(data[11]);
        rom.setText(data[12]);
        purchase_date.setText(data[13]);
        price.setText(data[14]);
        warranty.setText(data[15]);

        for (i = 0; i < Constants.statusList.length; i++) {
            if (data[16].equals(Constants.statusList[i])){
                break;
            }
        }
        status.setSelectedIndex(i);

        room_nb.setText(data[17]);
        floor.setText(data[18]);
        dguv.setText(data[19]);
        note.setText(data[20]);

        for (i = 0; i < Constants.conditionList.length; i++) {
            if (data[21].equals(Constants.conditionList[i])){
                break;
            }
        }
        condition.setSelectedIndex(i);

        conditionNote.setText(data[22]);
    }
}
