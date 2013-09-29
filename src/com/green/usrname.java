package com.green;

import java.sql.SQLException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class usrname extends Activity {
	
	DBAdapter db;
	Intent myInt;
	public void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
        
		setContentView(R.layout.user_detail);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		db=new DBAdapter(this);
	     
	     final EditText txt_usrname=(EditText)findViewById(R.id.editText1);
	     final ImageView img_next=(ImageView)findViewById(R.id.imageView1);
	     
	     img_next.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if(txt_usrname.getText().toString()!= "")
					{
					try {
						db.open();
					
					db.insUsrName(txt_usrname.getText().toString());
					db.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					nextview();
					
					}
				}
			});
	     try {
				db.open();
			
			Cursor c = db.fetchuserName();
			c.moveToFirst();
			if(c.getCount() >0)
			{ 
				nextview();
				//myInt = new Intent(this,ButtonTestActivity.class);
				//startActivityForResult(myInt, 0);
			}
			
			db.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     
	     
	     //db.insUsrName(txt_usrname.getText().toString());
		
       
	}
	 public void nextview()
	 {
		 myInt = new Intent(this,GreenAlertActivity.class);
			startActivityForResult(myInt, 0);
	 }
	 @Override
		protected void onStop() {
			/* may as well just finish since saving the state is not important for this toy app */
			//Canvas c = new Canvas();
			//v1.draw(c);
			finish();
			super.onStop();
		}
}
