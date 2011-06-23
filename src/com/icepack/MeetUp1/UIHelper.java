package com.icepack.MeetUp1;

import android.widget.TextView;

public class UIHelper {
	TextView txtStatus;
	TextView txtStatus2;
	TextView txtTopstat;
	
	private int statMsgCount = 0;
	private int maxMsgCount = 50;
	
	public UIHelper(TextView txtStatusl, TextView txtStatusl2, TextView txtTopstatl)
	{
		this.txtStatus = txtStatusl;
		this.txtStatus2 = txtStatusl2;
		this.txtTopstat = txtTopstatl;
		txtStatus.setText("fireup");
		txtStatus2.setText("fireup");
	}
	
	public void dispMsg(String message)
	{
		if(statMsgCount >= maxMsgCount)
		{
			txtStatus.setText("");
			statMsgCount = 0;
		}
		txtStatus.setText(message+"\n"+txtStatus.getText());
		statMsgCount++;
	}
	
	public void clearMsg()
	{
		txtStatus.setText("");
	}
	
	public void setSecMsg(String message)
	{
		txtStatus2.setText(message);
	}
	
	public void setTopMsg(String message)
	{
		txtTopstat.setText(message);
	}
	
	public void displayAlert(String message)
	{
		
	}
}
