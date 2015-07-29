package com.ReubensGames.Charge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

public class MainMenu extends Window
{	
	int particleNumber;
	ParticleSystem ps;
	EulerParticle[] ep;
	Texture particleTexture;
	Texture titleTexture;
	Texture playButtonTexture;
	Texture helpButtonTexture;
	Texture quitButtonTexture;
	Sprite titleSprite;
	Button playButton;
	Button helpButton;
	Button quitButton;
	float titleHeight;
	float particleMaxY;
	
	private float GetRandomFloat(float fMin, float fMax)
	{
		return fMin + ((float)Math.random() * (fMax - fMin));
	}
	
	public MainMenu()
	{	

	}
	
	public void Create()
	{		
		playButtonTexture = new Texture();
		playButtonTexture.Load(context, R.drawable.play_button);
		playButton = new Button(playButtonTexture);
		float buttonHeight = 1.0f * playButtonTexture.GetAspectRatio();
		playButton.SetSize(1.0f, buttonHeight);
		playButton.SetPosition(0.0f, -0.2f);
		
		helpButtonTexture = new Texture();
		helpButtonTexture.Load(context, R.drawable.help_button);
		helpButton = new Button(helpButtonTexture);
		helpButton.SetSize(1.0f, buttonHeight);
		helpButton.SetPosition(0.0f, -buttonHeight * 1.5f);
		
		quitButtonTexture = new Texture();
		quitButtonTexture.Load(context, R.drawable.quit_button);
		quitButton = new Button(quitButtonTexture);
		quitButton.SetSize(1.0f, buttonHeight);
		quitButton.SetPosition(0.0f, -buttonHeight * 3.0f);

		titleTexture = new Texture();
		titleTexture.Load(context, R.drawable.charge_title);
		titleSprite = new Sprite(titleTexture);
		titleHeight = titleTexture.GetAspectRatio() * 2.0f;
		titleSprite.SetSize(2.0f, titleHeight);
		titleSprite.SetPosition(0.0f, Manager.aspectRatio - titleHeight / 2);
		
		particleNumber = 200;
		particleTexture = new Texture();
		particleTexture.Load(context, R.drawable.particle);
		ps = new ParticleSystem(particleNumber, particleTexture);
		ep = new EulerParticle[particleNumber];
		for(int i = 0; i < particleNumber; i++)
		{
			ep[i] = new EulerParticle();
			ps.SetSize(i, 0.25f, 0.25f);
			ps.SetColor(i, GetRandomFloat(0.0f, 1.0f), GetRandomFloat(0.0f, 1.0f), GetRandomFloat(0.0f, 1.0f), 1.0f);
			ps.SetVisible(i, true);
			
			particleMaxY = Manager.aspectRatio - titleHeight - 0.05f;
			float randX = GetRandomFloat(-1.0f, 1.0f);
			float randY = GetRandomFloat(-Manager.aspectRatio, particleMaxY);
			ps.SetPosition(i, randX, randY);
			ep[i].x = randX;
			ep[i].y = randY;
			ep[i].vx = 0.0f;
			ep[i].vy = 0.0f;
			ep[i].ax = 0.0f;
			ep[i].ay = 0.0f;
		}
	}
	
	public void Activate()
	{
		
	}
	
	public void Render()
	{
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		ps.Render();
		playButton.Render();
		//helpButton.Render();
		//quitButton.Render();
		titleSprite.Render();
		for(int i = 0; i < particleNumber; i++)
		{
			ep[i].AddFieldForce(0.0f, -0.2f, 0.0001f);
			ep[i].Update();
			if(ep[i].x > 1.0f) {ep[i].x = 1.0f; ep[i].vx = -ep[i].vx * 0.5f;}
			if(ep[i].x < -1.0f) {ep[i].x = -1.0f; ep[i].vx = -ep[i].vx * 0.5f;}
			//if(ep[i].y > particleMaxY) {ep[i].y = particleMaxY; ep[i].vy = -ep[i].vy * 0.5f;}
			if(ep[i].y > Manager.aspectRatio) {ep[i].y = Manager.aspectRatio; ep[i].vy = -ep[i].vy * 0.5f;}
			if(ep[i].y < -Manager.aspectRatio) {ep[i].y = -Manager.aspectRatio; ep[i].vy = -ep[i].vy * 0.5f;}
			ps.SetPosition(i, ep[i].x, ep[i].y);
		}
	}
	
	public boolean OnTouchEvent(MotionEvent e) 
	{
		if(playButton.OnTouchEvent(e))
		{
			//SharedPreferences userPref = context.getSharedPreferences("userPref", 0);
			//int saveState = userPref.getInt("saveState", 20);
			//manager.Activate(saveState);
			manager.Activate(1);
		}/*
		if(helpButton.OnTouchEvent(e))
		{
			//GOTO help page
		}
		if(quitButton.OnTouchEvent(e))
		{
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}*/
		return true;
	}
	
	public void OnBackPressed()
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}