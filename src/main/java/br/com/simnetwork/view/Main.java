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
		
		// Get port config of heroku on environment variable
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }
        port(port);
		
        
        TelegramResponse api = new TelegramResponse();
        
        post("/readMessages", (req, res) -> {
            api.readMessage(req.bodyAsBytes());
            return "Success";
       });
        
        	
    }
	
}
