package com.icepack.MeetUp1;

import java.util.Stack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UISettings extends Activity {
	
	public UIHelper uiHelperRef;
	public ClientCommunicationHttp clientComm;
	
	Button btnSave;
	EditText tVal1;
	EditText tVal2;
	
	Button test;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.tvsettings);
        
        ((MeetUp1Activity)this.getParent()).tabCallback3(this);
        btnSave = (Button)findViewById(R.id.btnsaveset);
        tVal1 = (EditText)findViewById(R.id.inp_val1);
        tVal2 = (EditText)findViewById(R.id.inp_val2);
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
			this.uiHelperRef.setStServerIp(tVal1.getText().toString());
			this.uiHelperRef.setStOwnUserId(new Integer(tVal2.getText().toString()));
		} catch (Exception e) {
			//error
		}
    }
	
	public void loadSettings() {
		tVal1.setText(this.uiHelperRef.getStServerIp());
		tVal2.setText(Integer.toString(this.uiHelperRef.getStOwnUserId()));
	}
}
