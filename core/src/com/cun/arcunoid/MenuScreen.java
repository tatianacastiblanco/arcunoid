package com.cun.arcunoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by CUN on 28/12/2017.
 */

public class MenuScreen extends BaseScreen{
    private Sprite logo;
    private TextureAtlas menuAtlas, scoreAtlas;
    private Image signText, logInText, box, boxPass, mode;
    private TextField userName, passw;
    boolean userChanged, passChanged;
    private ImageButton play, scores, online, offline, logIn, signIn, create, back;
    private Label onlineMessage, lblCredits;
    private Music menuMusic;
    private boolean onOut = false;

    MenuScreen(arCunoid g){
        super(g);
        Gdx.app.log("Line", String.valueOf(game.fontBig.getLineHeight()));
        game.setDatoPass("NULL");

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/ambientmain.ogg"));
        menuMusic.setLooping(true);
        menuMusic.play();

        logo = new Sprite(new Texture("logo.png"));
        logo.setPosition(540-logo.getWidth()/2, 1000);

        menuAtlas = new TextureAtlas("menu.txt");
        scoreAtlas = new TextureAtlas("scores.txt");

        mode = new Image(new Texture("gameMode.png"));
        mode.setPosition(114.797f, 1030.698f);
        Skin butts = new Skin();
        butts.add("up", new Texture("btnOnlineUp.png"));
        butts.add("down", new Texture("btnOnlineDown.png"));
        butts.add("up2", new Texture("btnOfflineUp.png"));
        butts.add("down2", new Texture("btnOfflineDown.png"));
        online = new ImageButton(butts.getDrawable("up"), butts.getDrawable("down"));
        online.setPosition(310.353f, 844.355f);
        online.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                game.online = true;
                Widget[] images = {mode};
                ImageButton[] btns = {online, offline};
                game.setMessage(50, 505, 980, 800, images, btns, false);
                userChanged = false;
                passChanged = false;
                userName.setText("Usuario");
                userName.setPosition(310, 1070);
                box.setPosition(130, 1070);
                logIn.setY(790f);
                Widget[] images2 = {logInText, box, boxPass, userName, passw, onlineMessage};
                ImageButton[] btns2 = {logIn, signIn, back};
                game.setMessage(images2, btns2, true, 1068, 426);
            }
        });
        offline = new ImageButton(butts.getDrawable("up2"), butts.getDrawable("down2"));
        offline.setPosition(310.353f, 676.052f);
        offline.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                game.online = false;
                Widget[] images = {mode};
                ImageButton[] btns = {online, offline};
                game.setMessage(50, 505, 980, 800, images, btns, false);
                boolean fileExist = Gdx.files.local("levelData/data.json").exists();
                if(fileExist){
                    onOut = true;
                    game.addTransitionOutScreen("Story0");
                }else{
                    play.setVisible(false);
                    scores.setVisible(false);
                    userChanged = false;
                    userName.setText("Usuario");
                    userName.setPosition(310, 870);
                    box.setPosition(130, 870);
                    logIn.setY(690);
                    Widget[] images2 = {signText, box, userName};
                    ImageButton[] btns2 = {logIn};
                    game.setMessage(50, 505, 980, 800, images2, btns2, true);
                }
            }
        });

        Label.LabelStyle labelStyle = new Label.LabelStyle(game.fontSmall, Color.YELLOW);
        onlineMessage = new Label("", labelStyle);
        onlineMessage.setBounds(0, 1130, 1080, 200);
        onlineMessage.setAlignment(Align.center);

        Label.LabelStyle labelStyle2 = new Label.LabelStyle(game.font, Color.WHITE);
        lblCredits = new Label("Ver Creditos", labelStyle2);
        lblCredits.setPosition(540-lblCredits.getWidth()/2, 50);
        lblCredits.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new CreditsScreen(game));
            }
        });

        box = new Image(new Texture("userName.png"));
        box.setBounds(130, 1070, 820, 122);
        boxPass = new Image(new Texture("passw.png"));
        boxPass.setBounds(130, 940, 820, 122);
        TextField.TextFieldStyle signStyle = new TextField.TextFieldStyle();
        signStyle.font = game.font;
        signStyle.fontColor = Color.WHITE;
        userName = new TextField("Usuario", signStyle);
        userName.setBounds(310, 1070, 640, 122);
        userName.setMaxLength(10);
        userName.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(!userChanged){
                    userName.setText("");
                    userChanged = true;
                }
                return  true;
            }
        });
        passw = new TextField("Contraseña", signStyle);
        passw.setPasswordMode(true);
        passw.setBounds(310, 940, 640, 122);
        passw.setMaxLength(15);
        passw.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(!passChanged){
                    passw.setText("");
                    passChanged = true;
                }
                return  true;
            }
        });

        Skin btnPlaySkin = new Skin(menuAtlas);
        ImageButton.ImageButtonStyle btnPlay = new ImageButton.ImageButtonStyle();
        btnPlay.up = btnPlaySkin.getDrawable("btnPlayUp");
        btnPlay.down = btnPlaySkin.getDrawable("btnPlayDown");
        play = new ImageButton(btnPlay);
        play.setPosition(540-play.getWidth()/2, 796);
        play.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                play.setVisible(false);
                scores.setVisible(false);
                Widget[] images = {mode};
                ImageButton[] btns = {online, offline};
                game.setMessage(50, 505, 980, 800, images, btns, true);
            }
        });

        Skin btnScoreSkin = new Skin(scoreAtlas);
        ImageButton.ImageButtonStyle btnScore = new ImageButton.ImageButtonStyle();
        btnScore.up = btnScoreSkin.getDrawable("btnScoresUp");
        btnScore.down = btnScoreSkin.getDrawable("btnScoresDown");
        scores = new ImageButton(btnScore);
        scores.setPosition(540-scores.getWidth()/2, 615);
        scores.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new ScoreScreen(game));
            }
        });

        signText = new Image(new Texture("createUser.png"));
        signText.setPosition(540-signText.getWidth()/2, 1060);

        logInText = new Image(new Texture("ingresa.png"));
        logInText.setPosition(540-logInText.getWidth()/2, 1278.245f);

        Skin btnAceptSkin = new Skin();
        btnAceptSkin.add("up", new Texture("aceptarUp.png"));
        btnAceptSkin.add("down", new Texture("aceptarDown.png"));
        logIn = new ImageButton(btnAceptSkin.getDrawable("up"), btnAceptSkin.getDrawable("down"));
        logIn.setPosition(540-logIn.getWidth()/2, 790);
        logIn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                if(game.online){
                    game.checkUser(userName.getText(), passw.getText());
                }else{
                    FileHandle file = Gdx.files.local("levelData/data.json");
                    Json json = new Json();
                    DataJson dataUser = game.getDataUser();
                    dataUser.setDataUs(userName.getText());
                    String data = json.toJson(dataUser);
                    data = json.prettyPrint(data);
                    file.writeString(data, false);
                    onOut = true;
                    game.addTransitionOutScreen("Story0");
                }
            }
        });
        Skin btnLogSkin = new Skin(menuAtlas);
        ImageButton.ImageButtonStyle btnLogIn = new ImageButton.ImageButtonStyle();
        btnLogIn.up = btnLogSkin.getDrawable("btnSignUp");
        btnLogIn.down = btnLogSkin.getDrawable("btnSignDown");
        signIn = new ImageButton(btnLogIn);
        signIn.setWidth(logIn.getWidth());
        signIn.setPosition(540-signIn.getWidth()/2, 640);
        signIn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                onOut = true;
                game.addTransitionOutScreen("Sign");
            }
        });
        Skin btnBack = new Skin();
        btnBack.add("up", new Texture("backUp.png"));
        btnBack.add("down", new Texture("backDown.png"));
        back = new ImageButton(btnBack.getDrawable("up"), btnBack.getDrawable("down"));
        back.setPosition(540-signIn.getWidth()/2, 490);
        back.setWidth(468);
        back.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                if(game.online){
                    game.online = false;
                    onlineMessage.setText("");
                    Widget[] images2 = {logInText, box, boxPass, userName, passw, onlineMessage};
                    ImageButton[] btns2 = {logIn, signIn, back};
                    game.setMessage(images2, btns2, false, 1068, 426);
                    Widget[] images = {mode};
                    ImageButton[] btns = {online, offline};
                    game.setMessage(50, 505, 980, 800, images, btns, true);
                }else{

                }
            }
        });
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0.0745f, 0.13f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        if(onOut)
            menuMusic.setVolume(menuMusic.getVolume()-0.05f);
        if(!game.getMusicOn())
            menuMusic.pause();
        else
            menuMusic.play();
        if(game.created != 0){
            if(game.created == 2){
                onOut = true;
                game.addTransitionOutScreen("Story0");
                game.created = 0;
            }else if(game.created == 3){
                Gdx.app.log("Entre", "NOP");
                onlineMessage.setText(game.textOnline);
                game.created = 0;
            }
        }
        camera.update();
        batch.begin();
        drawBack(delta, true);
        logo.draw(batch);
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show(){
        //game.addTransitionInScreen();
        //stage.clear();
        stage.addActor(lblCredits);
        stage.addActor(mode);
        mode.setVisible(false);
        stage.addActor(online);
        online.setVisible(false);
        stage.addActor(offline);
        offline.setVisible(false);
        stage.addActor(play);
        stage.addActor(scores);
        stage.addActor(box);
        box.setVisible(false);
        stage.addActor(boxPass);
        boxPass.setVisible(false);
        stage.addActor(userName);
        userName.setVisible(false);
        stage.addActor(passw);
        passw.setVisible(false);
        stage.addActor(onlineMessage);
        onlineMessage.setVisible(false);
        stage.addActor(logIn);
        logIn.setVisible(false);
        stage.addActor(signIn);
        signIn.setVisible(false);
        stage.addActor(back);
        back.setVisible(false);
        stage.addActor(signText);
        signText.setVisible(false);
        stage.addActor(logInText);
        logInText.setVisible(false);
        game.addTransitionInScreen();
        music.setPosition(975.581f, 1821.267f);
        stage.addActor(music);
    }

    @Override
    public void hide(){
        menuMusic.stop();
        //stage.clear();
        //game.addTransitionOutScreen("Level");
    }

    @Override
    public void dispose(){
        menuMusic.dispose();
    }
}
