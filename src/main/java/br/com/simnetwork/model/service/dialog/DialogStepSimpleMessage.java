package br.com.simnetwork.model.service.dialog;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.acesso.Acesso;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.view.DialogTypeFinish;

@Service("stepSimpleMessage")
public class DialogStepSimpleMessage implements DialogStep {
	
	public String mensagemBot;
	
	@Override
	public DialogTypeFinish action(Usuario usuario, String mensagemUsuario, DialogTypeFinish dialogTypeFinish, JSONObject complement) {
		
		try {
			
			bot.sendMessage(usuario, mensagemBot);
			return DialogTypeFinish.FINALIZADOSTEP;
			
		} catch (Exception e) {
			Acesso access = App.getCon().getBean("access",Acesso.class);
			if(usuario.getApelido()!=null) {
				bot.sendMessage(access.getAdminTelegram(), "Ocorreu o seguinte erro para o usuário: "+usuario.getApelido()
				+"\n:"+e.getMessage());
			}else {
				bot.sendMessage(access.getAdminTelegram(), "Ocorreu o seguinte erro (não foi possivel saber o usuário): "+e.getMessage());
			}
			return DialogTypeFinish.ERRO;
			
		}
		
	}


	public String getMensagemBot() {
		return mensagemBot;
	}

	public void setMensagemBot(String mensagemBot) {
		this.mensagemBot = mensagemBot;
	}
	
	

}
