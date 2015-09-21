package com.ritcat14.GotYourSix.graphics;

import com.ritcat14.GotYourSix.graphics.Screen;

public class Font {

    private static SpriteSheet font       = new SpriteSheet("/fonts/custom.png", 8);
    private static Sprite[]    characters = Sprite.split(font);

    private static String      charIndex  = "abcdefghijklm" + //
                                            "nopqrstuvwxyz" + //
                                            "ABCDEFGHIJKLM" + //
                                            "NOPQRSTUVWXYZ" + //
                                            "0123456789,.?!";

    public Font() {

    }

    public void render(int x, int y, String text, Screen screen) {
        render(x, y, 0, 0, text, screen);
    }

    public void render(int x, int y, int color, String text, Screen screen) {
        render(x, y, 0, color, text, screen);
    }

    public void render(int x, int y, int spacing, int color, String text, Screen screen) { 
        int xOffset = 0;
        int line = 0;
        for (int i = 0; i < text.length(); i++) {
            xOffset += 8 + spacing;
        		int yOffset = 0;
            char currentChar = text.charAt(i);
            if (currentChar == '\n'){
                xOffset = 0;
                line++;
            }
            int index = charIndex.indexOf(currentChar);
            if (index < 0) continue;
            screen.renderTextCharacter(x + xOffset, y + line * 10 + yOffset, characters[index], color, false);
        }
    }

}
