package objects;

import mvc.Model;

public enum CompetenciaTipo {
	
	PROGRAMACAO("Programação"),
	ARTE("Arte Gráfica"),
	VENDAS("Vendas")
	;
	
	public String descricao;
	public Model model;
	
	CompetenciaTipo(String descricao) {
		this.descricao = descricao;
	}
	
	public CompetenciaTipo locateCompetenciaTipoByString(String string, Model model){
		for (CompetenciaTipo tipo : CompetenciaTipo.values()) {
			if(string.equals(tipo.descricao)){
				return tipo;
			}
		}
		return null;
	}

}
