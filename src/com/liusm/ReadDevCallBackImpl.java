package com.liusm;

/**
 * Created by liusmchs on 2017/2/1 0001.
 */
public class ReadDevCallBackImpl implements ReadDevCallBack {
    @Override
    public void callback(String sn, int cmd, String data, int len) {
        System.out.printf("sn: %s, cmd: %d, data: %s, len: %s \n",sn, cmd, data, len);
    }
}
