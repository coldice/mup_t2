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
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        
        
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        //LayoutInflater.from(this).inflate(R.layout.main, tabHost.getTabContentView(), true);

        // ************ sub class setup
        
        
        
        /*
        wv = getLayoutInflater().inflate(R.layout.user_tab, mTabHost.getTabContentView(), false);
        mTabHost.getTabContentView().addView(wv);
        mTabHost.addTab(mTabHost.newTabSpec("User").setIndicator("User").setContent(wv.getId()));
		*/

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, dummyClass1.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("tab1").setIndicator("tab1",
                          res.getDrawable(R.drawable.ic_tab_artists))
                      .setContent(intent);
        tabHost.addTab(spec);

        /*
        View wv = getLayoutInflater().inflate(R.layout.tview1, tabHost.getTabContentView(), false);
        tabHost.getTabContentView().addView(wv);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("tab2").setContent(wv.getId()));
        
        */
        // Do the same for the other tabs
        //intent = new Intent().setClass(this, dummyClass1.class);
/*        spec = tabHost.newTabSpec("albums").setIndicator("tab2",
                          res.getDrawable(R.drawable.ic_tab_artists))
                      .setContent(intent);*/
        /*
        spec = tabHost.newTabSpec("tab2").setIndicator("tab2", res.getDrawable(R.drawable.ic_tab_artists)).setContent(R.id.tview1);
        tabHost.addTab(spec);
        */

        intent = new Intent().setClass(this, UIMaps.class);

        spec = tabHost.newTabSpec("trackTab").setIndicator("track",
                          res.getDrawable(R.drawable.ic_tab_artists))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        
        
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
    	
        uiHelper = new UIHelper(tv1, tv2, tv3);
        uiHelper.clearMsg();
        uiHelper.dispMsg("loading system..");
        uiHelper.setSecMsg("waiting for GPS data");
        uiHelper.setTopMsg("Pos:");
        
        ownPosTracker = new ULocTracker();
        
        
        gpsMgr = new GPSMgr();
    	callClass.uiHelper = this.uiHelper;
    	callClass.uiHelper.dispMsg("system loaded!");
    	this.uiMaps = callClass;
    	
    	initTrackConnection();
    	
    	
    	//callClass.uiHelper.dispMsg("set mark");
    }
    
    public void gpsUpdateCallback(Location newLoc)
    {
    	
    }
}