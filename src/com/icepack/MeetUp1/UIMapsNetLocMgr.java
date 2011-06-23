package com.icepack.MeetUp1;

import java.util.ArrayList;

import com.google.android.maps.MapView;
import com.icepack.MeetUp1.common.MULocation;
import com.icepack.MeetUp1.common.MUUser;

public class UIMapsNetLocMgr {
	public ArrayList<ULocTracker> locTrackerList;
	public ArrayList<MapsOverlayDraw> mapNetOverlays;
	ClientCommunicationHttp clientComm;
	MapView refMapView;
	
	public UIMapsNetLocMgr(ArrayList<ULocTracker> locTrackerList, ClientCommunicationHttp clientCommt, MapView refMapView)
	{
		this.locTrackerList = locTrackerList;
		this.clientComm = clientComm;
		this.refMapView = refMapView;
	}
	
	public void addLocUser(MUUser user) {
		ULocTracker newTracker = new ULocTracker();
		newTracker.setLocUser(user);
		
		MapsOverlayDraw newDraw = new MapsOverlayDraw(refMapView);
		newDraw.MULocPoints = newTracker.refinedLocP;
		mapNetOverlays.add(newDraw);
		this.locTrackerList.add(newTracker);
	}
	
	public void cleanLocUser() {
		//TODO: to be implemented
	}
	
	public void updateUserLoc(int id) {
		//locTrackerList.get(i);
		
		for(ULocTracker uLocTracker : locTrackerList) {
			ArrayList<MULocation> newLocs = clientComm.getLocation(uLocTracker.locUser.id, uLocTracker.lastLocId);
			for(MULocation newLocPoint: newLocs) {
				uLocTracker.refinedLocP.add(newLocPoint);
			}
		}
	}
}
