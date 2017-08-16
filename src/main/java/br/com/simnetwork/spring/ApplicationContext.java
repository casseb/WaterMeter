package br.com.simnetwork.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContext {
	
private static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    
    public static ClassPathXmlApplicationContext getContext() {
        return context;
    }

}
