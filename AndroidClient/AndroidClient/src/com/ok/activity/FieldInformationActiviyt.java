package com.ok.activity;

import java.util.concurrent.ExecutionException;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.ok.attribute.FieldInformation;
import com.ok.client.InformationClient;
import com.ok.androidclient.R;;

public class FieldInformationActiviyt extends Activity {
   private GridView gridView;
   private Button mainMenu,information;
   private ArrayAdapter adapter;
   private SoapObject soapObject;
   private FieldInformation[] fieldInformationList;
   private static final String SOAP_ACTION = "http://farm.ok.com/FarmServer/getFieldInformationListRequest";
   private static final String METHOD_NAME = "getFieldInformationList";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_information);
		 mainMenu = (Button) findViewById(R.id.mainMenu);
		 information = (Button) findViewById(R.id.information);
		 gridView = (GridView) findViewById(R.id.gridview); 
		 SoapManager();
         mainMenu.setOnClickListener(new OnClickListener() {
        	  @Override
        	  public void onClick(View v) {
        		  
        			Intent intent = new Intent(FieldInformationActiviyt.this,FieldListActivity.class);
          			intent.putExtra("UserName",getIntent().getStringExtra("UserName").toString());
     			    intent.putExtra("Password",getIntent().getStringExtra("Password").toString());
          			startActivity(intent);
          			finish();
        	  }
        	  });
         information.setOnClickListener(new OnClickListener() {
       	  @Override
       	  public void onClick(View v) {
       		    Intent intent = new Intent(FieldInformationActiviyt.this,WateringActivity.class);
       		    intent.putExtra("FieldID",getIntent().getIntExtra("FieldID",1));
       		    intent.putExtra("UserName",getIntent().getStringExtra("UserName").toString());
 			    intent.putExtra("Password",getIntent().getStringExtra("Password").toString());
       			startActivity(intent);
       			finish();
       	  }
       	  });
	}
	private void UpdateAdapter(FieldInformation[] items)
	{
		adapter = new ArrayAdapter<FieldInformation>(this,
                android.R.layout.simple_list_item_1, items);
		gridView.setAdapter(adapter);
		
		
	}
	private void SoapManager()
	{  System.out.println("bakalým " +getIntent().getIntExtra("FieldID",0));
		
		InformationClient myclass =
		           new InformationClient(getIntent().getIntExtra("FieldID",1),SOAP_ACTION,METHOD_NAME);
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
			    		System.out.println(" asdasd");
			    		fieldInformationList=RetrieveFromSoap(soapObject);
			    		UpdateAdapter(fieldInformationList);
			    	for (int i = 0; i < fieldInformationList.length; i++) {
			            	System.out.println(" bu catagory" + fieldInformationList[i].getDateInformation());
			 			}
			    	}
	}
    /**
     * 
     * @param soap - represents the entering Soap object
     * @return 
     * @return returns the list of categories extracted from the response
     */
    public static FieldInformation[] RetrieveFromSoap(SoapObject soap)
    {
    	FieldInformation[] categories = new FieldInformation[soap.getPropertyCount()];
        for (int i = 0; i < categories.length; i++) {
            SoapObject pii = (SoapObject)soap.getProperty(i);
            FieldInformation category = new FieldInformation();
            category.setDateInformation(pii.getProperty(0).toString());
            category.setHumidity(Integer.parseInt(pii.getProperty(1).toString()));
            category.setTemperature(Integer.parseInt(pii.getProperty(2).toString()));

            categories[i] = category;
        }
        return categories;
    }
    
}
