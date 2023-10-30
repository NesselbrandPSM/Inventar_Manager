package GUI.GUIS.Dialogs;

import Main.utility.Constants;
import Main.utility.Printer.ArbeitsmittelPrinter;
import Main.utility.UtilPrintables.IVObject;
import Main.utility.UtilPrintables.ReturnTripel;
import Main.utility.Utils;
import SQL.SQLConnector;
import SQL.Statements.SQLInsertStatements;
import SQL.Statements.SQLSelectStatements;
import SQL.Statements.SQLUpdateStatements;
import SQL.util.SQLStatement;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
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
    private JTextArea workContractDate;

    private DefaultListModel<String> userEntryListModel;
    private DefaultListModel<String> arbeitsmittelListModel;

    private SQLSelectStatements sqlSelectStatements;
    private SQLUpdateStatements sqlUpdateStatements;
    private SQLInsertStatements sqlInsertStatements;

    private ArrayList<String> addedIV_Numbers;
    private ArrayList<SQLStatement> retrievedIV_NumbersStatements;
    private ArrayList<ReturnTripel> returnTripels;

    public UserEntryDialog(String user) {
        this.setTitle("Nutzer Bearbeiten");
        addedIV_Numbers = new ArrayList<>();
        returnTripels = new ArrayList<>();
        retrievedIV_NumbersStatements = new ArrayList<>();
        sqlSelectStatements = new SQLSelectStatements(new SQLConnector());
        sqlUpdateStatements = new SQLUpdateStatements(new SQLConnector());
        sqlInsertStatements = new SQLInsertStatements(new SQLConnector());
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        String[] userData = sqlSelectStatements.getUserAttributes(user);

        if (!Objects.equals(userData[0], "-1")) {
            streetNRField.setText(userData[0].substring(0, userData[0].indexOf(" | ")));
            userData[0] = userData[0].substring(userData[0].indexOf(" | ") + 3);
            plzField.setText(userData[0].substring(0, userData[0].indexOf(" | ")));
            userData[0] = userData[0].substring(userData[0].indexOf(" | ") + 3);
            cityField.setText(userData[0]);
        }

        workingHours.setText(userData[1]);
        workingDays.setText(userData[2]);
        workContractDate.setText(userData[5]);

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
                Constants.setActive = false;
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
                        ConditionModifyDialog.init(s);
                        if (!Utils.newConditionStatus.equals("null")){
                            addedIV_Numbers.remove(s);
                            userEntryListModel.removeElement(s);
                            arbeitsmittelListModel.add(0, s);

                            SQLStatement statement = new SQLStatement(
                                    "update " + Utils.getTableFromShortCut(s.substring(0, 2)) + " SET inventory_user_key = '-1', current_status = 'edv eingelagert', c_note = '" + Utils.newConditionNote + "', c_status = '" + Utils.newConditionStatus + "' " +
                                            "where iv_number = '" + s + "'"
                            );
                            retrievedIV_NumbersStatements.add(statement);
                            statement = new SQLStatement(
                                    "update udmapping SET r_date = '" + Utils.getDateTimeNow() + "' where user = '" + userlabel.getText() + "' and iv_number = '" + s + "' and r_date = '-1'"
                            );
                            retrievedIV_NumbersStatements.add(statement);
                            ReturnTripel ret = new ReturnTripel(s, Utils.newConditionStatus, Utils.newConditionNote);
                            returnTripels.add(ret);

                            Utils.newConditionStatus = "null";
                            Utils.newConditionNote = "null";
                        }
                    }
                }
            }
            arbeitsmittelList.setSelectedIndex(-1);
            userEntryList.setSelectedIndex(-1);
        });
    }

    private void onOK() {
        Constants.setActive = true;
        SQLConnector sqlConnector = new SQLConnector();

        if (returnTripels.size() > 0){
            Constants.returnList = sqlSelectStatements.getReturnList(returnTripels);
            ArbeitsmittelPrinter.print("", 3);
        }

        for (SQLStatement statement : retrievedIV_NumbersStatements) {
            sqlConnector.query(statement);
        }

        for (String iv_number : addedIV_Numbers) {
            String[] data = new String[4];
            data[0] = userlabel.getText();
            data[1] = iv_number;
            data[2] = Utils.getDateTimeNow();
            String status = "in verwendung (hybrid)";
            sqlInsertStatements.inputUDStatement(data);
            sqlUpdateStatements.updateEntryUserAndStatus(data[0], iv_number, status, Utils.getTableFromShortCut(iv_number.substring(0, 2)));
        }

        String[] userData = new String[6];
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
        userData[5] = workContractDate.getText();

        sqlUpdateStatements.updateUserData(userlabel.getText(), userData);

        dispose();
    }

    public static void start(String user) {
        UserEntryDialog dialog = new UserEntryDialog(user);
        dialog.pack();
        dialog.setVisible(true);
    }
}