package unb.model;

import java.util.ArrayList;
import unb.controlador.*;

public class RepositorioAgendamentos {
	private ArrayList<Agendamento> agendamentos;
	private Banco banco;

	public RepositorioAgendamentos(Banco b) {
		this.banco = b;
		this.agendamentos = new ArrayList<Agendamento>();
	}

	public int add(Agendamento a) {
		if(!agendamentos.contains(a)){
			int newid = banco.newAgendamentoId();
			a.setId(newid);
			agendamentos.add(a);
			return newid;
		}else{
			return -1;
		}
	}
}
