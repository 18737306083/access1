package com.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/** 
 * @author 
 * @version 
 *  
 */
public class AccessTest {
	public static void main(String[] args) throws Exception { 
		//——————————————————————————————— 
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//这个驱动的地址不要改 
		Connection con=DriverManager.getConnection("jdbc:ucanaccess:"
		+ "//D:\\工作\\接收文件\\201710201628管理\\ZKTeco\\ZKTCAccess.mdb","",""); 
		Statement stmt=con.createStatement(); 
		//——————————————————————————————— 
		//查找数据 
		ResultSet rs=stmt.executeQuery("select * from UserInfo"); 
		while(rs.next()){ 
		System.out.println(rs.getString("sex")); 
		} 
		//——————————————————————————————— 
		//添加数据 
		/*if(stmt.executeUpdate("insert into test(name,age) values(‘王五’,20)")>0){ 
		System.out.println("添加成功!!!"); 
		}else{ 
		System.out.println("添加失败!!!"); 
		}*/ 
		//——————————————————————————————— 
		//其余的（增、删、改、查）操作和 MySQL Oracle 的基本一样 
		//可以自己试验 
		} 

}
