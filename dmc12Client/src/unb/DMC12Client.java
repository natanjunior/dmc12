package unb;

public class DMC12Client {
	
	private static Fachada fachada;

	public static void main(String[] args) {
		fachada = Fachada.obterInstancia();
		
		fachada.init();
	}

}
