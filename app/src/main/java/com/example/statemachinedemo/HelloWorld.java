package com.example.statemachinedemo;

import android.os.Message;
import android.util.Log;

import com.android.internal.util.State;
import com.android.internal.util.StateMachine;

/**
 * Description:
 * Author: qiubing
 * Date: 2017-05-24 17:10
 */
public class HelloWorld extends StateMachine {
    private static final String TAG = "HelloWorld";
    private State1 mState1 = new State1();
    protected HelloWorld(String name) {
        super(name);
        addState(mState1);
        setInitialState(mState1);
    }

    public static HelloWorld makeHelloWorld(){
        HelloWorld helloWorld = new HelloWorld("hw");
        helloWorld.start();
        return helloWorld;
    }

    class State1 extends State{
        @Override
        public void enter() {
            super.enter();
            Log.e(TAG,"enter()...");
        }

        @Override
        public boolean processMessage(Message message) {
            Log.e(TAG,"processMessage()...");
            Log.e(TAG,"Hello world!!!");
            return HANDLED;
        }

        @Override
        public void exit() {
            super.exit();
            Log.e(TAG, "exit()...");
        }
    }
}
