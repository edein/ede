package com.beehai.iupac;

import android.util.Log;

public class CheckProx {
	
	private float[] matchValue = new float[4];

	public CheckProx()
	{	  
	}
	
	public boolean closeBy(float xCur, float yCur)
	{
		boolean changed =false;
		int groupSize = LineInfo.groupVector.size();
		for (int i = 0; i <groupSize; i++)
		{	
			
			for (int m = 0; m<LineInfo.groupVector.get(i).size(); m++)
			{
			LineInfo mLine = LineInfo.groupVector.get(i).get(m);
			float x0 = mLine.getx0();
			float y0 = mLine.gety0();
			float x1 = mLine.getx1();
			float y1 = mLine.gety1();
				
				if ( Math.abs(xCur - x0) <20 && Math.abs(yCur - y0) < 20 )
			   {
						matchValue[0]= x0;
				   		matchValue[1] = y0;
				   		changed= true;
			   }
//				if ( Math.abs(xCur - y1) <20 && Math.abs(yCur - y1) < 20)
//			   {
//					   	matchValue[2]= x1;
//				   		matchValue[3]= y1;
//				   		Log.v("inside closeBy", "");
//				   		changed=true;
//			   }
			}
		}
		
		return changed;
		
	}
	
	
	
	
	public float getx0()
	{
		return matchValue[0];
	}
	public float gety0()
	{
		return matchValue[1];
	}
	public float getx1()
	{
		return matchValue[2];
	}
	public float gety1()
	{
		return matchValue[3];
	}
	
	
	public static void checkProxim(float x0, float y0, float x1, float y1)
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
//                EdeMain.mathOutput(x0, y0);
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
//                EdeMain.mathOutput(x0, y0);;
            }
        }
     
    }
	
}

