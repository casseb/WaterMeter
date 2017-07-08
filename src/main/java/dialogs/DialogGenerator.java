package dialogs;

import org.json.JSONObject;

import com.pengrad.telegrambot.*;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.SendMessage;

import mvc.Model;
import objects.Person;
import objects.Route;

public class DialogGenerator {
	
	private TelegramBot bot;
	private JSONObject telegramMessage;
	private Model model;
	private String admin;
	private String message = " ";
	private Person person;

	public DialogGenerator(JSONObject telegramMessage, Model model, TelegramBot bot, String admin) {
		super();
		this.telegramMessage = telegramMessage;
		this.model = model;
		this.bot = bot;
		this.admin = admin;
	}
	
	public DialogGenerator(String message, Model model, TelegramBot bot, String admin, Person person) {
		super();
		this.message = message;
		this.model = model;
		this.bot = bot;
		this.admin = admin;
		this.person = person;
	}
	
	public Dialog create(String routeGroup){
		
		if(!person.isActive()){
			bot.execute(new SendMessage(person.getIdTelegram(),"Usuário Bloqueado"));
			return null;
		}
		
		Route route = model.locateRoute(routeGroup+" - "+message);
		if(route==null && !message.equals("Voltar")){
			bot.execute(new SendMessage(person.getIdTelegram(), "Esta rota não existe!"));
			return new DialogCommands(bot, person, model.locateRoute("Navegação - Comandos"), model, message);
		}else{
			if(!(model.havePermission(route, person))){
				bot.execute(new SendMessage(person.getIdTelegram(), "Você não possui Permissão para esta rota!"));
				return null;
			}else{	
				String completeRoute = routeGroup + " - " + message;
				if((completeRoute).equals("Informações - Termos")){
					return new DialogTermos(bot,person,route,model,message);
				}
				if((completeRoute).equals("Meus Dados - Editar Login")){
					return new DialogEditLogin(bot,person,route,model,message);
				}
				if((completeRoute).equals("Administrativo - Dar Permissão")){
					return new DialogGrandPermission(bot,person,route,model,message);
				}
				if((completeRoute).equals("Administrativo - Remover Permissão")){
					return new DialogRevokePermission(bot,person,route,model,message);
				}
				if((completeRoute).equals("Navegação - Comandos")){
					return new DialogCommands(bot,person,route,model,message);
				}
				if((completeRoute).equals("Administrativo - Dados dos Usuários")){
					return new DialogShowAllUsersInfo(bot,person,route,model,message);
				}
				if((completeRoute).equals("Meus Dados - Dados Login")){
					return new DialogShowUserInfo(bot,person,route,model,message);
				}
				if((completeRoute).equals("Projeto - Adicionar")){
					return new DialogAddProject(bot,person,route,model,message);
				}
				if((completeRoute).equals("Administrativo - Remover Usuário")){
					return new DialogRemoveUser(bot,person,route,model,message);
				}
				if((completeRoute).equals("Projeto - Excluir")){
					return new DialogRemoveProject(bot,person,route,model,message);
				}
				if((completeRoute).equals("Projeto - Detalhes")){
					return new DialogShowProject(bot,person,route,model,message);
				}
				if((completeRoute).equals("Projeto - Editar")){
					return new DialogEditProject(bot,person,route,model,message);
				}
				if((completeRoute).equals("Clientes - Adicionar")){
					return new DialogAddClient(bot,person,route,model,message);
				}
				if((completeRoute).equals("Navegação - Testes")){
					return new DialogTest(bot,person,route,model,message);
				}
				if((completeRoute).equals("Administrativo - Ativar Usuário")){
					return new DialogActiveUser(bot,person,route,model,message);
				}
				if((completeRoute).equals("Administrativo - Desativar Usuário")){
					return new DialogDisableUser(bot,person,route,model,message);
				}
				
				return null;
			}
		}
	}
	
	public Dialog create(){
		
		if(telegramMessage.getJSONObject("message").has("text")){
			this.message = telegramMessage.getJSONObject("message").getString("text");
		}
		
		person = model.locateTelegramUser(Integer.toString(telegramMessage.getJSONObject("message").getJSONObject("chat").getInt("id")));
		
		if(person==null){
			person = model.addPersonByTelegram(Integer.toString(telegramMessage.getJSONObject("message").getJSONObject("chat").getInt("id")));
			return new DialogEditLogin(bot,person,model.locateRoute("Meus Dados - Editar Login"),model,message);
		}
		
		if(!person.isActive()){
			bot.execute(new SendMessage(person.getIdTelegram(),"Usuário Bloqueado"));
			return null;
		}
		
		
		if(message.equals("Menu")){
			bot.execute(new SendMessage(person.getIdTelegram(), "Você retornou ao Menu Principal"));
			return new DialogCommands(bot, person, model.locateRoute("Navegação - Comandos"), model, message);
		}
		return new DialogCommands(bot, person, model.locateRoute("Navegação - Comandos"), model, message);
	}
	
}
