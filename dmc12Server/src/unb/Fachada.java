// FACHADA DE SERVIDOR

package unb;

import unb.controlador.*;
import unb.tela.Tela;

public class Fachada {
	private static Fachada instancia;
	private Conexao conexao;
	private Banco banco;
	private Tela tela;
	
	public static Fachada obterInstancia(){
		if(instancia == null){
			instancia = new Fachada();
		}
		return instancia;
	}
	
	private Fachada(){
		tela = new Tela(this);
		conexao = new Conexao(this);
		banco = new Banco(this);
	}

	public void init() {
		banco.carregaUsuarios();
		tela.inicial();
		conexao.escutar();
	}

	public int cadastrarCliente(String endereco, String nome, String chave) {
		Cliente c = new Cliente(nome, chave, endereco);
		int id = banco.addCliente(c);
		return id;
	}
	
	public int loginCliente(String nome, String chave) {
		Cliente c = banco.buscaCliente(nome);
		int id=-1;
		if(c!=null){
			if(c.getChave().equals(chave))
				id = c.getId();
		}
		return id;
	}

}
