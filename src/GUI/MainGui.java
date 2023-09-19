package GUI;

import GUI.util.ColumNames;
import GUI.util.ShowAllTableModel;
import GUI.util.StatusList;
import Main.Main;
import Main.utility.Utils;
import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;

public class MainGui {
    //region attributes
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
    private JButton editUserButton;
    private JPanel checkBoxPanel;
    private JCheckBox deskBox;
    private JCheckBox druckerBox;
    private JCheckBox pcBox;
    private JCheckBox scannerBox;
    private JCheckBox headsetBox;
    private JCheckBox monitorBox;
    private JCheckBox dockingstationBox;
    private JCheckBox telephoneBox;
    private SQLConnector connector;
    private SQLSelectStatements sqlSelectStatements;
    private ShowAllTableModel tableModel;
    private StatusList statusList;

    private String lastactivefilterbox = "";

    private static int showAllTableModelFlag = 0;
    private static boolean showAll = true;
    //endregion

    public MainGui(SQLConnector connector, SQLSelectStatements sqlSelectStatements) {
        this.connector = connector;
        this.statusList = new StatusList(statusLabel);
        this.sqlSelectStatements = sqlSelectStatements;
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
                            updateShowAllTableModel(showAllTableModelFlag);
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

        //region aktualisierenListener
        aktuallisierenButton.addActionListener(e -> {
            int i = table1.getSelectedRow();
            if (i < 0) {
                selectedItemTextPane.setText("no item selected");
                selectedItemTextPane.setBackground(null);
                textArea2.setText("");
                textArea2.setDisabledTextColor(new Color(2, 126, 254));
            }
            updateShowAllTableModel(showAllTableModelFlag);
            table1.getSelectionModel().setSelectionInterval(0, i);
        });
        //endregion

        //region searchactionListener
        class searchAction extends AbstractAction {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (searchTextField.getText().equals("")) {
                    setShowAll(true);
                    updateShowAllTableModel(0);
                } else {
                    setShowAll(false);
                    switch (searchSelectorComboBox.getSelectedItem().toString()) {
                        case "Inventar Nummer" -> updateShowAllTableModel(1);
                        case "Firma" -> updateShowAllTableModel(2);
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
                        setTextField2(sqlSelectStatements.getAllFromPCView(Integer.parseInt(selRow[2]))[0]);
                    }
                    case "pr" -> {
                        setTextField1(ColumNames.allAttributesPR);
                        setTextField2(sqlSelectStatements.getAllFromPRView(Integer.parseInt(selRow[2]))[0]);
                    }
                    case "sc" -> {
                        setTextField1(ColumNames.allAttributesSC);
                        setTextField2(sqlSelectStatements.getAllFromSCView(Integer.parseInt(selRow[2]))[0]);
                    }
                    case "mo" -> {
                        setTextField1(ColumNames.allAttributesMO);
                        setTextField2(sqlSelectStatements.getAllFromMOView(Integer.parseInt(selRow[2]))[0]);
                    }
                    case "te" -> {
                        setTextField1(ColumNames.allAttributesTE);
                        setTextField2(sqlSelectStatements.getAllFromTEView(Integer.parseInt(selRow[2]))[0]);
                    }
                    case "hd" -> {
                        setTextField1(ColumNames.allAttributesHD);
                        setTextField2(sqlSelectStatements.getAllFromHDView(Integer.parseInt(selRow[2]))[0]);
                    }
                    case "ds" -> {
                        setTextField1(ColumNames.allAttributesDS);
                        setTextField2(sqlSelectStatements.getAllFromDSView(Integer.parseInt(selRow[2]))[0]);
                    }
                    case "dk" -> {
                        setTextField1(ColumNames.allAttributesDK);
                        setTextField2(sqlSelectStatements.getAllFromDKView(Integer.parseInt(selRow[2]))[0]);
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
                        setShowAll(true);
                        lastactivefilterbox = "";
                        uncheckFilterBoxes();
                        updateShowAllTableModel(0);
                    }
                }
        );
        //endregion

        //region einfügenListener
        elementEinfügenButton.addActionListener(e -> {
            MainInputGui gui = new MainInputGui();
            gui.init();
        });
        //endregion


        editUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserEditGui userEditGui = new UserEditGui();
                userEditGui.init();
            }
        });

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox c = (JCheckBox) e.getSource();
                uncheckFilterBoxes();
                if (lastactivefilterbox.equals(c.getText())) {
                    updateShowAllTableModel(0);
                    lastactivefilterbox = "";
                    return;
                }
                c.setSelected(true);
                lastactivefilterbox = c.getText();
                updateShowAllTableModel(3);
            }
        };
        druckerBox.addActionListener(listener);
        pcBox.addActionListener(listener);
        scannerBox.addActionListener(listener);
        deskBox.addActionListener(listener);
        headsetBox.addActionListener(listener);
        monitorBox.addActionListener(listener);
        dockingstationBox.addActionListener(listener);
        telephoneBox.addActionListener(listener);
    }

    public void updateShowAllTableModel(int flags) {
        switch (flags) {
            case 0 -> { // show Default View
                if (lastactivefilterbox.equals("")) {
                    statusList.add("updating table ...", 0.3);
                    tableModel.update(sqlSelectStatements.getDefaultView());
                    showAllTableModelFlag = 0;
                } else {
                    updateShowAllTableModel(3);
                }
            }
            case 1 -> { // show Search View for iv_number
                statusList.add("searching ...", 0.2);
                String[][] result = sqlSelectStatements.getSelectViewIV_Number(searchTextField.getText());
                if (result == null) {
                    JOptionPane.showConfirmDialog(null, "Die Eingegebene Inventar Nummer war fehlerhaft!");
                    searchTextField.setText("");
                    tableModel.update(sqlSelectStatements.getDefaultView());
                } else {
                    searchTextField.setText("");
                    tableModel.update(result);
                }
                showAllTableModelFlag = 1;
            }
            case 2 -> { // show search View for company
                statusList.add("searching ...", 0.2);
                String[][] result;
                if (lastactivefilterbox.equals("")) {
                    result = sqlSelectStatements.getSelectViewCompany(searchTextField.getText());
                } else {
                    result = sqlSelectStatements.getSelectViewCompany(searchTextField.getText(), Utils.filterBoxTextToAccordingDataTable(lastactivefilterbox));
                }
                if (result == null) {
                    JOptionPane.showConfirmDialog(null, "Die Eingegebene Firma war fehlerhaft!");
                    searchTextField.setText("");
                    tableModel.update(sqlSelectStatements.getDefaultView());
                } else {
                    searchTextField.setText("");
                    tableModel.update(result);
                }
                showAllTableModelFlag = 2;
            }
            case 3 -> { // show search View for type
                statusList.add("searching ...", 0.2);
                String[][] result = sqlSelectStatements.getSelectViewType(Utils.filterBoxTextToAccordingDataTable(lastactivefilterbox));
                if (result == null) {
                    JOptionPane.showConfirmDialog(null, "Es ist ein Fehler aufgetreten!");
                    searchTextField.setText("");
                    tableModel.update(sqlSelectStatements.getDefaultView());
                } else {
                    searchTextField.setText("");
                    tableModel.update(result);
                }
                showAllTableModelFlag = 3;
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

    //region util
    private void uncheckFilterBoxes() {
        druckerBox.setSelected(false);
        pcBox.setSelected(false);
        scannerBox.setSelected(false);
        deskBox.setSelected(false);
        headsetBox.setSelected(false);
        monitorBox.setSelected(false);
        dockingstationBox.setSelected(false);
        telephoneBox.setSelected(false);
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

    public static synchronized boolean isShowAll() {
        return showAll;
    }

    public static synchronized void setShowAll(boolean b) {
        showAll = b;
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

    private void createUIComponents() {
        tableModel = new ShowAllTableModel(Main.m.sqlSelectStatements);
        table1 = new JTable(tableModel);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void init() {
        JFrame frame = new JFrame("Inventar Manager");
        frame.setContentPane(new MainGui(connector, sqlSelectStatements).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }
    //endregion
}