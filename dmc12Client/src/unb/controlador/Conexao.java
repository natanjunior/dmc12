package unb.controlador;

import java.net.*;

public class Conexao {
	public Conexao() {
		
	}
	
	public void enviarMsg(){
		try{
			Socket cliente = new Socket("127.0.0.1",2016);
			cliente.close();
		}catch(Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

}
