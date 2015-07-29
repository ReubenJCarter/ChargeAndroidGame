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

public class Levela18 extends Window
{
	private int gridSizeX;
	private int gridSizeY;
	Grid grid;
	
	ParticleSource particleSource0;
	Magnet magnet0; 
	Magnet magnet1; 
	Magnet magnet2; 
	Detector detector;
	Wall wall0;
	Wall wall1;
	Wall wall2;
	
	int complete0;
	int complete1;
	int complete2;
	
	public Levela18()
	{		
		
	}
	
	public void Create()
	{	
		particleSource0 = new ParticleSource(70, ParticleSource.TYPE_BLUE, 5, 0.5f, 0.25f);
		particleSource0.x = 0.0f;
		particleSource0.y = 1.2f;
		particleSource0.rot = -3 * 3.141f / 4.0f;
		particleSource0.on = true;
		
		magnet0 = new Magnet(Magnet.TYPE_RED, 0.2f, 0.2f, 0.2f);
		magnet0.x = 0.2f;
		magnet0.y = -0.2f;
		
		magnet1 = new Magnet(Magnet.TYPE_BLUE, 0.2f, 0.2f, 0.2f);
		magnet1.x = 0.2f;
		magnet1.y = -0.4f;
		
		magnet2 = new Magnet(Magnet.TYPE_BLUE, 0.2f, 0.2f, 0.2f);
		magnet2.x = 0.2f;
		magnet2.y = 0.2f;
		
		detector = new Detector(0.8f, 40);
		detector.y = -1.0f;
		detector.x = -0.6f;
		
		wall0 = new Wall(0.8f, 0.0f);
		wall0.x = 0.4f;
		wall0.y = 0.8f;
		
		wall1 = new Wall(0.8f, 0.0f);
		wall1.x = -0.4f;
		wall1.y = 0.0f;
		
		wall2 = new Wall(0.8f, 0.0f);
		wall2.x = 0.4f;
		wall2.y = -0.8f;
		
		grid = new Grid(0.2f, 0.2f);
		
		complete0 = 0; 
		complete1 = 0; 
		complete2 = 0; 
	}
	
	public void Activate()
	{
		magnet0.x = 0.2f;
		magnet0.y = -0.2f;
		magnet1.x = 0.2f;
		magnet1.y = -0.4f;
		magnet2.x = 0.2f;
		magnet2.y = 0.2f;
		
		particleSource0.Reset();

		complete0 = 0; 
	}
	 
	public void Render()
	{ 
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		
		particleSource0.AddField(magnet0);
		particleSource0.AddField(magnet1);
		particleSource0.AddField(magnet2);
		
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
		
		particleSource0.AddWall(wall0);
		particleSource0.AddWall(wall1);
		particleSource0.AddWall(wall2);
		
		magnet0.AddSourceCollision(particleSource0);
		magnet0.AddMagnetCollision(magnet1);
		magnet0.AddMagnetCollision(magnet2);
		magnet0.AddWallCollision(wall0);
		magnet0.AddWallCollision(wall1);
		magnet0.AddWallCollision(wall2);
		
		magnet1.AddSourceCollision(particleSource0);
		magnet1.AddMagnetCollision(magnet0);
		magnet1.AddMagnetCollision(magnet2);
		magnet1.AddWallCollision(wall0);
		magnet1.AddWallCollision(wall1);
		magnet1.AddWallCollision(wall2);
		
		magnet2.AddSourceCollision(particleSource0);
		magnet2.AddMagnetCollision(magnet0);
		magnet2.AddMagnetCollision(magnet1);
		magnet2.AddWallCollision(wall0);
		magnet2.AddWallCollision(wall1);
		magnet2.AddWallCollision(wall2);
		
		grid.Render();
		particleSource0.RenderSourceSprite();
		magnet0.Render();
		magnet1.Render();
		magnet2.Render();
		particleSource0.Render();
		wall0.Render();
		wall1.Render();
		wall2.Render();
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