package unb.controlador;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.Charset;

import unb.Fachada;

public class Conexao implements Runnable{
	private Fachada fachada;
	private Socket cliente;
	
	public Conexao(Fachada f) {
		this.fachada = f;
	}
	
	public Conexao(Fachada f, Socket c) {
		this.fachada = f;
		this.cliente = c;
	}
	
	public void escutar(){
		System.out.println("Escutando");
		try{
			ServerSocket servidor = new ServerSocket(2016);
			
			while(true) {
				Socket cliente = servidor.accept();
				
				Conexao tratamento = new Conexao(fachada, cliente);
				Thread t = new Thread(tratamento);
				t.start();
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
			if(comandos.length==3) //	fazer validação
				retorno = Integer.toString(fachada.loginCliente(comandos[1], comandos[2]));
			break;
		case "1":
			if(comandos.length==3) //	fazer validação
				retorno = Integer.toString(fachada.cadastrarCliente(endereco, comandos[1], comandos[2]));		
			break;
		case "2":
			if(comandos.length==5) //	fazer validação
				retorno = Integer.toString(fachada.agendar(comandos[1], comandos[2], comandos[3], comandos[4]));		
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

	@Override
	public void run() {
		try{
			System.out.println("Cliente conectado: " + this.cliente.getInetAddress().getHostAddress() + 
					" " + this.cliente.getPort());
			InputStream entrada = this.cliente.getInputStream();
			System.out.println(entrada);
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
