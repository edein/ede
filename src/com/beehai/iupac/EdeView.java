package com.beehai.iupac;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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
		        // manage move
		    }
		    else if (event.getAction() == MotionEvent.ACTION_UP){
		    	mx1 = event.getX();
		    	my1 = event.getY();
		    	  fillArray(lineID, mx0, my0, mx1, my1);
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
		try
			{ 
				canvas.drawLine(mx0, my0, xCur, yCur, mPaint);
				
			
				int test = lineArray.size();
				for (int i = 0; i <test; i++)
				{
				LineInfo mtest = (LineInfo) lineArray.get(i);
				canvas.drawLine(mtest.getx0(), mtest.gety0(), mtest.getx1(),mtest.gety1(), mPaint);
				
				EdeMain.updateCoord(lineID, test, mtest.getx1(), mtest.gety1());
				}
				
			}	
				catch(Exception e)
				{
					EdeMain.errorDump(e);
				}
			
		}
	public static void undo()
	{
		if (lineArray.size() > 0)
		lineArray.remove(lineArray.size() - 1);
	}
	
}
