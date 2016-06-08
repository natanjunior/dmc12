package unb.tela;

import javax.swing.JFrame;

import unb.Fachada;

public class Tela extends JFrame {
	private Fachada fachada;
	private Principal principal;

	public Tela(Fachada f) {
		super("DMC12 Server - Mr. Fusion");
		
		this.fachada = f;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.validate();
		
		principal = new Principal(this);
	}
	
	public void inicial(){
		this.add(principal);
		this.setVisible(true);
	}
}
