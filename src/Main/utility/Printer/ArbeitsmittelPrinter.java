package Main.utility.Printer;

import Main.utility.Constants;
import Main.utility.UtilPrintables.IVObject;
import Main.utility.UtilPrintables.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.print.*;
import java.util.ArrayList;

public class ArbeitsmittelPrinter {

    public static void print(String name) {
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
        p.calculatePages(pageFormat, name);
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

        private Font titleF;
        private Font standardF;
        private Font paragraphF;
        private Font footF;

        private int xMargin;
        private int yMargin;
        private int lineHeight;

        private int width;
        private int height;

        private static final String font = "Arial";

        ArrayList<ArrayList<Line>> pageLineList;
        ArrayList<ArrayList<IVObject>> objectList;

        public void init() {
            titleF = new Font(font, Font.BOLD, 13);
            standardF = new Font(font, Font.PLAIN, 10);
            paragraphF = new Font(font, Font.BOLD, 10);
            footF = new Font(font, Font.PLAIN, 7);

            xMargin = 40;
            yMargin = 60;
            lineHeight = 10;
        }

        public void calculatePages(PageFormat pageFormat, String name){
            pageLineList = new ArrayList<>();

            width = (int) pageFormat.getImageableWidth() - xMargin;
            height = (int) pageFormat.getImageableHeight();

            int maxLines = 76;
            //page 1 with default
            pageLineList.add(new ArrayList<>());
            pageLineList.get(0).add(new Line("Überlassung von Arbeitsmitteln", Double.POSITIVE_INFINITY, 4, titleF, 5, 0));
            pageLineList.get(0).add(new Line("Zwischen", xMargin, 2, standardF));
            pageLineList.get(0).add(new Line("Apocare GmbH - Im Gewerbepark 11, 96155 Buttenheim", xMargin + 10, 0, standardF));
            pageLineList.get(0).add(new Line("[im Folgenden Firma]", Double.NEGATIVE_INFINITY, 2, standardF));
            pageLineList.get(0).add(new Line("und " + name, xMargin + 10, 0, standardF));
            pageLineList.get(0).add(new Line("[im Folgenden Arbeitnehmer]", Double.NEGATIVE_INFINITY, 3, standardF));
            pageLineList.get(0).add(new Line("werden nachfolgende Vereinbarungen getroffen:", xMargin, 3, standardF));

            double currentLine = 13;
            int page = 0;
            int paraIndex = 1;
            int internalWidth = width - xMargin;

            for (int i = 0; i < Constants.paragraphs.length; i++) {
                String paragraphC = Constants.paragraphs[i];
                if ((2 + currentLine + calcParaLines(paragraphC, internalWidth, standardF)) < maxLines){
                    pageLineList.get(page).add(new Line("§ " + paraIndex, xMargin, 2, paragraphF));
                    currentLine += 2;

                    String paragraph = paragraphC;
                    while (paragraph.length() > 0){
                        String line = paragraph;
                        while (getTextWidth(line, standardF) > internalWidth) {
                            line = line.substring(0, line.lastIndexOf(" "));
                        }
                        paragraph = paragraph.substring(line.length());
                        if (line.substring(0, 1).equals(" ")){
                            line = line.substring(1);
                        }
                        pageLineList.get(page).add(new Line(line, xMargin, 1.3 , standardF));
                        currentLine += 1.3;
                    }
                    pageLineList.get(page).get(pageLineList.get(page).size() - 1).lineAdd = 2;
                    currentLine += 0.7;
                    paraIndex++;
                } else {
                    pageLineList.add(new ArrayList<>());
                    page++;
                    currentLine = 1;
                    i--;
                }
            }

            double tempLineAddon = 9.5;
            if ((currentLine + tempLineAddon) > maxLines){
                page++;
                currentLine = 1;
                pageLineList.add(new ArrayList<>());
            }
            pageLineList.get(page).add(new Line("", xMargin, 3, standardF));
            pageLineList.get(page).add(new Line("Buttenheim, _____________", xMargin, 5, standardF));
            pageLineList.get(page).add(new Line("________________________________", xMargin, 0, standardF));
            pageLineList.get(page).add(new Line("________________________________", width - getTextWidth("________________________________", standardF) - 20, 1.5, standardF, 0, -10));
            pageLineList.get(page).add(new Line("Unterschrift Arbeitnemer", xMargin, 0, standardF));
            pageLineList.get(page).add(new Line("Unterschrift Arbeitgeber", width - getTextWidth("________________________________", standardF) - 20, 3, standardF, 0, -10));
            currentLine += tempLineAddon;
            currentLine += 3;

            tempLineAddon = 24;
            if ((currentLine + tempLineAddon) > maxLines){
                page++;
                currentLine = 1;
                pageLineList.add(new ArrayList<>());
            }
            int underLineOffset = 95;
            pageLineList.get(page).add(new Line(Math.PI, 3));
            pageLineList.get(page).add(new Line("Rückgabe der Arbeitsmittel", xMargin, 3, titleF));
            pageLineList.get(page).add(new Line("Zustand:", xMargin, 0, standardF));
            pageLineList.get(page).add(new Line("_________________________________________________________________________", xMargin + underLineOffset, 2, standardF));
            pageLineList.get(page).add(new Line("_________________________________________________________________________", xMargin + underLineOffset, 2, standardF));
            pageLineList.get(page).add(new Line("_________________________________________________________________________", xMargin + underLineOffset, 4, standardF));

            pageLineList.get(page).add(new Line("Angenommen von:", xMargin, 0, standardF));
            pageLineList.get(page).add(new Line("_________________________________________________________________________", xMargin + underLineOffset, 4, standardF));
            pageLineList.get(page).add(new Line("Datum und ", xMargin, 2, standardF));
            pageLineList.get(page).add(new Line("Unterschrift:", xMargin, 0, standardF));
            pageLineList.get(page).add(new Line("_________________________________________________________________________", xMargin + underLineOffset, 4, standardF));
            currentLine += tempLineAddon;
        }

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
            Graphics2D g = (Graphics2D) graphics;
            g.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            if (pageIndex == 0) {
                double line = 0;
                g.setFont(standardF);
                for (Line l : pageLineList.get(0)) {
                    if (l.x == Math.PI){
                        int y = (int) ((line * lineHeight) + yMargin);
                        g.drawLine(xMargin, y, width, y);
                    } else {
                        g.setFont(l.font);
                        if (l.x == Double.POSITIVE_INFINITY) {
                            l.x = getXMiddle(l.line, g.getFontMetrics());
                        } else if (l.x == Double.NEGATIVE_INFINITY) {
                            l.x = getXRight(l.line, g.getFontMetrics());
                        }

                        l.x += l.xAddon;
                        int y = (int) ((line * lineHeight) + yMargin);
                        g.drawString(l.line, (int) l.x, (int) (y + l.yAddon));
                    }
                    line += l.lineAdd;
                }
                //Footer
                g.setFont(footF);
                g.drawString("Seite", xMargin, height - 30);
                int showIndex = pageIndex + 1;
                g.drawString(showIndex + "/" + pageLineList.size(), (int) getXRight(pageIndex + "/" + pageLineList.size(), g.getFontMetrics()), height - 30);
                Color temp = g.getColor();
                g.setColor(new Color(169, 169, 169));
                g.drawLine(xMargin, height - 40, width, height - 40);
                g.setColor(temp);

                return PAGE_EXISTS;
            } else if (pageIndex > 0 && pageIndex < pageLineList.size()){
                double line = 0;
                for (Line l : pageLineList.get(pageIndex)) {
                    if (l.x == Math.PI) {
                        int y = (int) ((line * lineHeight) + yMargin);
                        g.drawLine(xMargin, y, width, y);
                    } else {
                        g.setFont(l.font);
                        if (l.x == Double.POSITIVE_INFINITY) {
                            l.x = getXMiddle(l.line, g.getFontMetrics());
                        } else if (l.x == Double.NEGATIVE_INFINITY) {
                            l.x = getXRight(l.line, g.getFontMetrics());
                        }
                        l.x += l.xAddon;
                        int y = (int) ((line * lineHeight) + yMargin);
                        g.drawString(l.line, (int) l.x, (int) (y + l.yAddon));
                    }
                    line += l.lineAdd;
                }
                //Footer
                g.setFont(footF);
                g.drawString("Seite", xMargin, height - 30);
                int showIndex = pageIndex + 1;
                g.drawString(showIndex + "/" + pageLineList.size(), (int) getXRight(pageIndex + "/" + pageLineList.size(), g.getFontMetrics()), height - 30);
                Color temp = g.getColor();
                g.setColor(new Color(169, 169, 169));
                g.drawLine(xMargin, height - 40, width, height - 40);
                g.setColor(temp);

                return PAGE_EXISTS;
            } else if (pageIndex >= pageLineList.size() && pageIndex < objectList.size()) {

                return NO_SUCH_PAGE;
            }
            return NO_SUCH_PAGE;
        }

        private double getXMiddle(String line, FontMetrics metrics){
            return (double) (width - metrics.stringWidth(line)) / 2;
        }

        private double getXRight(String line, FontMetrics metrics){
            return width - metrics.stringWidth(line);
        }

        private double calcParaLines(String par, int width, Font font) {
            double lines = 0;
            while (par.length() > 0) {
                String s = par;
                int cutOff = 0;
                if (getTextWidth(s, font) > (width + 50)) {
                    while (getTextWidth(s, font) > (width + 50)) {
                        cutOff = s.lastIndexOf(" ");
                        s = s.substring(0, cutOff);
                    }
                    if (s.indexOf(" ") == 0) {
                        s = s.substring(1);
                    }
                    lines += 1.3;
                    par = par.substring(cutOff);
                } else {
                    if (s.indexOf(" ") == 0) {
                        s = s.substring(1);
                    }
                    lines += 1.3;
                    par = "";
                }
            }
            lines += 0.7;
            return lines;
        }

        private int getTextWidth(String text, Font font){
            AffineTransform affinetransform = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
            return (int)(font.getStringBounds(text, frc).getWidth());
        }
    }
}