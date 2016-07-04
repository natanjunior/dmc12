package unb.tela;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JPanel usuariosPanel, agendamentosPanel, configPanel;
	private JTable tbUsuarios, tbAgendamentos;
	private JTableHeader thUsuarios, thAgendamentos;
	private JLabel lbUsuarios, lbAgendamentos;
	private DefaultTableModel mdlUsuario, mdlAgendamento;
	private Box bxAgendamento, bxListaAgendamento, bxCliente, bxListaCliente;
	private Color cinzaClaro, cinzaEscuro;
	private int xSize, ySize;
	
	public Principal(Tela t, Fachada f) {
		super();
		
		this.tela = t;
		this.fachada = f;
		
		this.setLayout(new BorderLayout());
		
		Toolkit tk = Toolkit.getDefaultToolkit(); // usar tamanho maximo da tela
		this.xSize = ((int) tk.getScreenSize().getWidth());
		this.ySize = ((int) tk.getScreenSize().getHeight());
		
		usuariosPanel = new JPanel();
		usuariosPanel.setLayout(new BoxLayout(usuariosPanel, BoxLayout.Y_AXIS));
		usuariosPanel.setPreferredSize(new Dimension(350, Integer.MAX_VALUE));
		
		lbUsuarios = new JLabel("Clientes");
		lbUsuarios.setFont(lbUsuarios.getFont().deriveFont(20.0f));
		lbAgendamentos = new JLabel("Agendamentos");
		lbAgendamentos.setFont(lbAgendamentos.getFont().deriveFont(20.0f));
		
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
		agendamentosPanel.setPreferredSize(new Dimension(xSize-350, Integer.MAX_VALUE));
		agendamentosPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));
		
		this.cinzaClaro = new Color(224, 224, 224);
		this.cinzaEscuro = new Color(192, 192, 192);
		
//		criarTabelas();
		telaLogin();
	}
	
	public void criarTabelas(){
		usuariosPanel.remove(bxListaCliente);
		agendamentosPanel.remove(bxListaAgendamento);
		this.remove(usuariosPanel);
		this.remove(agendamentosPanel);
		
		this.validate();
		
		bxListaAgendamento = Box.createVerticalBox();
		bxAgendamento = Box.createHorizontalBox();
		bxListaCliente = Box.createVerticalBox();
		bxCliente = Box.createHorizontalBox();
		
		bxAgendamento.add(lbAgendamentos);
		bxListaAgendamento.add(bxAgendamento);
		bxAgendamento = Box.createHorizontalBox();
		bxAgendamento.add(new JLabel("#"));
		bxAgendamento.add(Box.createHorizontalGlue());
		bxAgendamento.add(new JLabel("Arquivo"));
		bxAgendamento.add(Box.createHorizontalGlue());
		bxAgendamento.add(new JLabel("Data"));
		bxAgendamento.add(Box.createHorizontalGlue());
		bxAgendamento.add(new JLabel("Estado"));
		bxAgendamento.setOpaque(true);
		bxAgendamento.setBackground(cinzaEscuro);
		bxListaAgendamento.add(bxAgendamento);
		
		bxCliente.add(lbUsuarios);
		bxListaCliente.add(bxCliente);
		bxCliente = Box.createHorizontalBox();
		bxCliente.add(new JLabel("#"));
		bxCliente.add(Box.createHorizontalGlue());
		bxCliente.add(new JLabel("Login"));
		bxCliente.add(Box.createHorizontalGlue());
		bxCliente.add(new JLabel("Endereço"));
		bxCliente.add(Box.createHorizontalGlue());
		bxCliente.add(new JLabel("Porta"));
		bxCliente.setOpaque(true);
		bxCliente.setBackground(cinzaEscuro);
		bxListaCliente.add(bxCliente);
		
		for(Cliente c : fachada.getClientes()){
			bxCliente = Box.createHorizontalBox();
			bxCliente.add(new JLabel(Integer.toString(c.getId())));
			bxCliente.add(Box.createHorizontalGlue());
			bxCliente.add(new JLabel(c.getNome()));
			bxCliente.add(Box.createHorizontalGlue());
			bxCliente.add(new JLabel(c.getEndereco()));
			bxCliente.add(Box.createHorizontalGlue());
			bxCliente.add(new JLabel(Integer.toString(c.getPorta())));
			bxCliente.setOpaque(true);
			bxCliente.setBackground(cinzaClaro);
			bxListaCliente.add(bxCliente);
		}
		usuariosPanel.add(bxListaCliente);
		
		for(Agendamento a : fachada.getAgendamentos()){
			bxAgendamento = Box.createHorizontalBox();
			bxAgendamento.add(new JLabel(Integer.toString(a.getId())));
			bxAgendamento.add(Box.createHorizontalGlue());
			bxAgendamento.add(new JLabel(a.getArquivo()));
			bxAgendamento.add(Box.createHorizontalGlue());
			bxAgendamento.add(new JLabel(a.getData()));
			bxAgendamento.add(Box.createHorizontalGlue());
			bxAgendamento.add(new JLabel(tela.rotuloEstado(a.getEstado())));
			bxAgendamento.setOpaque(true);
			bxAgendamento.setBackground(cinzaClaro);
			bxListaAgendamento.add(bxAgendamento);
		}
		agendamentosPanel.add(bxListaAgendamento);
		
		this.add(usuariosPanel, BorderLayout.LINE_START);
		this.add(agendamentosPanel, BorderLayout.CENTER);
		
		this.validate();
	}

	public void atualizar() {
		criarTabelas();
	}
	
	private void telaLogin() {
		configPanel = new JPanel();
		JLabel lbPorta = new JLabel("Porta");
		final JTextField tfPorta = new JTextField(10);
		tfPorta.setText("2016");
		JButton btPorta = new JButton("Setar Porta");
		btPorta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int porta = Integer.parseInt(tfPorta.getText());
				if(porta>1024)
					tela.setarPorta(porta);
			}
		});
		configPanel.add(lbPorta);
		configPanel.add(tfPorta);
		configPanel.add(btPorta);
		this.add(configPanel);
	}

	public void telaPrincipal(String endereco) {
		this.remove(configPanel);
		System.out.println(endereco);
		criarTabelas();
	}

}
