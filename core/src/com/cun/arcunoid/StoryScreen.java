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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by CUN on 11/01/2018.
 */

public class StoryScreen extends BaseScreen{
    int page = 0;
    JsonValue datos;

    Sprite logo;
    String message = "";
    Sprite[] sprites = new Sprite[40];
    Array<Sprite> drawSprites = new Array<Sprite>();
    Array<Sprite> drawPattern = new Array<Sprite>();
    ImageButton skip, next, start;
    TextureAtlas playable, story, story2, story3, story4, buttons;
    Label storyText;
    String[] powers = {"Respeto:\nPermite aumentar el tamaño\nde la nave \"CUN\".", "Creatividad:\nPermite retomar la nave \"MAE\"\npara enviarla nuevamente\ndesde otra posición.", "Tolerancia:\nBrinda una nave localizadora\nmás.", "Compromiso:\nDisminuye la velocidad de la\nnave \"MAE\".", "Adaptabilidad:\nAumenta la velocidad de la\nnave \"MAE\"."};
    float[][] posPowerText = {{350.397f, 1345.319f}, {350.397f, 1154.623f}, {350.397f, 899.569f}, {350.397f, 690.386f}, {350.397f, 451.667f}};
    Sprite auxSquare;
    Image logoCebiac, win;
    ImageButton scores, replay, menu;
    float time = 0;
    Sound victory, ship, explosion, alert, robot, robotSearch;
    Sound[] storySounds;
    Music storyMusic;
    private boolean onOut = false;
    boolean newSound = false;
    float timeSound = 0;

