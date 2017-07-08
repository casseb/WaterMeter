package dialogs.basic;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;

import access.AccessConfiguration;
import lombok.Getter;
import lombok.Setter;
import mvc.Model;
import objects.BoxFileObject;
import objects.BoxFolderObject;
import objects.MessageLog;
import objects.Person;
import objects.Route;
import objects.ScheduleMessage;

/**
 * 
 * Classe abstrata onde as principais rotinas dos diálogos estão parametrizados.
 * Para criar uma nova rota de diálogo, basta criar uma nova classe extendendo
 * esta.
 *
 */
public abstract class Dialog {

	/**
	 * Instância do Telegram Bot.
	 */
	protected TelegramBot bot;

	/**
	 * Id do Telegram do Admin do bot, usado para facilitar a comunicação direta
	 * entre o bot e o administrador.
	 */
	protected String admin;

	/**
	 * Instância da classe responsável pela persistência de dados.
	 */
	protected Model model;

	/**
	 * Instâcia que representa uma pessoa dentro do bot, nela o principal dado
	 * usado é o atributo idTelegram.
	 */
	protected Person person;

	/**
	 * Instância que representa a rota respectiva do Dialog
	 */
	protected Route route;

	/**
	 * Step é o passo que a diálogo esta atualmente, iniciando-se no 1.
	 */
	protected int step;

	protected int stepControl;

	/**
	 * Mensagem enviado pelo usuário.
	 */
	protected String message;

	/**
	 * JSON que armazena os dados que o usuário esta inserindo no dialog,
	 * ficando temporariamente na instância do Dialog, sendo necessário ainda no
	 * diálogo persistir em um banco final.
	 */
	protected JSONObject complement;

	/**
	 * Define que o Dialog esta ativo eu se já esta finalizado.
	 */
	protected boolean finish;

	/**
	 * StringBuilder que armazena a resposta que o bot vai dar ao usuário. Esta
	 * resposta é enviada ao executar o método finishStep pu finishHim.
	 */
	protected StringBuilder answer = new StringBuilder();

	/**
	 * Lista de String para adicionar os submenus de um dialog, encapsulando a
	 * tratativa de criação de novos dialogs a partir de um dialog original.
	 */
	protected List<String> subMenus = new LinkedList<>();

	private Map<Integer, String[]> map = new HashMap<Integer, String[]>();
	
	public String fileName;

	private boolean needAFile = false;

	/**
	 * Solicita ao usuário o envio de um arquivo
	 * 
	 * @param folder
	 *            Nome da pasta que será salvo o arquivo
	 */
	public void iNeedAFile(String... folder) {
		model.inicializeBox();
		answer.append("Envie um arquivo a seguir\n");
		this.needAFile = true;
		BoxFolderObject currentFolder = null;
		BoxFolderObject rootFolder = null;

		for (String string : folder) {
			currentFolder = model.locateBoxFolderObjectByName(string);
			if (currentFolder == null) {
				currentFolder = new BoxFolderObject(string, model, rootFolder);
			}
			rootFolder = currentFolder;
		}

		this.boxFolderObject = currentFolder;
	}
	
	public void iNeedAFile(boolean defineFileName, String fileName, String... folder) {
		iNeedAFile(folder);
		this.fileName = fileName;
	}

	protected void sendFile(File file) {
		bot.execute(new SendDocument(person.getIdTelegram(), file));
	}

	public boolean isNeedAFile() {
		return needAFile;
	}

	public void setNeedAFile(boolean needAFile) {
		this.needAFile = needAFile;
	}

	protected BoxFileObject boxFileObject;
	protected BoxFolderObject boxFolderObject;
	
	AccessConfiguration access = new AccessConfiguration();

	public Dialog(TelegramBot bot, Person person, Route route, Model model, String message) {
		super();
		this.bot = bot;
		this.person = person;
		this.route = route;
		this.complement = new JSONObject();
		this.step = 1;
		this.stepControl = 1;
		this.admin = access.getAdminTelegram();
		this.model = model;
		this.message = message;
	}

