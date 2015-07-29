package com.ReubensGames.Charge.levels;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.lang.Math;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.ReubensGames.Charge.glAbstraction.Mesh;
import com.ReubensGames.Charge.glAbstraction.ShaderProgram;
import com.ReubensGames.Charge.glAbstraction.Texture;
import com.ReubensGames.Charge.physicsEngine.EulerParticle;
import com.ReubensGames.Charge.windowManager.Window;
import com.ReubensGames.Charge.windowManager.Manager;

import com.ReubensGames.Charge.ParticleSource;
import com.ReubensGames.Charge.Magnet;
import com.ReubensGames.Charge.Detector;
import com.ReubensGames.Charge.Wall;
import com.ReubensGames.Charge.Mirror;
import com.ReubensGames.Charge.Sprite;
import com.ReubensGames.Charge.Grid;

import com.ReubensGames.Charge.R;

public class Levela0 extends Window
{
	private int gridSizeX;
	private int gridSizeY;
	Grid grid;
	
	ParticleSource particleSource0;
	Magnet magnet0; 
	Detector detector;
	
	Texture texMoveMag;
	Texture texOpposite;
	Texture texSpot;
	Sprite hintMoveMag;
	Sprite hintOpposite;
	Sprite hintSpot;
	
	int complete0;
	int complete1;
	int complete2;
	
	public Levela0()
	{		
		
	}
	
	public void Create()
	{		
		texMoveMag = new Texture();
		texMoveMag.Load(context, R.drawable.move_mag_hint);
		hintMoveMag = new Sprite(texMoveMag);
		hintMoveMag.SetSize(0.8f, 0.8f * texMoveMag.GetAspectRatio());
		hintMoveMag.SetPosition(-0.2f, -0.2f);
		
		texOpposite = new Texture();
		texOpposite.Load(context, R.drawable.opposites_hint);
		hintOpposite = new Sprite(texOpposite);
		hintOpposite.SetSize(0.6f, 0.6f * texOpposite.GetAspectRatio());
		hintOpposite.SetPosition(-0.6f, 0.8f);
		
		texSpot = new Texture();
		texSpot.Load(context, R.drawable.spot_hint);
		hintSpot = new Sprite(texSpot);
		hintSpot.SetSize(1.0f, 1.0f * texSpot.GetAspectRatio());
		hintSpot.SetPosition(0.0f, -0.8f);
	
		particleSource0 = new ParticleSource(70, ParticleSource.TYPE_BLUE, 5, 0.5f, 0.25f);
		particleSource0.x = -0.0f;
		particleSource0.y = 0.9f;
		particleSource0.rot = -3.141f / 4.0f;
		particleSource0.on = true;
		
		magnet0 = new Magnet(Magnet.TYPE_RED, 0.2f, 0.2f, 0.2f);
		magnet0.x = 0.2f;
		magnet0.y = 0.2f;
		
		detector = new Detector(0.8f, 40);
		detector.y = -0.8f;
		detector.x = 0.8f;
		
		grid = new Grid(0.2f, 0.2f);
		
		complete0 = 0; 
		complete1 = 0; 
		complete2 = 0; 
	}
	
	public void Activate()
	{
		magnet0.x = 0.2f;
		magnet0.y = 0.2f;
		
		particleSource0.Reset();

		complete0 = 0; 
	}
	 
	public void Render()
	{ 
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		
		particleSource0.AddField(magnet0);
		
		boolean s0 = particleSource0.AddDetector(detector);
		int maxScore = 500;
		int incValue = 20;
		int decValue = 3;
		int winThreshold = maxScore - 20;
		if(s0) complete0 += incValue; else complete0 -= decValue;
		if(complete0 < 0) complete0 = 0; 
		if(complete0 > maxScore) complete0 = maxScore; 
		if(complete0 >= winThreshold)
		{
			SharedPreferences userPref = context.getSharedPreferences("userPref", 0);
			int saveState = userPref.getInt("saveState", 20);
			if(saveState <  windowId + 1)
			{
				SharedPreferences.Editor editorPref = userPref.edit();
				editorPref.putInt("saveState", windowId + 1);
				editorPref.commit();
			}
			manager.Activate(windowId + 1);
		}

		magnet0.AddSourceCollision(particleSource0);
		
		grid.Render();
		
		hintMoveMag.Render();
		hintOpposite.Render();
		hintSpot.Render();
		particleSource0.RenderSourceSprite();
		magnet0.Render();
		particleSource0.Render();
		detector.Render();		
	}
	
	public boolean OnTouchEvent(MotionEvent e) 
	{
		magnet0.OnTouchEvent(e);
		return true;
	}
	
	public void OnBackPressed()
	{
		manager.Activate(0);
	}
}
