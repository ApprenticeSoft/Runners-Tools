package com.pace.converter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pace.converter.Donnees;
import com.pace.converter.MyGdxGame;
import com.pace.converter.ScrollPaneList;
import com.pace.converter.Variables;
import com.pace.converter.enums.ScreenEnum;

public class CalorieScreen implements Screen{

	final MyGdxGame game;
	OrthographicCamera camera;
	private Stage stage;
	private LabelStyle labelStyle, labelStyleOnglet, labelStyleTitre, labelStyleCalorie, labelStyleUnité;
	private Label labelOngletConverter, labelOngletCalorie, labelOngletInterval,
					labelPoids, labelPoidsUnité, labelDistance, labelDistanceUnité, labelCalorie, labelCalorieCalculée;
	private Skin skin, menuSkin;
	private TextureAtlas menuAtlas;
	private Table tablePoids, tableDistance;
	private ScrollPaneList scrollPaneListPoids, scrollPaneListDistance;
	private ShapeRenderer srOnglet1, srOnglet2; 
	private Image image1, image2;
	boolean initPoids;
	private ImageButton optionBouton;
	private ImageButtonStyle optionBoutonStyle;
	private String stringOngletConverter1, stringOngletCalorie1, stringOngletInterval1, stringPoids, stringDistance;
	
	
	float chiffreDistance, chiffrePoids;
	int ONGLET_HEIGHT, ONGLET_Y;
	
