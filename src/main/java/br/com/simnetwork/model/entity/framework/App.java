package br.com.simnetwork.model.entity.framework;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
private static ClassPathXmlApplicationContext globalContext = new ClassPathXmlApplicationContext("applicationContext.xml");
private static ClassPathXmlApplicationContext staticDialogContext = new ClassPathXmlApplicationContext("staticDialogContext.xml");
private static ClassPathXmlApplicationContext dianamicDialogContext = new ClassPathXmlApplicationContext("dianamicDialogContext.xml");

    
    public static ClassPathXmlApplicationContext getCon() {
    	return globalContext;
    }


	public static ClassPathXmlApplicationContext getStaticDialogContext() {
		return staticDialogContext;
	}


	public static ClassPathXmlApplicationContext getDianamicDialogContext() {
		return dianamicDialogContext;
	}
    
    

	
}
