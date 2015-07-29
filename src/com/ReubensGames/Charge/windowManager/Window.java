package com.ReubensGames.Charge.windowManager;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.lang.Math;

public abstract class Window
{
	public int windowId;
	public Manager manager;
	
	public float screenWidth;
	public float screenHeight;
	public float aspectRatio;
	public Context context;
	
	public Window()
	{
	}
	
	public void Create()
	{		
	}
	
	public void Render()
	{
	}
	
	public void Deactivate()
	{
	}
	
	public void Activate()
	{
	}
	
	public boolean OnTouchEvent(MotionEvent e)
	{
		return true;
	}
	public void OnBackPressed()
	{
	}
}