package unb.controlador;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.Charset;

import unb.Fachada;

public class SocketCliente implements Runnable {
	private Conexao conexao;
	private Socket cliente;

	public SocketCliente(Conexao c) {
		this.conexao = c;
	}

	public void enviarMsg(Cliente c, String payload) {
		try {
			cliente = new Socket(c.getEndereco(), c.getPorta());
			OutputStream saida = cliente.getOutputStream();
			saida.write(payload.getBytes(Charset.forName("UTF-8")));
			saida.flush();

			byte[] mybytearray = new byte[6022386];
			InputStream entrada = cliente.getInputStream();
			FileOutputStream fos = new FileOutputStream("teste.tar.gz");
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
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	public String lexer(InputStream entrada) {
		byte[] messageByte = new byte[1000];
		String messageString = "";
		int bytesRead;
		try {
			bytesRead = entrada.read(messageByte);
			messageString = new String(messageByte, 0, bytesRead);
			;
		} catch (IOException e) {
			e.printStackTrace();
			messageString = null;
		}
		return messageString;
	}

	@Override
	public void run() {

	}
}
