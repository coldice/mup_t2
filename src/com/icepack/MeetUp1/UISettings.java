package com.icepack.MeetUp1;

import java.util.Stack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class UISettings extends Activity {
	
	public UIHelper uiHelperRef;
	public Settings settings;
	public ClientCommunicationHttp clientComm;
	
	Button btnSave;
	EditText tVal1;
	EditText tVal2;
	CheckBox cbAutologin;
	
	Button test;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.tvsettings);
        
        ((UIMain)this.getParent()).tabCallback3(this);
        btnSave = (Button)findViewById(R.id.btnsaveset);
        tVal1 = (EditText)findViewById(R.id.inp_val1);
        tVal2 = (EditText)findViewById(R.id.inp_val2);
        cbAutologin = (CheckBox)findViewById(R.id.autologin);
        test = (Button)findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if(clientComm!=null)
        		{
            		clientComm.test();
        		}
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	saveSettings();
            }
        });
        
        loadSettings();
        
	}
	
	
	public void saveSettings() {
		try {
			this.settings.setServerIp(tVal1.getText().toString());
			this.settings.setAutologin(cbAutologin.isChecked());
			//DEBUG
			this.settings.setUserId(new Integer(tVal2.getText().toString()));
			
			this.settings.save(); //TODO Catch 0 Exception
		} catch (Exception e) {
			//error
		}
    }
	
	public void loadSettings() {
		tVal1.setText(this.settings.getServerIp());
		cbAutologin.setChecked(settings.getAutologin());
		//DEBUG
		tVal2.setText(""+this.settings.getUserId());
		
	}
}