    StoryScreen(arCunoid g, int type){
        super(g);

        storyMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/ObservingTheStar.ogg"));
        storyMusic.setLooping(true);
        storyMusic.play();

        victory = Gdx.audio.newSound(Gdx.files.internal("sounds/victory.ogg"));
        victory.setVolume(1, 0.5f);

        logo = new Sprite(new Texture("logo.png"));
        logo.setBounds(204, 1675, 672, 179);
        JsonReader j = new JsonReader();
        datos = j.parse(Gdx.files.internal("levelData/story.json").reader("UTF8"));
        story = new TextureAtlas("historia1.txt");
        Skin skin = new Skin(story);
        Label.LabelStyle style = new Label.LabelStyle(game.font, Color.WHITE);
        storyText = new Label("", style);
        storyText.setAlignment(Align.left);
        if(type == 0){
            ship = Gdx.audio.newSound(Gdx.files.internal("sounds/ship2.ogg"));
            explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion1.ogg"));
            alert = Gdx.audio.newSound(Gdx.files.internal("sounds/alert2.ogg"));
            robot = Gdx.audio.newSound(Gdx.files.internal("sounds/robotness.ogg"));
            robotSearch = Gdx.audio.newSound(Gdx.files.internal("sounds/calculating.ogg"));
            storySounds = new Sound[5];
            storySounds[0] = ship;
            storySounds[1] = explosion;
            storySounds[2] = alert;
            storySounds[3] = robot;
            storySounds[4] = robotSearch;
            Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            p.setColor(0.09f, 0.157f, 0.427f, 0.8f);
            p.fill();
            auxSquare = new Sprite(new Texture(p));
            auxSquare.setBounds(0, 228, 1080, 858);
            playable = new TextureAtlas("jugable2.txt");
            story = new TextureAtlas("historia1.txt");
            story2 = new TextureAtlas("historia2.txt");
            story3 = new TextureAtlas("historia3.txt");
            sprites[0] = story.createSprite("naveGrande");
            sprites[1] = story.createSprite("parcheNave");
            sprites[2] = story.createSprite("lineas-de-nave");
            sprites[3] = story.createSprite("lineas-de-nave");
            sprites[4] = story.createSprite("meteoro");
            sprites[5] = story.createSprite("efecto");
            sprites[6] = story2.createSprite("panel-1");
            sprites[7] = story2.createSprite("linea-panel-1-1");
            sprites[8] = story2.createSprite("linea-panel-1-2");
            sprites[9] = story2.createSprite("contenido-panel-1-1");
            sprites[10] = story2.createSprite("contenido-panel-1-2");
            sprites[11] = story2.createSprite("contenido-panel-1-3");
            sprites[12] = story3.createSprite("patron");
            sprites[13] = story3.createSprite("cuadro-paneles");
            sprites[14] = story3.createSprite("cuadro-paneles");
            sprites[15] = story3.createSprite("cuadro-paneles");
            sprites[16] = story3.createSprite("linea-panel-2-1");
            sprites[17] = story3.createSprite("linea-panel-2-2");
            sprites[18] = story3.createSprite("linea-panel-2-3");
            sprites[19] = story3.createSprite("contenido-panel-2-1");
            sprites[20] = story3.createSprite("contenido-panel-2-2");
            sprites[21] = story3.createSprite("contenido-panel-2-3");
            sprites[22] = playable.createSprite("poder-respeto");
            sprites[23] = playable.createSprite("poder-creatividad");
            sprites[24] = playable.createSprite("poder-tolerancia");
            sprites[25] = playable.createSprite("poder-compromiso");
            sprites[26] = playable.createSprite("poder-adaptabilidad");
            sprites[27] = new Sprite(new Texture("panel-2.png"));
            sprites[28] = playable.createSprite("nave-centro");
            sprites[29] = playable.createSprite("nave-lado-izq");
            sprites[30] = playable.createSprite("nave-lado-izq");
            sprites[30].flip(true, false);
            sprites[31] = playable.createSprite("ball");
            sprites[32] = story.createSprite("mano");
            sprites[33] = new Sprite(new Texture("luz-proyeccion.png"));

            float pattX = -30;
            float pattY = 228;
            for(int x = 0; x < 3; x++){
                for(int y = 0; y < 4; y++){
                    Sprite patt = new Sprite(sprites[12]);
                    patt.setPosition(pattX, pattY);
                    drawPattern.add(patt);
                    pattX += 297;
                }
                pattX = -30;
                pattY += 286;
            }
            skip = new ImageButton(skin.getDrawable("omitir"));
            skip.setPosition(43.452f, 25.547f);
            skip.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    return  true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    page = 5;
                    nextPage();
                }
            });
            start = new ImageButton(skin.getDrawable("comenzar"));
            start.setPosition(263.416f, 25.456f);
            start.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    return  true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    onOut = true;
                    game.addTransitionOutScreen("Level");
                }
            });
        }else{
            page = 6;
            story4 = new TextureAtlas("historia4.txt");
            buttons = new TextureAtlas("botones.txt");
            Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            p.setColor(0, 0, 0, 0.8f);
            p.fill();
            auxSquare = new Sprite(new Texture(p));
            auxSquare.setBounds(0, 460, 1080, 1000);
            sprites[0] = story4.createSprite("nave");
            sprites[1] = story4.createSprite("galaxia");
            logoCebiac = new Image(new Texture("cebiac.png"));
            logoCebiac.setPosition(540-logoCebiac.getWidth()/2, 1130.225f);
            Skin s = new Skin(story4);
            Skin b = new Skin(buttons);
            win = new Image(s.getDrawable("ganaste"));
            win.setPosition(540-win.getWidth()/2, 1200);
            scores = new ImageButton(b.getDrawable("btnScoresUp"), b.getDrawable("btnScoresDown"));
            scores.setPosition(540-scores.getWidth()/2, 930);
            scores.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    return  true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    onOut = true;
                    ImageButton[] btns = new ImageButton[3];
                    btns[0] = scores;
                    btns[1] = replay;
                    btns[2] = menu;
                    Widget[] images = {win};
                    game.setMessage(0, 650, 1080, 858, images, btns, storyText, false);
                    game.addTransitionOutScreen("Score");
                }
            });
            replay = new ImageButton(b.getDrawable("btnReplayUp"), b.getDrawable("btnReplayDown"));
            replay.setPosition(540-replay.getWidth()/2, 760);
            replay.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    return  true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    onOut = true;
                    game.addTransitionOutScreen("Level");
                }
            });
            menu = new ImageButton(b.getDrawable("btnStartUp"), b.getDrawable("btnStartDown"));
            menu.setPosition(540-menu.getWidth()/2, 590);
            menu.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    return  true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    onOut = true;
                    game.addTransitionOutScreen("Menu");
                }
            });
        }
        next = new ImageButton(skin.getDrawable("siguiente"));
        next.setPosition(742.342f, 25.547f);
        next.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                nextPage();
            }
        });
        nextPage();
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0.0745f, 0.13f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        camera.update();
        if(onOut)
            storyMusic.setVolume(storyMusic.getVolume()-0.05f);
        if(!game.getMusicOn()){
            storyMusic.pause();
            if(page<7){
                for(int i = 0; i < storySounds.length; i++){
                    storySounds[i].pause();
                }
            }
        }else{
            storyMusic.play();
            if(newSound){
                if(page==1){
                    if(timeSound>1.8f){
                        storySounds[0].play();
                        newSound = false;
                        timeSound = 0;
                    }else{
                        timeSound += delta;
                    }
                }else if(timeSound>0.8f){
                    Gdx.app.log("nave", "entre");
                    storySounds[2].play();
                    newSound = false;
                    timeSound = 0;
                }else{
                    timeSound += delta;
                }
            }
        }
        batch.begin();
        drawBack(delta, false);
        if(page >= 7){
            if(page == 8){
                logo.draw(batch);
                if(time>5 && time<6){
                    if(game.getMusicOn())
                        victory.play();
                    ImageButton[] btns = new ImageButton[3];
                    btns[0] = scores;
                    btns[1] = replay;
                    btns[2] = menu;
                    storyText.setBounds(0, 1107, 1080, 50);
                    storyText.setAlignment(Align.center);
                    Widget[] images = {win};
                    logoCebiac.setVisible(false);
                    game.setMessage(0, 650, 1080, 858, images, btns, storyText, true);
                    time = 6;
                }else{
                    time += delta;
                }
            }else{
                for(int i = 0; i < drawSprites.size; i++){
                    drawSprites.get(i).draw(batch);
                }
            }
        }else{
            logo.draw(batch);
            if(page==3 || page == 4){
                for(int i = 0; i < drawPattern.size; i++){
                    drawPattern.get(i).draw(batch);
                }
                auxSquare.draw(batch);
            }
            for(int i = 0; i < drawSprites.size; i++){
                drawSprites.get(i).draw(batch);
            }
            if(page == 6){
                for(int i = 0; i < powers.length; i++){
                    game.fontSmall.draw(batch, powers[i], posPowerText[i][0], posPowerText[i][1]);
                }
            }
        }
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show(){
        music.setPosition(975.581f, 1821.267f);
        stage.addActor(music);
        stage.addActor(storyText);
        stage.addActor(next);
        if(page==1)
            stage.addActor(skip);
        else{
            Gdx.app.log("Entre", "SIP");
            stage.addActor(logoCebiac);
            logoCebiac.setVisible(false);
            stage.addActor(win);
            win.setVisible(false);
            stage.addActor(scores);
            scores.setVisible(false);
            stage.addActor(replay);
            replay.setVisible(false);
            stage.addActor(menu);
            menu.setVisible(false);
        }
        game.addTransitionInScreen();
    }

    private void nextPage(){
        game.setBackground("story"+page);
        drawSprites.clear();
        JsonValue dataPage = datos.get(page);
        message = dataPage.get(0).toString();
        storyText.setText(message);
        float[] textBounds = dataPage.get(5).asFloatArray();
        storyText.setBounds(textBounds[0], textBounds[1], textBounds[2], textBounds[3]);
        float[] blackBounds = dataPage.get(1).asFloatArray();
        game.back.setBounds(blackBounds[0], blackBounds[1], blackBounds[2], blackBounds[3]);
        game.back.setAlpha(0.6f);
        int[] indexSprites = dataPage.get(2).asIntArray();
        int cantObj = dataPage.get(3).asInt();
        float[][] spritesBounds = new float[cantObj][4];
        for(int i = 0; i < cantObj; i++){
            if(indexSprites[i]!=12){
                for(int j = 0; j < 4; j++){
                    spritesBounds[i] = dataPage.get(4).get(i).asFloatArray();
                }
            }
        }
        if(page<4 && game.getMusicOn()){
            int[] sounds = dataPage.get(6).asIntArray();
            if(sounds.length>1 || page == 0) {
                if(page != 0)
                    storySounds[sounds[0]].play();
                newSound = true;
            }else{
                storySounds[sounds[0]].play();
            }
        }
        for(int i = 0; i < cantObj; i++){
            if(indexSprites[i]!=12){
                sprites[indexSprites[i]].setBounds(spritesBounds[i][0], spritesBounds[i][1], spritesBounds[i][2], spritesBounds[i][3]);
                drawSprites.add(sprites[indexSprites[i]]);
            }
        }
        if(page==5){
            skip.remove();
            next.remove();
            stage.addActor(start);
        }
        if(page==7){
            logoCebiac.setVisible(true);
            next.remove();
            storyText.setAlignment(Align.center);
        }
        page++;
    }

    @Override
    public void hide(){
        storyMusic.stop();
    }

    @Override
    public void dispose(){
        storyMusic.dispose();
    }
}
