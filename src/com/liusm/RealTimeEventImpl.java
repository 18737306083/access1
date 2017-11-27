package com.liusm;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created by liusmchs on 2017/2/1 0001.
 */
public class RealTimeEventImpl implements RealTimeEvent {
    @Override
    public void callback(String sn, String info) { 
        System.out.println(new Date()+" realtime event, sn:"+sn+", info: "+new String(info.getBytes(),Charset.forName("gbk")));
    }
}
