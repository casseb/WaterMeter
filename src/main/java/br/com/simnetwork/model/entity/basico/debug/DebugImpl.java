package br.com.simnetwork.model.entity.basico.debug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.acesso.Acesso;
import br.com.simnetwork.model.entity.framework.Configuracao;
import br.com.simnetwork.model.service.Bot;

@Service("debug")
public class DebugImpl implements Debug {

	@Autowired
	private Bot bot;
	@Autowired
	private Configuracao config;
	@Autowired
	private Acesso acesso;
	@SuppressWarnings("unused")
	private String message;
	
	
	public DebugImpl() {
		super();
	}

	public Bot getBot() {
		return bot;
	}

	public void setBot(Bot bot) {
		this.bot = bot;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
		if(config.isDebug()) {
			bot.sendMessageWithoutKeyboard(acesso.getAdminTelegram(), message);
		}
		
	}
	
	

}
