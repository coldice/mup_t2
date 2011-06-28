package com.icepack.MeetUp1;

import java.util.List;

import android.location.Location;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class ULocMgr {

	ULocTracker ownLocTracker;
	
	MapView refMapView;
	
	private List<Overlay> mapOverlays;
	private MapsOvLineDrawer ownLineDrawer;
	
	public ULocMgr(MapView refMapView) {
		this.refMapView = refMapView;
		this.ownLocTracker = new ULocTracker();
		this.ownLineDrawer = new MapsOvLineDrawer(refMapView);
		
		// setup the base object to draw
		this.ownLineDrawer.MULocPoints = this.ownLocTracker.refinedLocP;
		
		// add overlay
		refMapView.getOverlays().add(this.ownLineDrawer);
	}
	
	
	
	public void addUser(int userID)
	{
		
	}
	
	//methods for own tracking
	public void updateOwnLoc(Location newLoc)
	{
		ownLocTracker.updateCurrentLoc(newLoc);
	}
}
