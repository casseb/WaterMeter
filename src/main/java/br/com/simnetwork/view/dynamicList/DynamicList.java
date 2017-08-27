package br.com.simnetwork.view.dynamicList;

import java.util.List;

public interface DynamicList {
	
	public void prepareList(Object... object);
	
	public List<String> getList();

}
