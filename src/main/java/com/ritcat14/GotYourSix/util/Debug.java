package com.ritcat14.GotYourSix.util;

import com.ritcat14.GotYourSix.graphics.Screen;

public class Debug {

    private Debug() {
    }

    public static void drawRect(Screen screen, int x, int y, int width, int height, boolean fixed) {
        screen.drawRect(x, y, width, height, 0xffff0000, fixed);
    }

    public static void drawRect(Screen screen, int x, int y, int width, int height, int col, boolean fixed) {
        screen.drawRect(x, y, width, height, col, fixed);
    }

}
