package unb.controlador;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import unb.Fachada;
import unb.model.*;

public class Banco {
	private Fachada fachada;
	private RepositorioClientes rpClientes;
	private File arqLog, arqClientes;
	private int newid;
	private XStream xstream;
	
	public Banco(Fachada f) {
		this.fachada = f;
		
		this.rpClientes = new RepositorioClientes(this);
		this.xstream = new XStream(new DomDriver());
	}
	
	public void carregaUsuarios() {
		arqClientes = new File("clientes.xml");
		if(!arqClientes.exists()){
			this.newid = 1313; // base dos id's
			try {
				arqClientes.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			escreverArq(arqClientes, Integer.toString(newid));
		}		
	}

	public int addCliente(Cliente c) {
		int id = rpClientes.add(c);
		if(id>0){
			xstream.alias("cliente", Cliente.class);
			String xml = xstream.toXML(c);
			System.out.println(xml);
			escreverArq(arqClientes, xml);
		}
		return id;
	}

	public int newid() {
		return newid+1;
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

}
