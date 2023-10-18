package GUI.GUIS.Dialogs;

import javax.swing.*;
import java.awt.event.*;

public class PrinterDialog extends JDialog {
    private JPanel contentPane;
    private JPanel panel1;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel userLabel;
    private JLabel workingHoursLabel;
    private JLabel workingDaysLabel;
    private JLabel addressLabel;
    private JLabel ueberlassungLabel;
    private JLabel homeofficeLabel;
    private JCheckBox ueberlassungCheckBox;
    private JCheckBox homeofficeCheckBox;
    private JTextArea paragraphArea;

    private int ueberlassung;
    private int homeoffice;

    private int lastCheckBox;

    public PrinterDialog(String user, String[] data) {
        ueberlassung = 0;
        homeoffice = 0;

        lastCheckBox = 0;

        userLabel.setText(user);
        addressLabel.setText(data[0]);
        workingHoursLabel.setText(data[1]);
        workingDaysLabel.setText(data[2]);
        ueberlassungLabel.setText("Nein");
        homeofficeLabel.setText("Nein");
        if (data[3].equals("1")) {
            homeofficeLabel.setText("Ja");
            homeoffice = 1;
        }
        if (data[4].equals("1")) {
            ueberlassungLabel.setText("Ja");
            ueberlassung = 1;
        }

        if (ueberlassung == 0) {
            ueberlassungCheckBox.setVisible(false);
        }
        if (homeoffice == 0) {
            homeofficeCheckBox.setVisible(false);
        }

        if (homeoffice == 1 && ueberlassung == 0) {
            homeofficeCheckBox.setSelected(true);
            ueberlassungCheckBox.setSelected(false);
            lastCheckBox = 1;
        }
        if ((homeoffice == 0 && ueberlassung == 1) || (homeoffice == 1 && ueberlassung == 1)) {
            homeofficeCheckBox.setSelected(false);
            ueberlassungCheckBox.setSelected(true);
        }


        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionListener listener = e -> {
            if (e.getSource() == ueberlassungCheckBox && ueberlassungCheckBox.isSelected()) {
                ueberlassungCheckBox.setSelected(true);
                homeofficeCheckBox.setSelected(false);
                lastCheckBox = 0;
            } else if (e.getSource() == homeofficeCheckBox && homeofficeCheckBox.isSelected()) {
                ueberlassungCheckBox.setSelected(false);
                homeofficeCheckBox.setSelected(true);
                lastCheckBox = 1;
            } else {
                if (e.getSource() == ueberlassungCheckBox && lastCheckBox == 0) {
                    ueberlassungCheckBox.setSelected(true);
                }
                if (e.getSource() == homeofficeCheckBox && lastCheckBox == 1) {
                    homeofficeCheckBox.setSelected(true);
                }
            }
            //TODO update
        };
        ueberlassungCheckBox.addActionListener(listener);
        homeofficeCheckBox.addActionListener(listener);
    }

    private void onOK() {
        //TODO write everything in printarrays
        //TODO set Constants.printable = true;
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static void start(String user, String[] data) {
        PrinterDialog dialog = new PrinterDialog(user, data);
        dialog.pack();
        dialog.setVisible(true);
    }
}
