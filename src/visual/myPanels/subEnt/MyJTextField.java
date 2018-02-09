package visual.myPanels.subEnt;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import mech.Constants;

@SuppressWarnings("serial")
public class MyJTextField extends JTextField implements FocusListener, Constants{
	
	private int i;
	
	public MyJTextField(int value, int i) {
		this.i = i;
		
		defineTextFieldSize();
		
		defineFont();
		setText(Integer.toString(value));
		setHorizontalAlignment(JTextField.CENTER);
		
		addKeyListener(new KeyAdapter() {	
			   public void keyTyped(KeyEvent e) {
			      char c = e.getKeyChar();
			      if ( ((c < '0') || (c > '9'))
			    	&& (c != KeyEvent.VK_BACK_SPACE)
			      	&& (c != '-')){
			         e.consume();  // ignore event
			      }
			   }
			});
		addFocusListener(this);
	}

	private void defineTextFieldSize() {
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

	@Override
	public void focusGained(FocusEvent e) {
		selectAll();
	}
	public void focusLost(FocusEvent e) {
		checkValueOnLostFocus();
	}

	private void checkValueOnLostFocus() {		
		if (!getText().matches("[-]?\\d{1,3}")){
			setDefaultValue();
		}
	}

	private void setDefaultValue(){
		setText("1");
	}
	
	public int getValue(){
		return Integer.valueOf(getText());
	}
}
