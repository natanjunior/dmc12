package unb.model;

import java.util.ArrayList;
import unb.controlador.*;

public class RepositorioBackups {
	private ArrayList<Backup> backups;
	private Banco banco;

	public RepositorioBackups(Banco b) {
		this.banco = b;
		this.backups = new ArrayList<Backup>();
	}

	public void add(Backup b) {
		backups.add(b);
	}
	
	public Backup busca(int id) {
		for(Backup b : backups) {
			if (b.getId()==id)
				return b;
		}
		return null;
	}

	public ArrayList<Backup> getBackups() {
		return backups;
	}
}
