package unb;

import unb.controlador.Servidor;

public class Fachada {
	private static Fachada instancia;
	private Servidor servidor;
	
	public static Fachada obterInstancia(){
		if(instancia == null){
			instancia = new Fachada();
		}
		return instancia;
	}
	
	private Fachada(){
		servidor = new Servidor();
	}

	public void iniciarServidor() {
		servidor.escutar();
	}

}
