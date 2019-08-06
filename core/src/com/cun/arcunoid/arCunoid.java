package com.cun.arcunoid;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.HashMap;
import java.util.Map;

public class arCunoid extends Game{
	OrthographicCamera camera;
	FitViewport viewport;
	SpriteBatch batch;
	Stage stage;

	private String datoPass = "NULL";
	String textOnline = "";
	boolean online = true;
	HttpRequestBuilder builder;
	Net.HttpRequest request;
	boolean dataGet = false;
	int created = 0;

	ImageButton music;
	boolean musicOn = true;

	int end = 0;
	DataJson dataUser;
	String time;

	float[][] sparkPos = {
			{0, 0, 1}, {196.406f, 52.765f, 3}, {33.57f, 264.372f, 1}, {243.763f, 399.832f, 1}, {497.847f, 292.388f, 1}, {705.79f, 534.238f, 3}, {972, 645.322f, 1},
			{80.068f, 710.048f, 2}, {834.119f, 833.758f, 2}, {652.861f, 958.847f, 1}, {310.545f, 882.121f, 3}, {58.992f, 967.665f, 1}, {0, 1300.805f, 3}, {612.992f, 1409.305f, 1},
			{845.125f, 1351.895f, 2}, {254.428f, 1539.748f, 1}, {559.238f, 1622.362f, 3}, {199.859f, 1735.65f, 1}, {399.835f, 1802.468f, 1}, {942.846f, 1788.406f, 1}
	};
	Array<Spark> sparks = new Array<Spark>(50);

	float[][] nebPos = {
			{650.721f, 860.922f, 0, 0.45f, 549,601}, {891.973f, 829.004f, 340.17f, 0.45f, 450.486f, 492.228f}, {580.013f, 1189.128f, 331.41f, 0.4f, 450.486f, 492.228f},
			{343.295f, 293.427f, 331.41f, 0.4f, 450.486f, 492.228f}, {29.086f, 654.68f, 279.8f, 0.25f, 685.644f, 721.127f}, {329.567f, 1189.256f, 37.15f, 0.4f, 549,601},
			{0, 739.065f, 10.16f, 0.45f, 685.644f, 721.127f}, {833.371f, 769.479f, 337.26f, 0.4f, 248.836f, 272.405f}, {208.337f, 651.808f, 315.4f, 0.5f, 549,601},
			{265.427f, 793.096f, 0, 0.45f, 549,601}, {567.291f, 1255.178f, 299.65f, 0.5f, 248.836f, 272.405f}, {581.197f, 1288.976f, 322.95f, 0.4f, 450.486f, 492.228f},
			{0, 357.334f, 293.43f, 0.45f, 549,601}, {394.382f, 243.356f, 0, 0.45f, 685.644f, 721.127f}, {783.686f, 309.19f, 342.66f, 0.5f, 248.836f, 272.405f},
			{258.925f, 207.666f, 318.6f, 0.45f, 450.486f, 492.228f}, {0, 0, 14.27f, 0.3f, 450.486f, 492.228f}, {612.547f, 0, 22.68f, 0.3f, 450.486f, 492.228f},
			{-40, 1306.849f, 278.97f, 0.5f, 549, 601}
	};
	Array<Sprite> nebulous = new Array<Sprite>(50);

	Sprite back;
	Image black;

	public Sprite logo, planet1, planet2, planet3, planet4;
	public MovingAsteroid comet, meteor;
	public TextureAtlas backAtlas;

	int life = 1;

	String[][] scores = new String[10][4];
	Label score;
	Label scorePoints;
	int scorePointsNumb = 0;
	arCunoid game;

	BitmapFont font, fontBig, fontSmall, fontPage, fontTitle, fontText, fontCredits;

	JsonValue planetsData;
	
