package br.com.simnetwork.controller;

import static spark.Spark.get;
import static spark.Spark.post;

import org.json.JSONObject;

import br.com.simnetwork.model.entity.framework.App;
import spark.Request;
import spark.Response;
import spark.Route;


public class REST {

	// ---------------------------------------------------------------------------
	

	//private Model model = (Model) ApplicationContext.getContext().getBean("model");

	public REST() {

	}
	
	public void getTermos() {
		get("/gettermos", new Route() {
			@Override
			public Object handle(final Request request, final Response response) throws Exception {
				response.header("Access-Control-Allow-Origin", "*");
				
				return null;
				

			}

		});
	}
	
	


}
