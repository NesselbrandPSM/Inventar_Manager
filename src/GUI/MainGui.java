package GUI;

import GUI.util.ShowAllTableModel;
import GUI.util.StatusList;
import Main.utility.Utils;
import SQL.SQLConnector;
import SQL.Statements.SQLStatement;
import SQL.util.IllegalSQLStatementException;
import SQL.util.TableAttributes;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class MainGui {
    private JPanel panel1;
    private JButton suchenButton;
    private JTextField searchTextField;
    private JTable table1;
    private JTextArea textArea1;
    private JButton elementEinfügenButton;
    private JButton elementBearbeitenButton;
    public JButton aktuallisierenButton;
    private JComboBox searchSelectorComboBox;
    private JTextArea textArea2;
    private JTextPane selectedItemTextPane;
    private JLabel statusLabel;
    private JScrollPane tableScrollPane;
    private JCheckBox PCcheckBox1;

    private SQLConnector connector;
    private ShowAllTableModel tableModel;
    private StatusList statusList;

    private int flags;

    private static boolean showAll = true;

    public MainGui(SQLConnector connector) {
        this.connector = connector;
        this.statusList = new StatusList(statusLabel);
        statusList.start();

        textArea1.setText("");
        for (TableAttributes attr : TableAttributes.values()) {
            textArea1.append(attr.toGermanString() + ":\n");
        }

        JTableHeader tableHeader = table1.getTableHeader();
        tableHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
        table1.setTableHeader(tableHeader);

        //region updatetableThread
        class UpdateTableThread extends Thread {
            @Override
            public void run() {
                while (true) {
                    if (isShowAll()) {
                        try {
                            int i = table1.getSelectedRow();
                            updateShowAllTableModel();
                            table1.getSelectionModel().setSelectionInterval(0, i);
                            Thread.sleep(60 * 1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        new UpdateTableThread().start();
        //endregion

        aktuallisierenButton.addActionListener(e -> {
            int i = table1.getSelectedRow();
            if (i < 0) {
                selectedItemTextPane.setText("no item selected");
                selectedItemTextPane.setBackground(null);
                textArea2.setText("");
                textArea2.setDisabledTextColor(new Color(2, 126, 254));
            }
            updateShowAllTableModel();
            table1.getSelectionModel().setSelectionInterval(0, i);
        });

        //region searchactionListener
        class searchAction extends AbstractAction {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (searchTextField.getText().equals("")) {
                    setShowAll(true);
                    //TODO
                } else {
                    setShowAll(false);
                    try {
                        //TODO
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    searchTextField.setText("");
                }
            }
        }
        //endregion
        suchenButton.addActionListener(new searchAction());
        searchTextField.addActionListener(new searchAction());

        AtomicInteger temp = new AtomicInteger();
        //region tableListener
        ListSelectionModel selectionModel = table1.getSelectionModel();
        selectionModel.addListSelectionListener(x -> {
            if (temp.get() == 0) {
                temp.set(1);
                int selectedRow = table1.getSelectedRow();
                if (selectedRow >= 0) {
                    String[] selected = (String[]) tableModel.getRow(selectedRow);
                    String fullIV = selected[0].toLowerCase();
                    System.out.println(fullIV);
                    String ivnumber = selected[0].substring(0, 2).toLowerCase();
                    //ResultSet res = connector.query(new SQLStatement("select * from inventory_company join inventory_" + ivnumber + " where inventory_" + ivnumber + ".iv_number = " + "\"" + fullIV + "\""));

                    selectedItemTextPane.setText(selected[0] + "\n");
                    selectedItemTextPane.setBackground(null);
                    textArea2.setText("");
                    textArea2.setDisabledTextColor(new Color(2, 126, 254));
                }
            } else {
                temp.set(0);
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override //LeftClickListener
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    openLeftClickPopUpForTable(e);
                }
            }

            @Override //DoubleClickListener
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openLeftClickPopUpForTable(e);
                }
            }
        });

        table1.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        table1.getActionMap().put("escape", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!showAll) {
                            setShowAll(true);
                            //TODO
                        }
                    }
                }
        );
        //endregion

        PCcheckBox1.addActionListener(e -> {
            if (PCcheckBox1.isSelected()) {
            }
        });
    }

    public void init() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        JFrame frame = new JFrame("Inventar Manager");
        frame.setContentPane(new MainGui(connector).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(1000, 700));
    }

    private void createUIComponents() {
        tableModel = new ShowAllTableModel(connector);
        table1 = new JTable(tableModel);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void updateShowAllTableModel() {
        statusList.add("updating table ...", 0.2);
        //TODO
    }

    private void openLeftClickPopUpForTable(MouseEvent e) {
        int r = table1.rowAtPoint(e.getPoint());
        if (r >= 0 && r < table1.getRowCount()) {
            table1.setRowSelectionInterval(0, r);
            JPopupMenu popupMenu = createLeftClickPopUpForTable();
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        } else {
            table1.clearSelection();

        }
    }

    private JPopupMenu createLeftClickPopUpForTable() {
        JPopupMenu popupMenu = new JPopupMenu();

        popupMenu.add("Bearbeiten");
        //region Bearbeiten
        popupMenu.getComponent(0).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    System.out.println("Bearbeiten");
                }
            }
        });
        //endregion

        popupMenu.add("Löschen");
        //region Löschen
        popupMenu.getComponent(1).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int toDeleteKey = Integer.parseInt((String) tableModel.getRow(table1.getSelectedRow())[0]);
                    String[] options = {"Ja", "Abbrechen"};
                    int result = JOptionPane.showOptionDialog(
                            panel1,
                            "Sind sie sich sicher, dass sie den Eintrag mit key = " + toDeleteKey + " löschen wollen?",
                            "Warnung",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE,
                            null,
                            options,
                            options[1]);
                    if (result == 0) {
                        //TODO
                        //connector.query(new SQLDeleteKeyStatement(toDeleteKey));
//                        statusList.add("deleting key " + toDeleteKey, 1);
//                        String s = (String) tableModel.getRow(table1.getSelectedRow())[1] + " (deleted)";
//                        updateShowAllTableModel(0);
//                        selectedItemTextPane.setText(s);
//                        selectedItemTextPane.setBackground(new Color(255, 0, 0));
//                        textArea2.setDisabledTextColor(new Color(255, 0, 0));
                    }
                }
            }
        });
        //endregion

        return popupMenu;
    }

    //region getter | setter
    public static synchronized boolean isShowAll() {
        return showAll;
    }

    public static synchronized void setShowAll(boolean b) {
        showAll = b;
    }
    //endregion
}