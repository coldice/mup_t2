package com.icepack.MeetUp1.common;

public class MULocation {
	public double longitude;
	public double latitude;
	public long time;
	
	public MULocation(double latitude, double longitude, long time)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		this.time = time;
	}
}
