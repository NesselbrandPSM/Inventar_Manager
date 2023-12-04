package GUI.GUIS.Dialogs;

import GUI.util.ComboBoxItem;
import Main.utility.Constants;
import Main.utility.Settings;
import SQL.SQLConnector;
import SQL.Statements.SQLUpdateStatements;

import javax.swing.*;
import java.awt.event.*;

public class SettingsDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox standardStatusComboBox;
    private JTextField loginCredentialsPath;

    private SQLUpdateStatements sqlUpdateStatements;

    private String[] companys;

    public SettingsDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        sqlUpdateStatements = new SQLUpdateStatements(new SQLConnector());

        for (String s : Constants.statusList) {
            standardStatusComboBox.addItem(new ComboBoxItem(s));
        }
        int i = 0;
        for (i = 0; i < Constants.statusList.length; i++) {
            if (Constants.statusList[i].equals(Settings.standardStatusAfterDeviceLoan)){
                break;
            }
        }
        standardStatusComboBox.setSelectedIndex(i);


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if (!standardStatusComboBox.getSelectedItem().toString().equals(Settings.standardStatusAfterDeviceLoan)){
            sqlUpdateStatements.updateSetting("standardStatusAfterDeviceLoan", standardStatusComboBox.getSelectedItem().toString());
            Settings.standardStatusAfterDeviceLoan = standardStatusComboBox.getSelectedItem().toString();
        }
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public void init() {
        SettingsDialog dialog = new SettingsDialog();
        dialog.pack();
        dialog.setVisible(true);
        dialog.setTitle("Einstellungen");
    }
}
