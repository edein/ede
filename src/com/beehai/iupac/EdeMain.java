package com.beehai.iupac;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class EdeMain extends Activity {
    /** Called when the activity is first created. */

   static TextView coord;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        coord = (TextView) findViewById(R.id.coord);
		coord.setTextColor(Color.MAGENTA);
		coord.setBackgroundColor(Color.CYAN);
	    
    }
    public static  void updateCoord(float x0, float y0, float x1, float y1 ){
    	coord.setText("xS: " + x0 + " - y0: "  + y0 + "             x1: " + x1 + " - y1: " + y1 ); 	
    	
    
    }
    
    public static void updateProx( float xCur, float yCur){
    	
    	coord.append( '\n' + " xCur: " + xCur + " - yCur: "+ yCur + "    Near: ");
    	
    }
    
    public static void mathOutput(float x, float y)
    {
    	//coord.append( '\n' + "mathOutput: " + x + " " + y);    	
    }
    
    
    public static void checkProxim(float x0, float y0, float x1, float y1, float xCur, float yCur)
    {
	 	
        int dy = (int) (y1 - y0);
        int dx = (int) (x1 - x0);
        int stepx, stepy;
        

        if (dy < 0) { dy = -dy;  stepy = -1; } else { stepy = 1; }
        if (dx < 0) { dx = -dx;  stepx = -1; } else { stepx = 1; }
        dy <<= 1;                                                  // dy is now 2*dy
        dx <<= 1;                                                  // dx is now 2*dx

        if (dx > dy) {
            int fraction = dy - (dx >> 1);                         // same as 2*dy - dx
            while (x0 != x1) {
                if (fraction >= 0) {
                    y0 += stepy;
                    fraction -= dx;                                // same as fraction -= 2*dx
                    	
                }
                x0 += stepx;
                fraction += dy;                                    // same as fraction -= 2*dy
                EdeMain.mathOutput(x0, y0);
            }
        } else {
            int fraction = dx - (dy >> 1);
            while (y0 != y1) {
                if (fraction >= 0) {
                    x0 += stepx;
                    fraction -= dy;
                }
                y0 += stepy;
                fraction += dx;
                EdeMain.mathOutput(x0, y0);;
            }
        }
     
    }
    
}
    
    












