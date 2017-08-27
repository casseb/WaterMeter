package br.com.simnetwork.model.entity.basico.validacao;

public interface Validacao {
	
	public boolean eValido(Object object);
	
	public void setCondicao(Object condicao);
	
	public String getInvalidMessage();

}
