package com.icepack.MeetUp1.common;

import com.google.android.maps.GeoPoint;

public class MULocation {
	public double longitude;
	public double latitude;
	public long time;
	public int id;
	
	public MULocation(double latitude, double longitude, long time, int id)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		this.time = time;
		this.id = id;
	}
	
	public GeoPoint getGeoLoc()
	{
		GeoPoint retGeoPoint = new GeoPoint((int)(latitude*1000000), (int)(longitude*1000000));
		
		return retGeoPoint;
	}
}
