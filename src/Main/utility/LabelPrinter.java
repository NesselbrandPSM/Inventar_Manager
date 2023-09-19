package Main.utility;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;

public class LabelPrinter {

    public static void print() {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = new PageFormat();
        Paper label = new Paper();
        double width = 90 / 25.4;
        double height = 90 / 25.4;
        label.setSize(width * 72.0, height * 72.0);
        //label.setImageableArea(0, 0, width, height);
        pageFormat.setPaper(label);

        PrintObject p = new PrintObject();
        printJob.setPrintable(p, pageFormat);

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
        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
            Graphics2D g = (Graphics2D) graphics;
            g.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            switch (pageIndex){
                case 0 -> {
                    g.drawString("test", 10, 10);
                    return PAGE_EXISTS;
                }
                case 1 -> {
                    return NO_SUCH_PAGE;
                }
            }
            return NO_SUCH_PAGE;
        }
    }
}
