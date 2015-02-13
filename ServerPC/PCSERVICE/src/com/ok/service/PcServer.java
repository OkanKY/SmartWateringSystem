package com.ok.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import com.ok.attiribute.Information;

@WebService
public class PcServer {
	
	public static void main(String[] args) {
		
		Endpoint.publish("http://192.168.2.42:8050/PcServer", new PcServer());
	//Endpoint.publish("http://localhost:8500/ArabaWS", new ArabaSunucusu());
		System.out.println("Sunucu baslatildi ve komutlari bekliyor.");

	}
	@WebMethod
	public Boolean setWatering(@WebParam(name = "Time") Integer Time)
	{
		
		return true;
	}
	@WebMethod
	public Information getInformation()
	{
		Information information=new Information();
		information.setHumidity(11);
		information.setTemperature(22);
		return information;
	}
}
