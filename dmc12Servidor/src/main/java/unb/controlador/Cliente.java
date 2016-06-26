package unb.controlador;

public class Cliente {
	private int id;
	private String nome;
	private String chave;
	private String endereco;
	private int porta;
	
	public Cliente(String n, String c) {
		this.nome = n;
		this.chave = c;
	}

	public String getNome() {
		return nome;
	}

	public void setId(int newid) {
		this.id = newid;
	}
	
	public String getChave(){
		return chave;
	}
	
	public int getId(){
		return id;
	}
	
	public void setPorta(int p){
		this.porta = p;
	}

	public int getPorta(){
		return porta;
	}

	public void setEndereco(String e, int p) {
		this.endereco = e;
		this.porta = p;
	}

	public String getEndereco() {
		return endereco;
	}
}
