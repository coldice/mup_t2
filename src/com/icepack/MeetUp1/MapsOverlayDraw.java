package com.icepack.MeetUp1;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import com.icepack.MeetUp1.common.MULocation;

public class MapsOverlayDraw extends Overlay {

	public ArrayList<MULocation> MULocPoints = new ArrayList<MULocation>();
	private ArrayList<Point> locPoints = new ArrayList<Point>();

	private Path dPath1;
	private MapView mapView;
	
	private Point tmpPoint;

	private Projection mapProjection;
	
	//private ArrayList<MapsOverlayItem> mapOverlayItems = new ArrayList<MapsOverlayItem>();
	private MapsOverlayItem mapOverlayItems;
	private OverlayItem ovIGeoLoc;
	public MULocation geoLoc;
	private GeoPoint geoLocPoint;
	private ArrayList<GeoPoint>geoLocPoints;
	
	private Drawable itemDrawable;
	private List<Overlay> mapOverlays;
	
	public int sel_col = 0;
	
	Paint dPaint0;
	Paint dPaint1;
	Paint dPaint2;
	Paint dPaint3;
	
	public MapsOverlayDraw(MapView mapView) {
		//this.mapProjection = mapsViewProjection;
		this.mapView = mapView;
		this.tmpPoint = new Point();
		this.dPath1 = new Path();
		
		this.mapProjection = mapView.getProjection();
		//set up draw parameters
		dPaint0 = new Paint(Paint.ANTI_ALIAS_FLAG);
        dPaint0.setDither(true);
        dPaint0.setColor(Color.RED);
        dPaint0.setStyle(Paint.Style.STROKE);

        dPaint0.setStrokeJoin(Paint.Join.ROUND);
        dPaint0.setStrokeCap(Paint.Cap.ROUND);
        dPaint0.setStrokeWidth(2);

        dPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        dPaint1.setDither(true);
        dPaint1.setColor(Color.GREEN);
        dPaint1.setStyle(Paint.Style.STROKE);

        dPaint1.setStrokeJoin(Paint.Join.ROUND);
        dPaint1.setStrokeCap(Paint.Cap.ROUND);
        dPaint1.setStrokeWidth(2);

        
        dPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        dPaint2.setDither(true);
        dPaint2.setColor(Color.BLUE);
        dPaint2.setStyle(Paint.Style.STROKE);

        dPaint2.setStrokeJoin(Paint.Join.ROUND);
        dPaint2.setStrokeCap(Paint.Cap.ROUND);
        dPaint2.setStrokeWidth(2);
        
        dPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        dPaint3.setDither(true);
        dPaint3.setColor(Color.YELLOW);
        dPaint3.setStyle(Paint.Style.STROKE);

        dPaint3.setStrokeJoin(Paint.Join.ROUND);
        dPaint3.setStrokeCap(Paint.Cap.ROUND);
        dPaint3.setStrokeWidth(2);
        
        //overlay items
        //drawable = getP .getResources().getDrawable(R.drawable.mark1);
        
        
        /*locGeoPoints.add(new GeoPoint(19240000,-99120000));
        locGeoPoints.add(new GeoPoint(35410000, 139460000));
        locGeoPoints.add(new GeoPoint(25452000, 120460123));
        locGeoPoints.add(new GeoPoint(30000000, 109461234));
        locGeoPoints.add(new GeoPoint(10500000, 129478912));
        updateLocPoints(); */
	}
	
	public void setItemDrawable(Drawable iDrawable)
	{
		this.itemDrawable = iDrawable;
		
		mapOverlayItems = new MapsOverlayItem(itemDrawable);
        mapOverlays = mapView.getOverlays();
        geoLocPoint = new GeoPoint(19240000, -99120000);
        //geoLoc = new GeoPoint(19240000, -99120000);
        ovIGeoLoc = new OverlayItem(geoLocPoint, "", "");
        
        mapOverlayItems.addOverlay(ovIGeoLoc);
        
        
        //mapOverlays.add(mapOverlayItems);
	}
	
	public void updateLocPoints()
	{
		int itemcount = MULocPoints.size();
		
		this.mapProjection = mapView.getProjection();
		
		locPoints.clear();
		
		for (int i=0; i<itemcount; i++)
		{
			GeoPoint newGeoPoint = new GeoPoint((int)(MULocPoints.get(i).latitude*1000000), (int)(MULocPoints.get(i).longitude*1000000));
			mapProjection.toPixels(newGeoPoint, tmpPoint); // sets tmpPoint to GeoPoint Coordinates
			locPoints.add(new Point(tmpPoint));
		}
		
		/*
		if(geoLoc!=null)
		{
			geoLocPoint = new GeoPoint((int)(this.geoLoc.getLatitude()*1000000), (int)(this.geoLoc.getLongitude()*1000000));
		}
		*/
	}
	
	public void draw(Canvas canvas, MapView mapv, boolean shadow){
        super.draw(canvas, mapv, shadow);
        updateLocPoints();

        int itemcount = locPoints.size();
        
        dPath1.reset();
        dPath1.setFillType(FillType.EVEN_ODD);
        if(itemcount==0) return;
        
        dPath1.moveTo(locPoints.get(0).x, locPoints.get(0).y);
        for (int i=1; i<itemcount; i++) {
			dPath1.lineTo(locPoints.get(i).x, locPoints.get(i).y);
		}

        dPath1.moveTo(locPoints.get(0).x, locPoints.get(0).y);

        if (this.sel_col==0) {
        	canvas.drawPath(dPath1, dPaint0);
        }
        else if(this.sel_col==1) {
        	canvas.drawPath(dPath1, dPaint1);
        } else if(this.sel_col==2)
        {
        	canvas.drawPath(dPath1, dPaint2);
        } else {
        	canvas.drawPath(dPath1, dPaint3);
        }
        
        
        
	}
	
	public void updateOverlayItems()
	{
		//mapOverlayItems.clear();
		
		//GeoPoint point = new GeoPoint(19240000,-99120000);
		/*
		if(locGeoPoints.size()>=1)
		{
			ovIGeoLoc = new OverlayItem(locGeoPoints.get(locGeoPoints.size()-1), "", "");
		}
		*/
	}
	
	public void updateOverlayItemGeoLoc()
	{
		if(geoLoc!=null)
		{
			geoLocPoint = new GeoPoint((int)(this.geoLoc.latitude*1000000), (int)(this.geoLoc.latitude*1000000));
		}
	}
	

}
