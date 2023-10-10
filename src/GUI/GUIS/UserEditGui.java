package GUI.GUIS;

import GUI.util.UserTableModell;
import Main.utility.ADWrapper;
import Main.utility.Printer.ArbeitsmittelPrinter;
import SQL.SQLConnector;
import SQL.Statements.SQLDeleteStatements;
import SQL.Statements.SQLSelectStatements;
import SQL.Statements.SQLUpdateStatements;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserEditGui {
    private JPanel userEditPanel;
    private JCheckBox aktivCheckBox;
    private JCheckBox neuCheckBox;
    private JCheckBox inactivecheckBox;
    private JCheckBox allCheckBox;
    private JButton abbrechenButton;
    private JButton fertigButton;
    private JTable userTable;
    private JScrollPane userTableScrollPane;
    private JButton aktualisierenButton;
    private JButton databaseSyncenButton;
    private JButton aktivButton;
    private JButton inaktivButton;
    private JButton neuButton;
    private JButton löschenButton;
    private JButton druckButton;
    private JCheckBox überlassungCheckBox;
    private JCheckBox homeOfficeCheckBox;
    private JCheckBox arbeitsmittelCheckBox;
    private JTextArea addressArea;
    private JTextArea hoursArea;
    private JTextArea daysArea;
    private JButton editButton;
    private static JFrame frame;

    private UserTableModell userTableModell;

    private SQLSelectStatements sqlSelectStatements;
    private SQLUpdateStatements sqlUpdateStatements;
    private SQLDeleteStatements sqlDeleteStatements;

    private static int userDataEditState = 0;

    public UserEditGui() {
        sqlUpdateStatements = new SQLUpdateStatements(new SQLConnector());
        sqlDeleteStatements = new SQLDeleteStatements(new SQLConnector());

        JTableHeader tableHeader = userTable.getTableHeader();
        tableHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
        userTable.setTableHeader(tableHeader);

        ActionListener listener = e -> {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            uncheckBoxes();
            checkBox.setSelected(true);
            update();
        };

        allCheckBox.addActionListener(listener);
        neuCheckBox.addActionListener(listener);
        inactivecheckBox.addActionListener(listener);
        aktivCheckBox.addActionListener(listener);

        allCheckBox.setSelected(true);
        abbrechenButton.addActionListener(e -> close());
        fertigButton.addActionListener(e -> close());
        aktualisierenButton.addActionListener(e -> update());
        databaseSyncenButton.addActionListener(e -> {
            ADWrapper.syncDatabase();
            update();
        });

        aktivButton.addActionListener(e -> changeStatus(1));
        inaktivButton.addActionListener(e -> changeStatus(-1));
        neuButton.addActionListener(e -> changeStatus(0));
        löschenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = ((String[]) userTableModell.getRow(userTable.getSelectedRow()))[0];
                String[] options = {"JA", "NEIN"};
                int i = JOptionPane.showOptionDialog(null, "Wollen sie Nutzer " + user + " wirklich löschen?",
                        "Einfügen",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        0);
                if (i == 0) {
                    sqlDeleteStatements.deleteUser(user);
                    update();
                }
            }
        });
        druckButton.addActionListener(e -> {
            String name = (String) userTableModell.getRow(userTable.getSelectedRow())[0];
            if (überlassungCheckBox.isSelected()){
                ArbeitsmittelPrinter.print(name, 0);
            }
            if (homeOfficeCheckBox.isSelected()){
                ArbeitsmittelPrinter.print(name, 1);
            }
            if (arbeitsmittelCheckBox.isSelected()){
                ArbeitsmittelPrinter.print(name, 2);
            }
            uncheckPrinterBoxes();
        });

        ListSelectionModel selectionModel = userTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow >= 0){
                    String userName = userTableModell.getRow(selectedRow)[0].toString();
                    String[] data = sqlSelectStatements.getUserInfos(userName);
                    for (int i = 0; i < data.length; i++) {
                        if (data[i] == null){
                            data[i] = " - ";
                        } else if (data[i].equals("")){
                            data[i] = " - ";
                        }
                    }
                    addressArea.setText(data[2]);
                    hoursArea.setText(data[3]);
                    daysArea.setText(data[4]);
                }
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(userDataEditState);
                if (userDataEditState == 0){
                    userDataEditState = 1;
                    addressArea.setEnabled(true);
                    addressArea.setEditable(true);
                    hoursArea.setEnabled(true);
                    hoursArea.setEditable(true);
                    daysArea.setEditable(true);
                    daysArea.setEnabled(true);

                    addressArea.setForeground(Color.RED);
                    hoursArea.setForeground(Color.RED);
                    daysArea.setForeground(Color.RED);

                    editButton.setForeground(Color.RED);

                    editButton.setText("Fertig");
                } else if (userDataEditState == 1){
                    String[] updateData  = new String[3];
                    updateData[0] = addressArea.getText();
                    updateData[1] = hoursArea.getText();
                    updateData[2] = daysArea.getText();

                    sqlUpdateStatements.updateUserData(userTableModell.getRow(userTable.getSelectedRow())[0].toString(), updateData);

                    userDataEditState = 0;
                    editButton.setText("Edit");
                    addressArea.setEnabled(false);
                    addressArea.setEditable(false);
                    hoursArea.setEnabled(false);
                    hoursArea.setEditable(false);
                    daysArea.setEditable(false);
                    daysArea.setEnabled(false);

                    addressArea.setForeground(Color.black);
                    hoursArea.setForeground(Color.black);
                    daysArea.setForeground(Color.black);

                    editButton.setForeground(Color.BLACK);
                }
            }
        });
    }

    private void uncheckPrinterBoxes(){
        arbeitsmittelCheckBox.setSelected(false);
        homeOfficeCheckBox.setSelected(false);
        überlassungCheckBox.setSelected(false);
    }

    private void changeStatus(int status) {
        String[] row = (String[]) userTableModell.getRow(userTable.getSelectedRow());
        changeStatus(row[0], status);
    }

    private void changeStatus(String name, int status) {
        sqlUpdateStatements.updateUserStatus(name, status);
        update();
    }

    public void init() {
        frame = new JFrame("UserEditGui");
        frame.setContentPane(new UserEditGui().userEditPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private void uncheckBoxes() {
        aktivCheckBox.setSelected(false);
        neuCheckBox.setSelected(false);
        inactivecheckBox.setSelected(false);
        allCheckBox.setSelected(false);
    }

    private void close() {
        frame.dispose();
    }

    private int getCheckedBox() {
        if (allCheckBox.isSelected()) {
            return 2;
        }
        if (neuCheckBox.isSelected()) {
            return 0;
        }
        if (inactivecheckBox.isSelected()) {
            return -1;
        }
        if (aktivCheckBox.isSelected()) {
            return 1;
        }
        return -100;
    }

    public void update(int x) {
        String[][] data = sqlSelectStatements.getAllUsersTableModel(x);
        for (int i = 0; i < data.length; i++) {
            switch (data[i][2]) {
                case "0" -> data[i][2] = "neu";
                case "-1" -> data[i][2] = "inaktiv";
                case "1" -> data[i][2] = "aktiv";
            }
        }
        userTableModell.update(data);
    }

    private void update() {
        update(getCheckedBox());
    }

    private void createUIComponents() {
        sqlSelectStatements = new SQLSelectStatements(new SQLConnector());
        userTableModell = new UserTableModell(new SQLSelectStatements(new SQLConnector()));
        update(2);
        userTable = new JTable(userTableModell);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}