package com.example.statemachinedemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testStateMachine();
        //testHelloWorld();
    }

    void testHelloWorld(){
        Log.e(TAG, "testHelloWorld()....");
        HelloWorld helloWorld = HelloWorld.makeHelloWorld();
        helloWorld.sendMessage(helloWorld.obtainMessage());
    }

    void testStateMachine(){
        StateMachineTest sm = StateMachineTest.makeStateMachine();
        synchronized (sm){
            sm.sendMessage(Message.obtain(sm.getHandler(),StateMachineTest.CMD_1));
            sm.sendMessage(Message.obtain(sm.getHandler(), StateMachineTest.CMD_2));
            try {
                sm.wait();
            }catch (InterruptedException e){
                Log.e(TAG,"exception while waiting " + e.getMessage());
            }
        }
    }
}
