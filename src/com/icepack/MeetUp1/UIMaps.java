package com.icepack.MeetUp1;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class UIMaps extends MapActivity {

	MapView mMapView;
	MapController mapControl;
    Projection projection;
    List<Overlay> mapOverlays;
    Drawable drawable;
    ULocTracker locTracker;
    GeoPoint tmpPoint;
    
    MapsOverlayDraw mapsOverlays;
    MapsOverlayItemMgr mapOvItemMgr;
    
    public UIHelper uiHelper;
    
    
    SeekBar rangeSel;
	/*
    public UIMaps(UIHelper curUIHelper)
	{
		this.uiHelper = curUIHelper;
	}
	*/
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.tview1);
        
        mMapView = (MapView)findViewById(R.id.mapview1);
        mMapView.setBuiltInZoomControls(true);
        
        mapControl = mMapView.getController();
        mapOverlays = mMapView.getOverlays();

        mapsOverlays = new MapsOverlayDraw(mMapView);
        mapOvItemMgr = new MapsOverlayItemMgr();
        
        locTracker = new ULocTracker();
        mapsOverlays.MULocPoints = locTracker.refinedLocP;
//        mapsOverlays.geoLoc = locTracker.currentLoc;
//        mapsOverlays.setItemDrawable(this.getResources().getDrawable(R.drawable.mark1));
        mapOvItemMgr.tfunc1(this.getResources().getDrawable(R.drawable.mark1), (MapView)this.findViewById(R.id.mapview1), locTracker.geoLocPoint);
        
        /*
        Activity parActivity = getParent();
        TabActivity partTabActivity = (TabActivity) parActivity;
        MeetUp1Activity mainRef = (MeetUp1Activity) partTabActivity;
        mainRef.tabCallback1(this);
        */
        
//        TextView statView = (TextView)findViewById(R.id.statText1);
//        statView.setText("blabla");
        
       ((MeetUp1Activity)getParent()).tabCallback1(this);
        locTracker.uiHelper = this.uiHelper; //pass down ref
        
        /*
        Location newLoc1 = new Location(LOCATION_SERVICE);
        Location newLoc2 = new Location(LOCATION_SERVICE);
        Location newLoc3 = new Location(LOCATION_SERVICE);
        newLoc1.setLatitude(19.240000);
        newLoc1.setLongitude(-99.120000);
        newLoc2.setLatitude(50.410000);
        newLoc2.setLongitude(-97.946000);
        newLoc3.setLatitude(30.410000);
        newLoc3.setLongitude(-80.946000);
        locTracker.updateCurrentLoc(newLoc1);
        locTracker.updateCurrentLoc(newLoc2);
        locTracker.updateCurrentLoc(newLoc3);
        */
        
        mapOverlays.add(mapsOverlays);
        
        //GeoPoint animp = new GeoPoint((int)newLoc1.getLatitude()*1000000, (int)newLoc1.getLongitude()*1000000);
        
        //mapControl.animateTo(animp);
        
        //ui listeners
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
				locTracker.minPointDistance = selProg;
				uiHelper.setSecMsg("|pc:"+locTracker.refinedLocP.size()+" |minD:"+(int)locTracker.minPointDistance+" |lastD:"+(int)locTracker.lastPointDistance+"  ");
			}
		};
        
        rangeSel.setOnSeekBarChangeListener(seekChangeListener);

        
	}
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void updateLocData(Location newLoc)
	{
		//uiHelper.dispMsg("New Loc: "+newLoc.getLatitude()+" : "+newLoc.getLongitude());
		locTracker.updateCurrentLoc(newLoc);
		//update down
		//mapsOverlays.updateOverlayItems();
		
		tmpPoint = new GeoPoint((int)(newLoc.getLatitude()*1000000), (int)(newLoc.getLongitude()*1000000));
		mapControl.animateTo(tmpPoint);
		
		mapsOverlays.geoLoc = locTracker.currentLoc;
		mapsOverlays.updateOverlayItemGeoLoc();
		
		mapOvItemMgr.updateGPoint(tmpPoint, (MapView)this.findViewById(R.id.mapview1));

	}
	
	public void updateMapViewSet()
	{
		//tmpPoint = new GeoPoint((int)locTracker.currentLoc.getLatitude()*1000000, (int)locTracker.currentLoc.getLongitude()*1000000);
	}

}
