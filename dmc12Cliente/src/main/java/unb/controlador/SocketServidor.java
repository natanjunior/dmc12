package unb.controlador;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
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
	
	public void run() {
		try{
			System.out.println("Servidor conectado...");
			InputStream entrada = this.cliente.getInputStream();
			String entradaTraduzida = lexer(entrada);
//			String payload = comando(entradaTraduzida);

			File arq = conexao.buscarArquivo(entradaTraduzida);
			byte [] mybytearray  = new byte [(int)arq.length()];
			FileInputStream fis = new FileInputStream(arq);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(mybytearray,0,mybytearray.length);
			OutputStream saida = cliente.getOutputStream();
			saida.write(mybytearray,0,mybytearray.length);
			saida.flush();
			saida.close();
			
			entrada.close();
			cliente.close();
		}catch(Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
//	public String comando(String payload){
//		String[] comandos = payload.split(" ");
//		String retorno = null;
//		switch(comandos[0]){
//		case "a":
//			if(comandos.length==2){
//				File arq = conexao.buscarArquivo(comandos[1]);
//			}
//			break;
//		}
//		
//		return retorno;
//	}
}
