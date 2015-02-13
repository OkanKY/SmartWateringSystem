package com.ok.activity;

import java.util.concurrent.ExecutionException;

import org.ksoap2.serialization.SoapObject;

import com.ok.androidclient.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ok.attribute.UserField;
import com.ok.client.MyClientClass;





public class FieldListActivity extends Activity implements OnClickListener {
	private static final String SOAP_ACTION = "http://farm.ok.com/FarmServer/getUserFieldListRequest";
	private static final String METHOD_NAME = "getUserFieldList";
	Button reflesh,exit;
 	private SoapObject soapObject;
 	private UserField[] userFieldList;
 	private ArrayAdapter<UserField> adapter;
 	private ListView listView1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.fieldlist);
	    
	    listView1 = (ListView) findViewById(R.id.listView1);
	    listView1.setOnItemClickListener(new OnItemClickListener() {
	    	  @Override
	    	  public void onItemClick(AdapterView<?> parent, View view,
	    	    int position, long id) {

	    			Intent intent = new Intent(FieldListActivity.this,FieldInformationActiviyt.class);
	    			intent.putExtra("FieldID",userFieldList[position].getFieldID());
	    			intent.putExtra("UserName",getIntent().getStringExtra("UserName").toString());
	    			intent.putExtra("Password",getIntent().getStringExtra("Password").toString());
	    			startActivity(intent);
	    			finish();
	    	
	    	  }
	    	});
	    reflesh = (Button) findViewById(R.id.reflesh);
	    exit=(Button)this.findViewById(R.id.exitfield);
	    reflesh.setOnClickListener(this);
	    exit.setOnClickListener(this);
	    SoapManager();
	    
	}

	@Override
	public void onClick(View v) {
	    if (v.getId() == R.id.reflesh) {
	    	SoapManager();
	    	}
	    if (v.getId() == R.id.exitfield) {
	    	finish();
	    	}
	}
	private void SoapManager()
	{
		MyClientClass myclass =
		           new MyClientClass(getIntent().getStringExtra("UserName").toString(),getIntent().getStringExtra("Password").toString(),SOAP_ACTION,METHOD_NAME);
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
			    	 userFieldList=RetrieveFromSoap(soapObject);
			    	 RefreshAdapter(userFieldList);
			    	 /*for (int i = 0; i < userFieldList.length; i++) {
			            	System.out.println(" bu catagory" + userFieldList[i].getFieldName());
			 			}*/
			    	}
	}
	
	private void RefreshAdapter(UserField[] items)
	{
		adapter = new ArrayAdapter<UserField>(this,
                android.R.layout.simple_list_item_1, items);
		listView1.setAdapter(adapter);
		
	}
    /**
     * 
     * @param soap - represents the entering Soap object
     * @return 
     * @return returns the list of categories extracted from the response
     */
    public static UserField[] RetrieveFromSoap(SoapObject soap)
    {
    	UserField[] categories = new UserField[soap.getPropertyCount()];
        for (int i = 0; i < categories.length; i++) {
            SoapObject pii = (SoapObject)soap.getProperty(i);
            UserField category = new UserField();
            category.setFieldID(Integer.parseInt(pii.getProperty(0).toString()));
            category.setFieldName( pii.getProperty(1).toString());

            categories[i] = category;
        }
       
        return categories;
    }
    

	
}
