// FACHADA DE CLIENTE

package unb;

import java.io.IOException;
import java.math.*;
import java.net.Socket;
import java.security.*;
import unb.controlador.*;
import unb.tela.*;

public class Fachada {
	private static Fachada instancia;
	private SocketServidor socketServidor;
	private SocketCliente socketCliente;
	private Cliente cliente;
	private Tela tela;
	private Backup backup;
	private int porta;
	private Socket socket;
	
	public static Fachada obterInstancia(){
		if(instancia == null){
			instancia = new Fachada();
		}
		return instancia;
	}
	
	private Fachada(){
		tela = new Tela(this);
		socketCliente = new SocketCliente(this);
		socketServidor = new SocketServidor(this);
		backup = new Backup(this);
		cliente = new Cliente();
	}

	public void init() {
		tela.inicial();
//		tela.principal();
		Thread t = new Thread(socketServidor);
		t.start();
		porta = socketServidor.getPorta();
		entrar("junior", "123");
		agendar(" /Users/natan.junior/teste 18/06/2016 03:06:00");
	}
		
	public void entrar(String nome, String senha) {
		String chave = pegarHash(nome+senha);
		int id = Integer.parseInt(socketCliente.enviarMsg("0 "+nome+" "+chave));
		if(id>0){
			cliente.setNome(nome);
			cliente.setId(id);
			cliente.setChave(chave);
			tela.principal();
		}
	}
	
	public void cadastrar(String nome, String senha){
		String chave = pegarHash(nome+senha);
		int id = Integer.parseInt(socketCliente.enviarMsg("1 "+nome+" "+chave));
		if(id>0){
			cliente.setNome(nome);
			cliente.setId(id);
			cliente.setChave(chave);
			tela.principal();
		}else{
			
		}
	}
	
	public void agendar(String msg) {
		socketCliente.enviarMsg("2 "+cliente.getId()+msg);
	}
	
	private String pegarHash(String palavra){
		MessageDigest m = null;
		try {
			m=MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BigInteger hash = new BigInteger(1,m.digest(palavra.getBytes()));
		return hash.toString(16);
	}

	public String nomeCliente() {
		return cliente.getNome();
	}
	
	public void comprimir(String diretorio){
		backup.comprimir(diretorio);
	}

}