	/**
	 * Adiciona um complemento String na instância do diálogo. Automaticamente
	 * pega o conteúdo digitado pelo usuário, sendo necessario informar somente
	 * a chave usada.
	 * 
	 * @param key
	 *            Chave usada para recuperar o dado quando necessário.
	 * @return Sem retorno.
	 * 
	 *         <blockquote>
	 * 
	 *         <pre>
	 *Exemplo:
	 *
	 *addComplementString("usuario");
	 *
	 *Armazenado o que foi digitado pelo usuário na chave 
	 *"usuario" da instância do dialogo.
	 *         </pre>
	 * 
	 * 		</blockquote>
	 */
	public JSONObject addComplementString(String key) {
		if (!message.equals("Voltar")) {
			this.complement.put(key, message);
		}
		return this.complement;
	}

	/**
	 * Adiciona um complemento String na instância do diálogo. Pode ser
	 * customizada, podendo receber uma string tratada como conteúdo.
	 * 
	 * @param key
	 *            Chave usada para recuperar o dado quando necessário.
	 * @param content
	 *            Conteúdo String do complemento.
	 * @return Sem retorno.
	 * 
	 *         <blockquote>
	 * 
	 *         <pre>
	 *Exemplo:
	 *
	 *addCustomComplementString("usuario","teste");
	 *
	 *Armazenado a palavra "teste" na chave 
	 *"usuario" da instância do dialogo.
	 *         </pre>
	 * 
	 * 		</blockquote>
	 */
	public JSONObject addComplementString(String key, String content) {
		if (!message.equals("Voltar")) {
			this.complement.put(key, content);
		}
		return this.complement;
	}

