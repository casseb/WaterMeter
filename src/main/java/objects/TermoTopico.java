package objects;

import mvc.Model;

public enum TermoTopico {
	
	VALIDADE("Validade dos Termos"),
	COMPORTAMENTO("Comportamentos"),
	ENTRADA("Entrada na Iniciativa"),
	PROJETOS("Projetos"),
	CARGOAPROVADO("Cargo Aprovado"),
	PERMANENCIA("Permanência no projeto"),
	SAIDA("Saída"),
	STATUS("Status dos Projetos"),
	CARGOS("Cargos Oficiais"),
	PRODUTOS("Produtos Oficiais"),
	PUBLICO("Público Alvo Oficiais"),
	INICOIN("Inicoin")
	;
	
	public String descricao;
	
	public TermoTopico locateTermoTopicoByString(String string){
		for (TermoTopico topico : TermoTopico.values()) {
			if(string.equals(topico.descricao)){
				return topico;
			}
		}
		return null;
	}
	
	TermoTopico(String descricao) {
		this.descricao = descricao;
	}

	public int getUltimoParagrafo(Model model) {
		return model.lastParagraph(this);
	}
	
	

}
