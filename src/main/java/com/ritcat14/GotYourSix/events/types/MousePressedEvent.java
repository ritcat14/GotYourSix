package com.ritcat14.GotYourSix.events.types;

import com.ritcat14.GotYourSix.events.Event;

public class MousePressedEvent extends MouseButtonEvent {

	public MousePressedEvent(int button, int x, int y) {
		super(button, x, y, Event.Type.MOUSE_PRESSED);
	}

}
