package unb.controlador;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.Charset;

import unb.Fachada;

public class SocketServidor implements Runnable{
	private Fachada fachada;
	private ServerSocket servidor;
	private Socket cliente;
	
	public SocketServidor(Fachada f) {
		this.fachada = f;
		try {
			servidor = new ServerSocket(2016);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SocketServidor(Fachada f, Socket c) {
		this.fachada = f;
		this.cliente = c;
	}
	
	public String comando(String endereco, String payload){
		String[] comandos = payload.split(" ");
		String retorno = null;
		
		switch(comandos[0]){
		case "0":
			if(comandos.length==4) //	fazer validação
				retorno = Integer.toString(fachada.loginCliente(comandos[1], comandos[2], Integer.parseInt(comandos[3])));
			break;
		case "1":
			if(comandos.length==4) //	fazer validação
				retorno = Integer.toString(fachada.cadastrarCliente(comandos[1], comandos[2], Integer.parseInt(comandos[3])));		
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
		System.out.println("Escutando Servidor");
		try{
			while(true) {
				Socket cliente = servidor.accept();
				
				SocketServidor tratamento = new SocketServidor(fachada, cliente);
				Thread t = new Thread(tratamento);
				t.start();
			}
			
		}catch(Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}
