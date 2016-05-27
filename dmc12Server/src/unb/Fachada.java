package unb;

import unb.controlador.*;

public class Fachada {
	private static Fachada instancia;
	private Conexao conexao;
	private Banco banco;
	
	public static Fachada obterInstancia(){
		if(instancia == null){
			instancia = new Fachada();
		}
		return instancia;
	}
	
	private Fachada(){
		conexao = new Conexao(this);
		banco = new Banco(this);
	}

	public void init() {
		banco.carregaUsuarios();
//		conexao.escutar();
	}

}
