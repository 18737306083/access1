package com.liusm;

import com.sun.jna.Callback;

interface ClientDisconnect extends Callback {
        void callback(String info);
    }