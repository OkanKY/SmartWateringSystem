package com.ok.client;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;

public class WateringClient extends AsyncTask<String, Void, SoapObject> {
	private SoapObject result;
	private static  String SOAP_ACTION;
	private static  String METHOD_NAME;
	private static final String NAMESPACE = "http://farm.ok.com/";
	private static final String URL = "http://192.168.2.42:8500/ArabaWS";
 	private SoapSerializationEnvelope envelope ;
 	private Integer FieldID;
 	private Integer Time;
 	public WateringClient(Integer FieldID,Integer Time,String SOAP_ACTION,String METHOD_NAME )
 	{
 		this.FieldID=FieldID;
 		this.Time=Time;
 		this.SOAP_ACTION=SOAP_ACTION;
 		this.METHOD_NAME=METHOD_NAME;
 		
 	}
    @SuppressWarnings({ })
	@Override
    protected SoapObject doInBackground(String... params) {
        try {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("FieldID",FieldID );
            request.addProperty("Time",Time );
            envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            try {
                transport.call(SOAP_ACTION, envelope);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            
               result = (SoapObject) envelope.bodyIn;
     
            //System.out.println("Result is : " + result.toString());
        
        } catch (Exception e) {
            System.out.println("Exception : "+e.toString());
   
        }

        return result;
    }
}
