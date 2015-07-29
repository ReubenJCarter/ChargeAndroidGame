package com.ReubensGames.Charge;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.Display;
import android.graphics.Point;
import android.view.WindowManager;
import android.view.View;
import android.content.pm.ActivityInfo;

class LocalGLSurfaceView extends GLSurfaceView 
{
	public GLRenderer renderer;
	
    public LocalGLSurfaceView(Context context)
	{
        super(context);
		// Create OpenGL ES 2.0 rendering context, this has to go before setRenderer
		setEGLContextClientVersion(2);
		//Get screen size
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
        // Set the Renderer for drawing on the GLSurfaceView
		renderer = new GLRenderer(context, (float)size.x, (float)size.y);
		//Set renderer
        setRenderer(renderer);
    }
	
	@Override
	public boolean onTouchEvent(MotionEvent e) 
	{
		return renderer.OnTouchEvent(e);
	}
	
	public void OnBackPressed()
	{
		renderer.OnBackPressed();
	}
	
	public void SetImmersiveMode()
	{
		//Set Immersive sticky Mode
		setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
			| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
            | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
	}
}

public class Charge extends Activity
{
	private LocalGLSurfaceView localGLSurfaceView;
	
    // Called when the activity is first created. 
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		localGLSurfaceView = new LocalGLSurfaceView(this);
        setContentView(localGLSurfaceView);
    }
	
	@Override
	public void onResume()
	{
		super.onResume();
		localGLSurfaceView.SetImmersiveMode();
	}
	
	@Override
	public void onBackPressed()
	{
		localGLSurfaceView.OnBackPressed();
	}
}
