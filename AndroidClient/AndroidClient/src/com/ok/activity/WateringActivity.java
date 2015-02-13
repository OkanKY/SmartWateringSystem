package com.ok.activity;

import java.util.concurrent.ExecutionException;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ok.attribute.Information;
import com.ok.client.InformationClient;
import com.ok.client.WateringClient;
import com.ok.androidclient.R;

public class WateringActivity extends Activity {
  
	private Button mainMenuButton,wateringButton;
	private SoapObject soapObject;
	private TextView textInformationhty,textInformationtete,textWatering;
	private EditText editTextTime;
	private static final String SOAP_ACTION_INFORMATION = "http://farm.ok.com/FarmServer/getCurrentInformationRequest";
	private static final String METHOD_NAME_INFORMATION = "getCurrentInformation";
	private static final String SOAP_ACTION_WATERING = "http://farm.ok.com/FarmServer/setWateringRequest";
	private static final String METHOD_NAME_WATERING = "setWatering";
	private Information information;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.watering);
		
		
		textInformationhty=(TextView)findViewById(R.id.inhty);
		textInformationtete=(TextView)findViewById(R.id.intete);
		textWatering=(TextView)findViewById(R.id.textWatering);	
		editTextTime=(EditText)findViewById(R.id.editTextTime);	
		mainMenuButton = (Button) findViewById(R.id.mainMenuu);
		wateringButton = (Button) findViewById(R.id.watering);
		InformationSoapManager();
     	mainMenuButton.setOnClickListener(new OnClickListener() {
      	  @Override
      	  public void onClick(View v) {
      		   
      			Intent intent = new Intent(WateringActivity.this,FieldListActivity.class);
      			intent.putExtra("UserName",getIntent().getStringExtra("UserName").toString());
 			    intent.putExtra("Password",getIntent().getStringExtra("Password").toString());
      			startActivity(intent);
      			finish();
      	  }
      	  });
     	wateringButton.setOnClickListener(new OnClickListener() {
        	  @Override
        	  public void onClick(View v) {
        		 WateringSoapManager();
        	  }
        	  });
	}
	private void WateringSoapManager()
	{  
		
		WateringClient myclass =
		           new WateringClient(getIntent().getIntExtra("FieldID",1),Integer.parseInt(editTextTime.getText().toString()),SOAP_ACTION_WATERING,METHOD_NAME_WATERING);
			    	try {
						soapObject= myclass.execute("").get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	if(soapObject==null)
			    	{
			    		System.out.println("bos");
			    	}
			    	else
			    	{		
			    		UpdateWateriýnText(soapObject.getProperty(0).toString().equals("true"));
			    	}
	}
	
	private void InformationSoapManager()
	{  
		System.out.println("bakalým " +getIntent().getIntExtra("FieldID",0));
		
		InformationClient myclass =
		           new InformationClient(getIntent().getIntExtra("FieldID",1),SOAP_ACTION_INFORMATION,METHOD_NAME_INFORMATION);
			    	try {
						soapObject= myclass.execute("").get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	if(soapObject==null)
			    	{
			    		System.out.println("bos");
			    	}
			    	else
			    	{	
			    		
			    		information=RetrieveFromSoap(soapObject);
			    		UpdateInformationText(information);
			    
			    	}
	}
	  /**
     * 
     * @param soap - represents the entering Soap object
     * @return 
     * @return returns the list of categories extracted from the response
     */
    public static Information RetrieveFromSoap(SoapObject soap)
    {
    	
    	    SoapObject pii = (SoapObject)soap.getProperty(0);
            Information category = new Information();
            System.out.println("acaba veri geldimisad");
            category.setHumidity(Integer.parseInt(pii.getProperty(0).toString()));
            category.setTemperature(Integer.parseInt(pii.getProperty(1).toString()));

        return category;
    }
    private void UpdateWateriýnText(Boolean response)
	{
	   if(response)
		   textWatering.setText("process sugsess");
	   else
		   textWatering.setText("process fail");
	}
	private void UpdateInformationText(Information item)
	{
	   
		textInformationhty.setText("Humidity Information  "+item.getHumidity());
		textInformationtete.setText("Temperature Informatin"+item.getTemperature());
		
	}

}
