package com.ReubensGames.Charge;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.lang.Math;

import com.ReubensGames.Charge.glAbstraction.Texture;
import com.ReubensGames.Charge.physicsEngine.EulerParticle;
import com.ReubensGames.Charge.windowManager.Manager;

public class ParticleSource
{
	public static final int TYPE_RED = 0;
	public static final int TYPE_BLUE = 1;

	static Texture particleTex;
	static Texture redSourceTex;
	static Texture blueSourceTex;
	
	int particleNumber;
	public float x;
	public float y;
	public float force;
	public float rot;
	int type;
	int freq;
	int particleCounter;
	int stepCounter;
	public boolean on;
	float r;
	float g;
	float b;
	
	EulerParticle[] physics;
	ParticleSystem particleSystem;
	Sprite sourceSprite;
	float sourceSpriteWidth;
	float sourceSpriteHeight;
	
	public static void LoadTextures(Context fContext)
	{
		particleTex = new Texture();
		particleTex.Load(fContext, R.drawable.particle);
		
		redSourceTex = new Texture();
		redSourceTex.Load(fContext, R.drawable.source_red);

		blueSourceTex = new Texture();
		blueSourceTex.Load(fContext, R.drawable.source_blue);
	}
	
	public ParticleSource(int fMaxNumber, int fType, int fFreq, float fSourceWidth, float fPSize)
	{
		force = 0.01f;
		x = 0;
		y = 0;
		rot = 0;
		freq = fFreq;
		on = false;
		particleNumber = fMaxNumber;
		type = fType;
		particleCounter = 0;
		stepCounter = 0;
		sourceSpriteWidth = fSourceWidth;
		
		particleSystem = new ParticleSystem(particleNumber, particleTex);
		
		if(type == TYPE_RED)
		{
			sourceSprite = new Sprite(redSourceTex);
			sourceSpriteHeight = sourceSpriteWidth * redSourceTex.GetAspectRatio();
			sourceSprite.SetSize(sourceSpriteWidth, sourceSpriteHeight);
			r = 1.0f;
			g = 0.1f;
			b = 0.1f;
		}
		
		if(type == TYPE_BLUE)
		{
			sourceSprite = new Sprite(blueSourceTex);
			sourceSpriteHeight = sourceSpriteWidth * blueSourceTex.GetAspectRatio();
			sourceSprite.SetSize(sourceSpriteWidth, sourceSpriteHeight);
			r = 0.1f;
			g = 0.1f;
			b = 1.0f;
		}
		
		physics = new EulerParticle[particleNumber];
		for(int i = 0; i < particleNumber; i++)
		{
			particleSystem.SetVisible(i, false);
			particleSystem.SetSize(i, fPSize, fPSize * particleTex.GetAspectRatio());
			physics[i] = new EulerParticle();
			physics[i].x = x;
			physics[i].y = y;
			physics[i].vx = 0.0f;
			physics[i].vy = 0.0f;
			physics[i].ax = 0.0f;
			physics[i].ay = 0.0f;
		}
	}
	
	public void AddField(Magnet fMag)
	{
		if(fMag.type == Magnet.TYPE_RED && type == TYPE_RED)
		{
			for(int i = 0; i < particleNumber; i++)
			{
				physics[i].AddFieldForce(fMag.x, fMag.y, -fMag.power);
			}
		}
		if(fMag.type == Magnet.TYPE_BLUE && type == TYPE_BLUE)
		{
			for(int i = 0; i < particleNumber; i++)
			{
				physics[i].AddFieldForce(fMag.x, fMag.y, -fMag.power);
			}
		}
		if(fMag.type == Magnet.TYPE_RED && type == TYPE_BLUE)
		{
			for(int i = 0; i < particleNumber; i++)
			{
				physics[i].AddFieldForce(fMag.x, fMag.y, fMag.power);
			}
		}
		if(fMag.type == Magnet.TYPE_BLUE && type == TYPE_RED)
		{
			for(int i = 0; i < particleNumber; i++)
			{
				physics[i].AddFieldForce(fMag.x, fMag.y, fMag.power);
			}
		}
	}
	
	public boolean AddDetector(Detector fDetector)
	{
		boolean complete = false;
		for(int i = 0; i < particleNumber; i++)
		{
			float distX = physics[i].x - fDetector.x;
			float distY = physics[i].y - fDetector.y;
			float dist = (float)Math.sqrt(distX * distX + distY * distY);
			if(dist < fDetector.width / 4.0f && particleSystem.GetVisible(i))
			{
				fDetector.Ecxite();
				particleSystem.SetVisible(i, false);
				complete = true;
			}
		}
		return complete;
	}
	
