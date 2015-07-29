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

public class Grid
{
	float gridX;
	float gridY;

	Quad[] horLines;
	Quad[] verLines;
	int numberX;
	int numberY;
	
	public Grid(float fGridX, float fGridY)
	{
		gridX = fGridX;
		gridY = fGridY;
		
		numberX = (int)(2.0f / gridX) + 1;
		numberY = (int)(2.0f / gridY) * 2 + 1;
		
		horLines = new Quad[numberY];
		verLines = new Quad[numberX];
		for(int i = 0; i < numberY; i++)
		{
			horLines[i] = new Quad();
			horLines[i].SetPosition(0.0f, gridY * (i - numberY / 2));
			horLines[i].SetSize(2.0f, 0.01f);
			horLines[i].SetColor(0.8f, 0.8f, 0.8f, 0.1f);
		}
		for(int i = 0; i < numberX; i++)
		{
			verLines[i] = new Quad();
			verLines[i].SetPosition(gridX * (i - numberX / 2), 0.0f);
			verLines[i].SetSize(0.01f, 4.0f);
			verLines[i].SetColor(0.8f, 0.8f, 0.8f, 0.1f);
		}
	}
	
	public void Render()
	{
		for(int i = 0; i < numberY; i++)
		{
			horLines[i].Render();
		}
		for(int i = 0; i < numberX; i++)
		{
			verLines[i].Render();
		}
	}
}