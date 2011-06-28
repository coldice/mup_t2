package com.icepack.MeetUp1;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;
import com.icepack.MeetUp1.common.MULocation;
import com.icepack.MeetUp1.common.MUUser;

public class UIMaps extends MapActivity {
	Settings settings;
	MapView mMapView;
	MapController mapControl;
    Projection projection;
    List<Overlay> mapOverlays;
    Drawable drawable;
    ArrayList<ULocTracker> locTrackerList;
    ULocTracker OwnLocTracker;
    GeoPoint tmpPoint;
    
    UIMapsNetLocMgr netLocMgr;
    ClientCommunicationHttp clientComm;
    
    MapsOverlayDraw mapsOverlays;
    MapsOverlayItemMgr mapOvItemMgr;
    
    MapsOvLineDrawer mapsLineTest;
    
    ArrayList<MUUser> userList;
    
    Button btnRelUList;
    Button btnRelULoc;
    public UIHelper uiHelper;
    
    ULocMgr uLocManager; //Major object
    
    SeekBar rangeSel;

	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.tview1);
        
        mMapView = (MapView)findViewById(R.id.mapview1);
        mMapView.setBuiltInZoomControls(true);
        
        mapControl = mMapView.getController();
        mapOverlays = mMapView.getOverlays();

        uLocManager = new ULocMgr(mMapView);
        
        /*
         * TESTCASE
         */
        Location testloc1 = new Location(LOCATION_SERVICE);
        testloc1.setLatitude(38.964534);
        testloc1.setLongitude(-103.143311);
        
        Location testloc2 = new Location(LOCATION_SERVICE);
        testloc2.setLatitude(39.8136);
        testloc2.setLongitude(-92.947998);
        
        Location testloc3 = new Location(LOCATION_SERVICE);
        testloc3.setLatitude(34.67478);
        testloc3.setLongitude(-97.364502);
        
        uLocManager.updateOwnLoc(testloc1);
        uLocManager.updateOwnLoc(testloc2);
        uLocManager.updateOwnLoc(testloc3);
        
        /*
        mapsOverlays = new MapsOverlayDraw(mMapView);
        mapOvItemMgr = new MapsOverlayItemMgr();
        
        mapsLineTest = new MapsOvLineDrawer(mMapView);
        
        ArrayList<MULocation> testLocs = new ArrayList<MULocation>();
        testLocs.add(new MULocation(38.964534, -103.143311, 1, 1));
        testLocs.add(new MULocation(39.8136, -92.947998, 1, 2));
        testLocs.add(new MULocation(34.67478, -97.364502, 1, 3));
        
        mapsLineTest.MULocPoints = testLocs;
        
        locTrackerList = new ArrayList<ULocTracker>();
        OwnLocTracker = new ULocTracker();
        
        
        
        mapsOverlays.MULocPoints = OwnLocTracker.refinedLocP;

        
        mapOvItemMgr.tfunc1(this.getResources().getDrawable(R.drawable.mark1), (MapView)this.findViewById(R.id.mapview1), OwnLocTracker.geoLocPoint);
        

       
        OwnLocTracker.uiHelper = this.uiHelper; //pass down ref
        
        //clientComm = new ClientCommunicationHttp(this.uiHelper.getStServerIp(), 23232);
        
        //netref
        netLocMgr = new UIMapsNetLocMgr(locTrackerList, clientComm, mMapView);
        netLocMgr.uiHelperRef = this.uiHelper; //to be wrapped in constructer/method
        netLocMgr.fLocDrawable = this.getResources().getDrawable(R.drawable.mark2);
        //this sets drawable for secondary items
        
        //mapOverlays.add(mapsOverlays);
        mapOverlays.add(mapsLineTest);
        
        */
        // TEST
        	userList = new ArrayList<MUUser>();
        	((UIMain)getParent()).tabCallback1(this);
        	
        btnRelUList = (Button)findViewById(R.id.btnRelUserList);
        btnRelULoc = (Button)findViewById(R.id.btnRelUserLoc);
        
        
        // ******************
        // ui listeners
        
        rangeSel = (SeekBar) this.findViewById(R.id.rangeSel1);
        
        OnSeekBarChangeListener seekChangeListener = new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				int selProg = rangeSel.getProgress();
				//uiHelper.dispMsg("set minimum Point Distance:"+selProg);
				OwnLocTracker.minPointDistance = selProg;
				uiHelper.setSecMsg("|pc:"+OwnLocTracker.refinedLocP.size()+" |minD:"+(int)OwnLocTracker.minPointDistance+" |lastD:"+(int)OwnLocTracker.lastPointDistance+"  ");
			}
		};
        
        rangeSel.setOnSeekBarChangeListener(seekChangeListener);

      //OnClick Listeners
        btnRelUList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	updateUserDataList();
            	
            	
            }
        });
        
        btnRelULoc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	updateUserData();
            	
            	
            }
        });
        
	}
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* called from Location Manager (GPSMgr) on new (own) Location Data
	 * 
	 */
	public void updateOwnLocData(Location newLoc)
	{
		
		uiHelper.dispMsg("New Loc: "+newLoc.getLatitude()+" : "+newLoc.getLongitude());
		/*
		OwnLocTracker.updateCurrentLoc(newLoc);
		//update down
		//mapsOverlays.updateOverlayItems();
		
		
		tmpPoint = new GeoPoint((int)(newLoc.getLatitude()*1000000), (int)(newLoc.getLongitude()*1000000));
		mapControl.animateTo(tmpPoint);
		
		mapsOverlays.geoLoc = OwnLocTracker.currentLoc;
		mapsOverlays.updateOverlayItemGeoLoc();
		
		mapOvItemMgr.updateGPoint(tmpPoint, (MapView)this.findViewById(R.id.mapview1));
		
		
		
		//Update Online data
		MULocation newMULoc = new MULocation(newLoc.getLatitude(), newLoc.getLongitude(), newLoc.getTime(), -1);
		clientComm.setLocation( this.settings.getUserId(), newMULoc);
		
		uLocManager.updateOwnLoc(newLoc);
		*/
		
		uLocManager.updateOwnLoc(newLoc);
	}
	
	public void updateMapViewSet()
	{
		//tmpPoint = new GeoPoint((int)locTracker.currentLoc.getLatitude()*1000000, (int)locTracker.currentLoc.getLongitude()*1000000);
	}
	
	public void updateNetLocs()
	{
		netLocMgr.updateUserLoc();
	}
	
	public void updateUserData()
	{
		
		//do more
		
		//update list
		int icount = userList.size();
		
		
		for(int i=0;i<icount;i++) {
			this.uiHelper.dispMsg("user: "+userList.get(i).username+" (id: "+userList.get(i).id+")");
		}
		
		this.netLocMgr.updateUserLoc();
	}
	
	public void updateUserDataList() {
		if(this.uiHelper.resetUserListFlag==1) {
			this.userList.clear();
			this.uiHelper.resetUserListFlag=0;
			this.uiHelper.dispMsg("*** RESETTED USERLIST ***");
		}
		ArrayList<MUUser> tmpUserList = clientComm.getUserList( this.settings.getUserId());
		
		this.uiHelper.dispMsg("got userlist with "+tmpUserList.size()+" user");
		
		userList = tmpUserList;
		
		int icount = userList.size();
		int ocount = this.netLocMgr.locTrackerList.size();
		boolean found=false;
		boolean myself=false;
		for(int i=0; i<icount; i++) {
			found=false;
			myself=false;
			for(int o=0; o<ocount&&!found; o++) {
				if(this.netLocMgr.locTrackerList.get(o).getLocUser().id == userList.get(i).id) {
					found=true;
				}
			}
			if(userList.get(i).id == this.settings.getUserId()) {
				myself=true;
				this.uiHelper.dispMsg("found myself! id:"+userList.get(i).id);
			}
			if(found==false&&myself==false) {
				this.netLocMgr.addLocUser(userList.get(i));
				this.uiHelper.dispMsg("added user! id:"+userList.get(i).id);
			}
		}
	}
	
	public void setupOwnUserData() {
		ArrayList<MULocation> tmpLocList = clientComm.getLocation(this.settings.getUserId(), 1);
		
		this.uiHelper.dispMsg("got point list with"+tmpLocList.size()+" items");
		OwnLocTracker.refinedLocP.clear();
		for(MULocation tmpLocItem : tmpLocList) {
			OwnLocTracker.refinedLocP.add(tmpLocItem);
			if(OwnLocTracker.refinedLocP.size()-1 >=1) {
				//OwnLocTracker.updateCurrentLoc(OwnLocTracker.refinedLocP.get(OwnLocTracker.refinedLocP.size()-1));
			}
		}
		//OwnLocTracker.refinedLocP = tmpLocList;
	}

}
