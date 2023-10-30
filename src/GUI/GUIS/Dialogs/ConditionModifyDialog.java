package GUI.GUIS.Dialogs;

import GUI.util.ComboBoxItem;
import Main.utility.Constants;
import Main.utility.Utils;
import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

import javax.swing.*;
import java.awt.event.*;
import java.util.Objects;

public class ConditionModifyDialog extends JDialog {
    private JPanel contentPane;
    private JPanel panel1;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox cComboBox;
    private JTextArea conditionNote;

    private SQLSelectStatements sqlSelectStatements;

    private static String iv_number;

    public ConditionModifyDialog() {
        this.setTitle("Zustandsänderung: " + iv_number);
        sqlSelectStatements = new SQLSelectStatements(new SQLConnector());
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        String[] conditions = sqlSelectStatements.getConditions(iv_number);
        conditionNote.setText(conditions[1]);

        int selIndex = 0;
        for (int i = 0; i < Constants.conditionList.length; i++) {
            if (Objects.equals(conditions[0], Constants.conditionList[i])){
                selIndex = i;
            }
            cComboBox.addItem(new ComboBoxItem(Constants.conditionList[i]));
        }
        cComboBox.setSelectedIndex(selIndex);

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
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

    private void onOK() {
        Utils.newConditionNote = conditionNote.getText();
        Utils.newConditionStatus = cComboBox.getSelectedItem().toString();
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static void init(String iv_Number) {
        iv_number = iv_Number;
        ConditionModifyDialog dialog = new ConditionModifyDialog();
        dialog.pack();
        dialog.setVisible(true);
    }
}
