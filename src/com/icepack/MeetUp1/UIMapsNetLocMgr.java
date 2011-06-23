package com.icepack.MeetUp1;

import java.util.ArrayList;

import com.icepack.MeetUp1.common.MUUser;

public class UIMapsNetLocMgr {
	ArrayList<ULocTracker> locTrackerList;
	
	public UIMapsNetLocMgr(ArrayList<ULocTracker> locTrackerList)
	{
		this.locTrackerList = locTrackerList;
	}
	
	public void addLocUser(MUUser user) {
		ULocTracker newTracker = new ULocTracker();
		newTracker.setLocUser(user);
		
		this.locTrackerList.add(newTracker);
	}
	
	public void cleanLocUser() {
		//TODO: to be implemented
	}
	
	public void updateUserLoc(int id) {
		//locTrackerList.get(i);
	}
}
