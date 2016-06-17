// FACHADA DE SERVIDOR

package unb;

import java.util.ArrayList;

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
		conexao = new Conexao(this);
		Thread t = new Thread(conexao);
		t.start();
		banco = new Banco(this);
		tela = new Tela(this);
	}

	public void init() {
		banco.carregaUsuarios();
		banco.carregaAgendamentos();
		tela.inicial();
		conexao.escutar();
//		conexao.fazerBackup();
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

	public int agendar(String idCliente, String arquivo, String data, String hora) {
		Cliente c = banco.buscaCliente(Integer.parseInt(idCliente));
		Agendamento a = new Agendamento(c, arquivo, data, hora);
		int id = banco.addAgendamento(a);
		return 0;
	}

	public ArrayList<Cliente> getClientes() {
		return banco.getClientes();
	}

	public ArrayList<Agendamento> getAgendamentos() {
		return banco.getAgendamentos();
	}

}
