package br.com.simnetwork.old;

public enum Banco {
	
	BB("Banco do Brasil","001"),
	SANTANDER("Banco Santander","003"),
	INTERMEDIUM("Banco Intermedium", "077"),
	CEF("Caixa Econômica Federal","104"),
	BRADESCO("Banco Bradesco","237"),
	ITAU("Itaú Unibanco","341")
	;
	
	public String descricao;
	public String codigo;
	
	Banco(String description, String codigo){
		this.descricao = description;
		this.codigo = codigo;
	}
	
	public Banco locateBancoByString(String string){
		for (Banco banco : Banco.values()) {
			if(string.equals(banco.descricao)){
				return banco;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return codigo;
	}


}
