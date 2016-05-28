package unb.controlador;

public class Cliente {
	private int id;
	private String nome;
	private String chave;
	private String endereco;
	
	public Cliente(String n, String c, String e) {
		this.nome = n;
		this.chave = c;
		this.endereco = e;
	}

	public String getNome() {
		return nome;
	}

	public void setId(int newid) {
		this.id = newid;
	}

}
