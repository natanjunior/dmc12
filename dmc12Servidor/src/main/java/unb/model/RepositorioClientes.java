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
			int newid = banco.newClienteId();
			c.setId(newid);
			clientes.add(c);
			return newid;
		}else{
			return -1;
		}
	}

	public Cliente busca(String nome) {
		for(Cliente c : clientes) {
			if (c.getNome().equals(nome))
				return c;
		}
		return null;
	}
	
	public Cliente busca(int id) {
		for(Cliente c : clientes) {
			if (c.getId()==id)
				return c;
		}
		return null;
	}
}
