package GUI;

import GUI.util.UserTableModell;
import Main.utility.ADWrapper;
import SQL.SQLConnector;
import SQL.Statements.SQLDeleteStatements;
import SQL.Statements.SQLSelectStatements;
import SQL.Statements.SQLUpdateStatements;

import javax.swing.*;
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
    private static JFrame frame;

    private UserTableModell userTableModell;

    private SQLSelectStatements sqlSelectStatements;
    private SQLUpdateStatements sqlUpdateStatements;
    private SQLDeleteStatements sqlDeleteStatements;

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