package unb.controlador;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class Agendamento {
	private int id;
	private Cliente cliente;
	private String arquivo;
	private String data; //	* ver o que é melhor
	private String hora; //	* ver o que é melhor
	
	public Agendamento(Cliente c, String a, String d, String h) {
		this.arquivo = a;
		this.data = d;
		this.hora = h;
		this.cliente = c;
	}
	
	public void setId(int newid) {
		this.id = newid;
	}
}
