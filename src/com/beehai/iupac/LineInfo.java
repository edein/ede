package com.beehai.iupac;

import android.content.Context;

public class LineInfo{
	
	private float x0 = 1;
	private float y0;
	private float x1;
	private float y1;
	
	public LineInfo(){
		
	}

	public LineInfo(Context context) {
		
		// TODO Auto-generated constructor stub
	}

	public LineInfo(float x0, float y0, float x1, float y1)
	{
		this.x0=x0;
		this.y0=y0;
		this.x1=x1;
		this.y1=y1;
	}
	
	public float getx0()
	{
		return this.x0;
	}
	public float gety0()
	{
		return this.y0;
	}
	public float getx1()
	{
		return this.x1;
	}
	public float gety1()
	{
		return this.y1;
	}
}