package me.letrithanh.kaa.devices;

public class Light {

	private boolean isOn;
	
	public Light() {
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
			return "Light is turned on";
		}
		return "Light is turned off";
	}
}
