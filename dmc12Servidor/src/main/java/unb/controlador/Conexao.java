// CONEXAO SERVIDOR

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
	
	public Conexao(Fachada f) {
		this.fachada = f;
		sktCliente = new SocketCliente(this);
		sktServidor = new SocketServidor(this);
		try {
			servidor = new ServerSocket(2016);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int cadastrarCliente(String nome, String chave, String endereco, int porta) {
		return fachada.cadastrarCliente(nome, chave, endereco, porta);
	}
	
	public int loginCliente(String nome, String chave, String endereco, int porta) {
		return fachada.loginCliente(nome, chave, endereco, porta);
	}

	public int agendar(String idCliente, String arquivo, String data, String hora) {
		return fachada.agendar(idCliente, arquivo, data, hora);
	}
	
	@Override
	public void run() {
		System.out.println("SERVIDOR - escutando...");
		try{
			while(true) {
				Socket cliente = servidor.accept();
				SocketServidor tratamento = new SocketServidor(this, cliente);
				Thread t = new Thread(tratamento);
				t.start();
			}
			
		}catch(Exception e) {
			System.out.println("Conexao servidor - Erro: " + e.getMessage());
		}
	}

	public void atualizar() {
		fachada.atualizar();
	}

	public String enviarMsg(Cliente c, String payload) {
		return sktCliente.enviarMsg(c, payload);
	}
	
	public void enviarMsg(Cliente c, Agendamento a) {
		sktCliente.enviarMsg(c, a);
	}

	public String listar(String cliente) {
		return fachada.listarAgendamento(Integer.parseInt(cliente));
	}

	public String excluirAgendamento(String id) {
		return fachada.excluirAgendamento(id);
	}

	public String cancelarAgendamento(String id) {
		return fachada.cancelarAgendamento(id);
	}

	public String restaurarAgendamento(String id) {
		return fachada.restaurarAgendamento(id);
	}

	public String editarAgendamento(String string) {
		return null;
	}
}
