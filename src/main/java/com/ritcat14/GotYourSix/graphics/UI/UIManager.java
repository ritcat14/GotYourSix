package com.ritcat14.GotYourSix.graphics.UI;

import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;

import com.ritcat14.GotYourSix.events.Event;
import com.ritcat14.GotYourSix.graphics.UI.UIPanel;
import com.ritcat14.GotYourSix.graphics.layers.UILayer;

public class UIManager extends UILayer {

    private List<UIPanel> panels = new ArrayList<UIPanel>();

    public UIManager() {

    }

    public void onEvent(Event event) {
      for (UIPanel panel : panels) {
        panel.onEvent(event);
      }
    }

    public void addPanel(UIPanel panel) {
        panels.add(panel);
    }
  
    public void removePanel(UIPanel panel){
        panels.remove(panel);
    }
  
    public List<UIPanel> getPanels(){
        return panels;
    }

    public void update() {
        for (UIPanel panel : panels) {
            panel.update();
        }
    }

    public void render(Graphics g) {
        for (UIPanel panel : panels) {
            panel.render(g);
        }
    }

}
