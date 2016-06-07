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
		System.out.println("das");
		this.getContentPane().removeAll();
		this.getContentPane().add(principal);
		this.revalidate();
	}

	public void agendar(String msg) {
		fachada.agendar(msg);
	}

}
