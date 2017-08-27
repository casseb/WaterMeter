package br.com.simnetwork.model.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.simnetwork.model.entity.basico.Utils;
import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.repository.UsuarioRepository;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepo;

	public UsuarioServiceImpl() {
		super();
	}

	@Override
	@Transactional
	public Usuario validarNovoUsuario(String botId) {
		return usuarioRepo.findByBotId(botId);
	}

	@Override
	@Transactional
	public void criarUsuario(String botId, String apelido) {
		RotaService rotaService = App.getCon().getBean("rotaService", RotaService.class);
		Usuario usuario = new Usuario();
		usuario.setBotId(botId);
		apelido = Utils.firstUpper(apelido);
		usuario.setApelido(apelido);
		usuario.setLiberado(1);

		List<Rota> rotasBasicasList = rotaService.listarRotasBasicas();
		if (!(rotasBasicasList.isEmpty() || rotasBasicasList == null)) {
			Set<Rota> rotasBasicas = new HashSet<>(rotasBasicasList);
			usuario.setRotasPermitidas(rotasBasicas);
		}

		usuarioRepo.save(usuario);
	}

	@Override
	@Transactional
	public Usuario localizarUsuarioPorTelegram(String botId) {
		return usuarioRepo.findByBotId(botId);
	}

	@Override
	@Transactional
	public void atualizarUsuario(Usuario usuario) {
		usuarioRepo.save(usuario);

	}

	@Override
	@Transactional
	public Usuario localizarUsuarioPorApelido(String apelido) {
		return usuarioRepo.findByApelido(apelido);
	}

	@Override
	@Transactional
	public Usuario localizarUsuarioPorId(int id) {
		return usuarioRepo.findOne(id);
	}

	@Override
	@Transactional
	public void darPermissaoTodos(Rota rota) {

		for (Usuario usuario : usuarioRepo.findAll()) {
			darPermissao(usuario, rota);
		}

	}

	@Override
	@Transactional
	public void darPermissao(Usuario usuario, Rota rota) {
		if (!usuarioRepo.findByRotasPermitidasRotaPK(rota.getRotaPK()).contains(usuario)) {
			Set<Rota> currentRotas = new HashSet<>();
			currentRotas = usuario.getRotasPermitidas();
			currentRotas.add(rota);
			usuario.setRotasPermitidas(currentRotas);
			usuarioRepo.save(usuario);
		}
	}

}
