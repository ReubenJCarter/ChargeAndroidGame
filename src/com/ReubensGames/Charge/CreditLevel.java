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
import com.ReubensGames.Charge.ParticleSystem;

public class CreditLevel extends Window
{
	int saveState;

	int particleNumber;
	Texture particleTexture;
	ParticleSystem ps;
	EulerParticle[] ep;
	
	Texture endTexture;
	Sprite endSprite;
	Texture creditTexture;
	Sprite creditSprite;
	
	private float GetRandomFloat(float fMin, float fMax)
	{
		return fMin + ((float)Math.random() * (fMax - fMin));
	}
	
	public CreditLevel()
	{
		
	}
	
	public void Create()
	{		
		endTexture = new Texture();
		endTexture.Load(context, R.drawable.theend_title);
		endSprite = new Sprite(endTexture, 0.0f, 0.4f, 1.5f, 1.5f * endTexture.GetAspectRatio());
		endSprite.SetColor(1.0f, 1.0f, 1.0f, 0.8f);
		creditTexture = new Texture();
		creditTexture.Load(context, R.drawable.credit_title);
		creditSprite = new Sprite(creditTexture, 0.0f, -1.0f, 1.0f, 1.0f * creditTexture.GetAspectRatio());
		creditSprite.SetColor(1.0f, 1.0f, 1.0f, 0.8f);
		
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
			
			float randX = GetRandomFloat(-1.0f, 1.0f);
			float randY = GetRandomFloat(-Manager.aspectRatio, Manager.aspectRatio);
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
		
		endSprite.Render();
		creditSprite.Render();
		
		for(int i = 0; i < particleNumber; i++)
		{
			ep[i].AddFieldForce(0.0f, -0.0f, 0.0001f);
			ep[i].Update();
			if(ep[i].x > 1.0f) {ep[i].x = 1.0f; ep[i].vx = -ep[i].vx * 0.5f;}
			if(ep[i].x < -1.0f) {ep[i].x = -1.0f; ep[i].vx = -ep[i].vx * 0.5f;}
			if(ep[i].y > Manager.aspectRatio) {ep[i].y = Manager.aspectRatio; ep[i].vy = -ep[i].vy * 0.5f;}
			if(ep[i].y < -Manager.aspectRatio) {ep[i].y = -Manager.aspectRatio; ep[i].vy = -ep[i].vy * 0.5f;}
			ps.SetPosition(i, ep[i].x, ep[i].y);
		}
	}
	
	public boolean OnTouchEvent(MotionEvent e) 
	{

		return true;
	}
	
	public void OnBackPressed()
	{
		manager.Activate(0);
	}
}