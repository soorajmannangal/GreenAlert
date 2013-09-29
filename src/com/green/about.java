package com.green;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class about extends Activity {
	Intent myInt;
	public void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
        
		setContentView(R.layout.about);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
}
}
