package unb.tela;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import unb.Fachada;
import unb.controlador.Agendamento;
import unb.controlador.Cliente;

public class Principal extends JPanel{
	private Tela tela;
	private Fachada fachada;
	private JPanel usuariosPanel, agendamentosPanel;
	private JTable tbUsuarios, tbAgendamentos;
	private JLabel lbUsuarios, lbAgendamentos;
	private DefaultTableModel mdlUsuario, mdlAgendamento;
	
	public Principal(Tela t, Fachada f) {
		super();
		
		this.tela = t;
		this.fachada = f;
		
		this.setLayout(new FlowLayout());
		
		usuariosPanel = new JPanel();
		
		lbUsuarios = new JLabel("Usuarios");
		
		criarTabelas();
		
		usuariosPanel.add(lbUsuarios);
		usuariosPanel.add(tbUsuarios);
		
		agendamentosPanel = new JPanel();
				
		lbAgendamentos = new JLabel("Agendamentos");
		
		agendamentosPanel.add(lbAgendamentos);
		agendamentosPanel.add(tbAgendamentos);
		
		this.add(usuariosPanel);
		this.add(agendamentosPanel);
	}
	
	public void criarTabelas(){
		mdlUsuario = new DefaultTableModel();
		tbUsuarios = new JTable(mdlUsuario);
		mdlUsuario.addColumn("Id");
		mdlUsuario.addColumn("Login");
		mdlUsuario.addColumn("Endereço");
		mdlUsuario.addColumn("Porta");
		tbUsuarios.getColumnModel().getColumn(0).setPreferredWidth(70);
		tbUsuarios.getColumnModel().getColumn(1).setPreferredWidth(120);
		tbUsuarios.getColumnModel().getColumn(2).setPreferredWidth(120);
		tbUsuarios.getColumnModel().getColumn(3).setPreferredWidth(70);
		mdlUsuario.setNumRows(0);
		for (Cliente c : fachada.getClientes()) {
			mdlUsuario.addRow(new Object[]{c.getId(), c.getNome(), c.getEndereco(), c.getPorta()});
		}
		
		mdlAgendamento = new DefaultTableModel();
		tbAgendamentos = new JTable(mdlAgendamento);
		mdlAgendamento.addColumn("Id");
		mdlAgendamento.addColumn("Arquivo");
		tbAgendamentos.getColumnModel().getColumn(0).setPreferredWidth(50);
		tbAgendamentos.getColumnModel().getColumn(1).setPreferredWidth(120);
		mdlAgendamento.setNumRows(0);
		for (Agendamento a : fachada.getAgendamentos()) {
			mdlAgendamento.addRow(new Object[]{a.getId(), a.getArquivo()});
		}
	}

	public void atualizar() {
		usuariosPanel.remove(tbUsuarios);
		agendamentosPanel.remove(tbAgendamentos);
		
		criarTabelas();
		
		usuariosPanel.add(tbUsuarios);
		agendamentosPanel.add(tbAgendamentos);
		
		this.add(usuariosPanel);
		this.add(agendamentosPanel);
		
		this.validate();
	}

}
