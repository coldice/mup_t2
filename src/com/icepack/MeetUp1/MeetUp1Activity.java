package com.icepack.MeetUp1;


import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class MeetUp1Activity extends TabActivity {
    /** Called when the activity is first created. */
	UIHelper uiHelper;
	MapView mMapView;
	MapController mapControl;
	ULocTracker ownPosTracker; //not used
	GPSMgr gpsMgr;
	UIMaps uiMaps;
	ClientCommunicationHttp clientComm;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        
        
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        //LayoutInflater.from(this).inflate(R.layout.main, tabHost.getTabContentView(), true);

        // UI Helper Setup
        uiHelper = new UIHelper();
        this.uiHelper.setStOwnUserId(2); //DEBUG CONFIG
        this.uiHelper.setStServerIp("31.18.21.44"); //DEBUG CONFIG
        
        this.clientComm = new ClientCommunicationHttp(this.uiHelper.getStServerIp(), 23232);
        
        /* ************ sub class setup
         * 1: Room Tab 2: Track Tab 3: Settings Tab
         */
        
        //+ Room Tab
     	//- set up class as intent
        intent = new Intent().setClass(this, UIRooms.class);

        //- create Tabhost spec
        spec = tabHost.newTabSpec("roomTab").setIndicator("rooms", res.getDrawable(R.drawable.ic_tab_artists)).setContent(intent);
        tabHost.addTab(spec);

        //+ Track Tab
        intent = new Intent().setClass(this, UIMaps.class);

        spec = tabHost.newTabSpec("trackTab").setIndicator("track",res.getDrawable(R.drawable.ic_tab_artists)).setContent(intent);
        tabHost.addTab(spec);
        
     	//+ Settings Tab
        intent = new Intent().setClass(this, UISettings.class);

        spec = tabHost.newTabSpec("settingsTab").setIndicator("settings",res.getDrawable(R.drawable.ic_tab_artists)).setContent(intent);
        tabHost.addTab(spec);
        
        tabHost.getTabWidget().getChildAt(0).getLayoutParams().height = 75;
        tabHost.getTabWidget().getChildAt(1).getLayoutParams().height = 75;
        tabHost.getTabWidget().getChildAt(2).getLayoutParams().height = 75;
        
        /*

        tabHost.setCurrentTab(1);
        
        
        mMapView = (MapView)findViewById(R.id.mapview1);
        mMapView.setBuiltInZoomControls(true);
        
        mapControl = mMapView.getController();

		*/
    }
    
    public void initTrackConnection()
    {
    	gpsMgr.setupGPSMgr((LocationManager)this.getSystemService(Context.LOCATION_SERVICE), uiMaps);
    	uiHelper.dispMsg("GPS Setup finished!");
    }
    
    public void tabCallback1(UIMaps callClass)
    {
    	//TextView statView = (TextView)callClass.findViewById(R.id.statText1);
    	//statView.setText("bla");
    	
    	//get ui textviews for helper object
    	TextView tv1 = (TextView)callClass.findViewById(R.id.statText1);
    	TextView tv2 = (TextView)callClass.findViewById(R.id.statText2);
    	TextView tv3 = (TextView)callClass.findViewById(R.id.statText3);
    	
        
        uiHelper.setupUIHelperViews(tv1, tv2, tv3);
        uiHelper.clearMsg();
        uiHelper.dispMsg("loading system..");
        uiHelper.setSecMsg("waiting for GPS data");
        uiHelper.setTopMsg("Pos:");
        
        ownPosTracker = new ULocTracker();
        
        
        callClass.clientComm = this.clientComm;
        gpsMgr = new GPSMgr();
    	callClass.uiHelper = this.uiHelper;
    	callClass.uiHelper.dispMsg("system loaded!");
    	this.uiMaps = callClass;
    	
    	
    	initTrackConnection();
    	
    	
    	//callClass.uiHelper.dispMsg("set mark");
    }
    
    public void tabCallback2(UIRooms callClass)
    {
    	callClass.uiHelperRef = this.uiHelper;
    	callClass.clientComm = this.clientComm;
    }
    
    public void tabCallback3(UISettings callClass)
    {
    	callClass.uiHelperRef = this.uiHelper;
    	callClass.clientComm = this.clientComm;
    }
    
    public void gpsUpdateCallback(Location newLoc)
    {
    	
    }
}