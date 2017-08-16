package br.com.simnetwork.model.basic.person;

public enum Banco {
	
	BB("001 - Banco do Brasil"),
	SANTANDER("033 - Banco Santander"),
	INTERMEDIUM("077 - Banco Intermedium"),
	CEF("104 - Caixa Econômica Federal"),
	BRADESCO("237 - Banco Bradesco"),
	ITAU("341 - Itaú Unibanco")
	;
	
	public String descricao;
	
	Banco(String description){
		this.descricao = description;
	}
	
	public Banco locateBancoByString(String string){
		for (Banco banco : Banco.values()) {
			if(string.equals(banco.descricao)){
				return banco;
			}
		}
		return null;
	}


}
