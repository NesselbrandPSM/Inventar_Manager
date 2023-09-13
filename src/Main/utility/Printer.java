package Main.utility;

import javax.swing.*;
import java.awt.*;
import java.awt.print.*;

public class Printer {
    public static void print() {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = new PageFormat();
        Paper a4 = new Paper();
        a4.setSize(595, 842);
        pageFormat.setPaper(a4);

        PrintObject p = new PrintObject();
        p.init();
        printJob.setPrintable(new PrintObject(), pageFormat);

        boolean doPrint = printJob.printDialog();
        if (doPrint) {
            try {
                printJob.print();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Printjob failed!\n\n" + e);
            }
        }
    }

    static class PrintObject implements Printable {

        private Font title;

        public void init(){
            title = new Font("Arial", Font.BOLD, 20);
        }

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
            Graphics2D g = (Graphics2D) graphics;
            g.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            int width = (int) pageFormat.getImageableWidth();
            int height = (int) pageFormat.getImageableHeight();

            switch (pageIndex) {
                case 0 -> {
                    FontMetrics fontMetrics = g.getFontMetrics();
                    int x = (width - fontMetrics.stringWidth("Überlassung von Arbeitsmitteln")) / 2;
                    g.setFont(title);
                    g.drawString("Überlassung von Arbeitsmitteln", x, 50); // Ueberschrift

                    return PAGE_EXISTS;
                }
                case 1 -> {
                    return NO_SUCH_PAGE;
                }
            }
            return NO_SUCH_PAGE;
        }

        private static double toInches(double millimeters) {
            return millimeters * 0.0393701;
        }
    }
}