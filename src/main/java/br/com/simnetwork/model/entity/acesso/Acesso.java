package br.com.simnetwork.model.entity.acesso;

public class Acesso {
	
	private String tokenTelegram;
	private String adminTelegram;
	private String boxUsername;
	private String boxIdUsername;
	
	public Acesso() {
		super();
	}
	
	public void setTokenTelegram(String tokenTelegram) {
		this.tokenTelegram = tokenTelegram;
	}


	public void setAdminTelegram(String adminTelegram) {
		this.adminTelegram = adminTelegram;
	}


	public void setBoxUsername(String boxUsername) {
		this.boxUsername = boxUsername;
	}


	public void setBoxIdUsername(String boxIdUsername) {
		this.boxIdUsername = boxIdUsername;
	}

	public String getTokenTelegram() {
		return tokenTelegram;
	}


	public String getAdminTelegram() {
		return adminTelegram;
	}


	public String getBoxUsername() {
		return boxUsername;
	}


	public String getBoxIdUsername() {
		return boxIdUsername;
	}
	
	
	
	
}
