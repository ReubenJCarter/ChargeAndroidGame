package com.ReubensGames.Charge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.lang.Math;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.ReubensGames.Charge.glAbstraction.Mesh;
import com.ReubensGames.Charge.glAbstraction.ShaderProgram;
import com.ReubensGames.Charge.glAbstraction.Texture;
import com.ReubensGames.Charge.physicsEngine.EulerParticle;
import com.ReubensGames.Charge.windowManager.Window;
import com.ReubensGames.Charge.windowManager.Manager;

public class LevelChooseMenu extends Window
{
	int saveState;

	int particleNumber;
	Texture particleTexture;
	ParticleSystem ps;
	EulerParticle[] ep;
	
	Texture stage1Texture;
	Sprite stage1Sprite;
	Texture stage2Texture;
	Sprite stage2Sprite;
	
	Texture lvl1ButtonTexture;
	Texture lvl2ButtonTexture;
	Texture lvl3ButtonTexture;
	Texture lvl4ButtonTexture;
	Texture lvl5ButtonTexture;
	Texture lvl6ButtonTexture;
	Texture lvl7ButtonTexture;
	Texture lvl8ButtonTexture;
	Texture lvl9ButtonTexture;
	Texture lvl10ButtonTexture;
	
	Button lvla1Button;
	Button lvla2Button;
	Button lvla3Button;
	Button lvla4Button;
	Button lvla5Button;
	Button lvla6Button;
	Button lvla7Button;
	Button lvla8Button;
	Button lvla9Button;
	Button lvla10Button;
	
	Button lvlb1Button;
	Button lvlb2Button;
	Button lvlb3Button;
	Button lvlb4Button;
	Button lvlb5Button;
	Button lvlb6Button;
	Button lvlb7Button;
	Button lvlb8Button;
	Button lvlb9Button;
	Button lvlb10Button;
	
	private float GetRandomFloat(float fMin, float fMax)
	{
		return fMin + ((float)Math.random() * (fMax - fMin));
	}
	
	public LevelChooseMenu()
	{
		
	}
	
