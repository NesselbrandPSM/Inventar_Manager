package GUI;

import javax.swing.*;

public class UserEditGui {
    private JPanel userEditPanel;
    private static JFrame frame;

    public void init() {
        frame = new JFrame("UserEditGui");
        frame.setContentPane(new UserEditGui().userEditPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}