	public CalorieScreen(final MyGdxGame gam) {
		game = gam;
		Variables.ECRAN = ScreenEnum.Calorie;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		/**************************DIFFERENTES LANGUES****************************/
		switch(Donnees.getLangage()){
			case 1 :
				stringOngletConverter1 = new String("SPEED/PACE");
				stringOngletCalorie1 = new String("CALORIES");
				stringOngletInterval1 = new String("INTERVAL");
				stringPoids = new String("Weight");
				stringDistance = new String("Distance");
				break;
			case 2 :
				stringOngletConverter1 = new String("VITESSE/ CADENCE");
				stringOngletCalorie1 = new String("CALORIES");
				stringOngletInterval1 = new String("INTERVALLES");
				stringPoids = new String("Poids");
				stringDistance = new String("Distance");	
				break;
			case 3 :
				stringOngletConverter1 = new String("VELOCIDAD/ RITMO");
				stringOngletCalorie1 = new String("CALORIAS");
				stringOngletInterval1 = new String("INTERVALO");
				stringPoids = new String("Peso");
				stringDistance = new String("Distancia");			
				break;
			default :
				stringOngletConverter1 = new String("SPEED/PACE");
				stringOngletCalorie1 = new String("CALORIES");
				stringOngletInterval1 = new String("INTERVAL");
				stringPoids = new String("Weight");
				stringDistance = new String("Distance");
		}	
		/*************************************************************************/
		
		menuSkin = new Skin();
		menuAtlas = game.assets.get("Images.pack", TextureAtlas.class);
		menuSkin.addRegions(menuAtlas);
		
		stage = new Stage();
		
		initPoids = false;
		
		ONGLET_HEIGHT = (int)(0.07f*Gdx.graphics.getHeight());
		ONGLET_Y = (int)(Gdx.graphics.getHeight() - ONGLET_HEIGHT);
			
		image1 = new Image(menuSkin.getDrawable("CelluleVitesse"));
		image1.setWidth(0.399f*Gdx.graphics.getWidth());
		image1.setHeight(0.112f*Gdx.graphics.getHeight());
		image1.setX(0.065f*Gdx.graphics.getWidth());
		image1.setY(0.662f*Gdx.graphics.getHeight());
		image2 = new Image(menuSkin.getDrawable("CelluleVitesse"));
		image2.setWidth(image1.getWidth());
		image2.setHeight(image1.getHeight());
		image2.setX(0.539f*Gdx.graphics.getWidth());
		image2.setY(image1.getY());
	
		labelStyle = new LabelStyle(game.assets.get("font1.ttf", BitmapFont.class), Color.WHITE);
		labelStyleUnité = new LabelStyle(game.assets.get("font1.ttf", BitmapFont.class), Color.BLACK);
		labelStyleTitre = new LabelStyle(game.assets.get("font2.ttf", BitmapFont.class), Color.BLACK);
		labelStyleOnglet = new LabelStyle(game.assets.get("fontOnglet.ttf", BitmapFont.class), Color.WHITE);
		labelStyleCalorie = new LabelStyle(game.assets.get("fontCalorie.ttf", BitmapFont.class), Color.WHITE);
		
		labelPoids = new Label(stringPoids, labelStyleTitre);
		labelPoids.setX(image1.getX() + image1.getWidth()/2 - labelPoids.getWidth()/2);
		labelPoids.setY(image1.getY() + 3*image1.getHeight()/4 - labelPoids.getHeight()/2);
		labelDistance = new Label(stringDistance, labelStyleTitre);
		labelDistance.setX(image2.getX() + image2.getWidth()/2 - labelDistance.getWidth()/2);
		labelDistance.setY(image2.getY() + 3*image2.getHeight()/4 - labelDistance.getHeight()/2);
		labelCalorieCalculée = new Label("0", labelStyleCalorie);
		labelCalorieCalculée.setX(Gdx.graphics.getWidth()/2 - labelCalorieCalculée.getWidth()/2);
		labelCalorieCalculée.setY(3*image1.getY()/5 - labelCalorieCalculée.getHeight()/2);
		labelCalorieCalculée.setWidth(game.assets.get("fontCalorie.ttf", BitmapFont.class).getBounds(labelCalorieCalculée.getText()).width);
		labelCalorie = new Label("kcal", labelStyle);
		labelCalorie.setX(Gdx.graphics.getWidth()/2 - labelCalorie.getWidth()/2);
		labelCalorie.setY(labelCalorieCalculée.getY() - Gdx.graphics.getHeight()/40);
		
		/*****************************SÉLECTION DES DONNÉES*********************************/	
		scrollPaneListPoids = new ScrollPaneList(game.assets.get("font1.ttf", BitmapFont.class), 500, Color.BLACK);
		scrollPaneListPoids.setSelectedIndex(Donnees.getPoids());
		scrollPaneListPoids.setScrollY(scrollPaneListPoids.getItemHeight() * Donnees.getPoids());
		labelPoidsUnité = new Label(Donnees.getPoidsUnite(), labelStyleUnité);
		
		tablePoids = new Table();
		tablePoids.defaults().height(scrollPaneListPoids.getItemHeight()).top().pad(3);
		tablePoids.add(scrollPaneListPoids.getScrollPane()).width(scrollPaneListPoids.getWidth());
		tablePoids.add(labelPoidsUnité).height(scrollPaneListPoids.getItemHeight()).width(game.assets.get("font1.ttf", BitmapFont.class).getBounds(labelPoidsUnité.getText()).width).top();
		tablePoids.setFillParent(true);
		tablePoids.setX((tablePoids.getPrefWidth()/2 - Gdx.graphics.getWidth()/2) + image1.getX() + image1.getWidth()/2 - tablePoids.getPrefWidth()/2);
		tablePoids.setY((tablePoids.getPrefHeight()/2 - Gdx.graphics.getHeight()/2) + image1.getY() + image1.getHeight()/4 - 0.5f*tablePoids.getPrefHeight());
		
		scrollPaneListDistance = new ScrollPaneList(game.assets.get("font1.ttf", BitmapFont.class), 10000, Color.BLACK);;
		labelDistanceUnité = new Label(Donnees.getDistanceUnite(), labelStyleUnité);
		tableDistance = new Table();
		tableDistance.defaults().height(scrollPaneListDistance.getItemHeight()).top().pad(3);
		tableDistance.add(scrollPaneListDistance.getScrollPane()).width(scrollPaneListDistance.getWidth());
		tableDistance.add(labelDistanceUnité).height(scrollPaneListDistance.getItemHeight()).width(game.assets.get("font1.ttf", BitmapFont.class).getBounds(labelDistanceUnité.getText()).width).top();
		tableDistance.setFillParent(true);
		tableDistance.setX((tableDistance.getPrefWidth()/2 - Gdx.graphics.getWidth()/2) + image2.getX() + image2.getWidth()/2 - tableDistance.getPrefWidth()/2);
		tableDistance.setY((tableDistance.getPrefHeight()/2 - Gdx.graphics.getHeight()/2) + image2.getY() + image2.getHeight()/4 - 0.5f*tableDistance.getPrefHeight());
		/***********************************************************************************/
		
		/************************************ONGLETS****************************************/
		labelOngletConverter = new Label(stringOngletConverter1, labelStyleOnglet);
		labelOngletConverter.setWidth(Variables.ONGLET_WIDTH);
		labelOngletConverter.setX(Variables.ONGLET_X2/2 - labelOngletConverter.getWidth()/2);
		labelOngletConverter.setY(ONGLET_Y + 2*ONGLET_HEIGHT/4 - labelOngletConverter.getHeight()/2);
		labelOngletConverter.setAlignment(Align.center);
		labelOngletConverter.setWrap(true);
		
		labelOngletCalorie = new Label(stringOngletCalorie1, labelStyleOnglet);
		labelOngletCalorie.setX(Variables.ONGLET_X2 + Variables.ONGLET_WIDTH/2 - labelOngletCalorie.getWidth()/2);
		labelOngletCalorie.setY(ONGLET_Y + 2*ONGLET_HEIGHT/4 - labelOngletCalorie.getHeight()/2);

		labelOngletInterval = new Label(stringOngletInterval1, labelStyleOnglet);
		labelOngletInterval.setX(Variables.ONGLET_X3 + Variables.ONGLET_WIDTH/2 - labelOngletInterval.getWidth()/2);
		labelOngletInterval.setY(ONGLET_Y + 2*ONGLET_HEIGHT/4 - labelOngletInterval.getHeight()/2);
		
		srOnglet1 = new ShapeRenderer();
		srOnglet2 = new ShapeRenderer();
		/***********************************************************************************/

		/**********************************BOUTON OPTION************************************/
		optionBoutonStyle = new ImageButtonStyle();
		optionBoutonStyle.imageUp = menuSkin.getDrawable("Option");
		optionBoutonStyle.imageDown = menuSkin.getDrawable("OptionChecked");
		optionBouton = new ImageButton(optionBoutonStyle);
		optionBouton.setWidth(Gdx.graphics.getWidth()/10);
		optionBouton.setHeight(optionBouton.getWidth());
		optionBouton.setX(9*Gdx.graphics.getWidth()/10 - optionBouton.getWidth()/2);
		optionBouton.setY(Gdx.graphics.getWidth()/10 - optionBouton.getWidth()/2);
		/***********************************************************************************/
		
		stage.addActor(image1);
		stage.addActor(image2);
		stage.addActor(labelOngletConverter);
		stage.addActor(labelOngletCalorie);
		stage.addActor(labelOngletInterval);
		stage.addActor(labelPoids);
		stage.addActor(labelDistance);
		stage.addActor(labelCalorie);
		stage.addActor(labelCalorieCalculée);
		stage.addActor(tablePoids);
		stage.addActor(tableDistance);
		stage.addActor(optionBouton);
	}

