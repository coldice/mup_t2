package com.icepack.MeetUp1;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.location.Location;
import android.text.format.DateFormat;

import com.google.android.maps.GeoPoint;

public class ULocTracker {
	public ArrayList<GeoPoint> refinedLocGP;// = new ArrayList<GeoPoint>();
	public Location currentLoc;
	private Location lastSavedLoc;
	public GeoPoint geoLocPoint;
	
	int maxTrackLength;
	public float minPointDistance;
	public float lastPointDistance;
	public int maxPointNum;
	private DecimalFormat df1;

	
	UIHelper uiHelper;
	
	public ULocTracker()
	{
		refinedLocGP = new ArrayList<GeoPoint>();
		minPointDistance = 20;
		maxPointNum = 50;
		geoLocPoint = new GeoPoint(0, 0);
		lastPointDistance = 0;
		df1  = new DecimalFormat("0.00000");
	}
	
	public void updateCurrentLoc(Location newLoc)
	{
		currentLoc = newLoc;
		
		if(lastSavedLoc == null)
		{
			GeoPoint newPoint = new GeoPoint((int)(currentLoc.getLatitude()*1E6), (int)(currentLoc.getLongitude()*1E6));
			refinedLocGP.add(newPoint);
			lastSavedLoc = currentLoc;
		}
		else if(lastPointDistance > minPointDistance)
		{
			lastPointDistance = currentLoc.distanceTo(lastSavedLoc);
			GeoPoint newPoint = new GeoPoint((int)(currentLoc.getLatitude()*1E6), (int)(currentLoc.getLongitude()*1E6));
			refinedLocGP.add(newPoint);
			lastSavedLoc = currentLoc;
			
			dispatchMessage("Location Saved : "+df1.format(currentLoc.getLatitude())+" : "+df1.format(currentLoc.getLongitude()), 1);
		}
		else
		{
			lastPointDistance = currentLoc.distanceTo(lastSavedLoc);
			GeoPoint newPoint = new GeoPoint((int)(currentLoc.getLatitude()*1E6), (int)(currentLoc.getLongitude()*1E6));
			refinedLocGP.set(refinedLocGP.size()-1, newPoint);
			//dispatchMessage("loc distance below min("+this.minPointDistance+")", 1);
		}
		
		if(refinedLocGP.size()>maxPointNum)
		{
			refinedLocGP.remove(0);
		}
		
		geoLocPoint = new GeoPoint((int)(currentLoc.getLatitude()*1E6), (int)(currentLoc.getLongitude()*1E6));
		
		dispatchMessage("|pc:"+this.refinedLocGP.size()+" |minD:"+(int)minPointDistance+" |lastD:"+(int)lastPointDistance+"  ", 2);
		dispatchMessage("Pos: "+df1.format(currentLoc.getLatitude())+" : "+df1.format(currentLoc.getLongitude())+"  Time:"+DateFormat.format("hh:mm:ss", currentLoc.getTime()), 3);
	}
	
	public ArrayList<GeoPoint> getRefinedLocs()
	{
		return refinedLocGP;
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
