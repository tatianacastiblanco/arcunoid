package com.cun.arcunoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by CUN on 27/12/2017.
 */

public class Spark extends Sprite{
    float a;
    boolean in = false;

    Spark(Sprite s){
        super(s);
    }

    public void init(float x, float y, float w, float h, float alpha){
        this.setPosition(x, y);
        this.setSize(w, h);
        a = alpha;
        this.setAlpha(alpha);
    }

    public void update(){
        if(!in){
            if(a>0.31)
                a -= 0.01;
            else
                in = true;
        }else{
            if(a<0.99)
                a += 0.01;
            else
                in = false;
        }
        this.setAlpha(a);
    }
}
