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
      for (int i = panels.size() - 1; i > -1; i--) {
        panels.get(i).onEvent(event);
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
        for (int i = panels.size() - 1; i > -1; i--) {
            panels.get(i).update();
        }
    }

    public void render(Graphics g) {
        for (int i = panels.size() - 1; i > -1; i--) {
            panels.get(i).render(g);
        }
    }

}
