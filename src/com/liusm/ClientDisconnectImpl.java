package com.liusm;

import java.util.Date;

/**
 * Created by liusmchs on 2017/2/1 0001.
 */
public class ClientDisconnectImpl implements ClientDisconnect {
    @Override
    public void callback(String info) {
        System.out.println(new Date()+" client disconnect info: "+info);
    }

}
