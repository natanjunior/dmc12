package unb.model;

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.*;

import unb.controlador.*;

@XStreamAlias("AGENDAMENTOS")
public class RepositorioAgendamentos {
	@XStreamImplicit(itemFieldName = "agendamento")
	private ArrayList<Agendamento> agendamentos;

	public RepositorioAgendamentos() {
		this.agendamentos = new ArrayList<Agendamento>();
	}

	public int add(Agendamento a, int id) {
		if(!agendamentos.contains(a)){
			a.setId(id);
			agendamentos.add(a);
			return id;
		}else{
			return -1;
		}
	}

	public ArrayList<Agendamento> getAgendamentos() {
		return agendamentos;
	}
	
	public ArrayList<Agendamento> getAgendamentos(Cliente c) {
		int cliente = c.getId();
		ArrayList<Agendamento> retorno = new ArrayList<Agendamento>();
		for (Agendamento a : agendamentos) {
			if(a.getCliente().getId()==cliente)
				retorno.add(a);
		}
		return retorno;
	}

	public int size() {
		return agendamentos.size();
	}

	public Agendamento getAgendamento(int id) {
		Agendamento retorno = null;
		for (Agendamento a : agendamentos) {
			if(a.getId()==id)
				retorno = a;
		}
		return retorno;
	}
}
