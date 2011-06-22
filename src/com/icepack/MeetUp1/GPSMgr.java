package com.icepack.MeetUp1;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class GPSMgr implements LocationListener{
	private double latitude;
	private double longitude;
	private LocationManager locMgrRef = null;
	private UIMaps uiMapRef = null;
	
	
	public GPSMgr() {
		// TODO Auto-generated constructor stub
		/*
        
        txt_pos1 = (TextView)findViewById(R.id.txt_pos1);
        txt_log = (TextView)findViewById(R.id.txt_log);
        btn_updateloc = (Button)findViewById(R.id.btn_updateloc);
        
        txt_pos1.setText("loading...");
        */
        //locMgrRef = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE); //to be set outside
        
	}
	
	public void setupGPSMgr(LocationManager refLocMgr, UIMaps refUIMap)
	{
		this.locMgrRef = refLocMgr;
		this.uiMapRef = refUIMap;
		
		locMgrRef.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	}
	
	public void updateLocation() {
		/*
    	try {
    		Location tmp_loc = locMgr.getLastKnownLocation(LOCATION_SERVICE);
    		
    		this.latitude = tmp_loc.getLatitude();
            this.longitude = tmp_loc.getLongitude();
    	} catch (Exception e)
    	{
    		this.txt_log.setText(this.txt_log.getText().toString() +e.getMessage());
    		this.txt_pos1.setText("Update failed");
    	}
    	*/
    }
    
    @Override
    public void onLocationChanged(Location location) {
    	
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        
        uiMapRef.updateLocData(location);
    }
    @Override
    public void onProviderDisabled(String provider) {
       
    }
    @Override
    public void onProviderEnabled(String provider) {
       
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
       
    }

}
