package com.pace.converter;

import com.badlogic.gdx.Gdx;
import com.pace.converter.enums.ScreenEnum;

public class Variables {

	public static boolean TIMER_ACTIF = false;
	public static ScreenEnum ECRAN; 
	public static int ONGLET_X1 = 0;
	public static int ONGLET_X2 = (int)(Gdx.graphics.getWidth()/3);
	public static int ONGLET_X3 = (int)(2*Gdx.graphics.getWidth()/3);
	public static int ONGLET_HEIGHT = (int)(0.058f*Gdx.graphics.getHeight());
	public static int ONGLET_Y = (int)(Gdx.graphics.getHeight() - ONGLET_HEIGHT);
	public static int ONGLET_WIDTH = (int)(Gdx.graphics.getWidth()/3); 
}
