package unb.controlador;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import unb.Fachada;

public class Backup extends TimerTask{
	private Fachada fachada;
	private int id;
	private String arquivo;
	private Agendamento agendamento;
	private Date data;
	private int estado;
	
	public Backup(Agendamento a, Fachada f) {
		this.fachada = f;
		this.id = a.getId();
		this.agendamento = a;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			this.data = (Date)sdf.parse(a.getData());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.estado = 0;
	}
	
	public int getId(){
		return id;
	}

	public void run() {
		if(this.estado==1){
			this.estado = 2;
			fachada.buscarArquivo(agendamento);
		}
		this.cancel();
	}

	public long getTimer() {
		return data.getTime() - Calendar.getInstance().getTimeInMillis();
	}

	public void setEstado(int i) {
		this.estado = i;
	}

	public int getEstado() {
		return estado;
	}

}
