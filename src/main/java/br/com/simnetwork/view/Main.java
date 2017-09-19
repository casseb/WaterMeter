package br.com.simnetwork.view;

import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.service.UsuarioService;

public class Main {
	
	public static void main(String[] args) {
		
		Start.startEnvironment();
		Start.carregandoMapeamentoSpring();
		System.out.println("Concluido o carregamento da aplicação");
		
		UsuarioService usuarioService = App.getCon().getBean("usuarioService",UsuarioService.class);
		
		usuarioService.salvar("Casseb", "123");
    }
	
}
