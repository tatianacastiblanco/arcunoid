package com.cun.arcunoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/**
 * Created by CUN on 26/12/2017.
 */

public class Ball extends Sprite{
    static float origX = 515;
    static float origY = 250;
    float X, Y, W, H;
    enum Directions{
        RIGHT, LEFT, UP, DOWN
    }
    Directions dirX = Directions.RIGHT;
    Directions dirY = Directions.UP;
    float vel = 350;
    boolean onBase = true;
    boolean hasChangeDir = false;
    boolean isColliding = false;

    Ball(TextureAtlas.AtlasRegion t, float x, float y, float w, float h){
        super(t);
        X = x;
        Y = y;
        W = w;
        H = h;
        this.setBounds(x, y, w, h);
    }

    public void update(float delta){
        if(dirX == Directions.RIGHT)
            X += (vel*delta);
        else
            X -= (vel*delta);
        if(dirY == Directions.UP)
            Y += (vel*delta);
        else
            Y -= (vel*delta);
        this.setPosition(X, Y);
    }

    public void reverseMove(){
        if(dirX == Directions.RIGHT){
            dirX = Directions.LEFT;
        }else{
            dirX = Directions.RIGHT;
        }
        if(dirY == Directions.UP){
            dirY = Directions.DOWN;
        }else{
            dirY = Directions.UP;
        }
    }

    public void setVel(float v){
        vel += v;
    }

    public void setLvlVel(float v){
        vel = v;
    }

    public void setHasChangeDir(boolean ch){
        hasChangeDir = ch;
    }

    public boolean getHasChangeDir(){
        return hasChangeDir;
    }

    public Directions getDirX(){
        return dirX;
    }

    public void setDirX(Directions dir){
        dirX = dir;
    }

    public Directions getDirY(){
        return dirY;
    }

    public void setDirY(Directions dir){
        dirY = dir;
    }
    
    public void moveOnCollide(Array<Sprite> sq){
        float[] data = new float[5];
        if(sq.size == 1){
            Gdx.app.log("SIZE", "1");
            Sprite colliding = sq.get(0);
            data[0] = colliding.getX();
            data[1] = colliding.getY();
            data[2] = colliding.getWidth();
            data[3] = colliding.getHeight();
            data[4] = 0;
        }else if(sq.size == 2){
            Gdx.app.log("SIZE", "2");
            Sprite colliding = sq.get(0);
            Sprite colliding2 = sq.get(1);
            if(colliding.getX() == colliding2.getX()){
                data[0] = colliding.getX();
                if(colliding.getY() > colliding2.getY()){
                    data[1] = colliding2.getY();
                }else{
                    data[1] = colliding.getY();
                }
                data[2] = colliding.getWidth();
                data[3] = colliding.getHeight()+colliding2.getHeight();
                data[4] = 0;
            }else if(colliding.getY() == colliding2.getY()){
                if(colliding.getX()<colliding2.getX()){
                    data[0] = colliding.getX();
                }else{
                    data[0] = colliding2.getX();
                }
                data[1] = colliding.getY();
                data[2] = colliding.getWidth()+colliding2.getWidth();
                data[3] = colliding.getHeight();
                data[4] = 0;
            }else{
                data[4] = 1;
            }
        }else if(sq.size == 3){
            data[4] = 1;
        }

        if(dirX == Directions.RIGHT){
            if(data[4] == 1){
                dirX = Directions.LEFT;
                if(dirY == Directions.UP){
                    dirY = Directions.DOWN;
                }else{
                    dirY = Directions.UP;
                }
            }else{
                if(X+W-data[0] == Y+H-data[1]){
                    if(dirY == Directions.UP){
                        dirY = Directions.DOWN;
                    }
                    dirX = Directions.LEFT;
                }else if(X+W-data[0] == data[1]+data[3]-Y){
                    if(dirY == Directions.DOWN){
                        dirY = Directions.UP;
                    }
                    dirX = Directions.LEFT;
                }else if(X+W <= data[0]+W){
                    dirX = Directions.LEFT;
                }else if(dirY == Directions.UP){
                    dirY = Directions.DOWN;
                }else{
                    dirY = Directions.UP;
                }
            }
        }else{
            if(data[4] == 1){
                dirX = Directions.RIGHT;
                if(dirY == Directions.UP){
                    dirY = Directions.DOWN;
                }else{
                    dirY = Directions.UP;
                }
            }else{
                if(data[0]+data[2]-X == (Y+H)-data[1]){
                    if(dirY == Directions.UP){
                        dirY = Directions.DOWN;
                    }
                    dirX = Directions.RIGHT;
                }else if(data[0]+data[2]-X == (data[1]+data[3])-Y){
                    if(dirY == Directions.DOWN){
                        dirY = Directions.UP;
                    }
                    dirX = Directions.RIGHT;
                }else if(X >= data[0]+data[2]-W){
                    dirX = Directions.RIGHT;
                }else if(dirY == Directions.UP){
                    dirY = Directions.DOWN;
                }else{
                    dirY = Directions.UP;
                }
            }
        }
        hasChangeDir = true;
    }

    public void setBall(){
        dirX = Directions.RIGHT;
        dirY = Directions.UP;
        vel = 290;
        onBase = true;
        hasChangeDir = false;
        isColliding = false;
    }

    public void setPos(float x, float y){
        X = x;
        Y = y;
        setPosition(x, y);
    }

    public void addPos(float x, float y){
        X += x;
        Y += y;
        setPosition(X, Y);
    }

    public void setNewX(float x){
        X = x;
    }

    public void setNewY(float y){
        Y = y;
    }

    public void setToOrigin(){
        X = origX;
        Y = origY;
        setPosition(X, Y);
    }

    public void reset(){
        setToOrigin();
        onBase = true;
        hasChangeDir = false;
        isColliding = false;
        dirX = Directions.RIGHT;
        dirY = Directions.UP;
    }
}