	public void Create()
	{		
		stage1Texture = new Texture();
		stage1Texture.Load(context, R.drawable.stage1_title);
		stage1Sprite = new Sprite(stage1Texture, 0.0f, 1.2f, 1.0f, 1.0f * stage1Texture.GetAspectRatio());
		stage1Sprite.SetColor(1.0f, 1.0f, 1.0f, 0.8f);
		stage2Texture = new Texture();
		stage2Texture.Load(context, R.drawable.stage2_title);
		stage2Sprite = new Sprite(stage2Texture, 0.0f, 0.0f, 1.0f, 1.0f * stage2Texture.GetAspectRatio());
		stage2Sprite.SetColor(1.0f, 1.0f, 1.0f, 0.8f);

	
		lvl1ButtonTexture = new Texture();
		lvl1ButtonTexture.Load(context, R.drawable.lvl1_but);
		lvl2ButtonTexture = new Texture();
		lvl2ButtonTexture.Load(context, R.drawable.lvl2_but);
		lvl3ButtonTexture = new Texture();
		lvl3ButtonTexture.Load(context, R.drawable.lvl3_but);
		lvl4ButtonTexture = new Texture();
		lvl4ButtonTexture.Load(context, R.drawable.lvl4_but);
		lvl5ButtonTexture = new Texture();
		lvl5ButtonTexture.Load(context, R.drawable.lvl5_but);
		lvl6ButtonTexture = new Texture();
		lvl6ButtonTexture.Load(context, R.drawable.lvl6_but);
		lvl7ButtonTexture = new Texture();
		lvl7ButtonTexture.Load(context, R.drawable.lvl7_but);
		lvl8ButtonTexture = new Texture();
		lvl8ButtonTexture.Load(context, R.drawable.lvl8_but);
		lvl9ButtonTexture = new Texture();
		lvl9ButtonTexture.Load(context, R.drawable.lvl9_but);
		lvl10ButtonTexture = new Texture();
		lvl10ButtonTexture.Load(context, R.drawable.lvl10_but);
		
		lvla1Button = new Button(lvl1ButtonTexture, -0.8f, 0.8f, 0.4f, 0.4f * lvl1ButtonTexture.GetAspectRatio());
		lvla2Button = new Button(lvl2ButtonTexture, -0.4f, 0.8f, 0.4f, 0.4f * lvl2ButtonTexture.GetAspectRatio());
		lvla3Button = new Button(lvl3ButtonTexture, -0.0f, 0.8f, 0.4f, 0.4f * lvl3ButtonTexture.GetAspectRatio());
		lvla4Button = new Button(lvl4ButtonTexture, 0.4f, 0.8f, 0.4f, 0.4f * lvl4ButtonTexture.GetAspectRatio());
		lvla5Button = new Button(lvl5ButtonTexture, 0.8f, 0.8f, 0.4f, 0.4f * lvl5ButtonTexture.GetAspectRatio());
		lvla6Button = new Button(lvl6ButtonTexture, -0.8f, 0.4f, 0.4f, 0.4f * lvl6ButtonTexture.GetAspectRatio());
		lvla7Button = new Button(lvl7ButtonTexture, -0.4f, 0.4f, 0.4f, 0.4f * lvl7ButtonTexture.GetAspectRatio());
		lvla8Button = new Button(lvl8ButtonTexture, -0.0f, 0.4f, 0.4f, 0.4f * lvl8ButtonTexture.GetAspectRatio());
		lvla9Button = new Button(lvl9ButtonTexture, 0.4f, 0.4f, 0.4f, 0.4f * lvl9ButtonTexture.GetAspectRatio());
		lvla10Button = new Button(lvl10ButtonTexture, 0.8f, 0.4f, 0.4f, 0.4f * lvl10ButtonTexture.GetAspectRatio());
		
		lvlb1Button = new Button(lvl1ButtonTexture, -0.8f, -0.4f, 0.4f, 0.4f * lvl1ButtonTexture.GetAspectRatio());
		lvlb2Button = new Button(lvl2ButtonTexture, -0.4f, -0.4f, 0.4f, 0.4f * lvl2ButtonTexture.GetAspectRatio());
		lvlb3Button = new Button(lvl3ButtonTexture, -0.0f, -0.4f, 0.4f, 0.4f * lvl3ButtonTexture.GetAspectRatio());
		lvlb4Button = new Button(lvl4ButtonTexture, 0.4f, -0.4f, 0.4f, 0.4f * lvl4ButtonTexture.GetAspectRatio());
		lvlb5Button = new Button(lvl5ButtonTexture, 0.8f, -0.4f, 0.4f, 0.4f * lvl5ButtonTexture.GetAspectRatio());
		lvlb6Button = new Button(lvl6ButtonTexture, -0.8f, -0.8f, 0.4f, 0.4f * lvl6ButtonTexture.GetAspectRatio());
		lvlb7Button = new Button(lvl7ButtonTexture, -0.4f, -0.8f, 0.4f, 0.4f * lvl7ButtonTexture.GetAspectRatio());
		lvlb8Button = new Button(lvl8ButtonTexture, -0.0f, -0.8f, 0.4f, 0.4f * lvl8ButtonTexture.GetAspectRatio());
		lvlb9Button = new Button(lvl9ButtonTexture, 0.4f, -0.8f, 0.4f, 0.4f * lvl9ButtonTexture.GetAspectRatio());
		lvlb10Button = new Button(lvl10ButtonTexture, 0.8f, -0.8f, 0.4f, 0.4f * lvl10ButtonTexture.GetAspectRatio());
			
		particleNumber = 200;
		particleTexture = new Texture();
		particleTexture.Load(context, R.drawable.particle);
		ps = new ParticleSystem(particleNumber, particleTexture);
		ep = new EulerParticle[particleNumber];
		for(int i = 0; i < particleNumber; i++)
		{
			ep[i] = new EulerParticle();
			ps.SetSize(i, 0.25f, 0.25f);
			ps.SetColor(i, GetRandomFloat(0.0f, 1.0f), GetRandomFloat(0.0f, 1.0f), GetRandomFloat(0.0f, 1.0f), 1.0f);
			ps.SetVisible(i, true);
			
			float randX = GetRandomFloat(-1.0f, 1.0f);
			float randY = GetRandomFloat(-Manager.aspectRatio, Manager.aspectRatio);
			ps.SetPosition(i, randX, randY);
			ep[i].x = randX;
			ep[i].y = randY;
			ep[i].vx = 0.0f;
			ep[i].vy = 0.0f;
			ep[i].ax = 0.0f;
			ep[i].ay = 0.0f;
		}
	}
	
