package com.icepack.MeetUp1;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.icepack.MeetUp1.common.MULocation;
import com.icepack.MeetUp1.common.MUUser;

public class UIMapsNetLocMgr {
	public ArrayList<ULocTracker> locTrackerList;
	public ArrayList<MapsOverlayDraw> mapNetOverlays;
	public ArrayList<MapsOverlayItemMgr> mapOvItemMgrArr;
	public ClientCommunicationHttp clientComm;
	MapView refMapView;
	public UIHelper uiHelperRef;
	public Drawable fLocDrawable;
	
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
		
		MapsOverlayItemMgr newImgr = new MapsOverlayItemMgr();
		newImgr.tfunc1(fLocDrawable, refMapView, newTracker.geoLocPoint);
		//above line add the mark to the manager
		mapOvItemMgrArr.add(newImgr);
		newDraw.sel_col = user.id;
	}
	
	public void cleanLocUser() {
		//TODO: to be implemented
	}
	
	public void updateUserLoc() {
		//locTrackerList.get(i);
		
		int icount = locTrackerList.size();
		//for(ULocTracker uLocTracker : locTrackerList) {
		for(int i=0;i<icount;i++) {
			
			ArrayList<MULocation> newLocs = new ArrayList<MULocation>();
			if(this.clientComm!=null) {
				newLocs = clientComm.getLocation(locTrackerList.get(i).locUser.id, locTrackerList.get(i).lastLocId);
			}
			
			for(MULocation newLocPoint: newLocs) {
				locTrackerList.get(i).refinedLocP.add(newLocPoint);
			}
			MULocation tmpLoc = locTrackerList.get(i).getRefinedLocs().get(locTrackerList.get(i).getRefinedLocs().size()-1);
			GeoPoint newPoint = new GeoPoint((int)(tmpLoc.latitude*100000), (int)(tmpLoc.longitude*100000));
			this.mapOvItemMgrArr.get(i).updateGPoint(newPoint, refMapView);
			
			if(this.uiHelperRef != null) {
				this.uiHelperRef.dispMsg("got "+newLocs.size()+" locs for uid: "+locTrackerList.get(i).locUser.id);
			}
		}
	}
	
}
