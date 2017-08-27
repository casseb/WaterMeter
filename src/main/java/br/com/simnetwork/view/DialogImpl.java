package br.com.simnetwork.view;

import java.util.Map;
import java.util.TreeMap;

import org.json.JSONObject;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.service.Bot;
import br.com.simnetwork.model.service.UsuarioService;
import br.com.simnetwork.model.service.dialog.DialogStep;

public class DialogImpl implements Dialog {

	private Map<Integer, DialogStep> steps = new TreeMap<Integer, DialogStep>();
	private Bot bot = App.getCon().getBean("telegramBot", Bot.class);
	private Integer currentStep = 1;
	private DialogTypeFinish currentTypeFinish = DialogTypeFinish.INICIOSTEP;
	private JSONObject complements = new JSONObject();
	private boolean lastStep = false;
	private String customTable;
	public DialogImpl() {
		super();
	}

	@Override
	public DialogTypeFinish action(Usuario usuario, String mensagemUsuario) {
		
		if(currentTypeFinish.equals(DialogTypeFinish.FINALIZADOSTEP)) {
			currentTypeFinish = DialogTypeFinish.INICIOSTEP;
		}

		for (Map.Entry<Integer, DialogStep> step : steps.entrySet()) {
			if (step.getKey() == currentStep) {

				if (steps.get(currentStep + 1) == null) {
					lastStep = true;
				}
				Object retorno = step.getValue().action(usuario, mensagemUsuario, currentTypeFinish,complements);
				if (retorno instanceof DialogTypeFinish) {
					currentTypeFinish = (DialogTypeFinish) retorno;
					if (currentTypeFinish.equals(DialogTypeFinish.ERRO)) {
						bot.sendMessage(usuario,
								"Algo errado aconteceu. \nPeço que contate o administrador do sistema");
					}
					if (currentTypeFinish.equals(DialogTypeFinish.FINALIZADOSTEP)) {
						currentStep++;
					}
					if (lastStep && currentTypeFinish.equals(DialogTypeFinish.FINALIZADOSTEP)) {
						currentTypeFinish = DialogTypeFinish.FINALIZADO;
					}
					if (currentTypeFinish.equals(DialogTypeFinish.CONTEUDOINVALIDO)) {
						bot.sendMessage(usuario, "Conteúdo Inválido, tente novamente");
						currentTypeFinish = DialogTypeFinish.FINALIZADOSTEP;
					}
					if (currentTypeFinish.equals(DialogTypeFinish.CANCELADO)) {
						bot.sendMessage(usuario, "Operação Cancelada pelo Usuário");
						currentTypeFinish = DialogTypeFinish.FINALIZADO;
					}
					if (currentTypeFinish.equals(DialogTypeFinish.CONFIRMADO)) {
						if(customTable.equals("Usuario")) {
							UsuarioService usuarioService = App.getCon().getBean("usuarioService",UsuarioService.class);
							Usuario usuarioAtual = usuarioService.validarNovoUsuario(usuario.getBotId());
							if(usuarioAtual == null) {
								usuarioAtual = new Usuario();
								usuarioAtual.setBotId(usuario.getBotId());
								for (String complementKey : complements.keySet()) {
									if(complementKey.equals("usu_apelido")) {
										usuarioAtual.setApelido(complements.getString(complementKey));
									}
									if(complementKey.equals("usu_liberado")) {
										usuarioAtual.setLiberado(Integer.parseInt(complements.getString(complementKey)));
									}
								}
								usuarioService.criarUsuario(usuarioAtual.getBotId(),usuarioAtual.getApelido());
							}else {
								usuarioService.atualizarUsuario(usuarioAtual);
							}
							
							bot.sendMessage(usuarioAtual,"Gravação concluida com sucesso!");
							currentTypeFinish = DialogTypeFinish.FINALIZADO;
						}
					}
					return currentTypeFinish;
				}
				if (retorno instanceof JSONObject) {
					JSONObject retornoJson = new JSONObject();
					retornoJson = (JSONObject) JSONObject.wrap(retorno);
					String key = null;
					String value = null;
					for (String retornoJsonKey : retornoJson.keySet()) {
						key = retornoJsonKey;
						value = retornoJson.getString(retornoJsonKey);
					}
					complements.put(key, value);
					currentStep++;
					currentTypeFinish = DialogTypeFinish.FINALIZADOSTEP;
					return DialogTypeFinish.FINALIZADOSTEP;
				}
			}
		}

		return currentTypeFinish;

	}

	@Override
	public DialogTypeFinish getCurrentDialogTypeFinish() {
		return currentTypeFinish;
	}

	public Map<Integer, DialogStep> getSteps() {
		return steps;
	}

	public void setSteps(Map<Integer, DialogStep> steps) {
		this.steps = steps;
	}

	public int getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}

	public Bot getBot() {
		return bot;
	}

	public void setBot(Bot bot) {
		this.bot = bot;
	}

	public DialogTypeFinish getCurrentTypeFinish() {
		return currentTypeFinish;
	}

	public void setCurrentTypeFinish(DialogTypeFinish currentTypeFinish) {
		this.currentTypeFinish = currentTypeFinish;
	}

	public JSONObject getComplements() {
		return complements;
	}

	public void setComplements(JSONObject complements) {
		this.complements = complements;
	}

	public boolean isLastStep() {
		return lastStep;
	}

	public void setLastStep(boolean lastStep) {
		this.lastStep = lastStep;
	}

	public void setCurrentStep(Integer currentStep) {
		this.currentStep = currentStep;
	}

	public String getCustomTable() {
		return customTable;
	}

	public void setCustomTable(String customTable) {
		this.customTable = customTable;
	}

	
	
}
