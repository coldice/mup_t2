package com.icepack.MeetUp1;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.location.Location;
import android.text.format.DateFormat;

import com.google.android.maps.GeoPoint;
import com.icepack.MeetUp1.common.MULocation;

public class ULocTracker {
	public ArrayList<MULocation> refinedLocP;// = new ArrayList<GeoPoint>();
	public MULocation currentLoc;
	private MULocation lastSavedLoc;
	public GeoPoint geoLocPoint;
	
	int maxTrackLength;
	public float minPointDistance;
	public float lastPointDistanceData[];
	public float lastPointDistance;
	public int maxPointNum;
	private DecimalFormat df1;

	
	UIHelper uiHelper;
	
	public ULocTracker()
	{
		refinedLocP = new ArrayList<MULocation>();
		minPointDistance = 20;
		maxPointNum = 50;
		geoLocPoint = new GeoPoint(0, 0);
		lastPointDistanceData = new float[2];
		lastPointDistance = 0;
		df1  = new DecimalFormat("0.00000");
	}
	
	public void updateCurrentLoc(Location newLoc)
	{
		currentLoc = new MULocation(newLoc.getLatitude(), newLoc.getLongitude(), newLoc.getTime());
		
		if(refinedLocP.size()>=1)
		{
			Location.distanceBetween(currentLoc.latitude, currentLoc.longitude, currentLoc.latitude, currentLoc.longitude, lastPointDistanceData);
			lastPointDistance = lastPointDistanceData[0];
		}
		
		if(lastSavedLoc == null)
		{
			//GeoPoint newPoint = new GeoPoint((int)(currentLoc.latitude), (int)(currentLoc.getLongitude()*1E6));
			refinedLocP.add(currentLoc);
			lastSavedLoc = currentLoc;
		}
		else if(lastPointDistance > minPointDistance)
		{
			//GeoPoint newPoint = new GeoPoint((int)(currentLoc.getLatitude()*1E6), (int)(currentLoc.getLongitude()*1E6));
			refinedLocP.add(currentLoc);
			lastSavedLoc = currentLoc;
			
			dispatchMessage("Location Saved : "+df1.format(currentLoc.latitude)+" : "+df1.format(currentLoc.longitude), 1);
		}
		else
		{
			MULocation newPoint = new MULocation(newLoc.getLatitude(), newLoc.getLongitude(), newLoc.getTime());
			refinedLocP.set(refinedLocP.size()-1, newPoint);
			//dispatchMessage("loc distance below min("+this.minPointDistance+")", 1);
		}
		
		if(refinedLocP.size()>maxPointNum)
		{
			refinedLocP.remove(0);
		}
		
		//geoLocPoint = new GeoPoint((int)(currentLoc.getLatitude()*1E6), (int)(currentLoc.getLongitude()*1E6));
		
		dispatchMessage("|pc:"+this.refinedLocP.size()+" |minD:"+(int)minPointDistance+" |lastD:"+(int)lastPointDistance+"  ", 2);
		dispatchMessage("Pos: "+df1.format(currentLoc.latitude)+" : "+df1.format(currentLoc.longitude)+"  Time:"+DateFormat.format("hh:mm:ss", currentLoc.time), 3);
	}
	
	public ArrayList<MULocation> getRefinedLocs()
	{
		return refinedLocP;
	}
	
	private void dispatchMessage(String message, int tgt)
	{
		if(this.uiHelper != null)
		{
			if(tgt==1)
			{
				uiHelper.dispMsg(message);
			} else if(tgt==2) {
				uiHelper.setSecMsg(message);
			} else if(tgt==3) {
				uiHelper.setTopMsg(message);
			}
			
		}
	}
	
	
}
