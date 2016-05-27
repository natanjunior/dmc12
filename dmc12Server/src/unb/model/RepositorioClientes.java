package unb.model;

import java.util.ArrayList;
import unb.controlador.*;

public class RepositorioClientes {
	private ArrayList<Cliente> clientes;
	private Banco banco;
	
	public RepositorioClientes(Banco b) {
		this.banco = b;
	}
}
