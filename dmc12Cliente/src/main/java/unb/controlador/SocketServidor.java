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
	private int porta;

	public SocketServidor(Fachada f) {
		this.fachada = f;
		try {
			servidor = new ServerSocket(0);
			this.porta = servidor.getLocalPort();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SocketServidor(Fachada f, Socket c) {
		this.fachada = f;
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

	public void run() {
		System.out.println("Escutando");
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
