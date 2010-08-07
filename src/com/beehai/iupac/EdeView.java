package com.beehai.iupac;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class EdeView extends View{
	
	private float xStartv;
	private float xEndv;
	private float yStartv;
	private float yEndv ;
	private float xCur;
	private float yCur;
	
	Paint mPaint;
	
	
	public EdeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub		
		mPaint= new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.WHITE);
		mPaint.setStrokeWidth(3);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
			
		int mEvent = event.getAction();
		
		
		switch(mEvent){
		case(MotionEvent.ACTION_DOWN):
			 xStartv = event.getX(); 
			 yStartv = event.getY();
			 
			 
		case(MotionEvent.ACTION_UP):
			 xEndv= event.getX();
			 yEndv = event.getY();
		}
		
		
		xCur = event.getX();
		yCur = event.getY();

		
		EdeMain.updateCoord(xStartv, xEndv, yStartv, yEndv);
		EdeMain.checkProxim(xStartv, yStartv, xEndv, yEndv, xCur, yCur);
		invalidate();
		return true;
		}
	
	@Override
	public void onDraw(Canvas canvas){
		canvas.drawLine(xStartv, yStartv, xCur, yCur, mPaint);
		//if(mLineInfo.getx0() > 0)
			//canvas.drawLine(mLineInfo.getx0(), mLineInfo.gety0(), mLineInfo.getx1(), mLineInfo.gety1(), mPaint);
	}

}
