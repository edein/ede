package com.beehai.iupac;

public class CheckProx {
	
	private float[] trueValue = new float[4];

	public CheckProx()
	{	  
	}
	
	public boolean closeBy(float xCur, float yCur)
	{
		boolean changed =false;
		int arraySize = LineInfo.lineArray.size();
		for (int i = 0; i <arraySize; i++)
		{	
			float x0 = LineInfo.lineArray.get(i).getx0();
			float y0 = LineInfo.lineArray.get(i).gety0();
			float x1 = LineInfo.lineArray.get(i).getx1();
			float y1 = LineInfo.lineArray.get(i).gety1();
				
				if ( Math.abs(xCur - x0) <20 && Math.abs(yCur - y0) < 20 )
			   {
						trueValue[0]= x0;
				   		trueValue[1] = y0;
				   		changed= true;
//				   		Log.v("test", "inside closeBy first not LOCKED");
			   }
				if ( Math.abs(xCur - x1) <20 && Math.abs(yCur - y1) < 20)
			   {
					   	trueValue[0]= x1;
				   		trueValue[1] = y1;
				   		changed=true;
			   }
		}
		
		return changed;
		
	}
	
	
	
	
	public float getx0()
	{
		return trueValue[0];
	}
	public float gety0()
	{
		return trueValue[1];
	}
	public float getx1()
	{
		return trueValue[2];
	}
	public float gety1()
	{
		return trueValue[3];
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

