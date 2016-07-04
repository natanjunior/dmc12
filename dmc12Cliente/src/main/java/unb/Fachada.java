// FACHADA DE CLIENTE

package unb;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.*;
import java.net.Socket;
import java.security.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

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
	private Boolean conectado = false;
	
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
		tela.inicial();
		porta = conexao.getPorta();
//		entrar("natan", "123");
//		agendar(" /Users/natan.junior/teste 01/07/2016 10:40:10");
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
		String retorno = conexao.enviarMsg("2 "+cliente.getId()+msg);
		atualizar();
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
	
	public File buscarArquivo(String diretorio) {
		return backup.comprimir(diretorio, Integer.toString(cliente.getId()));
	}

	public int getId() {
		return cliente.getId();
	}

	public String[] listar() {	// falta validação
		String[] retorno = null;
		String r = conexao.enviarMsg("3 "+cliente.getId());
		if(r!=null)
			retorno = r.split(" ");
		return retorno;
	}

	public void excluirAgendamento(String id) {	// falta validação - mandar id
		conexao.enviarMsg("4 "+id);
		atualizar();
	}
	
	public void cancelarAgendamento(String id) {	// falta validação - mandar id
		conexao.enviarMsg("5 "+id);
		atualizar();
	}
	
	public void restaurarAgendamento(String id, String endereco) {	// falta validação - mandar id
		conexao.restaurar(id, endereco);
		String log = conexao.enviarMsg("8 "+cliente.getId()+"/"+id+".tar.gz");
		String entradas[] = log.split(" ");
		System.out.println(entradas[4]);
		if(entradas[4].equals(getHMAC(cliente.getId(), endereco))){
			System.out.println("Certo");
		}else{
			System.out.println("Errado");
		}
	}
	
	public void editarAgendamento(String msg) {
		conexao.enviarMsg("7 "+msg);
		atualizar();
	}
	
	public void atualizar(){
		tela.principal();
	}
	
	public String getHMAC(int c, String end){
		System.out.println("c "+c+" a "+c);
		SecretKeySpec key = new SecretKeySpec(BigInteger.valueOf(c).toByteArray(), "HmacMD5");
        Mac mac;
        byte[] bytes;
        String sEncodedString = null;
		try {
			mac = Mac.getInstance("HmacMD5");
			mac.init(key);
			
			File arq = new File(end+".tar.gz");
	        byte [] mybytearray  = new byte [(int)arq.length()];
			FileInputStream fis = new FileInputStream(arq);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(mybytearray,0,mybytearray.length);
			
			bytes = mac.doFinal(mybytearray);
            StringBuffer hash = new StringBuffer();

            for (int i=0; i<bytes.length; i++) {
                String hex = Integer.toHexString(0xFF &  bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            sEncodedString = hash.toString();
		} catch (NoSuchAlgorithmException | InvalidKeyException | IOException e) {
			e.printStackTrace();
		}
        return sEncodedString;
	}

	public void setarPorta(int porta) {
		conexao.setPorta(porta);
		Thread t = new Thread(conexao);
		t.start();
	}
}