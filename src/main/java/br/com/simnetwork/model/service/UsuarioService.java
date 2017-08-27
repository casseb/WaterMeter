package br.com.simnetwork.model.service;

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
	
}
