package br.com.simnetwork.model.entity.basico.validacao;

import org.springframework.stereotype.Service;

@Service("validacaoMaxString")
public class ValidacaoMaxString implements Validacao {

	Object condicao;
	String invalidMessage;
	
	public Object getCondicao() {
		return condicao;
	}

	@Override
	public boolean eValido(Object object) {
		try {
			String palavra = (String) object;
			Integer max = Integer.parseInt((String) condicao);
			
			if(palavra.length()<=max) {
				return true;
			}else {
				invalidMessage = "O texto deve conter até "+max+" caracteres.";
				return false;
			}
		} catch (Exception e) {
			invalidMessage = "Erro ao tentar converter os dados.";
			return false;
		}
	}

	@Override
	public void setCondicao(Object condicao) {
		this.condicao = condicao;
	}

	@Override
	public String getInvalidMessage() {
		return this.invalidMessage;
	}
	
	

}
