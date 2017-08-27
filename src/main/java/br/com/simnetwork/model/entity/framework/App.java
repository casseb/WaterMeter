package br.com.simnetwork.model.entity.framework;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
private static ClassPathXmlApplicationContext globalContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//private static ClassPathXmlApplicationContext dialogContext = new ClassPathXmlApplicationContext("dialogContext.xml");
    
    public static ClassPathXmlApplicationContext getCon() {
        return globalContext;
    }

	//public static ClassPathXmlApplicationContext getDialogContext() {
		//return dialogContext;
	//}
 
}
