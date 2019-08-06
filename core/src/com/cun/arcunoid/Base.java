package com.cun.arcunoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

/**
 * Created by CUN on 4/01/2018.
 */

public class Base{
    static float origX = 420;
    static float origY = 170;
    boolean onBase = true;
    boolean canMove = true;
    float[] leftPos, centrePos, rigthPos, boxPos, firePos, firePos2;
    Image left, centre, centreBig, rigth, collisionBox, touchBox;
    int currentCentre = 0;
    Array<Image> fireFrames;
    Array<Image> fireFrames2;
    Animation<Image> animFire;
    int currentFire = 0;
    float timeAnim = 0;

    Base(Drawable leftT, Drawable centreT, Drawable centreBigT, TextureRegion rigthT, float x, float y){
        Texture fire = new Texture("fuegoAnim.png");
        TextureRegion[][] tmp = TextureRegion.split(fire,
                fire.getWidth() / 5,
                fire.getHeight() / 2);
        TextureRegion[] regionsFire = new TextureRegion[10];
        int index = 0;
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 5; j++){
                    regionsFire[index++] = tmp[i][j];
            }
        }
        leftPos = new float[2];
        centrePos = new float[2];
        rigthPos = new float[2];
        boxPos = new float[2];
        firePos = new float[2];
        firePos2 = new float[2];
        Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        p.setColor(0,0,0,0);
        left = new Image(leftT);
        leftPos[0] = x;
        leftPos[1] = y;
        left.setPosition(x, y);
        centre = new Image(centreT);
        centrePos[0] = x+72;
        centrePos[1] = y+6;
        centre.setPosition(centrePos[0], centrePos[1]);
        centreBig = new Image(centreBigT);
        centreBig.setPosition(centrePos[0], centrePos[1]);
        centreBig.setVisible(false);
        rigth = new Image(rigthT);
        rigth.setOrigin(72,79.5f);
        rigth.setRotation(90);
        rigth.setOrigin(0,0);
        rigthPos[0] = centrePos[0]+168;
        rigthPos[1] = y;
        rigth.setPosition(rigthPos[0], rigthPos[1]);
        collisionBox = new Image(new Texture(p));
        collisionBox.setBounds(x, y, 240, 79);
        touchBox = new Image(new Texture(p));
        touchBox.setBounds(x-50, y-25, 340, 179);
        firePos[0] = x+15.31f;
        firePos[1] = y-44;
        firePos2[0] = x+171.187f;
        firePos2[1] = y-44;
        fireFrames = new Array<Image>(5);
        for(int i = 0; i < 9; i++){
            Image img = new Image(regionsFire[i]);
            img.setPosition(firePos[0], firePos[1]);
            fireFrames.add(img);
        }
        fireFrames2 = new Array<Image>(5);
        for(int i = 0; i < 9; i++){
            Image img = new Image(regionsFire[i]);
            img.setPosition(firePos2[0], firePos2[1]);
            fireFrames2.add(img);
        }
        animFire = new Animation<Image>(0.2f, fireFrames, Animation.PlayMode.LOOP);
    }

    public void addListener(InputListener inputListenerl){
        touchBox.addListener(inputListenerl);
    }

    public boolean getOnBase(){
        return onBase;
    }

    public void setOnBase(boolean oB){
        onBase = oB;
    }

    public boolean getCanMove(){
        return canMove;
    }

    public void setCanMove(boolean cM){
        canMove = cM;
    }

    public void setPosition(float x, float y){
        leftPos[0] += x;
        leftPos[1] += y;
        left.setPosition(leftPos[0], leftPos[1]);
        centrePos[0] += x;
        centrePos[1] += y;
        if(currentCentre == 0)
            centre.setPosition(centrePos[0], centrePos[1]);
        else
            centreBig.setPosition(centrePos[0], centrePos[1]);
        rigthPos[0] += x;
        rigthPos[1] += y;
        rigth.setPosition(rigthPos[0], rigthPos[1]);
        collisionBox.setPosition(leftPos[0], leftPos[1]);
        touchBox.setPosition(leftPos[0]-50, leftPos[1]-50);
        firePos[0] += x;
        firePos2[0] += x;
        for(int i = 0; i < fireFrames.size; i++){
            fireFrames.get(i).setPosition(firePos[0], firePos[1]);
            fireFrames2.get(i).setPosition(firePos2[0], firePos2[1]);
        }
    }

    public void changeSize(){
        if(currentCentre == 0){
            currentCentre = 1;
            if (leftPos[0] - 72.5 <= 50){
                leftPos[0] = 51;
            }else if(leftPos[0]-72.5+385 >= 1030){
                leftPos[0] = 644;
            }else{
                leftPos[0] -= 72.5f;
            }
            left.setX(leftPos[0]);
            centrePos[0] = leftPos[0]+72;
            rigthPos[0] = centrePos[0]+313;
            rigth.setX(rigthPos[0]);
            centreBig.setPosition(centrePos[0], centrePos[1]);
            centre.setVisible(false);
            centreBig.setVisible(true);
            firePos[0] = leftPos[0]+15.31f;
            firePos2[0] = leftPos[0]+316.187f;
            for(int i = 0; i < fireFrames.size; i++){
                fireFrames.get(i).setPosition(firePos[0], firePos[1]);
                fireFrames2.get(i).setPosition(firePos2[0], firePos2[1]);
            }
            collisionBox.setWidth(385);
            touchBox.setSize(385, 179);
        }else{
            currentCentre = 0;
            leftPos[0] += 72.5f;
            left.setX(leftPos[0]);
            centrePos[0] = leftPos[0]+72;
            rigthPos[0] = centrePos[0]+168;
            rigth.setX(rigthPos[0]);
            centre.setPosition(centrePos[0], centrePos[1]);
            centreBig.setVisible(false);
            centre.setVisible(true);
            firePos[0] = leftPos[0]+15.31f;
            firePos2[0] = leftPos[0]+171.187f;
            for(int i = 0; i < fireFrames.size; i++){
                fireFrames.get(i).setPosition(firePos[0], firePos[1]);
                fireFrames2.get(i).setPosition(firePos2[0], firePos2[1]);
            }
            collisionBox.setWidth(240);
            touchBox.setSize(340, 179);
        }
    }

    public void update(float delta){
        timeAnim += delta;
        currentFire = animFire.getKeyFrameIndex(timeAnim);
        Gdx.app.log("Frame", String.valueOf(currentFire));
        for(int i = 0; i < fireFrames.size; i++){
            if(i == currentFire){
                fireFrames.get(i).setVisible(true);
                fireFrames2.get(i).setVisible(true);
            }else{
                fireFrames.get(i).setVisible(false);
                fireFrames2.get(i).setVisible(false);
            }
        }
    }

    public void addToStage(Stage s){
        s.addActor(left);
        s.addActor(centre);
        s.addActor(centreBig);
        s.addActor(rigth);
        s.addActor(collisionBox);
        s.addActor(touchBox);
        for(int i = 0; i < fireFrames.size; i++){
            s.addActor(fireFrames.get(i));
            s.addActor(fireFrames2.get(i));
        }
    }

    public float getX(){
        return leftPos[0];
    }

    public float getY(){
        return leftPos[1];
    }

    public float getWidth(){
        return collisionBox.getWidth();
    }

    public float getHeight(){
        return collisionBox.getHeight();
    }

    public void setToOrig(){
        leftPos[0] = origX;
        leftPos[1] = origY;
        left.setPosition(origX, origY);
        centrePos[0] = origX+72;
        centrePos[1] = origY+6;
        centre.setPosition(centrePos[0], centrePos[1]);
        rigthPos[0] = centrePos[0]+168;
        rigthPos[1] = origY;
        rigth.setPosition(rigthPos[0], rigthPos[1]);
        collisionBox.setPosition(origX, origY);
        touchBox.setPosition(origX-50, origY-50);
        firePos[0] = origX+15.31f;
        firePos[1] = origY-44;
        firePos2[0] = origX+171.187f;
        firePos2[1] = origY-44;
        for(int i = 0; i < fireFrames.size; i++){
            fireFrames.get(i).setPosition(firePos[0], firePos[1]);
            fireFrames2.get(i).setPosition(firePos2[0], firePos2[1]);
        }
    }

    public void reset(){
        setToOrig();
        canMove = false;
        onBase = true;
        currentCentre = 0;
        currentFire = 0;
        timeAnim = 0;
        centreBig.setVisible(false);
        centre.setVisible(true);
        collisionBox.setWidth(240);
        touchBox.setSize(340, 179);
    }
}
