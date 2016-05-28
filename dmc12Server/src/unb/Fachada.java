// FACHADA DE SERVIDOR

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
		conexao.escutar();
	}

	public int cadastrarCliente(String endereco, String nome, String chave) {
		Cliente c = new Cliente(nome, chave, endereco);
		int id = banco.addCliente(c);
//		if(id>0){
//			conexao.enviarMsg(Integer.toString(id));
//		}
		return id;
	}

}
