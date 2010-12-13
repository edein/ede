package com.beehai.iupac;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

import android.util.Log;

public class GetName {

	String name;
	Vector<Vector<LineInfo>> branches= new Vector<Vector<LineInfo>>();
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
		boolean mCyclo=false;
		int mBond=0;
		for(int i=0; i<groupSize;i++)
		{
			int lineSize = LineInfo.groupVector.get(i).size();
			for(int m=0; m<lineSize;m++)
			{
				LineInfo mLine = LineInfo.groupVector.get(i).get(m);
				if(mLine.getAtomType()==LineInfo.CARBON)
					carbonCount++;
				if(mLine.getCycloPoint()[0]>1)
					mCyclo=true;
				if(mLine.getBond()>0)
					mBond=mLine.getBond();
			}
		
		}
		switch(carbonCount)
		{
		case 1 : name = "Meth"; break;case 2 : name = "Eth";break;case 3 : name = "Prop";break;
		case 4 : name = "But";break;case 5 : name = "Pent";break;case 6 : name = "Hex";break;
		case 7 : name = "Hept";break;case 8 : name = "Oct";break;	case 9 : name = "Non";break;
		case 10 : name = "Dec";break;case 11 : name = "Undec";break;case 12 : name = "Dodec";break;
		case 13 : name = "Tridec";break;case 14 : name = "Tetradec";break;case 15 : name = "Pentadec";break;
		case 16 : name = "Hexadec";break;case 17 : name = "Heptadec";break;case 18 : name = "Octadec";break;
		case 19 : name = "Nonadec";break;case 20 : name = "Eicos";break;case 21 : name = "Heneicos";break;
		case 22 : name = "Docos";break;case 23 : name = "Tricos";break;case 24 : name = "Tetracos";break;
		case 25 : name = "Pentacos";break;case 26 : name = "Hexacos";break;case 27 : name = "Heptacos";break;
		case 28 : name = "Octacos";break;case 29 : name = "Nonacos";break;case 30 : name = "Triacont";break;
		case 31 : name = "Hentriacont";break;case 32 : name = "Dotriacont";break;case 33 : name = "Tritriacont";break;
		case 34 : name = "Tetratriacont";break;case 35 : name = "Pentatriacont";break;case 36 : name = "Hexatriacont";break;
		
		}
		if(mCyclo)
		{
			String cyclo= "Cyclo";
			name =name.toLowerCase();
			name = cyclo.concat(name);
		}
		switch(mBond)
		{
			case 1:	String ane = "ane";name = name.concat(ane);break;	
			case 2: String ene = "ene";name = name.concat(ene);break;
			case 3: String yne = "yne";name = name.concat(yne);break;
		}
		//**********************
		//*******LOG INFO SECTION*************
		int branchCounter=0;		
		if(LineInfo.cycloAt!=null)//** cyclo parser ************
		{
//			LineInfo startAtom = LineInfo.cycloAt;
//			LineInfo currentAtom1=null;
//			LineInfo nextAtom1=null;
//			Vector<LineInfo> branchMembers=null;
//			do
//			{
//				
//				
//			}while(true);//** cyclo parser ************
		}else
		{
			for(int i=0; i < LineInfo.groupVector.size(); i++)
			{
				//Log.v("groupVectorSizez", " "+LineInfo.groupVector.size());
				for(int m=0; m < LineInfo.groupVector.get(i).size(); m++)
				{	
					LineInfo startAtom = LineInfo.groupVector.get(i).get(m);
					if(startAtom.getConnectedPoint()==1)
					{
						LineInfo currentAtom1=null;
						LineInfo nextAtom1=null;
						Vector<LineInfo> branchMembers=null;
						boolean restartSameBranch =false;
						int mNewBranch=0;
						do
						{
							if(branchMembers==null || mNewBranch == 1)
								branchMembers= new Vector<LineInfo>();
							
							if(currentAtom1==null && nextAtom1==null)
							{
								startAtom.crawledSet(true);
								branchMembers.add(startAtom);
								LineInfo[] mAtoms= startAtom.getConnectedAtom();
								currentAtom1= mAtoms[0];                                                                                                                                                                
								mNewBranch=0;
							}else
							{
								if(nextAtom1!=null)//set next Atom
								{ 
									currentAtom1 = nextAtom1;
									if(restartSameBranch==true)
									{
										branchMembers.add(startAtom);
										currentAtom1=startAtom.getConnectedAtom()[0];
										restartSameBranch=false;
									}
								} 
								LineInfo[] mAtoms = currentAtom1.getConnectedAtom();
								
								if(currentAtom1.getConnectedAtomSize()==2)
								{
									branchMembers.add(currentAtom1);//**prevent error when parsing from other ends
									if(mAtoms[0].getID()==branchMembers.get(branchMembers.size()-2).getID())
									{
										//nextAtom1.crawledStatusSet(nextAtom1.crawledStatusGet()+1);
										currentAtom1.crawledSet(true);
										nextAtom1= mAtoms[1];//prevent error when parsing from different ends**
									}else
									{
										//nextAtom1.crawledStatusSet(nextAtom1.crawledStatusGet()+1);
										currentAtom1.crawledSet(true);
										nextAtom1= mAtoms[0];
									}								
									mNewBranch =0;
								}		
								
								if(currentAtom1.getConnectedAtomSize()==3)
								{
									if(currentAtom1.crawlingStatusGet()==0)
									{
										branchMembers.add(currentAtom1);
										if(mAtoms[0].getID()==branchMembers.get(branchMembers.size()-2).getID())
										{
											nextAtom1= mAtoms[1];
										}else if(mAtoms[1].getID()==branchMembers.get(branchMembers.size()-2).getID())
										{
											nextAtom1= mAtoms[0];
										}else if(mAtoms[2].getID()==branchMembers.get(branchMembers.size()-2).getID())
										{
											nextAtom1= mAtoms[0];	
										}
										currentAtom1.crawledSet(true);
										currentAtom1.crawlingStatusSet(currentAtom1.crawlingStatusGet()+1);
										mNewBranch=0;
									}
									else if(currentAtom1.crawlingStatusGet()==1)
									{
										branchMembers.add(currentAtom1);
										if(mAtoms[0].getID()==branchMembers.get(branchMembers.size()-2).getID())
										{
											nextAtom1= mAtoms[2];
										}else if(mAtoms[1].getID()==branchMembers.get(branchMembers.size()-2).getID())
										{
											nextAtom1= mAtoms[2];
										}else if(mAtoms[2].getID()==branchMembers.get(branchMembers.size()-2).getID())
										{
											nextAtom1= mAtoms[1];	
										}
										currentAtom1.crawlingStatusSet(0);	
										currentAtom1.crawledSet(true);
									}
									mNewBranch=0;
								}
								if(currentAtom1.getConnectedAtomSize()==4)
								{
									if(currentAtom1.crawlingStatusGet()==0)
									{
										branchMembers.add(currentAtom1);
										if(mAtoms[0].getID()==branchMembers.get(branchMembers.size()-2).getID())
										{
											nextAtom1= mAtoms[1];
										}else if(mAtoms[1].getID()==branchMembers.get(branchMembers.size()-2).getID())
										{
											nextAtom1= mAtoms[0];
										}else if(mAtoms[2].getID()==branchMembers.get(branchMembers.size()-2).getID())
										{
											nextAtom1= mAtoms[0];	
										}
										currentAtom1.crawlingStatusSet(currentAtom1.crawlingStatusGet()+1);
										currentAtom1.crawledSet(true);
										mNewBranch=0;
									}
									if(currentAtom1.crawlingStatusGet()==1)
									{
										branchMembers.add(currentAtom1);
										if(mAtoms[0].getID()==branchMembers.get(branchMembers.size()-2).getID())
										{
											nextAtom1= mAtoms[2];
										}else if(mAtoms[1].getID()==branchMembers.get(branchMembers.size()-2).getID())
										{
											nextAtom1= mAtoms[2];
										}else if(mAtoms[2].getID()==branchMembers.get(branchMembers.size()-2).getID())
										{
											nextAtom1= mAtoms[1];	
										}
										currentAtom1.crawlingStatusSet(currentAtom1.crawlingStatusGet()+1);
										currentAtom1.crawledSet(true);
										mNewBranch=0;
									}
									if(currentAtom1.crawlingStatusGet()==2)
									{
										branchMembers.add(currentAtom1);
										if(mAtoms[0].getID()==branchMembers.get(branchMembers.size()-2).getID())
										{
											nextAtom1= mAtoms[3];
										}else if(mAtoms[1].getID()==branchMembers.get(branchMembers.size()-2).getID())
										{
											nextAtom1= mAtoms[3];
										}else if(mAtoms[2].getID()==branchMembers.get(branchMembers.size()-2).getID())
										{
											nextAtom1= mAtoms[3];	
										}
										currentAtom1.crawlingStatusSet(0);
										currentAtom1.crawledSet(true);
										mNewBranch=0;
									}
								}
								if(currentAtom1.getConnectedAtomSize()==1)
								{
									currentAtom1.crawledSet(true);
									branchMembers.add(currentAtom1);					
									if(branches.size()==0)
									{
										branches.add(branchMembers);
										branchCounter++;
										mNewBranch=1;
									}else{
										if(branchMembers.size()>branches.lastElement().size())
										{
											branches.remove(branches.size()-1);
											branches.add(branchMembers);
											branchCounter++;
											mNewBranch=1;
										}
										if(branchMembers.size()==branches.lastElement().size())
										{
											branches.add(branchMembers);
											branchCounter++;
											mNewBranch=1;
										}
										if(branchMembers.size()<branches.lastElement().size())
										{
											branchMembers.clear();
										}
									}
									
									
									if(unCrawlAtoms().size()>=1)
									{
									restartSameBranch=true;
									}
									
									if(unCrawlAtoms().size()==0)
									{
										crawlReset();
										restartSameBranch=false;
										break;
									}
								}
								
	//							if(currentAtom1.getConnectedAtomSize()==1 && unCrawlAtoms().size()==0)
	//							{
	//								crawlReset();
	//								break;
	//							}
							}
						}while(unCrawlAtoms().size()!=0);
						
			
						
					}
	//					Log.v("LOGINFO", "connectedAtom:" +mLine.getConnectedAtomSize());
				
			
	//				Log.v("lineVectorSizez", " "+LineInfo.groupVector.get(i).size());
	//				LineInfo mLine = LineInfo.groupVector.get(i).get(m);
	//				LineInfo[] atom = mLine.getConnectedAtom();
	//				
	//				int[] atomType = {-1,-1,-1,-1};
	//				for (int z=0; z<atom.length; z++)
	//				{
	//					if(atom[z] !=null)
	//					atomType[z]=atom[z].getAtomType();
	//				}
	//				Log.v("LOG_GetName", "groupSize:"+groupSize+
	//									 " groupID:"+mLine.getGroupID()+ 
	//									 " lineID:"+ mLine.getID()+
	//									 " ConnectedPoints:"+ mLine.getConnectedPoint()+
	//									 " connctedAtomType:"+atomType[0]+atomType[1]+atomType[2]+atomType[3]+
	//									 " Bonds:"+mLine.getBond());
				
					
				}
				Log.v("test", "test");
				//branchesCleanup();
			}
		}
		//***********************************
		
		
	}
	
	public void branchesCleanup()
	{
		//***get longest chain number....not in order******
		//Vector<Vector<LineInfo>> tempVector = new Vector<Vector<LineInfo>>();
		int branchesSize = branches.size()-1;
		for(int i=0; i < branchesSize; i++)
		{
			Vector<LineInfo> currentGroup= branches.get(i);
			int groupSize= currentGroup.size();
			for (int i2=i+1; i2<branchesSize;i2++)
			{
				Vector<LineInfo> nextGroup= branches.get(i2);
				for(int m=0; m<currentGroup.size(); m++)
				{
					if(currentGroup.get(m)==nextGroup.get(groupSize-(m-1)))
					{
//						matchCounter++;
					}
				}
			}
		}
		//***get longest chain number....not in order******
	}
	
	public Vector<LineInfo> subsParser( Vector<LineInfo> branch)
	{
		
		return branch;
	}
	
	public void setName(CharSequence mName)
	{
		name = mName.toString();
		return;
	}
	
	public CharSequence name()
	{
		
		return name;
	}
	
	public Vector<LineInfo> unCrawlAtoms()
	{
		Vector<LineInfo> unCrawlAtoms= new Vector<LineInfo>();
		for(int i=0; i < LineInfo.groupVector.size(); i++)
		{
			//Log.v("groupVectorSizez", " "+LineInfo.groupVector.size());
			int mCounter =0;
			for(int m=0; m < LineInfo.groupVector.get(i).size(); m++)
			{	
				LineInfo mAtom = LineInfo.groupVector.get(i).get(m);
				if(mAtom.crawledCheck() == false)
				{
					unCrawlAtoms.add(mAtom);
					mCounter++;
				}
			}
		}
		return unCrawlAtoms;
		
	}
	public void crawlReset()
	{
		for(int i=0; i < LineInfo.groupVector.size(); i++)
		{
			for(int m=0; m < LineInfo.groupVector.get(i).size(); m++)
			{	
				LineInfo mAtom = LineInfo.groupVector.get(i).get(m);
				mAtom.crawledSet(false);
				mAtom.crawlingStatusSet(0);
			}
		}
	}
	
}




// (3,11) - (4,8) - (5,15) - 8,10 
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

