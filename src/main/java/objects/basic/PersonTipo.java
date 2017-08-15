package objects.basic;

import objects.TermoTopico;

public enum PersonTipo {
	
	PARCEIRO("Parceiro"),CLIENTE("Cliente"),FORNECEDOR("Fornecedor");
	
	public String descricao;
	
	PersonTipo(String description){
		this.descricao = description;
	}
	
	public PersonTipo locatePersonTipoByString(String string){
		for (PersonTipo personTipo : PersonTipo.values()) {
			if(string.equals(personTipo.descricao)){
				return personTipo;
			}
		}
		return null;
	}
	

}
