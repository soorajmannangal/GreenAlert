package com.green;



import java.sql.SQLException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;


public class contactList extends Activity implements
AdapterView.OnItemClickListener, OnItemSelectedListener{
	
	GridView contList; 
	
	int[] names = new int[]{R.id.txtName};
	String[] cols = new String[]{"contname"};
	TextView tv;
	Cursor c;
	 
	 public static String contname,type="H";
	 Spinner spnType;
	 ArrayAdapter<CharSequence> adapter ;
	public void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
        
		setContentView(R.layout.contactlist);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		tv=(TextView)findViewById(R.id.textView1);
		
		contList =(GridView)findViewById(R.id.gridContacts);
		
		spnType=(Spinner)findViewById(R.id.spinType);
		
		
		contList.setOnItemClickListener(this);
		
		adapter = ArrayAdapter.createFromResource(this, R.array.contactType,android.R.layout.simple_dropdown_item_1line);
		 spnType.setAdapter(adapter);
		 
		 spnType.setOnItemSelectedListener(this);
		 fillGrid();
		
		
	}
	public void fillGrid()
    {
		
		
		 DBAdapter db = new DBAdapter(this);
        try {
			db.open();
        c = db.getallname(type);
        startManagingCursor(c);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.contlayout ,c,cols,names);
        contList.setAdapter(adapter);
        //c.close();
        db.close();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	
	public void onResume()
	{
		super.onResume();
		 fillGrid();
	}
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		
		c.moveToFirst();
		c.moveToPosition(position);
		
		contname=c.getString(1);
		
		
		Intent myInt = new Intent(v.getContext(),contView.class);
		
		startActivityForResult(myInt, 0);
		 
		 
	}
	public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
		
		if(parent.getItemAtPosition(pos).toString().equalsIgnoreCase("help me"))
			type="H";
			else if(parent.getItemAtPosition(pos).toString().equalsIgnoreCase("Cleaning"))
				type="C";
			else if(parent.getItemAtPosition(pos).toString().equalsIgnoreCase("Fire_Force"))
				type="F";
			else if(parent.getItemAtPosition(pos).toString().equalsIgnoreCase("Hospital/Ambulance"))
				type="A";
		 fillGrid();
		
		
	  }
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	public void onStop()
	{super.onStop();
		c.close();
	}
	

}
