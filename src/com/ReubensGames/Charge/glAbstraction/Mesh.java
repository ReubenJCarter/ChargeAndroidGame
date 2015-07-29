package com.ReubensGames.Charge.glAbstraction;

import android.opengl.GLES20;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ByteOrder;
import java.nio.ByteBuffer;

public class Mesh
{
	private int attributeNumber;
	private int[] attribSize;
	private int[] attribOffset;
	private int vertexSize;
	private int vertexNumber;
	private int triangleNumber;
	private int renderNumber;
	private FloatBuffer vertexArray;
	private IntBuffer triangleArray;
	private int[] vertexBuffer;
	private int[] triangleBuffer;
	
	public Mesh()
	{
		vertexBuffer = new int[1];
		triangleBuffer = new int[1];
	}	
	
	public void SetAttributeValue(int fIndex, int fAttrib, float fV)
	{
		int offset = fIndex * vertexSize + attribOffset[fAttrib];
		vertexArray.put(offset, fV);
	}
	
	public void SetAttributeValue(int fIndex, int fAttrib, float fV0, float fV1)
	{
		int offset = fIndex * vertexSize + attribOffset[fAttrib];
		vertexArray.put(offset, fV0);
		vertexArray.put(offset + 1, fV1);
	}
	
	public void SetAttributeValue(int fIndex, int fAttrib, float fV0, float fV1, float fV2)
	{
		int offset = fIndex * vertexSize + attribOffset[fAttrib];
		vertexArray.put(offset, fV0);
		vertexArray.put(offset + 1, fV1);
		vertexArray.put(offset + 2, fV2);
	}
	
	public void SetAttributeValue(int fIndex, int fAttrib, float fV0, float fV1, float fV2, float fV3)
	{
		int offset = fIndex * vertexSize + attribOffset[fAttrib];
		vertexArray.put(offset, fV0);
		vertexArray.put(offset + 1, fV1);
		vertexArray.put(offset + 2, fV2);
		vertexArray.put(offset + 3, fV3);
	}
	
	public void SetTriangleValue(int fIndex, int fA, int fB, int fC)
	{
		int offset = fIndex * 3;
		triangleArray.put(offset, fA);
		triangleArray.put(offset + 1, fB);
		triangleArray.put(offset + 2, fC);
	}
	
	public void SetVertexBufferSize(int fVertexNumber, int fVertexSize, int fAttribNumber)
	{
		vertexNumber = fVertexNumber;
		vertexSize = fVertexSize;
		attributeNumber = fAttribNumber;
		attribSize = new int[fAttribNumber];
		attribOffset = new int[fAttribNumber];
		ByteBuffer bb = ByteBuffer.allocateDirect(fVertexNumber * fVertexSize * 4);
		bb.order(ByteOrder.nativeOrder());
		vertexArray = bb.asFloatBuffer();
	}
	
	public void SetTriangleBufferSize(int fSize)
	{
		triangleNumber = fSize;
		renderNumber = triangleNumber;
		ByteBuffer bb = ByteBuffer.allocateDirect(3 * fSize * 4);
		bb.order(ByteOrder.nativeOrder());
		triangleArray = bb.asIntBuffer();
	}
	
	public void SetEachAttributeSize(int fSize)
	{
		attribSize[0] = fSize;
		attribOffset[0] = 0;
	}
	
	public void SetEachAttributeSize(int fSize0, int fSize1)
	{
		attribSize[0] = fSize0;
		attribSize[1] = fSize1;
		attribOffset[0] = 0;
		attribOffset[1] = fSize0;
	}
	
	public void SetEachAttributeSize(int fSize0, int fSize1, int fSize2)
	{
		attribSize[0] = fSize0;
		attribSize[1] = fSize1;
		attribSize[2] = fSize2;
		attribOffset[0] = 0;
		attribOffset[1] = fSize0;
		attribOffset[2] = fSize0 + fSize1;
	}
	
	public void SetEachAttributeSize(int fSize0, int fSize1, int fSize2, int fSize3)
	{
		attribSize[0] = fSize0;
		attribSize[1] = fSize1;
		attribSize[2] = fSize2;
		attribSize[3] = fSize3;
		attribOffset[0] = 0;
		attribOffset[1] = fSize0;
		attribOffset[2] = fSize0 + fSize1;
		attribOffset[3] = fSize0 + fSize1 + fSize2;
	}
	
	public void SetEachAttributeSize(int fSize0, int fSize1, int fSize2, int fSize3, int fSize4)
	{
		attribSize[0] = fSize0;
		attribSize[1] = fSize1;
		attribSize[2] = fSize2;
		attribSize[3] = fSize3;
		attribSize[4] = fSize4;
		attribOffset[0] = 0;
		attribOffset[1] = fSize0;
		attribOffset[2] = fSize0 + fSize1;
		attribOffset[3] = fSize0 + fSize1 + fSize2;
		attribOffset[4] = fSize0 + fSize1 + fSize2 + fSize3;
	}
	
