package Main.utility;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.UnsupportedEncodingException;

public class LabelPrinter {

    private static String iv_number;

    public static void print(String iv_Number) {
        iv_number = iv_Number;
        PrinterJob printJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = printJob.defaultPage();
        Paper paper = new Paper();

        final double widthPaper = (1.5 * 72);
        final double heightPaper = (3 * 72);

        paper.setSize(widthPaper, heightPaper);
        paper.setImageableArea(0, 0, widthPaper, heightPaper);

        pageFormat.setPaper(paper);

        pageFormat.setOrientation(PageFormat.LANDSCAPE);

        boolean doPrint = printJob.printDialog();
        if (doPrint) {
            try {
                printJob.setPrintable(new PrintObject(), pageFormat);
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
            if (pageIndex == 0) {
                int width = (int) pageFormat.getImageableWidth();
                g.setFont(new Font("Arial", Font.PLAIN, 18));
                g.drawString("Equipment Nummer", (width - g.getFontMetrics().stringWidth("Equipment Nummer")) / 2 - 10, 15);
                g.setFont(new Font("Arial", Font.PLAIN, 10));
                g.drawString("bitte bei jeder Störung mit angeben", (width - g.getFontMetrics().stringWidth("Equipment Nummer")) / 2 - 43, 30);
                g.setFont(new Font("Arial", Font.PLAIN, 15));
                g.drawString(iv_number, 65, 50);

                try {
                    Image bI = createCode(iv_number);
                    g.drawImage(bI, -26, 60, null);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                g.setFont(new Font("Arial", Font.PLAIN, 10));
                g.drawString("Ticket an: ticket@perfectus-solutions.de", 10, 97);

                return PAGE_EXISTS;
            }
            return NO_SUCH_PAGE;
        }

        private static BufferedImage createCode(String data) throws WriterException {
            return MatrixToImageWriter.toBufferedImage(new MultiFormatWriter().encode(data, BarcodeFormat.CODE_128, 250, 25));
        }
    }
}
