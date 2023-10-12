package GUI.GUIS;

import Main.utility.UtilPrintables.IVObject;
import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;
import SQL.Statements.SQLUpdateStatements;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class UserEntryDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField userlabel;
    private JTextArea streetNRField;
    private JTextArea plzField;
    private JTextArea cityField;
    private JCheckBox homeofficeCheckBox;
    private JPanel panel1;
    private JList arbeitsmittelList;
    private JList userEntryList;
    private JButton toUserListButton;
    private JButton removeFromUserList;
    private JTextArea workingDays;
    private JTextArea workingHours;
    private JCheckBox entryTransferCheckbox;

    private DefaultListModel<String> userEntryListModel;
    private DefaultListModel<String> arbeitsmittelListModel;

    private SQLSelectStatements sqlSelectStatements;
    private SQLUpdateStatements sqlUpdateStatements;

    private ArrayList<String> addedIV_Numbers;

    public UserEntryDialog(String user) {
        addedIV_Numbers = new ArrayList<>();
        sqlSelectStatements = new SQLSelectStatements(new SQLConnector());
        sqlUpdateStatements = new SQLUpdateStatements(new SQLConnector());
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        String[] userData = sqlSelectStatements.getUserAttributes(user);

        if (userData[0] != null) {
            streetNRField.setText(userData[0].substring(0, userData[0].indexOf(" | ")));
            userData[0] = userData[0].substring(userData[0].indexOf(" | ") + 3);
            plzField.setText(userData[0].substring(0, userData[0].indexOf(" | ")));
            userData[0] = userData[0].substring(userData[0].indexOf(" | ") + 3);
            cityField.setText(userData[0]);
        }

        workingHours.setText(userData[1]);
        workingDays.setText(userData[2]);

        if (Objects.equals(userData[3], "1")){
            homeofficeCheckBox.setSelected(true);
        }
        if (Objects.equals(userData[4], "1")){
            entryTransferCheckbox.setSelected(true);
        }

        ArrayList<IVObject> userEntrys = sqlSelectStatements.getUserObjects(user);
        userEntryListModel = new DefaultListModel<>();
        int i = 0;
        for (IVObject ivo : userEntrys) {
            userEntryListModel.add(i, ivo.values[0]);
            i++;
        }
        userEntryList.setModel(userEntryListModel);

        String[] ivNumbers = sqlSelectStatements.getAllFreeEntrys();
        arbeitsmittelListModel = new DefaultListModel<>();
        i = 0;
        for (String s : ivNumbers) {
            arbeitsmittelListModel.add(i, s);
            i++;
        }
        arbeitsmittelList.setModel(arbeitsmittelListModel);
        arbeitsmittelList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2){
                    //Doubleclicklistener
                }
            }
        });

        //region okCancelButton
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        buttonCancel.addActionListener(e -> dispose());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        contentPane.registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        //endregion

        userlabel.setText(user);


        toUserListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (arbeitsmittelList.getSelectedIndex() >= 0){
                    int[] indizes = arbeitsmittelList.getSelectedIndices();
                    String[] entrys = new String[indizes.length];
                    for (int i = 0; i < indizes.length; i++) {
                        entrys[i] = arbeitsmittelListModel.get(indizes[i]);
                    }

                    for (int i = entrys.length -1; i >= 0; i--) {
                        arbeitsmittelListModel.removeElement(entrys[i]);
                        userEntryListModel.add(0, entrys[i]);
                        addedIV_Numbers.add(entrys[i]);
                    }
                }
                arbeitsmittelList.setSelectedIndex(-1);
                userEntryList.setSelectedIndex(-1);
            }
        });

        removeFromUserList.addActionListener(e -> {
            if (userEntryList.getSelectedIndex() >= 0){
                int[] indizes = userEntryList.getSelectedIndices();
                String[] entrys = new String[indizes.length];
                for (int i1 = 0; i1 < indizes.length; i1++) {
                    entrys[i1] = userEntryListModel.get(indizes[i1]);
                }

                for (String s : entrys) {
                    if (addedIV_Numbers.contains(s)){
                        addedIV_Numbers.remove(s);
                        userEntryListModel.removeElement(s);
                        arbeitsmittelListModel.add(0, s);
                    } else {
                        //TODO statusänderung von zurückgegebenen items in liste mit sqlabfragen
                    }
                }
            }
            arbeitsmittelList.setSelectedIndex(-1);
            userEntryList.setSelectedIndex(-1);
        });
    }

    private void onOK() {
        //TODO alle iv_nummern aus liste bearbeiten
        String[] userData = new String[5];
        userData[0] = streetNRField.getText() + " | " + plzField.getText() + " | " + cityField.getText();
        userData[1] = workingHours.getText();
        userData[2] = workingDays.getText();
        if (homeofficeCheckBox.isSelected()){
            userData[3] = "1";
        } else {
            userData[3] = "0";
        }
        if (entryTransferCheckbox.isSelected()){
            userData[4] = "1";
        } else {
            userData[4] = "0";
        }

        sqlUpdateStatements.updateUserData(userlabel.getText(), userData);

        dispose();
    }

    public static void start(String user) {
        UserEntryDialog dialog = new UserEntryDialog(user);
        dialog.pack();
        dialog.setVisible(true);
    }
}