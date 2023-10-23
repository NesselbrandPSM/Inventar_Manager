package Main.utility.Printer;

import Main.utility.ADWrapper;
import Main.utility.Constants;
import Main.utility.UtilPrintables.IVObject;
import Main.utility.UtilPrintables.IVObjectRet;
import Main.utility.UtilPrintables.IVObjectType;
import Main.utility.UtilPrintables.Line;
import Main.utility.Utils;
import SQL.SQLConnector;
import SQL.Statements.SQLSelectStatements;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.print.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class ArbeitsmittelPrinter {

    public static void print(String name, int flag) {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = new PageFormat();
        Paper a4 = new Paper();
        int width = 595;
        int height = 842;
        a4.setSize(width, height);
        a4.setImageableArea(0, 0, width, height);
        pageFormat.setPaper(a4);

        PrintObject p = new PrintObject();
        p.init(flag);
        p.calculatePages(pageFormat, name, flag);
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
        private Font tableHeaderF;
        private Font tableF;

        private Font bigF;
        private Font mediumF;

        private int xMargin;
        private int yMargin;
        private int lineHeight;

        public int width;
        public int height;

        private static final String font = "Arial";

        private int flag;

        private final int max_width_secondColumn = 290;

        private SQLSelectStatements sqlSelectStatements;

        ArrayList<ArrayList<Line>> pageLineList;
        ArrayList<ArrayList<IVObject>> objectPageList;
        ArrayList<ArrayList<IVObjectRet>> objectPageListRet;

        // 0 -> überlassung
        // 1 -> HomeOffice
        // 2 -> Arbeitsmittelliste
        // 3 -> Arbeitsmittelliste mit Rueckgabe
        public void init(int flag) {
            sqlSelectStatements = new SQLSelectStatements(new SQLConnector());

            this.flag = flag;
            titleF = new Font(font, Font.BOLD, 13);
            standardF = new Font(font, Font.PLAIN, 10);
            paragraphF = new Font(font, Font.BOLD, 10);
            footF = new Font(font, Font.PLAIN, 7);
            tableHeaderF = new Font(font, Font.BOLD, 12);
            tableF = new Font(font, Font.PLAIN, 12);
            bigF = new Font(font, Font.PLAIN, 28);
            mediumF = new Font(font, Font.PLAIN, 19);

            xMargin = 40;
            yMargin = 60;
            lineHeight = 10;
        }

        public void calculatePages(PageFormat pageFormat, String name, int flag) {
            width = (int) pageFormat.getImageableWidth() - xMargin;
            height = (int) pageFormat.getImageableHeight();

            switch (flag) {
                // Replacementitems:
                // - <<working_days>> => arbeitstage
                // - <<working_hours>> => arbeitszeit
                // - <<contract_date>> => Arbeitsvertragsdatum
                // - <<ret>> => zeilenumbruch

                case 0 -> {
                    ArrayList<String> temp = new ArrayList<>();
                    for (String s : Constants.PRINT_paragraphsUeberlassung) {
                        if (!s.equals("")) {
                            temp.add(s);
                        }
                    }
                    Constants.PRINT_paragraphsUeberlassung = temp.toArray(new String[0]);

                    String[] userData = sqlSelectStatements.getUserAttributes(name);

                    for (int i = 0; i < Constants.PRINT_paragraphsUeberlassung.length; i++) {
                        if (Constants.PRINT_paragraphsUeberlassung[i].contains("<<contract_date>>")) {
                            Constants.PRINT_paragraphsUeberlassung[i] = Constants.PRINT_paragraphsUeberlassung[i].replace("<<contract_date>>", " " + userData[5] + " ");
                        }
                    }

                    int maxLines = 76;
                    pageLineList = new ArrayList<>();
                    //page 1 with default
                    pageLineList.add(new ArrayList<>());
                    name = ADWrapper.getFullName(name);
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

                    for (int i = 0; i < Constants.PRINT_paragraphsUeberlassung.length; i++) {
                        String paragraphC = Constants.PRINT_paragraphsUeberlassung[i];
                        if ((2 + currentLine + calcParaLines(paragraphC, internalWidth, standardF)) < maxLines) {
                            pageLineList.get(page).add(new Line("§ " + paraIndex, xMargin, 2, paragraphF));
                            currentLine += 2;

                            String paragraph = paragraphC;
                            while (paragraph.length() > 0) {
                                String line = paragraph;
                                while (getTextWidth(line, standardF) > internalWidth) {
                                    line = line.substring(0, line.lastIndexOf(" "));
                                }
                                paragraph = paragraph.substring(line.length());
                                if (line.substring(0, 1).equals(" ")) {
                                    line = line.substring(1);
                                }
                                pageLineList.get(page).add(new Line(line, xMargin, 1.3, standardF));
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
                    if ((currentLine + tempLineAddon) > maxLines) {
                        page++;
                        currentLine = 1;
                        pageLineList.add(new ArrayList<>());
                    }
                    pageLineList.get(page).add(new Line("", xMargin, 3, standardF));
                    pageLineList.get(page).add(new Line("Buttenheim, _____________", xMargin, 5, standardF));
                    pageLineList.get(page).add(new Line("________________________________", xMargin, 0, standardF));
                    pageLineList.get(page).add(new Line("________________________________", width - getTextWidth("________________________________", standardF) - 20, 1.5, standardF, 0, -10));
                    pageLineList.get(page).add(new Line("Unterschrift Arbeitnehmer", xMargin, 0, standardF));
                    pageLineList.get(page).add(new Line("Unterschrift Arbeitgeber", width - getTextWidth("________________________________", standardF) - 20, 3, standardF, 0, -10));
                    currentLine += tempLineAddon;
                }
                case 1 -> {
                    ArrayList<ArrayList<String>> temp = new ArrayList<>();
                    for (int i = 0; i < Constants.PRINT_paragraphsHomeOffice.length; i++) {
                        if (!Constants.PRINT_paragraphsHomeOffice[i][1].equals("")) {
                            ArrayList<String> temp2 = new ArrayList<>();
                            temp2.add(Constants.PRINT_paragraphsHomeOffice[i][0]);
                            temp2.add(Constants.PRINT_paragraphsHomeOffice[i][1]);
                            temp.add(temp2);
                        }
                    }
                    Constants.PRINT_paragraphsHomeOffice = Utils.convertArrayList_ArrayList_StringTo2DArray(temp);

                    String[] userData = sqlSelectStatements.getUserInfos(name);

                    int maxLines = 65;
                    int xAddon = 2;
                    pageLineList = new ArrayList<>();
                    //page 1 with default
                    pageLineList.add(new ArrayList<>());
                    pageLineList.get(0).add(new Line("Vereinbarung über zeitanteilige", Double.POSITIVE_INFINITY, 7.5, bigF, 40, xAddon));
                    pageLineList.get(0).add(new Line("Arbeit im Homeoffice", Double.POSITIVE_INFINITY, 4, bigF, 0, xAddon));
                    pageLineList.get(0).add(new Line("- nachfolgend Homeoffice Vereinbarung genannt -", Double.POSITIVE_INFINITY, 5, mediumF, 0, xAddon));
                    pageLineList.get(0).add(new Line("Zwischen", Double.POSITIVE_INFINITY, 2.5, standardF, 0, xAddon));
                    pageLineList.get(0).add(new Line("Apocare GmbH", Double.POSITIVE_INFINITY, 1.5, standardF, 0, xAddon));
                    pageLineList.get(0).add(new Line("im Gewerbepark 11", Double.POSITIVE_INFINITY, 1.5, standardF, 0, xAddon));
                    pageLineList.get(0).add(new Line("96158 Buttenheim", Double.POSITIVE_INFINITY, 2.5, standardF, 0, xAddon));
                    pageLineList.get(0).add(new Line("- nachfolgend Arbeitgeber genannt -", Double.POSITIVE_INFINITY, 6, standardF, 0, xAddon));

                    pageLineList.get(0).add(new Line("Und", Double.POSITIVE_INFINITY, 2.5, standardF, 0, xAddon));
                    pageLineList.get(0).add(new Line(ADWrapper.getFullName(name), Double.POSITIVE_INFINITY, 1.5, standardF, 0, xAddon));
                    pageLineList.get(0).add(new Line(userData[2], Double.POSITIVE_INFINITY, 2.5, standardF, 0, xAddon));
                    pageLineList.get(0).add(new Line("- nachfolgend Arbeitnehmer/in genannt -", Double.POSITIVE_INFINITY, 2.5, standardF, 0, xAddon));


                    double currentLine = 1;
                    int page = 1;
                    pageLineList.add(new ArrayList<>());
                    int paraIndex = 0;
                    int internalWidth = width - xMargin;

                    String[][] paragraphsHO = Constants.PRINT_paragraphsHomeOffice.clone();
                    for (int i = 0; i < paragraphsHO.length; i++) {
                        if (paragraphsHO[i][1].contains("<<working_days>>")) {
                            paragraphsHO[i][1] = paragraphsHO[i][1].replace("<<working_days>>", " " + userData[4] + " ");
                        }
                        if (paragraphsHO[i][1].contains("<<working_hours>>")) {
                            paragraphsHO[i][1] = paragraphsHO[i][1].replace("<<working_hours>>", " " + userData[3] + " ");
                        }
                    }

                    for (int i = 0; i < paragraphsHO.length; i++) {
                        String paragraphC = paragraphsHO[i][1];
                        if ((2 + currentLine + calcParaLines(paragraphC, internalWidth, standardF)) < maxLines) {
                            if (paraIndex == 0) {
                                pageLineList.get(page).add(new Line(paragraphsHO[i][0], xMargin, 2, paragraphF));
                            } else {
                                pageLineList.get(page).add(new Line("§ " + paraIndex + " " + paragraphsHO[i][0], xMargin, 2, paragraphF));
                            }
                            currentLine += 2;

                            String paragraph = paragraphC;
                            if (paragraph.contains("<<ret>>")) {
                                ArrayList<String> subparagraphList = new ArrayList<>();
                                paragraph = paragraph.replaceAll("\n", "");
                                while (paragraph.length() > 0) {
                                    subparagraphList.add(paragraph.substring(0, paragraph.indexOf("<<ret>>")));
                                    paragraph = paragraph.substring(paragraph.indexOf("<<ret>>") + 7);
                                    if (!paragraph.contains("<<ret>>")) {
                                        subparagraphList.add(paragraph);
                                        paragraph = "";
                                    }
                                }
                                String[] subparagraphs = subparagraphList.toArray(new String[0]);
                                for (int j = 0; j < subparagraphs.length; j++) {
                                    if (subparagraphs[j].indexOf(" ") == 0) {
                                        subparagraphs[j] = subparagraphs[j].substring(1);
                                    }
                                }
                                subparagraphList.clear();
                                Collections.addAll(subparagraphList, subparagraphs);

                                for (String s : subparagraphList) {
                                    while (s.length() > 0) {
                                        String line = s;
                                        while (getTextWidth(line, standardF) > internalWidth) {
                                            line = line.substring(0, line.lastIndexOf(" "));
                                        }
                                        s = s.substring(line.length());
                                        if (line.substring(0, 1).equals(" ")) {
                                            line = line.substring(1);
                                        }
                                        pageLineList.get(page).add(new Line(line, xMargin, 1.3, standardF));
                                        currentLine += 1.3;
                                    }
                                    pageLineList.get(page).get(pageLineList.get(page).size() - 1).lineAdd = 2;
                                    currentLine += 0.7;
                                }
                            } else {
                                while (paragraph.length() > 0) {
                                    String line = paragraph;
                                    while (getTextWidth(line, standardF) > internalWidth) {
                                        line = line.substring(0, line.lastIndexOf(" "));
                                    }
                                    paragraph = paragraph.substring(line.length());
                                    if (line.substring(0, 1).equals(" ")) {
                                        line = line.substring(1);
                                    }
                                    pageLineList.get(page).add(new Line(line, xMargin, 1.3, standardF));
                                    currentLine += 1.3;
                                }
                                pageLineList.get(page).get(pageLineList.get(page).size() - 1).lineAdd = 2;
                                currentLine += 0.7;
                            }
                            paraIndex++;
                        } else {
                            pageLineList.add(new ArrayList<>());
                            page++;
                            currentLine = 1;
                            i--;
                        }
                    }

                    double tempLineAddon = 6.5;
                    if ((currentLine + tempLineAddon) > maxLines) {
                        page++;
                        currentLine = 1;
                        pageLineList.add(new ArrayList<>());
                    }
                    pageLineList.get(page).add(new Line("", xMargin, 5, standardF));
                    pageLineList.get(page).add(new Line("____________________________________", xMargin, 0, standardF));
                    pageLineList.get(page).add(new Line("____________________________________", width - getTextWidth("____________________________________", standardF) - 20, 1.5, standardF, 0, -10));
                    pageLineList.get(page).add(new Line("Ort/Datum/Unterschrift Arbeitnehmer", xMargin, 0, standardF, 0, 0));
                    pageLineList.get(page).add(new Line("Ort/Datum/Unterschrift Arbeitgeber", width - getTextWidth("____________________________________", standardF) - 20, 3, standardF, 0, -10));
                    currentLine += tempLineAddon;
                }
                case 2 -> {
                    int maxLines = 51;
                    ArrayList<IVObject> objects = new SQLSelectStatements(new SQLConnector()).getUserObjects(name);
                    int currentLine = 1;
                    int page = 0;
                    objectPageList = new ArrayList<>();
                    objectPageList.add(new ArrayList<>());

                    for (int i = 0; i < objects.size(); i++) {
                        IVObject object = objects.get(i);
                        int calcLines = (int) Math.ceil(calcTableLines(object, max_width_secondColumn));
                        if ((currentLine + calcLines) < maxLines) {
                            objectPageList.get(page).add(object);
                            currentLine += calcLines;
                        } else {
                            objectPageList.add(new ArrayList<>());
                            page++;
                            currentLine = 1;
                            i--;
                        }
                    }

                    if ((currentLine + 8) > maxLines) {
                        page++;
                        objectPageList.add(new ArrayList<>());
                    }
                    objectPageList.get(page).add(new IVObject(new String[]{"sign"}, IVObjectType.pc));
                }
                case 3 -> {
                    int maxLines = 51;
                    ArrayList<IVObjectRet> objects = Constants.returnList;
                    int currentLine = 1;
                    int page = 0;
                    objectPageListRet = new ArrayList<>();
                    objectPageListRet.add(new ArrayList<>());

                    for (int i = 0; i < objects.size(); i++) {
                        IVObjectRet object = objects.get(i);
                        int calcLines = (int) Math.ceil(calcTableLines(object, max_width_secondColumn));
                        if ((currentLine + calcLines) < maxLines) {
                            objectPageListRet.get(page).add(object);
                            currentLine += calcLines;
                        } else {
                            objectPageListRet.add(new ArrayList<>());
                            page++;
                            currentLine = 1;
                            i--;
                        }
                    }

                    if ((currentLine + 8) > maxLines) {
                        page++;
                        objectPageListRet.add(new ArrayList<>());
                    }
                    objectPageListRet.get(page).add(new IVObjectRet(new String[]{"sign"}, new String[]{"sign"}));
                }
            }
        }

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
            Graphics2D g = (Graphics2D) graphics;
            g.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            switch (flag) {
                case 0 -> {
                    int pagesGes = pageLineList.size();
                    if (pageIndex == 0) {
                        double line = 0;
                        g.setFont(standardF);
                        for (Line l : pageLineList.get(0)) {
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
                        g.drawString("Überlassung von Arbeitsmitteln", xMargin, height - 30);
                        int showIndex = pageIndex + 1;
                        g.drawString("Seite " + showIndex + "/" + pagesGes, (int) getXRight("Seite " + pageIndex + "/" + pagesGes, g.getFontMetrics()), height - 30);
                        Color temp = g.getColor();
                        g.setColor(new Color(169, 169, 169));
                        g.drawLine(xMargin, height - 40, width, height - 40);
                        g.setColor(temp);

                        return PAGE_EXISTS;
                    } else if (pageIndex > 0 && pageIndex < pageLineList.size()) {
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
                        g.drawString("Überlassung von Arbeitsmitteln", xMargin, height - 30);
                        int showIndex = pageIndex + 1;
                        g.drawString("Seite " + showIndex + "/" + pagesGes, (int) getXRight("Seite " + pageIndex + "/" + pagesGes, g.getFontMetrics()), height - 30);
                        Color temp = g.getColor();
                        g.setColor(new Color(169, 169, 169));
                        g.drawLine(xMargin, height - 40, width, height - 40);
                        g.setColor(temp);

                        return PAGE_EXISTS;
                    }
                    return NO_SUCH_PAGE;
                }
                case 1 -> {
                    int pagesGes = pageLineList.size();
                    if (pageIndex == 0) {
                        double line = 0;
                        g.setFont(standardF);
                        for (Line l : pageLineList.get(0)) {
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
                        g.drawString("Homeoffice Vereinbarung", xMargin, height - 30);
                        int showIndex = pageIndex + 1;
                        g.drawString("Seite " + showIndex + "/" + pagesGes, (int) getXRight("Seite " + pageIndex + "/" + pagesGes, g.getFontMetrics()), height - 30);
                        Color temp = g.getColor();
                        g.setColor(new Color(169, 169, 169));
                        g.drawLine(xMargin, height - 40, width, height - 40);
                        g.setColor(temp);

                        return PAGE_EXISTS;
                    } else if (pageIndex > 0 && pageIndex < pageLineList.size()) {
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
                        g.drawString("Homeoffice Vereinbarung", xMargin, height - 30);
                        int showIndex = pageIndex + 1;
                        g.drawString("Seite " + showIndex + "/" + pagesGes, (int) getXRight("Seite " + pageIndex + "/" + pagesGes, g.getFontMetrics()), height - 30);
                        Color temp = g.getColor();
                        g.setColor(new Color(169, 169, 169));
                        g.drawLine(xMargin, height - 40, width, height - 40);
                        g.setColor(temp);

                        return PAGE_EXISTS;
                    }
                    return NO_SUCH_PAGE;
                }
                case 2 -> {
                    double line = 1;
                    if (pageIndex == 0) {
                        g.setFont(titleF);
                        int y = (int) ((line * lineHeight) + yMargin);
                        int x = (int) getXMiddle("Arbeitsmittel Anhang", g.getFontMetrics());

                        g.drawString("Arbeitsmittel Anhang", x, y);
                        line += 3;

                        for (IVObject ob : objectPageList.get(pageIndex)) {
                            if (!ob.values[0].equals("sign")) {
                                int yStart;
                                g.setFont(tableHeaderF);
                                line += 2;
                                x = xMargin + 10;
                                int secondColumnOffset = 200;
                                y = (int) ((line * lineHeight) + yMargin);
                                yStart = y - 15;
                                g.drawString("Arbeitsmittel:", x, y);
                                g.drawString(ob.values[0], x + secondColumnOffset, y);
                                g.drawLine(xMargin + 5, y + 5, width - xMargin + 25, y + 5);
                                g.setFont(tableF);
                                line += 1.8;
                                for (int i = 1; i < ob.values.length; i++) {
                                    if (!Objects.equals(ob.values[i], "null") && !Objects.equals(ob.values[i], " - ")) {
                                        y = (int) ((line * lineHeight) + yMargin);
                                        g.drawString(Utils.ivObjectToDisplayable(ob.objectType.getAttributes()[i]), x, y);

                                        if (getTextWidth(ob.values[i], tableF) > max_width_secondColumn) {
                                            String paragraph = ob.values[i];
                                            while (paragraph.length() > 0) {
                                                String lines = paragraph;
                                                while (getTextWidth(lines, tableF) > max_width_secondColumn) {
                                                    lines = lines.substring(0, lines.lastIndexOf(" "));
                                                }
                                                paragraph = paragraph.substring(lines.length());
                                                if (lines.substring(0, 1).equals(" ")) {
                                                    lines = lines.substring(1);
                                                }
                                                y = (int) ((line * lineHeight) + yMargin);
                                                g.drawString(lines, x + secondColumnOffset, y);
                                                line += 1.8;
                                            }
                                        } else {
                                            g.drawString(ob.values[i], x + secondColumnOffset, y);
                                            if (!(i == ob.values.length - 1)) {
                                                g.drawLine(xMargin + 5, y + 5, width - xMargin + 25, y + 5);
                                            }
                                            line += 1.8;
                                        }
                                    }

                                }
                                g.drawRect(xMargin + 5, yStart, width - xMargin - 20, y - yStart + 10);
                                g.drawLine(xMargin + secondColumnOffset - 3, yStart, xMargin + secondColumnOffset - 3, y + 10);
                            } else {
                                line+= 4;
                                y = (int) ((line * lineHeight) + yMargin);
                                x = 40;
                                g.drawString("____________________________", x, y);
                                g.drawString("____________________________", width - 220, y);
                                line += 1.7;
                                y = (int) ((line * lineHeight) + yMargin);
                                g.drawString("Unterschrift Arbeitnehmer", x, y);
                                g.drawString("Unterschrift Arbeitgeber", width - 220, y);
                            }
                        }

                        //Footer
                        g.setFont(footF);
                        g.drawString("Arbeitsmittelanhang", xMargin, height - 30);
                        int showIndex = pageIndex + 1;
                        g.drawString("Seite " + showIndex + "/" + objectPageList.size(), (int) getXRight("Seite " + pageIndex + "/" + objectPageList.size(), g.getFontMetrics()), height - 30);
                        Color temp = g.getColor();
                        g.setColor(new Color(169, 169, 169));
                        g.drawLine(xMargin, height - 40, width, height - 40);
                        g.setColor(temp);

                        return PAGE_EXISTS;
                    } else if (pageIndex < objectPageList.size()) {
                        int x;
                        int y;

                        for (IVObject ob : objectPageList.get(pageIndex)) {
                            if (!ob.values[0].equals("sign")) {

                                int yStart;
                                g.setFont(tableHeaderF);
                                line += 2;
                                x = xMargin + 10;
                                int secondColumnOffset = 200;
                                y = (int) ((line * lineHeight) + yMargin);
                                yStart = y - 15;
                                g.drawString("Arbeitsmittel:", x, y);
                                g.drawString(ob.values[0], x + secondColumnOffset, y);
                                g.drawLine(xMargin + 5, y + 5, width - xMargin + 25, y + 5);
                                g.setFont(tableF);
                                line += 1.8;
                                for (int i = 1; i < ob.values.length; i++) {
                                    if (!Objects.equals(ob.values[i], "null") && !Objects.equals(ob.values[i], " - ")) {
                                        y = (int) ((line * lineHeight) + yMargin);
                                        g.drawString(Utils.ivObjectToDisplayable(ob.objectType.getAttributes()[i]), x, y);

                                        if (getTextWidth(ob.values[i], tableF) > max_width_secondColumn) {
                                            String paragraph = ob.values[i];
                                            while (paragraph.length() > 0) {
                                                String lines = paragraph;
                                                while (getTextWidth(lines, tableF) > max_width_secondColumn) {
                                                    lines = lines.substring(0, lines.lastIndexOf(" "));
                                                }
                                                paragraph = paragraph.substring(lines.length());
                                                if (lines.substring(0, 1).equals(" ")) {
                                                    lines = lines.substring(1);
                                                }
                                                y = (int) ((line * lineHeight) + yMargin);
                                                g.drawString(lines, x + secondColumnOffset, y);
                                                line += 1.8;
                                            }
                                        } else {
                                            g.drawString(ob.values[i], x + secondColumnOffset, y);
                                            if (!(i == ob.values.length - 1)) {
                                                g.drawLine(xMargin + 5, y + 5, width - xMargin + 25, y + 5);
                                            }
                                            line += 1.8;
                                        }
                                    }
                                }
                                g.drawRect(xMargin + 5, yStart, width - xMargin - 20, y - yStart + 10);
                                g.drawLine(xMargin + secondColumnOffset - 3, yStart, xMargin + secondColumnOffset - 3, y + 10);
                            } else {
                                line+= 4;
                                y = (int) ((line * lineHeight) + yMargin);
                                x = 40;
                                g.drawString("____________________________", x, y);
                                g.drawString("____________________________", width - 220, y);
                                line += 1.7;
                                y = (int) ((line * lineHeight) + yMargin);
                                g.drawString("Unterschrift Arbeitnehmer", x, y);
                                g.drawString("Unterschrift Arbeitgeber", width - 220, y);
                            }
                        }

                        //Footer
                        g.setFont(footF);
                        g.drawString("Arbeitsmittelanhang", xMargin, height - 30);
                        int showIndex = pageIndex + 1;
                        g.drawString("Seite " + showIndex + "/" + objectPageList.size(), (int) getXRight("Seite " + pageIndex + "/" + objectPageList.size(), g.getFontMetrics()), height - 30);
                        Color temp = g.getColor();
                        g.setColor(new Color(169, 169, 169));
                        g.drawLine(xMargin, height - 40, width, height - 40);
                        g.setColor(temp);

                        return PAGE_EXISTS;
                    }
                    return NO_SUCH_PAGE;
                }
                case 3 -> {
                    double line = 1;
                    if (pageIndex == 0) {
                        g.setFont(titleF);
                        int y = (int) ((line * lineHeight) + yMargin);
                        int x = (int) getXMiddle("Arbeitsmittelrückgabe", g.getFontMetrics());

                        g.drawString("Arbeitsmittelrückgabe", x, y);
                        line += 2;
                        g.setFont(standardF);
                        y = (int) ((line * lineHeight) + yMargin);
                        x = (int) getXMiddle("Datum: " + Utils.getDateTimeNowDay(), g.getFontMetrics());
                        g.drawString("Datum: " + Utils.getDateTimeNowDay(), x, y);
                        line += 3;

                        for (IVObjectRet ob : objectPageListRet.get(pageIndex)) {
                            if (!ob.values[0].equals("sign")) {
                                int yStart;
                                g.setFont(tableHeaderF);
                                line += 2;
                                x = xMargin + 10;
                                int secondColumnOffset = 200;
                                y = (int) ((line * lineHeight) + yMargin);
                                yStart = y - 15;
                                g.drawString("Arbeitsmittel:", x, y);
                                g.drawString(ob.values[0], x + secondColumnOffset, y);
                                g.drawLine(xMargin + 5, y + 5, width - xMargin + 25, y + 5);
                                g.setFont(tableF);
                                line += 1.8;
                                for (int i = 1; i < ob.values.length; i++) {
                                    if (!Objects.equals(ob.values[i], "null") && !Objects.equals(ob.values[i], " - ")) {
                                        y = (int) ((line * lineHeight) + yMargin);
                                        g.drawString(Utils.ivObjectRetToDisplayable(ob.objectTypes[i]), x, y);

                                        if (getTextWidth(ob.values[i], tableF) > max_width_secondColumn) {
                                            String paragraph = ob.values[i];
                                            while (paragraph.length() > 0) {
                                                String lines = paragraph;
                                                while (getTextWidth(lines, tableF) > max_width_secondColumn) {
                                                    lines = lines.substring(0, lines.lastIndexOf(" "));
                                                }
                                                paragraph = paragraph.substring(lines.length());
                                                if (lines.substring(0, 1).equals(" ")) {
                                                    lines = lines.substring(1);
                                                }
                                                y = (int) ((line * lineHeight) + yMargin);
                                                g.drawString(lines, x + secondColumnOffset, y);
                                                line += 1.8;
                                            }
                                        } else {
                                            g.drawString(ob.values[i], x + secondColumnOffset, y);
                                            if (!(i == ob.values.length - 1)) {
                                                g.drawLine(xMargin + 5, y + 5, width - xMargin + 25, y + 5);
                                            }
                                            line += 1.8;
                                        }
                                    }
                                }
                                g.drawRect(xMargin + 5, yStart, width - xMargin - 20, y - yStart + 10);
                                g.drawLine(xMargin + secondColumnOffset - 3, yStart, xMargin + secondColumnOffset - 3, y + 10);
                            } else {
                                line+= 4;
                                y = (int) ((line * lineHeight) + yMargin);
                                x = 40;
                                g.drawString("____________________________", x, y);
                                g.drawString("____________________________", width - 220, y);
                                line += 1.7;
                                y = (int) ((line * lineHeight) + yMargin);
                                g.drawString("Unterschrift Arbeitnehmer", x, y);
                                g.drawString("Unterschrift Arbeitgeber", width - 220, y);
                            }
                        }

                        //Footer
                        g.setFont(footF);
                        g.drawString("Arbeitsmittelrückgabe", xMargin, height - 30);
                        int showIndex = pageIndex + 1;
                        g.drawString("Seite " + showIndex + "/" + objectPageListRet.size(), (int) getXRight("Seite " + pageIndex + "/" + objectPageListRet.size(), g.getFontMetrics()), height - 30);
                        Color temp = g.getColor();
                        g.setColor(new Color(169, 169, 169));
                        g.drawLine(xMargin, height - 40, width, height - 40);
                        g.setColor(temp);

                        return PAGE_EXISTS;
                    } else if (pageIndex < objectPageListRet.size()) {
                        int x;
                        int y;

                        for (IVObjectRet ob : objectPageListRet.get(pageIndex)) {
                            if (!ob.values[0].equals("sign")) {

                                int yStart;
                                g.setFont(tableHeaderF);
                                line += 2;
                                x = xMargin + 10;
                                int secondColumnOffset = 200;
                                y = (int) ((line * lineHeight) + yMargin);
                                yStart = y - 15;
                                g.drawString("Arbeitsmittel:", x, y);
                                g.drawString(ob.values[0], x + secondColumnOffset, y);
                                g.drawLine(xMargin + 5, y + 5, width - xMargin + 25, y + 5);
                                g.setFont(tableF);
                                line += 1.8;
                                for (int i = 1; i < ob.values.length; i++) {
                                    if (!Objects.equals(ob.values[i], "null") && !Objects.equals(ob.values[i], " - ")) {
                                        y = (int) ((line * lineHeight) + yMargin);
                                        g.drawString(Utils.ivObjectRetToDisplayable(ob.objectTypes[i]), x, y);

                                        if (getTextWidth(ob.values[i], tableF) > max_width_secondColumn) {
                                            String paragraph = ob.values[i];
                                            while (paragraph.length() > 0) {
                                                String lines = paragraph;
                                                while (getTextWidth(lines, tableF) > max_width_secondColumn) {
                                                    lines = lines.substring(0, lines.lastIndexOf(" "));
                                                }
                                                paragraph = paragraph.substring(lines.length());
                                                if (lines.substring(0, 1).equals(" ")) {
                                                    lines = lines.substring(1);
                                                }
                                                y = (int) ((line * lineHeight) + yMargin);
                                                g.drawString(lines, x + secondColumnOffset, y);
                                                line += 1.8;
                                            }
                                        } else {
                                            g.drawString(ob.values[i], x + secondColumnOffset, y);
                                            if (!(i == ob.values.length - 1)) {
                                                g.drawLine(xMargin + 5, y + 5, width - xMargin + 25, y + 5);
                                            }
                                            line += 1.8;
                                        }
                                    }
                                }
                                g.drawRect(xMargin + 5, yStart, width - xMargin - 20, y - yStart + 10);
                                g.drawLine(xMargin + secondColumnOffset - 3, yStart, xMargin + secondColumnOffset - 3, y + 10);
                            } else {
                                line+= 4;
                                y = (int) ((line * lineHeight) + yMargin);
                                x = 40;
                                g.drawString("____________________________", x, y);
                                g.drawString("____________________________", width - 220, y);
                                line += 1.7;
                                y = (int) ((line * lineHeight) + yMargin);
                                g.drawString("Unterschrift Arbeitnehmer", x, y);
                                g.drawString("Unterschrift Arbeitgeber", width - 220, y);
                            }
                        }

                        //Footer
                        g.setFont(footF);
                        g.drawString("Arbeitsmittelrückgabe", xMargin, height - 30);
                        int showIndex = pageIndex + 1;
                        g.drawString("Seite " + showIndex + "/" + objectPageListRet.size(), (int) getXRight("Seite " + pageIndex + "/" + objectPageListRet.size(), g.getFontMetrics()), height - 30);
                        Color temp = g.getColor();
                        g.setColor(new Color(169, 169, 169));
                        g.drawLine(xMargin, height - 40, width, height - 40);
                        g.setColor(temp);

                        return PAGE_EXISTS;
                    }
                    return NO_SUCH_PAGE;
                }
            }
            return NO_SUCH_PAGE;
        }

        private double getXMiddle(String line, FontMetrics metrics) {
            return (double) (width - metrics.stringWidth(line)) / 2;
        }

        private double getXRight(String line, FontMetrics metrics) {
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

        private double calcTableLines(IVObject ivObject, int width) {
            double lines = 0;
            for (String par : ivObject.values) {
                if (getTextWidth(par, tableF) > width) {

                    double temp = 0;
                    while (par.length() > 0) {
                        String s = par;
                        int cutOff = 0;
                        if (getTextWidth(s, tableF) > (width)) {
                            while (getTextWidth(s, tableF) > (width)) {
                                cutOff = s.lastIndexOf(" ");
                                s = s.substring(0, cutOff);
                            }
                            if (s.indexOf(" ") == 0) {
                                s = s.substring(1);
                            }
                            temp += 1.8;
                            par = par.substring(cutOff);
                        } else {
                            if (s.indexOf(" ") == 0) {
                                s = s.substring(1);
                            }
                            temp += 1.8;
                            par = "";
                        }
                    }
                    lines += temp;

                } else if (!par.equals("null") && !par.equals(" - ")) {
                    lines += 1.8;
                }
            }
            return lines;
        }

        private double calcTableLines(IVObjectRet ivObject, int width) {
            double lines = 0;
            for (String par : ivObject.values) {
                if (getTextWidth(par, tableF) > width) {

                    double temp = 0;
                    while (par.length() > 0) {
                        String s = par;
                        int cutOff = 0;
                        if (getTextWidth(s, tableF) > (width)) {
                            while (getTextWidth(s, tableF) > (width)) {
                                cutOff = s.lastIndexOf(" ");
                                s = s.substring(0, cutOff);
                            }
                            if (s.indexOf(" ") == 0) {
                                s = s.substring(1);
                            }
                            temp += 1.8;
                            par = par.substring(cutOff);
                        } else {
                            if (s.indexOf(" ") == 0) {
                                s = s.substring(1);
                            }
                            temp += 1.8;
                            par = "";
                        }
                    }
                    lines += temp;

                } else if (!par.equals("null") && !par.equals(" - ")) {
                    lines += 1.8;
                }
            }
            return lines;
        }

        private int getTextWidth(String text, Font font) {
            AffineTransform affinetransform = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
            return (int) (font.getStringBounds(text, frc).getWidth());
        }
    }
}