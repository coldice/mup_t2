package com.icepack.MeetUp1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MapTestOverlay extends Overlay {

	Paint mPaint;
	Path path;
	Projection projection;
	
	public MapTestOverlay() {
		// TODO Auto-generated constructor stub
	}

	public void draw(Canvas canvas, MapView mapv, boolean shadow){
        super.draw(canvas, mapv, shadow);

        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(2);

        GeoPoint gP1 = new GeoPoint(19240000,-99120000);
        GeoPoint gP2 = new GeoPoint(35410000, 139460000);

        Point p1 = new Point();
        Point p2 = new Point();

        path = new Path();

        projection.toPixels(gP1, p1);
        projection.toPixels(gP2, p2);

        path.moveTo(p2.x, p2.y);
        path.lineTo(p1.x,p1.y);

        canvas.drawPath(path, mPaint);
	}
}
