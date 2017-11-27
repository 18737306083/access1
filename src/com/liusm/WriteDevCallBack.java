package com.liusm;

import com.sun.jna.Callback;

interface WriteDevCallBack extends Callback {
        void callback(String sn, int cmd, String data, int len);
    }