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
		banco = new Banco(this);
		tela = new Tela(this);
	}

	public void init() {
		banco.carregaUsuarios();
		banco.carregaAgendamentos();
		Thread t = new Thread(conexao);
		t.start();
		tela.inicial();
//		conexao.fazerBackup();
	}

	public int cadastrarCliente(String nome, String chave, String endereco, int porta) {
		Cliente c = new Cliente(nome, chave);
		c.setEndereco(endereco, porta);
		int id = banco.addCliente(c);
		return id;
	}
	
	public int loginCliente(String nome, String chave, String endereco, int porta) {
		Cliente c = banco.buscaCliente(nome);
		int id=-1;
		if(c!=null){
			if(c.getChave().equals(chave)){
				id = c.getId();
				c.setEndereco(endereco, porta);
			}
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
