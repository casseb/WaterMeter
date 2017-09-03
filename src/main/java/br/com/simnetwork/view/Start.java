package br.com.simnetwork.view;

import static spark.Spark.port;
import static spark.Spark.post;

import java.util.LinkedList;
import java.util.List;

import br.com.simnetwork.controller.TelegramResponse;
import br.com.simnetwork.model.entity.basico.Utils;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.entity.framework.Configuracao;
import br.com.simnetwork.model.service.RotaService;

public class Start {
	
	public static void startEnvironment() {
		System.out.println("Iniciando Ambiente\n\n\n");
		
		// Get port config of heroku on environment variable
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }
        port(port);
        
        System.out.println("\n\n\nAmbiente Iniciado com Sucesso\n");
	}
	
	public static void startTelegramMethods() {
		System.out.println("Iniciando Métodos do Telegram\n\n\n");
		
		TelegramResponse api = new TelegramResponse();
        
        post("/readMessages", (req, res) -> {
            api.readMessage(req.bodyAsBytes());
            return "Success";
       });
       
       System.out.println("\n\n\nMétodos do Telegram Iniciados com Sucesso\n");
	}
	
	public static void carregandoMapeamentoSpring() {
		System.out.println("Iniciando Mapeamento do Spring\n\n\n");
	
		App.getCon();
		App.getStaticDialogContext();
		App.getDianamicDialogContext();
		
		
		System.out.println("\n\n\nMapeamento Realizado com Sucesso\n");
	}
	
	public static void persistirRotas() {
		System.out.println("Iniciando Sincronização de rotas\n\n\n");
		
		RotaService rotaService = App.getCon().getBean("rotaService",RotaService.class);
		rotaService.sincronizarRotas();
		
		System.out.println("\n\n\nSincornização realizada com Sucesso\n");
		
	}
	
	public static void conferindoConfiguracoes() {
		
		System.out.println("Mostrando Configurações\n\n\n");
		
		Configuracao config = App.getCon().getBean("config",Configuracao.class);
		
		if(config.isDebug()) {
			System.out.println("Modo debug ativado\n");
		}else {
			System.out.println("Modo debug desativado\n");
		}
		
		System.out.println("Finalizado Configurações\n");
			
	}
}
