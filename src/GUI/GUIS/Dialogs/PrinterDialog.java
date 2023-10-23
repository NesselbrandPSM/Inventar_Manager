package GUI.GUIS.Dialogs;

import Main.utility.Constants;
import Main.utility.Printer.ArbeitsmittelPrinter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.Arrays;

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
    private JList paragraphList;

    private int ueberlassung;
    private int homeoffice;

    private int lastCheckBox;

    private DefaultListModel<String> paraListModel;

    public PrinterDialog(String user, String[] data) {
        this.setTitle("Druckansicht");
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

            paralistUpdate();
            paragraphArea.setText("");
        };
        ueberlassungCheckBox.addActionListener(listener);
        homeofficeCheckBox.addActionListener(listener);

        paralistUpdate();

        paragraphList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (paragraphList.getSelectedIndex() >= 0) {
                    if (ueberlassungCheckBox.isSelected()) {
                        paragraphArea.setText(Constants.PRINT_paragraphsUeberlassung[paragraphList.getSelectedIndex()]);
                    } else { //Home-office
                        paragraphArea.setText(Constants.PRINT_paragraphsHomeOffice[paragraphList.getSelectedIndex()][1]);
                    }
                }
            }
        });

        paragraphArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (paragraphList.getSelectedIndex() > -1) {
                    if (homeofficeCheckBox.isSelected()){
                        Constants.PRINT_paragraphsHomeOffice[paragraphList.getSelectedIndex()][1] = paragraphArea.getText();
                    } else {
                        Constants.PRINT_paragraphsUeberlassung[paragraphList.getSelectedIndex()] = paragraphArea.getText();
                    }
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (paragraphList.getSelectedIndex() > -1) {
                    if (homeofficeCheckBox.isSelected()){
                        Constants.PRINT_paragraphsHomeOffice[paragraphList.getSelectedIndex()][1] = paragraphArea.getText();
                    } else {
                        Constants.PRINT_paragraphsUeberlassung[paragraphList.getSelectedIndex()] = paragraphArea.getText();
                    }
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    private void paralistUpdate() {
        paraListModel.removeAllElements();

        if (homeofficeCheckBox.isSelected()) {
            for (int i = Constants.paragraphsHomeOffice.length - 1; i >= 0; i--) {
                paraListModel.add(0, Constants.paragraphsHomeOffice[i][0]);
            }
        } else {
            for (int i = Constants.paragraphsUeberlassung.length - 1; i >= 0; i--) {
                paraListModel.add(0, "Paragraph " + (i + 1));
            }
        }
        paragraphList.setSelectedIndex(-1);
    }

    private void onOK() {
        Constants.printable = true;
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

    private void createUIComponents() {
        paraListModel = new DefaultListModel<>();
        paragraphList = new JList<String>();
        paragraphList.setModel(paraListModel);
    }
}
