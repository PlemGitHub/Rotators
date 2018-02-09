package visual;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import mech.Constants;
import mech.MainTimer;
import mech.SavePNG;
import visual.myPanels.ent.LabelsPanel;
import visual.myPanels.ent.RotatorPanel;

@SuppressWarnings("serial")
public class MainPanel extends JPanel implements Constants{
	private MainPanel mainPanel;
	private MainTimer mainTimer;
	private JButton addBtn, deleteBtn, startBtn, stopBtn, savePNGBtn;
	private ArrayList<RotatorPanel> rotatorPanels = new ArrayList<>();
	private BufferedImage image;
	private JCheckBox linesCB;
	private Point p1, p2;
	
	public MainPanel() {
		mainPanel = this;
		setLayout(null);
		setBackground(Color.WHITE);
		
		for (int i = 0; i < 2; i++) {
			createNewRotator();
		}
		
		LabelsPanel labelsPanel = new LabelsPanel();
		add(labelsPanel);
		
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "exit");
		getActionMap().put("exit", exitAction);
		
		createStartBtn();
		createStopBtn();
		createAddBtn();
		createDeleteBtn();
		createLinesCheckBox();
		createSavePNGBtn();
	}
	
	private void createSavePNGBtn() {
		savePNGBtn = new JButton();
		savePNGBtn.setBounds(COMPONENT_WIDTH, COMPONENT_HEIGHT*5, 100, COMPONENT_HEIGHT);
		savePNGBtn.setAction(savePNGAction);
		savePNGBtn.setText("Save PNG");
		savePNGBtn.setFocusable(false);
		add(savePNGBtn);
	}

	private void createLinesCheckBox() {
		linesCB = new JCheckBox();
		linesCB.setBounds(COMPONENT_WIDTH, COMPONENT_HEIGHT*4, 100, COMPONENT_HEIGHT);
		linesCB.setText("Lines");
		add(linesCB);
	}

	private void createStopBtn() {
		stopBtn = new JButton();
		stopBtn.setBounds(COMPONENT_WIDTH, COMPONENT_HEIGHT*3, 100, COMPONENT_HEIGHT);
		stopBtn.setAction(stopAction);
		stopBtn.setText("Stop");
		stopBtn.setFocusable(false);
		add(stopBtn);
	}

	private void createDeleteBtn() {
		deleteBtn = new JButton();
		deleteBtn.setBounds(COMPONENT_WIDTH, COMPONENT_HEIGHT*2, 100, COMPONENT_HEIGHT);
		deleteBtn.setAction(deleteAction);
		deleteBtn.setText("Delete");
		deleteBtn.setFocusable(false);
		add(deleteBtn);
	}

	private void createAddBtn() {
		addBtn = new JButton();
		addBtn.setBounds(COMPONENT_WIDTH, COMPONENT_HEIGHT, 100, COMPONENT_HEIGHT);
		addBtn.setAction(addAction);
		addBtn.setText("Add");
		addBtn.setFocusable(false);
		add(addBtn);
	}

	private void createStartBtn() {
		startBtn = new JButton();
		startBtn.setBounds(COMPONENT_WIDTH, 0, 100, COMPONENT_HEIGHT);
		startBtn.setAction(startAction);
		startBtn.setText("Start");
		startBtn.setFocusable(false);
		add(startBtn);
	}

	private void createNewRotator() {
		int newPos = rotatorPanels.size();
		RotatorPanel rotatorPanel = new RotatorPanel(this, newPos);
		rotatorPanels.add(rotatorPanel);
		add(rotatorPanel);
	}

	private void deleteLastRotator() {
		int lastIndex = rotatorPanels.size()-1;
		RotatorPanel lastR = rotatorPanels.get(lastIndex);
		rotatorPanels.remove(lastIndex);
		remove(lastR);
		if (lastIndex == 0){
			startBtn.setEnabled(false);
			deleteBtn.setEnabled(false);
			stopBtn.setEnabled(false);
		}
		repaint();
	}

	private Action exitAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	};
	
	private Action startAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			resetAllCurrentAngles();
			resetAllCurrentPositions();
			mainTimer = new MainTimer(DELAY, null, mainPanel);
			mainTimer.addActionListener(mainTimer);
			mainTimer.start();
		}
	}; 
	
	private Action stopAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (mainTimer != null)
				mainTimer.stop();
			enableAllFields();
			p2.x = 0;
		}
	}; 
	
	private Action addAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			createNewRotator();
			startBtn.setEnabled(true);
			deleteBtn.setEnabled(true);
			stopBtn.setEnabled(true);
		}
	}; 
	
	private Action deleteAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			deleteLastRotator();
		}
	}; 
	
	private Action savePNGAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String rotatorsCode = getAllRotatorsCodes();
			SavePNG.save(image, rotatorsCode);
		}
	};

	private String getAllRotatorsCodes() {
		String str = "";
		for (RotatorPanel rotatorPanel : rotatorPanels) {
			str = str+rotatorPanel.getRotatorCode();
		}
		return str;
	}

	public RotatorPanel getPreviousRotator(int i){
		return rotatorPanels.get(i-1);
	}
	
	public void moveAllRotators(){
		for (RotatorPanel rotatorPanel : rotatorPanels) {
			rotatorPanel.updateCenterPoint();
			rotatorPanel.moveCurrentPoint();
		}
	}
	
	public void readAllPropertiesBeforeStart(){
		for (RotatorPanel rotatorPanel : rotatorPanels) {
			rotatorPanel.readPropertiesBeforeStart();;
		}
	}
	
	public void disableAllFields(){
		for (RotatorPanel rotatorPanel : rotatorPanels) {
			rotatorPanel.disableFields();
		}
		disableButtonsOnStart();
	}
	
	public void enableAllFields(){
		for (RotatorPanel rotatorPanel : rotatorPanels) {
			rotatorPanel.enableFields();
		}
		enableButtonsOnStart();
	}
	
	private void resetAllCurrentAngles(){
		for (RotatorPanel rotatorPanel : rotatorPanels) {
			rotatorPanel.resetCurrentAngle();
		}
	}

	private void resetAllCurrentPositions() {
		for (RotatorPanel rotatorPanel : rotatorPanels) {
			rotatorPanel.resetCurrentPosition();
		}
	}
	
	public void drawLastPoint(){
		Graphics g2 = image.getGraphics();
		g2.setColor(Color.BLACK);
		int lastIndex = rotatorPanels.size()-1;
		int x = rotatorPanels.get(lastIndex).getCurrentPoint().x;
		int y = rotatorPanels.get(lastIndex).getCurrentPoint().y;
		if (linesCB.isSelected()){
			if (p2.x == 0){
				p2 = new Point(x, y);
				p1 = (Point)p2.clone();
			}else{
				p1 = (Point)p2.clone();
				p2 = new Point(x, y);
				g2.drawLine(p1.x, p1.y, p2.x, p2.y);
				repaint(p1.x, p1.y, p2.x, p2.y);
			}
		}else{
			g2.drawRect(x, y, 1, 1);
			repaint(x-5, y-5,x+5,y+5);
		}
	}
	
	public void createMyImage(){
		p1 = new Point();
		p2 = new Point();
		int size = 0;
		for (RotatorPanel rotatorPanel : rotatorPanels) {
			size += rotatorPanel.getRadius()*2;
		}
		if (size < WINDOW_HEIGHT)
			image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		else{
			image = new BufferedImage(size+5, size+5, BufferedImage.TYPE_INT_ARGB);
			rotatorPanels.get(0).setCenterPoint(new Point(size/2, size/2));
		}
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(image, COMPONENT_WIDTH, 0, null);
	}
	
	public void disableButtonsOnStart(){
		startBtn.setEnabled(false);
		addBtn.setEnabled(false);
		deleteBtn.setEnabled(false);
		linesCB.setEnabled(false);
	}
	
	public void enableButtonsOnStart(){
		addBtn.setEnabled(true);
		deleteBtn.setEnabled(true);
		linesCB.setEnabled(true);
		if (rotatorPanels.size() != 0)
			startBtn.setEnabled(true);
	}
}
