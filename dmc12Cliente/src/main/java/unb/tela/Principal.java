package unb.tela;

import java.awt.FlowLayout;
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
	private JPanel novoPanel, agendadosPanel, backupsPanel, configPanel;
	private JLabel lbNovo;
	private JButton selecArqBtn, agendarBtn;
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
	
	public Principal(Tela t) {
		super();
		
		this.tela = t;

		this.setLayout(new FlowLayout());
		
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
		
		this.add(novoPanel);
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
		
		if(true)
			tela.agendar(msg);
		
	}

}
