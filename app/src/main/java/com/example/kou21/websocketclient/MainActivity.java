package com.example.warp.websocketclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import org.java_websocket.*;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.google.gson.*;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    private WebSocketClient mClient;
    URI uri;
    Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            uri = new URI("ws://54.95.63.119:8080");
            mClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.v("connection","OK");
                  //  mClient.send("Hello");
                }

                @Override
                public void onMessage(String message) {
                    Log.v("Recieved",message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.v("Recieved","dissconnect");
                }

                @Override
                public void onError(Exception ex) {

                }
            };
            mClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public  boolean onTouchEvent(MotionEvent event){
        float[] numX = new float[2];
        float[] numY = new float[2];

        int count = event.getPointerCount();
        if(count > 2) {
            count = 2;
        }
        for (int i = 0; i < count; i++) {
            int pid = event.getPointerId(i);
            int id = event.findPointerIndex(pid);
            Log.v("pos", count + "" + event.getX(id) + ":" + event.getY(id));
            numX[i] = event.getX(id);
            numY[i] = event.getY(id);
            //PosData posData = new PosData(i,event.getX(id),event.getY(id));
        }

        PosData posData;
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            posData = new PosData(count, numX[0], numY[0], numX[1], numY[1]);
            mClient.send(gson.toJson(posData));
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            posData = new PosData(count, numX[0], numY[0], numX[1], numY[1]);
            mClient.send(gson.toJson(posData));
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            posData = new PosData(0, 0, 0, 0, 0);
            mClient.send(gson.toJson(posData));
        }



        return true;
    }
}
