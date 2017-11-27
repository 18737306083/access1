package com.liusm;

import com.sun.jna.Callback;

interface ClientConnect extends Callback {
        void callback(String info);

    }