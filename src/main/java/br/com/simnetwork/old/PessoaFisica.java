package br.com.simnetwork.old;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.simnetwork.model.entity.basico.usuario.Usuario;

@Entity
@Table(name = "Pessoa_Fisica")
public class PessoaFisica extends Usuario {
	
	@Column(name = "pef_nome_completo", length = 200, nullable = true)
	private String nomeCompleto;
	
	@Column(name = "pef_cpf", length = 11, nullable = true)
	private String cpf;
	
	@Column(name = "pef_data_nascimento", nullable = true)
	private Timestamp dataNascimento;

	public PessoaFisica() {
		super();
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDateTime getDataNascimento() {
		if (dataNascimento != null) {
			return dataNascimento.toLocalDateTime();
		}
		return null;
	}

	public void setDataNascimento(LocalDateTime dataNascimento) {
		this.dataNascimento = Timestamp.valueOf(dataNascimento);
	}
	
	
	
}