	/**
	 * Recupera o conteúdo inserido pelo <code> addComplementString </code> ou
	 * <code> addCustomComplementString </code>.
	 * 
	 * @param key
	 *            Chave usada na criação para identificar o que deseja ser
	 *            retornado.
	 * @return String gravada anteriormente.
	 * 
	 *         <blockquote>
	 * 
	 *         <pre>
	 *Exemplo:
	 *
	 *String resultado = getComplementString("usuario");
	 *
	 *A String resultado acaba recebendo o conteudo armazenado
	 *na chave "usuario".
	 *         </pre>
	 * 
	 * 		</blockquote>
	 */
	public String getComplementString(String key) {
		try {
			return this.complement.getString(key);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Define manualmente qual o step do dialog. <br>
	 * <b>Atenção</b><br>
	 * Cuidado ao usar este recurso, pois um dialog pode estar capturando dados
	 * do usuário, se for adiantado algum step sem estes dados, sua lógica pode
	 * não funcionar.
	 * 
	 * @param step
	 *            int - Coloque o número do step que deseja avançar.
	 */
	public void setStep(int step) {
		this.step = step;
	}

	/**
	 * Recupera o objeto Person do usuário que está interagindo com o bot.
	 * 
	 * @return Person - Instância direata de Person.
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * Sobrescreve a mensagem que o usuário digitou para o bot. <br>
	 * <b>Atenção</b><br>
	 * Cuidado ao utiliza-lo para não perder a referência do que o usuário
	 * digitou.
	 * 
	 * @param message
	 *            String - Mensagem que será considerada no bot.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public Dialog finishHim(Dialog dialog) {
		map.put(map.size() + 1, new String[] { "Menu" });
		bot.execute(new SendMessage(person.getIdTelegram(), answer.toString())
				.replyMarkup(new ReplyKeyboardMarkup(map.values().toArray(new String[map.size()][20]))));
		this.finish = true;
		this.step++;
		map = new HashMap<>();
		return dialog;
	}

	public Dialog finishHim() {
		map.put(map.size() + 1, new String[] { "Menu" });
		bot.execute(new SendMessage(person.getIdTelegram(), answer.toString())
				.replyMarkup(new ReplyKeyboardMarkup(map.values().toArray(new String[map.size()][20]))));
		this.finish = true;
		this.step++;
		map = new HashMap<>();
		return null;
	}

	/**
	 * Método que verifica se uma instância de Dialog já foi finalizada.
	 * 
	 * @return true - Já finalizado<br>
	 *         false - Não finalizado
	 */
	public boolean isFinish() {
		return this.finish;
	}

	private void answer(String message) {
		bot.execute(new SendMessage(person.getIdTelegram(), message));
	}

	/**
	 * Faz o bot enviar uma mensagem a alguem customizado.
	 * 
	 * @param person
	 *            - Objeto Person do destinatário da mensagem.
	 * @param message
	 *            - String da Mensagem que deseja enviar
	 */
	protected void answer(Person person, String message) {
		bot.execute(new SendMessage(person.getIdTelegram(), message));
	}

	/**
	 * Finaliza abruptamente uma instância de Dialog, ao usuário enviar
	 * "/desisto" ao bot.
	 */
	public void quit() {
		if (message.equals("Menu")) {
			this.step = 0;
			this.finish = true;
		}
	}

	/**
	 * Retorno utilizado quando o usuário envia um conteúdo inválido.
	 * 
	 * @return True - Utilizado para finalizar o passo sem avança-lo, forçando o
	 *         usuário a tentar novamente.
	 * 
	 * 
	 */
	protected Dialog messageInvalid() {
		answer("Conteúdo inválido, tente novamente");
		return null;
	}

	/**
	 * Método que retorna a forma padrão de pedir alguma confirmação ao usuário
	 * Já esta no padrão de linkagem do telegram para que o usuário possa
	 * pressionar o link.
	 * 
	 * @return
	 */
	protected void messageConfirmation() {
		answer.append("\n\nDeseja Confirmar esta ação?");
		map.put(map.size() + 1, new String[] { "Sim", "Não" });
	}

	/**
	 * Encapsulamento da confimação final do usuário, antes de efetivar a ação
	 * do usuário
	 * 
	 * @return true quando o usuário confirmar a ação e false quando ele recusar
	 */
	protected boolean isConfirmated() {
		if (!message.equals("Sim")) {
			answer.append("Ação cancelada\n");
			return false;
		} else {
			answer.append("Ação concluída com sucesso\n");
			return true;
		}
	}

	/**
	 * Adiciona na resposta do bot uma lista com as opções de submenu
	 * adicionadas
	 */
	protected void messageSubMenus() {
		for (String string : subMenus) {
			answer.append("\n" + string);
		}
	}

	protected boolean isSubMenuValid(String string) {
		for (String subMenu : subMenus) {
			if (subMenu.equals(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Prepara o keyboard de resposta com n colunas, fornecido por parametro
	 * para a definição manual do mesmo.
	 * 
	 * @param strings
	 * @param n
	 */
	protected void prepareKeyboard(List<String> strings, int n) {
		int linha = 0;
		int item = 0;
		// Caso a lista seja menor que a quantidade de colunas
		if (n > strings.size()) {
			String[] conteudoUnico = new String[strings.size()];
			for (int i = 0; i < strings.size(); i++) {
				conteudoUnico[i] = strings.get(item++);
			}
			map.put(linha++, conteudoUnico);
		} else {
			// Caso haja sobra na distribuição dos botões
			if (strings.size() % n != 0) {
				int sobra = strings.size() % n;
				if (sobra > 0) {
					String[] conteudoSobra = new String[sobra];
					for (int i = 0; i < sobra; i++) {
						conteudoSobra[i] = strings.get(item++);
					}
					map.put(linha++, conteudoSobra);
				}
			}
			// Distribuição final
			while (item < strings.size()) {
				String[] conteudo = new String[n];
				for (int i = 0; i < n; i++) {
					conteudo[i] = strings.get(item++);
				}
				map.put(linha++, conteudo);
			}
		}
	}

	/**
	 * Método que, baseado no tamanho da maior string da lista fornecida,
	 * prepara um keyboard definindo uma melhor quantidade de colunas mantendo
	 * mais agradável o uso.
	 * 
	 * @param strings
	 */
	protected void prepareKeyboard(List<String> strings) {
		int linha = 0;
		int item = 0;

		int biggerString = biggerString(strings);

		int size1 = 32;
		int size2 = 15;
		int size3 = 9;
		int size4 = 5;

		int n;

		if (biggerString >= size1) {
			n = 1;
		} else {
			if (biggerString >= size2) {
				n = 1;
			} else {
				if (biggerString >= size3) {
					n = 2;
				} else {
					if (biggerString >= size4) {
						n = 3;
					} else {
						n = 4;
					}
				}
			}
		}

		// Caso a lista seja menor que a quantidade de colunas
		if (n > strings.size()) {
			String[] conteudoUnico = new String[strings.size()];
			for (int i = 0; i < strings.size(); i++) {
				conteudoUnico[i] = strings.get(item++);
			}
			map.put(linha++, conteudoUnico);
		} else {
			// Caso haja sobra na distribuição dos botões
			if (strings.size() % n != 0) {
				int sobra = strings.size() % n;
				if (sobra > 0) {
					String[] conteudoSobra = new String[sobra];
					for (int i = 0; i < sobra; i++) {
						conteudoSobra[i] = strings.get(item++);
					}
					map.put(linha++, conteudoSobra);
				}
			}
			// Distribuição final
			while (item < strings.size()) {
				String[] conteudo = new String[n];
				for (int i = 0; i < n; i++) {
					conteudo[i] = strings.get(item++);
				}
				map.put(linha++, conteudo);
			}
		}
	}

	private int biggerString(List<String> strings) {
		int result = 0;
		for (String string : strings) {
			if (string.length() > result) {
				result = string.length();
			}
		}
		return result;
	}

	protected Dialog finishStep(boolean menu) {
		if (menu) {
			if (step != 1) {
				map.put(map.size() + 1, new String[] { "Voltar", "Menu" });
			} else {
				map.put(map.size() + 1, new String[] { "Menu" });
			}
		}
		bot.execute(new SendMessage(person.getIdTelegram(), answer.toString())
				.replyMarkup(new ReplyKeyboardMarkup(map.values().toArray(new String[map.size()][20]))));
		model.addMessageLog(new MessageLog(person, answer.toString(),"Enviado pelo Bot"));
		answer = new StringBuilder();
		++this.step;
		this.stepControl = 1;
		map = new HashMap<>();
		return null;
	}

	protected Dialog finishStep() {
		internalFinishStep();
		++this.step;
		return null;
	}

	protected Dialog finishStep(int step) {
		internalFinishStep();
		this.step = step;
		return null;
	}

	private void internalFinishStep() {
		if (step != 1) {
			map.put(map.size() + 1, new String[] { "Voltar", "Menu" });
		} else {
			map.put(map.size() + 1, new String[] { "Menu" });
		}
		bot.execute(new SendMessage(person.getIdTelegram(), answer.toString())
				.replyMarkup(new ReplyKeyboardMarkup(map.values().toArray(new String[map.size()][20]))));
		model.addMessageLog(new MessageLog(person, answer.toString(),"Enviado pelo Bot"));
		answer = new StringBuilder();
		this.stepControl = 1;
		map = new HashMap<>();
		
		
	}

	public void backStep() {
		if (step == 2) {
			this.step = step - 1;
		} else {
			this.step = step - 2;
		}

	}

	public void setBoxFile(String fileId, String name) {
		model.inicializeBox();
		GetFile request = new GetFile(fileId);
		GetFileResponse getFileResponse = bot.execute(request);
		com.pengrad.telegrambot.model.File file = getFileResponse.file();
		this.boxFileObject = new BoxFileObject(name, bot.getFullFilePath(file), model, boxFolderObject);
	}

	public void resetBoxFile() {
		this.boxFileObject = null;

	}

	protected boolean nextStep() {
		if (stepControl == step) {
			return true;
		} else {
			stepControl++;
			return false;
		}
	}
	
	
	/**
	 * Adiciona uma mensagem agendada, que será executada somente quando a data/hora atual for igual ao
	 * localdatetime enviado neste método.
	 * @param person
	 * @param message
	 * @param trigger
	 */
	protected void addMessageSchedule(Person person, String message, LocalDateTime trigger){
		ScheduleMessage scheduleMessage = new ScheduleMessage(person, message, trigger);
		model.addScheduleMessage(scheduleMessage);
		model.schedule.schedule(() -> {
			bot.execute(new SendMessage(person.getIdTelegram(),message));
			model.removeScheduleMessage(scheduleMessage);
			},LocalDateTime.now().until(trigger, ChronoUnit.MILLIS),TimeUnit.MILLISECONDS);
		
	}
	
	protected void sendMessages(String message, List<Person> persons){
		for (Person person : persons) {
			answer(person,message);
		}
	}
	
	protected void sendFiles(File file, List<Person> persons){
		for (Person person : persons) {
			bot.execute(new SendDocument(person.getIdTelegram(), file));
		}
	}
	

	/**
	 * Método de ação do bot, deve ser utilizada retornos com os métodos
	 * finishStep, finishHim ou invalidMessage.
	 * 
	 * @return Sempre True, o retorno é somente um gatilho para finalizar o
	 *         método.
	 * 
	 *         <blockquote>
	 * 
	 *         <pre>
	 *Exemplo:
	 *
	 *public boolean action() {
	 *	answer.append("Olá Humano!!!");
	 *	return finishHim();
	 *}
	 *
	 *
	 *         </pre>
	 * 
	 * 		</blockquote>
	 */
	public abstract Dialog action();

}
