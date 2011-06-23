package com.icepack.MeetUp1.common;

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
}
