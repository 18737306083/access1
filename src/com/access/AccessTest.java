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
		//�������������������������������������������������������������� 
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//��������ĵ�ַ��Ҫ�� 
		Connection con=DriverManager.getConnection("jdbc:ucanaccess:"
		+ "//D:\\����\\�����ļ�\\201710201628����\\ZKTeco\\ZKTCAccess.mdb","",""); 
		Statement stmt=con.createStatement(); 
		//�������������������������������������������������������������� 
		//�������� 
		ResultSet rs=stmt.executeQuery("select * from UserInfo"); 
		while(rs.next()){ 
		System.out.println(rs.getString("sex")); 
		} 
		//�������������������������������������������������������������� 
		//������� 
		/*if(stmt.executeUpdate("insert into test(name,age) values(�����塯,20)")>0){ 
		System.out.println("��ӳɹ�!!!"); 
		}else{ 
		System.out.println("���ʧ��!!!"); 
		}*/ 
		//�������������������������������������������������������������� 
		//����ģ�����ɾ���ġ��飩������ MySQL Oracle �Ļ���һ�� 
		//�����Լ����� 
		} 

}
