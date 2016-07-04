package unb.tela;

import javax.swing.*;
import unb.*;

public class Tela extends JFrame {
	private Fachada fachada;
	private Login login;
	private Principal principal;
	
	public Tela(Fachada f) {
		super("DMC12 - 1.21 gigawatts!");
		
		this.fachada = f;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.validate();
		
		login = new Login(this);
		principal = new Principal(this);
	}
	
	public void inicial(){
		this.getContentPane().add(login);
		this.setVisible(true);
	}

	public void entrar(String nome, String senha) {
		fachada.entrar(nome, senha);
	}

	public void cadastrar(String nome, String senha) {
		fachada.cadastrar(nome, senha);
	}

	public void principal() {
		String[] agendamentos = fachada.listar();
		principal.criarLista(agendamentos);
		
		this.getContentPane().removeAll();
		this.getContentPane().add(principal);
		this.revalidate();
	}

	public void agendar(String msg) {
		fachada.agendar(msg);
	}

	public void excluirAgendamento(String id) {
		fachada.excluirAgendamento(id);
	}
	
	public void cancelarAgendamento(String id) {
		fachada.cancelarAgendamento(id);
	}
	
	public void restaurarAgendamento(String id, String endereco) {
		fachada.restaurarAgendamento(id, endereco);
	}
	
	public void editarAgendamento(String msg) {
		fachada.editarAgendamento(msg);
	}
	
	public String rotuloEstado(int e){
		String retorno = "";
		switch(e){
		case -3:
			retorno = "EXCLUIDO";
			break;
		case -1:
			retorno = "CANCELADO";
			break;
		case 0:
			retorno = "AGENDADO";
			break;
		case 1:
			retorno = "ERRO";
		case 2:
			retorno = "ARMAZENADO";
			break;
		}
		return retorno;
	}

	public void setarPorta(int porta) {
		fachada.setarPorta(porta);
	}

}
