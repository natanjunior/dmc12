package unb.controlador;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.Charset;

import unb.Fachada;

public class Conexao {
	private Fachada fachada;
	
	public Conexao(Fachada f) {
		this.fachada = f;
	}
	
	public void escutar(){
		System.out.println("Escutando");
		try{
			ServerSocket servidor = new ServerSocket(2016);
			
			while(true) {
				Socket cliente = servidor.accept();
				
				System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
				InputStream entrada = cliente.getInputStream();
				String payload = this.comando(cliente.getInetAddress().getHostAddress(), lexer(entrada));
			    
				OutputStream saida = cliente.getOutputStream();
				saida.write(payload.getBytes(Charset.forName("UTF-8")));
				saida.flush();
				saida.close();
				
				entrada.close();
				cliente.close();
			}
			
		}catch(Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	public String comando(String endereco, String payload){
		String[] comandos = payload.split(" ");
		String retorno = null;
		
		switch(comandos[0]){
		case "0":
			System.out.println("login");
			break;
		case "1":
			if(comandos.length==3)
				retorno = Integer.toString(fachada.cadastrarCliente(endereco, comandos[1], comandos[2]));		
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
			messageString = new String(messageByte, 0, bytesRead);;
		} catch (IOException e) {
			e.printStackTrace();
			messageString = null;
		}
	    return messageString;
	}
	
//	public void enviarMsg(String payload){
//		try{
//			Socket cliente = new Socket("127.0.0.1",2016);
//			OutputStream saida = cliente.getOutputStream();
//			saida.write(payload.getBytes(Charset.forName("UTF-8")));
//			saida.flush();
//			saida.close();
//			cliente.close();
//		}catch(Exception e) {
//			System.out.println("Erro: " + e.getMessage());
//		}
//	}
}
