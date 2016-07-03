package unb.controlador;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.Charset;

import unb.Fachada;

public class SocketCliente{
	private Conexao conexao;
	private Socket cliente;
	private int porta;

	public SocketCliente(Conexao c) {
		this.conexao = c;
	}

	public String enviarMsg(String payload){
		String retorno = null;
		try{
			cliente = new Socket("127.0.0.1",2016);
			OutputStream saida = cliente.getOutputStream();
			saida.write(payload.getBytes(Charset.forName("UTF-8")));
			saida.flush();
			
			InputStream entrada = cliente.getInputStream();
			retorno = this.comando(payload, lexer(entrada));
			
			saida.close();
			entrada.close();
			cliente.close();
		}catch(Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return retorno;
	}
	
	public String comando(String payload, String feedback){	// mudar isso
		String[] comandos = payload.split(" ");
		String retorno = null;
		
//		System.out.println("retorno " + feedback);
		
		switch(comandos[0]){
		case "0":
			retorno = feedback;
			break;
		case "1":
			retorno = feedback;
			break;
		case "3":
			retorno = feedback;
			break;
		}
		
		return retorno;
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
}
