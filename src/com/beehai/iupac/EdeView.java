package com.beehai.iupac;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class EdeView extends View implements OnGestureListener{

	float x0;
	float y0;
	float x1;
	float y1;
	float xCur;
	float yCur;
	
	float xAmountTranslated =0;
	float yAmountTranslated =0;
	
	static boolean translate = false;
	
	LineInfo lineInfo = new LineInfo();//used to fill in lineArray
	
	Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	Bitmap mBitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888); //place to draw lines onto
	
	Paint paintCloseBy = new Paint(Paint.ANTI_ALIAS_FLAG);

	Canvas cc = new Canvas(mBitmap); //hold lines to draw onto mBitmap
	
	CheckProx checkProx = new CheckProx();
	
	int groupSize = LineInfo.groupVector.size();
	
	int connectedPoint;
	
//	float sx = 1;
//	float sy = 1;
//	float px;
//	float py;
//	float tx;
//	float ty;
	public EdeView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub	
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(3);	
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if(xAmountTranslated == 0 && yAmountTranslated ==0 && !translate ) //check to see if canvas is translated (It is NOT)
		{
		   if (event.getAction() == MotionEvent.ACTION_DOWN) 
		   {
		        x0 = event.getX();
		        y0 = event.getY();
		    	//place proximity checking here***************** set x0,y0 to snap to closest endpoint
				   if(groupSize>=0)
				   {
					   		if(checkProx.closeBy(x0, y0))
							   {
							   		 x0=checkProx.getx0();
							   		 y0=checkProx.gety0();
							   		 
							   		 paintCloseBy.setColor(Color.BLUE);
							   		 paintCloseBy.setAlpha(30);
							   		 paintCloseBy.setStyle(Style.FILL_AND_STROKE);
							   		 cc.drawCircle(x0, y0, 90, paintCloseBy);
							   }
					   		
					}
				//place proximity checking here***************** 
		    }
		   if (event.getAction() == MotionEvent.ACTION_UP) 
			{
		    	x1 = event.getX();
		    	y1 = event.getY();
//		    	place proximity checking here*****************set x1,y1 to snap to closest endpoint
				   if(groupSize>=0)
				   {
					   		if(checkProx.closeBy(x1, y1))
							   	{
							   		 x1=checkProx.getx0();
							   		 y1=checkProx.gety0();
							   	}
					   		
					}
				//place proximity checking here*****************
		    	xCur=0; //reset so Draw current line is correct
		    	lineInfo.matchCheck(x0, y0, x1, y1);
		    	doDraw(); //draw lines into mBitmap
				//Log.v(EdeMain.LOG_TAG, "lineArray Size: " +LineInfo.lineArray.size());
			}
		   if (event.getAction() == MotionEvent.ACTION_MOVE)
		   {
			   xCur = event.getX();
			   yCur = event.getY();
			 //place proximity checking here*****************set xCur,yCur to snap to closest endpoint
			   if(groupSize>=0)
			   {
				   		if(checkProx.closeBy(xCur, yCur))
						   	{
						   		 xCur=checkProx.getx0();
						   		 yCur=checkProx.gety0();
						   		
					   			 paintCloseBy.setColor(Color.LTGRAY);
					   			 paintCloseBy.setStyle(Style.STROKE);
					   			 cc.drawCircle(xCur, yCur, 90, paintCloseBy);
						   		 
						   	}
				}
			  //place proximity checking here*****************
		   }
		   
		   
		   
		}
		else if(!translate && (xAmountTranslated > 0 || yAmountTranslated >0)) //***check to see if canvas is translated (It IS!)
		{
		    	if (event.getAction() == MotionEvent.ACTION_DOWN) 
				{
			        x0 = event.getX() + xAmountTranslated;
			        y0 = event.getY() + yAmountTranslated; 
			      //place proximity checking here*****************

					   if(groupSize>=0)
					   {
						   		if(checkProx.closeBy(x0, y0))
								   {
								   		 x0=checkProx.getx0() ;
								   		 y0=checkProx.gety0();
								   }
						}
				   //place proximity checking here***************** 
			        
			    }
				if (event.getAction() == MotionEvent.ACTION_UP) 
				{
			        x1 = event.getX() +xAmountTranslated;
			        y1 = event.getY() +yAmountTranslated;  
			        
			        //place proximity checking here*****************
					   if(groupSize>=0)
					   {
						   		if(checkProx.closeBy(x1, y1))
								   	{
								   		 x1=checkProx.getx0();
								   		 y1=checkProx.gety0();
								   	}
						}
					//place proximity checking here*****************
					xCur=0;
			        lineInfo.matchCheck(x0, y0, x1, y1);
			        doDraw();
			    }
				
				if (event.getAction() == MotionEvent.ACTION_MOVE)
				   {
					xCur = event.getX() +xAmountTranslated;
					yCur = event.getY() +yAmountTranslated;
					 //place proximity checking here*****************
					   if(groupSize>=0)
					   {
						   		if(checkProx.closeBy(xCur, yCur))
								   	{
								   		 xCur=checkProx.getx0();
								   		 yCur=checkProx.gety0();
								   	}
						}
					  //place proximity checking here*****************   
				   }
		    }
		    else if (translate)
			{
				EdeMain.myDetector.onTouchEvent(event);
			}
		invalidate(); //redraw canvas every time movement is detected.
		return true	;
	}
	
	public void doDraw() //draw lines from lineArray into mBitmap
	{
		cc.drawColor(Color.CYAN); // works as invalidate() since this is custom canvas+bitmap
		Paint textPaint= new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(Color.BLACK);
		
		int groupSize = LineInfo.groupVector.size();
		for (int i=0; i <groupSize; i++)
		{
			int lineSize =LineInfo.groupVector.get(i).size();
			if( lineSize==1)
			{
				LineInfo mLine = (LineInfo) LineInfo.groupVector.get(i).get(0);
				if(mLine.getAtomType()==1)
				{
					cc.drawText(mLine.getAtomTypeByLetter(), mLine.getx0(), mLine.gety0(), textPaint);
				}
			}
			else
			{
				for(int m=0;m < lineSize; m++)
				{
					LineInfo prevLine=null;//*** skip atom if same as prev***********************
					if(m>=1)
					{
						prevLine= LineInfo.groupVector.get(i).get(m-1);
					}//*** skip atom if same as prev***********************
					LineInfo mLine = (LineInfo) LineInfo.groupVector.get(i).get(m);
					LineInfo[] connectedAtoms = mLine.getConnectedAtom();
					if(mLine.bondsWithCounter()>1 )//*** draw double-triple bonds ***********************
					{
						for(int p=0; p< mLine.bondsWithCounter()-1; p++)
						{
							LineInfo bondsWith = mLine.bondsWithGet(p);
							int mRotation;
							if(bondsWith!=null && bondsWith!=prevLine && bondsWith.getID()>mLine.getID())
							{
								if(mLine.getx0()>bondsWith.getx0()){
									 mRotation= 0;
								}else
									 mRotation= 1;
//								Log.v("double-Bond Draw", "mLine ID: "+ mLine.getID());
//								Log.v("double-Bond Draw", "bondsWith ID: "+ bondsWith.getID());
//								float mRatio = bondsWith.gety0()/mLine.gety0();
//								Log.v("ratio", " "+mRatio);
								float[] newCoords = calcParallel(mLine, bondsWith, mRotation);
								cc.drawLine(newCoords[0], newCoords[1], newCoords[2], newCoords[3], paint);
								
							}
						}
					}//*** draw double-triple bonds ***********************
					for(int n=0; n<connectedAtoms.length;n++)
					{
						if(connectedAtoms[n]!=null )
						{
							cc.drawLine(mLine.getx0(), mLine.gety0(), connectedAtoms[n].getx0(),connectedAtoms[n].gety0(), paint);
							if (mLine.getConnectedPoint()==1)
							{
								cc.drawText(mLine.getAtomTypeByLetter(), mLine.getx0(), mLine.gety0(), textPaint);
								//cc.drawText(connectedAtoms[n].getAtomTypeByLetter(), connectedAtoms[n].getx0(), connectedAtoms[n].gety0(), textPaint);	
							}
							if (mLine.getConnectedPoint()==2)
							{	cc.drawText(mLine.getAtomTypeByLetter(), mLine.getx0(), mLine.gety0(), textPaint);
								//cc.drawText(connectedAtoms[n].getAtomTypeByLetter(), connectedAtoms[n].getx0(), connectedAtoms[n].gety0(), textPaint);
							}
							if (mLine.getConnectedPoint()==3)
							{	cc.drawText(mLine.getAtomTypeByLetter(), mLine.getx0(), mLine.gety0(), textPaint);
								//cc.drawText(connectedAtoms[n].getAtomTypeByLetter(), connectedAtoms[n].getx0(), connectedAtoms[n].gety0(), textPaint);
							}
							if (mLine.getConnectedPoint()==4)
							{
								cc.drawText(mLine.getAtomTypeByLetter(), mLine.getx0(), mLine.gety0(), textPaint);
								//cc.drawText(connectedAtoms[n].getAtomTypeByLetter(), connectedAtoms[n].getx0(), connectedAtoms[n].gety0(), textPaint);
							}
//							if (connectedAtoms[n].getConnectedPoint()==1)
//								cc.drawText(mLine.getAtomTypeByLetter(), connectedAtoms[n].getx0(), connectedAtoms[n].gety0(), textPaint);
//							if (connectedAtoms[n].getConnectedPoint()==2)
//								cc.drawText(mLine.getAtomTypeByLetter(), connectedAtoms[n].getx0(), connectedAtoms[n].gety0(), textPaint);
//							if (connectedAtoms[n].getConnectedPoint()==3)
//								cc.drawText(mLine.getAtomTypeByLetter(), connectedAtoms[n].getx0(), connectedAtoms[n].gety0(), textPaint);
//							
							if (connectedAtoms[n].getCycloPoint()[0] >=1)
							{
								cc.drawLine(connectedAtoms[n].getx0(), connectedAtoms[n].gety0(), connectedAtoms[n].getCycloPoint()[0],connectedAtoms[n].getCycloPoint()[1], paint);
							}
//							if (connectedAtoms[n].)
						}
					}
					
				}		
			}	
		}
	}
	
	@Override
	public void onDraw(Canvas c)
	{
		c.drawColor(Color.CYAN);
		if (!translate)
		{
			try
			{ 
				c.drawBitmap(mBitmap, -xAmountTranslated, -yAmountTranslated, paint); //draw mBitmap (which contains all previous lines)
				if(xCur>0) //**draw xyCurrent line (only for visual)
				{
					c.drawLine(x0 -xAmountTranslated, y0 -yAmountTranslated, xCur-xAmountTranslated, yCur-yAmountTranslated, paint);
				}
			} 	catch(Exception e){}	
		}
		else if (translate)
		{
			c.drawBitmap(mBitmap, -xAmountTranslated, -yAmountTranslated, paint);
		}
		
	}
	

		
