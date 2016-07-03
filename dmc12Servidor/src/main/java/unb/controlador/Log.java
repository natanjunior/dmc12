package unb.controlador;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import unb.Fachada;

public class Log {
	private Fachada fachada;
	private File aLog;
	private SimpleDateFormat sdf;
	
	public Log(Fachada f) {
		this.fachada = f;
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		String data = sdf.format(new Date(System.currentTimeMillis()));
		aLog = new File(System.getProperty("user.home")+"/dmc/log/"+data+"_log.txt");
		if(!aLog.exists()){
			try {
				new File(System.getProperty("user.home")+"/dmc/log/").mkdir();
				aLog.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy ");
		logging("# Servidor iniciado");
	}
	
	public void logging(String linha){
		FileWriter escritor;
		linha = sdf.format(Calendar.getInstance().getTimeInMillis())+linha;
		try {
			escritor = new FileWriter(aLog, true);
			escritor.write(linha);
			escritor.write(System.getProperty( "line.separator" ));
			escritor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
