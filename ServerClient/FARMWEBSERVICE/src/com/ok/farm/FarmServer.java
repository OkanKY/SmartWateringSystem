package com.ok.farm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Endpoint;

import com.ok.attiribute.FieldInformation;
import com.ok.attiribute.Information;
import com.ok.attiribute.UserField;
import com.ok.farm.client.BindingPcClient;
import com.ok.farm.client.WateringPcClient;
import com.ok.manager.FarmManager;
import com.ok.manager.FarmManager.DatabaseType;


@WebService
public class FarmServer {
	private String METHOD_NAME_CURRENTIN = "getInformation";
	private String METHOD_NAME_WATERING = "setWatering";
	public static void main(String[] args) {
		
		Endpoint.publish("http://192.168.2.42:8500/FieldServer", new FarmServer());

		System.out.println("Sunucu baslatildi ve komutlari bekliyor.");

	}
	private String getUrl(Integer FieldID) throws SQLException
	{
		ResultSet resultSet = null;
		FarmManager farmManager=getFarmManager();
		resultSet = farmManager.query("SELECT F.FieldUrl FROM FIELD AS F WHERE F.FieldID='"+FieldID+"';");
		resultSet.next();
		return resultSet.getString("FieldUrl").toString();
		 
	}
	private FarmManager getFarmManager()
	{
		FarmManager farmManager = null;
		farmManager = new FarmManager();
		farmManager.setDatabaseType(DatabaseType.MicrosoftSQL);
		farmManager.setServer("localhost");
		farmManager.setPointConnection("1433");
		farmManager.setDatabase("FIELD");
		farmManager.setUser("field_application");
		farmManager.setPassword("parola");
		
		return farmManager;
	}
	private void insertdFieldInformation(Integer FieldID,Integer Humidity,Integer Temperature )
	{
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		FarmManager farmManager=getFarmManager();
		try {
			 
			farmManager.query("INSERT INTO FIELDINFORMATION (FieldID, DateInformation, humidity, temperature)"
			+"VALUES ("+FieldID+",'"+dateFormat.format(date)+"',"+Humidity+","+Temperature+");");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	@WebMethod
	public Boolean setWatering(@WebParam(name = "FieldID") Integer FieldID,@WebParam(name = "Time") Integer Time)
	{
        Boolean result =false;
        String url=null;
		try {
			url = getUrl(FieldID);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        WateringPcClient binding =new WateringPcClient(url,METHOD_NAME_WATERING,Time);
		try {
			    SOAPMessage soapResponse=binding.ManagerBinding();
			    
			    Iterator iterator =soapResponse.getSOAPBody().getChildElements();
		        SOAPElement element = (SOAPElement)iterator.next();
		        Iterator iterator2 = element.getChildElements();
		        SOAPElement element2 = (SOAPElement)iterator2.next();
		        result=element2.getChildNodes().item(0).getTextContent().equals("true");
		       
			//System.out.println(" donen veri"+binding.ManagerBinding());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
	@WebMethod
	public Information getCurrentInformation(@WebParam(name = "FieldID") Integer FieldID )
	{
		Information result =null;
		String url=null;
		try {
			url = getUrl(FieldID);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BindingPcClient binding =new BindingPcClient(url,METHOD_NAME_CURRENTIN);
		try {
			    SOAPMessage soapResponse=binding.ManagerBinding();
			    result= new Information();
			    Iterator iterator =soapResponse.getSOAPBody().getChildElements();
		        SOAPElement element = (SOAPElement)iterator.next();
		        Iterator iterator2 = element.getChildElements();
		        SOAPElement element2 = (SOAPElement)iterator2.next();
		        result.setHumidity(Integer.parseInt(element2.getChildNodes().item(0).getTextContent()));
		        result.setTemperature(Integer.parseInt(element2.getChildNodes().item(1).getTextContent()));
			    System.out.println(" dogrumu"+result.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		insertdFieldInformation(FieldID,result.getHumidity(),result.getTemperature());
		return result;
	}
	

    @WebMethod
    public List<FieldInformation> getFieldInformationList(@WebParam(name = "FieldID") Integer FieldID ) {
		
		ResultSet resultSet = null;
		FarmManager farmManager = null;
		List<FieldInformation> fieldList = null;

		try {

			fieldList = new ArrayList<FieldInformation>();
			farmManager=getFarmManager();
			
			resultSet = 
farmManager.query(" SELECT FI.DateInformation,FI.humidity,FI.temperature FROM FIELDINFORMATION AS FI WHERE FI.FieldID='"+FieldID+"'; ");
			while (resultSet.next()) {

				FieldInformation field = null;
		
				field = new FieldInformation();
				field.setDateInformation(resultSet.getString("DateInformation"));
				field.setHumidity(resultSet.getInt("humidity"));
				field.setTemperature(resultSet.getInt("temperature"));
				
				
				fieldList.add(field);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return fieldList;

	}
	
	@WebMethod
	public List<UserField> getUserFieldList(@WebParam(name = "UserName") String UserName,@WebParam(name="Password")String Password ) {
		
		ResultSet resultSet = null;
		FarmManager farmManager = null;
		List<UserField> fieldList = null;

		try {

			fieldList = new ArrayList<UserField>();
			
			farmManager=getFarmManager();
			
			resultSet = 
		    farmManager.query("SELECT F.FieldName,F.FieldID FROM FIELD AS F WHERE F.FieldID IN "
	                         +" (SELECT L.FieldID FROM LOGINFIELD AS L  WHERE L.UserName IN "
					         +" ( SELECT U.UserName FROM USERLOGIN AS U WHERE U.UserName ='"+UserName+"' AND U.Password='"+Password+"' AND U.Status=1) ); ");
			while (resultSet.next()) {

				UserField field = null;
		
				field = new UserField();
				field.setFieldName(resultSet.getString("FieldName"));
				field.setFieldID(resultSet.getInt("FieldID"));
			
				fieldList.add(field);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return fieldList;

	}
	@WebMethod
	public Boolean getUserList(@WebParam(name = "UserName") String UserName,@WebParam(name="Password")String Password ) {
		ResultSet resultSet = null;
		FarmManager farmManager = null;
        Boolean result =false;
		try {

			
			farmManager=getFarmManager();
			
			resultSet = 
		    farmManager.query("SELECT U.UserName FROM USERLOGIN AS U WHERE U.UserName ='"+UserName+"' AND U.Password='"+Password+"' AND U.Status=1; ");
		    result =resultSet.next();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return result;
	}
	
	
	

}
