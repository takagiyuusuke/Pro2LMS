package GUI.components;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("serial")
public class EntitySelect extends JPanel {
	private String[] values;
	private JLabel errLabel = new JLabel("");
	private JComboBox<String> select;
	private Consumer<String> onSelect;
	private boolean isOk;
	
	public EntitySelect(String label, List<String> listValues) {
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
	
	public void reset(String label, List<String> listValues) {
        listValues.addFirst(label); 
        this.values = listValues.toArray(new String[listValues.size()]);
        try {
        this.select.removeAllItems();
        } catch (Exception e) {
        }
        for (String value : this.values) {
            this.select.addItem(value);
        }
        this.select.setSelectedIndex(0);
        this.errLabel.setText("");
    }
	
	class SelectActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int index = EntitySelect.this.select.getSelectedIndex();
			if (index == 0) {
				EntitySelect.this.errLabel.setText("it must be selected!");
				EntitySelect.this.errLabel.setForeground(Color.red);
				EntitySelect.this.isOk = false;
			} else {
				EntitySelect.this.isOk = true;
				EntitySelect.this.errLabel.setText("there is no problem!");
				EntitySelect.this.errLabel.setForeground(Color.blue);
				String teacherName = EntitySelect.this.select.getItemAt(index);
				EntitySelect.this.onSelect.accept(teacherName);
			}
			
		}
	}

}