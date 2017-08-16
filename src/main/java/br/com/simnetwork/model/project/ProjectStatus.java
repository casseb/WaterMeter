package br.com.simnetwork.model.project;

public enum ProjectStatus {
	
	IDEIA(1, "Idéia"), ITENS(2, "Itens"), RH(3, "RH"), ORCAMENTO(4, "Orçamento"), 
	PENDENTEFINANCEIRO(5,"Pendente Financeiro"), DESENVOLVIMENTO(6,"Desenvolvimento"),
	FINALIZACAO(7,"Finalização"), CONCLUIDO(8,"Concluído");
	
	public int order;
	public String description;
	
	ProjectStatus(int order,String description){
		this.order = order;
		this.description = description;
	}

	public boolean isProjectStatusValid(String messageWithoutBar) {
		for (ProjectStatus status : ProjectStatus.values()) {
			if(messageWithoutBar.equals(status.name())){
				return true;
			}
		}
		return false;
	}
	
	public ProjectStatus locateProjectStatusByString(String string){
		for (ProjectStatus status: ProjectStatus.values()) {
			if(string.equals(status.name())){
				return status;
			}
		}
		return null;
	}
	
	public ProjectStatus locateProjectStatusByDesc(String desc){
		for (ProjectStatus status: ProjectStatus.values()) {
			if(desc.equals(status.description)){
				return status;
			}
		}
		return null;
	}

}
