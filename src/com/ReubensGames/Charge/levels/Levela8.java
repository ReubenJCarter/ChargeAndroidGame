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

public class Levela8 extends Window
{
	private int gridSizeX;
	private int gridSizeY;
	Grid grid;
	
	ParticleSource particleSource0;
	ParticleSource particleSource1;
	ParticleSource particleSource2;
	Magnet magnet0; 
	Magnet magnet1; 
	Magnet magnet2; 
	Detector detector;
	
	int complete0;
	int complete1;
	int complete2;
	
	public Levela8()
	{		
		
	}
	
	public void Create()
	{	
		particleSource0 = new ParticleSource(70, ParticleSource.TYPE_BLUE, 5, 0.5f, 0.25f);
		particleSource0.x = -0.8f;
		particleSource0.y = 0.6f;
		particleSource0.rot = -3.141f / 2.0f;
		particleSource0.on = true;
		
		particleSource1 = new ParticleSource(70, ParticleSource.TYPE_BLUE, 5, 0.5f, 0.25f);
		particleSource1.x = -0.2f;
		particleSource1.y = 0.8f;
		particleSource1.rot = 0.0f;
		particleSource1.on = true;
		
		particleSource2 = new ParticleSource(70, ParticleSource.TYPE_RED, 5, 0.5f, 0.25f);
		particleSource2.x = -0.2f;
		particleSource2.y = -0.8f;
		particleSource2.rot = 0.0f;
		particleSource2.on = true;
		
		magnet0 = new Magnet(Magnet.TYPE_RED, 0.2f, 0.2f, 0.2f);
		magnet0.x = -0.2f;
		magnet0.y = -0.8f;
		
		magnet1 = new Magnet(Magnet.TYPE_BLUE, 0.2f, 0.2f, 0.2f);
		magnet1.x = -0.4f;
		magnet1.y = -0.9f;
		
		magnet2 = new Magnet(Magnet.TYPE_RED, 0.2f, 0.2f, 0.2f);
		magnet2.x = -0.6f;
		magnet2.y = -0.8f;
		
		detector = new Detector(0.8f, 40);
		detector.x = 0.8f;
		detector.y = -0.1f;
		
		grid = new Grid(0.2f, 0.2f);
		
		complete0 = 0; 
		complete1 = 0; 
		complete2 = 0; 
	}
	
	public void Activate()
	{
		magnet0.x = 0.2f;
		magnet0.y = -0.8f;
		magnet1.x = 0.4f;
		magnet1.y = -0.9f;
		magnet2.x = -0.6f;
		magnet2.y = -0.8f;
		
		particleSource0.Reset();
		particleSource1.Reset();
		particleSource2.Reset();

		complete0 = 0; 
		complete1 = 0; 
		complete2 = 0; 
	}
	 
	public void Render()
	{ 
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		
		particleSource0.AddField(magnet0);
		particleSource0.AddField(magnet1);
		particleSource0.AddField(magnet2);
		particleSource1.AddField(magnet0);
		particleSource1.AddField(magnet1);
		particleSource1.AddField(magnet2);
		particleSource2.AddField(magnet0);
		particleSource2.AddField(magnet1);
		particleSource2.AddField(magnet2);
		
		boolean s0 = particleSource0.AddDetector(detector);
		boolean s1 = particleSource1.AddDetector(detector);
		boolean s2 = particleSource2.AddDetector(detector);
		int maxScore = 500;
		int incValue = 20;
		int decValue = 3;
		int winThreshold = maxScore - 20;
		if(s0) complete0 += incValue; else complete0 -= decValue;
		if(s1) complete1 += incValue; else complete1 -= decValue;
		if(s2) complete2 += incValue; else complete2 -= decValue;
		if(complete0 < 0) complete0 = 0; 
		if(complete1 < 0) complete1 = 0; 
		if(complete2 < 0) complete2 = 0; 
		if(complete0 > maxScore) complete0 = maxScore; 
		if(complete1 > maxScore) complete1 = maxScore; 
		if(complete2 > maxScore) complete2 = maxScore; 
		if(complete0 >= winThreshold && complete1 >= winThreshold && complete2 >= winThreshold) 
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
		magnet0.AddSourceCollision(particleSource1);
		magnet0.AddSourceCollision(particleSource2);
		magnet1.AddSourceCollision(particleSource0);
		magnet1.AddSourceCollision(particleSource1);
		magnet1.AddSourceCollision(particleSource2);
		magnet2.AddSourceCollision(particleSource0);
		magnet2.AddSourceCollision(particleSource1);
		magnet2.AddSourceCollision(particleSource2);
		
		magnet0.AddMagnetCollision(magnet1);
		magnet0.AddMagnetCollision(magnet2);
		magnet1.AddMagnetCollision(magnet0);
		magnet1.AddMagnetCollision(magnet2);
		magnet2.AddMagnetCollision(magnet0);
		magnet2.AddMagnetCollision(magnet1);
		
		grid.Render();
		
		particleSource0.RenderSourceSprite();
		particleSource1.RenderSourceSprite();
		particleSource2.RenderSourceSprite();
		magnet0.Render();
		magnet1.Render();
		magnet2.Render();
		particleSource0.Render();
		particleSource1.Render();
		particleSource2.Render();
		detector.Render();		
	}
	
	public boolean OnTouchEvent(MotionEvent e) 
	{
		magnet0.OnTouchEvent(e);
		magnet1.OnTouchEvent(e);
		magnet2.OnTouchEvent(e);
		return true;
	}
	
	public void OnBackPressed()
	{
		manager.Activate(0);
	}
}