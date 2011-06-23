package com.icepack.MeetUp1;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.location.Location;
import android.text.format.DateFormat;

import com.google.android.maps.GeoPoint;
import com.icepack.MeetUp1.common.MULocation;
import com.icepack.MeetUp1.common.MUUser;

public class ULocTracker {
	public ArrayList<MULocation> refinedLocP;// = new ArrayList<GeoPoint>();
	public MULocation currentLoc;
	private MULocation lastSavedLoc;
	public GeoPoint geoLocPoint;
	
	public int lastLocId;
	
	int maxTrackLength;
	public float minPointDistance;
	public float lastPointDistanceData[];
	public float lastPointDistance;
	public int maxPointNum;
	private DecimalFormat df1;
	
	public MUUser locUser;

	
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
		lastLocId = 0;
	}
	
	public void updateCurrentLoc(Location newLoc)
	{
		currentLoc = new MULocation(newLoc.getLatitude(), newLoc.getLongitude(), newLoc.getTime(), -1);
		
		if(refinedLocP.size()>=1)
		{
			Location.distanceBetween(currentLoc.latitude, currentLoc.longitude, refinedLocP.get(refinedLocP.size()-1).latitude, refinedLocP.get(refinedLocP.size()-1).longitude, lastPointDistanceData);
			lastPointDistance = Math.abs(lastPointDistanceData[1]);
			dispatchMessage("dst1:"+lastPointDistanceData[0]+" dst2:"+lastPointDistanceData[1], 1);
			
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
			MULocation newPoint = new MULocation(newLoc.getLatitude(), newLoc.getLongitude(), newLoc.getTime(), -1);
			refinedLocP.set(refinedLocP.size()-1, newPoint);
			//dispatchMessage("loc distance below min("+this.minPointDistance+")", 1);
		}
		
		if(refinedLocP.size()>maxPointNum)
		{
			refinedLocP.remove(0);
			uiHelper.dispMsg("removed 1 point for user "+locUser.id);
		}
		
		//geoLocPoint = new GeoPoint((int)(currentLoc.getLatitude()*1E6), (int)(currentLoc.getLongitude()*1E6));
		
		dispatchMessage("|pc:"+this.refinedLocP.size()+" |minD:"+(int)minPointDistance+" |lastD:"+(int)lastPointDistance+"  ", 2);
		dispatchMessage("Pos: "+df1.format(currentLoc.latitude)+" : "+df1.format(currentLoc.longitude)+"  Time:"+DateFormat.format("hh:mm:ss", currentLoc.time), 3);
	}
	
	public ArrayList<MULocation> getRefinedLocs()
	{
		return refinedLocP;
	}
	
	public void postRefineLocs()
	{
		uiHelper.dispMsg("postrefine userid: (uid:"+locUser.id+")");
		if(this.refinedLocP.size() > maxPointNum) {
			uiHelper.dispMsg("too many points - refining (uid:"+locUser.id+")");
			int isize = this.refinedLocP.size();
			int difsize = isize - maxPointNum;
			
			ArrayList<MULocation> tmpPoints = new ArrayList<MULocation>();
			
			for(int i=difsize; i<isize; i++) {
				tmpPoints.add(this.refinedLocP.get(i));
			}
			
			this.refinedLocP = tmpPoints;
			
			uiHelper.dispMsg("done refining");
		}
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
	
	public void setLocUser(MUUser newuser) {
		this.locUser = newuser;
	}
	
	public MUUser getLocUser() {
		return this.locUser;
	}
	
	
}
