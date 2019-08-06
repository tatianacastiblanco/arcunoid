package com.cun.arcunoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by CUN on 11/01/2018.
 */

public class MovingAsteroid extends Sprite{
    int dir = 0;
    float time = 0;
    float vel;

    MovingAsteroid(Sprite s, float v){
        super(s);
        setPosition(-1000, -1000);
        vel = v;
    }

    public void update(float delta){
        float x = getX();
        float y = getY();
        float w = getWidth();
        float h = getHeight();
        if(x+w<0 || x>1080 || y+h<0 || y>1920){
            if(time <= 0){
                float rand = MathUtils.random(0, 4);
                float rand2;
                if(rand == 0){
                    rand2 = MathUtils.random(300, 1900);
                    setPosition(-w, rand2);
                    dir = 0;
                    setRotation(0);
                }else if(rand == 1){
                    rand2 = MathUtils.random(300, 1900);
                    setPosition(1080, rand2);
                    dir = 1;
                    setRotation(270);
                }else if(rand == 2){
                    rand2 = MathUtils.random(-w, 1081);
                    setPosition(rand2, -h);
                    if(rand2 >540){
                        dir = 1;
                        setRotation(270);
                    }else{
                        dir = 0;
                        setRotation(0);
                    }
                }else{
                    rand2 = MathUtils.random(-w, 1081);
                    setPosition(rand2, 1921);
                    if(rand2 >540){
                        dir = 1;
                        setRotation(270);
                    }else{
                        dir = 0;
                        setRotation(0);
                    }
                }
                time = 0.5f;
            }else{
                time -= delta;
            }
        }else{
            y -= (vel*delta);
            if(dir == 0){
                x += (vel*delta);
            }else{
                x -= (vel*delta);
            }
            setPosition(x, y);
        }
    }
}
