package com.green;

import java.sql.SQLException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class contactEdit extends Activity{
	
	 EditText txtName;
	 EditText txtPhone;
	 
	 
	 DBAdapter db = new DBAdapter(this);
	 Button btnSave;
		//Button btnBack=(Button)findViewById(R.id.b);
		TextView txtType;
	public void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contedit);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		btnSave=(Button)findViewById(R.id.btnSave);
		txtType=(TextView)findViewById(R.id.txtType);
		
		
		
		 txtName=(EditText)findViewById(R.id.edtName);
		 txtPhone=(EditText)findViewById(R.id.edtPhone);
		
		txtName.setText(contactList.contname);
		txtPhone.setText(contView.phone);
		
		if(contactList.type.equalsIgnoreCase("H"))
			txtType.setText("Help Me Contacts ");
		else if(contactList.type.equalsIgnoreCase("C"))
			txtType.setText("Cleaning Contacts ");
		else if(contactList.type.equalsIgnoreCase("F"))
			txtType.setText("Fire_Force Contacts ");
		else if(contactList.type.equalsIgnoreCase("A"))
			txtType.setText("Hospital/Ambulance Contacts" );
		
		txtName.setText(contactList.contname);
		
		
btnSave.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
					if((!txtName.getText().toString().equalsIgnoreCase(""))&&(!txtPhone.getText().toString().equalsIgnoreCase(""))){
					
					
					try {
						db.open();
						db.updateContact(txtName.getText().toString().trim(),contactList.contname,txtPhone.getText().toString().trim(),contView.phone.trim(),contactList.type.trim());
					Toast.makeText(v.getContext(), "Saved successfully", 500).show();

					Intent myInt = new Intent(v.getContext(),contactList.class);	
					startActivityForResult(myInt, 0);
						
					db.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
				}
				else
				{
					Toast.makeText(v.getContext(), "Fields Cannot be null", 500).show();
					
				}
				
			}
		});
		
		//*/
	}
	
	public void onResume()
	{
		super.onResume();
		btnSave=(Button)findViewById(R.id.btnSave);
		txtType=(TextView)findViewById(R.id.txtType);
		
		
		
		 txtName=(EditText)findViewById(R.id.edtName);
		 txtPhone=(EditText)findViewById(R.id.edtPhone);
		
		txtName.setText(contactList.contname);
		txtPhone.setText(contView.phone);
		if(contactList.type.equalsIgnoreCase("H"))
			txtType.setText("Help Me Contacts ");
		else if(contactList.type.equalsIgnoreCase("C"))
			txtType.setText("Cleaning Contacts ");
		else if(contactList.type.equalsIgnoreCase("F"))
			txtType.setText("Fire_Force Contacts ");
		else if(contactList.type.equalsIgnoreCase("A"))
			txtType.setText("Hospital/Ambulance Contacts" );
		
		txtName.setText(contactList.contname);
		
		
	}
	public void onStop()
	{
		finish();
		super.onStop();
	}
}
