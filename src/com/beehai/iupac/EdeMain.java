package com.beehai.iupac;

import java.io.PrintWriter;
import java.io.StringWriter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
   public static Button zoom12;
   
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
		
		EdeMain.zoom12 = (Button) findViewById(R.id.zoom12);
		EdeMain.zoom12.setTextColor(Color.BLACK);
		EdeMain.zoom12.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	EdeView edeView;
            	edeView = (EdeView) findViewById(R.id.EdeView);
            	edeView.zoom12();
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
    
    












