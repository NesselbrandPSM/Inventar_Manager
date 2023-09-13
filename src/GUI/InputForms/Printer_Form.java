package GUI.InputForms;

import GUI.util.ComboBoxItem;
import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

public class Printer_Form {
    private JPanel printerPanel;
    private JTextField currentStatus;
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

    private String[][] companySet;

    private SQLSelectStatements sqlSelectStatements;

    public Printer_Form() {
        sqlSelectStatements = new SQLSelectStatements(new SQLConnector());

        companys.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                companys.removeAllItems();
                String[] companysArr = sqlSelectStatements.getAllCompanys()[0];
                companySet = sqlSelectStatements.getAllCompanys();
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

        args.add(currentStatus.getText());

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
        eq_number.setText("");
        ip.setText("");
        purchaseDate.setText("");
        purchasePrice.setText("");
        warranty.setText("");
        dguv.setText("");
    }
}
