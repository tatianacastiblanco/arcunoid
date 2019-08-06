package com.cun.arcunoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

/**
 * Created by CUN on 13/02/2018.
 */

public class CreditsScreen extends BaseScreen{
    Image musicTitle, logo;
    Image next, back;
    Label text;
    String texts[] = {"UNA PRODUCCIÓN DE\n\n\n\n\n\n\n\n\nCREADO POR\n\nJonathan Hernando Sierra Hernández\n\n\nARTE Y DISEÑO\n\nJonathan Hernando Sierra Hernández\nJeimmy Carolina Barreto Gomez\n\n\n\nCEBIAC-CUN\n2018",
            "\n[YELLOW]Horror Sound Effects Library: Jingle_Lose_00\n[WHITE]por LittleRobotSoundFactory\nCC BY 3.0.\nTomado de freesound.org\n[YELLOW]Victory (Rock Guitar Tapping)\n[WHITE]por DeathToMayo\nTomado de freesound.org\n[YELLOW]Itemize\n[WHITE]por Scrampunk\nCC BY 3.0\nTomado de freesound.org\n[YELLOW]Video Game Beeps: Tone Beep\n[WHITE]por pan14\nTomado de freesound.org\n[YELLOW]Space Marines: Terradorian’s Theme Song\n[WHITE]por Telaron\nCC BY 3.0\nTomado de opengameart.org\n[YELLOW]Tragic Ambient Main Menu: Ambientmain_0\n[WHITE]por Brandon75689\nTomado de opengameart.org\n[YELLOW]Another Space Background Track: ObservingTheStar\n[WHITE]por yd\nTomado de opengameart.org\n",
            "\n[YELLOW]Bell Arpeggio 24 \n[WHITE]por CynicMusic (The Cynic Project)\nTomado de opengameart.org\n[YELLOW]Alert 2 \n[WHITE]por IFartInUrGeneralDirection \nCC BY 3.0 \nTomado de freesound.org\n[YELLOW]8-Bit Concert: Robotness \n[WHITE]por The Horrible Joke \nTomado de freesound.org\n[YELLOW]Calculating \n[WHITE]por freedomfightervictor \nTomado de freesound.org\n[YELLOW]Explosion Type Sound: Explosion 1 \n[WHITE]por Deganoth \nTomado de freesound.org\n[YELLOW]Ship2 \n[WHITE]por Sergenious \nCC BY 3.0 \nTomado de freesound.org\n[YELLOW]Spiral of Darkness \n[WHITE]por Trevor Lentz \nCC BY SA 3.0 \nTomado de opengameart.org\n"};
    int currentPage = 0;
    Music creditsMusic;

    CreditsScreen(arCunoid g) {
        super(g);

        creditsMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/SpiralOfDarkness.ogg"));
        creditsMusic.setLooping(true);
        creditsMusic.play();

        musicTitle = new Image(new Texture("musicTitle.png"));
        musicTitle.setPosition(99.543f, 1730.499f);

        logo = new Image(new Texture("cebiac.png"));
        logo.setPosition(540-logo.getWidth()/2, 1155.225f);

        Label.LabelStyle labelStyle = new Label.LabelStyle(game.font, Color.WHITE);
        text = new Label(texts[0], labelStyle);
        text.setBounds(0, 70, 1080, 1700);
        text.setAlignment(Align.center);

        next = new Image(new Texture("nextBtn.png"));
        next.setPosition(729.236f, 36.314f);
        next.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                if(currentPage<2){
                    if(currentPage==0){
                        text.setStyle(new Label.LabelStyle(game.fontCredits, Color.WHITE));
                        musicTitle.setVisible(true);
                        logo.setVisible(false);
                    }
                    currentPage++;
                }else{
                    game.setScreen(new MenuScreen(game));
                }
                text.setText(texts[currentPage]);
            }
        });
        back = new Image(new Texture("backBtn.png"));
        back.setPosition(52.322f, 36.314f);
        back.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                if(currentPage>0){
                    if(currentPage == 1){
                        text.setStyle(new Label.LabelStyle(game.font, Color.WHITE));
                        musicTitle.setVisible(false);
                        logo.setVisible(true);
                    }
                    currentPage--;
                }else{
                    game.setScreen(new MenuScreen(game));
                }
                text.setText(texts[currentPage]);
            }
        });
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0.0745f, 0.13f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        camera.update();
        if(!game.getMusicOn()){
            creditsMusic.pause();
        }else{
            creditsMusic.play();
        }
        batch.begin();
        drawBack(delta, true);
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show(){
        stage.clear();
        stage.addActor(logo);
        stage.addActor(musicTitle);
        musicTitle.setVisible(false);
        stage.addActor(text);
        stage.addActor(back);
        stage.addActor(next);
        game.addTransitionInScreen();
    }

    @Override
    public void hide(){
        stage.clear();
        creditsMusic.stop();
    }

    @Override
    public void dispose(){
        creditsMusic.dispose();
    }
}
