package unb.controlador;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.*;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

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
			String[] entradas = entradaTraduzida.split(" ");

			if(entradas[0].equals("a")){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
				String[] log = new String[5];
				log[0] = entradas[1];
				log[1] = sdf.format(Calendar.getInstance().getTimeInMillis());
				File arq = conexao.buscarArquivo(entradas[1]);
				log[3] = Long.toString(arq.length());
				byte [] mybytearray  = new byte [(int)arq.length()];
				FileInputStream fis = new FileInputStream(arq);
				BufferedInputStream bis = new BufferedInputStream(fis);
				bis.read(mybytearray,0,mybytearray.length);
				OutputStream saida = cliente.getOutputStream();
				saida.write(mybytearray,0,mybytearray.length);
				saida.flush();
				log[4] = getHMAC(mybytearray);
				log[2] = sdf.format(Calendar.getInstance().getTimeInMillis());
				conexao.salvarLog(log);
				saida.close();
			}else if(entradas[0].equals("b")){
				String payload = conexao.buscarLog(entradas[1]);
				OutputStream saida = cliente.getOutputStream();
				saida.write(payload.getBytes(Charset.forName("UTF-8")));
				saida.flush();
				saida.close();
			}
			entrada.close();
			cliente.close();
		}catch(Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	public String getHMAC(byte [] mybytearray){
		SecretKeySpec key = new SecretKeySpec(BigInteger.valueOf(conexao.getId()).toByteArray(), "HmacMD5");
        Mac mac;
        byte[] bytes;
        String sEncodedString = null;
		try {
			mac = Mac.getInstance("HmacMD5");
			mac.init(key);
			
			bytes = mac.doFinal(mybytearray);
            StringBuffer hash = new StringBuffer();

            for (int i=0; i<bytes.length; i++) {
                String hex = Integer.toHexString(0xFF &  bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            sEncodedString = hash.toString();
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
		}
        return sEncodedString;
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
