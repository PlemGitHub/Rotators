package mech;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import visual.MainPanel;

@SuppressWarnings("serial")
public class MainTimer extends Timer implements ActionListener{
	private MainPanel mainPanel;

	public MainTimer(int delay, ActionListener listener, MainPanel mainPanel) {
		super(delay, listener);
		this.mainPanel = mainPanel;
		mainPanel.createMyImage();
		mainPanel.readAllPropertiesBeforeStart();
		mainPanel.disableAllFields();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mainPanel.moveAllRotators();
		mainPanel.drawLastPoint();
	}

}
