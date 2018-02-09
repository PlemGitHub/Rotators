package visual.myPanels.ent;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JPanel;

import mech.Constants;
import visual.MainPanel;
import visual.myPanels.subEnt.MyJTextField;

@SuppressWarnings("serial")
public class RotatorPanel extends JPanel implements Constants{
	private MainPanel mainPanel;
	private int i;
	/**
	 * Radius.
	 */
	private int radius;
	/**
	 * Positive or negative direction. {-1, 1}
	 */
	private int kDD;
	/**
	 * Step in degrees.
	 */
	private int dD;
	/**
	 * Delay in ticks.
	 */
	private int delay;
	
	private int currentAngle;
	private int currentTick;
	private boolean canMove;
	private Point currentPoint = new Point();
	private Point centerPoint;
	
	private MyJTextField radiusTF, kDDTF, dDTF, delayTF;

	public RotatorPanel(MainPanel mainPanel, int i) {
		this.mainPanel = mainPanel;
		this.i = i;
		setLayout(null);
		setSize(COMPONENT_WIDTH, COMPONENT_HEIGHT);
		setLocation(0, (i+1)*COMPONENT_HEIGHT);
		initProperties();
	}

	private void initProperties() {
		defineCenterPoint();
		createRadius();	// 1
		createKDD();	// 2
		createDD();		// 3
		createDelay();	// 4
		defineCurrentPoint();
	}

	private void defineCenterPoint() {
		if (i == 0){
			centerPoint = new Point(CENTER_X, CENTER_Y);
		}else{
			int previousRadius = mainPanel.getPreviousRotator(i).getRadius();
			int x = mainPanel.getPreviousRotator(i).getCenterPoint().x + previousRadius;
			int y = mainPanel.getPreviousRotator(i).getCenterPoint().y;
			centerPoint = new Point(x, y);
		}
	}

	private void defineCurrentPoint() {
		currentPoint.x = centerPoint.x + radius;
		currentPoint.y = centerPoint.y;
	}

	private void createRadius() {
		radius = (int)(Math.random()*50)+10;
		radiusTF = new MyJTextField(radius, 0);
		add(radiusTF);
	}

	private void createKDD() {
		kDD = (Math.random()<0.5)? -1:1;
		kDD = (int)(kDD*(Math.random()*2+1));
		kDDTF = new MyJTextField(kDD, 1);
		add(kDDTF);
	}

	private void createDD() {
		dD = (int)(Math.random()*5)+1;
//		dD = 1;
		dDTF = new MyJTextField(dD, 2);
		add(dDTF);
	}

	private void createDelay() {
		delay = (int)(Math.random()*3)+1;
//		delay = 1;
		delayTF = new MyJTextField(delay, 3);
		add(delayTF);
	}
	
	public Point getCurrentPoint(){
		return currentPoint;
	}
	
	public Point getCenterPoint(){
		return centerPoint;
	}
	
	public void moveCurrentPoint(){
		increaseTick();
		if (canMove){
			currentAngle += dD*kDD;
//			if (currentAngle > 20000)
//				currentAngle = 0;
			double c = Math.cos(Math.toRadians(currentAngle));
			double s = Math.sin(Math.toRadians(currentAngle));
			currentPoint.x = (int)(centerPoint.x + radius*c);
			currentPoint.y = (int)(centerPoint.y - radius*s);
			canMove = false;
		}
	}

	private void increaseTick() {
		currentTick++;
		if (currentTick == delay){
			currentTick = 0;
			canMove = true;
		}
	}
	
	public void readPropertiesBeforeStart(){
		checkRadiusInput();
		kDD = kDDTF.getValue();
		dD = dDTF.getValue();
		delay = delayTF.getValue();
	}
	
	private void checkRadiusInput() {
		radius = radiusTF.getValue();
		if (radius <= 0){
			radius = 20;
			radiusTF.setText("20");
		}
	}

	public void updateCenterPoint(){
		if (i != 0){
			int x = mainPanel.getPreviousRotator(i).getCurrentPoint().x;
			int y = mainPanel.getPreviousRotator(i).getCurrentPoint().y;
			centerPoint.x = x;
			centerPoint.y = y;
		}
	}
	
	public void disableFields(){
		for (Component c : getComponents()) {
			if (c instanceof MyJTextField)
				c.setEnabled(false);
		}
	}
	
	public void enableFields(){
		for (Component c : getComponents()) {
			if (c instanceof MyJTextField)
				c.setEnabled(true);
		}
	}
	
	public int getRadius(){
		return radius;
	}
	
	public void setCenterPoint(Point p){
		centerPoint = p;
	}
	
	public void resetCurrentAngle(){
		currentAngle = 0;
	}

	public void resetCurrentPosition() {
		defineCenterPoint();
		defineCurrentPoint();
	}
	
	public String getRotatorCode(){
		String r = Integer.toString(radius);
		String k = Integer.toString(kDD);
		String dd = Integer.toString(dD);
		String d = Integer.toString(delay);
		String str = "("+r+","+k+","+dd+","+d+")";
		return str;
	}
}
