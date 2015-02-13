package com.ok.attiribute;

public class FieldInformation {


	private String DateInformation =null;
	private Integer  Humidity =null;
	private Integer temperature=null;
	public String getDateInformation() {
		return DateInformation;
	}
	public void setDateInformation(String DateInformation) {
		this.DateInformation = DateInformation;
	}
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
	
}
