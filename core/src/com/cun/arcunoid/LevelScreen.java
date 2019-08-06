package com.cun.arcunoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.FloatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.text.DecimalFormat;

/**
 * Created by CUN on 23/11/2017.
 */

public class LevelScreen extends BaseScreen{
    public enum GameStates{
        BEGIN, RUN, PAUSE, NEXT, WIN, DETH, OVER, END
    }
    GameStates gameState = GameStates.RUN;

    LevelScreen screen;

    int level;

    DataJson dataUser;

    Array<Sprite> wallsRigth = new Array<Sprite>(14);
    Array<Sprite> wallsLeft = new Array<Sprite>(14);
    Sprite wallUp;
    Array<Sprite> squaresDes = new Array<Sprite>(20);
    Array<Sprite> squaresInd = new Array<Sprite>(20);
    Array<Sprite> lifes = new Array<Sprite>(5);
    int[][] lvl1SqDesPos = {
            {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
            {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    int[][] lvl2SqDesPos = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 0},
            {0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0},
            {0, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 0},
            {0, 0, 0, 6, 6, 6, 6, 6, 6, 6, 6, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
            {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    int[][] lvl3SqDesPos = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
            {0, 0, 5, 5, 5, 5, 5, 5, 5, 5, 1, 0, 0, 0},
            {0, 5, 5, 6, 6, 5, 6, 6, 6, 5, 5, 1, 0, 0},
            {1, 5, 6, 5, 5, 6, 5, 5, 5, 6, 5, 1, 1, 0},
            {1, 1, 5, 5, 6, 6, 6, 5, 5, 5, 1, 1, 1, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 1, 0, 0},
            {0, 0, 1, 3, 1, 1, 1, 3, 1, 1, 1, 0, 0, 0},
            {5, 5, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5, 0},
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    int[][] lvl4SqDesPos = {
            {0, 0, 0, 0, 6, 6, 6, 6, 6, 6, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0},
            {0, 0, 6, 6, 1, 1, 1, 1, 1, 1, 6, 6, 0, 0},
            {0, 6, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 6, 0},
            {6, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 6},
            {6, 6, 6, 1, 1, 1, 1, 1, 1, 1, 1, 6, 6, 6},
            {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
            {0, 0, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 0, 0}
    };
    int[][] lvlPrueba = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    int[][] lvlData;
    TextureAtlas textureAtlas, playT, butts;
    Ball ball;
    Base base;
    Array<Power> powers = new Array<Power>(5);
    Array<Integer> inactivePowers = new Array<Integer>(5);
    Power pR, pT, pCo, pCr, pA;
    Label textLbl1, textLbl2;

    Pixmap px;
    Image pause, failText, pauseText;
    ImageButton contin, menu, retry, dontSave, replay, next;
    Label yourScoreLbl;

    boolean canAddTime = false;
    float gameTime = 0;
    float lvlTime = 0;

    float touchX = 0;

    Texture borrar;

    float timeOnScreen = 0;

    Sound getMaterial, fail, capsule, win;
    Music spaceBackground;

    private boolean onOut = false;

    LevelScreen(arCunoid Game, int lvl) {
        super(Game);

        dataUser = game.getDataUser();

        spaceBackground = Gdx.audio.newMusic(Gdx.files.internal("sounds/spaceBackground.ogg"));
        spaceBackground.setVolume(0.8f);
        spaceBackground.setLooping(true);
        spaceBackground.play();

        getMaterial = Gdx.audio.newSound(Gdx.files.internal("sounds/beep.ogg"));
        getMaterial.setVolume(1, 1);

        fail = Gdx.audio.newSound(Gdx.files.internal("sounds/fail.ogg"));

        capsule = Gdx.audio.newSound(Gdx.files.internal("sounds/capsule.ogg"));

        win = Gdx.audio.newSound(Gdx.files.internal("sounds/win.ogg"));

        textureAtlas = new TextureAtlas("jugable1.txt");
        playT = new TextureAtlas("jugable2.txt");
        butts = new TextureAtlas("botones.txt");
        Skin s = new Skin(playT);
        TextureAtlas.AtlasRegion rigthT = playT.findRegion("nave-lado-izq");
        TextureRegion t = new TextureRegion(rigthT);
        t.flip(true, false);

        ball = new Ball(playT.findRegion("ball"), 0, 0, 50, 50);
        base = new Base(s.getDrawable("nave-lado-izq"), s.getDrawable("nave-centro"), s.getDrawable("nave-centro-extendido"), t, 300, 300);
        base.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(gameState == GameStates.RUN){
                    base.setCanMove(true);
                    touchX = base.getX()-Gdx.input.getX();
                }
                return  true;
            }
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer){
                if(gameState == GameStates.RUN && base.getCanMove()){
                    float newX = Gdx.input.getDeltaX()*2f;
                    if(base.getX()+newX<=50){
                        newX = 0;
                    }else if(base.getX()+base.getWidth()+newX>=1030){
                        newX = 0;
                    }
                    if(newX != 0){
                        if(x < 0){
                            base.setPosition(newX, 0);
                            if (base.getOnBase())
                                ball.addPos(newX, 0);
                        }else if(x > 0){
                            base.setPosition(newX, 0);
                            if (base.getOnBase())
                                ball.addPos(newX, 0);
                        }
                    }
                }
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                if(gameState == GameStates.RUN && base.getOnBase() && base.getCanMove()) {
                    base.setOnBase(false);
                    canAddTime = true;
                    touchX = 0;
                }
            }
        });

        pR = new Power(playT.createSprite("poder-respeto"));
        pR.power = Power.Powers.R;
        pR.setSize(90, 58);
        pT = new Power(playT.createSprite("poder-tolerancia"));
        pT.power = Power.Powers.T;
        pT.setSize(90, 58);
        pCo = new Power(playT.createSprite("poder-compromiso"));
        pCo.power = Power.Powers.CO;
        pCo.setSize(90, 58);
        pCr = new Power(playT.createSprite("poder-creatividad"));
        pCr.power = Power.Powers.CR;
        pCr.setSize(90, 58);
        pA = new Power(playT.createSprite("poder-adaptabilidad"));
        pA.power = Power.Powers.A;
        pA.setSize(90, 58);
        inactivePowers.add(0);
        inactivePowers.add(1);
        inactivePowers.add(2);
        inactivePowers.add(3);
        inactivePowers.add(4);

        float lifeX = 70;
        for(int i = 0; i < 5; i++){
            Sprite l = playT.createSprite("ball");
            l.setPosition(lifeX, 30);
            lifes.add(l);
            lifeX += 75;
        }

        level = lvl;

        Label.LabelStyle textLblStyle = new Label.LabelStyle(game.fontPage, Color.WHITE);
        textLbl1 = new Label("", textLblStyle);
        textLbl2 = new Label("", textLblStyle);

        px = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        px.setColor(0, 0, 0, 0.9f);
        px.fill();
        pauseText = new Image(new TextureRegion(textureAtlas.findRegion("pausa")));
        pauseText.setPosition(351.5f, 1100);
        pause = new Image(new TextureRegion(textureAtlas.findRegion("boton-pausa")));
        pause.setPosition(975.581f, 1821.267f);
        pause.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                if(gameState == GameStates.RUN){
                    gameState = GameStates.PAUSE;
                    Widget[] widgets = {pauseText};
                    menu.setPosition(307, 730);
                    ImageButton[] btns = {contin, menu};
                    game.setMessage(0, 0, 0, 0, widgets, btns, true);
                }
            }
        });
        failText = new Image(new TextureRegion(textureAtlas.findRegion("perdiste")));
        failText.setPosition(236.5f, 1140);
        Skin s2 = new Skin(butts);
        contin = new ImageButton(s2.getDrawable("btnContinueUp"), s2.getDrawable("btnContinueDown"));
        contin.setPosition(307, 900);
        contin.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameState = GameStates.RUN;
                if(game.getMusicOn())
                    spaceBackground.play();
                Widget[] widgets = {pauseText};
                menu.setPosition(307, 730);
                ImageButton[] btns = {contin, menu};
                game.setMessage(0, 0, 0, 0, widgets, btns, false);
            }
        });
        menu = new ImageButton(s2.getDrawable("btnStartUp"), s2.getDrawable("btnStartDown"));
        menu.setPosition(307, 730);
        menu.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                onOut = true;
                game.addTransitionOutScreen("Menu");
            }
        });
        Skin retryS = new Skin();
        retryS.add("btnTryUp", new Texture("btnTryUp.png"));
        retryS.add("btnTryDown", new Texture("btnTryDown.png"));
        retryS.add("btnDontUp", new Texture("btnDontSaveUp.png"));
        retryS.add("btnDontDown", new Texture("btnDontSaveDown.png"));
        retry = new ImageButton(retryS.getDrawable("btnTryUp"), retryS.getDrawable("btnTryDown"));
        retry.setPosition(307, 1020);
        retry.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                dataUser.addValue(game.scorePointsNumb, time);
                game.saveData();
            }
        });
        dontSave = new ImageButton(retryS.getDrawable("btnDontUp"), retryS.getDrawable("btnDontDown"));
        dontSave.setPosition(307, 852);
        dontSave.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameState = GameStates.END;
                onOut = true;
                game.addTransitionOutScreen("Story1");
            }
        });
        replay = new ImageButton(s2.getDrawable("btnReplayUp"), s2.getDrawable("btnReplayDown"));
        replay.setPosition(307, 820);
        replay.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameState = GameStates.END;
                game.addTransitionOutScreen(screen, "Reset");
            }
        });
        next = new ImageButton(s2.getDrawable("btnContinueUp"), s2.getDrawable("btnContinueDown"));
        next.setPosition(307, 860);
        next.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(level < 4){
                    game.addTransitionOutScreen(screen, "Change");
                }else{
                    gameState = GameStates.WIN;
                    dataUser.addValue(game.scorePointsNumb, time);
                    game.saveData();
                }
            }
        });
        Label.LabelStyle labelStyle = new Label.LabelStyle(game.fontSmall, Color.WHITE);
        yourScoreLbl = new Label("Usuario tu puntaje es 7494094", labelStyle);
        yourScoreLbl.setBounds(0, 1020, 1080, 50);
        yourScoreLbl.setAlignment(Align.center);
        screen = this;
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0.0745f, 0.13f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        camera.update();
        if(onOut)
            spaceBackground.setVolume(spaceBackground.getVolume()-0.05f);
        if(!game.getMusicOn() || gameState == GameStates.PAUSE)
            spaceBackground.pause();
        else
            spaceBackground.play();
        batch.begin();
        drawBack(delta, true);
        if(gameState == GameStates.BEGIN){

        }else if(gameState == GameStates.RUN){
            if(squaresDes.size == 0){
                if(level <= 4 && timeOnScreen>=2.5f){
                    gameTime += lvlTime;
                    if(game.getMusicOn())
                        win.play();
                    game.scorePointsNumb += 50*game.life;
                    DecimalFormat formatInt = new DecimalFormat("0");
                    formatInt.setMinimumIntegerDigits(2);
                    DecimalFormat format = new DecimalFormat("0.00");
                    time = String.valueOf(lvlTime);
                    Gdx.app.log("MYTIME", time);
                    float minutes = lvlTime/60;
                    time = String.valueOf(format.format(minutes));
                    Gdx.app.log("MYTIME", time);
                    String[] s;
                    if(time.contains("."))
                        s = time.split("\\.");
                    else
                        s = time.split(",");
                    float seconds = (minutes-Float.valueOf(s[0]))*60;
                    minutes = Integer.valueOf(s[0]);
                    time = String.valueOf(format.format(seconds));
                    String[] m;
                    if(time.contains("."))
                        m = time.split("\\.");
                    else
                        m = time.split(",");
                    float miliseconds = (seconds-Float.valueOf(m[0]))*1000;
                    seconds = Integer.valueOf(m[0]);
                    if(miliseconds<100)
                        miliseconds *= 10;
                    time = formatInt.format(minutes)+":"+formatInt.format(seconds)+":"+(int)miliseconds;
                    Gdx.app.log("MYTIME", time);
                    menu.setPosition(307, 690);
                    ImageButton[] btns = {next, menu};
                    if(level < 4)
                        yourScoreLbl.setPosition(0, 1000);
                    else
                        yourScoreLbl.setPosition(0, 1080);
                    game.setMessage(time, btns, yourScoreLbl, true);
                    time = String.valueOf(gameTime);
                    float minutesG = gameTime/60;
                    time = String.valueOf(format.format(minutesG));
                    String[] sG;
                    if(time.contains("."))
                        sG = time.split("\\.");
                    else
                        sG = time.split(",");
                    float secondsG = (minutesG-Float.valueOf(sG[0]))*60;
                    minutesG = Integer.valueOf(sG[0]);
                    time = String.valueOf(format.format(secondsG));
                    String[] mG;
                    if(time.contains("."))
                        mG = time.split("\\.");
                    else
                        mG = time.split(",");
                    float milisecondsG = (secondsG-Float.valueOf(mG[0]))*1000;
                    secondsG = Integer.valueOf(mG[0]);
                    if(milisecondsG<100)
                        milisecondsG *= 10;
                    time = formatInt.format(minutesG)+":"+formatInt.format(secondsG)+":"+(int)milisecondsG;
                    gameState = GameStates.NEXT;
                }else{
                    timeOnScreen += delta;
                    //Dibujar las paredes
                    for(int i= 0; i < 14; i++){
                        wallsLeft.get(i).draw(batch);
                        wallsRigth.get(i).draw(batch);
                    }
                    wallUp.draw(batch);
                    //Dibujar las vidas
                    for(int i = 0; i < game.life; i++){
                        lifes.get(i).draw(batch);
                    }
                    textLbl1.draw(batch, 1);
                    textLbl2.draw(batch, 1);
                }
            }else{
                if(canAddTime){
                    lvlTime += delta;
                }
                //Dibujar las paredes
                for(int i= 0; i < 14; i++){
                    wallsLeft.get(i).draw(batch);
                    wallsRigth.get(i).draw(batch);
                }
                wallUp.draw(batch);
                //Dibujar las vidas
                for(int i = 0; i < game.life; i++){
                    lifes.get(i).draw(batch);
                }

                textLbl1.draw(batch, 1);
                textLbl2.draw(batch, 1);

                //Calcular choques y movimientos
                Array<Sprite> sqs = new Array<Sprite>(3);
                ball.hasChangeDir = false;
                //Si esta chocando con los cuadros no destruibles
                if(squaresInd.size != 0){
                    for(int i = 0; i < squaresInd.size; i++){
                        Sprite sq = squaresInd.get(i);
                        if(ball.getX()+ball.getWidth() > sq.getX() && ball.getX() < sq.getX() + sq.getWidth()){
                            if(ball.getY()+ball.getHeight() > sq.getY() && ball.getY() < sq.getY() + sq.getHeight()){
                                sqs.add(sq);
//                                    ball.moveOnCollide(sq);
                            }
                        }
                        sq.draw(batch);
                    }
                }
                //Si esta chocando con los cuadros destruibles
                for(int i = 0; i < squaresDes.size; i++){
                    boolean draw = true;
                    Sprite sq = squaresDes.get(i);
                    if(ball.getX()+ball.getWidth() > sq.getX() && ball.getX() < sq.getX() + sq.getWidth()){
                        if(ball.getY()+ball.getHeight() > sq.getY() && ball.getY() < sq.getY() + sq.getHeight()){
                            sqs.add(squaresDes.get(i));
                            if(game.getMusicOn())
                                getMaterial.play(1.0f);
                            float r = MathUtils.random(0, 4);
                            if(r == 3){
                                addRandPower(squaresDes.get(i).getX(), squaresDes.get(i).getY());
                            }
                            game.scorePointsNumb += 50;
                            draw = false;
                        }
                    }
                    if(draw){
                        sq.draw(batch);
                    }
                }
                if(sqs.size > 0)
                    ball.moveOnCollide(sqs);
                for(int i = 0; i < sqs.size; i++)
                    squaresDes.removeValue(sqs.get(i), true);

                //Si esta chocando con las paredes
                if(ball.getX()+ball.getWidth() >= 1030 && ball.getDirX() == Ball.Directions.RIGHT){
                    ball.setNewX(980);
                    ball.setDirX(Ball.Directions.LEFT);
                }else if(ball.getX() <= 50 && ball.getDirX() == Ball.Directions.LEFT){
                    ball.setNewX(50);
                    ball.setDirX(Ball.Directions.RIGHT);
                }
                if(ball.getY()+ball.getHeight() >= 1760 && ball.getDirY() == Ball.Directions.UP){
                    ball.setNewY(1710);
                    ball.setDirY(Ball.Directions.DOWN);
                }else if(ball.getY() <= 0 && ball.getDirY() == Ball.Directions.DOWN){
                    gameState = GameStates.DETH;
                }

                if(ball.getX()+ball.getWidth() > base.getX() && ball.getX() < base.getX() + base.getWidth()){
                    if(ball.getY()+ball.getHeight() > base.getY()+ base.getHeight() / 2 && ball.getY() < base.getY() + base.getHeight()){
                        int rand = new MathUtils().random(0, 1);
                        if(rand == 0)
                            ball.setDirX(Ball.Directions.RIGHT);
                        else
                            ball.setDirX(Ball.Directions.LEFT);
                        ball.setDirY(Ball.Directions.UP);
                    }
                }

                if(powers.size > 0){
                    for(int i = 0; i < powers.size; i++){
                        if(powers.get(i).getActive()){
                            powers.get(i).update(delta);
                            if(base.getX()+base.getWidth() > powers.get(i).getX() && base.getX() < powers.get(i).getX() + powers.get(i).getWidth()){
                                if(base.getY()+base.getHeight() > powers.get(i).getY() && base.getY() < powers.get(i).getY() + powers.get(i).getHeight()){
                                    if(game.getMusicOn())
                                        capsule.play();
                                    powers.get(i).setActive(false);
                                    runPower(powers.get(i), true);
                                }
                            }
                            powers.get(i).draw(batch);
                        }else if(powers.get(i).onPower){
                            powers.get(i).update(delta);
                        }else{
                            if(powers.get(i).getEndtime())
                                runPower(powers.get(i), false);
                            if(powers.get(i).getPower() == Power.Powers.R)
                                inactivePowers.add(0);
                            else if (powers.get(i).getPower() == Power.Powers.T)
                                inactivePowers.add(1);
                            else if (powers.get(i).getPower() == Power.Powers.CO)
                                inactivePowers.add(2);
                            else if (powers.get(i).getPower() == Power.Powers.CR)
                                inactivePowers.add(3);
                            else if (powers.get(i).getPower() == Power.Powers.A)
                                inactivePowers.add(4);
                            powers.removeIndex(i);
                        }
                    }
                }
                if(!base.getOnBase()){
                    ball.update(delta);
                }
                game.scorePoints.setText(String.valueOf(game.scorePointsNumb));
                if(game.life<=0){
                    spaceBackground.stop();
                    if(game.getMusicOn())
                        fail.play();
                    DecimalFormat formatInt = new DecimalFormat("0");
                    formatInt.setMinimumIntegerDigits(2);
                    DecimalFormat format = new DecimalFormat("0.00");
                    time = String.valueOf(lvlTime);
                    Gdx.app.log("MYTIME1", time);
                    float minutes = lvlTime/60;
                    time = String.valueOf(format.format(minutes));
                    Gdx.app.log("MYTIME2", time);
                    String[] s;
                    if(time.contains("."))
                        s = time.split("\\.");
                    else
                        s = time.split(",");
                    double seconds = (minutes-Double.valueOf(s[0]))*60;
                    minutes = Integer.valueOf(s[0]);
                    time = String.valueOf(format.format(seconds));
                    String[] m;
                    if(time.contains("."))
                        m = time.split("\\.");
                    else
                        m = time.split(",");
                    double miliseconds = (seconds-Float.valueOf(m[0]))*1000;
                    seconds = Integer.valueOf(m[0]);
                    time = formatInt.format(minutes)+":"+formatInt.format(seconds)+":"+formatInt.format(miliseconds);
                    Gdx.app.log("MYTIME3", time);
                    gameState = GameStates.OVER;
                    menu.setPosition(307, 650);
                    yourScoreLbl.setPosition(0, 1020);
                    Widget[] images = {failText};
                    ImageButton[] btns = {replay, menu};
                    game.setMessage(0, 0, 0, 0, images, btns, yourScoreLbl, true);
                    dataUser.addValue(game.scorePointsNumb, time);
                    game.saveData();
                }
            }
        }else if(gameState == GameStates.PAUSE){
            //Dibujar las paredes
            for(int i= 0; i < 14; i++){
                wallsLeft.get(i).draw(batch);
                wallsRigth.get(i).draw(batch);
            }
            wallUp.draw(batch);
            for(int i = 0; i < squaresInd.size; i++){
                squaresInd.get(i).draw(batch);
            }
            //Si esta chocando con los cuadros destruibles
            for(int i = 0; i < squaresDes.size; i++){
                squaresDes.get(i).draw(batch);
            }
        }else if(gameState == GameStates.NEXT){
            //Dibujar las paredes
            for(int i= 0; i < 14; i++){
                wallsLeft.get(i).draw(batch);
                wallsRigth.get(i).draw(batch);
            }
            wallUp.draw(batch);
            for(int i = 0; i < squaresInd.size; i++){
                squaresInd.get(i).draw(batch);
            }
            //Si esta chocando con los cuadros destruibles
            for(int i = 0; i < squaresDes.size; i++){
                squaresDes.get(i).draw(batch);
            }
        }else if(gameState == GameStates.WIN){
            //Dibujar las paredes
            for(int i= 0; i < 14; i++){
                wallsLeft.get(i).draw(batch);
                wallsRigth.get(i).draw(batch);
            }
            wallUp.draw(batch);
            for(int i = 0; i < squaresInd.size; i++){
                squaresInd.get(i).draw(batch);
            }
            //Si esta chocando con los cuadros destruibles
            for(int i = 0; i < squaresDes.size; i++){
                squaresDes.get(i).draw(batch);
            }
            if(game.online){
                if(game.dataGet){
                    if(game.textOnline.equals("Registrado")){
                        gameState = GameStates.END;
                        onOut = true;
                        game.addTransitionOutScreen("Story1");
                    }else{
                        yourScoreLbl.setY(1220);
                        yourScoreLbl.setText(game.textOnline);
                        retry.setVisible(true);
                        dontSave.setVisible(true);
                        next.setVisible(false);
                    }
                    game.dataGet = false;
                }
            }else{
                gameState = GameStates.END;
                onOut = true;
                game.addTransitionOutScreen("Story1");
            }
        }else if(gameState == GameStates.OVER){
            //Dibujar las paredes
            for(int i= 0; i < 14; i++){
                wallsLeft.get(i).draw(batch);
                wallsRigth.get(i).draw(batch);
            }
            wallUp.draw(batch);
            for(int i = 0; i < squaresInd.size; i++){
                squaresInd.get(i).draw(batch);
            }
            //Si esta chocando con los cuadros destruibles
            for(int i = 0; i < squaresDes.size; i++){
                squaresDes.get(i).draw(batch);
            }
        }else if(gameState == GameStates.DETH){
            game.life--;
            gameState = GameStates.RUN;
            clearObjects();
        }else if(gameState == GameStates.END){

        }
        ball.draw(batch);
        base.update(delta);
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    public void setScene(){
        music.setPosition(870.581f, 1821.267f);
        level = 0;
        game.life = 1;
        game.scorePointsNumb = 0;
        setWalls();
        changeLevel();
    }

    @Override
    public void show(){
        setScene();
        //game.addTransitionInScreen();
    }

    @Override
    public void hide(){
        spaceBackground.stop();
    }

    public void addRandPower(float x, float y){
        if(inactivePowers.size != 0){
            Power power = pR;
            int rand = MathUtils.random(0, inactivePowers.size-1);
            int powerIndex = inactivePowers.get(rand);
            if(powerIndex == 0){
                power = pR;
            }else if(powerIndex == 1){
                power = pT;
            }else if(powerIndex == 2){
                power = pCo;
            }else if(powerIndex == 3){
                power = pCr;
            }else if(powerIndex == 4){
                power = pA;
            }
            power.setPower(x, y);
            power.setActive(true);
            powers.add(power);
            inactivePowers.removeValue(powerIndex, true);
        }
    }

    public void runPower(Power p, boolean active){
        if(p.getPower() == Power.Powers.A){
            if(active){
                game.scorePointsNumb += 10;
                p.onPower = true;
                ball.setVel(200);
            }else{
                p.onPower = false;
                ball.setVel(-200);
                p.setEndTime(false);
            }
        }else if(p.getPower() == Power.Powers.R){
            base.changeSize();
            if(active){
                game.scorePointsNumb += 10;
                p.onPower = true;
            }else{
                p.onPower = false;
                p.setEndTime(false);
            }
        }else if(p.getPower() == Power.Powers.CO){
            if(active){
                game.scorePointsNumb += 10;
                p.onPower = true;
                ball.setVel(-200);
            }else{
                p.onPower = false;
                ball.setVel(200);
                p.setEndTime(false);
            }
        }else if(p.getPower() == Power.Powers.CR){
            game.scorePointsNumb += 10;
            if(active){
                p.onPower = true;
                ball.reset();
                base.reset();
                canAddTime = false;
                pR.reset();
            }else{
                p.onPower = false;
                base.setOnBase(false);
                base.setCanMove(true);
                canAddTime = true;
                p.setEndTime(false);
            }
        }else if(p.getPower() == Power.Powers.T){
            if(active){
                game.scorePointsNumb += 10;
                if(game.life<3){
                    game.life++;
                }else
                    game.scorePointsNumb += 100;
            }
        }
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose(){
        //batch.dispose();
        spaceBackground.dispose();
    }

    public void setLvl(){
        timeOnScreen = 0;
        lvlTime = 0;
        float posX = 50;
        float posY = 560;
        squaresDes.clear();
        squaresInd.clear();
        lvlData = new int[12][14];
        if(level == 1){
            textLbl1.setStyle(new Label.LabelStyle(game.fontPage, Color.WHITE));
            textLbl1.setAlignment(Align.center);
            textLbl1.setOrigin(0, 0);
            textLbl1.setBounds(0, 603, 1080, 370);
            textLbl1.setText("DESCUBRO\n\nINVESTIGO");
            textLbl2.setStyle(new Label.LabelStyle(game.fontPage, Color.WHITE));
            textLbl2.setAlignment(Align.center);
            textLbl2.setOrigin(0, 0);
            textLbl2.setBounds(0, 1132, 1080, 377);
            textLbl2.setText("ESTUDIO\n\nTRABAJO");
            lvlData = lvl1SqDesPos;
            ball.setLvlVel(450);
        }else if(level == 2){
            textLbl1.setStyle(new Label.LabelStyle(game.fontText, Color.WHITE));
            textLbl1.setAlignment(Align.center);
            textLbl1.setBounds(0, 696, 1080, 658);
            textLbl1.setText(
                    "\nDESARROLLO                 MUJER Y DESARROLLO\n" +
                            "SOSTENIBLE                            SOCIAL             \n\n\n" +
                            "EMPRENDIMIENTO\n\n\n\n" +
                            "    FUNDAMENTACIÓN                  APROPIACIÓN DE      \n" +
                            "       LÓGICO                                    LA CIENCIA,   \n" +
                            "      MATEMÁTICO Y                           TECNOLOGÍA        \n" +
                            "     LECTURA CRITICA                       E INNOVACIÓN        ");
            textLbl2.setStyle(new Label.LabelStyle(game.fontTitle, Color.WHITE));
            textLbl2.setAlignment(Align.center);
            textLbl2.setBounds(0, 1419, 1080, 53);
            textLbl2.setText("AXIOMAS");
            lvlData = lvl2SqDesPos;
            ball.setLvlVel(550);
        }else if(level == 3){
            textLbl1.setStyle(new Label.LabelStyle(game.fontText, Color.WHITE));
            textLbl1.setAlignment(Align.center);
            textLbl1.setBounds(0, 1106, 1080, 164);
            textLbl1.setText(
                    "Estamos comprometidos con\n" +
                            "la formación integral del ser humano\n" +
                            "y en especial de la mujer\n" +
                            "atravéz de un modelo innovador\n");
            textLbl2.setStyle(new Label.LabelStyle(game.fontTitle, Color.WHITE));
            textLbl2.setAlignment(Align.center);
            textLbl2.setBounds(0, 1327, 1080, 53);
            textLbl2.setText("MISIÓN");
            lvlData = lvl3SqDesPos;
            ball.setLvlVel(650);
        }else if(level == 4){
            textLbl1.setStyle(new Label.LabelStyle(game.fontText, Color.WHITE));
            textLbl1.setAlignment(Align.center);
            textLbl1.setBounds(0, 1055, 1080, 381);
            textLbl1.setText(
                    "Ser en el 2022 una institución\n" +
                            "de formación respetada,\n" +
                            "entretenida, innovadora y\n" +
                            "reconocida nacional\n" +
                            "e internacionalmente,\n" +
                            "por su contribución a\n" +
                            "la transformación\n" +
                            "social.");
            textLbl2.setStyle(new Label.LabelStyle(game.fontTitle, Color.WHITE));
            textLbl2.setAlignment(Align.center);
            textLbl2.setBounds(0, 1463, 1080, 53);
            textLbl2.setText("VISIÓN");
            lvlData = lvl4SqDesPos;
            ball.setLvlVel(750);
        }
        for(int i = 0; i < lvlData.length; i++){
            for(int j = 0; j < lvlData[i].length; j++){
                Sprite sq;
                if(lvlData[i][j] != 0){
                    if(lvlData[i][j] == 6){
                        sq = textureAtlas.createSprite("gris");
                        sq.setPosition(posX, posY);
                        squaresInd.add(sq);
                    }else{
                        sq = textureAtlas.createSprite("amarillo");
                        if(lvlData[i][j] == 2){
                            sq = textureAtlas.createSprite("azul");
                        }else if(lvlData[i][j] == 3){
                            sq = textureAtlas.createSprite("verde");
                        }else if(lvlData[i][j] == 4){
                            sq = textureAtlas.createSprite("fucsia");
                        }else if(lvlData[i][j] == 5){
                            sq = textureAtlas.createSprite("blanco");
                        }
                        sq.setPosition(posX, posY);
                        squaresDes.add(sq);
                    }
                }
                posX += 70;
            }
            posX = 50;
            posY += 90;
        }
    }

    public void setWalls(){
        float y = -88;
        for(int i = 0; i < 14; i++){
            Sprite w1 = new Sprite(new Texture("marcoIzquierda.png"));
            w1.setPosition(0, y);
            Sprite w2 = new Sprite(new Texture("marcoDerecha.png"));
            w2.setPosition(1030, y);
            wallsLeft.add(w1);
            wallsRigth.add(w2);
            y += 132;
        }
        wallUp = new Sprite(new Texture("marcoArriba.png"));
        wallUp.setPosition(0, 1760);
    }

    public void changeLevel(){
        if(level<4){
            gameState = GameStates.END;
            level++;

            //Agregar los actores al nivel
            stage.addActor(music);
            stage.addActor(pause);
            stage.addActor(pauseText);
            pauseText.setVisible(false);
            stage.addActor(contin);
            contin.setVisible(false);
            stage.addActor(menu);
            menu.setVisible(false);
            stage.addActor(retry);
            retry.setVisible(false);
            stage.addActor(dontSave);
            dontSave.setVisible(false);
            stage.addActor(failText);
            failText.setVisible(false);
            stage.addActor(replay);
            replay.setVisible(false);
            stage.addActor(next);
            next.setVisible(false);
            stage.addActor(yourScoreLbl);
            yourScoreLbl.setVisible(false);
            stage.addActor(game.score);
            stage.addActor(game.scorePoints);
            clearObjects();
            base.addToStage(stage);

            setLvl();

            game.addTransitionInScreen();
            gameState = GameStates.RUN;
        }
    }

    public void clearObjects(){
        ball.reset();
        if(level == 1)
            ball.setLvlVel(350);
        else if(level == 2)
            ball.setLvlVel(450);
        else if(level == 3)
            ball.setLvlVel(550);
        else if(level == 4)
            ball.setLvlVel(650);
        base.reset();
        canAddTime = false;
        powers.clear();
        inactivePowers.clear();
        for(int i = 0; i < 5; i++)
            inactivePowers.add(i);
        pR.reset();
        pCo.reset();
        pA.reset();
        pCr.reset();
        pT.reset();
    }
}