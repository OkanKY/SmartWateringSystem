package com.ok.attribute;

public class Information {
	private Integer  Humidity =null;
	private Integer temperature=null;
	public Integer getHumidity() {
		return Humidity;
	}
	public void setHumidity(Integer humidity) {
		Humidity = humidity;
	}
	public Integer getTemperature() {
		return temperature;
	}
	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}
	@Override
	public String toString() {
        return "Humidity "+this.Humidity + " temperature"+this.temperature;
 }
}
