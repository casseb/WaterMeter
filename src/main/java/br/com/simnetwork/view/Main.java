package br.com.simnetwork.view;

import static spark.Spark.port;
import static spark.Spark.post;

import br.com.simnetwork.controller.TelegramResponse;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.service.Bot;
import br.com.simnetwork.model.service.UsuarioService;

public class Main {
	
	public static void main(String[] args) {
		
		Start.startEnvironment();
		Start.startTelegramMethods();
		Start.carregandoMapeamentoSpring();
		Start.persistirRotas();
		System.out.println("Concluido o carregamento da aplicação");
    }
	
}
