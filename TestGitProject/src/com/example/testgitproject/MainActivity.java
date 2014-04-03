package com.example.testgitproject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("", "");
		Log.d("", "Line 2 added");
		// Now all the changes are there in server

        //pradeep added
        //2nd time
		// added this line without taking pradeeps update
        //adding one more line // pradeep
		// added a new file and this line changed
        //one more time commit // pradeep
		// arun commiting
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
