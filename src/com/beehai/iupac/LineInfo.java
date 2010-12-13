package com.beehai.iupac;

import java.util.Vector;

import android.content.Context;
import android.util.Log;

public class LineInfo{
	public static final int CARBON = 1;
	public static final int BROMINE= 2;
	
	private float x0;
	private float y0;
	private float x1;
	private float y1;
	
	private int lineID;
	private int groupID;
	private int connectedPoint;
	private LineInfo connectedAtom[]= new LineInfo[4];
	private int connectedAtomCounter=0;
	private boolean methane=false;
	private int atomType=-1;
	private float[] cycloPoint= new float[2];
	private LineInfo[] bondsWith= new LineInfo[3];
	private int bondsCounter;
	private int bond=0;
	
	public int groupIDCount = 0;
	
	public static Vector<Vector<LineInfo>> groupVector = new Vector<Vector<LineInfo>>();
	
	public static int lineCount = 0;
	
	public static int atomCurrent=CARBON;
	public static LineInfo cycloAt=null;
	
	
	private int crawlingStatus=0;
	private boolean crawled=false;
	
	public LineInfo(){
		
	}
	public LineInfo(Context context) {
		// TODO Auto-generated constructor stub
	}
	public void setLineInfo(int ID, float x0, float y0)
	{
		this.lineID = ID;
		this.x0=x0;
		this.y0=y0;
//		this.x1=x1;
//		this.y1=y1;
	}
	public LineInfo bondsWithGet(int x)
	{
		return this.bondsWith[x];
	}
	public void bondsWithSet(LineInfo Atom)
	{
		if(this.bondsWith[0]==null)
			this.bondsWith[0]=Atom;
		else if(this.bondsWith[1]==null)
			this.bondsWith[1]=Atom;
		else
			this.bondsWith[2]=Atom;
	}
	public void bondsWithRemove(LineInfo atom)
	{
		for(int i=0; i<this.bondsWithCounter();i++)
		{
			LineInfo x= this.bondsWith[i];
			if (x==atom)
			{
				this.bondsWith[i]=null;
			}
		}
	}
	public int bondsWithCounter()
	{
		int mCounter=1;
		for(LineInfo x: this.bondsWith)
		{
			if (x!=null)
				mCounter++;
		}
		return mCounter;
	}
	public void removeBond()
	{
		this.bond=this.getBond() - 1;
	}
	public int getBond()
	{
		return this.bond;
	}
	public void setBond(int b)
	{
		//if (this.bond <3)
		this.bond += b;
	}
	public void setCycloPoint(float x, float y)
	{
		this.cycloPoint[0]=x;
		this.cycloPoint[1]=y;
	}
	public float[] getCycloPoint()
	{
		return this.cycloPoint;
	}
	public void setAtomType(int atom)
	{
		this.atomType = atom;
	}
	public int getAtomType()
	{
		return this.atomType;
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
	public void setGroupID(int id)
	{
		this.groupID=id;
	}
	
	public void matchCheck(float x0, float y0, float x1, float y1)
	{
	int groupSize = groupVector.size();
	boolean match = false;
	int matchGroupID = -1;
	int highestGroupID = -1;//parse through lineArray and get highest groupID for Undo to work properly
	boolean mCyclo=false;
	LineInfo mConnectedObj =null;
	int matchEnd=0;
	boolean mBondFormed=false;
	float xTotal= Math.abs((x0-x1));
	float yTotal = Math.abs((y0-y1));
	if(xTotal>15 || yTotal > 15)
	{
		for(int i=0; i<groupSize; i++)
		{
			boolean break2=false; //added break2 so group loop does't keep going (will set match to false)
			int lineSize = groupVector.get(i).size();
			//********************Check if new coords match any point and get match group ID********
			for ( int m=0; m < lineSize; m++)
			{
				LineInfo obj = groupVector.get(i).get(m);
				float obj_x0 = obj.getx0(); float obj_y0 = obj.gety0();

				if (x0==obj_x0 && y0 == obj_y0)
				{
					//***CHECK for double/triple bonds******************
					boolean bondCheckReturn=false;
					LineInfo[] mBondCheck = obj.getConnectedAtom();
					boolean breakBondCheck=false;
					for (int b=0; b<mBondCheck.length;b++)
					{
						if(mBondCheck[b]!=null)
						{
							if(mBondCheck[b].getx0()==x1 && mBondCheck[b].gety0()==y1)
							{
//								obj.setBond(1);
//								obj.setConnectedPoint(1);
//								mBondCheck[b].setBond(1);
								//mBondCheck[b].setConnectedPoint(1);
								obj.bondsWithSet(mBondCheck[b]);
								mBondCheck[b].bondsWithSet(obj);
								mBondFormed=true;
								bondCheckReturn=true;
								match = true;
								breakBondCheck=true;
								break;
							}
						}
					}
					//***********************************
					if(bondCheckReturn==false)
					{
					//**Cyclone/Bonds checker...*****************
						for (int n=0; n< lineSize; n++)
						{
								LineInfo obj2= groupVector.get(i).get(n);
								if(x1==obj2.getx0() && y1==obj2.gety0())
								{
			//						if(!mBondFormed)//**cyclone check
			//						{
									match = true;
									matchGroupID= obj2.getGroupID();
									mCyclo=true;
									Log.v("insideCyClo 1", " "+mCyclo);
									obj.setCycloPoint(obj2.getx0(), obj2.gety0());
									obj2.setCycloPoint(obj.getx0(), obj.gety0());
									obj2.setConnectedPoint(1);
									
									obj.setConnectedAtom(obj2);
									obj2.setConnectedAtom(obj);
									
									obj.setBond(1);
									obj2.setBond(1);
									
									LineInfo.cycloAt=obj; //**set cyclo obj for parser starting point
									
									break2=true;
									break;
			//						}
								}
							
						}
					//***********************************************************
					
					match = true;
					matchEnd= 1;
					matchGroupID = obj.getGroupID();
					obj.setConnectedPoint(1);
					mConnectedObj=obj;
					
					obj.setBond(1);
					//Log.v("inside 1 IF", " ");
					//break2 =true;
					break;
					}
					if (breakBondCheck)
						break;
				}
				else if(x1 == obj_x0 && y1==obj_y0)
				{
					//***CHECK for double/triple bonds***
					boolean bondCheckReturn=false;
					LineInfo[] mBondCheck = obj.getConnectedAtom();
					boolean breakBondCheck=false;
					for (int b=0; b<mBondCheck.length;b++)
					{
						if(mBondCheck[b]!=null)
						{
							if(mBondCheck[b].getx0()==x0 && mBondCheck[b].gety0()==y0)
							{
//								obj.setBond(1);
//								obj.setConnectedPoint(1);
//								mBondCheck[b].setBond(1);
								//mBondCheck[b].setConnectedPoint(1);
								obj.bondsWithSet(mBondCheck[b]);
								mBondCheck[b].bondsWithSet(obj);
								mBondFormed=true;
								bondCheckReturn=true;
								match = true;
								breakBondCheck=true;
								break;
							}
						}
					}
					//***********************************
					if(bondCheckReturn==false)
					{
					//**Cyclone is formed check if x0&y0 match ...*****************added this to continue looping after first match
						for (int n=0; n< lineSize; n++)
						{
							LineInfo obj2= groupVector.get(i).get(n);
							if(x0==obj2.getx0() && y0==obj2.gety0())
							{
								match = true;
								matchGroupID= obj2.getGroupID();
								mCyclo=true;
								//Log.v("mCylco0", " "+mCyclo);
								obj.setCycloPoint(obj2.getx0(), obj2.gety0());
								obj2.setCycloPoint(obj.getx0(), obj.gety0());
								obj2.setConnectedPoint(1);
								
								obj.setConnectedAtom(obj2);
								obj2.setConnectedAtom(obj);
								
								obj.setBond(1);
								obj2.setBond(1);
								
								LineInfo.cycloAt=obj; //**set cyclo obj for parser starting point
								
								break2=true;
								break;
		//						}
							}
						}
					//************************************************************	
					
						//Log.v("eklse if", "lineID:" +obj.getID());
						match = true;
						matchEnd=2;
						matchGroupID = obj.getGroupID();
						obj.setConnectedPoint(1);
						mConnectedObj=obj;
						
						obj.setBond(1);
						break;
					}
					if(breakBondCheck)
						break;
				}
				else
				{
					match = false;
	//				Log.v("GetName inside ELSE", "lineID(G):"+obj2.getID()+"("+obj2.getGroupID()+")");
				}
				
				if( obj.getGroupID() > highestGroupID)
				highestGroupID = obj.getGroupID();
			}
			if(break2)
				break;
			//**************************************************************************************
		}
	}
//		if(xTotal<20 && yTotal < 20)
//		{
//			fillArray(x0,y0,x1,y1,groupIDCount);
//			groupIDCount++;
//		}
//		else 
		if(groupSize>0 && match)
		{	
			if(mCyclo || mBondFormed)
			{
				//Log.v("inside mCyclo [true]", "test");
			}
			else{ 
				fillArray(x0, y0, x1, y1, matchGroupID, match, mConnectedObj, matchEnd);
				//Log.v("inside match else", " ");
				}
			
		}
		else if(groupSize>0 && !match)//*****Feature removed Multiples groups disable**********************************
		{
			//fillArray(x0, y0, x1, y1, highestGroupID+1, match, mConnectedObj,matchEnd);
			//groupIDCount++;
			//Log.v("inside !match", " ");
		}
		else
		{
			fillArray(x0, y0, x1, y1, highestGroupID+1,match,mConnectedObj,matchEnd);
			groupIDCount++;
			//Log.v("inside last ELSE", " ");
		}
	}
	
	public void fillArray(float x0, float y0, float x1, float y1, int groupID, boolean match, LineInfo mConnectedObj, int matchEnd)
	{
		float xTotal= Math.abs((x0-x1));
		float yTotal = Math.abs((y0-y1));
		if(xTotal<20 && yTotal < 20)
		{
			Vector<LineInfo> lineVector = new Vector<LineInfo>();
			LineInfo mLine = new LineInfo();
			mLine.groupID = groupID;
			mLine.setMethane(true);
			mLine.connectedPoint= 0;
			mLine.setAtomType(atomCurrent);
			mLine.setLineInfo(lineCount, x0, y0);
			lineVector.add(mLine);
			groupVector.add(groupID,lineVector);
			lineCount++;
			//Log.v("inside fillArray <20" , " ");
		}
		else
		{
			
			if (!match)
			{
				Vector<LineInfo> lineVector = new Vector<LineInfo>();
				LineInfo mLine = new LineInfo();
				mLine.setGroupID(groupID);
				mLine.setAtomType(atomCurrent);
				mLine.setConnectedPoint(1);
				mLine.setBond(1);
				mLine.setLineInfo(lineCount, x0, y0);
				
				lineVector.add(mLine);
				groupVector.add(groupID, lineVector);
				lineCount++;
				
				LineInfo mLine2 = new LineInfo();
				mLine2.setGroupID(groupID);
				mLine2.setAtomType(atomCurrent);
				mLine2.setConnectedPoint(1);
				mLine2.setBond(1);
				mLine2.setLineInfo(lineCount, x1, y1);
				groupVector.get(groupID).add(mLine2);
				lineCount++;
				
				mLine.setConnectedAtom(mLine2);
				mLine2.setConnectedAtom(mLine);
			}
			else
			{
				LineInfo mLine3 = new LineInfo();
				if (matchEnd==1)
				{
				mLine3.setGroupID(groupID);
				mLine3.setAtomType(atomCurrent);
				mLine3.setLineInfo(lineCount, x1, y1);
				mLine3.setConnectedPoint(1);
				mLine3.setBond(1);
				groupVector.get(groupID).add(mLine3);
				lineCount++;
				}else{
					mLine3.setGroupID(groupID);
					mLine3.setAtomType(atomCurrent);
					mLine3.setLineInfo(lineCount, x0, y0);
					mLine3.setConnectedPoint(1);
					mLine3.setBond(1);
					groupVector.get(groupID).add(mLine3);
					lineCount++;
				}
				
				if(mConnectedObj !=null)
				{
					mLine3.setConnectedAtom(mConnectedObj);
					mConnectedObj.setConnectedAtom(mLine3);
				}
			}
				
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
	public void removeConnectedPoint()
	{
		this.connectedPoint-=1;
	}
	public static void resetConnectedPoint()
	{
//		int arraySize = lineVector.size();
//		for ( int i=0; i < arraySize; i++)
//		{
//		lineVector.get(i).connectedPoint = 0;
//		}
	}
	
	public boolean checkMethane()
	{
		return methane;
	}
	public void setMethane(boolean b)
	{
		methane = b;
	}
	
	public void setConnectedAtom(LineInfo atom)
	{
		if (connectedAtomCounter<=3)
		{
			connectedAtom[connectedAtomCounter]=atom;
			connectedAtomCounter++;
		}
	}
	public LineInfo[] getConnectedAtom()
	{
		return this.connectedAtom;
	}
	public int getConnectedAtomSize()
	{
		int mCounter=0;
		for(int i=0; i<this.connectedAtom.length;i++)
		{
			if (this.connectedAtom[i]!=null)
				mCounter++;
		}
		return mCounter;
	}
	public void removeConnectedAtom()
	{
		this.connectedAtom[this.connectedAtomCounter-1]=null;
		this.connectedAtomCounter--;
	}
	
	public String getAtomTypeByLetter()
	{
		int mAtom =this.atomType;
		int mBond= this.connectedPoint;
		if(this.bondsWithCounter()>=2)
			mBond= (this.connectedPoint-1)+this.bondsWithCounter();
		String atomLetter= "";
		switch(mAtom)
		{
			case 1: 
				switch(mBond)
				{
				case 0: atomLetter = "CH4";break;
				case 1: atomLetter = "CH3";break;
				case 2: atomLetter = "CH2";break;
				case 3: atomLetter = "CH";break;
				case 4: atomLetter = "C";break;
				}break;
			case 2: atomLetter = "Br";break;
		}
		return atomLetter;
	}
  public void crawledSet(boolean x)
  {
	  this.crawled=x;
  }
  public boolean crawledCheck()
  {
	  return this.crawled;
  }
  public void crawlingStatusSet(int status)
  {
	  if (status >this.connectedAtomCounter)
		  this.crawlingStatus=0;
	  else
		  this.crawlingStatus= status;
  }
  public int crawlingStatusGet()
  {
	  return this.crawlingStatus;
  }

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}
