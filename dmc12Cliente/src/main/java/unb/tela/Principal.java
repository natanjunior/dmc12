package unb.tela;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.text.DateFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import unb.helper.DateLabelFormatter;

public class Principal extends JPanel{
	private Tela tela;
	private JPanel novoPanel, agendadosPanel, configPanel;
	private JLabel lbNovo, lbAgendados, lbArq, lbData, lbHora, lbEstado, lbId;
	private JButton selecArqBtn, agendarBtn, act1Btn;
	private UtilDateModel modelData;
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	private Calendar calendar;
	private SpinnerDateModel modelHora;
	private JSpinner spinnerHora;
	private DateFormatter formatter;
	private JFileChooser arquivoFC;
	private File arquivo;
	private DateFormat df;
	private Box bxListaAgendamento, bxAgendamento;
	
	public Principal(Tela t) {
		super();
		
		this.tela = t;
		this.setLayout(new BorderLayout());
		
		novoPanel = new JPanel();
		
		lbNovo = new JLabel("Agendar Backup");
			
		modelData = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Hoje");
		p.put("text.month", "Mês");
		p.put("text.year", "Ano");
		datePanel = new JDatePanelImpl(modelData, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 24); // 24 == 12 PM == 00:00:00
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        modelHora = new SpinnerDateModel();
        modelHora.setValue(calendar.getTime());

        spinnerHora = new JSpinner(modelHora);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerHora, "HH:mm:ss");
        formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false); // this makes what you want
        formatter.setOverwriteMode(true);

        spinnerHora.setEditor(editor);
        
        arquivoFC = new JFileChooser();
        arquivoFC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        selecArqBtn = new JButton("Selecionar Diretorio");
        selecArqBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int retorno = arquivoFC.showOpenDialog(null);
				if (retorno == JFileChooser.APPROVE_OPTION) {
					arquivo = arquivoFC.getSelectedFile();
				}
			}
		});
        
        agendarBtn = new JButton("Agendar");
        agendarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agendar();
			}
		});
		 
		novoPanel.add(lbNovo);
		novoPanel.add(datePicker);
		novoPanel.add(spinnerHora);
		novoPanel.add(selecArqBtn);
		novoPanel.add(agendarBtn);
		
		agendadosPanel = new JPanel();
		
		lbAgendados = new JLabel("Agendamentos");
		
		bxListaAgendamento = Box.createVerticalBox();
		bxAgendamento = Box.createHorizontalBox();
		
		bxAgendamento.add(lbAgendados);
		bxListaAgendamento.add(bxAgendamento);
		
		this.add(novoPanel, BorderLayout.PAGE_START);
		this.add(agendadosPanel, BorderLayout.CENTER);
	}
	
	public void agendar(){
		String msg = "";
		boolean valido = true;
		
		if(arquivo!=null){
			msg += " " + arquivo.getAbsolutePath();
		}else{
			valido = false;
		}
		
		Date selectedDate = (Date) datePicker.getModel().getValue();
		if(selectedDate!=null){
			df = new SimpleDateFormat("dd/MM/yyyy");
			msg += " " + df.format(selectedDate);
		}else{
			valido = false;
		}
		
		df = new SimpleDateFormat("HH:mm:ss");
		msg += " " + df.format(spinnerHora.getValue());
		
		if(true)	// mudar aqui - fazer validacao
			tela.agendar(msg);
		
	}

	public void criarLista(String[] agendamentos) {
		agendadosPanel.remove(bxListaAgendamento);
		this.remove(agendadosPanel);
		bxListaAgendamento = Box.createVerticalBox();
		bxAgendamento = Box.createHorizontalBox();
		bxAgendamento.add(lbAgendados);
		bxListaAgendamento.add(bxAgendamento);
		if(agendamentos==null)
			return;
		for (int i = 0; i < agendamentos.length; i=i+5) {
			bxAgendamento = Box.createHorizontalBox();
			lbId = new JLabel(agendamentos[i]);
			String id = agendamentos[i];
			bxAgendamento.add(lbId);
			lbArq = new JLabel(agendamentos[i+1]);
			String end = agendamentos[i+1];
			bxAgendamento.add(lbArq);
			lbData = new JLabel(agendamentos[i+2]);
			bxAgendamento.add(lbData);
			lbHora = new JLabel(agendamentos[i+3]);
			bxAgendamento.add(lbHora);
			lbEstado = new JLabel(tela.rotuloEstado(Integer.parseInt(agendamentos[i+4])));
			bxAgendamento.add(lbEstado);
			if(agendamentos[i+4].equals("2")){
				act1Btn = new JButton("Excluir");
				act1Btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						excluir(id);
					}
				});
				bxAgendamento.add(act1Btn);
				act1Btn = new JButton("Restaurar");
				act1Btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						restaurar(id, end);
					}
				});
				bxAgendamento.add(act1Btn);
			}else if((agendamentos[i+4].equals("0"))||(agendamentos[i+4].equals("1"))){
				act1Btn = new JButton("Cancelar");
				act1Btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelar(id);
					}
				});
				bxAgendamento.add(act1Btn);
				act1Btn = new JButton("Reagendar");
				act1Btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						reagendar(id);
					}
				});
				bxAgendamento.add(act1Btn);
			}else{
				act1Btn = new JButton("Cancelar");
				act1Btn.setEnabled(false);
				bxAgendamento.add(act1Btn);
				act1Btn = new JButton("Reagendar");
				act1Btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						reagendar(id);
					}
				});
				bxAgendamento.add(act1Btn);
			}
			bxListaAgendamento.add(bxAgendamento);
		}
		agendadosPanel.add(bxListaAgendamento);
		this.add(agendadosPanel, BorderLayout.CENTER);
		
		this.validate();
	}

	public void excluir(String id) {
		tela.excluirAgendamento(id);
	}
	
	public void cancelar(String id) {
		tela.cancelarAgendamento(id);
	}
	
	public void restaurar(String id, String endereco) {
		tela.restaurarAgendamento(id, endereco);
	}
	
	public void reagendar(String id) {
		String msg = id;
		boolean valido = true;
		
		Date selectedDate = (Date) datePicker.getModel().getValue();
		if(selectedDate!=null){
			df = new SimpleDateFormat("dd/MM/yyyy");
			msg += " " + df.format(selectedDate);
		}else{
			valido = false;
		}
		
		df = new SimpleDateFormat("HH:mm:ss");
		msg += " " + df.format(spinnerHora.getValue());
		
		if(true)	// mudar aqui - fazer validacao
			tela.editarAgendamento(msg);
	}
}
