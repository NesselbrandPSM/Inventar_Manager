package GUI.InputForms;

import GUI.util.ComboBoxItem;
import Main.utility.Constants;
import SQL.SQLConnector;
import SQL.Statements.SQLInsertStatements;
import SQL.Statements.SQLSelectStatements;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Miscellaneous_Form {
    private JPanel mcPanel;
    private JComboBox status;
    private JComboBox condition;
    private JTextArea conditionNote;
    private JButton newButton;
    private JComboBox type;
    private JComboBox companys;
    private JTextField name;
    private JTextArea note;

    private String[][] companySet;

    private SQLConnector sqlConnector;
    private SQLSelectStatements sqlSelectStatements;
    private SQLInsertStatements sqlInsertStatements;

    public Miscellaneous_Form() {
        sqlConnector = new SQLConnector();
        sqlSelectStatements = new SQLSelectStatements(sqlConnector);
        sqlInsertStatements = new SQLInsertStatements(sqlConnector);

        for (String s : Constants.typs) {
            type.addItem(new ComboBoxItem(s));
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

        for (String s : Constants.conditionList) {
            condition.addItem(new ComboBoxItem(s));
        }


        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String m = JOptionPane.showInputDialog("Neuen Typ hinzufügen:");
                if (m != null) {
                    sqlInsertStatements.insertTyp(m);
                    Constants.refreshTyp();
                    type.removeAllItems();
                    for (String s : Constants.typs) {
                        type.addItem(new ComboBoxItem(s));
                    }
                }
            }
        });
    }

    public JPanel getPanel() {
        return mcPanel;
    }

    public String[] getArgs(String iv_number) {
        ArrayList<String> args = new ArrayList<>();

        args.add(iv_number);

        args.add("-1");

        String currentComp = companys.getSelectedItem().toString();
        for (int i = 0; i < companySet[0].length; i++) {
            if (currentComp.equals(companySet[0][i])) {
                args.add(companySet[1][i]);
            }
        }

        args.add(status.getSelectedItem().toString());
        args.add(condition.getSelectedItem().toString());
        args.add(conditionNote.getText());
        args.add(type.getSelectedItem().toString());
        args.add(name.getText());
        args.add(note.getText());


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
        conditionNote.setText("");
        name.setText("");
        note.setText("");
    }
}
