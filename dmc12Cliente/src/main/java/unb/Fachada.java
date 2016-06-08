// FACHADA DE CLIENTE

package unb;

import java.io.IOException;
import java.math.*;
import java.security.*;
import unb.controlador.*;
import unb.tela.*;

public class Fachada {
	private static Fachada instancia;
	private Conexao conexao;
	private Cliente cliente;
	private Tela tela;
	private Backup backup;
	
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
//		tela.inicial();
//		tela.principal();
		comprimir("../../teste");
	}

	public void entrar(String nome, String senha) {
		String chave = pegarHash(nome+senha);
		System.out.println(senha);
		int id = Integer.parseInt(conexao.enviarMsg("0 "+nome+" "+chave));
		if(id>0){
			cliente.setNome(nome);
			cliente.setId(id);
			cliente.setChave(chave);
			tela.principal();
		}
	}
	
	public void cadastrar(String nome, String senha){
		String chave = pegarHash(nome+senha);
		System.out.println(senha);
		int id = Integer.parseInt(conexao.enviarMsg("1 "+nome+" "+chave));
		if(id>0){
			cliente.setNome(nome);
			cliente.setId(id);
			cliente.setChave(chave);
			tela.principal();
		}else{
			
		}
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

	public void agendar(String msg) {
		conexao.enviarMsg("2 "+cliente.getId()+" "+msg);
	}
	
	public void comprimir(String diretorio){
		backup.comprimir(diretorio);
	}

}