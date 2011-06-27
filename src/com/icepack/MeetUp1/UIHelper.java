package com.icepack.MeetUp1;

import android.widget.TextView;

import com.icepack.MeetUp1.common.MUUser;

public class UIHelper {
	TextView txtStatus;
	TextView txtStatus2;
	TextView txtTopstat;
	
	private int statMsgCount = 0;
	private int maxMsgCount = 50;
	
	//internal options
	private int ST_own_userid;
	private String ST_server;
	
	//ADDED! (temp)
	private MUUser user;

	
	//set through
	public int resetUserListFlag = 0;
	
	
	public UIHelper()
	{
		this.ST_own_userid = -1;
		this.ST_server = "";
	}
	
	public void setupUIHelperViews(TextView txtStatusl, TextView txtStatusl2, TextView txtTopstatl) {
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
	
	public void setStOwnUserId(int id)
	{
		this.ST_own_userid = id;
	}
	
	public void setStServerIp(String ip)
	{
		this.ST_server = ip;
	}
	
	public int getStOwnUserId()
	{
		return this.ST_own_userid;
	}
	
	public String getStServerIp()
	{
		return this.ST_server;
	}
}
