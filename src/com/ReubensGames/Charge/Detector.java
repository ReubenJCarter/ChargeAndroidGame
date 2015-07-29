package com.ReubensGames.Charge;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.util.Log;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.lang.Math;

import com.ReubensGames.Charge.glAbstraction.Texture;
import com.ReubensGames.Charge.physicsEngine.EulerParticle;
import com.ReubensGames.Charge.windowManager.Manager;

public class Detector
{
	static Texture centreTexture;
	static Texture ringTexture;
	static Texture particleTexture;
	static Sprite centreSprite;
	static Sprite ringSprite;
	
	float centreWidth;
	float centreHeight;
	float ringWidth;
	float ringHeight;
	float particleWidth;
	float particleHeight;
	
	EulerParticle[] physics;
	ParticleSystem particleSystem;
	int particleNumber;
	int freq;
	int particleCounter;
	int stepCounter;
	boolean on;
	
	public float width;
	public float x; 
	public float y;
	public int excited;
	public boolean detectorCompete; 
	
	public static void LoadTextures(Context fContext)
	{
		centreTexture = new Texture();
		centreTexture.Load(fContext, R.drawable.detector_centre);
		
		ringTexture = new Texture();
		ringTexture.Load(fContext, R.drawable.detector_ring);
		
		particleTexture = new Texture();
		particleTexture.Load(fContext, R.drawable.particle);
	}
	
	float GetRandomFloat(float fMin, float fMax)
	{
		return fMin + ((float)Math.random() * (fMax - fMin));
	}
	
	public Detector(float fWidth, int fParticleNumber)
	{	
		width = fWidth;
		
		centreSprite = new Sprite(centreTexture);
		centreWidth = fWidth;
		centreHeight = fWidth * centreTexture.GetAspectRatio();
		centreSprite.SetSize(centreWidth, centreHeight);
		
		ringSprite = new Sprite(ringTexture);
		ringWidth = fWidth * 0.7f;
		ringHeight = ringWidth * ringTexture.GetAspectRatio();
		ringSprite.SetSize(ringWidth, ringHeight);
		
		particleNumber = fParticleNumber;
		freq = 1;
		particleCounter = 0;
		stepCounter = 0;
		on = true;
		particleSystem = new ParticleSystem(particleNumber, particleTexture);
		physics = new EulerParticle[particleNumber];
		for(int i = 0; i < particleNumber; i++)
		{
			particleSystem.SetVisible(i, false);
			particleSystem.SetSize(i, fWidth * 0.2f, fWidth * 0.2f * particleTexture.GetAspectRatio());
			physics[i] = new EulerParticle();
			physics[i].x = x;
			physics[i].y = y;
			physics[i].vx = 0.0f;
			physics[i].vy = 0.0f;
			physics[i].ax = 0.0f;
			physics[i].ay = 0.0f;
		}
		excited = 0;
		detectorCompete = false;
	}
	
	public boolean OnTouchEvent(MotionEvent e)
	{
		return true;
	}
	
	public void Ecxite()
	{
		excited++;
	}
	
	public void Render()
	{
		for(int i = 0; i < particleNumber; i++)
		{
			physics[i].AddFieldForce(x, y, 0.00008f);
			physics[i].Update();
			particleSystem.SetPosition(i, physics[i].x, physics[i].y);
			particleSystem.SetColor(i, 1.0f, 1.0f, 1.0f, 1.0f);
		}
		
		centreSprite.SetPosition(x, y);
		if(excited == 0) 
		{
			freq = 60;
			ringSprite.SetPosition(x, y);
		}
		else if(excited == 1) 
		{
			freq = 5;
			ringSprite.SetPosition(x + GetRandomFloat(-0.1f, 0.1f), y + GetRandomFloat(-0.1f, 0.1f));
		}
		else if(excited == 2)
		{
			freq = 2;
			ringSprite.SetPosition(x + GetRandomFloat(-0.2f, 0.2f), y + GetRandomFloat(-0.2f, 0.2f));
		}
		else if(excited >= 3) 
		{
			freq = 1;
			ringSprite.SetPosition(x + GetRandomFloat(-0.3f, 0.3f), y + GetRandomFloat(-0.3f, 0.3f));
		}

		stepCounter++;
		if(stepCounter >= freq)
		{
			stepCounter = 0;
			particleCounter++;
			if(particleCounter >= particleNumber)
			{
				particleCounter = 0;
			}
			
			if(on)
			{
				particleSystem.SetVisible(particleCounter, true);
				physics[particleCounter].x = x;
				physics[particleCounter].y = y;
				physics[particleCounter].vx = 0.0f;
				physics[particleCounter].vy = 0.0f;
				physics[particleCounter].ax = 0.0f;
				physics[particleCounter].ay = 0.0f;
				float theta = GetRandomFloat(0, 6.283f);
				physics[particleCounter].AddForce((float)Math.cos(theta) * 0.02f, (float)Math.sin(theta) * 0.02f);
			}
			else
			{
				particleSystem.SetVisible(particleCounter, false);
				physics[particleCounter].vx = 0.0f;
				physics[particleCounter].vy = 0.0f;
				physics[particleCounter].ax = 0.0f;
				physics[particleCounter].ay = 0.0f;
			}
		}
		
		particleSystem.Render();
		
		centreSprite.Render();
		ringSprite.Render();
		
		excited = 0;
	}
}