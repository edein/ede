package com.beehai.iupac;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class LineInfo{
	
	private float x0;
	private float y0;
	private float x1;
	private float y1;
	
	private int lineID;
	
	public static ArrayList<LineInfo> lineArray = new ArrayList<LineInfo>();
	public static int lineCount = 0;
	
	public float[] endPointConnected;
	public float[] startPointConnected;
	
	public LineInfo(){
		
	}

	public LineInfo(Context context) {
		
		// TODO Auto-generated constructor stub
	}

	public void setLineInfo(int ID, float x0, float y0, float x1, float y1)
	{
		this.lineID = ID;
		this.x0=x0;
		this.y0=y0;
		this.x1=x1;
		this.y1=y1;
	}
	
	public float getx0()
	{
		return this.x0;
	}
	public float gety0()
	{
		return this.y0;
	}
	public float getx1()
	{
		return this.x1;
	}
	public float gety1()
	{
		return this.y1;
	}
	public int getID()
	{
		return this.lineID;
	}
	public void setStartPointConnect(float x0, float y0)
	{
		startPointConnected[0] = x0;
		startPointConnected[1] = y0;
	}
	public void setEndPointConnect(float x1, float y1)
	{
		endPointConnected[0] = x1;
		endPointConnected[1] = y1;
	}
	
	public static void fillArray(float x0, float y0, float x1, float y1)
	{
		LineInfo mLineInfo = new LineInfo();
		mLineInfo.setLineInfo(lineCount, x0, y0, x1, y1);
		Log.v("TestView", "line ID: " + lineCount +" "+"x0: "+x0 +" "+ "y0: "+y0+" "+"x1: "+ x1+" "+"y1: "+ y1);
		lineArray.add(mLineInfo);
		lineCount++;
	}
	
	
}
