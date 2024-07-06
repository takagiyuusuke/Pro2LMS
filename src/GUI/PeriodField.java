package GUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.function.Consumer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class PeriodField extends JPanel {
	private boolean isOk = false;
	private int period;
	
	private JLabel errlabel = new JLabel("");
	private JTextField periodField = new JTextField("");
	
	private Consumer<Integer> onTextChanged;
	
	
	public PeriodField(String label) {
		super();
		this.periodField.getDocument().addDocumentListener(this.listener);
		JPanel namePane = new JPanel();
		namePane.setLayout(new GridLayout(1, 0));
		namePane.add(new JLabel(label + ":"));
		namePane.add(this.periodField);
		
		this.setLayout(new GridLayout(0, 1));
		this.add(namePane);
		this.add(this.errlabel);
	}
	
	public void setOnTextChanged(Consumer<Integer> onTextChanged) {
		this.onTextChanged = onTextChanged;
	}
	
	public String getText() {
		return periodField.getText();
	}
	
	public int getPeriod() {
		return this.period;
	}
	
	public boolean isOk() {
		return this.isOk;
	}
	

	public void reset() {
		this.periodField.setText("");
		this.errlabel.setText("");
	}
	
	private void periodCheck() {
		String newPeriodString = this.periodField.getText();
		if (newPeriodString == null || !newPeriodString.matches("[0-9]+")) {
			this.errlabel.setText("Period should have numbers!");
			this.errlabel.setForeground(Color.red);
			this.isOk = false;
			return;
		}
		this.period = Integer.valueOf(newPeriodString);
		if (!(this.period >= 1 && this.period <= 6)) {
			this.errlabel.setText("Period should be in 1~6!");
			this.errlabel.setForeground(Color.red);
			this.isOk = false;
		} else {
			this.errlabel.setText("there are no problem with period!");
			this.errlabel.setForeground(Color.blue);
			this.isOk = true;
		}
	}

	class TextFieldDocumentListener implements DocumentListener {
		@Override
		public void insertUpdate(DocumentEvent e) {
			onTextChanged(e);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			onTextChanged(e);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			onTextChanged(e);
		}

		private void onTextChanged(DocumentEvent e) {
			periodCheck();
			PeriodField.this.onTextChanged.accept(PeriodField.this.getPeriod());	
		}
	}

	TextFieldDocumentListener listener = new TextFieldDocumentListener();

}
