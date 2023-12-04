package GUI.GUIS.Dialogs;

import GUI.InputForms.*;
import Main.utility.Utils;
import SQL.SQLConnector;
import SQL.Statements.SQLUpdateStatements;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ShowEditDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel content;

    private Dockingstation_Form dockingstationForm;
    private Headset_Form headsetForm;
    private Monitor_Form monitorForm;
    private PC_Form pcForm;
    private Printer_Form printerForm;
    private Scanner_Form scannerForm;
    private Telephone_Form telephoneForm;
    private Desk_Form deskForm;
    private Miscellaneous_Form miscellaneousForm;
    private TV_Form tvForm;
    private Router_Form routerForm;

    private String table;
    private String iv_number;

    public ShowEditDialog(String table, String iv_number) {
        this.iv_number = iv_number;
        this.table = table;
        initForms(table);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void switchTable(String table, String[] data) {
        switch (table) {
            case "pc":
                pcForm.initData(data);
                show(pcForm.getPcPanel());
                break;
            case "telephone":
                telephoneForm.initData(data);
                show(telephoneForm.getTelephonePanel());
                break;
            case "headset":
                headsetForm.initData(data);
                show(headsetForm.getHeadsetPanel());
                break;
            case "tv":
                tvForm.initData(data);
                show(tvForm.getTVPanel());
                break;
            case "scanner":
                scannerForm.initData(data);
                show(scannerForm.getScannerPanel());
                break;
            case "router":
                routerForm.initData(data);
                show(routerForm.getRtPanel());
                break;
            case "dockingstation":
                dockingstationForm.initData(data);
                show(dockingstationForm.getDockingPanel());
                break;
            case "desk":
                deskForm.initData(data);
                show(deskForm.getDeskPanel());
                break;
            case "miscellaneous":
                miscellaneousForm.initData(data);
                show(miscellaneousForm.getPanel());
                break;
            case "monitor":
                monitorForm.initData(data);
                show(monitorForm.getMonitorPanel());
                break;
            case "printer":
                printerForm.initData(data);
                show(printerForm.getPrinterPanel());
                break;
        }
    }

    private void show(JPanel panel) {
        content.removeAll();
        content.add(panel, new GridBagConstraints());
        content.revalidate();
        content.repaint();
    }

    private void onOK() {
        SQLUpdateStatements sqlUpdateStatements = new SQLUpdateStatements(new SQLConnector());
        switch (table){
            case "pc":
                 sqlUpdateStatements.updatePC(pcForm.getArgs(""), iv_number);
                break;
            case "telephone":
                sqlUpdateStatements.updateTE(telephoneForm.getArgs(""), iv_number);
                break;
            case "headset":
                sqlUpdateStatements.updateHD(headsetForm.getArgs(""), iv_number);
                break;
            case "tv":
                sqlUpdateStatements.updateTV(tvForm.getArgs(""), iv_number);
                break;
            case "scanner":
                sqlUpdateStatements.updateSC(scannerForm.getArgs(""), iv_number);
                break;
            case "router":
                sqlUpdateStatements.updateRT(routerForm.getArgs(""), iv_number);
                break;
            case "dockingstation":
                sqlUpdateStatements.updateDS(dockingstationForm.getArgs(""), iv_number);
                break;
            case "desk":
                sqlUpdateStatements.updateDesk(deskForm.getArgs(""), iv_number);
                break;
            case "miscellaneous":
                sqlUpdateStatements.updateMC(miscellaneousForm.getArgs(""), iv_number);
                break;
            case "monitor":
                sqlUpdateStatements.updateMO(monitorForm.getArgs(""), iv_number);
                break;
            case "printer":
                sqlUpdateStatements.updatePR(printerForm.getArgs(""), iv_number);
                break;
        }
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static void init(String table, String[] data, String iv_number) {
        ShowEditDialog dialog = new ShowEditDialog(table, iv_number);
        dialog.setTitle(iv_number);
        dialog.switchTable(table, data);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void initForms(String table) {
        switch (table) {
            case "pc":
                pcForm = new PC_Form();
                pcForm.getPcPanel().setName("pc");
                break;
            case "telephone":
                telephoneForm = new Telephone_Form();
                telephoneForm.getTelephonePanel().setName("telephone");
                break;
            case "headset":
                headsetForm = new Headset_Form();
                headsetForm.getHeadsetPanel().setName("headset");
                break;
            case "tv":
                tvForm = new TV_Form();
                tvForm.getTVPanel().setName("tv");
                break;
            case "scanner":
                scannerForm = new Scanner_Form();
                scannerForm.getScannerPanel().setName("scanner");
                break;
            case "router":
                routerForm = new Router_Form();
                routerForm.getRtPanel().setName("router");
                break;
            case "dockingstation":
                dockingstationForm = new Dockingstation_Form();
                dockingstationForm.getDockingPanel().setName("dockingstation");
                break;
            case "desk":
                deskForm = new Desk_Form();
                deskForm.getDeskPanel().setName("desk");
                break;
            case "miscellaneous":
                miscellaneousForm = new Miscellaneous_Form();
                miscellaneousForm.getPanel().setName("miscellaneous");
                break;
            case "monitor":
                monitorForm = new Monitor_Form();
                monitorForm.getMonitorPanel().setName("monitor");
                break;
            case "printer":
                printerForm = new Printer_Form();
                printerForm.getPrinterPanel().setName("printer");
                break;
        }
    }

    private void createUIComponents() {
        contentPane = new JPanel();
        content = new JPanel();
    }
}