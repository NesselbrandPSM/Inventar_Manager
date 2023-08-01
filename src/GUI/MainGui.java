package GUI;

import GUI.util.ShowAllTableModel;
import GUI.util.StatusList;
import SQL.SQLConnector;
import SQL.Statements.SQLSequenzStatements;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
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
    private JCheckBox PCCheckBox;

    private SQLConnector connector;
    private SQLSequenzStatements sqlSequenzStatements;
    private ShowAllTableModel tableModel;
    private StatusList statusList;

    private static boolean showAll = true;

    public MainGui(SQLConnector connector, SQLSequenzStatements sqlSequenzStatements) {
        this.connector = connector;
        this.sqlSequenzStatements = sqlSequenzStatements;
        this.statusList = new StatusList(statusLabel);
        statusList.start();

        textArea1.setText("");
        //TODO set attributes to textArea1

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
                            updateShowAllTableModel(0);
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
            updateShowAllTableModel(0);
            table1.getSelectionModel().setSelectionInterval(0, i);
        });

        //region searchactionListener
        class searchAction extends AbstractAction {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (searchTextField.getText().equals("")) {
                    setShowAll(true);
                    //TODO updateTableModel to show all
                } else {
                    setShowAll(false);
                    try {
                        //TODO updateTableModel with search parameters
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

        AtomicInteger temp = new AtomicInteger(); //so no doubleclicking
        //region tableListener
        ListSelectionModel selectionModel = table1.getSelectionModel();
        selectionModel.addListSelectionListener(x -> {
            if (temp.get() == 0) {
                temp.set(1);
                int selectedRow = table1.getSelectedRow();
                if (selectedRow >= 0) {
                    //TODO get infos from select + show on the right
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
                            updateShowAllTableModel(0);
                        }
                    }
                }
        );
        //endregion

        PCCheckBox.addActionListener(e -> {
            if (PCCheckBox.isSelected()) {
                //TODO show only checkbox specific entrys => update Table Model
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
        frame.setContentPane(new MainGui(connector, sqlSequenzStatements).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(1000, 700));
    }

    private void createUIComponents() {
        tableModel = new ShowAllTableModel(connector, sqlSequenzStatements);
        table1 = new JTable(tableModel);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void updateShowAllTableModel(int flags) {
        switch (flags) {
            case 0 -> { // show Default View
                statusList.add("updating table ...", 0.2);
                tableModel.update(sqlSequenzStatements.getDefaultView());
            }
        }
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
                    //TODO delete (active => 0)
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