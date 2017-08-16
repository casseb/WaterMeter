package br.com.simnetwork.view.basic;

import org.json.JSONObject;

import com.pengrad.telegrambot.*;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.basic.person.Person;
import br.com.simnetwork.model.basic.route.Route;
import br.com.simnetwork.spring.ApplicationContext;
import br.com.simnetwork.view.additional.competencia.DialogAddCompetencia;
import br.com.simnetwork.view.additional.competencia.DialogDeleteCompetencia;
import br.com.simnetwork.view.additional.competencia.DialogEditCompetencia;
import br.com.simnetwork.view.additional.competencia.DialogInfoCompetencia;
import br.com.simnetwork.view.additional.competencia.DialogMinhasCompetencias;
import br.com.simnetwork.view.additional.information.DialogTermos;
import br.com.simnetwork.view.additional.project.DialogAddProject;
import br.com.simnetwork.view.additional.project.DialogEditProject;
import br.com.simnetwork.view.additional.project.DialogRemoveProject;
import br.com.simnetwork.view.additional.project.DialogShowProject;
import br.com.simnetwork.view.additional.termos.DialogAcceptTermo;
import br.com.simnetwork.view.additional.termos.DialogAddTermos;
import br.com.simnetwork.view.additional.termos.DialogDeleteTermos;
import br.com.simnetwork.view.additional.termos.DialogEditTermos;
import br.com.simnetwork.view.additional.termos.DialogOfficialTermos;
import br.com.simnetwork.view.additional.termos.DialogReadTermos;
import br.com.simnetwork.view.basic.users.DialogActiveUser;
import br.com.simnetwork.view.basic.users.DialogCommands;
import br.com.simnetwork.view.basic.users.DialogDisableUser;
import br.com.simnetwork.view.basic.users.DialogEditApelido;
import br.com.simnetwork.view.basic.users.DialogEditNome;
import br.com.simnetwork.view.basic.users.DialogGrandGroupPermission;
import br.com.simnetwork.view.basic.users.DialogGrandPermission;
import br.com.simnetwork.view.basic.users.DialogRevokeGroupPermission;
import br.com.simnetwork.view.basic.users.DialogRevokePermission;
import br.com.simnetwork.view.basic.users.DialogShowAllUsersInfo;
import br.com.simnetwork.view.basic.users.DialogShowUserInfo;
import br.com.simnetwork.view.basic.users.DialogTest;

public class DialogGenerator {
	
	private TelegramBot bot;
	private JSONObject telegramMessage;
	private Model model = (Model) ApplicationContext.getContext().getBean("model");
	private String admin;
	private String message = " ";
	private Person person;

	public DialogGenerator(JSONObject telegramMessage, TelegramBot bot, String admin) {
		super();
		this.telegramMessage = telegramMessage;
		this.bot = bot;
		this.admin = admin;
	}
	
	public DialogGenerator(String message, TelegramBot bot, String admin, Person person) {
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
				if((completeRoute).equals("Projeto - Adicionar")){
					return new DialogAddProject(bot,person,route,model,message);
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