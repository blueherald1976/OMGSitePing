package com.slrclub.free;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.slrclub.itfext.HelloItf;

@Component
public class ComponentTest {

	@Autowired
	HelloItf hello;
	
	public void hello() {
		hello.sayItfHello();
		System.out.println("ComponentTest >> hello");
	}
}
