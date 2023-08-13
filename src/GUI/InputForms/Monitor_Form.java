package GUI.InputForms;

import GUI.util.ComboBoxItem;
import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Monitor_Form {
    private JPanel monitorPanel;
    private JTextField currentStatus;
    private JComboBox companys;
    private JTextField manufacturer;
    private JTextField modell;
    private JTextField s_number;
    private JTextField resolution;
    private JTextField purchaseDate;
    private JTextField purchaseNumber;
    private JTextField purchasePrice;
    private JTextField warranty;

    private SQLSelectStatements sqlSelectStatements;

    public Monitor_Form() {
        sqlSelectStatements = new SQLSelectStatements(new SQLConnector());

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

    public JPanel getMonitorPanel() {
        return monitorPanel;
    }
}
