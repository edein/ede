package ede.iupac;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import ede.iupac.edeMain;

public class edeView extends View{
	
	private float xStartv;
	private float xEndv;
	private float yStartv;
	private float yEndv ;
	private float xCur;
	private float yCur;
	
	Paint mPaint;
	
	
	public edeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub		
		mPaint= new Paint();
		mPaint.setColor(Color.WHITE);
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
		
		
		edeMain.updateCoord(xStartv, xEndv, yStartv, yEndv);
	
		invalidate();
		return true;
		}
	
	@Override
	public void onDraw(Canvas canvas){
		canvas.drawLine(xStartv, yStartv, xCur, yCur, mPaint);
	}

}