	@Override
	public void create(){
		camera = new OrthographicCamera(1080, 1920);
		viewport = new FitViewport(1080, 1920, camera);
		batch = new SpriteBatch();

		boolean fileExist = Gdx.files.local("levelData/data.json").exists();
		dataUser = new DataJson();
		if(fileExist){
			FileHandle file = Gdx.files.local("levelData/data.json");
			Json json = new Json();
			dataUser = json.fromJson(DataJson.class, file);
		}else{
			dataUser.setDataUs("CUNY01");
			dataUser.setDataSc();
			dataUser.setDataTm();
		}

		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(0, 0, 0, 1);
		pixmap.fill();

		back = new Sprite(new Texture(pixmap));
		back.setBounds(0, 0, 1080, 1920);
		back.setColor(0, 0, 0, 0.3f);

		black = new Image(new Texture(pixmap));
		black.setBounds(0, 0, 1080, 1920);
		black.setColor(0, 0, 0, 0);

		game = this;

		final Skin mSkin = new Skin();
		mSkin.add("on", new Texture(Gdx.files.internal("musicOn.png")));
		mSkin.add("off", new Texture(Gdx.files.internal("musicOff.png")));

		music = new ImageButton(mSkin.getDrawable("on"), mSkin.getDrawable("on"), mSkin.getDrawable("off"));
		music.setPosition(500, 500);
		music.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				return  true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				if(musicOn){
					music.setChecked(true);
					musicOn = false;
				}else{
					music.setChecked(false);
					musicOn = true;
				}
			}
		});

		backAtlas = new TextureAtlas("fondo.txt");
		//Set Asteroids
		meteor = new MovingAsteroid(backAtlas.createSprite("asteroide"), 300);
		comet = new MovingAsteroid(backAtlas.createSprite("cometa"), 500);
		//Set planets
		planet1 = new Sprite(backAtlas.createSprite("planeta1"));
		planet1.setPosition(450.5f, 51.809f);
		planet2 = new Sprite(backAtlas.createSprite("planeta2"));
		planet2.setPosition(0, 416.016f);
		planet3 = new Sprite(backAtlas.createSprite("planeta3"));
		planet3.setPosition(794.684f, 1667.739f);
		planet4 = new Sprite(backAtlas.createSprite("planeta4"));
		planet4.setPosition(0, 1685.121f);
		//Set Stars
		for(int i = 0; i < 20; i++){
			float w = 249;
			float h = 246;
			if(sparkPos[i][2] == 1){
				w = 133;
				h = 130;
			}else if(sparkPos[i][2] == 2){
				w = 193;
				h = 130;
			}
			Spark spark = new Spark(backAtlas.createSprite("estrella"));
			spark.init(sparkPos[i][0], sparkPos[i][1], w, h, 0.8f);
			sparks.add(spark);
		}
		//Set Nebulous
		for(int i = 0; i < 19; i++){
			Sprite neb = new Sprite(backAtlas.createSprite("nebulosa"));
			neb.setBounds(nebPos[i][0], nebPos[i][1], nebPos[i][4], nebPos[i][5]);
			neb.setAlpha(nebPos[i][3]);
			neb.setRotation(nebPos[i][2]);
			nebulous.add(neb);
		}

		camera.translate(540, 960, 0);
		stage = new Stage(viewport);

		logo = new Sprite(new Texture("logo.png"));
		logo.setPosition(540-logo.getWidth()/2, 1000);

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("MyriadPro-Regular.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 59;
		parameter.characters = "abcdefghijklmnñopqrstuvwxyzáéíóúÁÍÓABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789,.:;!¡'()¿?-@*/\"";
		font = generator.generateFont(parameter);
		parameter.size = 68;
		fontBig = generator.generateFont(parameter);
		fontBig.getData().markupEnabled = true;
		parameter.size = 51;
		fontSmall = generator.generateFont(parameter);
		fontSmall.getData().markupEnabled = true;
		parameter.size = 121;
		fontPage = generator.generateFont(parameter);
		parameter.size = 71;
		fontTitle = generator.generateFont(parameter);
		parameter.size = 43;
		fontText = generator.generateFont(parameter);
        parameter.size = 41;
        fontCredits = generator.generateFont(parameter);
		fontCredits.getData().setLineHeight(65);
		fontCredits.getData().markupEnabled = true;
		generator.dispose();

		Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
		score = new Label("Score:", labelStyle);
		score.setPosition(30, 1820);
		scorePoints = new Label("0", labelStyle);
		scorePoints.setPosition(score.getX()+score.getWidth()+30, 1815);

		JsonReader j = new JsonReader();
		planetsData = j.parse(Gdx.files.internal("levelData/planets.json").reader("UTF8"));

		Gdx.input.setInputProcessor(stage);
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void resize(int width, int height){
		viewport.update(width, height);
		stage.getViewport().update(width, height);
	}
	
	@Override
	public void dispose(){
		///batch.dispose();
	}

	public void changeLevel(int lvl){
		this.setScreen(new LevelScreen(this, lvl));
	}

	public void addTransitionOutScreen(final String screen){
		stage.clear();
		black.setBounds(0, 0, 1080, 1920);
		//black.setColor(0, 0, 0, 0);
		black.setVisible(true);
		stage.addActor(black);
		RunnableAction run = new RunnableAction();
		run.setRunnable(new Runnable() {
			@Override
			public void run(){
				Gdx.app.log("Entre","OUT");
				if(screen.equals("Level")){
					game.setScreen(new LevelScreen(game, 0));
				}else if(screen.equals("Menu")){
					game.setScreen(new MenuScreen(game));
				}else if(screen.equals("Score")){
					game.setScreen(new ScoreScreen(game));
				}else if(screen.equals("Story0")){
					game.setScreen(new StoryScreen(game, 0));
				}else if(screen.equals("Story1")){
					game.setScreen(new StoryScreen(game, 1));
				}else if(screen.equals("Sign")){
					game.setScreen(new OnlineRegister(game));
				}
				end = 2;
			}
		});
		black.addAction(Actions.sequence(Actions.alpha(0), Actions.alpha(1, 1), Actions.delay(0.5f), run));
	}

	public void addTransitionOutScreen(final LevelScreen screen, final String type){
		stage.clear();
		black.setBounds(0, 0, 1080, 1920);
		black.setVisible(true);
		stage.addActor(black);
		RunnableAction run = new RunnableAction();
		run.setRunnable(new Runnable() {
			@Override
			public void run(){
				Gdx.app.log("Entre","OUT");
				if(type.equals("Change"))
					screen.changeLevel();
				else if(type.equals("Reset"))
					screen.setScene();
			}
		});
		black.addAction(Actions.sequence(Actions.alpha(0), Actions.alpha(1, 1), Actions.delay(0.5f), run));
	}

	public void addTransitionInScreen(){
		black.clear();
		//black.setColor(0, 0, 0, 1);
		black.setBounds(0, 0, 1080, 1920);
		black.setVisible(true);
		black.setZIndex(1000);
		stage.addActor(black);
		RunnableAction run = new RunnableAction();
		run.setRunnable(new Runnable() {
			@Override
			public void run(){
				black.clear();
				black.setVisible(false);
				Gdx.app.log("Entre", "IN");
			}
		});
		black.addAction(Actions.sequence(Actions.alpha(1), Actions.alpha(0, 2), run));
	}

	public void setMessage(float x, float y, float w, float h, Widget[] images, ImageButton[] btns, boolean visible){
		black.clear();
		black.setBounds(0, 560, 1080, 800);
		black.setColor(0, 0, 0, 0.8f);
		black.setVisible(visible);
		black.setZIndex(0);
		for(int i = 0; i < images.length; i++){
			images[i].setVisible(visible);
		}
		for(int i = 0; i < btns.length; i++){
			btns[i].setVisible(visible);
		}
	}

	public void setMessage(Widget[] images, ImageButton[] btns, boolean visible, float height, float y){
		black.clear();
		black.setBounds(0, y, 1080, height);
		black.setColor(0, 0, 0, 0.8f);
		black.setVisible(visible);
		black.setZIndex(0);
		for(int i = 0; i < images.length; i++){
			images[i].setVisible(visible);
		}
		for(int i = 0; i < btns.length; i++){
			btns[i].setVisible(visible);
		}
	}

	public void setMessage(float x, float y, float w, float h, Widget[] images, ImageButton[] btns, Label lbl, boolean visible){
		black.clear();
		black.setColor(0, 0, 0, 0.8f);
		black.setBounds(0, 560, 1080, 800);
		black.setVisible(visible);
		black.setZIndex(0);
		for(int i = 0; i < images.length; i++) {
			images[i].setVisible(visible);
		}
		for(int i = 0; i < btns.length; i++){
			btns[i].setVisible(visible);
		}
		lbl.setStyle(new Label.LabelStyle(fontSmall, Color.WHITE));
		lbl.setText("[YELLOW]"+dataUser.getDataUs()+"[WHITE] tu puntaje es: "+"[YELLOW]"+scorePointsNumb);
		lbl.setVisible(visible);
	}

	public void setMessage(String time, ImageButton[] btns, Label lbl, boolean visible){
		black.clear();
		black.setColor(0, 0, 0, 0.8f);
		black.setBounds(0, 560, 1080, 800);
		black.setVisible(visible);
		black.setZIndex(0);
		for(int i = 0; i < btns.length; i++){
			btns[i].setVisible(visible);
		}
		lbl.setStyle(new Label.LabelStyle(fontBig, null));
		lbl.setText("[YELLOW]"+dataUser.getDataUs()+"[WHITE] tu tiempo fue:\n"+"[YELLOW]"+time);
		lbl.setPosition(0, 1075);
		lbl.setVisible(visible);
	}

	public void setBackground(String currentScreen){
		JsonValue datos = planetsData.get(currentScreen);
		float[] planet1Data = datos.get(0).asFloatArray();
		float[] planet2Data = datos.get(1).asFloatArray();
		float[] planet3Data = datos.get(2).asFloatArray();
		float[] planet4Data = datos.get(3).asFloatArray();

		planet1.setPosition(planet1Data[0], planet1Data[1]);
		planet2.setPosition(planet2Data[0], planet2Data[1]);
		planet3.setPosition(planet3Data[0], planet3Data[1]);
		planet4.setPosition(planet4Data[0], planet4Data[1]);
	}

	public void setMusicOn(boolean mOn){
		musicOn = mOn;
	}

	public boolean getMusicOn(){
		return musicOn;
	}

	public DataJson getDataUser(){
		return dataUser;
	}

	public void saveData(){
		if(online){
			Map<String, String> params = new HashMap<String, String>();
			params.put("arcunoid", "arcunoid");
			params.put("user", dataUser.getDataUs());
			params.put("pass", datoPass);
			params.put("score", String.valueOf(dataUser.getBestSc()));
			params.put("time", dataUser.getBestTm());
			Gdx.app.log("WebRequestUser", dataUser.getDataUs());
			Gdx.app.log("WebRequestPass", datoPass);
			builder = new HttpRequestBuilder();
			request = builder.newRequest().method(Net.HttpMethods.POST).url("http://c.biu.us/arcunoid/process.php").build();
			request.setHeader("Content-Type", "application/x-www-form-urlencoded");
			request.setContent(HttpParametersUtils.convertHttpParameters(params));

			final long start = System.nanoTime(); //for checking the time until response
			Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
				@Override
				public void handleHttpResponse(Net.HttpResponse httpResponse) {
					String text = httpResponse.getResultAsString();
					Gdx.app.log("WebRequest", "HTTP Response code: " + httpResponse.getStatus().getStatusCode());
					Gdx.app.log("WebRequest", "HTTP Response code: " + text);
					Gdx.app.log("WebRequest", "Response time: " + ((System.nanoTime() - start) / 1000000) + "ms");
					if(text.equals("Registrado")){
						textOnline = "Registrado";
					}else{
						textOnline = "Ocurrió un error al guardar.";
					}
					dataGet = true;
				}

				@Override
				public void failed(Throwable t) {
					Gdx.app.log("WebRequest", String.valueOf(t));
					textOnline = "Ocurrió un error al guardar.";
					dataGet = true;
				}

				@Override
				public void cancelled() {
					Gdx.app.log("WebRequest", "HTTP request cancelled");
					textOnline = "Ocurrió un error al guardar.";
					dataGet = true;
				}
			});
		}
		FileHandle file = Gdx.files.local("levelData/data.json");
		Json json = new Json();
		String data = json.toJson(dataUser);
		data = json.prettyPrint(data);
		file.writeString(data, false);
	}

	public void getSaveData(boolean onLine){
		if(onLine){
			Map<String, String> params = new HashMap<String, String>();
			params.put("arcunoidGet", "arcunoid");
			params.put("user", dataUser.getDataUs());
			params.put("pass", datoPass);

			builder = new HttpRequestBuilder();
			request = builder.newRequest().method(Net.HttpMethods.POST).url("http://c.biu.us/arcunoid/process.php").build();
			request.setHeader("Content-Type", "application/x-www-form-urlencoded");
			request.setContent(HttpParametersUtils.convertHttpParameters(params));

			final long start = System.nanoTime(); //for checking the time until response
			Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
				@Override
				public void handleHttpResponse(Net.HttpResponse httpResponse) {
					String text = httpResponse.getResultAsString();
					Gdx.app.log("WebRequest", "HTTP Response code: " + httpResponse.getStatus().getStatusCode());
					Gdx.app.log("WebRequest", "HTTP Response code: " + text);
					Gdx.app.log("WebRequest", "Response time: " + ((System.nanoTime() - start) / 1000000) + "ms");
					if(!text.equals("Error") && !text.isEmpty()){
						String[] scoresOnline1 = text.split("<<<");
						if(scoresOnline1.length == 10){
							for(int i = 0; i < 10; i++){
								scores[i] = scoresOnline1[i].split(",");
							}
						}else{
							for(int i = 0; i < scoresOnline1.length; i++){
								scores[i] = scoresOnline1[i].split(",");
							}
							for(int i = scoresOnline1.length; i < 10; i++){
								if(i<9)
									scores[i] = new String[]{"  "+String.valueOf(i+1)+".", "CUN", "0", "--:--:---"};
								else
									scores[i] = new String[]{String.valueOf(i+1)+".", "CUN", "0", "--:--:---"};
							}
						}
						dataGet = true;
					}
				}

				@Override
				public void failed(Throwable t) {
					Gdx.app.log("WebRequest", String.valueOf(t));
					textOnline = "Ocurrio un error, vuelve a intentarlo.";
				}

				@Override
				public void cancelled() {
					Gdx.app.log("WebRequest", "HTTP request cancelled");
					textOnline = "Ocurrio un error, vuelve a intentarlo.";
				}
			});
		}else{
			boolean fileExist = Gdx.files.local("levelData/data.json").exists();
			if(fileExist){
				FileHandle file = Gdx.files.local("levelData/data.json");
				Json json = new Json();
				DataJson datos = json.fromJson(DataJson.class, file);
				Array<Integer> scoreData = datos.getDataSc();
				Array<String> timeData = datos.getDataTm();
				for (int i = 0; i < 10; i++) {
					if (i == 9)
						scores[i][0] = (i + 1) + ".";
					else
						scores[i][0] = "  " + (i + 1) + ".";
					scores[i][1] = datos.getDataUs();
					scores[i][2] = String.valueOf(scoreData.get(i));
					scores[i][3] = timeData.get(i);
				}
			}else{
				for (int i = 0; i < 10; i++) {
					if (i == 9)
						scores[i][0] = (i + 1) + ".";
					else
						scores[i][0] = "  " + (i + 1) + ".";
					scores[i][1] = "Usuario";
					scores[i][2] = "0";
					scores[i][3] = "--:--:---";
				}
			}
			dataGet = true;
		}
	}

	public void checkUser(final String user, final String passw){
		created = 1;
		Map<String, String> params = new HashMap<String, String>();
		params.put("arcunoidConnect", "arcunoid");
		params.put("user", user);
		params.put("pass", passw);

		builder = new HttpRequestBuilder();
		request = builder.newRequest().method(Net.HttpMethods.POST).url("http://c.biu.us/arcunoid/register.php").build();
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		request.setContent(HttpParametersUtils.convertHttpParameters(params));
		request.setTimeOut(30000);

		final long start = System.nanoTime(); //for checking the time until response
		Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener(){
			@Override
			public void handleHttpResponse(Net.HttpResponse httpResponse) {
				String text = httpResponse.getResultAsString();
				Gdx.app.log("WebRequest", "HTTP Response code: " + httpResponse.getStatus().getStatusCode());
				Gdx.app.log("WebRequest", "HTTP Response code2: " + text);
				Gdx.app.log("WebRequest", "Response time: " + ((System.nanoTime() - start) / 1000000) + "ms");
				if(text.equals("Correcto")){
					created = 2;
					Gdx.app.log("WebRequestUser", String.valueOf(passw));
					datoPass = passw;
					textOnline = "";
				}else{
					created = 3;
					textOnline = text;
				}
			}

			@Override
			public void failed(Throwable t) {
				Gdx.app.log("WebRequest", String.valueOf(t));
				created = 3;
				textOnline = "Ocurrio un error, vuelve a intentarlo.";
			}

			@Override
			public void cancelled() {
				Gdx.app.log("WebRequest", "HTTP request cancelled");
				created = 3;
				textOnline = "Ocurrio un error, vuelve a intentarlo.";
			}
		});
	}

	public void addUser(final String user, final String passw, String name, String lastname, String mail, String phone){
		created = 1;
		Map<String, String> params = new HashMap<String, String>();
		params.put("register", "arcunoid");
		params.put("user", user);
		params.put("pass", passw);
		params.put("name", name);
		params.put("lastname", lastname);
		params.put("mail", mail);
		params.put("phone", phone);

		builder = new HttpRequestBuilder();
		request = builder.newRequest().method(Net.HttpMethods.POST).url("http://c.biu.us/arcunoid/register.php").build();
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		request.setContent(HttpParametersUtils.convertHttpParameters(params));
		request.setTimeOut(30000);

		final long start = System.nanoTime(); //for checking the time until response
		Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener(){
			@Override
			public void handleHttpResponse(Net.HttpResponse httpResponse) {
				String text = httpResponse.getResultAsString();
				Gdx.app.log("WebRequest", "HTTP Response code: " + httpResponse.getStatus().getStatusCode());
				Gdx.app.log("WebRequest", "HTTP Response code2: " + text);
				Gdx.app.log("WebRequest", "Response time: " + ((System.nanoTime() - start) / 1000000) + "ms");
				if(text.equals("Registrado")){
					created = 2;
					textOnline = "";
					FileHandle file = Gdx.files.local("levelData/data.json");
					Json json = new Json();
					DataJson dataUser = game.getDataUser();
					dataUser.setDataUs(user);
					datoPass = passw;
					String data = json.toJson(dataUser);
					data = json.prettyPrint(data);
					file.writeString(data, false);
				}else{
					created = 3;
					textOnline = text;
				}
			}

			@Override
			public void failed(Throwable t) {
				Gdx.app.log("WebRequest", String.valueOf(t));
				created = 3;
				textOnline = "Ocurrio un error, vuelve a intentarlo.";
			}

			@Override
			public void cancelled() {
				Gdx.app.log("WebRequest", "HTTP request cancelled");
				created = 3;
				textOnline = "Ocurrio un error, vuelve a intentarlo.";
			}
		});
	}

	public void setDatoPass(String val){
		datoPass = val;
	}
}
