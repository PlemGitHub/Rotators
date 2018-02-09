package mech;

import java.awt.GraphicsEnvironment;

public interface Constants {
	
	int WINDOW_WIDTH = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	int WINDOW_HEIGHT = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	
	int COMPONENT_WIDTH = WINDOW_HEIGHT/4;
	int COMPONENT_HEIGHT = COMPONENT_WIDTH/8;
	int SUBCOMPONENT_WIDTH = WINDOW_HEIGHT/16;
	
	int CENTER_X = WINDOW_WIDTH/2;
	int CENTER_Y = WINDOW_HEIGHT/2;
	
	int DELAY = 0;
}
