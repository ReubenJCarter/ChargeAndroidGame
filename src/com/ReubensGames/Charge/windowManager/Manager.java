package com.ReubensGames.Charge.windowManager;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.lang.Math;

public class Manager
{
	private Window[] window;
	private int windowNumber;
	private boolean[] exists;
	public int activeWindowId;
	
	public static float screenWidth;
	public static float screenHeight;
	public static float aspectRatio;
	public static Context context;
	
	public static void SetScreenSize(float fScreenX, float fScreenY)
	{
		screenWidth = fScreenX;
		screenHeight = fScreenY;
		aspectRatio = screenHeight / screenWidth;
	}
	
	public static void SetContext(Context fContext)
	{
		context = fContext;
	}
	
	public Manager(int fWindowNumber)
	{
		windowNumber = fWindowNumber;
		window = new Window[windowNumber];
		exists = new boolean[windowNumber];
		
		for(int i = 0; i < windowNumber; i++)
		{
			exists[i] = false;
		}
		activeWindowId = -1;
	}
	
	public void AddWindow(int fId, Window fWindow)
	{
		window[fId] = fWindow;
		exists[fId] = true;
		fWindow.manager = this;
		fWindow.windowId = fId;
		fWindow.screenWidth = screenWidth;
		fWindow.screenHeight = screenHeight;
		fWindow.aspectRatio = aspectRatio;
		fWindow.context = context;
	}
	
	public void Activate(int fId)
	{
		if(fId != activeWindowId)
		{
			if(activeWindowId >= 0)
			{
				window[activeWindowId].Deactivate();
			}
			activeWindowId = fId;
			window[activeWindowId].Activate();
		}
	}
	
	public void CreateWindows()
	{
		for(int i = 0; i < windowNumber; i++)
		{
			if(exists[i])
			{
				window[i].Create();
			}
		}
	}
	
	public void Render()
	{
		if(activeWindowId >= 0)
		{
			window[activeWindowId].Render();
		}
	}
	
	public boolean OnTouchEvent(MotionEvent e)
	{
		if(activeWindowId >= 0)
		{
			window[activeWindowId].OnTouchEvent(e);
		}
		return true;
	}
	
	public void OnBackPressed()
	{
		if(activeWindowId >= 0)
		{
			window[activeWindowId].OnBackPressed();
		}
	}
}