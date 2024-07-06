package GUI.components;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("serial")
public class PeopleSelect extends JPanel {
	private String[] values;
	private JLabel errLabel = new JLabel("");
	private JComboBox<String> select;
	private Consumer<String> onSelect;
	private boolean isOk;
	
	public PeopleSelect(String label, List<String> listValues) {
		super();
		listValues.addFirst(label);
		this.values = listValues.toArray(new String[listValues.size()]);
		JPanel selectPane = new JPanel();
		selectPane.setLayout(new GridLayout(1, 0));
		JLabel selectLabel = new JLabel(label + ": ");
		this.select = new JComboBox<>(this.values);
		SelectActionListener listener = new SelectActionListener();
		this.select.addActionListener(listener);
		
		selectPane.add(selectLabel);
		selectPane.add(this.select);
		
		this.setLayout(new GridLayout(0, 1));
		this.add(selectPane);
		this.add(this.errLabel);
	}
	
	public boolean isOk() {
		return this.isOk;
	}
	
	public void setOnSelect(Consumer<String> onSelect) {
		this.onSelect = onSelect;
	}
	
	public void reset() {
		this.select.setSelectedIndex(0);
		this.errLabel.setText("");
	}
	
	class SelectActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int index = PeopleSelect.this.select.getSelectedIndex();
			if (index == 0) {
				PeopleSelect.this.errLabel.setText("it must be selected!");
				PeopleSelect.this.errLabel.setForeground(Color.red);
				PeopleSelect.this.isOk = false;
			} else {
				PeopleSelect.this.isOk = true;
				PeopleSelect.this.errLabel.setText("there is no problem!");
				PeopleSelect.this.errLabel.setForeground(Color.blue);
				String teacherName = PeopleSelect.this.select.getItemAt(index);
				PeopleSelect.this.onSelect.accept(teacherName);
			}
			
		}
	}

}
