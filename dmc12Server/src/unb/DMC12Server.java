package unb;

public class DMC12Server {
	
	private static Fachada fachada;

	public static void main(String[] args) {
		fachada = Fachada.obterInstancia();
		
		fachada.iniciarServidor();
	}

}
