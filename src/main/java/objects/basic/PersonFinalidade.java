package objects.basic;

public enum PersonFinalidade {
	
	FISICO("Físico"),JURIDICO("Jurídico");
	
	public String descricao;
	
	PersonFinalidade(String description){
		this.descricao = description;
	}
	
	public PersonFinalidade locatePersonFinalidadeByString(String string){
		for (PersonFinalidade personFinalidade : PersonFinalidade.values()) {
			if(string.equals(personFinalidade.descricao)){
				return personFinalidade;
			}
		}
		return null;
	}

}
