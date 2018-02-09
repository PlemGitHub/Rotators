package visual.myPanels.ent;

import javax.swing.JPanel;

import mech.Constants;
import visual.myPanels.subEnt.MyJLabel;

@SuppressWarnings("serial")
public class LabelsPanel extends JPanel implements Constants{	
	
	private MyJLabel radiusLbl, kDDLbl, dDLbl, delayLbl;

	public LabelsPanel() {
		setLayout(null);

		setSize(COMPONENT_WIDTH, COMPONENT_HEIGHT);
		setLocation(0, 0);
		createLabels();
	}

	private void createLabels() {
		createRadius();	// 1
		createKDR();	// 2
		createDR();		// 3
		createDelay();	// 4
	}

	private void createRadius() {
		radiusLbl = new MyJLabel("R", 0);
		add(radiusLbl);
	}

	private void createKDR() {
		kDDLbl = new MyJLabel("kDD", 1);
		add(kDDLbl);
	}

	private void createDR() {
		dDLbl = new MyJLabel("dD", 2);
		add(dDLbl);
	}

	private void createDelay() {
		delayLbl = new MyJLabel("delay", 3);
		add(delayLbl);
	}
}
