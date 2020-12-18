package com.Kreimben;

import java.awt.*;

public enum WTColor {

    BLUE(0, 122, 255),
    GREEN(52, 199, 89),
    INDIGO(88, 86, 214),
    ORANGE(255, 149, 0),
    PINK(255, 45, 85),
    PURPLE(175, 82, 222),
    RED(255, 59, 48),
    TEAL(90, 200, 250),
    LIGHT_GRAY(151,151,151),
    YELLOW(255, 204, 0);

    int r, g, b;

    private WTColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color getColor() { return new Color(r, g, b); }
}