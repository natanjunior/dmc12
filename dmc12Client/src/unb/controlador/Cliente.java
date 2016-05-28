package unb.controlador;

public class Cliente {
	private int id;
	private String nome;
	private String chave;
	
	public Cliente() {
		nome = "DrEmmettBrown"; //	nome default
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome2) {
		this.nome = nome2;
	}
	
	public void setId(int id2) {
		this.id = id2;
	}
	
	public void setChave(String chave2){
		this.chave = chave2;
	}

}
