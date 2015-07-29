package com.ReubensGames.Charge.physicsEngine;

public class UniformGrid
{
	float voxelSize;
	int width;
	int halfWidth;
	int height;
	int halfHeight;
	int pairNumber;
	
	public UniformGrid()
	{
		width = 1000;
		height = 1000;
		halfWidth = width / 2;
		halfHeight = height / 2;
		voxelSize = 20.0f;
		pairNumber = 0;
	}
	
	private int Hash(float fX, float fY)
	{
		return ((int)(fY / voxelSize) + halfHeight) * width + (int)(fX / voxelSize) + halfWidth;
	}
	
	
}