package mech;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;

public class SavePNG {
	private static File f;
	
	public static void save(BufferedImage buffImg, String rotatorsCode) {
        try{
        	String home = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
        	File myFolder = new File(home+"/RotatorsPNG");
        	if (!myFolder.exists())
        		myFolder.mkdir();
//        	File directionCharsFolder = new File(myFolder+"/"+rotatorsCode);
//        	if (!directionCharsFolder.exists())
//        		directionCharsFolder.mkdir();
        	f = new File(myFolder+"/pic.png");
            ImageIO.write(buffImg, "PNG", f);
        }
        catch(Exception e){
            e.printStackTrace();
        }
	}
}

