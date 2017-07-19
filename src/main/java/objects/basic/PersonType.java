package objects.basic;

public enum PersonType {
	
	PARCEIRO("Parceiro"),CLIENTE("Cliente"),FORNECEDOR("Fornecedor");
	
	public String description;
	
	PersonType(String description){
		this.description = description;
	}
	
	

}
