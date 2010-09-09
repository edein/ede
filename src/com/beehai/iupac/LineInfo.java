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
	
	private int connectedPoint;
	
	private boolean methane=false;
	
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
	public void fillArray(float x0, float y0, float x1, float y1)
	{
		LineInfo mLineInfo = new LineInfo();
		//***Check if its a Methane CH4
		float xTotal= Math.abs((x0-x1));
		float yTotal = Math.abs((y0-y1));
		//******************************
		if (xTotal <20 && yTotal <20)
		{
			mLineInfo.methane= true;
			mLineInfo.connectedPoint= 0;
			mLineInfo.setLineInfo(lineCount, x0, y0, x0, y0);
			lineArray.add(mLineInfo);
			lineCount++;
			Log.v("TestView", "Methane line ID: " + lineCount +" "+"x0: "+x0 +" "+ "y0: "+y0+" "+"x1: "+ x1+" "+"y1: "+ y1);
		}
		else
		{
			mLineInfo.setLineInfo(lineCount, x0, y0, x1, y1);
			Log.v("TestView", "line ID: " + lineCount +" "+"x0: "+x0 +" "+ "y0: "+y0+" "+"x1: "+ x1+" "+"y1: "+ y1);
			lineArray.add(mLineInfo);
			lineCount++;
		}
	}
	
	public void setConnectedPoint(int point)
	{
		connectedPoint += point;
		return;
	}
	public int getConnectedPoint()
	{
		return connectedPoint;
	}
	public static void resetConnectedPoint()
	{
		int arraySize = lineArray.size();
		for ( int i=0; i < arraySize; i++)
		{
		lineArray.get(i).connectedPoint = 0;
		}
	}
	
	public boolean checkMethane()
	{
		return methane;
	}
	
}
