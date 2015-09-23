package com.ritcat14.GotYourSix.graphics.UI;

public class UIButtonListener {

    public void entered(UIButton button){
        button.setColor(0xCDCDCD);
    }
    public void exited(UIButton button){
        button.setColor(0xAAAAAA);
    }

    public void pressed(UIButton button){
        button.setColor(0xcc2222);
    }
    public void released(UIButton button){
        button.setColor(0xAAAAAA);
    }

}
