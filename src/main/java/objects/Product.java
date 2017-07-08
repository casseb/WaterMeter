package objects;

import java.util.List;

public class Product {
	
	private int id;
	private String desc;
	private List<Service> services;
	
	public float getPrice(){
		return 10;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<Service> getServices() {
		return services;
	}
	public void setServices(List<Service> services) {
		this.services = services;
	}
	
	

}
