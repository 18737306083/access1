package com.honoo.sms.test;
public class HelloHxrt {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello,华兴软通短信系统!");
		HttpRuquest.sendSms(2);
		HttpRuquest.getBalance(2);
	}

}
