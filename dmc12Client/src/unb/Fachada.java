package unb;

import unb.controlador.Conexao;

public class Fachada {
	private static Fachada instancia;
	private Conexao cliente;
	
	public static Fachada obterInstancia(){
		if(instancia == null){
			instancia = new Fachada();
		}
		return instancia;
	}
	
	private Fachada(){
		cliente = new Conexao();
	}

	public void iniciarServidor() {
		cliente.enviarMsg();
	}

}