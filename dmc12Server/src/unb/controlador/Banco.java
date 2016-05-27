package unb.controlador;

import java.io.File;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import unb.Fachada;
import unb.model.*;

public class Banco {
	private Fachada fachada;
	private RepositorioClientes rpClientes;
	private File arqLog, arqClientes;
	XStream xstream;
	
	public Banco(Fachada f) {
		this.fachada = f;
		
		this.rpClientes = new RepositorioClientes(this);
		this.xstream = new XStream(new DomDriver());
	}
	
	public void carregaUsuarios() {
		arqClientes = new File("clientes.xml");
		if(!arqClientes.exists()){
			System.out.println("gool");
		}
//		xstream.alias("cliente", Cliente.class);
		
	}

}
