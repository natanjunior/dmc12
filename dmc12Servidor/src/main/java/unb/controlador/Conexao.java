// CONEXAO SERVIDOR

package unb.controlador;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import unb.Fachada;

public class Conexao implements Runnable{
	private Fachada fachada;
	private SocketCliente sktCliente;
	private SocketServidor sktServidor;
	private ServerSocket servidor;
	private Socket cliente;
	private ArrayList<String[]> logs; // isto não é daqui!
	private Semaphore semaforo;
	
	public Conexao(Fachada f) {
		this.fachada = f;
		this.logs = new ArrayList<String[]>();
	}
	
	public String init(int porta){
		String endereco = "";
		try {
			servidor = new ServerSocket(porta);
			endereco = servidor.getInetAddress().getHostAddress() + " " + porta;
			sktCliente = new SocketCliente(this);
			sktServidor = new SocketServidor(this);
			semaforo = new Semaphore(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return endereco;
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
				SocketServidor tratamento = new SocketServidor(this, cliente, semaforo);
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

	public String excluirAgendamento(String agendamento, String cliente) {
		return fachada.excluirAgendamento(agendamento, cliente);
	}

	public String cancelarAgendamento(String agendamento, String cliente) {
		return fachada.cancelarAgendamento(agendamento, cliente);
	}

	public String restaurarAgendamento(String id) {
		return fachada.restaurarAgendamento(id);
	}

	public String editarAgendamento(String cliente, String agendamento, String novaData, String novaHora) {
		return fachada.editarAgendamento(cliente, agendamento, novaData, novaHora);
	}
	
	public File buscarArquivo(String diretorio) {
		return fachada.buscarArquivo(diretorio);
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
}
