package com.icepack.MeetUp1;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MeetUp1Activity extends Activity {
    /** Called when the activity is first created. */
	ClientCommunicationHttp clientComm;
	Settings settings;
	Button bLogin;
	EditText etUsername;
	EditText etPassword;
	CheckBox cbAutologin;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	// SETUP OBJECTS
        settings = new Settings((SharedPreferences)(getSharedPreferences("prefs",MODE_PRIVATE)));
        this.clientComm = new ClientCommunicationHttp(this.settings.getServerIp(), this.settings.getServerPort());
    	if(settings.getAutologin()) {
    		clientComm.login(settings.getUsername(), settings.getPassword());
    		Intent intent = new Intent(MeetUp1Activity.this, UIMain.class);
    		startActivity(intent);
    		finish();
    	}
    	else {
	        // SETUP LAYOUT
	        setContentView(R.layout.llogin);
	       	bLogin = (Button)findViewById(R.id.login);
	        etUsername = (EditText)findViewById(R.id.username);
	        etPassword = (EditText)findViewById(R.id.password);
	        cbAutologin = (CheckBox)findViewById(R.id.autologin);
	        cbAutologin.setChecked(false);
	        
	        bLogin.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	if(clientComm!=null)
	        		{
	            		settings.setUsername(etUsername.getText().toString());
	            		settings.setPassword(etPassword.getText().toString());
	            		settings.setAutologin(cbAutologin.isChecked());
	            		settings.save(); //TODO Exception if save() fails
	            		clientComm.login(settings.getUsername(), settings.getPassword());
	            		Intent intent = new Intent(MeetUp1Activity.this, UIMain.class);
	            		startActivity(intent);
	            		finish();
	        		}
	            }
	        });
    	}
        
        
        
        //Resources res = getResources(); // Resource object to get Drawables
        //Intent intent;  // Reusable Intent for each tab

        //LayoutInflater.from(this).inflate(R.layout.main, tabHost.getTabContentView(), true);
        
        
        
        /* ************ sub class setup
         * 1: Room Tab 2: Track Tab 3: Settings Tab
         */
        
        //+ Room Tab
     	//- set up class as intent
        //intent = new Intent().setClass(this, UIRooms.class);        
    }
}