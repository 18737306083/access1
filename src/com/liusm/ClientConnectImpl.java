package com.liusm;

import java.util.Date;

/**
 * Created by liusmchs on 2017/2/1 0001.
 */
public class ClientConnectImpl implements ClientConnect{
    @Override
    public void callback(String info) {
        System.out.println(new Date()+" info: "+info);
        int res = Main.ServiceCallBack.INSTANCE.SEC_PUSH_WriteTableData("HNACB2D170303L000462", 1, "{\"TabCommand\":[{\"DoorControl\":\"1,5\"}]}", 40);
System.out.println("write cmd result "+res);
      res = Main.ServiceCallBack.INSTANCE.SEC_PUSH_ReadTableData("HNACB2D170303L000462", 1, "{\"TabCommand\":[{\"AppVersion\":\"\"}]}", 32);
        System.out.println("read data result "+res);
    }
}
