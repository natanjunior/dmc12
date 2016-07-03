package unb.controlador;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
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
	
	public String comando(String payload, String feedback){	// mudar tudo isso
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
		case "8":
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

	public void restaurar(String payload, String endereco) {
		try{
			cliente = new Socket("127.0.0.1",2016);
			OutputStream saida = cliente.getOutputStream();
			saida.write(payload.getBytes(Charset.forName("UTF-8")));
			saida.flush();
			
			byte[] mybytearray = new byte[6022386];
			InputStream entrada = cliente.getInputStream();
			FileOutputStream fos = new FileOutputStream(endereco+".tar.gz");
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			int bytesRead = entrada.read(mybytearray, 0, mybytearray.length);
			int current = bytesRead;
			do {
				bytesRead = entrada.read(mybytearray, current, (mybytearray.length - current));
				if (bytesRead >= 0)
					current += bytesRead;
			} while (bytesRead > -1);
			bos.write(mybytearray, 0, current);
			bos.close();
			
			saida.close();
			cliente.close();
		}catch(Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}	
}
