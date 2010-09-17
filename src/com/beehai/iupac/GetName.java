package com.beehai.iupac;

import java.util.Enumeration;

import android.util.Log;

public class GetName {

	CharSequence name;
	
	public GetName ()
	{
	//	int arraySize = LineInfo.lineVector.size();
//		*************parse lineArray and for connectedPoint*********************************************
//			if (arraySize >=1)
//			{
//				for ( int i=0; i < arraySize; i++)
//					{
//						LineInfo obj1 = LineInfo.lineArray.get(i);
////						float obj1_1 = obj1.getx0() ;	
////						float obj1_2 = obj1.gety0();
//						//Log.v("", "obj1_1 total: " + obj1_1);
//						
//						int insideCounter = i + 1;
//							for ( int m= insideCounter; m <arraySize; m++)
//							{
//							LineInfo obj2 = LineInfo.lineArray.get(m);
//								if (obj1.getGroupID() == obj2.getGroupID())
//								{
//									obj1.setConnectedPoint(1);
//								}
//							float obj2_1 = obj2.getx0();
//							float obj2_2 = obj2.gety0();
							//Log.v("", "obj2_1 total: " + obj2_1);
							//Log.v("GetName", "obj1 ID: " + obj1.getID() + " Obj2 ID: " + obj2.getID());
//								if (obj1_1==obj2_1 || obj1_1 == obj2_2)
//								{
//									obj1.setConnectedPoint(1);
//									obj2.setConnectedPoint(1);
//									//Log.v("test", "connected Points: " + obj1.getConnectedPoint());
//								}
//								if (obj1_2==obj2_1 || obj1_2 == obj2_2)
//								{
//									obj1.setConnectedPoint(1);
//									obj2.setConnectedPoint(1);
//								}
//							}	
						//Log.v("", "insideCountner: " + insideCounter);
//					}
//			}
			//********************
		

		//*******algorithm
		int groupSize = LineInfo.groupVector.size();
		int carbonCount=0;
		for(int i=0; i<groupSize;i++)
		{
			int lineSize = LineInfo.groupVector.get(i).size();
			for(int m=0; m<lineSize;m++)
			{
				LineInfo mLine = LineInfo.groupVector.get(i).get(m);
				if(mLine.getAtomType()==LineInfo.CARBON)
					carbonCount++;
			}
		
		}
		switch(carbonCount)
		{
		case 1 : name = "Methane"; break;
		case 2 : name = "Ethane";break;
		case 3 : name = "Propane";break;
		case 4 : name = "Butane";break;
		case 5 : name = "Pentane";break;
		case 6 : name = "Hexane";break;
		case 7 : name = "Heptane";break;
		case 8 : name = "Octane";break;
		case 9 : name = "Nonane";break;
		case 10 : name = "Decane";break;
		}
		//**********************
		//*******LOG INFO SECTION*************
		for(int i=0; i < LineInfo.groupVector.size(); i++)
		{
			//Log.v("groupVectorSizez", " "+LineInfo.groupVector.size());
			for(int m=0; m < LineInfo.groupVector.get(i).size(); m++)
			{
//				Log.v("lineVectorSizez", " "+LineInfo.groupVector.get(i).size());
				LineInfo mLine = LineInfo.groupVector.get(i).get(m);
				LineInfo[] atom = mLine.getConnectedAtom();
				
				int[] atomType = {-1,-1,-1,-1};
				for (int z=0; z<atom.length; z++)
				{
					if(atom[z] !=null)
					atomType[z]=atom[z].getAtomType();
				}
				Log.v("LOG_GetName", "groupID:"+mLine.getGroupID()+ 
									 " lineID:"+ mLine.getID()+
									 " ConnectedPoints:"+ mLine.getConnectedPoint()+
									 " connctedAtomType:"+atomType[0]+atomType[1]+atomType[2]+atomType[3] );
				
			}
		}
		//***********************************
		
		
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
