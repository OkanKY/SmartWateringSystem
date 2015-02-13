package com.ok.activity;

import java.util.concurrent.ExecutionException;

import org.ksoap2.serialization.SoapObject;

import com.ok.androidclient.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ok.attribute.User;
import com.ok.client.MyClientClass;



public class MainLoginActivity extends Activity {

	 private EditText txtUserName;
	 private EditText txtPassword;
	 private Button btnLogin,btnexit;
	 private static final String SOAP_ACTION = "http://farm.ok.com/FarmServer/getUserListRequest";
	 private static final String METHOD_NAME = "getUserList";
	 private SoapObject soapObject;
	 private User user;
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       txtUserName=(EditText)this.findViewById(R.id.uname);
       txtPassword=(EditText)this.findViewById(R.id.pwd);
       btnLogin=(Button)this.findViewById(R.id.login);
       btnexit=(Button)this.findViewById(R.id.exit);
       btnexit.setOnClickListener(new OnClickListener() {
       	  @Override
       	  public void onClick(View v) {
       			finish();
       	  }
       	  });
       btnLogin.setOnClickListener(new OnClickListener() {
  
  @Override
  public void onClick(View v) {
   // TODO Auto-generated method stub
	user=new User();
	user.setUserName(txtUserName.getText().toString());
	user.setPassword(txtPassword.getText().toString());
	MyClientClass myclass =
	new MyClientClass(txtUserName.getText().toString(),txtPassword.getText().toString(),SOAP_ACTION,METHOD_NAME);
	try {
		soapObject= myclass.execute("").get();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(soapObject.getProperty(0).toString().equals("true"))
	{
		Intent intent = new Intent(MainLoginActivity.this,FieldListActivity.class);
		intent.putExtra("UserName",txtUserName.getText().toString());
		intent.putExtra("Password",txtPassword.getText().toString());
		startActivity(intent);
		finish();

	}
	else
		Toast.makeText(MainLoginActivity.this, "Invalid Login",Toast.LENGTH_LONG).show();
	
	System.out.println("Result is : " + soapObject.getProperty(0).toString());
   
  }
 });       
   }
	
}

