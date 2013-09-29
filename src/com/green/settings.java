package com.green;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class settings extends Activity {

	public EditText names;
	public EditText ph_num;
	DBAdapter db;
	
	
	public void onCreate(Bundle savedInstanceState) {
        
		super.onCreate(savedInstanceState);
        setContentView(R.layout.instn);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
       final Spinner spinner1 = (Spinner)findViewById(R.id.spinner1);
       
       final Button btnSave=(Button)findViewById(R.id.btnSaveContacts);

        names=(EditText)findViewById(R.id.edit_name);
       ph_num = (EditText)findViewById(R.id.edit_ph_num);
       
    
       ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.contactType,android.R.layout.simple_dropdown_item_1line);
        spinner1.setAdapter(adapter);
	
      
btnSave.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			
				// TODO Auto-generated method stub
				String name1,num;
				name1 = names.getText().toString();
				num = ph_num.getText().toString();
				if((!name1.equalsIgnoreCase(""))&&(!num.equalsIgnoreCase(""))){
					
				if(spinner1.getSelectedItem().toString().equalsIgnoreCase("help me"))
				{
					if((checkname(name1, "H") == true) && (checkphone(num, "H")==true))
						{insert(num,name1,"H");}
					
				}
				else if(spinner1.getSelectedItem().toString().equalsIgnoreCase("Cleaning"))
				{
					if((checkname(name1, "C") == true) && (checkphone(num, "C")==true)){
				insert(num,name1,"C");}
				}
				else if(spinner1.getSelectedItem().toString().equalsIgnoreCase("Fire_Force"))
				{
					if((checkname(name1, "F") == true) && (checkphone(num, "F")==true)){
						insert(num,name1,"F");}
				}
				else if(spinner1.getSelectedItem().toString().equalsIgnoreCase("Hospital/Ambulance"))
				{
					if((checkname(name1, "A") == true) && (checkphone(num, "A")==true)){
					insert(num,name1,"A");}
				}
				
				
				}else{
					Toast.makeText(v.getContext(),"Fill all fields..",
			               500).show();
						
						
				}
				
				
			}
		});
		
        
	}

       
       
	
public boolean checkname(String name,String mode)
{
Cursor c1;
    db = new DBAdapter(this);
try {
	db.open();


c1=db.checkName(mode, name);
if(c1.getCount() > 0)
{
	c1.moveToFirst();
	Toast.makeText(this, "Name already exist for : \n"+ c1.getString(0), 500).show();
	c1.close();
	db.close();
	return false;
}
else
{
	
	c1.close();
	db.close();
	return true;
}


} catch (java.sql.SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	return true;
}
public boolean checkphone(String phone,String mode)
{
Cursor c1;
    db = new DBAdapter(this);
try {
	db.open();


c1=db.checkPhone(mode, phone);
if(c1.getCount() > 0)
{
	c1.moveToFirst();
	Toast.makeText(this, "No already exist for : \n"+ c1.getString(0), 500).show();
	c1.close();
	db.close();
	return false;
}
else
{
	
	c1.close();
	db.close();
	return true;
}


} catch (java.sql.SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	return true;
}
	private void insert(String phone,String name,String mode){ 
     	

            db = new DBAdapter(this);
        try {
			db.open();
		
		
        db.insertTitle(phone,name,mode);
       
        db.close();
        } catch (java.sql.SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
        Toast.makeText(this,"Contact Saved.",500).show();
        names.setText("");
		ph_num.setText("");
        }
	
	 

		
}
