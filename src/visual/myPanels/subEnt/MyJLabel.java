package visual.myPanels.subEnt;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import mech.Constants;

@SuppressWarnings("serial")
public class MyJLabel extends JLabel implements Constants{
	private int i;
	
	public MyJLabel(String str, int i) {
		this.i = i;
		
		defineLabelSize();
		
		defineFont();
		setText(str);
		setHorizontalAlignment(JTextField.CENTER);
	}

	private void defineLabelSize() {
		setBounds(i*SUBCOMPONENT_WIDTH, 0, SUBCOMPONENT_WIDTH, COMPONENT_HEIGHT);
	}

	private void defineFont() {
		int size = defineFontSize();
		setFont(new Font("", Font.PLAIN, size));
	}

	private int defineFontSize() {
		int size = 0;
		do {
			size++;
		} while (size < getHeight()*.6);
		return size;
	}
}
