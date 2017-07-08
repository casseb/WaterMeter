package mvc;

import static spark.Spark.get;
import static spark.Spark.post;

import org.json.JSONObject;

import spark.Request;
import spark.Response;
import spark.Route;


public class REST {

	// ---------------------------------------------------------------------------
	

	private Model model;

	public REST(Model store) {
		this.model = store;
	}
	
	public void getTermos() {
		get("/gettermos", new Route() {
			@Override
			public Object handle(final Request request, final Response response) throws Exception {
				response.header("Access-Control-Allow-Origin", "*");
				
				return model.termos();
				

			}

		});
	}

	public void login() {
		post("/login", new Route() {
			@Override
			public Object handle(final Request request, final Response response) {
				response.header("Access-Control-Allow-Origin", "*");
				
				return model.login(new JSONObject(request.body()));
				
			}

		});
	}
	
	


}
