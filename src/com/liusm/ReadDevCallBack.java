package com.liusm;

import com.sun.jna.Callback;

interface ReadDevCallBack extends Callback {
        void callback(String sn, int cmd, String data, int len);
    }