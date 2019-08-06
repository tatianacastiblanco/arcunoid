package com.cun.arcunoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by CUN on 12/01/2018.
 */

public class BaseScreen extends ScreenAdapter{
    public arCunoid game;
    public OrthographicCamera camera;
    public FitViewport viewport;
    public Stage stage;
    public SpriteBatch batch;

    public ImageButton music;
    public Sprite planet1, planet2, planet3, planet4;
    public Array<Sprite> nebulous;
    public Array<Spark> sparks;
    public MovingAsteroid comet, meteor;
    public TextureAtlas backAtlas;
    public String time;

    BaseScreen(arCunoid g){
        game = g;
        camera = g.camera;
        viewport = g.viewport;
        stage = g.stage;
        batch = g.batch;

        time = game.time;
        music = game.music;
        sparks = game.sparks;
        nebulous = game.nebulous;
        planet1 = game.planet1;
        planet2 = game.planet2;
        planet3 = game.planet3;
        planet4 = game.planet4;
        meteor = game.meteor;
        comet = game.comet;

        backAtlas = new TextureAtlas("fondo.txt");
    }

    @Override
    public void show(){
        game.addTransitionInScreen();
    }
    
    public void drawBack(float delta, boolean fullBack){
        //Dibujar las nebulosas
        for(int i = 0; i < 19; i++){
            nebulous.get(i).draw(batch);
        }
        planet1.draw(batch);
        planet2.draw(batch);
        planet3.draw(batch);
        comet.update(delta);
        comet.draw(batch);
        meteor.update(delta);
        meteor.draw(batch);
        planet4.draw(batch);
        //Dibujar las estrellas
        for(int i = 0; i < 20; i++){
            sparks.get(i).update();
            sparks.get(i).draw(batch);
        }
        if(fullBack)
            game.back.setBounds(0, 0, 1080, 1920);
        game.back.draw(batch);
    }
}
