package com.ritcat14.GotYourSix.events;

public class Event {
	
	public enum Type {
		MOUSE_PRESSED,
		MOUSE_RELEASED,
		MOUSE_MOVED,
      KEY_PRESSED,
      KEY_RELEASED
	}
	
	private Type type = null;
	boolean handled = false;
	
	protected Event(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}

}
