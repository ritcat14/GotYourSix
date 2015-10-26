package com.ritcat14.GotYourSix.events.types;

import com.ritcat14.GotYourSix.events.Event;
import com.ritcat14.GotYourSix.events.types.MouseButtonEvent;

public class MouseReleasedEvent extends MouseButtonEvent {

	public MouseReleasedEvent(int button, int x, int y) {
		super(button, x, y, Event.Type.MOUSE_RELEASED);
	}
	
}
