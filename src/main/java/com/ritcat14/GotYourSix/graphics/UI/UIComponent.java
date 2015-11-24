package com.ritcat14.GotYourSix.graphics.UI;

import com.ritcat14.GotYourSix.events.Event;
import com.ritcat14.GotYourSix.events.EventListener;
import com.ritcat14.GotYourSix.input.Mouse;
import com.ritcat14.GotYourSix.util.Vector2i;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class UIComponent implements EventListener {

    public Vector2i    position = null, size = null;
    protected Vector2i offset   = null;
    public Color       colour   = null;
    protected UIPanel  panel    = null;

    public boolean     active   = true;

    public UIComponent(Vector2i position) {
        this.position = position;
        offset = new Vector2i();
    }

    public UIComponent(Vector2i position, Vector2i size) {
        this.position = position;
        this.size = size;
        offset = new Vector2i();
    }

    void init(UIPanel panel) {
        this.panel = panel;
    }

    public UIComponent setColor(int colour) {
        this.colour = new Color(colour);
        return this;
    }

    public void setSize(Vector2i size) {
        this.size = size;
    }

    public void update() {

    }

    public void render(Graphics g) {

    }

    public Vector2i getAbsolutePosition() {
        return new Vector2i(position).add(offset);
    }

    void setOffset(Vector2i offset) {
        this.offset = offset;
    }

    public void onEvent(Event event) {
    }

    public boolean hoverable() {
        if (!(this instanceof UIPanel))
            return false;
        return true;
    }

    public boolean isHovered() {
        if (!(this instanceof UIPanel)) {
            Rectangle r = new Rectangle(getAbsolutePosition().x, getAbsolutePosition().y, size.x, size.y);
            return (r.contains(new Point(Mouse.getX(), Mouse.getY())));
        }
        return false;
    }

}
