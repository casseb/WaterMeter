package br.com.simnetwork.model.service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.simnetwork.model.entity.basico.Utils;
import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.repository.UsuarioRepository;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepo;
	@Autowired
	private RotaService rotaService;

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
		Usuario usuario = usuarioRepo.findByBotId(botId);
		if(usuario == null) {
			usuario = new Usuario();
			usuario.setBotId(botId);
			apelido = Utils.firstUpper(apelido);
			usuario.setApelido(apelido);
			usuario.setLiberado(1);
		}else {
			usuario.setApelido(apelido);
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

	@Override
	@Transactional
	public List<Usuario> localizarTodosUsuarios() {
		List<Usuario> resultado = new LinkedList<>();
		for(Usuario usuario: usuarioRepo.findAll()) {
			resultado.add(usuario);
		}
		return resultado;
	}

	@Override
	@Transactional
	public List<Usuario> localizarUsuarioComPermissoesDisponiveis() {
		List<Usuario> resultado = new LinkedList<>();
		List<Rota> rotasMenu = rotaService.listarRotasVisiveisMenu();
		for(Usuario usuario : usuarioRepo.findAll()) {
			for(Rota rotaVisivel : rotasMenu) {
				if(!usuario.getRotasPermitidas().contains(rotaVisivel)) {
					resultado.add(usuario);
					break;
				}
			}
		}
		return resultado;
	}

	@Override
	public List<Rota> listarRotasBloqueadas(Usuario usuario) {
		List<Rota> resultado = new LinkedList<>();
		boolean tem;
		for(Rota rotaMenu : rotaService.listarRotasVisiveisMenu()) {
			tem = false;
			for(Rota rotaDisponivel : usuario.getRotasPermitidas()) {
				if(rotaMenu.getBeanName().equals(rotaDisponivel.getBeanName())){
					tem = true;
				}
			}
			if(!tem) {
				resultado.add(rotaMenu);
			}
		}
		return resultado;
	}

}
