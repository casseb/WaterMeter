package br.com.simnetwork.view;

import static spark.Spark.port;
import static spark.Spark.post;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.simnetwork.controller.TelegramResponse;
import br.com.simnetwork.model.entity.basico.Utils;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.service.RotaService;

public class Start {
	
	public static void startEnvironment() {
		System.out.println("Iniciando Ambiente");
		
		// Get port config of heroku on environment variable
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }
        port(port);
        
        System.out.println("Ambiente Iniciado com Sucesso");
	}
	
	public static void startTelegramMethods() {
		System.out.println("Iniciando Métodos do Telegram");
		
		TelegramResponse api = new TelegramResponse();
        
        post("/readMessages", (req, res) -> {
            api.readMessage(req.bodyAsBytes());
            return "Success";
       });
       
       System.out.println("Métodos do Telegram Iniciados com Sucesso");
	}
	
	public static void carregandoMapeamentoSpring() {
		System.out.println("Iniciando Mapeamento do Spring");
	
		App.getCon();
		
		System.out.println("Mapeamento Iniciado com Sucesso");
	}
	
	public static void persistirRotas() {
		System.out.println("Iniciando Persistencias das rotas");
		List<String> rotaBeans = new LinkedList<>();
		for (String beanString : App.getCon().getBeanDefinitionNames()) {
			if(beanString.contains("|R|")) {
				rotaBeans.add(beanString);
			}
		}
		
		RotaService rotaService = App.getCon().getBean("rotaService",RotaService.class);
		
		for (String rotaBean : rotaBeans) {
			List<String> parts = Utils.extractLetterFor(rotaBean, "|");
			
			rotaService.salvarRota(parts.get(2), parts.get(3), parts.get(4), parts.get(5));
		}
		
		System.out.println("Persistencia Iniciado com Sucesso");
		
		
		
	}
}
