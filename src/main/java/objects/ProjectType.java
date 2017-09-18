package objects;

public enum ProjectType {
	
	MONETIZADO("Monetizado"), VOLUNTARIO("Voluntário"), SIMNETWORK("Sim Network"), STARTUP("Startup");
	
	public String description;
	
	public boolean isProjectTypeValid(String candidate){
		for (ProjectType type : ProjectType.values()) {
			if(candidate.equals(type.description)){
				return true;
			}
		}
		return false;
	}
	
	public ProjectType locateProjectTypeByString(String string){
		for (ProjectType type : ProjectType.values()) {
			if(string.equals(type.name())){
				return type;
			}
		}
		return null;
	}
	
	public ProjectType locateProjectTypeByDesc(String desc){
		for (ProjectType type : ProjectType.values()) {
			if(desc.equals(type.description)){
				return type;
			}
		}
		return null;
	}
	
	ProjectType(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