	public void Activate()
	{
		SharedPreferences userPref = context.getSharedPreferences("userPref", 0);
		saveState = userPref.getInt("saveState", 20);
		
		if(saveState < 21){lvla2Button.SetUpColor(0.6f, 0.6f, 0.6f, 1.0f);lvla2Button.active = false;}
		else 			  {lvla2Button.SetUpColor(1.0f, 1.0f, 1.0f, 1.0f);lvla2Button.active = true;}
		if(saveState < 22){lvla3Button.SetUpColor(0.6f, 0.6f, 0.6f, 1.0f);lvla3Button.active = false;}
		else 			  {lvla3Button.SetUpColor(1.0f, 1.0f, 1.0f, 1.0f);lvla3Button.active = true;}
		if(saveState < 23){lvla4Button.SetUpColor(0.6f, 0.6f, 0.6f, 1.0f);lvla4Button.active = false;}
		else 			  {lvla4Button.SetUpColor(1.0f, 1.0f, 1.0f, 1.0f);lvla4Button.active = true;}
		if(saveState < 24){lvla5Button.SetUpColor(0.6f, 0.6f, 0.6f, 1.0f);lvla5Button.active = false;}
		else 			  {lvla5Button.SetUpColor(1.0f, 1.0f, 1.0f, 1.0f);lvla5Button.active = true;}
		if(saveState < 25){lvla6Button.SetUpColor(0.6f, 0.6f, 0.6f, 1.0f);lvla6Button.active = false;}
		else 			  {lvla6Button.SetUpColor(1.0f, 1.0f, 1.0f, 1.0f);lvla6Button.active = true;}
		if(saveState < 26){lvla7Button.SetUpColor(0.6f, 0.6f, 0.6f, 1.0f);lvla7Button.active = false;}
		else 			  {lvla7Button.SetUpColor(1.0f, 1.0f, 1.0f, 1.0f);lvla7Button.active = true;}
		if(saveState < 27){lvla8Button.SetUpColor(0.6f, 0.6f, 0.6f, 1.0f);lvla8Button.active = false;}
		else 			  {lvla8Button.SetUpColor(1.0f, 1.0f, 1.0f, 1.0f);lvla8Button.active = true;}
		if(saveState < 28){lvla9Button.SetUpColor(0.6f, 0.6f, 0.6f, 1.0f);lvla9Button.active = false;}
		else 			  {lvla9Button.SetUpColor(1.0f, 1.0f, 1.0f, 1.0f);lvla9Button.active = true;}
		if(saveState < 29){lvla10Button.SetUpColor(0.6f, 0.6f, 0.6f, 1.0f);lvla10Button.active = false;}
		else 			  {lvla10Button.SetUpColor(1.0f, 1.0f, 1.0f, 1.0f);lvla10Button.active = true;}
		
		if(saveState < 30){lvlb1Button.SetUpColor(0.6f, 0.6f, 0.6f, 1.0f);lvlb1Button.active = false;}
		else 			  {lvlb1Button.SetUpColor(1.0f, 1.0f, 1.0f, 1.0f);lvlb1Button.active = true;}
		if(saveState < 31){lvlb2Button.SetUpColor(0.6f, 0.6f, 0.6f, 1.0f);lvlb2Button.active = false;}
		else 			  {lvlb2Button.SetUpColor(1.0f, 1.0f, 1.0f, 1.0f);lvlb2Button.active = true;}
		if(saveState < 32){lvlb3Button.SetUpColor(0.6f, 0.6f, 0.6f, 1.0f);lvlb3Button.active = false;}
		else 			  {lvlb3Button.SetUpColor(1.0f, 1.0f, 1.0f, 1.0f);lvlb3Button.active = true;}
		if(saveState < 33){lvlb4Button.SetUpColor(0.6f, 0.6f, 0.6f, 1.0f);lvlb4Button.active = false;}
		else 			  {lvlb4Button.SetUpColor(1.0f, 1.0f, 1.0f, 1.0f);lvlb4Button.active = true;}
		if(saveState < 34){lvlb5Button.SetUpColor(0.6f, 0.6f, 0.6f, 1.0f);lvlb5Button.active = false;}
		else 			  {lvlb5Button.SetUpColor(1.0f, 1.0f, 1.0f, 1.0f);lvlb5Button.active = true;}
		if(saveState < 35){lvlb6Button.SetUpColor(0.6f, 0.6f, 0.6f, 1.0f);lvlb6Button.active = false;}
		else 			  {lvlb6Button.SetUpColor(1.0f, 1.0f, 1.0f, 1.0f);lvlb6Button.active = true;}
		if(saveState < 36){lvlb7Button.SetUpColor(0.6f, 0.6f, 0.6f, 1.0f);lvlb7Button.active = false;}
		else 			  {lvlb7Button.SetUpColor(1.0f, 1.0f, 1.0f, 1.0f);lvlb7Button.active = true;}
		if(saveState < 37){lvlb8Button.SetUpColor(0.6f, 0.6f, 0.6f, 1.0f);lvlb8Button.active = false;}
		else 			  {lvlb8Button.SetUpColor(1.0f, 1.0f, 1.0f, 1.0f);lvlb8Button.active = true;}
		if(saveState < 38){lvlb9Button.SetUpColor(0.6f, 0.6f, 0.6f, 1.0f);lvlb9Button.active = false;}
		else 			  {lvlb9Button.SetUpColor(1.0f, 1.0f, 1.0f, 1.0f);lvlb9Button.active = true;}
		if(saveState < 39){lvlb10Button.SetUpColor(0.6f, 0.6f, 0.6f, 1.0f);lvlb10Button.active = false;}
		else 			  {lvlb10Button.SetUpColor(1.0f, 1.0f, 1.0f, 1.0f);lvlb10Button.active = true;}
	}
	
