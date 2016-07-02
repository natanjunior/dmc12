package unb.model;

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.*;

import unb.controlador.*;

@XStreamAlias("AGENDAMENTOS")
public class RepositorioAgendamentos {
	@XStreamImplicit(itemFieldName = "agendamento")
	private ArrayList<Agendamento> agendamentos;
	
	@XStreamOmitField
	private Banco banco;

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

	public int size() {
		return agendamentos.size();
	}
}
