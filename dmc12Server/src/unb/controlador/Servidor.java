package unb.controlador;

import java.net.*;

public class Servidor {
	public Servidor() {
		
	}
	
	public void escutar(){
		try{
			ServerSocket servidor = new ServerSocket(2016);
			
			while(true) {
				Socket cliente = servidor.accept();
			
				System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
			
				cliente.close();
			}
			
		}catch(Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}
