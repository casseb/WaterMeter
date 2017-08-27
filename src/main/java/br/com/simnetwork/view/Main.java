package br.com.simnetwork.view;

public class Main {
	
	public static void main(String[] args) {
		
		Start.startEnvironment();
		Start.startTelegramMethods();
		Start.carregandoMapeamentoSpring();
		Start.persistirRotas();
		System.out.println("Concluido o carregamento da aplicação");
    }
	
}
