package br.com.simnetwork.view;

import static spark.Spark.port;
import static spark.Spark.post;

import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.entity.framework.Configuracao;

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
	
	
	public static void carregandoMapeamentoSpring() {
		System.out.println("Iniciando Mapeamento do Spring\n\n\n");
	
		App.getCon();
		
		
		System.out.println("\n\n\nMapeamento Realizado com Sucesso\n");
	}
	
}
