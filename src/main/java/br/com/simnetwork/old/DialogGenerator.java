package br.com.simnetwork.old;

import org.json.JSONObject;

import com.pengrad.telegrambot.*;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.view.additional.competencia.DialogAddCompetencia;
import br.com.simnetwork.view.additional.competencia.DialogDeleteCompetencia;
import br.com.simnetwork.view.additional.competencia.DialogEditCompetencia;
import br.com.simnetwork.view.additional.competencia.DialogInfoCompetencia;
import br.com.simnetwork.view.additional.competencia.DialogMinhasCompetencias;

public class DialogGenerator {
	
	private TelegramBot bot;
	private JSONObject telegramMessage;
	private Model model = (Model) App.getCon().getBean("model");
	private String admin;
	private String message = " ";
	private Usuario person;

	public DialogGenerator(JSONObject telegramMessage, TelegramBot bot, String admin) {
		super();
		this.telegramMessage = telegramMessage;
		this.bot = bot;
		this.admin = admin;
	}
	
	public DialogGenerator(String message, TelegramBot bot, String admin, Usuario person) {
		super();
		this.message = message;
		this.bot = bot;
		this.admin = admin;
		this.person = person;
	}
	
	public Dialog create(String routeGroup){
		
		if(!person.isActive()){
			bot.execute(new SendMessage(person.getIdTelegram(),"Usuário Bloqueado"));
			return null;
		}
		
		Rota route = model.locateRoute(routeGroup+" - "+message);
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
				if((completeRoute).equals("Meus Dados - Apelido")){
					return new DialogEditApelido(bot,person,route,model,message);
				}
				if((completeRoute).equals("Meus Dados - Nome Completo")){
					return new DialogEditNome(bot,person,route,model,message);
				}
				if((completeRoute).equals("Meus Dados - Competências")){
					return new DialogMinhasCompetencias(bot,person,route,model,message);
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
				if((completeRoute).equals("Meus Dados - Dados Login")){
					return new DialogShowUserInfo(bot,person,route,model,message);
				}
				if((completeRoute).equals("Navegação - Testes")){
					return new DialogTest(bot,person,route,model,message);
				}
				if((completeRoute).equals("Administrativo - Ativar Usuário")){
					return new DialogActiveUser(bot,person,route,model,message);
				}
				if((completeRoute).equals("Administrativo - Remover Permissão por menu")){
					return new DialogRevokeGroupPermission(bot,person,route,model,message);
				}
				if((completeRoute).equals("Administrativo - Desativar Usuário")){
					return new DialogDisableUser(bot,person,route,model,message);
				}
				if((completeRoute).equals("Administrativo - Dar Permissão por menu")){
					return new DialogGrandGroupPermission(bot,person,route,model,message);
				}
				if((completeRoute).equals("Termos - Adicionar")){
					return new DialogAddTermos(bot,person,route,model,message);
				}
				if((completeRoute).equals("Termos - Editar")){
					return new DialogEditTermos(bot,person,route,model,message);
				}
				if((completeRoute).equals("Termos - Ler")){
					return new DialogReadTermos(bot,person,route,model,message);
				}
				if((completeRoute).equals("Termos - Deletar")){
					return new DialogDeleteTermos(bot,person,route,model,message);
				}
				if((completeRoute).equals("Termos - Oficializar")){
					return new DialogOfficialTermos(bot,person,route,model,message);
				}
				if((completeRoute).equals("Termos - Aceitar")){
					return new DialogAcceptTermo(bot,person,route,model,message);
				}
				
				if((completeRoute).equals("Competências - Adicionar")){
					return new DialogAddCompetencia(bot,person,route,model,message);
				}
				if((completeRoute).equals("Competências - Editar")){
					return new DialogEditCompetencia(bot,person,route,model,message);
				}
				if((completeRoute).equals("Competências - Consultar")){
					return new DialogInfoCompetencia(bot,person,route,model,message);
				}
				if((completeRoute).equals("Competências - Deletar")){
					return new DialogDeleteCompetencia(bot,person,route,model,message);
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
			return new DialogEditApelido(bot,person,model.locateRoute("Meus Dados - Editar Login"),model,message);
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
