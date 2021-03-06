package com.ritcat14.GotYourSix.graphics.UI;

import com.ritcat14.GotYourSix.util.Vector2i;
import com.ritcat14.GotYourSix.input.Mouse;
import com.ritcat14.GotYourSix.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class UIButton extends UIComponent {

    public UILabel           label          = null;
    private UIButtonListener buttonListener = null;
    private UIActionListener actionListener = null;

    private BufferedImage    image          = null;

    private boolean          inside         = false;
    private boolean          pressed        = false;
    private boolean          ignorePressed  = false;
    private String           text           = "";

    public UIButton(Vector2i position, Vector2i size, UIActionListener actionListener, String text) {
        super(position, size);
        this.actionListener = actionListener;
        Vector2i lp = new Vector2i(position);
        this.text = text;
        lp.x += 4;
        lp.y += size.y - 5;
        label = new UILabel(lp, text);
        label.active = false;
        init();
    }

    public UIButton(Vector2i position, BufferedImage image, UIActionListener actionListener, String text) {
        super(position, new Vector2i(image.getWidth(), image.getHeight()));
        Dimension imageDim = new Dimension(image.getWidth(), image.getHeight());
        Dimension bound = new Dimension(Game.getAbsoluteWidth() / 4, Game.getAbsoluteHeight() / 4);
        Dimension finalDim = Game.getScaledDimension(imageDim, bound);
        setSize(new Vector2i(finalDim.width, finalDim.height));
        this.actionListener = actionListener;
        this.image = image;
        setImage(image);
        this.text = text;
        init();
    }

    private void init() {
        setColor(0xAAAAAA);
        buttonListener = new UIButtonListener();
    }

    void init(UIPanel panel) {
        super.init(panel);
        if (label != null)
            panel.addComponent(label);
    }

    public void setButtonListener(UIButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setText(String text) {
        this.text = text;
        if (text == "")
            label.active = false;
        else
            label.text = text;
    }

    public void update() {
        Rectangle rect = new Rectangle(getAbsolutePosition().x, getAbsolutePosition().y, size.x, size.y);
        boolean leftMouseDown = Mouse.getButton() == MouseEvent.BUTTON1;
        if (rect.contains(new Point(Mouse.getX(), Mouse.getY()))) {
            if (!inside) {
                if (leftMouseDown)
                    ignorePressed = true;
                else
                    ignorePressed = false;
                buttonListener.entered(this);
            }
            inside = true;

            if (!pressed && !ignorePressed && leftMouseDown) {
                pressed = true;
                buttonListener.pressed(this);
            } else if (Mouse.getButton() == MouseEvent.NOBUTTON) {
                if (pressed) {
                    buttonListener.released(this);
                    pressed = false;
                    actionListener.perform();
                }
                ignorePressed = false;
            }
        } else {
            if (inside) {
                buttonListener.exited(this);
                pressed = false;
            }
            inside = false;
        }
    }

    public void render(Graphics g) {
        int x = position.x + offset.x;
        int y = position.y + offset.y;
        if (image != null) {
            g.drawImage(image, x, y, size.x, size.y, null);
        } else {
            g.setColor(colour);
            g.fillRect(x, y, size.x, size.y);
            if (label != null)
                label.render(g);
        }
    }

}
