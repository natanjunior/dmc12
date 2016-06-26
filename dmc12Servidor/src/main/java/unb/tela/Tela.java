package unb.tela;

import java.util.ArrayList;

import javax.swing.JFrame;

import unb.Fachada;
import unb.controlador.Cliente;

public class Tela extends JFrame {
	private Fachada fachada;
	private Principal principal;

	public Tela(Fachada f) {
		super("DMC12 Server - Mr. Fusion");
		
		this.fachada = f;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.validate();
	}
	
	public void inicial(){
		principal = new Principal(this, fachada);
		this.add(principal);
		this.setVisible(true);
	}

	public void atualizar() {
		principal.atualizar();
	}
}
