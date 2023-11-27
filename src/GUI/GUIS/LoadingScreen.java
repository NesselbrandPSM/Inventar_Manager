package GUI.GUIS;

import javax.swing.*;
import java.awt.*;

public class LoadingScreen {

    private JPanel panel;
    private JTextArea textArea1;
    private JScrollPane sp;
    private JLabel versionField;
    private JFrame frame;

    private static LoadingScreen ld;

    public LoadingScreen() {
        frame = new JFrame("Loading ...");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(new Dimension(500, 500));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        versionField.setText(Main.Main.getVersion());
    }

    public static void init() {
        ld = new LoadingScreen();
    }

    public static void dispose(){
        ld.frame.dispose();
    }

    public static void print(String out){
        if (ld != null){
            ld.textArea1.setText(ld.textArea1.getText() + "\n" + out);
            SwingUtilities.invokeLater(() -> {
                JScrollBar bar = ld.sp.getVerticalScrollBar();
                bar.setValue(bar.getMaximum());
            });
        }
    }
}