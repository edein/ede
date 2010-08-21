package com.beehai.iupac;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class EdeView extends View{
	
	Paint mPaint;
	
	private int lineID = 0;
	public static ArrayList<LineInfo> lineArray = new ArrayList<LineInfo>();
	
	float mx0 = 0;
	float my0 = 0;
	float mx1 = 0;
	float my1 = 0;
	float xCur;
	float yCur;
	
	float sx = 1;
	float sy = 1;
	float px;
	float py;

	public EdeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub		
		mPaint= new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.WHITE);
		mPaint.setStrokeWidth(3);
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		
		

		   if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        mx0 = event.getX();
		        my0 = event.getY();
		    }
		    else if (event.getAction() == MotionEvent.ACTION_MOVE) {
		    	
		    }
		    else if (event.getAction() == MotionEvent.ACTION_UP){
		    	mx1 = event.getX();
		    	my1 = event.getY();
		    	fillArray(lineID, mx0, my0, mx1, my1);
		    	
		    	
		    	//EdeMain.checkProxim(mx0, my0, mx1, my1);
				//Log.v(EdeMain.LOG_TAG, "utstuff: " + mx0 +" - " +my0+" - " + mx1+" - " + my1);
		    }
		    else {
		        // manage any other MotionEvent
		    }

		 
		
		
		
		xCur = event.getX();
		yCur = event.getY();
		
		
		
		
		//EdeMain.updateCoord(xStartv, yStartv, xEndv, yEndv);
		//EdeMain.checkProxim(xStartv, yStartv, xEndv, yEndv, xCur, yCur);
		
		invalidate();
		return true;
		}
	
	private void fillArray(int ID, float x0, float y0, float x1, float y1)
	{
		LineInfo mLineInfo = new LineInfo();
		mLineInfo.setLineInfo(ID, x0, y0, x1, y1);
		lineArray.add(mLineInfo);
		lineID++;
	}
	
	@Override
	public void onDraw(Canvas canvas)
		{
		
		
		if (sx==1.2f)
		{
			canvas.scale(sx, sy, px, py);
			float realX;
			float realY;
			float realCurX;
			float realCurY;
			//realX = (inputX - scalecenterX) / scalingX + scalecenterx;
			realX = (mx0 - px) / sx + px;
			realY = (my0 - py) / sy + py;
			realCurX = (xCur - px) / sx + px;
			realCurY = (yCur - py) / sy + py;
			mPaint.setColor(Color.RED);
			if(xCur>0)
			{
				canvas.drawLine(realX, realY, realCurX, realCurY, mPaint);
			}
			int arraySize = lineArray.size();
			
			
			for (int i = 0; i <arraySize; i++)
			{
			LineInfo mtest = (LineInfo) lineArray.get(i);
			canvas.drawLine(mtest.getx0(), mtest.gety0(), mtest.getx1(),mtest.gety1(), mPaint);
			
			EdeMain.updateCoord(lineID, arraySize, mtest.getx1(), mtest.gety1());
			
			} 
			
		}
		else{
		
		try
			{ 
				int arraySize = lineArray.size();
				
				if(xCur>0)
				{
					
					
					canvas.drawLine(mx0, my0, xCur, yCur, mPaint);
				
				}
				
				for (int i = 0; i <arraySize; i++)
				{
				LineInfo mtest = (LineInfo) lineArray.get(i);
				canvas.drawLine(mtest.getx0(), mtest.gety0(), mtest.getx1(),mtest.gety1(), mPaint);
				
				EdeMain.updateCoord(lineID, arraySize, mtest.getx1(), mtest.gety1());
				
				}
				
			}	
				catch(Exception e)
				{
					EdeMain.errorDump(e);
				}
				
				
		}
		}
//place undoBtn here because non-static call	
	public void undo()
	{
		int arraySize = lineArray.size();
		if(arraySize > 1)
			{
			lineArray.remove(arraySize - 1);
			xCur= 0;
			lineID--;
			invalidate();
			}
		else if(arraySize == 1)
		{
			lineArray.remove(0);
			xCur=0;
			//Log.v(EdeMain.LOG_TAG, "my xCur"+ xCur);
			lineID--;
			invalidate();
		}
		
		

	}
	
	public void zoom12(){
		sx = 1.2f;
		sy = 1.2f;
		px = 10;
		py = 10;
		invalidate();
	}
}










