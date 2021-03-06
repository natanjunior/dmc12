package unb.tela;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Login extends JPanel {
	private Tela tela;
	private JLabel lbLogin, lbCadastro, lbNome, lbSenha;
	private JTextField tfNome;
	private JPasswordField pfSenha;
	private JButton btCadastrar, btEntrar, btSemCadastro;
	
	public Login(Tela t) {
		super();
		
		this.tela = t;
		
		this.setLayout(new FlowLayout());
		
		JPanel configPanel = new JPanel();
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
		
		lbLogin = new JLabel("Login");
		lbCadastro = new JLabel("Cadastro");
		lbNome = new JLabel("Nome");
		lbSenha = new JLabel("Senha");
		tfNome = new JTextField(15);
		pfSenha = new JPasswordField(15);
		
		btCadastrar = new JButton("Cadastrar");
		btCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrar();
			}
		});
		
		btEntrar = new JButton("Entrar");
		btEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				entrar();
			}
		});
		
		btSemCadastro = new JButton("Não tenho cadastro");
		btSemCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastro();
			}
		});
		
		this.add(lbLogin);
		this.add(lbCadastro);
		this.add(lbNome);
		this.add(lbSenha);
		this.add(btCadastrar);
		this.add(btEntrar);
		this.add(btSemCadastro);
		this.add(tfNome);
		this.add(pfSenha);
	}
	
	public void cadastrar(){
		String nome = tfNome.getText();
		char [] senhaChar = pfSenha.getPassword();
		String senha = String.valueOf(senhaChar);
		
		if((nome.length()>0)&&(senha.length()>0)){
			tela.cadastrar(nome, senha);
		}
	}
	
	public void entrar(){
		String nome = tfNome.getText();
		char [] senhaChar = pfSenha.getPassword();
		String senha = String.valueOf(senhaChar);
		
		if((nome.length()>0)&&(senha.length()>0)){
			tela.entrar(nome, senha);
		}
	}
	
	public void cadastro(){
		
	}

}
