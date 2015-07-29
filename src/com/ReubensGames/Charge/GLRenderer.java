package com.ReubensGames.Charge;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.lang.Math;
import android.util.Log;

import com.ReubensGames.Charge.windowManager.Window;
import com.ReubensGames.Charge.windowManager.Manager;
import com.ReubensGames.Charge.glAbstraction.Texture;

import com.ReubensGames.Charge.levels.Leveltest1;

import com.ReubensGames.Charge.levels.Levela0;
import com.ReubensGames.Charge.levels.Levela1;
import com.ReubensGames.Charge.levels.Levela2;
import com.ReubensGames.Charge.levels.Levela3;
import com.ReubensGames.Charge.levels.Levela4;
import com.ReubensGames.Charge.levels.Levela5;
import com.ReubensGames.Charge.levels.Levela6;
import com.ReubensGames.Charge.levels.Levela7;
import com.ReubensGames.Charge.levels.Levela8;
import com.ReubensGames.Charge.levels.Levela9;
import com.ReubensGames.Charge.levels.Levela10;
import com.ReubensGames.Charge.levels.Levela11;
import com.ReubensGames.Charge.levels.Levela12;
import com.ReubensGames.Charge.levels.Levela13;
import com.ReubensGames.Charge.levels.Levela14;
import com.ReubensGames.Charge.levels.Levela15;
import com.ReubensGames.Charge.levels.Levela16;
import com.ReubensGames.Charge.levels.Levela17;
import com.ReubensGames.Charge.levels.Levela18;


public class GLRenderer implements GLSurfaceView.Renderer
{
	private Context context;
	private float screenWidth;
	private float screenHeight;
	private boolean firstRun;
	
	MainMenu mainMenu;
	LevelChooseMenu lvlChooseMenu;
	Manager manager;
	
	Leveltest1 leveltest1;
	
	Levela0 levela0;
	Levela1 levela1;
	Levela2 levela2;
	Levela3 levela3;
	Levela4 levela4;
	Levela5 levela5;
	Levela6 levela6;
	Levela7 levela7;
	Levela8 levela8;
	Levela9 levela9;
	Levela10 levela10;
	Levela11 levela11;
	Levela12 levela12;
	Levela13 levela13;
	Levela14 levela14;
	Levela15 levela15;
	Levela16 levela16;
	Levela17 levela17;
	Levela18 levela18;
	CreditLevel creditLevel;
	
	//Texture LoadingTexture;
	//Sprite LoadingSprite;

	public GLRenderer(Context fContext, float fScreenSizeX, float fScreenSizeY)
	{
		context = fContext;
		screenWidth = fScreenSizeX;
		screenHeight = fScreenSizeY;
	}
	
	public void onSurfaceCreated(GL10 unused, EGLConfig config) 
	{
		Manager.SetContext(context);
		firstRun = true;
		
		//LoadingTexture = new Texture();
		//LoadingTexture.Load(context, R.drawable.theend_title);
		//LoadingSprite = new Sprite(LoadingTexture, 0.0f, 0.0f, 1.0f, 1.0f * LoadingTexture.GetAspectRatio());
			//GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
			//GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
			//LoadingSprite.Render();
    }

    public void onDrawFrame(GL10 unused) 
	{
		if(firstRun)
		{	
			GLES20.glDisable(GLES20.GL_CULL_FACE);
			GLES20.glDisable(GLES20.GL_DEPTH_TEST);
			GLES20.glDisable(GLES20.GL_DITHER);
			GLES20.glDisable(GLES20.GL_POLYGON_OFFSET_FILL);
			GLES20.glDisable(GLES20.GL_SAMPLE_ALPHA_TO_COVERAGE);
			GLES20.glDisable(GLES20.GL_SAMPLE_COVERAGE);
			GLES20.glDisable(GLES20.GL_SCISSOR_TEST);
			GLES20.glDisable(GLES20.GL_STENCIL_TEST);
		
			Sprite.CompileShader();
			Quad.CompileShader();
			ParticleSystem.CompileShader();
		
			ParticleSource.LoadTextures(context);
			Magnet.LoadTextures(context);
			Detector.LoadTextures(context);
			Wall.LoadTextures(context);
			Mirror.LoadTextures(context);
			
			manager = new Manager(200);
			mainMenu = new MainMenu();
			lvlChooseMenu = new LevelChooseMenu();
			manager.AddWindow(0, mainMenu);
			manager.AddWindow(1, lvlChooseMenu);
			
			leveltest1 = new Leveltest1();
			manager.AddWindow(10, leveltest1);
			
			levela0 = new Levela0();
			manager.AddWindow(20, levela0);
			levela1 = new Levela1();
			manager.AddWindow(21, levela1);
			levela2 = new Levela2();
			manager.AddWindow(22, levela2);
			levela3 = new Levela3();
			manager.AddWindow(23, levela3);
			levela4 = new Levela4();
			manager.AddWindow(24, levela4);
			levela5 = new Levela5();
			manager.AddWindow(25, levela5);
			levela6 = new Levela6();
			manager.AddWindow(26, levela6);
			levela7 = new Levela7();
			manager.AddWindow(27, levela7);
			levela8 = new Levela8();
			manager.AddWindow(28, levela8);
			levela9 = new Levela9();
			manager.AddWindow(29, levela9);
			levela10 = new Levela10();
			manager.AddWindow(30, levela10);
			levela11 = new Levela11();
			manager.AddWindow(31, levela11);
			levela12 = new Levela12();
			manager.AddWindow(32, levela12);
			levela13 = new Levela13();
			manager.AddWindow(33, levela13);
			levela14 = new Levela14();
			manager.AddWindow(34, levela14);
			levela15 = new Levela15();
			manager.AddWindow(35, levela15);
			levela16 = new Levela16();
			manager.AddWindow(36, levela16);
			levela17 = new Levela17();
			manager.AddWindow(37, levela17);
			levela18 = new Levela18();
			manager.AddWindow(38, levela18);
			creditLevel = new CreditLevel();
			manager.AddWindow(39, creditLevel);
			
			manager.CreateWindows();
			manager.Activate(0);
			firstRun = false;
		}
		else
		{
			manager.Render();
		}
    }
	
    public void onSurfaceChanged(GL10 unused, int width, int height) 
	{
		screenWidth = width;
		screenHeight = height;
        GLES20.glViewport(0, 0, width, height);
		Manager.SetScreenSize(screenWidth, screenHeight);
		//Log.i("CHARGE:", "w = " + screenWidth + "h = " + screenHeight);
		//Log.i("CHARGE:", GLES20.glGetString(GLES20.GL_EXTENSIONS));
    }
	
	public boolean OnTouchEvent(MotionEvent e) 
	{
		manager.OnTouchEvent(e);
		return true;
	}
	
	public void OnBackPressed()
	{
		manager.OnBackPressed();
	}
}
