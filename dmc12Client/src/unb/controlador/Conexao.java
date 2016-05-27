package unb.controlador;

import java.io.OutputStream;
import java.net.*;
import java.nio.charset.Charset;

import unb.Fachada;

public class Conexao {
	private Fachada fachada;
	
	public Conexao(Fachada f) {
		this.fachada = f;
	}
	
	public void enviarMsg(String payload){
		try{
			Socket cliente = new Socket("127.0.0.1",2016);
			OutputStream saida = cliente.getOutputStream();
			saida.write(payload.getBytes(Charset.forName("UTF-8")));
			saida.flush();
			saida.close();
			cliente.close();
		}catch(Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

}
