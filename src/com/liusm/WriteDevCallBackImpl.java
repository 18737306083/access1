package com.liusm;

/**
 * Created by liusmchs on 2017/2/1 0001.
 */
public class WriteDevCallBackImpl implements WriteDevCallBack {
    @Override
    public void callback(String sn, int cmd, String data, int len) {
        System.out.printf("writeDevCallBack info, sn: %s, cmd: %d, data: %s, len: %s \n",sn, cmd, data, len);
    }
}