	@Override
	public void render(float delta) {
		//Gdx.gl.glClearColor(0.355f, 0.605f, 0.832f, 0);
		Gdx.gl.glClearColor(0.383f, 0.453f, 0.508f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.setProjectionMatrix(camera.combined);
		
		//Permet d'initialiser le scrollPane à la valeur enregistrée
		if(!initPoids && scrollPaneListPoids.getScrollY() != (scrollPaneListPoids.getItemHeight() * Donnees.getPoids()) && Donnees.getPoids() != 199){
			scrollPaneListPoids.setSelectedIndex(Donnees.getPoids());
			scrollPaneListPoids.setScrollY(scrollPaneListPoids.getItemHeight() * Donnees.getPoids());
			if (scrollPaneListPoids.getScrollY() != (scrollPaneListPoids.getItemHeight() * Donnees.getPoids())) initPoids = false;
			else initPoids = true;
		}
		else scrollPaneListPoids.actif();
		
		scrollPaneListDistance.actif();
		
		/***********************************SHAPERENDERER***********************************/
		srOnglet1.begin(ShapeType.Filled);
		//srOnglet1.setColor(0.18f, 0.457f, 0.711f, 1);
		srOnglet1.setColor(0.309f, 0.367f, 0.410f, 1);
		srOnglet1.rect(Variables.ONGLET_X1, ONGLET_Y, Variables.ONGLET_WIDTH, ONGLET_HEIGHT);
		srOnglet1.end();
		srOnglet2.begin(ShapeType.Filled);
		//srOnglet2.setColor(0.18f, 0.457f, 0.711f, 1);
		srOnglet2.setColor(0.309f, 0.367f, 0.410f, 1);
		srOnglet2.rect(Variables.ONGLET_X3, ONGLET_Y, Variables.ONGLET_WIDTH, ONGLET_HEIGHT);
		srOnglet2.end();
		/***********************************************************************************/
		
		stage.act();
		stage.draw();
		
		/***********************************************************************************/
		/*******************************CALCUL DES CALORIES*********************************/
		if(labelPoidsUnité.textEquals("kg"))
			chiffrePoids = Float.parseFloat(scrollPaneListPoids.getSelected());
		else if(labelPoidsUnité.textEquals("lb"))
			chiffrePoids = 0.45359237f * Float.parseFloat(scrollPaneListPoids.getSelected());
		
		if(labelDistanceUnité.textEquals("km"))
			chiffreDistance = Float.parseFloat(scrollPaneListDistance.getSelected());
		else if(labelDistanceUnité.textEquals("mi"))
			chiffreDistance = 1.609344f * Float.parseFloat(scrollPaneListDistance.getSelected());
		
		labelCalorieCalculée.setText(Integer.toString((int)(chiffrePoids * chiffreDistance)));
		labelCalorieCalculée.setWidth(game.assets.get("fontCalorie.ttf", BitmapFont.class).getBounds(labelCalorieCalculée.getText()).width);
		labelCalorieCalculée.setX(Gdx.graphics.getWidth()/2 - labelCalorieCalculée.getWidth()/2);
		/***********************************************************************************/
		
		Donnees.setPoids(Integer.parseInt(scrollPaneListPoids.getSelected()));
		
		//Utilisation des onglets
		if(Gdx.input.justTouched() && Gdx.input.getX() < Variables.ONGLET_WIDTH && Gdx.input.getY() < (Gdx.graphics.getHeight() - ONGLET_Y))
			game.setScreen(new MainMenuScreen(game));
		
		if(Gdx.input.justTouched() && Gdx.input.getX() > Variables.ONGLET_X3 && Gdx.input.getY() < (Gdx.graphics.getHeight() - ONGLET_Y))
			game.setScreen(new IntervalScreen(game));
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
		optionBouton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				game.setScreen(new OptionScreen(game));
			}
		});
		
		labelPoidsUnité.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				if(labelPoidsUnité.textEquals("kg")){
					labelPoidsUnité.setText("lb");
					Donnees.setPoidsUnite("lb");
				}			
				else if(labelPoidsUnité.textEquals("lb")){
					labelPoidsUnité.setText("kg");
					Donnees.setPoidsUnite("kg");
				}
			}
		});
		
		labelDistanceUnité.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				if(labelDistanceUnité.textEquals("km")){
					labelDistanceUnité.setText("mi");
					Donnees.setDistanceUnite("mi");
				}
				else if(labelDistanceUnité.textEquals("mi")){
					labelDistanceUnité.setText("km");
					Donnees.setDistanceUnite("km");	
				}
			}
		});
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		menuSkin.dispose();
		skin.dispose();
		menuAtlas.dispose();
		scrollPaneListPoids.dispose();
		scrollPaneListDistance.dispose();
	}

}
