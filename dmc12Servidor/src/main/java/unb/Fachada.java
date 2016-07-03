// FACHADA DE SERVIDOR

package unb;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Timer;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import unb.controlador.*;
import unb.tela.Tela;

public class Fachada {
	private static Fachada instancia;
	private Conexao conexao;
	private Banco banco;
	private Tela tela;
	private Timer timer;
	
	public static Fachada obterInstancia(){
		if(instancia == null){
			instancia = new Fachada();
		}
		return instancia;
	}
	
	private Fachada(){
		conexao = new Conexao(this);
		banco = new Banco(this);
		tela = new Tela(this);
		timer = new Timer();
	}

	public void init() {
		banco.carregaUsuarios();
		banco.carregaAgendamentos();
		Thread t = new Thread(conexao);
		t.start();
		tela.inicial();
		setarBackups();
	}

	public int cadastrarCliente(String nome, String chave, String endereco, int porta) {
		Cliente c = new Cliente(nome, chave);
		c.setEndereco(endereco, porta);
		int id = banco.addCliente(c);
		return id;
	}
	
	public int loginCliente(String nome, String chave, String endereco, int porta) {
		Cliente c = banco.buscaCliente(nome);
		int id=-1;
		if(c!=null){
			if(c.getChave().equals(chave)){
				id = c.getId();
				c.setEndereco(endereco, porta);
			}
		}
		return id;
	}

	public int agendar(String idCliente, String arquivo, String data, String hora) {
		Cliente c = banco.buscaCliente(Integer.parseInt(idCliente));
		Agendamento a = new Agendamento(c, arquivo, data, hora);
		int id = banco.addAgendamento(a);
		Backup b = new Backup(a, this);
		banco.addBackup(b);
		setarBackup(b);
		return id;
	}
	
	public void fazerBackup(){
	    
	}
	
	public ArrayList<Cliente> getClientes() {
		return banco.getClientes();
	}

	public ArrayList<Agendamento> getAgendamentos() {
		return banco.getAgendamentos();
	}

	public void atualizar() {
		tela.atualizar();
	}
	
	public void setarBackups(){
		for (Backup b : banco.getBackups()) {
			if(b.getEstado() != 0)
				continue;
			setarBackup(b);
		}
	}
	
	public void setarBackup(Backup b){
		long tempo = b.getTimer();
		if(tempo>0){
			timer.scheduleAtFixedRate(b, tempo, 1000);
			b.setEstado(1);
		}else{
			b.setEstado(-1);
		}
	}

	public Cliente buscaCliente(int porta) {
		return banco.buscaCliente(porta);
	}

	public void buscarArquivo(Agendamento agendamento) {
		Cliente c = banco.buscaCliente(agendamento.getCliente().getId());	// trocar
		conexao.enviarMsg(c, agendamento);
		agendamento.setEstado(1);
		String log = conexao.enviarMsg(c, "b "+agendamento.getArquivo());
		String entradas[] = log.split(" ");
		if(entradas[4].equals(getHMAC(c.getId(), agendamento.getId()))){
			agendamento.setEstado(2);
		}
		banco.alterarAgendamentos();
	}
	
	public String getHMAC(int c, int a){
		SecretKeySpec key = new SecretKeySpec(BigInteger.valueOf(c).toByteArray(), "HmacMD5");
        Mac mac;
        byte[] bytes;
        String sEncodedString = null;
		try {
			mac = Mac.getInstance("HmacMD5");
			mac.init(key);
			
			File arq = new File(System.getProperty("user.home")+"/dmc/"+c+"/"+a+".tar.gz");
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

	public String listarAgendamento(int id) {
		StringBuilder sb = new StringBuilder();
		Cliente cliente = banco.buscaCliente(id);
		for (Agendamento a : banco.getAgendamentos(cliente)) {
			sb.append(a.getId()).append(" ").append(a.getArquivo()).append(" ").append(a.getData()).append(" ").append(a.getEstado()).append(" ");
		}
		return sb.toString();
	}

	public String excluirAgendamento(String id) {
		Agendamento a = banco.getAgendamento(Integer.parseInt(id));
		if(a==null)
			return "-1";
		if(a.getEstado()!=2)	// só por segurança
			return "-2";
		
		return null;
	}

	public String cancelarAgendamento(String id) {
		Agendamento a = banco.getAgendamento(Integer.parseInt(id));
		if(a==null)
			return "-1";
		if(a.getEstado()==2)
			return "-2";
		a.setEstado(-1);
		Backup b = banco.getBackup(a);
		b.setEstado(-1);
		banco.removeBackup(b);
		banco.alterarAgendamentos();
		return Integer.toString(a.getId());
	}

	public String restaurarAgendamento(String id) {	// falta validação
		Agendamento agendamento = banco.getAgendamento(Integer.parseInt(id));
		String a = Integer.toString(agendamento.getId());
		String c = Integer.toString(agendamento.getCliente().getId());
		String arq = System.getProperty("user.home")+"/dmc/"+c+"/"+a+".tar.gz";
		return arq+" "+c;
	}
	
	public File buscarArquivo(String arquivo) {
		File retorno = new File(arquivo);
		if(!retorno.exists())
			return null;
		return retorno;
	}

}
