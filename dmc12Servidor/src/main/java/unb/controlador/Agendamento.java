package unb.controlador;

public class Agendamento {
	private int id;
	private Cliente cliente;
	private String arquivo;
	private String data; //	* ver o que é melhor
	private String hora; //	* ver o que é melhor
	private int estado;
	
	public Agendamento(Cliente c, String a, String d, String h) {
		this.arquivo = a;
		this.data = d;
		this.hora = h;
		this.cliente = c;
		this.estado = 0;
	}
	
	public void setId(int newid) {
		this.id = newid;
	}
	
	public int getId(){
		return id;
	}
	
	public String getArquivo(){
		return arquivo;
	}

	public String getData() {
		return data + " " + hora;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public void setHora(String novaHora) {
		this.hora = novaHora;
	}

	public void setData(String novaData) {
		this.data = novaData;
	}
}
