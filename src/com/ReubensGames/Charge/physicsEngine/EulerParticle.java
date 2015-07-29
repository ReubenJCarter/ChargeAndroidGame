package com.ReubensGames.Charge.physicsEngine;

public class EulerParticle 
{
	public float x;
	public float y; 
	public float vx;
	public float vy;
	public float ax;
	public float ay;
	public float mass;
	
	public EulerParticle()
	{
		mass = 1;
		ax = 0;
		ay = 0;
		vx = 0;
		vy = 0;
		x = 0;
		y = 0;
	}
	
	public void AddForce(float fX, float fY)
	{
		ax += fX / mass;
		ay += fY / mass;
	}
	
	public void AddFieldForce(float fX, float fY, float fC)
	{
		float distX = fX - x;
		float distY = fY - y;
		float dist = (float)Math.sqrt(distX * distX + distY * distY);
		if(dist > 0)
		{
			distX = (distX / (dist * dist)) * fC;
			distY = (distY / (dist * dist)) * fC;
			ax += distX / mass;
			ay += distY / mass;
		}
	}
	
	public void Update()
	{
		vx += ax;
		vy += ay;
		x += vx; 
		y += vy;
		ax = 0;
		ay = 0;
	}
}