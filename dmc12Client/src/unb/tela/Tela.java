package unb.tela;

import javax.swing.*;
import unb.*;

public class Tela extends JFrame {
	private Fachada fachada;
	private Login login;
	
	public Tela(Fachada f) {
		super("DMC12 - 1.21 gigawatts!");
		
		this.fachada = f;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.validate();
		
		login = new Login(this);		
	}
	
	public void inicial(){
		this.add(login);
		this.setVisible(true);
	}

	public void entrar(String chave) {
		fachada.entrar(chave);
	}

}
