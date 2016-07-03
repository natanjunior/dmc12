package unb.tela;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import unb.Fachada;
import unb.controlador.Agendamento;
import unb.controlador.Cliente;

public class Principal extends JPanel{
	private Tela tela;
	private Fachada fachada;
	private JPanel usuariosPanel, agendamentosPanel;
	private JTable tbUsuarios, tbAgendamentos;
	private JTableHeader thUsuarios, thAgendamentos;
	private JLabel lbUsuarios, lbAgendamentos;
	private DefaultTableModel mdlUsuario, mdlAgendamento;
	private Box bxAgendamento, bxListaAgendamento, bxCliente, bxListaCliente;
	
	public Principal(Tela t, Fachada f) {
		super();
		
		this.tela = t;
		this.fachada = f;
		
		this.setLayout(new BorderLayout());
		
		usuariosPanel = new JPanel();
		usuariosPanel.setLayout(new BoxLayout(usuariosPanel, BoxLayout.Y_AXIS));
		
		lbUsuarios = new JLabel("Usuarios");
		lbAgendamentos = new JLabel("Agendamentos");
		
		bxListaAgendamento = Box.createVerticalBox();
		bxAgendamento = Box.createHorizontalBox();
		bxListaCliente = Box.createVerticalBox();
		bxCliente = Box.createHorizontalBox();
		
		bxAgendamento.add(lbAgendamentos);
		bxListaAgendamento.add(bxAgendamento);
		bxCliente.add(lbUsuarios);
		bxListaCliente.add(bxCliente);
				
		agendamentosPanel = new JPanel();
		agendamentosPanel.setLayout(new BoxLayout(agendamentosPanel, BoxLayout.Y_AXIS));
		
		this.add(usuariosPanel, BorderLayout.LINE_START);
		this.add(agendamentosPanel, BorderLayout.CENTER);
		
		criarTabelas();
	}
	
	public void criarTabelas(){
		this.remove(usuariosPanel);
		this.remove(agendamentosPanel);
		
		for(Cliente c : fachada.getClientes()){
			bxCliente = Box.createHorizontalBox();
			bxCliente.add(new JLabel(Integer.toString(c.getId())));
			bxCliente.add(new JLabel(c.getNome()));
			bxCliente.add(new JLabel(c.getEndereco()));
			bxCliente.add(new JLabel(Integer.toString(c.getPorta())));
			bxListaCliente.add(bxCliente);
		}
		usuariosPanel.add(bxListaCliente);
		
		for(Agendamento a : fachada.getAgendamentos()){
			bxAgendamento = Box.createHorizontalBox();
			bxAgendamento.add(new JLabel(Integer.toString(a.getId())));
			bxAgendamento.add(new JLabel(a.getArquivo()));
			bxAgendamento.add(new JLabel(a.getData()));
			bxAgendamento.add(new JLabel(tela.rotuloEstado(a.getEstado())));
			bxListaAgendamento.add(bxAgendamento);
		}
		agendamentosPanel.add(bxListaAgendamento);
		
		this.add(usuariosPanel, BorderLayout.LINE_START);
		this.add(agendamentosPanel, BorderLayout.CENTER);
		
		this.validate();
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
