package com.ReubensGames.Charge.glAbstraction;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;

public class Texture
{
	private int[] index;
	private float width;
	private float height;
	private float aspectRatio;
	
	public Texture()
	{
		index = new int[1];
		width = 0;
		height = 0;
	}
	
	public float GetWidth()
	{
		return width;
	}
	
	public float GetHeight()
	{
		return height;
	}
	
	public float GetAspectRatio()
	{
		return aspectRatio;
	}
	
	public int GetIndex()
	{
		return index[0];
	}
	
	public static void Bind(Texture fTexture)
	{
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, fTexture.index[0]);
	}
	
	public static void Bind(int fTexture)
	{
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, fTexture);
	}
	
	public void Load(Context fContext, int fResourceId)
	{
		GLES20.glGenTextures(1, index, 0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, index[0]);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
		Bitmap bitmap = BitmapFactory.decodeResource(fContext.getResources(), fResourceId);
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		aspectRatio = height / width;
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0); 
		GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
	}
}
