package com.cun.arcunoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CUN on 28/12/2017.
 */

public class ScoreScreen extends BaseScreen{
    Json json;
    boolean scoreOnline = false;
    Array<Sprite> wallsRigth = new Array<Sprite>(15);
    Array<Sprite> wallsLeft = new Array<Sprite>(15);
    Sprite wallUp, score;
    ImageButton menu, modeOn, modeOff;
    String[][] scores = {
            {" 1." ,"CUNY01","0", "--:--:---"},
            {" 2." ,"CUNY01" ,"0", "--:--:---"},
            {" 3." ,"CUNY01" ,"0", "--:--:---"},
            {" 4." ,"CUNY01" ,"0", "--:--:---"},
            {" 5." ,"CUNY01" ,"0", "--:--:---"},
            {" 6." ,"CUNY01" ,"0", "--:--:---"},
            {" 7." ,"CUNY01" ,"0", "--:--:---"},
            {" 8." ,"CUNY01" ,"0", "--:--:---"},
            {" 9." ,"CUNY01" ,"0", "--:--:---"},
            {"10." ,"CUNY01" ,"0", "--:--:---"}
    };
    float[] scoresPos = {50, 100};
    Label scorePoints;

    Music scoreMusic;

    Sprite subtitleOn;
    Sprite subtitleOff;

    HttpRequestBuilder builder;
    Net.HttpRequest request;

    ScoreScreen(arCunoid g){
        super(g);

        scoreMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/SpiralOfDarkness.ogg"));
        scoreMusic.setLooping(true);
        scoreMusic.play();

        game.getSaveData(false);

        TextureAtlas butts = new TextureAtlas("botones.txt");
        float y = -110;
        for(int i = 0; i < 15; i++){
            Sprite w1 = new Sprite(new Texture("marcoIzquierda.png"));
            w1.setPosition(0, y);
            Sprite w2 = new Sprite(new Texture("marcoDerecha.png"));
            w2.setPosition(1030, y);
            wallsLeft.add(w1);
            wallsRigth.add(w2);
            y += 132;
        }
        wallUp = new Sprite(new Texture("marcoArriba.png"));
        wallUp.setPosition(0, 1870);
        score = new Sprite(new Texture("puntajes.png"));
        score.setPosition(227.192f, 1606.873f);
        subtitleOn = new Sprite(new Texture("online.png"));
        subtitleOn.setPosition(648.171f, 1539.239f);
        subtitleOff = new Sprite(new Texture("offline.png"));
        subtitleOff.setPosition(439.24f, 1539.239f);
        Label.LabelStyle labelStyle = new Label.LabelStyle(game.fontBig, Color.BLUE);
        scorePoints = new Label("00000000", labelStyle);
        scorePoints.setOrigin(0, 0);
        scorePoints.setBounds(380, 902, 300, 50);
        scorePoints.setAlignment(Align.right);
        Skin buttText = new Skin(butts);
        menu = new ImageButton(buttText.getDrawable("btnStartUp"), buttText.getDrawable("btnStartDown"));
        menu.setPosition(318.732f, 63.182f);
        menu.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new MenuScreen(game));
            }
        });
        Skin button = new Skin();
        button.add("up", new Texture("onlineUp.png"));
        button.add("down", new Texture("onlineDown.png"));
        button.add("up2", new Texture("ownScoresUp.png"));
        button.add("down2", new Texture("ownScoresDown.png"));
        modeOn = new ImageButton(button.getDrawable("up"), button.getDrawable("down"));
        modeOn.setPosition(318.732f, 239.418f);
        modeOn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                scoreOnline = true;
                modeOn.setVisible(false);
                modeOff.setVisible(true);
                game.getSaveData(true);
            }
        });
        modeOff = new ImageButton(button.getDrawable("up2"), button.getDrawable("down2"));
        modeOff.setPosition(318.732f, 239.418f);
        modeOff.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                scoreOnline = false;
                modeOn.setVisible(true);
                modeOff.setVisible(false);
                game.getSaveData(false);
            }
        });
        modeOff.setVisible(false);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0.0745f, 0.13f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        if(game.dataGet){
            game.dataGet = false;
            scores = game.scores;
            Gdx.app.log("WebRequest2", game.scores[6][0]);
        }
        camera.update();
        batch.begin();
        if(!game.getMusicOn()){
            scoreMusic.pause();
        }else{
            scoreMusic.play();
        }
        drawBack(delta, true);
        game.back.draw(batch);
        for(int i= 0; i < 15; i++){
            wallsLeft.get(i).draw(batch);
            wallsRigth.get(i).draw(batch);
        }
        wallUp.draw(batch);
        score.draw(batch);
        if(scoreOnline)
            subtitleOn.draw(batch);
        else
            subtitleOff.draw(batch);
        String points = "";
        for(int i = scores.length-1; i >= 0; i--){
            for(int j = 0; j <= 3; j++){
                if(j == 0){
                    scoresPos[0] = 60;
                    game.fontBig.setColor(Color.RED);
                }else if(j == 1){
                    scoresPos[0] = 210f;
                    game.fontBig.setColor(Color.WHITE);
                }else if(j == 3){
                    scoresPos[0] = 720;
                    game.fontBig.setColor(Color.YELLOW);
                }
                if(j!=2)
                    game.fontBig.draw(batch, scores[i][j], scoresPos[0], scoresPos[1]);
                game.fontBig.getData().setLineHeight(50);
            }
            scoresPos[1] += 100;
        }
        scoresPos[1] = 550;
        for(int i = 0; i < 10; i++){
            String line = scores[i][2] + "\n\n";
            points += line;
        }
        scorePoints.setText(points);
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show(){
        stage.clear();
        music.setPosition(925.581f, 1775.267f);
        stage.addActor(music);
        stage.addActor(scorePoints);
        stage.addActor(modeOn);
        stage.addActor(modeOff);
        stage.addActor(menu);
        game.addTransitionInScreen();
    }

    @Override
    public void hide(){
        stage.clear();
        game.fontBig.getData().setLineHeight(82);
        scoreMusic.stop();
    }

    @Override
    public void dispose(){
        scoreMusic.dispose();
    }
}
