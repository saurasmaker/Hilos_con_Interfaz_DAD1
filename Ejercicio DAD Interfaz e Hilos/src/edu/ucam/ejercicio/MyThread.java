package edu.ucam.ejercicio;

import javax.swing.table.DefaultTableModel;

public class MyThread extends Thread{

	//Atributes
	private int iterations;
	private String nameThread;
	private Boolean process;
	private Object[] data;
	private MainFrame mainFrame;
	private DefaultTableModel dtm;
	private int myRow;
	boolean suspend = false, pause = false;

	//Constructor
	public MyThread() {
		
	}
	
	MyThread(MainFrame mainFrame, String nameThread, int iterations){
		this.mainFrame = mainFrame;
		this.nameThread = nameThread;
		this.iterations = iterations;
	}
	
	
	//Methods
	@Override
	public void run() {
		
		data = new Object[] {mainFrame.getTextField().getText(), 0, this.iterations, "En ejecución"};
		
		this.process = true;
		dtm =(DefaultTableModel)this.mainFrame.getTable().getModel();
		dtm.addRow(data);
		myRow = dtm.getRowCount()-1;
		
		for(int i = 0; i < this.iterations; ++i) {
			data[1] = i;
			dtm.setValueAt(i, myRow, 1);
			dtm.fireTableDataChanged();
			try {
				sleep(100);
				synchronized(this){
					while(this.isPaused()) 
						wait();
					
					if(this.isSuspended())
						break;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		dtm.setValueAt("Finalizado", myRow, 3);
		
		return;
	}
	
	void PauseThread() {
		this.pause = true;
		dtm.setValueAt("Pausado", myRow, 3);
		this.suspend = false;
	}
	
	synchronized void ResumeThread() {
		this.pause = false;
		dtm.setValueAt("En ejecución", myRow, 3);
		notify();
	}

	//Getters & Setters
	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public Boolean getProcess() {
		return process;
	}

	public void setProcess(Boolean process) {
		this.process = process;
	}
	public String getNameThread() {
		return nameThread;
	}

	public void setNameThread(String nameThread) {
		this.nameThread = nameThread;
	}
	
	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
		this.data = data;
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public DefaultTableModel getDtm() {
		return dtm;
	}

	public void setDtm(DefaultTableModel dtm) {
		this.dtm = dtm;
	}

	public boolean isSuspended() {
		return suspend;
	}

	public void setSuspend(boolean suspend) {
		this.suspend = suspend;
	}

	public boolean isPaused() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}
	
	
}
