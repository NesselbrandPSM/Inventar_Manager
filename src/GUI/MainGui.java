package GUI;

import GUI.util.ColumNames;
import GUI.util.ShowAllTableModel;
import GUI.util.StatusList;
import Main.Main;
import SQL.SQLConnector;
import SQL.Statements.SQLStatements;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;

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
    private SQLStatements sqlStatements;
    private ShowAllTableModel tableModel;
    private StatusList statusList;

    private static boolean showAll = true;

    public MainGui(SQLConnector connector, SQLStatements sqlStatements) {
        this.connector = connector;
        this.statusList = new StatusList(statusLabel);
        this.sqlStatements = sqlStatements;
        statusList.start();

        textArea1.setText("");

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
                    updateShowAllTableModel(0);
                } else {
                    setShowAll(false);
                    try {
                        //TODO search action
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

        //region tableListener
        ListSelectionModel selectionModel = table1.getSelectionModel();
        selectionModel.addListSelectionListener(x -> {
            int selectedRow = table1.getSelectedRow();
            if (selectedRow >= 0) {
                String[] selRow = (String[]) tableModel.getRow(selectedRow);
                selectedItemTextPane.setText(selRow[0]);
                switch (selRow[0].substring(0, 2).toLowerCase()) {
                    case "pc" -> {
                        setTextField1(ColumNames.allAttributesPC);
                        setTextField2(sqlStatements.getAllFromPCView(Integer.parseInt(selRow[2]))[0]);
                    }
                    case "pr" -> {
                        setTextField1(ColumNames.allAttributesPR);
                        setTextField2(sqlStatements.getAllFromPRView(Integer.parseInt(selRow[2]))[0]);
                    }
                    case "sc" -> {
                        setTextField1(ColumNames.allAttributesSC);
                        setTextField2(sqlStatements.getAllFromSCView(Integer.parseInt(selRow[2]))[0]);
                    }
                    case "mo" -> {
                        setTextField1(ColumNames.allAttributesMO);
                        setTextField2(sqlStatements.getAllFromMOView(Integer.parseInt(selRow[2]))[0]);
                    }
                    case "te" -> {
                        setTextField1(ColumNames.allAttributesTE);
                        setTextField2(sqlStatements.getAllFromTEView(Integer.parseInt(selRow[2]))[0]);
                    }
                    case "hd" -> {
                        setTextField1(ColumNames.allAttributesHD);
                        setTextField2(sqlStatements.getAllFromHDView(Integer.parseInt(selRow[2]))[0]);
                    }
                    case "ds" -> {
                        setTextField1(ColumNames.allAttributesDS);
                        setTextField2(sqlStatements.getAllFromDSView(Integer.parseInt(selRow[2]))[0]);
                    }
                }
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

    private void setTextField2(String[] attr) {
        textArea2.setText("");
        for (String s : attr) {
            textArea2.append(s);
            textArea2.append("\n");
        }
    }

    private void setTextField1(String[] attributes) {
        textArea1.setText("");
        for (String s : attributes) {
            textArea1.append(s);
            textArea1.append("\n");
        }
    }

    public void init() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        JFrame frame = new JFrame("Inventar Manager");
        frame.setContentPane(new MainGui(connector, sqlStatements).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(1000, 700));
    }

    private void createUIComponents() {
        tableModel = new ShowAllTableModel(connector, Main.m.sqlStatements);
        table1 = new JTable(tableModel);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void updateShowAllTableModel(int flags) {
        switch (flags) {
            case 0 -> { // show Default View
                statusList.add("updating table ...", 0.2);
                tableModel.update(sqlStatements.getDefaultView());
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
                    //TODO edit
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