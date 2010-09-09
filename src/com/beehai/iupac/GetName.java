package com.beehai.iupac;

import android.util.Log;

public class GetName {

	CharSequence name;
	
	public GetName ()
	{
		//***parse lineArray and for connectedPoint
		int arraySize = LineInfo.lineArray.size();
		if (arraySize >=1)
		{
			for ( int i=0; i < arraySize; i++)
				{
					LineInfo obj1 = LineInfo.lineArray.get(i);
					float obj1_1 = obj1.getx0() +obj1.gety0();
					float obj1_2 = obj1.getx1()+obj1.gety1();
					//Log.v("", "obj1_1 total: " + obj1_1);
					
					int insideCounter = i + 1;
						for ( int m= insideCounter; m <arraySize; m++)
						{
						LineInfo obj2 = LineInfo.lineArray.get(m);
						float obj2_1 = obj2.getx0() +obj2.gety0();
						float obj2_2 = obj2.getx1()+obj2.gety1();
						
						//Log.v("", "obj2_1 total: " + obj2_1);
						Log.v("test", "obj1 ID: " + obj1.getID() + " Obj2 ID: " + obj2.getID());
							if (obj1_1==obj2_1 || obj1_1 == obj2_2)
							{
								obj1.setConnectedPoint(1);
								obj2.setConnectedPoint(1);
								//Log.v("test", "connected Points: " + obj1.getConnectedPoint());
							}
							if (obj1_2==obj2_1 || obj1_2 == obj2_2)
							{
								obj1.setConnectedPoint(1);
								obj2.setConnectedPoint(1);
							}
						}	
					//Log.v("", "insideCountner: " + insideCounter);
				}
		}
		//********************
		//*******algorithm
		int connectedPointCount = 0;
		for ( int i=0; i < arraySize; i++)
			{
				int points= LineInfo.lineArray.get(i).getConnectedPoint();
				int id = LineInfo.lineArray.get(i).getID();
				Log.v("Test", "Line ID: " + id + " Connected Points: " + points);
				if (points ==	2)
				{
					connectedPointCount++;
				}
			}
		//int myCase = arraySize - connectedPointCount;
		Log.v("", "connectedPointCount: " +connectedPointCount);
		switch(connectedPointCount)
		{
		case 0 : name = "Methane"; break;
		case 1 : name = "Ethane";break;
		case 2 : name = "Propane";break;
		}
		//**********************
	}
	
	public void setName(CharSequence mName)
	{
		name = mName;
		return;
	}
	
	public CharSequence name()
	{
		
		return name;
	}
	
}
