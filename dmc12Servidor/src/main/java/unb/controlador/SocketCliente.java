package unb.controlador;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.Charset;

import unb.Fachada;

public class SocketCliente implements Runnable{
	private Fachada fachada;
	private Socket cliente;
	
	public SocketCliente(Fachada f) {
		this.fachada = f;
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

	}

//	public void fazerBackup(Agendamento a) {
	public void fazerBackup() {
		int porta = 1000;
		try{
			cliente = new Socket("127.0.0.1",porta);
			
			OutputStream saida = cliente.getOutputStream();
//			saida.write(payload.getBytes(Charset.forName("UTF-8")));
			saida.flush();
			
			InputStream entrada = cliente.getInputStream();
//			retorno = this.comando(payload, lexer(entrada));
			
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
