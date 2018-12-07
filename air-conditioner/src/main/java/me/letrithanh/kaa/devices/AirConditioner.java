package me.letrithanh.kaa.devices;

public class AirConditioner {

	private boolean isOn;
	private int temperature;
	
	public AirConditioner() {
		setOn(false);
		setTemperature(30);
	}
	
	public boolean isOn() {
		return isOn;
	}
	
	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}
	
	public int getTemperature() {
		return temperature;
	}
	
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	
	@Override
	public String toString() {
		if(isOn) {
			return "Air Conditioner is turned on with " + getTemperature() + " C degree"; 
		}
		
		return "Air Conditioner is turned off with default 30 C degree";
	}
	
}
