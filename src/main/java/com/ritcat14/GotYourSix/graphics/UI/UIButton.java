package com.ritcat14.GotYourSix.graphics.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.ritcat14.GotYourSix.graphics.UI.UIButtonListener;
import com.ritcat14.GotYourSix.graphics.UI.UIComponent;
import com.ritcat14.GotYourSix.util.Vector2i;
import com.ritcat14.GotYourSix.input.Mouse;

public class UIButton extends UIComponent {

    public UILabel           label;
    private UIButtonListener buttonListener;
    private UIActionListener actionListener;

    private boolean          inside        = false;
    private boolean          pressed       = false;
    private boolean          ignorePressed = false;

    public UIButton(Vector2i position, Vector2i size, UIActionListener actionListener) {
        super(position, size);
        this.actionListener = actionListener;
        Vector2i lp = new Vector2i(position);
        lp.x += 4;
        lp.y += size.y - 5;
        label = new UILabel(lp, "");
        label.setColor(0x444444);
        label.active = false;

        setColor(0xAAAAAA);

        buttonListener = new UIButtonListener();
    }

    void init(UIPanel panel) {
        super.init(panel);
        panel.addComponent(label);
    }

    public void setText(String text) {
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
        g.setColor(colour);
        g.fillRect(position.x + offset.x, position.y + offset.y, size.x, size.y);
        if (label != null)
            label.render(g);
    }

}