	public void Render()
	{
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		ps.Render();
		
		stage1Sprite.Render();
		stage2Sprite.Render();
		
		lvla1Button.Render();
		lvla2Button.Render();
		lvla3Button.Render();
		lvla4Button.Render();
		lvla5Button.Render();
		lvla6Button.Render();
		lvla7Button.Render();
		lvla8Button.Render();
		lvla9Button.Render();
		lvla10Button.Render();
		
		lvlb1Button.Render();
		lvlb2Button.Render();
		lvlb3Button.Render();
		lvlb4Button.Render();
		lvlb5Button.Render();
		lvlb6Button.Render();
		lvlb7Button.Render();
		lvlb8Button.Render();
		lvlb9Button.Render();
		lvlb10Button.Render();
		
		for(int i = 0; i < particleNumber; i++)
		{
			ep[i].AddFieldForce(0.0f, -0.0f, 0.0001f);
			ep[i].Update();
			if(ep[i].x > 1.0f) {ep[i].x = 1.0f; ep[i].vx = -ep[i].vx * 0.5f;}
			if(ep[i].x < -1.0f) {ep[i].x = -1.0f; ep[i].vx = -ep[i].vx * 0.5f;}
			if(ep[i].y > Manager.aspectRatio) {ep[i].y = Manager.aspectRatio; ep[i].vy = -ep[i].vy * 0.5f;}
			if(ep[i].y < -Manager.aspectRatio) {ep[i].y = -Manager.aspectRatio; ep[i].vy = -ep[i].vy * 0.5f;}
			ps.SetPosition(i, ep[i].x, ep[i].y);
		}
	}
	
	public boolean OnTouchEvent(MotionEvent e) 
	{
		if(lvla1Button.OnTouchEvent(e)) manager.Activate(20);
		if(lvla2Button.OnTouchEvent(e)) manager.Activate(21);
		if(lvla3Button.OnTouchEvent(e)) manager.Activate(22);
		if(lvla4Button.OnTouchEvent(e)) manager.Activate(23);
		if(lvla5Button.OnTouchEvent(e)) manager.Activate(24);
		if(lvla6Button.OnTouchEvent(e)) manager.Activate(25);
		if(lvla7Button.OnTouchEvent(e)) manager.Activate(26);
		if(lvla8Button.OnTouchEvent(e)) manager.Activate(27);
		if(lvla9Button.OnTouchEvent(e)) manager.Activate(28);
		if(lvla10Button.OnTouchEvent(e)) manager.Activate(29);
		
		if(lvlb1Button.OnTouchEvent(e)) manager.Activate(30);
		if(lvlb2Button.OnTouchEvent(e)) manager.Activate(31);
		if(lvlb3Button.OnTouchEvent(e)) manager.Activate(32);
		if(lvlb4Button.OnTouchEvent(e)) manager.Activate(33);
		if(lvlb5Button.OnTouchEvent(e)) manager.Activate(34);
		if(lvlb6Button.OnTouchEvent(e)) manager.Activate(35);
		if(lvlb7Button.OnTouchEvent(e)) manager.Activate(36);
		if(lvlb8Button.OnTouchEvent(e)) manager.Activate(37);
		if(lvlb9Button.OnTouchEvent(e)) manager.Activate(38);
		if(lvlb10Button.OnTouchEvent(e)) manager.Activate(39);

		return true;
	}
	
	public void OnBackPressed()
	{
		manager.Activate(0);
	}
}