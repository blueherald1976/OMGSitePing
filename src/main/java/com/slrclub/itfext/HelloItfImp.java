package com.slrclub.itfext;

import org.springframework.stereotype.Component;

@Component
public class HelloItfImp implements HelloItf {

	@Override
	public void sayItfHello() {
		// TODO Auto-generated method stub
		System.out.println("HelloItfImp >> itfSayHello");
	}

}