	public void CreateBuffers()
	{
		GLES20.glGenBuffers(1, vertexBuffer, 0);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBuffer[0]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexNumber * vertexSize * 4, vertexArray, GLES20.GL_STREAM_DRAW);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		
		GLES20.glGenBuffers(1, triangleBuffer, 0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, triangleBuffer[0]);
		GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, triangleNumber * 3 * 4, triangleArray, GLES20.GL_STREAM_DRAW);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
		
	}
	
	public void UpdateVertexBuffer()
	{
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBuffer[0]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexNumber * vertexSize * 4, vertexArray, GLES20.GL_STREAM_DRAW);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
	}

	public void UpdateTriangleBuffer()
	{
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, triangleBuffer[0]);
		GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, triangleNumber * 3 * 4, triangleArray, GLES20.GL_STREAM_DRAW);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void UpdateBuffers()
	{
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBuffer[0]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexNumber * vertexSize * 4, vertexArray, GLES20.GL_STREAM_DRAW);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, triangleBuffer[0]);
		GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, triangleNumber * 3 * 4, triangleArray, GLES20.GL_STREAM_DRAW);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public void DestroyBuffer()
	{
		GLES20.glDeleteBuffers(1, vertexBuffer, 0);
		GLES20.glDeleteBuffers(1, triangleBuffer, 0);
	}

	public void SetRenderNumber(int fNumber)
	{
		renderNumber = fNumber;
	}
	
	public void Render(int fAttribLocation0)
	{
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBuffer[0]);
		GLES20.glEnableVertexAttribArray(fAttribLocation0);
		GLES20.glVertexAttribPointer(fAttribLocation0, attribSize[0], GLES20.GL_FLOAT, false, vertexSize * 4, attribOffset[0] * 4);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, triangleBuffer[0]);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, 3 * renderNumber, GLES20.GL_UNSIGNED_INT, 0);
		GLES20.glDisableVertexAttribArray(fAttribLocation0);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void Render(int fAttribLocation0, int fAttribLocation1)
	{
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBuffer[0]);
		GLES20.glEnableVertexAttribArray(fAttribLocation0);
		GLES20.glEnableVertexAttribArray(fAttribLocation1);
		GLES20.glVertexAttribPointer(fAttribLocation0, attribSize[0], GLES20.GL_FLOAT, false, vertexSize * 4, attribOffset[0] * 4);
		GLES20.glVertexAttribPointer(fAttribLocation1, attribSize[1], GLES20.GL_FLOAT, false, vertexSize * 4, attribOffset[1] * 4);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, triangleBuffer[0]);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, 3 * renderNumber, GLES20.GL_UNSIGNED_INT, 0);
		GLES20.glDisableVertexAttribArray(fAttribLocation0);
		GLES20.glDisableVertexAttribArray(fAttribLocation1);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void Render(int fAttribLocation0, int fAttribLocation1, int fAttribLocation2)
	{
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBuffer[0]);
		GLES20.glEnableVertexAttribArray(fAttribLocation0);
		GLES20.glEnableVertexAttribArray(fAttribLocation1);
		GLES20.glEnableVertexAttribArray(fAttribLocation2);
		GLES20.glVertexAttribPointer(fAttribLocation0, attribSize[0], GLES20.GL_FLOAT, false, vertexSize * 4, attribOffset[0] * 4);
		GLES20.glVertexAttribPointer(fAttribLocation1, attribSize[1], GLES20.GL_FLOAT, false, vertexSize * 4, attribOffset[1] * 4);
		GLES20.glVertexAttribPointer(fAttribLocation2, attribSize[2], GLES20.GL_FLOAT, false, vertexSize * 4, attribOffset[2] * 4);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, triangleBuffer[0]);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, 3 * renderNumber, GLES20.GL_UNSIGNED_INT, 0);
		GLES20.glDisableVertexAttribArray(fAttribLocation0);
		GLES20.glDisableVertexAttribArray(fAttribLocation1);
		GLES20.glDisableVertexAttribArray(fAttribLocation2);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void Render(int fAttribLocation0, int fAttribLocation1, int fAttribLocation2, int fAttribLocation3)
	{
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBuffer[0]);
		GLES20.glEnableVertexAttribArray(fAttribLocation0);
		GLES20.glEnableVertexAttribArray(fAttribLocation1);
		GLES20.glEnableVertexAttribArray(fAttribLocation2);
		GLES20.glEnableVertexAttribArray(fAttribLocation3);
		GLES20.glVertexAttribPointer(fAttribLocation0, attribSize[0], GLES20.GL_FLOAT, false, vertexSize * 4, attribOffset[0] * 4);
		GLES20.glVertexAttribPointer(fAttribLocation1, attribSize[1], GLES20.GL_FLOAT, false, vertexSize * 4, attribOffset[1] * 4);
		GLES20.glVertexAttribPointer(fAttribLocation2, attribSize[2], GLES20.GL_FLOAT, false, vertexSize * 4, attribOffset[2] * 4);
		GLES20.glVertexAttribPointer(fAttribLocation3, attribSize[3], GLES20.GL_FLOAT, false, vertexSize * 4, attribOffset[3] * 4);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, triangleBuffer[0]);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, 3 * renderNumber, GLES20.GL_UNSIGNED_INT, 0);
		GLES20.glDisableVertexAttribArray(fAttribLocation0);
		GLES20.glDisableVertexAttribArray(fAttribLocation1);
		GLES20.glDisableVertexAttribArray(fAttribLocation2);
		GLES20.glDisableVertexAttribArray(fAttribLocation3);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void Render(int[] fAttribLocation)
	{
		for(int i = 0; i < attributeNumber; i++)
		{
			GLES20.glEnableVertexAttribArray(fAttribLocation[i]);
		}

		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBuffer[0]);
		
		for(int i = 0; i < attributeNumber; i++)
		{
			GLES20.glVertexAttribPointer(fAttribLocation[i], attribSize[i], GLES20.GL_FLOAT, false, vertexSize * 4, attribOffset[i] * 4);
		}
		
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, triangleBuffer[0]);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, 3 * renderNumber, GLES20.GL_UNSIGNED_INT, 0);
		
		for(int i = 0; i < attributeNumber; i++)
		{
			GLES20.glDisableVertexAttribArray(fAttribLocation[i]);
		}
		
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void RenderArray(int[] fAttribLocation)
	{
		
	}
	
}