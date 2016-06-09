package unb.controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import unb.Fachada;
import unb.model.*;

public class Banco {
	private Fachada fachada;
	private RepositorioClientes rpClientes;
	private RepositorioAgendamentos rpAgendamentos;
	private File arqLog, arqClientes, arqAgendamentos;
	private int newClienteId, newAgendamentoId;
	private XStream xstream;

	public Banco(Fachada f) {
		this.fachada = f;

		this.rpClientes = new RepositorioClientes(this);
		this.rpAgendamentos = new RepositorioAgendamentos(this);
		this.xstream = new XStream(new DomDriver());
	}

	public void carregaUsuarios() {
		this.newClienteId = 1313; // base dos id's
		arqClientes = new File("clientes.xml");
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
						for(int i=1;i<=5;i++){
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
		this.newAgendamentoId = 1313; // base dos id's
		arqAgendamentos = new File("agendamentos.xml");
		if(!arqAgendamentos.exists()){
			try {
				arqAgendamentos.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				BufferedReader leitor = new BufferedReader(new FileReader(arqAgendamentos));
				while(true){
					String linha = leitor.readLine();
					if(linha!=null){
						for(int i=1;i<=11;i++){
							linha += leitor.readLine();
						}
						rpAgendamentos.add(lerAgendamentoXML(linha));
					}else{
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public int addCliente(Cliente c) {
		int id = rpClientes.add(c);
		if(id>0){
			xstream.alias("cliente", Cliente.class);
			String xml = xstream.toXML(c);
			escreverArq(arqClientes, xml);
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

	public Cliente lerClienteXML(String xml){
		xstream.alias("cliente", Cliente.class);
		return  (Cliente) xstream.fromXML(xml);
	}
	
	public Agendamento lerAgendamentoXML(String xml){
		xstream.alias("agendamento", Agendamento.class);
		return  (Agendamento) xstream.fromXML(xml);
	}

	public Cliente buscaCliente(String nome) {
		return rpClientes.busca(nome);
	}
	
	public Cliente buscaCliente(int id) {
		return rpClientes.busca(id);
	}

	public int addAgendamento(Agendamento a) {
		int id = rpAgendamentos.add(a);
		if(id>0){
			xstream.alias("agendamento", Agendamento.class);
			xstream.autodetectAnnotations(true);
			String xml = xstream.toXML(a);
			escreverArq(arqAgendamentos, xml);
		}
		return id;
	}

}
