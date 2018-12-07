package me.letrithanh.kaa.devices;

public class Fan {

	private boolean isOn;
	
	public Fan() {
		setOn(false);
	}

	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}
	
	@Override
	public String toString() {
		if(isOn) {
			return "Fan is turned on";
		}
		return "Fan is turned off";
	}
	
}
