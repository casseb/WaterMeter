package br.com.simnetwork.old;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.request.SendMessage;

import br.com.simnetwork.model.entity.basico.rota.RouteGroup;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.service.TelegramBotImpl;


public class StartServiceImpl implements StartService{

	@Autowired
	private ScheduleMessageRepository scheduleMessageRepo;
	
	@Override
	public void triggerMessages() {
		
		Iterable<MensagemAgendada> scheduleMessages = scheduleMessageRepo.findAll();
		ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
		TelegramBotImpl bot = App.getCon().getBean("telegramBot",TelegramBotImpl.class);
		
		for (MensagemAgendada scheduleMessage : scheduleMessages) {
			schedule.schedule(() -> {
					bot.sendMessage(scheduleMessage.getPessoa(),scheduleMessage.getMessage());
					scheduleMessageRepo.delete(scheduleMessage);
				},LocalDateTime.now().until(scheduleMessage.getHora(), ChronoUnit.MILLIS),TimeUnit.MILLISECONDS);
		}
		
	}
	
}
