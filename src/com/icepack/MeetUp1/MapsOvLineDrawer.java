package com.icepack.MeetUp1;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Path.FillType;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import com.icepack.MeetUp1.common.MULocation;

public class MapsOvLineDrawer extends Overlay {

	public ArrayList<MULocation> MULocPoints = new ArrayList<MULocation>();
	private ArrayList<Point> locPoints = new ArrayList<Point>();

	private MapView mapView;
	
	private Point tmpPoint;
	private Projection mapProjection;
	private Path dPath1;
	
	private Paint dPaint0;

	
	public MapsOvLineDrawer(MapView mapView) {
		this.mapView = mapView;
		this.tmpPoint = new Point();
		this.dPath1 = new Path();
		
		//set up draw parameters
		dPaint0 = new Paint(Paint.ANTI_ALIAS_FLAG);
        dPaint0.setDither(true);
        dPaint0.setColor(Color.RED);
        dPaint0.setStyle(Paint.Style.STROKE);

        dPaint0.setStrokeJoin(Paint.Join.ROUND);
        dPaint0.setStrokeCap(Paint.Cap.ROUND);
        dPaint0.setStrokeWidth(2);
	}
	
	public void updateLocPoints()
	{
		int itemcount = MULocPoints.size();
		
		this.mapProjection = mapView.getProjection();
		
		locPoints.clear();
		
		for (int i=0; i<itemcount; i++)
		{
			GeoPoint newGeoPoint = MULocPoints.get(i).getGeoLoc();
			mapProjection.toPixels(newGeoPoint, tmpPoint); // sets tmpPoint to GeoPoint Coordinates
			locPoints.add(new Point(tmpPoint));
		}
	}
	
	public void draw(Canvas canvas, MapView mapv, boolean shadow){
		super.draw(canvas, mapv, shadow);
        updateLocPoints();

        int itemcount = locPoints.size();
        
        //cancel on empty list
        if(itemcount==0) return;
        
        //setup path
        dPath1.reset();
        dPath1.setFillType(FillType.EVEN_ODD);
        
        //move cursor to start
        dPath1.moveTo(locPoints.get(0).x, locPoints.get(0).y);
        //move cursor to all points
        for (int i=1; i<itemcount; i++) {
			dPath1.lineTo(locPoints.get(i).x, locPoints.get(i).y);
		}
        
        //move cursor back to start
        //dPath1.moveTo(locPoints.get(0).x, locPoints.get(0).y);
        
        //draw the path
       	canvas.drawPath(dPath1, dPaint0);
	}
	
}
