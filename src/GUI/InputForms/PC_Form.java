package GUI.InputForms;

import GUI.util.ComboBoxItem;
import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PC_Form {
    private JPanel pcPanel;
    private JCheckBox PCCheckBox;
    private JCheckBox laptopCheckBox;
    private JCheckBox tabletCheckBox;
    private JCheckBox thinclientCheckBox;
    private JTextField currentStatus;
    private JTextField manufacturer;
    private JTextField modell;
    private JTextField s_number;
    private JTextField cpu;
    private JTextField ram;
    private JTextField rom;
    private JTextField os;
    private JTextField ip;
    private JTextField lastUpdate;
    private JTextField purchaseDate;
    private JTextField purchaseNumber;
    private JTextField purchasePrice;
    private JTextField warranty;
    private JTextField note;
    private JComboBox users;
    private JComboBox companys;

    private String pcType = "";

    private SQLConnector sqlConnector;
    private SQLSelectStatements sqlSelectStatements;

    public PC_Form() {
        //sqlConnector = new SQLConnector();
        //sqlSelectStatements = new SQLSelectStatements(sqlConnector);

        ActionListener listener = e -> {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            uncheckBoxes();
            checkBox.setSelected(true);
            switch (checkBox.getText()){
                case "PC" -> pcType = "PC";
                case "Laptop" -> pcType = "NB";
                case "Tablet" -> pcType = "TB";
                case "ThinClient" -> pcType = "TC";
            }
        };

        PCCheckBox.addActionListener(listener);
        laptopCheckBox.addActionListener(listener);
        tabletCheckBox.addActionListener(listener);
        thinclientCheckBox.addActionListener(listener);
        companys.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                companys.removeAllItems();
                //TODO get all companys
            }
        });
        users.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                users.removeAllItems();
                //TODO get all users
            }
        });
        purchaseDate.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                
            }
        });
        lastUpdate.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                //TODO select lastUpdate Date
            }
        });
    }

    private void uncheckBoxes(){
        laptopCheckBox.setSelected(false);
        PCCheckBox.setSelected(false);
        tabletCheckBox.setSelected(false);
        thinclientCheckBox.setSelected(false);
    }

    public JPanel getPcPanel() {
        return pcPanel;
    }

    public String getPcType() {
        return pcType;
    }
}