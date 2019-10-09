package edu.ucam.ejercicio;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JSpinner;

public class MainFrame extends JFrame {

	
	private static final long serialVersionUID = 1L;

	//Atributes
	MainFrame thisFrame = this;
	
	ArrayList<MyThread> myThreads;
	
	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JComboBox<String> comboBox;
	private JSpinner spinner;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		myThreads = new ArrayList<MyThread>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblPruebasHilosE = new JLabel("Pruebas Hilos e Interfaz");
		lblPruebasHilosE.setHorizontalAlignment(SwingConstants.CENTER);
		lblPruebasHilosE.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddThread();
			}
		});
		
		JButton btnParar = new JButton("Parar/Cont.");
		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!myThreads.get(comboBox.getSelectedIndex()).isPaused())
					myThreads.get(comboBox.getSelectedIndex()).PauseThread();
				
				else
					myThreads.get(comboBox.getSelectedIndex()).ResumeThread();
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		comboBox = new JComboBox<String>();
		
		spinner = new JSpinner();
		spinner.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();
				if((caracter < 0 || caracter > 9) && caracter != '\b')
					e.consume();
			}
		});
		
		spinner.setModel(new SpinnerNumberModel(new Integer(100), null, null, new Integer(1)));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
						.addComponent(lblPruebasHilosE, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnCrear)
							.addGap(18)
							.addComponent(comboBox, 0, 107, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnParar)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblPruebasHilosE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnParar)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCrear))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Hilo", "Contador", "Iteraciones", "Estado"
			}
		));
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
	}

	
	//Methods
	void AddThread() {	
		if(textField.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "No puedes añadir un hilo sin nombre", "Error Añadir Hilo", JOptionPane.ERROR_MESSAGE); 
			return;
		}
		
		if((Integer)this.spinner.getValue()==0) {
			JOptionPane.showMessageDialog(null, "No puedes añadir un hilo sin iteraciones", "Error Añadir Hilo", JOptionPane.ERROR_MESSAGE); 
			return;
		}
		
		for(MyThread m: myThreads) 
			if(m.getNameThread().equals(textField.getText())) {
				JOptionPane.showMessageDialog(null, "No puedes añadir dos hilos con el mismo nombre", "Error Añadir Hilo", JOptionPane.ERROR_MESSAGE); 
				return;
			}
		
		myThreads.add(new MyThread(thisFrame, textField.getText(), (int)spinner.getValue()));
		comboBox.addItem(textField.getText());
		myThreads.get(myThreads.size()-1).start();
		return;
	}
	
	//Getters & Setters
	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}
}
