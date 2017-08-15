package objects.basic;

public enum ContatoTipo {
	
	CELULAR("Celular"),
	FIXO("Telefone Fixo"),
	EMAIL("E-mail")
	;
	
	public String descricao;
	
	ContatoTipo(String description){
		this.descricao = description;
	}
	
	public ContatoTipo locateContatoTipoByString(String string){
		for (ContatoTipo contatoTipo : ContatoTipo.values()) {
			if(string.equals(contatoTipo.descricao)){
				return contatoTipo;
			}
		}
		return null;
	}

}
