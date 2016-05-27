package unb.controlador;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;

import unb.Fachada;

public class Conexao {
	private Fachada fachada;
	
	public Conexao(Fachada f) {
		this.fachada = f;
	}
	
	public void escutar(){
		try{
			ServerSocket servidor = new ServerSocket(2016);
			
			while(true) {
				Socket cliente = servidor.accept();
				System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
				InputStream entrada = cliente.getInputStream();
				
				System.out.println(lexer(entrada));
			    
				cliente.close();
			}
			
		}catch(Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	public String lexer(InputStream entrada){
		byte[] messageByte = new byte[1000];
	    String messageString = "";
	    int bytesRead;
		try {
			bytesRead = entrada.read(messageByte);
			messageString = new String(messageByte, 0, bytesRead);;
		} catch (IOException e) {
			e.printStackTrace();
			messageString = null;
		}
	    return messageString;

	}
}
