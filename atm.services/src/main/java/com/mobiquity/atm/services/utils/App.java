package com.mobiquity.atm.services.utils;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class App {
	public static void main(String[] args) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(PropertyUtils.class);
		System.out.println("URL - " );
		System.out.println(context.getBean(PropertyUtils.class).getProperty((ATMServiceConstants.ATM_LIST_END_POINT_URL)));
	    System.out.println(context.getBean(PropertyUtils.class).getProperty((ATMServiceConstants.SERVER_PORT)));
	}
}
