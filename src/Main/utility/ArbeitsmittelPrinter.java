package Main.utility;

import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ArbeitsmittelPrinter {
    public static int currentParagraph = 0;

    public static void print() {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = new PageFormat();
        Paper a4 = new Paper();
        int width = 595;
        int height = 842;
        a4.setSize(width, height);
        a4.setImageableArea(0, 0, width, height);
        pageFormat.setPaper(a4);

        PrintObject p = new PrintObject();
        p.init();
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

        private Font title;
        private Font standard;
        private Font paragraph;
        private ArrayList<String> paragraphs;

        private int xMargin;
        private int yMargin;
        private int lineHeight;

        private static final String font = "Arial";

        public void init() {
            title = new Font(font, Font.BOLD, 13);
            standard = new Font(font, Font.PLAIN, 10);
            paragraph = new Font(font, Font.BOLD, 10);

            xMargin = 40;
            yMargin = 60;
            lineHeight = 10;
        }

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
            Graphics2D g = (Graphics2D) graphics;
            g.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            double line = 1;

            int width = (int) pageFormat.getImageableWidth();
            int height = (int) pageFormat.getImageableHeight();

            int maxLines = (height - yMargin - yMargin) / lineHeight;

            String name = "Leonard Schmidt";

            if (pageIndex == 0) {
                paragraphs = new ArrayList<>();
                paragraphs.addAll(Arrays.asList(Constants.paragraphs));
                g.setFont(title);
                FontMetrics fontMetrics = g.getFontMetrics();
                int x = (width - fontMetrics.stringWidth("Überlassung von Arbeitsmitteln")) / 2;
                int y = (int) (line * lineHeight) + yMargin;
                g.drawString("Überlassung von Arbeitsmitteln", x, y + 5); // Ueberschrift
                g.setFont(standard);
                line += 4;
                y = (int) (line * lineHeight) + yMargin;
                g.drawString("Zwischen", xMargin, y);
                line += 2;
                y = (int) (line * lineHeight) + yMargin;
                g.drawString("Apocare GmbH - Im Gewerbepark 11, 96155 Buttenheim", 10 + xMargin, y);
                g.drawString("[im Folgenden Firma]", width - fontMetrics.stringWidth("[im Folgenden Arbeitnehmer]") + 10, y);
                line += 2;
                y = (int) (line * lineHeight) + yMargin;
                g.drawString("und " + name, 10 + xMargin, y);
                g.drawString("[im Folgenden Arbeitnehmer]", width - fontMetrics.stringWidth("[im Folgenden Arbeitnehmer]") + 10, y);
                line += 3;
                y = (int) (line * lineHeight) + yMargin;
                g.drawString("werden nachfolgende Vereinbarungen getroffen:", xMargin, y);
                g.setFont(paragraph);

                currentParagraph = 1;
                while (paragraphs.size() != 0) {
                    String par = paragraphs.get(0);
                    if ((calcParaLines(g, par, width) + line) > maxLines) {
                        break;
                    }
                    paragraphs.remove(0);
                    line += 2;
                    y = (int) (line * lineHeight) + yMargin;
                    g.setFont(paragraph);
                    g.drawString("§ " + (currentParagraph), xMargin, y);
                    currentParagraph++;
                    g.setFont(standard);
                    while (par.length() > 0) {
                        line += 2;
                        String s = par;
                        y = (int) (line * lineHeight) + yMargin;
                        int cutOff = 0;
                        if (fontMetrics.stringWidth(s) > (width + 150)) {
                            while (fontMetrics.stringWidth(s) > (width + 150)) {
                                cutOff = s.lastIndexOf(" ");
                                s = s.substring(0, cutOff);
                            }
                            if (s.indexOf(" ") == 0) {
                                s = s.substring(1);
                            }
                            g.drawString(s, xMargin, y);
                            par = par.substring(cutOff);
                        } else {
                            if (s.indexOf(" ") == 0) {
                                s = s.substring(1);
                            }
                            g.drawString(s, xMargin, y);
                            par = "";
                        }
                    }
                }
                return PAGE_EXISTS;
            } else {
                return NO_SUCH_PAGE;
            }
        }

        private int calcParaLines(Graphics g, String par, int width) {
            int lines = 4;

            while (par.length() > 0) {
                String s = par;
                int cutOff = 0;
                if (g.getFontMetrics().stringWidth(s) > (width + 50)) {
                    while (g.getFontMetrics().stringWidth(s) > (width + 50)) {
                        cutOff = s.lastIndexOf(" ");
                        s = s.substring(0, cutOff);
                    }
                    if (s.indexOf(" ") == 0) {
                        s = s.substring(1);
                    }
                    lines += 2;
                    par = par.substring(cutOff);
                } else {
                    if (s.indexOf(" ") == 0) {
                        s = s.substring(1);
                    }
                    lines += 2;
                    par = "";
                }
            }

            return lines;
        }
    }
}