package br.com.simnetwork.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.simnetwork.model.entity.acesso.Acesso;
import br.com.simnetwork.model.entity.basico.Utils;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;

public class TelegramBotImpl implements Bot {

	private TelegramBot bot;
	private Acesso access;
	private Map<Integer, String[]> map = new HashMap<Integer, String[]>();

	public TelegramBotImpl() {

	}

	public TelegramBotImpl(Acesso access) {
		this.access = access;
		String token = access.getTokenTelegram();
		bot = TelegramBotAdapter.build(token);
	}

	private void prepareMap() {
		map.put(map.size() + 1, new String[] { "Menu" });
	}

	@Override
	public void sendMessage(Usuario usuario, String mensagem) {
		prepareMap();
		bot.execute(new SendMessage(usuario.getBotId(), mensagem)
				.replyMarkup(new ReplyKeyboardMarkup(map.values().toArray(new String[map.size()][20]))));
		map.clear();
	}

	public void sendMessage(String mensagem) {
		prepareMap();
		bot.execute(new SendMessage(access.getAdminTelegram(), mensagem)
				.replyMarkup(new ReplyKeyboardMarkup(map.values().toArray(new String[map.size()][20]))));
		map.clear();
	}

	@Override
	public void sendMessage(List<Usuario> usuarios, String mensagem) {
		for (Usuario usuario : usuarios) {
			prepareMap();
			bot.execute(new SendMessage(usuario.getBotId(), mensagem)
					.replyMarkup(new ReplyKeyboardMarkup(map.values().toArray(new String[map.size()][20]))));
			map.clear();
		}
	}

	@Override
	public void sendMessage(String usuario, String mensagem) {
		prepareMap();
		bot.execute(new SendMessage(usuario, mensagem)
				.replyMarkup(new ReplyKeyboardMarkup(map.values().toArray(new String[map.size()][20]))));
		map.clear();
	}

	@Override
	public void prepareKeyboard(List<String> strings, int n) {
		map.clear();
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

	@Override
	public void prepareKeyboard(List<String> strings) {
		map.clear();
		if (strings.size() != 0) {
			int linha = 0;
			int item = 0;

			int biggerString = Utils.biggerString(strings);

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

	public TelegramBot getBot() {
		return bot;
	}

	public void setBot(TelegramBot bot) {
		this.bot = bot;
	}

	public Acesso getAccess() {
		return access;
	}

	public void setAccess(Acesso access) {
		this.access = access;
	}

	@Override
	public void sendMessageWithoutKeyboard(String usuario, String mensagem) {
		bot.execute(new SendMessage(usuario, mensagem));
	}

}
