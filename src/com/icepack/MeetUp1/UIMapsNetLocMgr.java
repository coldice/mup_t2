package com.icepack.MeetUp1;

import java.util.ArrayList;

import com.google.android.maps.MapView;
import com.icepack.MeetUp1.common.MULocation;
import com.icepack.MeetUp1.common.MUUser;

public class UIMapsNetLocMgr {
	public ArrayList<ULocTracker> locTrackerList;
	public ArrayList<MapsOverlayDraw> mapNetOverlays;
	public ClientCommunicationHttp clientComm;
	MapView refMapView;
	public UIHelper uiHelperRef;
	
	public UIMapsNetLocMgr(ArrayList<ULocTracker> locTrackerList, ClientCommunicationHttp clientComm, MapView refMapView)
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
		mapNetOverlays = new ArrayList<MapsOverlayDraw>();
		mapNetOverlays.add(newDraw);
		this.locTrackerList.add(newTracker);
		this.refMapView.getOverlays().add(mapNetOverlays.get(mapNetOverlays.size()-1));
		newDraw.sel_col = user.id;
	}
	
	public void cleanLocUser() {
		//TODO: to be implemented
	}
	
	public void updateUserLoc() {
		//locTrackerList.get(i);
		
		for(ULocTracker uLocTracker : locTrackerList) {
				
			ArrayList<MULocation> newLocs = new ArrayList<MULocation>();
			if(this.clientComm!=null) {
				newLocs = clientComm.getLocation(uLocTracker.locUser.id, uLocTracker.lastLocId);
			}
			
			for(MULocation newLocPoint: newLocs) {
				uLocTracker.refinedLocP.add(newLocPoint);
			}
			
			if(this.uiHelperRef != null) {
				this.uiHelperRef.dispMsg("got "+newLocs.size()+" locs for uid: "+uLocTracker.locUser.id);
			}
		}
	}
	
}
