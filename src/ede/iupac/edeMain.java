package ede.iupac;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class edeMain extends Activity {
    /** Called when the activity is first created. */

    public static float xStart;
    public static float xEnd;
    public static float yStart;
    public static float yEnd;
    
    float test;
    
    static TextView coord;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        coord = (TextView) findViewById(R.id.coord);
		coord.setTextColor(Color.MAGENTA);
		coord.setBackgroundColor(Color.CYAN);
	    
    }
    public static  void updateCoord(float xS, float xE, float yS, float yE ){
    	xStart = xS; xEnd= xE; yStart= yS; yEnd = yE;
    	coord.setText("xS: " + xStart + " - xE: "  + xEnd + "             yS: " + yStart + " - yE: " + yEnd );
    	
    }
    
}
    
    












