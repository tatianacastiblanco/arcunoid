package com.cun.arcunoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;

/**
 * Created by CUN on 4/01/2018.
 */

public class Power extends Sprite{
    enum Powers{
        R, CR, CO, T, A
    }
    Powers power;
    boolean isActive = true;
    float timePower = 0;
    boolean onPower = false;
    boolean endTime = false;

    Power(Sprite t){
        super(t);
    }

    public void update(float delta){
        if(onPower){
            if(timePower <= 4){
                timePower += delta;
            }else{
                timePower = 0;
                onPower = false;
                endTime = true;
            }
        }else{
            float y = this.getY();
            if(y > 0){
                float newY = y-130*delta;
                this.setY(newY);
            }else{
                isActive = false;
                onPower = false;
                endTime = false;
            }
        }
    }

    public void setPower(float x, float y){
        this.setPosition(x, y);
    }

    public Powers getPower(){
        return power;
    }

    public void setActive(boolean a){
        isActive = a;
    }

    public boolean getActive(){
        return isActive;
    }

    public boolean getEndtime(){
        return endTime;
    }

    public void setEndTime(boolean t){
        endTime = t;
    }

    public void reset(){
        timePower = 0;
        isActive = true;
        onPower = false;
        endTime = false;
    }
}
