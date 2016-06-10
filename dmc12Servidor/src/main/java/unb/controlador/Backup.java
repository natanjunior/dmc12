package unb.controlador;
import java.util.TimerTask;

public class Backup extends TimerTask{
	private String arquivo;
	private Agendamento agendamento;
	
	public Backup() {
		// TODO Auto-generated constructor stub
	}

	public void run() {
		System.out.println("Hi see you after 10 seconds");
	}

}
