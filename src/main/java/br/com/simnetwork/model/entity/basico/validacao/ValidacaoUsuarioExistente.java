package br.com.simnetwork.model.entity.basico.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.simnetwork.model.entity.basico.Utils;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.service.UsuarioService;

@Service("validacaoUsuarioExistente")
public class ValidacaoUsuarioExistente implements Validacao {

	String invalidMessage;
	Object condicao;
	
	@Autowired
	UsuarioService usuarioService;
	
	public Object getCondicao() {
		return condicao;
	}
	
	@Override
	public boolean eValido(Object object) {
		try {
			String palavra = (String) object;
			palavra = Utils.firstUpper(palavra);
			
			if(usuarioService.localizarUsuarioPorApelido(palavra)==null) {
				return true;
			}else {
				invalidMessage = "Usuário já esta sendo utilizado";
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
