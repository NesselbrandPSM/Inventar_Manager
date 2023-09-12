package GUI;

import GUI.util.UserTableModell;
import Main.utility.ADWrapper;
import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

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
    private static JFrame frame;

    private UserTableModell userTableModell;

    private SQLSelectStatements sqlSelectStatements;

    public UserEditGui() {
        sqlSelectStatements = new SQLSelectStatements(new SQLConnector());

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
        fertigButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO write userdata to sql database
            }
        });
        aktualisierenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
        databaseSyncenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ADWrapper.syncDatabase();
            }
        });
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

    private void update() {
        userTableModell.update(sqlSelectStatements.getAllUsersTableModel(getCheckedBox()));
    }

    private void createUIComponents() {
        userTableModell = new UserTableModell(new SQLSelectStatements(new SQLConnector()));
        String[][] data = new SQLSelectStatements(new SQLConnector()).getAllUsersTableModel(2);
        userTableModell.update(data);
        userTable = new JTable(userTableModell);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}