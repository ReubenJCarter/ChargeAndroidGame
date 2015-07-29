package com.ReubensGames.Charge;

import android.content.Context;
import android.opengl.GLES20;

import com.ReubensGames.Charge.glAbstraction.Mesh;
import com.ReubensGames.Charge.glAbstraction.ShaderProgram;
import com.ReubensGames.Charge.glAbstraction.Texture;
import com.ReubensGames.Charge.windowManager.Manager;

public class ParticleSystem
{
	Texture texture;
	Mesh mesh;
	static ShaderProgram shader;

	int particleNumberMax;
	int particleNumber;
	
	float[] posX; 
	float[] posY;
	float[] sizX; 
	float[] sizY;
	float[] rot;
	float[] colR;
	float[] colG;
	float[] colB;
	float[] colA;
	boolean[] visible;
	
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
	
	static public void CompileShader()
	{
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
	
	public ParticleSystem(int fNumber, Texture fTex)
	{
		texture = fTex;
		particleNumberMax = fNumber;
		//Create arrays
		posX = new float[particleNumberMax]; 
		posY = new float[particleNumberMax]; 
		sizX = new float[particleNumberMax]; 
		sizY = new float[particleNumberMax]; 
		rot = new float[particleNumberMax]; 
		colR = new float[particleNumberMax]; 
		colG = new float[particleNumberMax]; 
		colB = new float[particleNumberMax];
		colA = new float[particleNumberMax]; 		
		visible = new boolean[particleNumberMax]; 
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
		mesh.SetVertexBufferSize(particleNumberMax * 4, 8, 3);
		mesh.SetEachAttributeSize(2, 2, 4);
		mesh.SetTriangleBufferSize(particleNumberMax * 2);
		mesh.CreateBuffers();
	}

	public void SetPosition(int fIndex, float fX, float fY)
	{
		posX[fIndex] = fX; posY[fIndex] = fY;
	}
	
	public void SetSize(int fIndex, float fX, float fY)
	{
		sizX[fIndex] = fX; sizY[fIndex] = fY;
	}
	
	public void SetRotation(int fIndex, float fRot)
	{
		rot[fIndex] = fRot;
	}
	
	public void SetColor(int fIndex, float fR, float fG, float fB, float fA)
	{
		colR[fIndex] = fR; colG[fIndex] = fG; colB[fIndex] = fB; colA[fIndex] = fA;
	}
	
	public void SetVisible(int fIndex, boolean fVis)
	{
		visible[fIndex] = fVis;
	}
	
	public boolean GetVisible(int fIndex)
	{
		return visible[fIndex];
	}
	
	public void Render()
	{
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		particleNumber = 0;
		int index = 0;
		for(int i = 0; i < particleNumberMax; i++)
		{
			if(visible[i])
			{
				int offset = index * 4;
				int offset2 = index * 2;
				
				mesh.SetAttributeValue(offset,     0, (posX[i] - 0.5f * sizX[i]), (posY[i] - 0.5f * sizY[i]) / Manager.aspectRatio);
				mesh.SetAttributeValue(offset + 1, 0, (posX[i] + 0.5f * sizX[i]), (posY[i] - 0.5f * sizY[i]) / Manager.aspectRatio);
				mesh.SetAttributeValue(offset + 2, 0, (posX[i] + 0.5f * sizX[i]), (posY[i] + 0.5f * sizY[i]) / Manager.aspectRatio);
				mesh.SetAttributeValue(offset + 3, 0, (posX[i] - 0.5f * sizX[i]), (posY[i] + 0.5f * sizY[i]) / Manager.aspectRatio);
				
				mesh.SetAttributeValue(offset,     1, 0, 1);
				mesh.SetAttributeValue(offset + 1, 1, 1, 1);
				mesh.SetAttributeValue(offset + 2, 1, 1, 0);
				mesh.SetAttributeValue(offset + 3, 1, 0, 0);
				
				mesh.SetAttributeValue(offset,     2, colR[i], colG[i], colB[i], colA[i]);
				mesh.SetAttributeValue(offset + 1, 2, colR[i], colG[i], colB[i], colA[i]);
				mesh.SetAttributeValue(offset + 2, 2, colR[i], colG[i], colB[i], colA[i]);
				mesh.SetAttributeValue(offset + 3, 2, colR[i], colG[i], colB[i], colA[i]);
				
				mesh.SetTriangleValue(offset2,     offset, offset + 1, offset + 2);
				mesh.SetTriangleValue(offset2 + 1, offset, offset + 2, offset + 3);
				index++;
			}	
		}
		mesh.UpdateVertexBuffer();
		mesh.UpdateTriangleBuffer();
		ShaderProgram.Use(shader);
		//Bind texture
		GLES20.glActiveTexture(0);
		Texture.Bind(texture);
		//Render
		mesh.SetRenderNumber(2 * index);
		mesh.Render(shader.GetAttributeLocation(0), shader.GetAttributeLocation(1), shader.GetAttributeLocation(2));
		//Unbind
		Texture.Bind(0);
		ShaderProgram.Use(0);
	}
}