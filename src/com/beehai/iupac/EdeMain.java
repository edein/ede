package com.beehai.iupac;

import java.io.PrintWriter;
import java.io.StringWriter;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class EdeMain extends Activity {
   /** Called when the activity is first created. */
   public static final String LOG_TAG = "iupac";
   public static Button undoBtn;
   ToggleButton translateToggle;
   public static Button exitBtn;
   public static Button getName;
   
   static GestureDetector myDetector;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        EdeView edeView;
    	edeView = (EdeView) findViewById(R.id.EdeView);
    	myDetector = new GestureDetector(this, edeView);
		
		EdeMain.undoBtn = (Button) findViewById(R.id.undoBtn);
		EdeMain.undoBtn.setTextColor(Color.BLACK);
		EdeMain.undoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	EdeView edeView;
            	edeView = (EdeView) findViewById(R.id.EdeView);
            	edeView.undo();
            }
		 });
		
		EdeMain.getName = (Button) findViewById(R.id.getName);
		EdeMain.getName.setTextColor(Color.BLACK);
		EdeMain.getName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	GetName getName;
            	getName = new GetName();

            	Context context = getApplicationContext();
            	LineInfo.resetConnectedPoint();
            	CharSequence text = getName.name();
            	int duration = Toast.LENGTH_LONG;

            	Toast toast = Toast.makeText(context, text, duration);
            	toast.show();
            }
		 });
		
		EdeMain.exitBtn = (Button) findViewById(R.id.exitBtn);
		EdeMain.exitBtn.setTextColor(Color.BLACK);
		EdeMain.exitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	System.exit(0);
            }
		 });
		
		translateToggle = (ToggleButton) findViewById(R.id.translateToggle);
        translateToggle.setTextColor(Color.BLACK);
        translateToggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if (translateToggle.isChecked()) {
                    EdeView.translate=true;
                } else {
                    EdeView.translate=false;
                }
            }
		 });
	    
    }

 
    
    
}
    
    












