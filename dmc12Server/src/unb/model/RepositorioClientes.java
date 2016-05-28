package unb.model;

import java.util.ArrayList;
import unb.controlador.*;

public class RepositorioClientes {
	private ArrayList<Cliente> clientes;
	private Banco banco;
	
	public RepositorioClientes(Banco b) {
		this.banco = b;
		this.clientes = new ArrayList<Cliente>();
	}

	public int add(Cliente c) {
		if(!clientes.contains(c)){
			int newid = banco.newid();
			c.setId(newid);
			clientes.add(c);
			return newid;
		}else{
			return -1;
		}
	}
}
