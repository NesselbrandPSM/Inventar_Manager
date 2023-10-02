package Main.utility.UtilPrintables;

import java.awt.*;

public class Line {

    public String line;
    public double x;
    public double yAddon;
    public double lineAdd;
    public Font font;
    public double xAddon;

    public Line(String line, double x, double lineAdd, Font font, double yAddon, double xAddon) {
        this.line = line;
        this.x = x;
        this.lineAdd = lineAdd;
        this.font = font;
        this.yAddon = yAddon;
        this.xAddon = xAddon;

        //special x values:
        // x = INFINITY => Text soll mittig aligned werden
        // x = -INFINITY => Text soll am rechten Rand aligned werden
        // x = Math.PI => Schwarzer Strich
    }

    public Line(String line, double x, double lineAdd, Font font) {
        this.line = line;
        this.x = x;
        this.lineAdd = lineAdd;
        this.font = font;
        this.yAddon = 0;
        this.xAddon = 0;
    }

    public Line(double x, double lineAdd) {
        this.x = Math.PI;
        this.lineAdd = lineAdd;
    }
}
