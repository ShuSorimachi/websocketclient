package com.example.kou21.websocketclient;

public class PosData {
    private int posNum;
    private float posX1;
    private float posY1;
    private float posX2;
    private float posY2;

    public PosData(int posNum,float posX1,float posY1,float posX2, float posY2){
        this.posNum = posNum;
        this.posX1 = posX1;
        this.posY1 = posY1;
        this.posX2 = posX2;
        this.posY2 = posY2;
    }
}
