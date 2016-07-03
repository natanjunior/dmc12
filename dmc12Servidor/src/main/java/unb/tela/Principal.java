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
	
	public Principal(Tela t, Fachada f) {
		super();
		
		this.tela = t;
		this.fachada = f;
		
		this.setLayout(new BorderLayout());
		
		usuariosPanel = new JPanel();
		usuariosPanel.setLayout(new BoxLayout(usuariosPanel, BoxLayout.Y_AXIS));
		
		lbUsuarios = new JLabel("Usuarios");
		
		criarTabelas();
		
		usuariosPanel.add(lbUsuarios);
		usuariosPanel.add(thUsuarios);
		usuariosPanel.add(tbUsuarios);
		
		agendamentosPanel = new JPanel();
		agendamentosPanel.setLayout(new BoxLayout(agendamentosPanel, BoxLayout.Y_AXIS));
				
		lbAgendamentos = new JLabel("Agendamentos");
		
		agendamentosPanel.add(lbAgendamentos);
		agendamentosPanel.add(thAgendamentos);
		agendamentosPanel.add(tbAgendamentos);
		
		this.add(usuariosPanel, BorderLayout.LINE_START);
		this.add(agendamentosPanel, BorderLayout.CENTER);
	}
	
	public void criarTabelas(){
		mdlUsuario = new DefaultTableModel();
		tbUsuarios = new JTable(mdlUsuario);
		tbUsuarios.setSelectionMode(0);
		mdlUsuario.addColumn("Id");
		mdlUsuario.addColumn("Login");
		mdlUsuario.addColumn("Endereço");
		mdlUsuario.addColumn("Porta");
		tbUsuarios.getColumnModel().getColumn(0).setPreferredWidth(40);
		tbUsuarios.getColumnModel().getColumn(1).setPreferredWidth(100);
		tbUsuarios.getColumnModel().getColumn(2).setPreferredWidth(100);
		tbUsuarios.getColumnModel().getColumn(3).setPreferredWidth(45);
		mdlUsuario.setNumRows(0);
//		tbUsuarios.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
//		    @Override
//		    public Component getTableCellRendererComponent(JTable table,
//		            Object value, boolean isSelected, boolean hasFocus, int row, int col) {
//
//		        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
//
//		        String status = (String)table.getModel().getValueAt(row, 2);
//		        if ("-".equals(status)) {
//		            setBackground(new Color(255, 255, 230));
//		            setForeground(Color.BLACK);
//		        } else {
//		            setBackground(new Color(255, 255, 230));
//		            setForeground(Color.BLACK);
//		        }       
//		        return this;
//		    }   
//		});
		for (Cliente c : fachada.getClientes()) {
			mdlUsuario.addRow(new Object[]{c.getId(), c.getNome(), c.getEndereco(), c.getPorta()});
		}
		thUsuarios = tbUsuarios.getTableHeader();
		
		mdlAgendamento = new DefaultTableModel();
		tbAgendamentos = new JTable(mdlAgendamento);
		tbAgendamentos.setSelectionMode(0);
		mdlAgendamento.addColumn("Id");
		mdlAgendamento.addColumn("Arquivo");
		mdlAgendamento.addColumn("Cliente");
		mdlAgendamento.addColumn("Data");
		mdlAgendamento.addColumn("Estado");
		tbAgendamentos.getColumnModel().getColumn(0).setPreferredWidth(25);
		tbAgendamentos.getColumnModel().getColumn(1).setPreferredWidth(200);
		tbAgendamentos.getColumnModel().getColumn(2).setPreferredWidth(100);
		tbAgendamentos.getColumnModel().getColumn(3).setPreferredWidth(200);
		tbAgendamentos.getColumnModel().getColumn(4).setPreferredWidth(100);
		mdlAgendamento.setNumRows(0);
		for (Agendamento a : fachada.getAgendamentos()) {
			mdlAgendamento.addRow(new Object[]{a.getId(), a.getArquivo(), a.getCliente().getNome(), a.getData(), tela.rotuloEstado(a.getEstado())});
		}
		thAgendamentos = tbAgendamentos.getTableHeader();
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
