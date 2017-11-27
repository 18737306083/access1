package com.liusm;

import com.sun.jna.Callback;

interface RealTimeEvent extends Callback {
        void callback(String sn, String info);
    }