	public void AddWall(Wall fWall)
	{
		for(int i = 0; i < particleNumber; i++)
		{
			if(particleSystem.GetVisible(i))
			{
				if(fWall.PointInside(physics[i].x, physics[i].y))
					particleSystem.SetVisible(i, false);
			}
		}
	}
	
	public void AddMirror(Mirror fMirror)
	{
		for(int i = 0; i < particleNumber; i++)
		{
			if(particleSystem.GetVisible(i))
			{ 
				if(fMirror.PointInside(physics[i].x, physics[i].y))
				{
					float nx = -(float)Math.sin(fMirror.rot);
					float ny = (float)Math.cos(fMirror.rot);
				
					float ndoti = physics[i].vx * nx + physics[i].vy * ny;
				
					physics[i].vx = -2 * ndoti * nx + physics[i].vx;
					physics[i].vy = -2 * ndoti * ny + physics[i].vy;
				}
			}
		}
	}
	
	public boolean CircleInside(float fX, float fY, float fRadius)
	{	
		float halfWidth = sourceSpriteWidth / 2.0f + fRadius;
		float halfHeight = sourceSpriteHeight / 2.0f + fRadius;
		
		float sinRot = (float)Math.sin(rot);
		float cosRot = (float)Math.cos(rot);
		
		float p0x = x + -halfWidth * cosRot + -halfHeight * -sinRot;   
		float p0y = y + -halfWidth * sinRot + -halfHeight * cosRot;
		
		float p1x = x + halfWidth * cosRot + -halfHeight * -sinRot;   
		float p1y = y + halfWidth * sinRot + -halfHeight * cosRot;
		
		float p3x = x + -halfWidth * cosRot + halfHeight * -sinRot;   
		float p3y = y + -halfWidth * sinRot + halfHeight * cosRot;
		
		float ax = p1x - p0x;
		float ay = p1y - p0y;
		
		float bx = p3x - p0x;
		float by = p3y - p0y;	
		
		float rx = fX - p0x;
		float ry = fY - p0y;
		
		float offsetWidth = sourceSpriteWidth + 2.0f * fRadius;
		float offsetHeight = sourceSpriteHeight + 2.0f * fRadius;
		
		float scalarra = (rx * ax + ry * ay) / offsetWidth;
		float scalarrb = (rx * bx + ry * by) / offsetHeight;
		
		return scalarra > 0 && scalarra < offsetWidth && scalarrb > 0 && scalarrb < offsetHeight;
	}
	
	public void Reset()
	{
		for(int i = 0; i < particleNumber; i++)
		{
			particleSystem.SetVisible(i, false);
		}
	}
	
	public void Render()
	{	
		for(int i = 0; i < particleNumber; i++)
		{
			float absForce = (float)Math.sqrt(physics[i].ax * physics[i].ax  + physics[i].ay * physics[i].ay) * 1500.0f;
			physics[i].Update();
			particleSystem.SetPosition(i, physics[i].x, physics[i].y);
			
			float absVelocity = (float)Math.sqrt(physics[i].vx * physics[i].vx + physics[i].vy * physics[i].vy) * 15.0f;
			if(type == TYPE_BLUE)
				particleSystem.SetColor(i, r + absForce, absVelocity, b - absForce, 1.0f);
			if(type == TYPE_RED)
				particleSystem.SetColor(i, r - absForce, absVelocity, b + absForce, 1.0f);
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
				physics[particleCounter].x = x + (float)Math.cos(rot) * 1.05f * sourceSpriteWidth / 2.0f;
				physics[particleCounter].y = y + (float)Math.sin(rot) * 1.05f * sourceSpriteWidth / 2.0f;
				physics[particleCounter].vx = 0.0f;
				physics[particleCounter].vy = 0.0f;
				physics[particleCounter].ax = 0.0f;
				physics[particleCounter].ay = 0.0f;
				physics[particleCounter].AddForce((float)Math.cos(rot) * force, (float)Math.sin(rot) * force);
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
		
	}
	
	public void RenderSourceSprite()
	{
		sourceSprite.SetPosition(x, y);
		sourceSprite.SetRotation(rot);
		sourceSprite.Render();
	}
}