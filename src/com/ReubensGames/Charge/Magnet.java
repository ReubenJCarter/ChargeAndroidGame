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

public class Magnet
{
	public static final int TYPE_RED = 0;
	public static final int TYPE_BLUE = 1;
	
	static Texture redMagnetTex;
	static Texture blueMagnetTex;
	
	Sprite magnetSprite;
	public float x;
	public float y;
	public float power;
	public int type;
	float xPrev; 
	float yPrev;
	boolean depressed;
	float depressedX;
	float depressedY;
	float depressedOffsetX;
	float depressedOffsetY;
	int pointerID;
	float spriteWidth;
	float spriteHeight;
	
	float gridX;
	float gridY;
	
	
	public static void LoadTextures(Context fContext)
	{
		redMagnetTex = new Texture();
		redMagnetTex.Load(fContext, R.drawable.magnet_red);
		blueMagnetTex = new Texture();
		blueMagnetTex.Load(fContext, R.drawable.magnet_blue);
	}
	
	public Magnet(int fType, float fWidth, float fGridX, float fGridY)
	{
		x = 0.0f;
		y = 0.0f;
		xPrev = 0.0f;
		yPrev = 0.0f;
		power = 0.00005f;
		type = fType;
		depressed = false;
		depressedX = 0.0f;
		depressedY = 0.0f;
		depressedOffsetX = 0.0f;
		depressedOffsetY = 0.0f;
		pointerID = MotionEvent.INVALID_POINTER_ID;
		
		gridX = fGridX;
		gridY = fGridY;
		
		if(type == TYPE_RED)
		{
			magnetSprite = new Sprite(redMagnetTex);
			spriteWidth = fWidth;
			spriteHeight = fWidth * redMagnetTex.GetAspectRatio();
			magnetSprite.SetSize(spriteWidth, spriteHeight);
		}
		
		if(type == TYPE_BLUE)
		{
			magnetSprite = new Sprite(blueMagnetTex);
			spriteWidth = fWidth;
			spriteHeight = fWidth * blueMagnetTex.GetAspectRatio();
			magnetSprite.SetSize(spriteWidth, spriteHeight);
		}
	}
	
	public boolean OnTouchEvent(MotionEvent e)
	{
		int actionIndex = e.getActionIndex();
		int actionCode = e.getActionMasked();
		
		if(actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_DOWN)
		{
			float pointerX = (e.getX(actionIndex) / Manager.screenWidth) * 2.0f - 1.0f;
			float pointerY = Manager.aspectRatio * ((e.getY(actionIndex) / Manager.screenHeight) * -2.0f + 1.0f);
			float halfWidth = spriteWidth / 2.0f;
			float halfHeight = spriteHeight / 2.0f;
			if(pointerX < (x + halfWidth) && pointerX > (x - halfWidth) &&
			   pointerY < (y + halfHeight) && pointerY > (y - halfHeight) &&
			   depressed == false)
			{
				depressed = true;
				depressedX = pointerX;
				depressedY = pointerY;
				depressedOffsetX = depressedX - x;
				depressedOffsetY = depressedY - y;
				pointerID = e.getPointerId(actionIndex);
			}
		}
		
		if(actionCode == MotionEvent.ACTION_POINTER_UP || actionCode == MotionEvent.ACTION_UP)
		{
			if(e.getPointerId(actionIndex) == pointerID)
			{
				depressed = false;	
				pointerID = MotionEvent.INVALID_POINTER_ID;
			}
		}
		
		if(actionCode == MotionEvent.ACTION_CANCEL)
		{
			if(e.getPointerId(actionIndex) == pointerID)
			{
				depressed = false;
				pointerID = MotionEvent.INVALID_POINTER_ID;
			}
		}
		
		if(actionCode == MotionEvent.ACTION_MOVE)
		{
			int pointerNumber = e.getPointerCount();
			for(int i = 0; i < pointerNumber; i++)
			{
				if(e.getPointerId(i) == pointerID)
				{
					depressedX = (e.getX(i) / Manager.screenWidth) * 2.0f - 1.0f;
					depressedY = Manager.aspectRatio * ((e.getY(i) / Manager.screenHeight) * -2.0f + 1.0f);
					xPrev = x;
					yPrev = y;
					x = (float)Math.ceil((depressedX - depressedOffsetX - 0.5f * gridX) / gridX) * gridX;
					y = (float)Math.ceil((depressedY - depressedOffsetY - 0.5f * gridY) / gridY) * gridY;
				}
			}
		}
		
		return true;
	}
	
	public void AddSourceCollision(ParticleSource fSource)
	{
		if(fSource.CircleInside(x, y, spriteWidth / 2.0f))
		{
			x = xPrev; 
			y = yPrev;
		}
	}
	
	public void AddWallCollision(Wall fWall)
	{
		if(fWall.CircleInside(x, y, spriteWidth / 2.0f))
		{
			x = xPrev; 
			y = yPrev;
		}
	}
	
	public void AddMirrorCollision(Mirror fMirror)
	{
		if(fMirror.CircleInside(x, y, spriteWidth / 2.0f))
		{
			x = xPrev; 
			y = yPrev;
		}
	}
	
	public void AddMagnetCollision(Magnet fMagnet)
	{
		float distX = x - fMagnet.x;
		float distY = y - fMagnet.y;
		float dist = (float)Math.sqrt(distX * distX + distY * distY);
		if(dist < (fMagnet.spriteWidth + spriteWidth) / 2.0f)
		{
			x = xPrev; 
			y = yPrev;
		}
	}
	
	public void Render()
	{
		if(depressed)
		{
			magnetSprite.SetColor(0.9f, 0.9f, 0.9f, 0.8f);
		}
		else
		{
			magnetSprite.SetColor(1.0f, 1.0f, 1.0f, 1.0f);
		}
		magnetSprite.SetPosition(x, y);
		magnetSprite.Render();
	}
}