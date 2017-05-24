package com.example.statemachinedemo;

import android.os.Message;
import android.util.Log;

import com.android.internal.util.State;
import com.android.internal.util.StateMachine;

/**
 * Description:
 * Author: qiubing
 * Date: 2017-05-24 17:23
 */
public class StateMachineTest extends StateMachine {
    private static final String TAG = "StateMachineTest";
    public static final int CMD_1 = 1;
    public static final int CMD_2 = 2;
    public static final int CMD_3 = 3;
    public static final int CMD_4 = 4;
    public static final int CMD_5 = 5;

    public static StateMachineTest makeStateMachine(){
        Log.e(TAG, "makeStateMachine()...");
        StateMachineTest machine = new StateMachineTest("StateMachineTest");
        machine.start();
        return machine;
    }


    protected StateMachineTest(String name) {
        super(name);
        Log.e(TAG, "StateMachineTest()...");
        addState(mP1);
        addState(mS1, mP1);
        addState(mS2, mP1);
        addState(mP2);

        setInitialState(mS1);
    }

    class P1 extends State{
        @Override
        public void enter() {
            Log.e(TAG, "P1 enter()");
        }

        @Override
        public boolean processMessage(Message message) {
            Log.e(TAG,"P1 processMessage()...message.what = " + message.what);
            boolean retVal;
            switch (message.what){
                case CMD_2:
                    sendMessage(obtainMessage(CMD_3));
                    deferMessage(message);
                    transitionTo(mS2);
                    retVal = HANDLED;
                    break;
                default:
                    retVal = NOT_HANDLED;
                    break;

            }
            return retVal;
        }

        @Override
        public void exit() {
            Log.e(TAG, "P1 exit()");
        }
    }

    class P2 extends State{
        @Override
        public void enter() {
            Log.e(TAG, "P2 enter()");
            sendMessage(obtainMessage(CMD_5));
        }

        @Override
        public boolean processMessage(Message message) {
            Log.e(TAG,"P2 processMessage()...message.what = " + message.what);
            switch (message.what){
                case CMD_3:
                    break;
                case CMD_4:
                    break;
                case CMD_5:
                    transitionToHaltingState();
                    break;
            }
            return HANDLED;
        }

        @Override
        public void exit() {
            Log.e(TAG, "P2 exit()");
        }
    }

    class S1 extends State{
        @Override
        public void enter() {
            Log.e(TAG, "S1 enter()");
        }

        @Override
        public boolean processMessage(Message message) {
            Log.e(TAG,"S1 processMessage()...message.what = " + message.what);
            boolean retVal;
            switch (message.what){
                case CMD_1:
                    transitionTo(mS1);
                    retVal = HANDLED;
                    break;
                default:
                    retVal = NOT_HANDLED;
                    break;
            }
            return retVal;
        }

        @Override
        public void exit() {
            Log.e(TAG, "S1 exit()");
        }
    }

    class S2 extends State{
        @Override
        public void enter() {
            Log.e(TAG, "S2 enter()");
        }

        @Override
        public boolean processMessage(Message message) {
            Log.e(TAG,"S2 processMessage()...message.what = " + message.what);
            boolean retVal;
            switch (message.what){
                case CMD_2:
                    sendMessage(obtainMessage(CMD_4));
                    retVal = HANDLED;
                    break;
                case CMD_3:
                    deferMessage(message);
                    transitionTo(mP2);
                    retVal = HANDLED;
                    break;
                default:
                    retVal = NOT_HANDLED;
                    break;
            }
            return retVal;
        }


        @Override
        public void exit() {
            Log.e(TAG, "S2 exit()");
        }
    }

    @Override
    protected void onHalting() {
        Log.e(TAG, "onHalting()....");
        synchronized (this){
            this.notifyAll();
        }
    }

    P1 mP1 = new P1();
    P2 mP2 = new P2();
    S1 mS1 = new S1();
    S2 mS2 = new S2();
}
