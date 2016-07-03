// CONEXAO CLIENTE

package unb.controlador;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import unb.Fachada;

public class Conexao implements Runnable{
	private Fachada fachada;
	private SocketCliente sktCliente;
	private SocketServidor sktServidor;
	private ServerSocket servidor;
	private Socket cliente;
	private int porta;
	private ArrayList<String[]> logs; // isto não é daqui!
	
	public Conexao(Fachada f) {
		this.fachada = f;
		sktCliente = new SocketCliente(this);
		sktServidor = new SocketServidor(this);
		try {
			servidor = new ServerSocket(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		porta = servidor.getLocalPort();
		this.logs = new ArrayList<String[]>();
	}
	
	public int getPorta(){
		return porta;
	}
	
	@Override
	public void run() {
		System.out.println("CLIENTE - escutando na porta "+porta);
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

	public File buscarArquivo(String arquivo) {
		return fachada.buscarArquivo(arquivo);
	}

	public void salvarLog(String[] log) {
		this.logs.add(log);
	}

	public String buscarLog(String log) {
		for(String[] l : logs) {
			if (l[0].equals(log))
				return l[0]+" "+l[1]+" "+l[2]+" "+l[3]+" "+l[4];
		}
		return null;
	}

	public int getId() {
		return fachada.getId();
	}

	public void restaurar(String id, String endereco) {
		sktCliente.restaurar("6 "+id, endereco);
	}
}
