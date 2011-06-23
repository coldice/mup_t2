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
		
		mapOvItemMgrArr = new ArrayList<MapsOverlayItemMgr>();
	}
	
	public void addLocUser(MUUser user) {
		ULocTracker newTracker = new ULocTracker();
		newTracker.setLocUser(user);
		newTracker.uiHelper = this.uiHelperRef;
		
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
		this.uiHelperRef.dispMsg("setting up marker for uid"+user.id);
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
			uiHelperRef.dispMsg("performing update for uid:"+locTrackerList.get(i).locUser.id);
			
			ArrayList<MULocation> newLocs = new ArrayList<MULocation>();
			if(this.clientComm!=null) {
				this.uiHelperRef.dispMsg("getLocation(uid:"+locTrackerList.get(i).locUser.id+"pid"+locTrackerList.get(i).lastLocId+")");
				this.uiHelperRef.dispMsg("cur points:"+locTrackerList.get(i).refinedLocP.size());
				newLocs = clientComm.getLocation(locTrackerList.get(i).locUser.id, locTrackerList.get(i).lastLocId);
			}
			
			if(newLocs.size()==1) {
				this.uiHelperRef.dispMsg("got only one point, hot update");
				
				GeoPoint newPoint = new GeoPoint((int)(newLocs.get(0).latitude*100000), (int)(newLocs.get(0).longitude*100000));
				this.mapOvItemMgrArr.get(i).updateGPoint(newPoint, refMapView);
				this.locTrackerList.get(i).setCurrentPoint(newLocs.get(0));
				break;
			}
			
			for(MULocation newLocPoint: newLocs) {
				locTrackerList.get(i).refinedLocP.add(newLocPoint);
			}
			if(locTrackerList.get(i).getRefinedLocs().size()>=1) {
				MULocation tmpLoc = locTrackerList.get(i).getRefinedLocs().get(locTrackerList.get(i).getRefinedLocs().size()-1);
				GeoPoint newPoint = new GeoPoint((int)(tmpLoc.latitude*100000), (int)(tmpLoc.longitude*100000));
				this.mapOvItemMgrArr.get(i).updateGPoint(newPoint, refMapView);
				if(locTrackerList.get(i).getRefinedLocs().size()>=1) {
					locTrackerList.get(i).lastLocId = locTrackerList.get(i).refinedLocP.get(locTrackerList.get(i).getRefinedLocs().size()-1).id;
					locTrackerList.get(i).postRefineLocs();
				}
			}
			
			if(this.uiHelperRef != null) {
				this.uiHelperRef.dispMsg("got "+newLocs.size()+" locs for uid: "+locTrackerList.get(i).locUser.id);
				this.uiHelperRef.dispMsg("new cur points:"+locTrackerList.get(i).refinedLocP.size());
			}
		}
	}
	
}
