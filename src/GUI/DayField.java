package GUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.function.Consumer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class DayField extends JPanel {
	private boolean isOk = false;
	
	private JLabel errlabel = new JLabel("");
	private JTextField dayField = new JTextField("");
	
	private Consumer<String> onTextChanged;
	
	
	public DayField(String label) {
		super();
		this.dayField.getDocument().addDocumentListener(this.listener);
		JPanel namePane = new JPanel();
		namePane.setLayout(new GridLayout(1, 0));
		namePane.add(new JLabel(label + ":"));
		namePane.add(this.dayField);
		
		this.setLayout(new GridLayout(0, 1));
		this.add(namePane);
		this.add(this.errlabel);
	}
	
	public void setOnTextChanged(Consumer<String> onTextChanged) {
		this.onTextChanged = onTextChanged;
	}
	
	public String getText() {
		return dayField.getText();
	}
	
	public boolean isOk() {
		return this.isOk;
	}
	

	public void reset() {
		this.dayField.setText("");
		this.errlabel.setText("");
	}
	
	private void inputCheck() {
		String newDay = dayField.getText();
		if (!newDay.matches("Sunday|Monday|Tuesday|Wednesday|Thursday|Friday|Saturday")) {
			this.errlabel.setText("day shoud match 'Sunday|Monday|Tuesday|Wednesday|Thursday|Friday|Saturday'");
			this.errlabel.setForeground(Color.red);
			this.isOk = false;
		} else {
			this.errlabel.setText("there are no problem with day!");
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
			DayField.this.inputCheck();
			DayField.this.onTextChanged.accept(DayField.this.getText());
		}
	}

	TextFieldDocumentListener listener = new TextFieldDocumentListener();

}
