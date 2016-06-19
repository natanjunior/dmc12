package unb.controlador;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import unb.Fachada;

public class Conexao implements Runnable{
	private Fachada fachada;
	private SocketCliente sktCliente;
	private SocketServidor sktServidor;
	private ServerSocket servidor;
	private Socket cliente;
	private int porta;
	
	public Conexao(Fachada f) {
		this.fachada = f;
		sktCliente = new SocketCliente(this);
		sktServidor = new SocketServidor(this);
		porta = sktServidor.getPorta();
	}
	
	public int getPorta(){
		return porta;
	}
	
	@Override
	public void run() {
		System.out.println("Escutando Cliente");
		try{
			while(true) {
				Socket cliente = servidor.accept();
				SocketServidor tratamento = new SocketServidor(this, cliente);
				Thread t = new Thread(tratamento);
				t.start();
			}
			
		}catch(Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	public String enviarMsg(String payload) {
		return sktCliente.enviarMsg(payload);
	}
}
