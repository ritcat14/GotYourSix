package com.ritcat14.GotYourSix.graphics.UI;

import org.w3c.dom.ranges.RangeException;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.ritcat14.GotYourSix.util.Vector2i;

public class UIProgressBar extends UIComponent {

    private Vector2i      size             = null;
    private double        progress         = 0.0; //0.0 - 1.0
    private Color         foregroundColour = null;
    private BufferedImage back             = null, fore = null;
    private UILabel       percent          = null;

    public UIProgressBar(Vector2i position, Vector2i size) {
        super(position);
        this.size = size;
        foregroundColour = new Color(0xff00ff);
    }

    public UIProgressBar(Vector2i position, Vector2i size, BufferedImage background, BufferedImage foreground) {
        super(position);
        this.back = background;
        this.fore = foreground;
        this.size = size;
        percent =
                  new UILabel(new Vector2i(this.getAbsolutePosition().x + (background.getWidth() / 2), this.getAbsolutePosition().y),
                              ((int)(progress * 100)) + "%");
        percent.setFont(new Font("Magneto", Font.BOLD, 6));
    }

    public UIProgressBar(Vector2i position, BufferedImage background, BufferedImage foreground) {
        super(position);
        this.back = background;
        this.fore = foreground;
        this.size = new Vector2i(background.getWidth(), background.getHeight());
        percent =
                  new UILabel(new Vector2i(this.getAbsolutePosition().x + (background.getWidth() / 2), this.getAbsolutePosition().y),
                              ((int)(progress * 100)) + "%");
        percent.setFont(new Font("Magneto", Font.BOLD, 12));
    }

    public void setProgress(double progress) {
        if (progress < 0.0 || progress > 1.0)
            throw new RangeException(RangeException.BAD_BOUNDARYPOINTS_ERR, "Progress must be between 0.0 and 1.0");
        this.progress = progress;
        percent.setText((int)(progress * 100) + "%");
    }

    public void setForegroundColour(int colour) {
        this.foregroundColour = new Color(colour);
    }

    public double getProgress() {
        return progress;
    }

    public void update() {
        percent.update();
        percent.position = (new Vector2i((this.getAbsolutePosition().x + (size.x / 2)) - 6, this.getAbsolutePosition().y));
    }

    public void render(Graphics g) {
        if (back != null) {
            g.drawImage(back, position.x + offset.x, position.y + offset.y, size.x, size.y, null);
        }
        if (fore != null) {
            g.drawImage(fore, position.x + offset.x, position.y + offset.y, (int)(progress * (size.x)), size.y, null);
        } else {
            g.setColor(colour);
            g.fillRect(position.x + offset.x, position.y + offset.y, size.x, size.y);

            g.setColor(foregroundColour);
            g.fillRect(position.x + offset.x, position.y + offset.y, (int)(progress * size.x), size.y);
        }
        percent.render(g);
    }

}
