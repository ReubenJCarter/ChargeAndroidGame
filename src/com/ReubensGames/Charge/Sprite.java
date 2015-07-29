package com.ReubensGames.Charge;

import android.content.Context;
import android.opengl.GLES20;

import com.ReubensGames.Charge.glAbstraction.Mesh;
import com.ReubensGames.Charge.glAbstraction.ShaderProgram;
import com.ReubensGames.Charge.glAbstraction.Texture;
import com.ReubensGames.Charge.windowManager.Manager;

public class Sprite
{
	Texture texture;
	Mesh mesh;
	static ShaderProgram shader;
	
	float x; 
	float y;
	float sizX; 
	float sizY;
	float rot;
	float colR;
	float colG;
	float colB;
	float colA;
	boolean visible;
	
	private static String vsSrc = 
	"attribute vec2 vPosition;" +
	"attribute vec2 vTexco;" +
	"attribute vec4 vCol;" +
	"varying vec2 fTexco;" +
	"varying vec4 fCol;" +
    "void main() {" +
	"  fTexco = vTexco;" +
	"  fCol = vCol;" +
    "  gl_Position = vec4(vPosition.x, vPosition.y, 0.0, 1.0);" +
    "}";
	
	private static String fsSrc = 
	"precision mediump float;" +
	"uniform sampler2D tex;" +
	"varying vec2 fTexco;" +
	"varying vec4 fCol;" +
    "void main() {" +
    "  gl_FragColor = texture2D(tex, fTexco) * fCol;" +
    "}";
	
	public static void CompileShader()
	{
		//Compile Shader
		shader = new ShaderProgram();
		shader.Compile(vsSrc, fsSrc);
		shader.SetAttributeNumber(3);
		shader.SetSamplerNumber(1);
		ShaderProgram.Use(shader);
		shader.SetAttributeLocation(0, "vPosition");
		shader.SetAttributeLocation(1, "vTexco");
		shader.SetAttributeLocation(2, "vCol");
		shader.SetSamplerLocation(0, "tex");
		ShaderProgram.Use(0);
	}
	
	public Sprite(Texture fTex)
	{
		texture = fTex;
		x = 0.0f; 
		y = 0.0f; 
		sizX = 1.0f; 
		sizY = 1.0f; 
		rot = 0.0f; 
		colR = 1.0f; 
		colG = 1.0f; 
		colB = 1.0f;
		colA = 1.0f; 		
		visible = true; 
		//Compile Shader
		/*
		shader = new ShaderProgram();
		shader.Compile(vsSrc, fsSrc);
		shader.SetAttributeNumber(3);
		shader.SetSamplerNumber(1);
		ShaderProgram.Use(shader);
		shader.SetAttributeLocation(0, "vPosition");
		shader.SetAttributeLocation(1, "vTexco");
		shader.SetAttributeLocation(2, "vCol");
		shader.SetSamplerLocation(0, "tex");
		ShaderProgram.Use(0);
		*/
		//Create Mesh
		mesh = new Mesh();
		mesh.SetVertexBufferSize(4, 8, 3);
		mesh.SetEachAttributeSize(2, 2, 4);
		mesh.SetTriangleBufferSize(2);
		mesh.CreateBuffers();
	}
	
	public Sprite(Texture fTex, float fX, float fY, float fWidth, float fHeight)
	{
		texture = fTex;
		x = fX; 
		y = fY; 
		sizX = fWidth; 
		sizY = fHeight; 
		rot = 0.0f; 
		colR = 1.0f; 
		colG = 1.0f; 
		colB = 1.0f;
		colA = 1.0f; 		
		visible = true; 
		//Compile Shader
		/*
		shader = new ShaderProgram();
		shader.Compile(vsSrc, fsSrc);
		shader.SetAttributeNumber(3);
		shader.SetSamplerNumber(1);
		ShaderProgram.Use(shader);
		shader.SetAttributeLocation(0, "vPosition");
		shader.SetAttributeLocation(1, "vTexco");
		shader.SetAttributeLocation(2, "vCol");
		shader.SetSamplerLocation(0, "tex");
		ShaderProgram.Use(0);
		*/
		//Create Mesh
		mesh = new Mesh();
		mesh.SetVertexBufferSize(4, 8, 3);
		mesh.SetEachAttributeSize(2, 2, 4);
		mesh.SetTriangleBufferSize(2);
		mesh.CreateBuffers();
	}

	public void SetPosition(float fX, float fY)
	{
		x = fX; y = fY;
	}
	
	public void SetSize(float fX, float fY)
	{
		sizX = fX; sizY = fY;
	}
	
	public void SetRotation(float fRot)
	{
		rot = fRot;
	}
	
	public void SetColor(float fR, float fG, float fB, float fA)
	{
		colR = fR; colG = fG; colB = fB; colA = fA;
	}
	
	public void SetVisible(boolean fVis)
	{
		visible = fVis;
	}
	
	public void Render() 
	{
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		if(visible)
		{
			float sinRot = (float)Math.sin(rot);
			float cosRot = (float)Math.cos(rot);
		
			mesh.SetAttributeValue(0, 0, x + (-0.5f * sizX) * cosRot + (-0.5f * sizY) * -sinRot,   
										 (y + (-0.5f * sizX) * sinRot + (-0.5f * sizY) * cosRot) / Manager.aspectRatio);
			mesh.SetAttributeValue(1, 0, x + (0.5f * sizX) * cosRot + (-0.5f * sizY) * -sinRot, 
										 (y + (0.5f * sizX) * sinRot + (-0.5f * sizY) * cosRot) / Manager.aspectRatio);
			mesh.SetAttributeValue(2, 0, x + (0.5f * sizX) * cosRot + (0.5f * sizY) * -sinRot, 
										 (y + (0.5f * sizX) * sinRot + (0.5f * sizY) * cosRot) / Manager.aspectRatio);
			mesh.SetAttributeValue(3, 0, x + (-0.5f * sizX) * cosRot + (0.5f * sizY) * -sinRot, 
										 (y + (-0.5f * sizX) * sinRot + (0.5f * sizY) * cosRot) / Manager.aspectRatio);
			
			mesh.SetAttributeValue(0, 1, 0, 1);
			mesh.SetAttributeValue(1, 1, 1, 1);
			mesh.SetAttributeValue(2, 1, 1, 0);
			mesh.SetAttributeValue(3, 1, 0, 0);
			
			mesh.SetAttributeValue(0, 2, colR, colG, colB, colA);
			mesh.SetAttributeValue(1, 2, colR, colG, colB, colA);
			mesh.SetAttributeValue(2, 2, colR, colG, colB, colA);
			mesh.SetAttributeValue(3, 2, colR, colG, colB, colA);
			
			mesh.SetTriangleValue(0, 0, 1, 2);
			mesh.SetTriangleValue(1, 0, 2, 3);
		}
		mesh.UpdateVertexBuffer();
		mesh.UpdateTriangleBuffer();
		ShaderProgram.Use(shader);
		//Bind texture
		GLES20.glActiveTexture(0);
		Texture.Bind(texture);
		//Render
		mesh.Render(shader.GetAttributeLocation(0), shader.GetAttributeLocation(1), shader.GetAttributeLocation(2));
		//Unbind
		Texture.Bind(0);
		ShaderProgram.Use(0);
	}
}