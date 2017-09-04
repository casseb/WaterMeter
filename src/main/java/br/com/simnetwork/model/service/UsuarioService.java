package br.com.simnetwork.model.service;

import java.util.List;

import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;

public interface UsuarioService {
	
	public Usuario validarNovoUsuario(String botId);
	
	public void criarUsuario(String botId, String apelido);
	
	public Usuario localizarUsuarioPorTelegram(String botId);
	
	public void atualizarUsuario(Usuario usuario);
	
	public Usuario localizarUsuarioPorApelido(String apelido);

	public Usuario localizarUsuarioPorId(int id);
	
	public void darPermissaoTodos(Rota rota);
	
	public void darPermissao(Usuario usuario, Rota rota);
	
	public void darPermissaoGrupo(Usuario usuario, String grupoRota);
	
	public void removerPermissao(Usuario usuario, Rota rota);
	
	public void removerPermissaoGrupo(Usuario usuario, String grupoRota);
	
	public List<Usuario> localizarTodosUsuarios();

	public List<Usuario> localizarUsuarioComPermissoesDisponiveis();
	
	public List<Rota> listarRotasBloqueadas(Usuario usuario);
}
