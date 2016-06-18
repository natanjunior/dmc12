// FACHADA DE SERVIDOR

package unb;

import java.util.ArrayList;

import unb.controlador.*;
import unb.tela.Tela;

public class Fachada {
	private static Fachada instancia;
	private SocketServidor socketServidor;
	private SocketCliente socketCliente;
	private Banco banco;
	private Tela tela;
	
	public static Fachada obterInstancia(){
		if(instancia == null){
			instancia = new Fachada();
		}
		return instancia;
	}
	
	private Fachada(){
		socketCliente = new SocketCliente(this);
		socketServidor = new SocketServidor(this);
		banco = new Banco(this);
		tela = new Tela(this);
	}

	public void init() {
		banco.carregaUsuarios();
		banco.carregaAgendamentos();
		Thread t = new Thread(socketServidor);
		t.start();
		tela.inicial();
//		conexao.fazerBackup();
	}

	public int cadastrarCliente(String nome, String chave, int porta) {
		Cliente c = new Cliente(nome, chave, porta);
		int id = banco.addCliente(c);
		return id;
	}
	
	public int loginCliente(String nome, String chave, int porta) {
		Cliente c = banco.buscaCliente(nome);
		int id=-1;
		if(c!=null){
			if(c.getChave().equals(chave)){
				id = c.getId();
				c.setPorta(porta);
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
