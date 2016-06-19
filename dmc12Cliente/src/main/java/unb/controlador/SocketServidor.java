package unb.controlador;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.Charset;

import unb.Fachada;

public class SocketServidor implements Runnable{
	private Conexao conexao;
	private ServerSocket servidor;
	private Socket cliente;
	private int porta;

	public SocketServidor(Conexao c) {
		this.conexao = c;
		try {
			servidor = new ServerSocket(0);
			this.porta = servidor.getLocalPort();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SocketServidor(Conexao cn, Socket c) {
		this.conexao = cn;
		this.cliente = c;
	}
		
	public String lexer(InputStream entrada){
		byte[] messageByte = new byte[1000];
	    String messageString = "";
	    int bytesRead;
		try {
			bytesRead = entrada.read(messageByte);
			messageString = new String(messageByte, 0, bytesRead);
		} catch (IOException e) {
			e.printStackTrace();
			messageString = null;
		}
	    return messageString;
	}
	
	public int getPorta(){
		return porta;
	}
	
	public String comando(String endereco, String payload){
		String[] comandos = payload.split(" ");
		String retorno = null;
				
		return retorno;
	}

	public void run() {
		try{
			InputStream entrada = this.cliente.getInputStream();
			String payload = this.comando(cliente.getInetAddress().getHostAddress(), lexer(entrada));
		    
			OutputStream saida = cliente.getOutputStream();
			saida.write(payload.getBytes(Charset.forName("UTF-8")));
			saida.flush();
			saida.close();
			
			entrada.close();
			cliente.close();
		}catch(Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}
