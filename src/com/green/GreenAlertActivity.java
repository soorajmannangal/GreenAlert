package com.green;

import java.sql.SQLException;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GreenAlertActivity extends Activity implements LocationListener {
    /** Called when the activity is first created. */
	static String usrname="";
	static final String msgfire="Pls help, here is a danger..\n";
	static final String msgpolice="I'm in danger pls help..\n";
	static final String msgambulance="Please hlp me iam in medical emergency..\n";
	static final String msgclean="Pls clean here..\n";
	String msgsend;
	static final String tag = "Main"; // for Log
	LocationManager lm;
	StringBuilder sb;
	int noOfFixes = 0;
	String SendTo = "9037223519";
	int i=1;
	String loc="default";
	TextView textView1;
	View v1;
	String typemod;
	//SmsManager 
	android.telephony.SmsManager smsmgr;
	DBAdapter db;
	final Animation a1 = new AlphaAnimation(1, 0);
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
		super.onCreateContextMenu(menu, v, menuInfo);  
		    menu.setHeaderTitle("Settings");  
		    menu.add(0, v.getId(), 0, "Display Contacts");  
		    menu.add(0, v.getId(), 0, "Add New Contacts");  
		    menu.add(0, v.getId(), 0, "About GreenAlert");  
		}  
	@Override
	public boolean onContextItemSelected(MenuItem item) {  
        if(item.getTitle()=="Display Contacts"){

			Intent myInt = new Intent(this,contactList.class);
			startActivityForResult(myInt, 0);
        	}  
    else if(item.getTitle()=="Add New Contacts"){
		Intent myInt = new Intent(this,settings.class);
		startActivityForResult(myInt, 0);}  
    else if(item.getTitle()=="About GreenAlert"){
		Intent myInt = new Intent(this,about.class);
		startActivityForResult(myInt, 0);}  
    else {return false;}  
return true;  
}  
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           setContentView(R.layout.hc);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
       
        v1 = this.getCurrentFocus();
     
       a1.setDuration(150); // duration - half a second
       a1.setInterpolator(new LinearInterpolator()); // do not alter animation rate
       a1.setRepeatCount(1); // Repeat animation infinitely
       a1.setRepeatMode(Animation.REVERSE); 
       
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        loc="Default";
        db = new DBAdapter(this);
        
        //----------read user name
        try {
			db.open();
		
		Cursor c = db.fetchuserName();
		c.moveToFirst();
		if(c.getCount() >0)
		{ 
			
			typemod=c.getString(0).toString();
		}
		
		db.close();
		} catch (SQLException e) {
			
		}
        //------------end read username
      
        textView1 = (TextView)findViewById(R.id.textView1);
        
        
        final ImageView settings = (ImageView)findViewById(R.id.imageView_settngs);
        
        textView1.setText("Even one SMS can do something ..");
        
        final ImageView button2 = (ImageView)findViewById(R.id.image_help);
        final ImageView button3 = (ImageView)findViewById(R.id.image_clean);
        final ImageView ambulance = (ImageView)findViewById(R.id.image_hospital);
        final ImageView fire = (ImageView)findViewById(R.id.image_fire);
    registerForContextMenu(settings);
        
        settings.setOnClickListener(new View.OnClickListener() {
			
			//@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				settings.startAnimation(a1);
				settings.showContextMenu();
				
			}
        });
	
        
        //Police
	button2.setOnClickListener(new View.OnClickListener() {
		
		//@Override  
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			button2.startAnimation(a1);
			
			if(loc.equalsIgnoreCase("Default"))
			{
				try {
					db.open();
				
				Cursor c = db.getAllTitles("H");
				if(c.moveToFirst())
				{
				do{	
				
				smsmgr = android.telephony.SmsManager.getDefault();
				smsmgr.sendTextMessage(c.getString(1), null, msgpolice+usrname, null, null);
				Toast.makeText(v.getContext(), "SMS send without satlite location....", 500).show();
				
				}while(c.moveToNext());
				db.clearq();
				db.pushmod("H");
				}
				else{
					Toast.makeText(v.getContext(), "Please assign contacts.", 500).show();
				}
				
				db.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				
			}
			else{
				
				
				try {
					db.open();
				
				Cursor c = db.getAllTitles("H");
				if(c.moveToFirst())
				{
				do{	
				
				smsmgr = android.telephony.SmsManager.getDefault();
				smsmgr.sendTextMessage(c.getString(1), null, msgpolice+usrname+"\n"+sb.toString(), null, null);
				Toast.makeText(v.getContext(),"SMS send with satlite location...", 500).show();
				}while(c.moveToNext());
				}
				else{
					Toast.makeText(v.getContext(), "Please assign contacts.", 500).show();
				}
				db.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		}
	});

	
	//Cleaning
button3.setOnClickListener(new View.OnClickListener() {
		
		//@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			button3.startAnimation(a1);
			if(loc.equalsIgnoreCase("Default"))
			{
				
			//	textView1.setText("Stelite not available... pls try again..");
				Toast.makeText(v.getContext(), "Satelite not available... pls try again..", 500).show();
			}
			else{
				try {
					db.open();
				
				Cursor c = db.getAllTitles("C");
				if(c.moveToFirst())
				{
				do{	
				
				smsmgr = android.telephony.SmsManager.getDefault();
				smsmgr.sendTextMessage(c.getString(1), null, msgclean+usrname+"\n"+sb.toString(), null, null);
				Toast.makeText(v.getContext(), "SMS send with satlite location..", 500).show();
				}while(c.moveToNext());
				
				}
				else{
					Toast.makeText(v.getContext(), "Please assign contacts.", 500).show();
				}
				db.close();
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	});

//Fire service
fire.setOnClickListener(new View.OnClickListener() {
	
	//@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		fire.startAnimation(a1);
		if(loc.equalsIgnoreCase("Default"))
		{
			try {
				db.open();
			
			Cursor c = db.getAllTitles("F");
			if(c.moveToFirst())
			{
			do{	
			
			smsmgr = android.telephony.SmsManager.getDefault();
			smsmgr.sendTextMessage(c.getString(1), null, msgfire+usrname, null, null);
			Toast.makeText(v.getContext(), "SMS send without satlite location.", 500).show();
			}while(c.moveToNext());
			db.clearq();
			db.pushmod("F");
			}
			else{
				Toast.makeText(v.getContext(), "Please assign contacts.", 500).show();
			}
			
			db.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		else{
			try {
				db.open();
			
			Cursor c = db.getAllTitles("F");
			if(c.moveToFirst())
			{
			do{	
			
			smsmgr = android.telephony.SmsManager.getDefault();
			smsmgr.sendTextMessage(c.getString(1), null, msgfire+usrname+"\n"+sb.toString(), null, null);
			Toast.makeText(v.getContext(), "SMS send with satlite location..", 500).show();
			}while(c.moveToNext());
			}
			else{
				Toast.makeText(v.getContext(), "Please assign contacts.", 500).show();
			}
			db.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
});	

//Ambulance
ambulance.setOnClickListener(new View.OnClickListener() {
	
	//@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ambulance.startAnimation(a1);
		if(loc.equalsIgnoreCase("Default"))
		{
			
			try {
				db.open();
			
			Cursor c = db.getAllTitles("A");
			if(c.moveToFirst())
			{
			do{	
			
			smsmgr = android.telephony.SmsManager.getDefault();
			smsmgr.sendTextMessage(c.getString(1), null, msgambulance+usrname+"\n", null, null);
			Toast.makeText(v.getContext(), "SMS send without satlite location..", 500).show();
			}while(c.moveToNext());
			db.clearq();
			db.pushmod("A");
			
			}
			else{
				Toast.makeText(v.getContext(), "Please assign contacts.", 500).show();
			}
			
			db.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else{
		
			try {
				db.open();
			
			Cursor c = db.getAllTitles("A");
			if(c.moveToFirst())
			{
			do{	
			
			smsmgr = android.telephony.SmsManager.getDefault();
			smsmgr.sendTextMessage(c.getString(1), null, msgambulance+usrname+"\n"+sb.toString(), null, null);
			Toast.makeText(v.getContext(), "SMS send with satlite location..", 500).show();
			}while(c.moveToNext());
			}
			else{
				Toast.makeText(v.getContext(), "Please assign contacts.", 500).show();
			}
			db.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
});
    }

	@Override
	protected void onResume() {
		/*
		 * onResume is is always called after onStart, even if the app hasn't been
		 * paused
		 *
		 * add location listener and request updates every 1000ms or 10m
		 */
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10f, this);
		super.onResume();
	}
    
	@Override
	protected void onPause() {
		/* GPS, as it turns out, consumes battery like crazy */
		lm.removeUpdates(this);
		super.onResume();
	}
	
	//@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Log.v(tag, "LocatFire_Forceion Changed");

		sb = new StringBuilder(512);

		noOfFixes++;

		/* display some of the data in the TextView */
		sb.append("Longitude: ");
		sb.append(location.getLongitude());
		sb.append('\n');

		sb.append("Latitude: ");
		sb.append(location.getLatitude());
		sb.append('\n');

		sb.append("Altitiude: ");
		sb.append(location.getAltitude());
		sb.append('\n');

		sb.append("Accuracy: ");
		sb.append(location.getAccuracy());
		sb.append('\n');

		sb.append("Timestamp: ");
		sb.append(location.getTime());
		sb.append('\n');
		loc = sb.toString();
		textView1.setText("GPS Data Available...!!");
		typemod = "";
		 try {
				db.open();
			
			Cursor cc = db.popmod();
			cc.moveToFirst();
			if(cc.getCount() >0)
			{ 
				
				typemod=cc.getString(0).toString();
			}
			
			cc.close();	
			
			db.clearq();
			db.close();
			} catch (SQLException e) {
				
			}
		 
			if(typemod!="")
		{
				if(typemod.equalsIgnoreCase("H")== true)
				{
					msgsend=msgpolice;
				}
				if(typemod.equalsIgnoreCase("A")== true)
				{
					msgsend=msgambulance;
				}
				if(typemod.equalsIgnoreCase("C")== true)
				{
					msgsend=msgclean;
				}
				if(typemod.equalsIgnoreCase("F")== true)
				{
					msgsend=msgfire;
				}
				
			try {
				db.open();
			
			Cursor c = db.getAllTitles(typemod);
			if(c.moveToFirst())
			{
			do{	
			
			smsmgr = android.telephony.SmsManager.getDefault();
			smsmgr.sendTextMessage(c.getString(1), null, msgsend+"\n"+usrname+"\n"+sb.toString(), null, null);
			Toast.makeText(this, "SMS send with satlite location..", 500).show();
			}while(c.moveToNext());
			}
			db.close();
			c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
	}

	//@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		Log.v(tag, "Disabled");

		/* bring up the GPS settings */
		Intent intent = new Intent(
				android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(intent);
	}

	//@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		/* This is called when the GPS status alters */
		switch (status) {
		case LocationProvider.OUT_OF_SERVICE:
			Log.v(tag, "Status Changed: Out of Service");
			Toast.makeText(this, "Status Changed: Out of Service",
					Toast.LENGTH_SHORT).show();
			break;
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			Log.v(tag, "Status Changed: Temporarily Unavailable");
			Toast.makeText(this, "Status Changed: Temporarily Unavailable",
					Toast.LENGTH_SHORT).show();
			break;
		case LocationProvider.AVAILABLE:
			Log.v(tag, "Status Changed: Available");
			Toast.makeText(this, "Status Changed: Available",
					Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
	
}