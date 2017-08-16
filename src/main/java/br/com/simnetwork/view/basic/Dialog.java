package br.com.simnetwork.view.basic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;

import br.com.simnetwork.access.AccessConfiguration;
import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.basic.MessageLog;
import br.com.simnetwork.model.basic.ScheduleMessage;
import br.com.simnetwork.model.basic.person.Person;
import br.com.simnetwork.model.basic.route.Route;
import br.com.simnetwork.model.files.BoxFileObject;
import br.com.simnetwork.model.files.BoxFolderObject;

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
	 * Instâcia que representa uma pessoa dentro do bot, nela o principal dado usado
	 * é o atributo idTelegram.
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

	/**
	 * Mensagem enviado pelo usuário.
	 */
	protected String message;

	/**
	 * JSON que armazena os dados que o usuário esta inserindo no dialog, ficando
	 * temporariamente na instância do Dialog, sendo necessário ainda no diálogo
	 * persistir em um banco final.
	 */
	protected JSONObject complement;

	/**
	 * StringBuilder que armazena a resposta que o bot vai dar ao usuário. Esta
	 * resposta é enviada ao executar o método finishStep ou finishHim.
	 */
	protected StringBuilder answer = new StringBuilder();

	/**
	 * Lista objetos aleatório para uso dentro do diálogo
	 */
	protected List objects = new LinkedList<>();

	/**
	 * Lista de conteudos que serão eliminados da lista objects ao executar o médodo
	 * emptyTrash
	 */
	protected List trash = new LinkedList<>();
	
	private boolean skip = false;
	
	private String skippedValue;
	
	/**
	 * Controlador interno para saber se a lista foi esvaziada para finalizar o
	 * dialogo em caso positivo
	 */
	private boolean isEmpty = false;

	/**
	 * Controlador interno que armazena parte do contador de passos
	 */
	private int stepControl;

	/**
	 * Define que o Dialog está ativo ou se já esta finalizado.
	 */
	private boolean finish;

	/**
	 * Map de strings para ser criado o keyboard
	 */
	private Map<Integer, String[]> map = new HashMap<Integer, String[]>();

	/**
	 * Recupera do TelegramReponse o nome do arquivo sugerido pelo usuário ou o
	 * original (arquivo novo)
	 */
	public String fileName;

	/**
	 * Armazena se foi solicitado a necessidade de um arquivo no próximo passo para
	 * as tratativas de arquivos válidos
	 */
	private boolean needAFile = false;

	/**
	 * Instancia de BoxFileObject para uso interno
	 */
	private BoxFileObject boxFileObject;

	/**
	 * Instancia de BoxFolderObject para uso interno
	 */
	private BoxFolderObject boxFolderObject;

	/**
	 * Instância dos dados customizados de acesso
	 */
	private AccessConfiguration access = new AccessConfiguration();
	
	private Document document;
	private File file;
	
	protected Font fontTitle;
	protected Font fontSubTitle;
	protected Font fontSimpleText;
	protected Font fontBold;

	// Construtor
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
		
		this.fontTitle = new Font();
		this.fontSubTitle = new Font();
		this.fontSimpleText = new Font();
		this.fontBold = new Font();
		
		this.fontTitle.setSize(18);
		this.fontTitle.setStyle(fontTitle.BOLD);
		
		this.fontSubTitle.setSize(14);
		
		this.fontSimpleText.setSize(12);
		
		this.fontBold.setSize(12);
		this.fontBold.setStyle(this.fontBold.BOLD);
	}

	// Inicio dos métodos protected--------------------------------------------------------------
	
	//Ciclos dentro do dialogo-------------------------------------------------------------------
	/**
	 * Usado dentro de um if para que seja utilizado o bloco responsável por um passo.
	 * <blockquote>
	 * 
	 *         <pre>
	 *         if(nextStep()){
	 *         		Instruções do passo 1
	 *         }
	 *         if(nextStep()){
	 *         		Instruções do passo 2
	 *         }
	 *         </pre>
	 * </blockquote>
	 * @return
	 */
	protected boolean nextStep() {
		if(message.equals("Pular") && skip) {
			message = skippedValue;
		}
		this.skip = false;
		
		if (stepControl == step) {
			return true;
		} else {
			stepControl++;
			return false;
		}
	}
	
	/**
	 * Define manualmente qual o step do dialog. <br>
	 * <b>Atenção</b><br>
	 * Cuidado ao usar este recurso, pois um dialog pode estar capturando dados do
	 * usuário, se for adiantado algum step sem estes dados, sua lógica pode não
	 * funcionar.
	 * 
	 * @param step
	 *            int - Coloque o número do step que deseja avançar.
	 */
	protected void setStep(int step) {
		this.step = step;
	}
	
	/**
	 * Prepara o keyboard de resposta com n colunas, fornecido por parametro para a
	 * definição manual do mesmo.
	 * 
	 * @param strings
	 * @param n
	 */
	protected void prepareKeyboard(List<String> strings, int n) {
		if (strings.size() != 0) {
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
	}

	/**
	 * Método que, baseado no tamanho da maior string da lista fornecida, prepara um
	 * keyboard definindo uma melhor quantidade de colunas mantendo mais agradável o
	 * uso.
	 * 
	 * @param strings
	 */
	protected void prepareKeyboard(List<String> strings) {
		if (strings.size() != 0) {
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

	}
	
	/**
	 * Finaliza o passo, colocando false no parametro ele não irá mostrar o menu
	 * @param menu
	 * @return
	 */
	protected Dialog finishStep(boolean menu) {
		if (isEmpty)
			return finishHim();
		if (menu) {
			if (step != 1) {
				map.put(map.size() + 1, new String[] { "Voltar", "Menu" });
			} else {
				map.put(map.size() + 1, new String[] { "Menu" });
			}
		}
		bot.execute(new SendMessage(person.getIdTelegram(), answer.toString())
				.replyMarkup(new ReplyKeyboardMarkup(map.values().toArray(new String[map.size()][20]))));
		model.addMessageLog(new MessageLog(person, answer.toString(), "Enviado pelo Bot"));
		answer = new StringBuilder();
		++this.step;
		this.stepControl = 1;
		map = new HashMap<>();
		return null;
	}

	/**
	 * Finaliza o passo
	 * @return
	 */
	protected Dialog finishStep() {
		if (isEmpty)
			return finishHim();
		internalFinishStep();
		++this.step;
		return null;
	}

	/**
	 * Finaliza o passo sendo informado manualmente o numero do passo que ele deve ir.
	 * @param step
	 * @return
	 */
	protected Dialog finishStep(int step) {
		internalFinishStep();
		this.step = step;
		return null;
	}
	
	protected Dialog finishStep(String skipValue) {
		if (isEmpty)
			return finishHim();
		map.put(map.size() + 1, new String[] { "Pular" });
		internalFinishStep();
		++this.step;
		this.skip = true;
		this.skippedValue = skipValue;
		return null;
	}
	
	/**
	 * Finaliza o diálogo enviando como retorno um novo diálogo. Usado para quando é
	 * necessário continuar o processo por um novo diálogo
	 * 
	 * @param dialog
	 *            Nova instância de dialogo criado manualmente para tomar o lugar da
	 *            que esta sendo finalizada
	 * @return Retorna a nova instância
	 */
	protected Dialog finishHim(Dialog dialog) {
		map.put(map.size() + 1, new String[] { "Menu" });
		bot.execute(new SendMessage(person.getIdTelegram(), answer.toString())
				.replyMarkup(new ReplyKeyboardMarkup(map.values().toArray(new String[map.size()][20]))));
		this.finish = true;
		this.step++;
		map = new HashMap<>();
		return dialog;
	}
	
	/**
	 * Finaliza o diálogo (Sem criação de novo diálogo)
	 * @return
	 */
	protected Dialog finishHim() {
		map.put(map.size() + 1, new String[] { "Menu" });
		bot.execute(new SendMessage(person.getIdTelegram(), answer.toString())
				.replyMarkup(new ReplyKeyboardMarkup(map.values().toArray(new String[map.size()][20]))));
		this.finish = true;
		this.step++;
		map = new HashMap<>();
		return null;
	}
	
	/**
	 * Caso seja necessário forçar a entrada no ciclo pelo comando voltar
	 * @return
	 * Booleano para entrar no bloque de entrada válida
	 */
	protected boolean isValid(String complementoNecessario) {
		if(message.equals("Voltar") && complement.has(complementoNecessario)) {
			return true;
		}else {
			return false;
		}
	}

	//Listas temporárias---------------------------------------------------------------------
	
	/**
	 * Adiciona um complemento String na instância do diálogo. Automaticamente pega
	 * o conteúdo digitado pelo usuário, sendo necessario informar somente a chave
	 * usada.
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
	 *         </blockquote>
	 */
	protected JSONObject addComplementString(String key) {
		if (!message.equals("Voltar")) {
			this.complement.put(key, message);
		}
		return this.complement;
	}

	/**
	 * Adiciona um complemento String na instância do diálogo. Pode ser customizada,
	 * podendo receber uma string tratada como conteúdo.
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
	 *         </blockquote>
	 */
	protected JSONObject addComplementString(String key, String content) {
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
	 *         </blockquote>
	 */
	protected String getComplementString(String key) {
		try {
			return this.complement.getString(key);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Remove os itens que devem estar na lista apresentada ao usuário, caso ela
	 * fique vazia, alerta o usuário.
	 */
	protected void emptyTrash() {
		objects.removeAll(trash);
		if (objects.size() == 0) {
			answer.append("\nNenhum registro foi localizado");
			isEmpty = true;
		}
	}

	//Manipulação de arquivos------------------------------------------------------------------
	
	/**
	 * Solicita ao usuário o envio de um arquivo
	 * 
	 * @param folder
	 *            Nome da pasta que será salvo o arquivo
	 */
	protected void iNeedAFile(String... folder) {
		System.out.println("Solicitado arquivo");
		answer.append("Envie um arquivo a seguir\n");
		this.needAFile = true;
		
		model.inicializeBox();
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

	/**
	 * Solicita ao usuário o envio de um arquivo
	 * 
	 * @param defineFileName
	 *            Coloque true para informar que será informado manualmente o nome
	 *            do arquivo
	 * @param fileName
	 *            String com o nome do arquivo que esta sendo definido
	 * @param folder
	 *            Nome da pasta que será salvo o arquivo
	 */
	protected void iNeedAFile(boolean defineFileName, String fileName, String... folder) {
		iNeedAFile(folder);
		this.fileName = fileName;
	}

	/**
	 * Envia um arquivo para o usuário do diálogo atual
	 * 
	 * @param file
	 *            Objeto File que será enviado ao usuário
	 */
	protected void sendFile(File file) {
		bot.execute(new SendDocument(person.getIdTelegram(), file));
	}

	/**
	 * Envio de arquivos para n persons
	 * @param file
	 * Arquivo em si
	 * @param persons
	 * Lista de pessoas que receberão os arquivos
	 */
	protected void sendFiles(File file, List<Person> persons) {
		for (Person person : persons) {
			bot.execute(new SendDocument(person.getIdTelegram(), file));
		}
	}
	
	protected File createPDF(String fileName, List<Chunk> chunks, String... folder) {
		this.document = new Document();
		this.file = new File("src/main/resources/temp/"+LocalDateTime.now().toString()+".pdf");
		List<Paragraph> paragraphs = new LinkedList<>();
		Paragraph paragraphTemp = null;
		
		for (Chunk chunk : chunks) {
			paragraphTemp = new Paragraph();
			paragraphTemp.add(chunk);
			paragraphs.add(paragraphTemp);
		}
		
		try {
			OutputStream fileOut = new FileOutputStream(file);
			PdfWriter.getInstance(document, fileOut);
			document.open();
			for (Paragraph paragraph : paragraphs) {
				document.add(paragraph);
			}
			document.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BoxFolderObject currentFolder = null;
		BoxFolderObject rootFolder = null;
		
		for (String string : folder) {
			currentFolder = model.locateBoxFolderObjectByName(string);
			if (currentFolder == null) {
				currentFolder = new BoxFolderObject(string, model, rootFolder);
			}
			rootFolder = currentFolder;
		}
		
		model.addBoxFileObject(new BoxFileObject(fileName+".pdf",model,file,currentFolder));
		
		return file;
		
	}

	//Envio de mensagens-----------------------------------------------------------------------------
	
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
	 * Retorno utilizado quando o usuário envia um conteúdo inválido.
	 * 
	 * @return True - Utilizado para finalizar o passo sem avança-lo, forçando o
	 *         usuário a tentar novamente.
	 * 
	 * 
	 */
	protected Dialog messageInvalid() {
		answer("Conteúdo inválido, tente novamente");
		this.stepControl = 1;
		return null;
	}
	
	/**
	 * Método que retorna a forma padrão de pedir alguma confirmação ao usuário Já
	 * esta no padrão de keyboard
	 * 
	 * @return
	 */
	protected void messageConfirmation() {
		answer.append("\n\nDeseja Confirmar esta ação?");
		map.put(map.size() + 1, new String[] { "Sim", "Não" });
	}

	/**
	 * Encapsulamento da confimação final do usuário, antes de efetivar a ação do
	 * usuário
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
	 * Adiciona uma mensagem agendada, que será executada somente quando a data/hora
	 * atual for igual ao localdatetime enviado neste método.
	 * 
	 * @param person
	 * @param message
	 * @param trigger
	 */
	protected void answer(Person person, String message, LocalDateTime trigger) {
		ScheduleMessage scheduleMessage = new ScheduleMessage(person, message, trigger);
		model.addScheduleMessage(scheduleMessage);
		model.schedule.schedule(() -> {
			bot.execute(new SendMessage(person.getIdTelegram(), message));
			model.removeScheduleMessage(scheduleMessage);
		}, LocalDateTime.now().until(trigger, ChronoUnit.MILLIS), TimeUnit.MILLISECONDS);

	}
	
	/**
	 * Envia a mesma mensagem para n persons
	 * @param message
	 * Mensagem em si
	 * @param persons
	 *Lista das pessoas que receberão a mensagem
	 */
	protected void sendMessages(String message, List<Person> persons) {
		for (Person person : persons) {
			answer(person, message);
		}
	}
		
	// Inicio dos métodos públicos

	/**
	 * Método usado para definir se será usado ou não um arquivo
	 * 
	 * @param needAFile
	 * 
	 */
	public void setNeedAFile(boolean needAFile) {
		this.needAFile = needAFile;
	}

	/**
	 * Método para conferir se esta sendo necessário o envio do arquivo (Usado pelo
	 * TelegramResponse)
	 * 
	 * @return
	 */
	public boolean isNeedAFile() {
		return needAFile;
	}

	/**
	 * Recupera o objeto Person do usuário que está interagindo com o bot.
	 * 
	 * @return Person - Instância direta de Person.
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * Sobrescreve a mensagem que o usuário digitou para o bot. <br>
	 * <b>Atenção</b><br>
	 * Cuidado ao utiliza-lo para não perder a referência do que o usuário digitou.
	 * 
	 * @param message
	 *            String - Mensagem que será considerada no bot.
	 */
	public void setMessage(String message) {
		this.message = message;
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

	/**
	 * Finaliza abruptamente uma instância de Dialog, ao usuário enviar "Menu"
	 * ao bot.
	 */
	public void quit() {
		if (message.equals("Menu")) {
			this.step = 0;
			this.finish = true;
		}
	}
	
	/**
	 * Volta um passo (implementação da rotina de voltar)
	 */
	public void backStep() {
		if (step == 2) {
			this.step = step - 1;
			this.stepControl = 1;
		} else {
			this.step = step - 2;
			this.stepControl = 1;
		}

	}

	/**
	 * Insere no banco e no box o arquivo enviado ao usuário (usado pelo TelegramResponse)
	 * @param fileId
	 * @param name
	 */
	public void setBoxFile(String fileId, String name) {
		model.inicializeBox();
		GetFile request = new GetFile(fileId);
		GetFileResponse getFileResponse = bot.execute(request);
		com.pengrad.telegrambot.model.File file = getFileResponse.file();
		this.boxFileObject = new BoxFileObject(name, bot.getFullFilePath(file), model, boxFolderObject);
	}

	/**
	 * Permite que o TelegramResponse resete o uso do objeto boxFile
	 */
	public void resetBoxFile() {
		this.boxFileObject = null;

	}

// Métodos Privados (Uso somente interno)
	
	/**
	 * Método onde o bot responde ao usuário (uso interno)
	 * @param message
	 */
	private void answer(String message) {
		bot.execute(new SendMessage(person.getIdTelegram(), message));
	}
	
	/**
	 * Localiza a maios palavra dentro de uma lista de strings retornando seu tamanho.
	 * @param strings
	 * @return
	 */
	private int biggerString(List<String> strings) {
		int result = 0;
		for (String string : strings) {
			if (string.length() > result) {
				result = string.length();
			}
		}
		return result;
	}
	
	/**
	 * Realiza verificações internas comuns a todo finish step
	 */
	private void internalFinishStep() {
		if (step != 1) {
			map.put(map.size() + 1, new String[] { "Voltar", "Menu" });
		} else {
			map.put(map.size() + 1, new String[] { "Menu" });
		}
		bot.execute(new SendMessage(person.getIdTelegram(), answer.toString())
				.replyMarkup(new ReplyKeyboardMarkup(map.values().toArray(new String[map.size()][20]))));
		model.addMessageLog(new MessageLog(person, answer.toString(), "Enviado pelo Bot"));
		answer = new StringBuilder();
		this.stepControl = 1;
		map = new HashMap<>();

	}
	
	/**
	 * Método de ação do bot, deve ser utilizada retornos com os métodos finishStep,
	 * finishHim ou invalidMessage.
	 * 
	 * @return Sempre True, o retorno é somente um gatilho para finalizar o método.
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
	 *         </blockquote>
	 */
	public abstract Dialog action();

}
