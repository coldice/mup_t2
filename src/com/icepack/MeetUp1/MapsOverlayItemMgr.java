package com.icepack.MeetUp1;

import java.util.List;

import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapsOverlayItemMgr {

	public Drawable itemDrawable;
	private List<Overlay> mapOverlays;
	GeoPoint gPoint;
	MapsOverlayItem mapOverlayItems;
	OverlayItem ovItem;
	
	public MapsOverlayItemMgr()
	{
		
	}
	
	public void tfunc1(Drawable iDrawable, MapView mapView, GeoPoint gPoint)
	{
		this.itemDrawable = iDrawable;
		
		mapOverlayItems = new MapsOverlayItem(itemDrawable);
        mapOverlays = mapView.getOverlays();
        this.gPoint = gPoint;
        ovItem = new OverlayItem(this.gPoint, "", "");
        
        mapOverlayItems.addOverlay(ovItem);
        
        mapOverlays.add(mapOverlayItems);
        mapView.postInvalidate();
	}
	
	public void updateGPoint(GeoPoint gPoint, MapView mapView)
	{
		this.gPoint = gPoint;
		ovItem = new OverlayItem(this.gPoint, "", "");
		mapOverlayItems = new MapsOverlayItem(itemDrawable);
		mapOverlayItems.addOverlay(ovItem);
		mapOverlays.set(0, mapOverlayItems);
		//mapOverlays.add(mapOverlayItems);
		mapView.postInvalidate();
		
	}
}
