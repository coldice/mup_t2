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
    
    
    Button btnRelUList;
    public UIHelper uiHelper;
    
    
    SeekBar rangeSel;

	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.tview1);
        
        mMapView = (MapView)findViewById(R.id.mapview1);
        mMapView.setBuiltInZoomControls(true);
        
        mapControl = mMapView.getController();
        mapOverlays = mMapView.getOverlays();

        mapsOverlays = new MapsOverlayDraw(mMapView);
        mapOvItemMgr = new MapsOverlayItemMgr();
        
        locTrackerList = new ArrayList<ULocTracker>();
        OwnLocTracker = new ULocTracker();
        
        mapsOverlays.MULocPoints = OwnLocTracker.refinedLocP;

        mapOvItemMgr.tfunc1(this.getResources().getDrawable(R.drawable.mark1), (MapView)this.findViewById(R.id.mapview1), OwnLocTracker.geoLocPoint);
        

       ((MeetUp1Activity)getParent()).tabCallback1(this);
        OwnLocTracker.uiHelper = this.uiHelper; //pass down ref
        
        //clientComm = new ClientCommunicationHttp(this.uiHelper.getStServerIp(), 23232);
        
        //netref
        netLocMgr = new UIMapsNetLocMgr(locTrackerList, clientComm, mMapView);
        netLocMgr.uiHelperRef = this.uiHelper; //to be wrapped in constructer/method
        mapOverlays.add(mapsOverlays);
        
        
        btnRelUList = (Button)findViewById(R.id.btnRelUser);
        
        
        
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
            	updateUserData();
            	
            	
            }
        });
        
	}
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void updateOwnLocData(Location newLoc)
	{
		//uiHelper.dispMsg("New Loc: "+newLoc.getLatitude()+" : "+newLoc.getLongitude());
		OwnLocTracker.updateCurrentLoc(newLoc);
		//update down
		//mapsOverlays.updateOverlayItems();
		
		tmpPoint = new GeoPoint((int)(newLoc.getLatitude()*1000000), (int)(newLoc.getLongitude()*1000000));
		mapControl.animateTo(tmpPoint);
		
		mapsOverlays.geoLoc = OwnLocTracker.currentLoc;
		mapsOverlays.updateOverlayItemGeoLoc();
		
		mapOvItemMgr.updateGPoint(tmpPoint, (MapView)this.findViewById(R.id.mapview1));
		
		//Update Online data
		MULocation newMULoc = new MULocation(newLoc.getLatitude(), newLoc.getLongitude(), newLoc.getTime());
		clientComm.setLocation(this.uiHelper.getStOwnUserId(), newMULoc);
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
		ArrayList<MUUser> userList = clientComm.getUserList(this.uiHelper.getStOwnUserId());
		
		this.uiHelper.dispMsg("got userlist with"+userList.size()+" items");
		//do more
		
		//update list
		
		this.netLocMgr.updateUserLoc();
	}

}
