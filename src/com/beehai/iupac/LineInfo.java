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
	private int groupID;
	
	public int groupIDCount = 0;
	
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
	public int getGroupID()
	{
		return this.groupID;
	}
	
	public void matchCheck(float x0, float y0, float x1, float y1)
	{
		
		int arraySize = lineArray.size();
		boolean match = false;
		int matchGroupID = -1;
		int highestGroupID = -1;//parse through lineArray and get highest groupID for Undo to work properly
		float xTotal= Math.abs((x0-x1));
		float yTotal = Math.abs((y0-y1));
		
		//********************Check if new coords match any point and get match group ID********
		for ( int i=0; i < arraySize; i++)
		{
			LineInfo obj2 = LineInfo.lineArray.get(i);
			float obj2_x0 = obj2.getx0(); float obj2_y0 = obj2.gety0();float obj2_x1 = obj2.getx1(); float obj2_y1 = obj2.gety1();
//			Log.v("", "lineID:" + obj2.getID());
			if (x0==obj2_x0 && y0 == obj2_y0)
			{
//				Log.v("inside 1IF 'matchBefore'", " " +match);
				match = true;
				matchGroupID = obj2.getGroupID();
				Log.v("inside 1IF", " ");break;
//				Log.v("GetName", "lineID(G):"+obj2.getID()+"("+obj2.getGroupID()+")");
//
//				Log.v("inside 1IF 'matchAfter'", " " +match);
			}
			else if (x0==obj2_x1 && y0 == obj2_y1)
			{
				match = true;
				matchGroupID = obj2.getGroupID();
				Log.v("inside 2IF", " ");break;
			}
			else if (x1==obj2_x0 && y1 == obj2_y1)
			{
				match = true;
				matchGroupID = obj2.getGroupID();
				Log.v("inside 3IF", " ");break;
			}
			else if (x1==obj2_x1 && y1 == obj2_y1)
			{
				match = true;	
				matchGroupID = obj2.getGroupID();
				Log.v("inside 4IF", " ");
				break;
			}
			else
			{
				match = false;
				
				Log.v("inside ELSE", " ");
//				Log.v("GetName inside ELSE", "lineID(G):"+obj2.getID()+"("+obj2.getGroupID()+")");
			}
			
			if( obj2.getGroupID() > highestGroupID)
			highestGroupID = obj2.getGroupID();
			
		}
		//**************************************************************************************
		
//		if(xTotal<20 && yTotal < 20)
//		{
//			fillArray(x0,y0,x1,y1,groupIDCount);
//			groupIDCount++;
//		}
//		else 
		if(arraySize>0 && match)
		{	
			fillArray(x0, y0, x1, y1, matchGroupID);
			Log.v("inside match", " ");
		}
		else if(arraySize>0 && !match)
		{
			fillArray(x0, y0, x1, y1, highestGroupID+1);
			groupIDCount++;
			Log.v("inside !match", " ");
		}
		else
		{
		fillArray(x0, y0, x1, y1, highestGroupID+1);
		groupIDCount++;
		}
	}
	
	public void fillArray(float x0, float y0, float x1, float y1, int groupID)
	{
		LineInfo mLineInfo = new LineInfo();
		float xTotal= Math.abs((x0-x1));
		float yTotal = Math.abs((y0-y1));
		if(xTotal<20 && yTotal < 20)
		{
			mLineInfo.groupID = groupID;
			mLineInfo.methaneSet(true);
			mLineInfo.connectedPoint= 0;
			mLineInfo.setLineInfo(lineCount, x0, y0, x0, y0);
			lineArray.add(mLineInfo);
			lineCount++;
		}
		else
		{
			mLineInfo.groupID = groupID;
			mLineInfo.setLineInfo(lineCount, x0, y0, x1, y1);
			lineArray.add(mLineInfo);
			lineCount++;
		}
		
//*************************OLD fillArray Algorithm*********************************
//		//*************Check if its a Methane CH4 ****************
//		float xTotal= Math.abs((x0-x1));
//		float yTotal = Math.abs((y0-y1));
//		//********************************************************
//		if (xTotal <20 && yTotal <20)
//		{
//			mLineInfo.methane= true;
//			mLineInfo.connectedPoint= 0;
//			mLineInfo.setLineInfo(lineCount, x0, y0, x0, y0);
//			lineArray.add(mLineInfo);
//			lineCount++;
//				}
//		else
//		{
//			mLineInfo.setLineInfo(lineCount, x0, y0, x1, y1);
//			lineArray.add(mLineInfo);
//			lineCount++;
//		}
//************************************************************************************
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
	
	public boolean methaneCheck()
	{
		return methane;
	}
	public void methaneSet(boolean b)
	{
		methane = b;
	}
	
	
	
	
}
