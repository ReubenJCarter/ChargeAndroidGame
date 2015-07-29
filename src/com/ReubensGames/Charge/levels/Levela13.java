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
import com.ReubensGames.Charge.Grid;

public class Levela13 extends Window
{
	private int gridSizeX;
	private int gridSizeY;
	Grid grid;
	
	ParticleSource particleSource0;
	Magnet magnet0; 
	Detector detector;
	Wall wall;
	Mirror mirror0;
	Mirror mirror1;
	
	int complete0;
	int complete1;
	int complete2;
	
	public Levela13()
	{		
		
	}
	
	public void Create()
	{	
		particleSource0 = new ParticleSource(70, ParticleSource.TYPE_RED, 5, 0.5f, 0.25f);
		particleSource0.x = -0.4f;
		particleSource0.y = -0.8f;
		particleSource0.rot = 3.141f / 2.0f;
		particleSource0.on = true;
		
		magnet0 = new Magnet(Magnet.TYPE_RED, 0.2f, 0.2f, 0.2f);
		magnet0.x = 0.4f;
		magnet0.y = -0.2f;
		
		detector = new Detector(0.8f, 40);
		detector.y = 1.0f;
		detector.x = 0.6f;
		
		wall = new Wall(0.8f, 0.0f);
		wall.x = 0.4f;
		wall.y = 0.4f;
		
		mirror0 = new Mirror(0.8f, 0.0f);
		mirror0.x = -0.2f;
		mirror0.y = 0.4f;
		mirror0.rot = -3.141f / 2.0f;
		
		mirror1 = new Mirror(0.8f, 0.0f);
		mirror1.x = 0.2f;
		mirror1.y = -0.1f;
		mirror1.rot = 3.141f / 2.0f;
		
		grid = new Grid(0.2f, 0.2f);
		
		complete0 = 0; 
		complete1 = 0; 
		complete2 = 0; 
	}
	
	public void Activate()
	{
		magnet0.x = 0.4f;
		magnet0.y = -0.2f;
		
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

		particleSource0.AddWall(wall);
		particleSource0.AddMirror(mirror0);
		particleSource0.AddMirror(mirror1);
		
		magnet0.AddSourceCollision(particleSource0);
		magnet0.AddWallCollision(wall);
		magnet0.AddMirrorCollision(mirror0);
		magnet0.AddMirrorCollision(mirror1);
		
		grid.Render();
		
		particleSource0.RenderSourceSprite();
		magnet0.Render();
		particleSource0.Render();
		wall.Render();
		mirror0.Render();
		mirror1.Render();
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