package ede.iupac;

import android.content.Context;
import android.view.View;

public class LineObj extends View {
	
	public static int x0;
	public static int x1;
	public static int y0;
	public static int y1;
	

	public static float testValue;
	
	public LineObj(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	
	
	 public static float checkProxim(float testX, float testY)
	    {
	        int dy = y1 - y0;
	        int dx = x1 - x0;
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
	                    	if (fraction == testX)
	                    		testValue= testX;
	                }
	                x0 += stepx;
	                fraction += dy;                                    // same as fraction -= 2*dy
	                
	            }
	        } else {
	            int fraction = dx - (dy >> 1);
	            while (y0 != y1) {
	                if (fraction >= 0) {
	                    x0 += stepx;
	                    fraction -= dy;
	                }
	                y0 += stepy;
	                fraction += dx;;
	            }
	        }
	        return testValue;
	    }

	
}
