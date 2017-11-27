package com.liusm;

import com.sun.jna.Structure;

/**
 * Created by liusmchs on 2017/1/24 0024.
 */
public  class DeviceEvent extends Structure{

        public String event;
        public String sn;
        public int cmd;
        public String data;

    public static class ByReference extends DeviceEvent implements Structure.ByReference { }
    public static class ByValue extends DeviceEvent implements Structure.ByValue { }

        @Override
        public String toString(){
            return "event: "+event+", sn: "+sn+", cmd: "+cmd+", data: "+data;
        }
}
