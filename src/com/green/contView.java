package com.green;

import java.sql.SQLException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class contView extends Activity{
	
	DBAdapter  db=new DBAdapter(this);
	public static String phone="";
	Button btnEdit;
	Button btnDelete;
	TextView txtType;
	TextView txtName;
	TextView txtPhone;
	Cursor con;
	public void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contview);
		btnEdit=(Button)findViewById(R.id.btnEdit);
		btnDelete=(Button)findViewById(R.id.btnDelete);
		txtType=(TextView)findViewById(R.id.txtType);
		txtName=(TextView)findViewById(R.id.txtName);
		txtPhone=(TextView)findViewById(R.id.txtPhone);
		
		
		
		if(contactList.type.equalsIgnoreCase("H"))
			txtType.setText("Help Me Contacts ");
		else if(contactList.type.equalsIgnoreCase("C"))
			txtType.setText("Cleaning Contacts ");
		else if(contactList.type.equalsIgnoreCase("F"))
			txtType.setText("Fire_Force Contacts ");
		else if(contactList.type.equalsIgnoreCase("A"))
			txtType.setText("Hospital/Ambulance Contacts" );
		
		txtName.setText(contactList.contname);
		
		try {
		
			db.open();
		
		con=db.findContact(contactList.contname,contactList.type);
		con.moveToFirst();
		phone=con.getString(0);
		con.close();
		db.close();
		
		txtPhone.setText(phone);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		btnEdit.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent myInt = new Intent(v.getContext(),contactEdit.class);	
				startActivityForResult(myInt, 0);
			}
		});
		btnDelete.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					db.open();
				
				db.deleteContact(contactList.contname,contactList.type);
				db.close();
				
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Toast.makeText(v.getContext(), contactList.contname + "  Deleted", 500).show();
			
				
				Intent myInt = new Intent(v.getContext(),contactList.class);
				
				startActivityForResult(myInt, 0);
			}
		});
		
	
		
		
		
	}
	public void onResume()
	{

		super.onResume();
		btnEdit=(Button)findViewById(R.id.btnEdit);
		btnDelete=(Button)findViewById(R.id.btnDelete);
		txtType=(TextView)findViewById(R.id.txtType);
		txtName=(TextView)findViewById(R.id.txtName);
		txtPhone=(TextView)findViewById(R.id.txtPhone);
		if(contactList.type.equalsIgnoreCase("H"))
			txtType.setText("Help Me Contacts ");
		else if(contactList.type.equalsIgnoreCase("C"))
			txtType.setText("Cleaning Contacts ");
		else if(contactList.type.equalsIgnoreCase("F"))
			txtType.setText("Fire_Force Contacts ");
		else if(contactList.type.equalsIgnoreCase("A"))
			txtType.setText("Hospital/Ambulance Contacts" );
		
		txtName.setText(contactList.contname);
		
		try {
		
			db.open();
			
			con=db.findContact(contactList.contname,contactList.type);
			con.moveToFirst();
			phone=con.getString(0);
			con.close();
			db.close();
		
		txtPhone.setText(phone);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void onStop()
	{
		super.onStop();
		finish();
		
	}
}
