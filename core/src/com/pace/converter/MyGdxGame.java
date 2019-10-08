package com.pace.converter;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.pace.converter.screens.LoadingScreen;

public class MyGdxGame extends Game implements ApplicationListener{
	public SpriteBatch batch;
	public AssetManager assets;
	public ActionResolver actionResolver;
	public BitmapFont fontCalorie;
	
	public MyGdxGame(ActionResolver actionResolver){
		this.actionResolver = actionResolver;
	}

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		assets = new AssetManager();
		
		fontCalorie = generateFont("Fonts/GOTHIC.TTF", 2*Gdx.graphics.getWidth()/9);
		
		this.setScreen(new LoadingScreen(this));
	}

	@Override
	public void render () {
		super.render();
		
		//if(Variables.ECRAN == ScreenEnum.Activity)
		//	actionResolver.showAds();
		//else actionResolver.hideAds();
		/*
		if(Donnees.getINTERSTITIAL_TRIGGER() < 1){
			Donnees.setINTERSTITIAL_TRIGGER(MathUtils.random(2,4));
			actionResolver.showOrLoadInterstital();
		}
		*/
	}
	
	public void dispose () {
		batch.dispose();
	}
	
	@Override
	public void pause() {
		super.pause();
	}
	
	public static BitmapFont generateFont(String fontPath, float size){
	    FileHandle fontFile = Gdx.files.internal(fontPath);
	    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
	    FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
	    params.genMipMaps = true;
	    params.magFilter = TextureFilter.MipMapLinearNearest;
	    params.minFilter = TextureFilter.MipMapLinearNearest;
	    params.size = (int)Math.ceil(size);
	    generator.scaleForPixelHeight((int)Math.ceil(size));
	    //params.characters = chars;
	    BitmapFont f = generator.generateFont(params);
	    //generator.dispose();
	    return f;
	}
}
