package unb.controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import unb.Fachada;
import unb.model.*;

public class Banco {
	private Fachada fachada;
	private RepositorioClientes rpClientes;
	private RepositorioAgendamentos rpAgendamentos;
	private RepositorioBackups rpBackups;
	private File arqClientes, arqAgendamentos;
	private int newClienteId, newAgendamentoId = 1;
	private XStream xstream;
	private String pastaHome;

	public Banco(Fachada f) {
		this.fachada = f;

		this.rpClientes = new RepositorioClientes(this);
		this.rpAgendamentos = new RepositorioAgendamentos();
		this.rpBackups = new RepositorioBackups(this);
		this.xstream = new XStream(new DomDriver());
		xstream.processAnnotations(RepositorioAgendamentos.class);
		
		pastaHome = System.getProperty("user.home")+"/dmc/";
		new File(pastaHome).mkdir();
	}

	public void carregaUsuarios() {
		this.newClienteId = 1313; // base dos id's
		arqClientes = new File(pastaHome+"clientes.xml");
		if(!arqClientes.exists()){
			try {
				arqClientes.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				BufferedReader leitor = new BufferedReader(new FileReader(arqClientes));
				while(true){
					String linha = leitor.readLine();
					if(linha!=null){
						for(int i=1;i<=6;i++){
							linha += leitor.readLine();
						}
						rpClientes.add(lerClienteXML(linha));
					}else{
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void carregaAgendamentos() {
		this.newAgendamentoId = 1; // base dos id's
		arqAgendamentos = new File(pastaHome+"agendamentos.xml");
		if(!arqAgendamentos.exists()){
			try {
				arqAgendamentos.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				FileReader reader = new FileReader(arqAgendamentos);
				this.rpAgendamentos = (RepositorioAgendamentos) xstream.fromXML(reader);
				for (Agendamento a : rpAgendamentos.getAgendamentos()) {
					if(a.getEstado()==0){
						Backup b = new Backup(a, fachada);
						addBackup(b);
					}
				}
				this.newAgendamentoId += rpAgendamentos.size();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public int addCliente(Cliente c) {
		int id = rpClientes.add(c);
		int porta = c.getPorta();
		String end = c.getEndereco();
		if(id>0){
			xstream.alias("cliente", Cliente.class);
			c.setEndereco("-", 0);
			String xml = xstream.toXML(c);
			escreverArq(arqClientes, xml);
			new File(pastaHome+id).mkdir();
			c.setEndereco(end, porta);
		}
		return id;
	}

	public int newClienteId() {
		this.newClienteId++;
		return newClienteId;
	}
	
	public int newAgendamentoId() {
		this.newAgendamentoId++;
		return newAgendamentoId;
	}

	public void escreverArq(File arq, String txt){
		FileWriter escritor;
		try {
			escritor = new FileWriter(arq, true);
			escritor.write(txt);
			escritor.write(System.getProperty( "line.separator" ));
			escritor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void escreverArq2(File arq, String txt){
		FileWriter escritor;
		try {
			escritor = new FileWriter(arq);
			escritor.write(txt);
			escritor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Cliente lerClienteXML(String xml){
		xstream.alias("cliente", Cliente.class);
		return  (Cliente) xstream.fromXML(xml);
	}
	
	public Cliente buscaCliente(String nome) {
		return rpClientes.busca(nome);
	}
	
	public Cliente buscaCliente(int id) {
		return rpClientes.busca(id);
	}

	public int addAgendamento(Agendamento a) {
		int id = rpAgendamentos.add(a, newAgendamentoId++);
		if(id>0){
			String xml = xstream.toXML(rpAgendamentos);
			escreverArq2(arqAgendamentos, xml);
		}
		return id;
	}

	public ArrayList<Cliente> getClientes() {
		return rpClientes.getClientes();
	}

	public ArrayList<Agendamento> getAgendamentos() {
		return rpAgendamentos.getAgendamentos();
	}

	public void addBackup(Backup b) {
		rpBackups.add(b);
	}

	public ArrayList<Backup> getBackups() {
		return rpBackups.getBackups();
	}

	public void alterarAgendamentos() {
		String xml = xstream.toXML(rpAgendamentos);
		escreverArq2(arqAgendamentos, xml);
	}

	public ArrayList<Agendamento> buscaAgendamentos(Cliente c) { // deveria ser "busca"
		return rpAgendamentos.getAgendamentos(c);
	}

	public Agendamento getAgendamento(int id) { // deveria ser "busca"
		return rpAgendamentos.getAgendamento(id);
	}

	public Backup getBackup(Agendamento a) {
		return rpBackups.busca(a.getId());
	}

	public void removeBackup(Backup b) {
		rpBackups.remove(b);
	}
}
