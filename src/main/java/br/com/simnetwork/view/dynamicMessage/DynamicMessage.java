package br.com.simnetwork.view.dynamicMessage;

import java.util.List;

public interface DynamicMessage {
	
	public void prepareMessage(Object... object);
	
	public String getMessage();

}
