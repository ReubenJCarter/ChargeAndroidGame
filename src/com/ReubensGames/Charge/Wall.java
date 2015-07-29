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

public class Wall
{
	static Texture wallTexture;
	
	Sprite wallSprite;
	public float x;
	public float y;
	public float rot;
	float width;
	float height;
	
	public static void LoadTextures(Context fContext)
	{
		wallTexture = new Texture();
		wallTexture.Load(fContext, R.drawable.wall);
	}
	
	public Wall(float fWidth, float fAngle)
	{
		rot = fAngle;
		wallSprite = new Sprite(wallTexture);
		width = fWidth;
		height = fWidth * wallTexture.GetAspectRatio();
		wallSprite.SetSize(width, height);
	}
	
	public boolean PointInside(float fX, float fY)
	{
		float halfWidth = width / 2.0f;
		float halfHeight = height / 2.0f;
		
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
		
		float scalarra = (rx * ax + ry * ay) / width;
		float scalarrb = (rx * bx + ry * by) / height;
		
		return scalarra > 0 && scalarra < width && scalarrb > 0 && scalarrb < height;
	}
	
	public boolean CircleInside(float fX, float fY, float fRadius)
	{	
		float halfWidth = width / 2.0f + fRadius;
		float halfHeight = height / 2.0f + fRadius;
		
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
		
		float offsetWidth = width + 2.0f * fRadius;
		float offsetHeight = height + 2.0f * fRadius;
		
		float scalarra = (rx * ax + ry * ay) / offsetWidth;
		float scalarrb = (rx * bx + ry * by) / offsetHeight;
		
		return scalarra > 0 && scalarra < offsetWidth && scalarrb > 0 && scalarrb < offsetHeight;
	}
	
	public void Render()
	{
		wallSprite.SetRotation(rot);
		wallSprite.SetPosition(x, y);
		wallSprite.Render();
	}
}