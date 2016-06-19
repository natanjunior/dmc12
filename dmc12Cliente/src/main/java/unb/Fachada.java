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
	private Conexao conexao;
	private Cliente cliente;
	private Tela tela;
	private Backup backup;
	private Socket socket;
	private int porta;
	
	public static Fachada obterInstancia(){
		if(instancia == null){
			instancia = new Fachada();
		}
		return instancia;
	}
	
	private Fachada(){
		tela = new Tela(this);
		conexao = new Conexao(this);
		backup = new Backup(this);
		cliente = new Cliente();
	}

	public void init() {
		porta = conexao.getPorta();
		Thread t = new Thread(conexao);
		t.start();
		tela.inicial();
		entrar("junior", "123");
//		tela.principal();
	}
		
	public void entrar(String nome, String senha) {
		String chave = pegarHash(nome+senha);
		int id = Integer.parseInt(conexao.enviarMsg("0 "+nome+" "+chave+" "+porta));
		if(id>0){
			cliente.setNome(nome);
			cliente.setId(id);
			cliente.setChave(chave);
			tela.principal();
		}
	}
	
	public void cadastrar(String nome, String senha){
		String chave = pegarHash(nome+senha);
		int id = Integer.parseInt(conexao.enviarMsg("1 "+nome+" "+chave+" "+porta));
		if(id>0){
			cliente.setNome(nome);
			cliente.setId(id);
			cliente.setChave(chave);
			tela.principal();
		}else{
			
		}
	}
	
	public void agendar(String msg) {
		conexao.enviarMsg("2 "+cliente.getId()+msg);
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