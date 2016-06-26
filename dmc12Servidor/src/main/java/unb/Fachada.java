// FACHADA DE SERVIDOR

package unb;

import java.util.ArrayList;
import java.util.Timer;
import unb.controlador.*;
import unb.tela.Tela;

public class Fachada {
	private static Fachada instancia;
	private Conexao conexao;
	private Banco banco;
	private Tela tela;
	private Timer timer;
	
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
		timer = new Timer();
	}

	public void init() {
		banco.carregaUsuarios();
		banco.carregaAgendamentos();
		Thread t = new Thread(conexao);
		t.start();
		tela.inicial();
		setarBackups();
//		fazerBackup();
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
		Backup b = new Backup(a, this);
		banco.addBackup(b);
		setarBackup(b);
		return id;
	}
	
	public void fazerBackup(){
	    
	}
	
	public ArrayList<Cliente> getClientes() {
		return banco.getClientes();
	}

	public ArrayList<Agendamento> getAgendamentos() {
		return banco.getAgendamentos();
	}

	public void atualizar() {
		tela.atualizar();
	}
	
	public void setarBackups(){
		for (Backup b : banco.getBackups()) {
			if(b.getEstado() != 0)
				continue;
			setarBackup(b);
		}
	}
	
	public void setarBackup(Backup b){
		long tempo = b.getTimer();
		if(tempo>0){
			timer.scheduleAtFixedRate(b, tempo, 1000);
			b.setEstado(1);
		}else{
			b.setEstado(-1);
		}
	}

	public Cliente buscaCliente(int porta) {
		return banco.buscaCliente(porta);
	}

	public void buscarArquivo(Agendamento agendamento) {
		Cliente c = banco.buscaCliente(agendamento.getCliente().getId());	// trocar
		conexao.enviarMsg(c, agendamento.getArquivo());
	}

}
