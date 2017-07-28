package objects;

import mvc.Model;

public enum CompetenciaUN {
	
	HORA("por hora"),
	UNIDADE("por unidade")
	;
	
	public String descricao;
	public Model model = new Model();
	
	CompetenciaUN(String descricao) {
		this.descricao = descricao;
	}
	
	public CompetenciaUN locateCompetenciaUNByString(String string){
		for (CompetenciaUN un : CompetenciaUN.values()) {
			if(string.equals(un.descricao)){
				return un;
			}
		}
		return null;
	}

}
