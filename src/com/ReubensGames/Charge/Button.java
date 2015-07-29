package com.ReubensGames.Charge;

import android.view.MotionEvent;
import android.util.Log;

import com.ReubensGames.Charge.glAbstraction.Texture;
import com.ReubensGames.Charge.windowManager.Manager;

public class Button
{
	Sprite sprite;
	boolean depressed;
	float width;
	float height;
	float x;
	float y;
	float rUp;
	float gUp;
	float bUp;
	float aUp;
	float rDown;
	float gDown;
	float bDown;
	float aDown;
	
	public boolean active;
	
	public Button(Texture fTexture)
	{
		sprite = new Sprite(fTexture);
		width = fTexture.GetWidth();
		height = fTexture.GetHeight();
		depressed = false;
		x = 0;
		y = 0;
		rUp = 1.0f;
		gUp = 1.0f;
		bUp = 1.0f;
		aUp = 1.0f;
		rDown = 0.9f;
		gDown = 0.9f;
		bDown = 0.9f;
		aDown = 0.8f;
		active = true;
	}
	
	public Button(Texture fTexture, float fX, float fY, float fWidth, float fHeight)
	{
		sprite = new Sprite(fTexture);
		width = fTexture.GetWidth();
		height = fTexture.GetHeight();
		depressed = false;
		x = fX;
		y = fY;
		width = fWidth;
		height = fHeight;
		sprite.SetSize(width, height);
		rUp = 1.0f;
		gUp = 1.0f;
		bUp = 1.0f;
		aUp = 1.0f;
		rDown = 0.9f;
		gDown = 0.9f;
		bDown = 0.9f;
		aDown = 0.8f;
		active = true;
	}
	
	public void SetPosition(float fX, float fY)
	{
		x = fX;
		y = fY;
	}
	
	public void SetSize(float fX, float fY)
	{
		width = fX;
		height = fY;
		sprite.SetSize(width, height);
	}
	
	public boolean OnTouchEvent(MotionEvent e) 
	{
		boolean retVal = false;
		int action = e.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		
		if(actionCode == MotionEvent.ACTION_DOWN)
		{
			float pointerX = (e.getX() / Manager.screenWidth) * 2.0f - 1.0f;
			float pointerY = Manager.aspectRatio * ((e.getY() / Manager.screenHeight) * -2.0f + 1.0f);
			float halfWidth = width / 2.0f;
			float halfHeight = height / 2.0f;
			if(pointerX < (x + halfWidth) && pointerX > (x - halfWidth) &&
			   pointerY < (y + halfHeight) && pointerY > (y - halfHeight))
			{
				depressed = true;
			}
		}
		
		if(actionCode == MotionEvent.ACTION_UP)
		{
			float pointerX = (e.getX() / Manager.screenWidth) * 2.0f - 1.0f;
			float pointerY = Manager.aspectRatio * ((e.getY() / Manager.screenHeight) * -2.0f + 1.0f);
			float halfWidth = width / 2.0f;
			float halfHeight = height / 2.0f;
			if(pointerX < (x + halfWidth) && pointerX > (x - halfWidth) &&
			   pointerY < (y + halfHeight) && pointerY > (y - halfHeight) && depressed)
			{
				retVal = true;
			}
			depressed = false;
		}
		
		if(actionCode == MotionEvent.ACTION_CANCEL)
		{
			depressed = false;
		}
		
		if(active == false)
		{
			retVal = false;
			depressed = false;
		}
		
		return retVal;
	}
	
	void SetUpColor(float fR, float fG, float fB, float fA)
	{
		rUp = fR;
		gUp = fG;
		bUp = fB;
		aUp = fA;
	}
	
	void SetDownColor(float fR, float fG, float fB, float fA)
	{
		rDown = fR;
		gDown = fG;
		bDown = fB;
		aDown = fA;
	}
	
	void Render()
	{
		if(active == false)depressed = false;
			
		if(depressed)
		{
			sprite.SetColor(rDown, gDown, bDown, aDown);
		}
		else
		{
			sprite.SetColor(rUp, gUp, bUp, aUp);
		}
		sprite.SetPosition(x, y);
		sprite.Render();
	}
}