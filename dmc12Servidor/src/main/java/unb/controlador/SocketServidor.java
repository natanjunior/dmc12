package unb.controlador;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.Charset;

public class SocketServidor implements Runnable{
	private Conexao conexao;
	private Socket cliente;
	
	public SocketServidor(Conexao c) {
		this.conexao = c;
	}
	
	public SocketServidor(Conexao cn, Socket c) {
		this.conexao = cn;
		this.cliente = c;
	}
	
	public String comando(String payload){
		String[] comandos = payload.split(" ");
		String retorno = null;
		switch(comandos[0]){
		case "0":
			if(comandos.length==5) //	fazer validação
				retorno = Integer.toString(conexao.loginCliente(comandos[1], comandos[2], comandos[4], Integer.parseInt(comandos[3])));
			break;
		case "1":
			if(comandos.length==5) //	fazer validação
				retorno = Integer.toString(conexao.cadastrarCliente(comandos[1], comandos[2], comandos[4], Integer.parseInt(comandos[3])));		
			break;
		case "2":
			if(comandos.length==6) //	fazer validação
				retorno = Integer.toString(conexao.agendar(comandos[1], comandos[2], comandos[3], comandos[4]));		
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

	@Override
	public void run() {
		try{
			System.out.println("Cliente conectado: " + this.cliente.getInetAddress().getHostAddress() + 
					" Porta: " + this.cliente.getPort());
			InputStream entrada = this.cliente.getInputStream();
			String entradaTraduzida = lexer(entrada);
			String payload = comando(entradaTraduzida+" "+this.cliente.getInetAddress().getHostAddress());
			OutputStream saida = cliente.getOutputStream();
			saida.write(payload.getBytes(Charset.forName("UTF-8")));
			saida.flush();
			saida.close();
			
			entrada.close();
			cliente.close();
			conexao.atualizar();
		}catch(Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}
