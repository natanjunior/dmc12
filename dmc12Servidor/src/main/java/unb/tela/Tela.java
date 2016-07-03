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
}