//place undoBtn here because non-static call	
	public void undo()
	{
		
		int mGroupSize =LineInfo.groupVector.size(); 
		if(mGroupSize>=1)	
		{
			int lineSize= LineInfo.groupVector.get(mGroupSize-1).size();
			//int arraySize = LineInfo.lineVector.size();
			if(lineSize>1)
			{
				LineInfo prevLine = LineInfo.groupVector.get(mGroupSize-1).get(lineSize-1);
				LineInfo[] mConnectedAtom = prevLine.getConnectedAtom();
				int mConnectedLength= mConnectedAtom.length;
				for(int m=0; m < mConnectedLength; m++)
				{	
					if(mConnectedAtom[m]!=null)
					{
						mConnectedAtom[m].getID();
						mConnectedAtom[m].removeConnectedAtom();
						mConnectedAtom[m].removeConnectedPoint();
						if(prevLine!=null)mConnectedAtom[m].bondsWithRemove(prevLine);
						for(int n=0 ; n< mConnectedAtom[m].getCycloPoint().length;n++)
						{
							mConnectedAtom[m].getCycloPoint()[n]=0;
						}
						
					}
				}
				//***remove cycloAt obj
				if(prevLine.getCycloPoint()[0]>0 || prevLine.getCycloPoint()[1]>0)
				LineInfo.cycloAt=null;
				if(LineInfo.cycloAt!=null)
				{
					Log.v("testzzz", "x;" +LineInfo.cycloAt.getID());
				}else{
					Log.v("testzzz", "x;" +LineInfo.cycloAt);
				}
				//***remove cycloAt obj
				LineInfo.groupVector.get(mGroupSize-1).remove(lineSize - 1);
	//			LineInfo prevLine= LineInfo.groupVector.get(mGroupSize-1).get(lineSize-2);
	//			prevLine.removeConnectedAtom();
	//			prevLine.removeConnectedPoint();
				xCur=0;
				LineInfo.lineCount--;
				doDraw(); //call doDraw to invoke cc.drawColor to invalidate();
				invalidate();
			}
			else if(lineSize == 1)
			{
				
				LineInfo.groupVector.get(mGroupSize-1).remove(0);
	//			LineInfo prevLine= LineInfo.groupVector.get(groupSize).get(lineSize-2);
	//			prevLine.removeConnectedAtom();
				//LineInfo.lineVector.remove(0);
				LineInfo.groupVector.remove(mGroupSize-1);
				xCur=0;
				LineInfo.lineCount--;
				doDraw(); //
				invalidate();
			}
		}
	}
	
	public void zoom12(){
//		sx = 1.2f;
//		sy = 1.2f;
//		px = 10;
//		py = 10;
//		invalidate();
	}
	
	
	
	
	
	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		//Log.v("TestView", "BeforeX0: " + xAmountTranslated + " - BeforeY0: " + yAmountTranslated);
		xAmountTranslated+=  distanceX;
		yAmountTranslated+=  distanceY;
		
		
		if (xAmountTranslated > (1000 - getWidth()))
			xAmountTranslated=1000 - getWidth();
		if (yAmountTranslated > (1000 - getHeight()))
			yAmountTranslated=1000 - getHeight();
		if (xAmountTranslated <0)
			xAmountTranslated=0;
		if (yAmountTranslated <0)
			yAmountTranslated=0;
		if (xAmountTranslated >1000)
			xAmountTranslated=1000;
		if (yAmountTranslated >1000)
		yAmountTranslated=1000;
				
		
		//Log.v("TestView", "X0: " + xAmountTranslated + " Y0: " + yAmountTranslated);
		//Log.v("testView", "getWidth()= " +getWidth() + "getHeight(): " + getHeight());
		//Log.v("TestView", "onsCrollX: " + distanceX + " onSchoolY: " + distanceY);
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public float[] calcParallel(LineInfo obj0, LineInfo obj1, int rotation)
	{
		float[] parallelCoords= new float[4];
		LineInfo mAtom0;
		LineInfo mAtom1;
//		if(obj0.getx0()>obj1.getx0())
//		{
//		 mAtom0 =obj1;
//		 mAtom1 =obj0;
//		}else{
			 mAtom0 =obj0;
			 mAtom1 =obj1;	
//		}
		Log.v("mAtom0", "ID"+mAtom0.getID());
		Log.v("mAtom1", "ID"+mAtom1.getID());
		float mAtom0_x= mAtom0.getx0();
		float mAtom0_y= mAtom0.gety0();
		float mAtom1_x= mAtom1.getx0();
		float mAtom1_y= mAtom1.gety0();
		if(LineInfo.cycloAt!=null)
		{
			if(mAtom0_y>mAtom1_y && mAtom0_x<mAtom1_x && rotation==1) //*** arrow up-->right
			{
				Log.v("calcParallel", "Arrow Up-->Right");
				parallelCoords[0]= mAtom0_x+10;
				parallelCoords[1]= mAtom0_y;
				parallelCoords[2]= mAtom1_x;
				parallelCoords[3]= mAtom1_y+10;
			}
			if(mAtom0_y>mAtom1_y && mAtom0_x>mAtom1_x)//*** arrow up--->left
			{
				Log.v("calcParallel", "Arrow Up-->Left");
				parallelCoords[0]= mAtom0_x;
				parallelCoords[1]= mAtom0_y	-10;
				parallelCoords[2]= mAtom1_x+10;
				parallelCoords[3]= mAtom1_y;
			}
			if(mAtom0_y<mAtom1_y && mAtom0_x<mAtom1_x)//**** arrow down-->right
			{
				Log.v("calcParallel", "Arrow Down-->Right");
				parallelCoords[0]= mAtom0_x;
				parallelCoords[1]= mAtom0_y+10;
				parallelCoords[2]= mAtom1_x-10;
				parallelCoords[3]= mAtom1_y;
			}
			if(mAtom0_y<mAtom1_y && mAtom0_x>mAtom1_x && rotation==0)//*** arrow down---Left
			{
				Log.v("calcParallel", "Arrow Down-->Left");
				parallelCoords[0]= mAtom0_x-10;
				parallelCoords[1]= mAtom0_y;
				parallelCoords[2]= mAtom1_x;
				parallelCoords[3]= mAtom1_y-10;
			}
		}else{
		if(mAtom0_y>mAtom1_y && mAtom0_x<mAtom1_x) //*** arrow up-->right
		{
			Log.v("calcParallel", "Arrow Up-->Right");
			parallelCoords[0]= mAtom0_x+10;
			parallelCoords[1]= mAtom0_y;
			parallelCoords[2]= mAtom1_x;
			parallelCoords[3]= mAtom1_y+10;
		}
		if(mAtom0_y>mAtom1_y && mAtom0_x>mAtom1_x)//*** arrow up--->left
		{
			Log.v("calcParallel", "Arrow Up-->Left");
			parallelCoords[0]= mAtom0_x-10;
			parallelCoords[1]= mAtom0_y;
			parallelCoords[2]= mAtom1_x;
			parallelCoords[3]= mAtom1_y+10;
		}
		if(mAtom0_y<mAtom1_y && mAtom0_x<mAtom1_x)//**** arrow down-->right
		{
			Log.v("calcParallel", "Arrow Down-->Right");
			parallelCoords[0]= mAtom0_x+10;
			parallelCoords[1]= mAtom0_y;
			parallelCoords[2]= mAtom1_x;
			parallelCoords[3]= mAtom1_y-10;
		}
		if(mAtom0_y<mAtom1_y && mAtom0_x>mAtom1_x)//*** arrow down---Left
		{
			Log.v("calcParallel", "Arrow Down-->Left");
			parallelCoords[0]= mAtom0_x-10;
			parallelCoords[1]= mAtom0_y;
			parallelCoords[2]= mAtom1_x;
			parallelCoords[3]= mAtom1_y-10;
		}
		}
		return parallelCoords;
	}
}










