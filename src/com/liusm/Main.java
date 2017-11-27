package com.liusm;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;

import java.io.File;
import java.util.Date;


public class Main {


    public interface ServiceCallBack extends Library{
        String sep = File.separator;
        File file=new File("");
        String str=file.getAbsolutePath().split("dlldemo")[0];
        
        
//        dll库文件路径D:\工作\工作软件\Demo_x64dcpushSDK
        ServiceCallBack INSTANCE = (ServiceCallBack) Native.loadLibrary("D:\\工作\\工作软件\\SDK_x641\\dcpushSDK",ServiceCallBack.class);

        boolean SEC_PUSH_SetServiceCallBack(ClientConnect  cc, ClientDisconnect cd, RealTimeEvent rte, WriteDevCallBack wdc, ReadDevCallBack rdc);
        int SEC_PUSH_StartServer(String ipaddr, String config);
        int SEC_PUSH_WriteTableData(String snList, int cmd, String data, int len);
        int SEC_PUSH_ReadTableData(String snList, int cmd, String data, int len);
        DeviceEvent SEC_PUSH_PollEvent();
    }

    //以下是5个定义需要注册的回调函数的方法




    public static void main(String[] args) {
    	File file=new File("");
          String str=file.getAbsolutePath().split("dlldemo")[0];
    	ClientConnect cc = new ClientConnectImpl();

        ClientDisconnect cdn = new ClientDisconnectImpl();

        RealTimeEvent rte = new RealTimeEventImpl();

        WriteDevCallBack wdc = new WriteDevCallBackImpl();

        ReadDevCallBack rdc = new ReadDevCallBackImpl();

        //首先启动SDK服务，参数1为服务器监听的IP地址，参数2为配置文件的路径（例子为绝对路径），参数为空则不需要配置文件
 int result = ServiceCallBack.INSTANCE.SEC_PUSH_StartServer("192.168.1.215:8068","D:\\工作\\工作软件\\SDK_x641\\ServiceConfig.cf");
        //设置回调函数，将函数方法注册进SDK服务，由SDK调度方法的执行时间。
        boolean res = ServiceCallBack.INSTANCE.SEC_PUSH_SetServiceCallBack(cc, cdn, rte, wdc, rdc);

        //主程序需要进入无限循环，保证SDK服务线程不退出
     System.out.println(new Date()+" result: "+result);
        while(true){
            try {
//                DeviceEvent rs = (DeviceEvent) ServiceCallBack.INSTANCE.SEC_PUSH_PollEvent();
//                if(rs!=null){
//                    System.out.println(rs);
//                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

