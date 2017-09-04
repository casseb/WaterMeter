package br.com.simnetwork.view;

import java.util.Map;
import java.util.TreeMap;

import org.json.JSONObject;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.service.Bot;
import br.com.simnetwork.view.dialogStep.DialogStep;

public class DialogIA implements Dialog {

	private Map<Integer, DialogStep> steps = new TreeMap<Integer, DialogStep>();
	private Bot bot = App.getCon().getBean("telegramBot", Bot.class);
	private Integer currentStep = 1;
	private DialogTypeFinish currentTypeFinish = DialogTypeFinish.INICIOSTEP;
	private JSONObject complements = new JSONObject();
	private boolean lastStep = false;
	private String customTable;
	private String mensagemUsuario = "Inicial";

	public DialogIA() {
		super();
	}

	public DialogIA(Map<Integer, DialogStep> steps) {
		this.steps = steps;
	}

	// Retorno False para não executar o próximo passo
	// Retorno true para executar o próximo passo
	@Override
	public boolean action(String botId, String mensagemUsuario) {

		this.mensagemUsuario = mensagemUsuario;

		if (this.currentTypeFinish.equals(DialogTypeFinish.AGUARDANDODADO)
				|| this.currentTypeFinish.equals(DialogTypeFinish.ERRO)
				|| this.currentTypeFinish.equals(DialogTypeFinish.CANCELADO)) {
			return false;

		}

		if (this.currentTypeFinish.equals(DialogTypeFinish.FINALIZADOSTEP)) {

			this.currentStep++;
			this.currentTypeFinish = DialogTypeFinish.INICIOSTEP;
			if (this.steps.get(currentStep) != null) {
				return true;
			} else {
				this.currentTypeFinish = DialogTypeFinish.FINALIZADO;
				return false;
			}
		} else {

			if (this.currentTypeFinish.equals(DialogTypeFinish.INICIOSTEP)
					|| this.currentTypeFinish.equals(DialogTypeFinish.AGUARDANDODADO)
					|| this.currentTypeFinish.equals(DialogTypeFinish.CONTEUDOINVALIDO)) {

				return true;
			}
		}

		return false;

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

	@Override
	public void cleanSteps() {
		this.steps = new TreeMap<Integer, DialogStep>();

	}

	public String getMensagemUsuario() {
		return mensagemUsuario;
	}

	public void setMensagemUsuario(String mensagemUsuario) {
		this.mensagemUsuario = mensagemUsuario;
	}

}
