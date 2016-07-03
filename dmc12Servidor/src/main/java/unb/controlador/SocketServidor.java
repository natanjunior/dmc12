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
	private Socket cliente;
	private Boolean enviarArq = false;
	
	public SocketServidor(Conexao c) {
		this.conexao = c;
	}
	
	public SocketServidor(Conexao cn, Socket c) {
		this.conexao = cn;
		this.cliente = c;
	}
	
	public String comando(String payload){
		System.out.println(payload);
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
		case "3":
			retorno = conexao.listar(comandos[1]);
			break;
		case "4":
			retorno = conexao.excluirAgendamento(comandos[1]);
			break;
		case "5":
			retorno = conexao.cancelarAgendamento(comandos[1]);
			break;
		case "6":
			retorno = conexao.restaurarAgendamento(comandos[1]);
			if(retorno != null)
				enviarArq = true;
			break;
		case "7":
			retorno = conexao.editarAgendamento(comandos[1]);
			break;
		case "8":
			retorno = conexao.buscarLog(System.getProperty("user.home")+"/dmc/"+comandos[1]);
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
			
			if(!enviarArq){
				OutputStream saida = cliente.getOutputStream();
				saida.write(payload.getBytes(Charset.forName("UTF-8")));
				saida.flush();
				saida.close();
			}else{
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
				String[] log = new String[5];
				String[] entradas = payload.split(" ");
				log[0] = entradas[0];
				log[1] = sdf.format(Calendar.getInstance().getTimeInMillis());
				File arq = conexao.buscarArquivo(entradas[0]);
				log[3] = Long.toString(arq.length());
				byte [] mybytearray  = new byte [(int)arq.length()];
				FileInputStream fis = new FileInputStream(arq);
				BufferedInputStream bis = new BufferedInputStream(fis);
				bis.read(mybytearray,0,mybytearray.length);
				OutputStream saida = cliente.getOutputStream();
				saida.write(mybytearray,0,mybytearray.length);
				saida.flush();
				log[4] = getHMAC(mybytearray, entradas[1]);
				log[2] = sdf.format(Calendar.getInstance().getTimeInMillis());
				conexao.salvarLog(log);
				saida.close();
			}
			
			entrada.close();
			cliente.close();
			conexao.atualizar();
		}catch(Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	public String getHMAC(byte [] mybytearray, String cliente){
		System.out.println("c "+cliente+" a "+cliente);
		int c = Integer.parseInt(cliente);
		SecretKeySpec key = new SecretKeySpec(BigInteger.valueOf(c).toByteArray(), "HmacMD5");
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
}
