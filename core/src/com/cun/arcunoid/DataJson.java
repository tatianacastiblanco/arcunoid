package com.cun.arcunoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

/**
 * Created by CUN on 22/01/2018.
 */

public class DataJson{
    private String datoUs = "Usuario";
    private Array<Integer> datoSc = new Array<Integer>(10);
    private Array<String> datoTm = new Array<String>(10);

    public String getDataUs(){
        return datoUs;
    }

    public Array<Integer> getDataSc(){
        return  datoSc;
    }

    public Integer getBestSc(){
        return  datoSc.get(0);
    }

    public Array<String> getDataTm(){
        return  datoTm;
    }

    public String getBestTm(){
        return  datoTm.get(0);
    }

    public void setDataUs(String val){
        datoUs = val;
    }

    public void setDataSc(){
        for(int i = 0; i < 10; i++)
            datoSc.add(0);
    }

    public void setDataTm(){
        for(int i = 0; i < 10; i++)
            datoTm.add("--:--:---");
    }

    public boolean addValue(int val, String time){
        boolean added = false;
        Array<Integer> oldData = new Array<Integer>(10);
        Array<String> oldTime = new Array<String>(10);
        for(int i = 0; i < 10; i++){
            oldData.add(datoSc.get(i));
            oldTime.add(datoTm.get(i));
        }
        for(int i = 0; i < 10; i++){
            int posToChange = -1;
            Array<Integer> samePoints = new Array<Integer>();
            if(val == datoSc.get(i)){
                posToChange = i;
                String[] newTime = time.split(":");
                int[][] oldTimes = new int[10][3];
                int cont = 0;
                for(int newi = i; newi < 10; newi++){
                    if(val == datoSc.get(newi)){
                        samePoints.add(newi);
                        String[] currenTime = datoTm.get(newi).split(":");
                        for(int t = 0; t < 3; t++){
                            oldTimes[cont][t] = Integer.valueOf(currenTime[t]);
                        }
                        cont++;
                    }
                }
                int[] newTimes = new int[3];
                for(int t = 0; t < 3; t++){
                    newTimes[t] = Integer.valueOf(newTime[t]);
                }
                boolean hasChange = false;
                for(int k = 0; k < samePoints.size; k++){
                    if(!hasChange){
                        if(newTimes[0]==oldTimes[k][0]){
                            if(newTimes[1]==oldTimes[k][1]){
                                if(newTimes[2]<=oldTimes[k][2]){
                                    posToChange = i+k;
                                    hasChange = true;
                                    break;
                                }
                            }else if(newTimes[1]<oldTimes[k][1]){
                                posToChange = i+k;
                                hasChange = true;
                                break;
                            }
                        }else if(newTimes[0]<oldTimes[k][0]){
                            posToChange = i+k;
                            hasChange = true;
                            break;
                        }
                    }
                }
                if(!hasChange){
                    posToChange = i+samePoints.size;
                }
            }else if(val > datoSc.get(i)){
                posToChange = i;
            }
            if(posToChange != -1){
                if(posToChange < 9){
                    for(int j = posToChange+1; j < 10; j++){
                        datoSc.set(j, oldData.get(j-1));
                        datoTm.set(j, oldTime.get(j-1));
                    }
                }
                if(posToChange>=10)
                    posToChange = 9;
                datoSc.set(posToChange, val);
                datoTm.set(posToChange, time);
                added = true;
                break;
            }
        }
        return added;
    }
}
