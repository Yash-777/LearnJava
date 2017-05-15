package com.github.coreconcepts;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class ScreenShort {
	public static void main(String[] args) throws Exception {
		String outFileName = "out.jpg";
		
		// determine current screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		Rectangle screenRect = new Rectangle(screenSize);
		// create screen shot
		Robot robot = new Robot();
		BufferedImage image = robot.createScreenCapture(screenRect);
		// save captured image to PNG file
		ImageIO.write(image, "png", new File(outFileName));
		System.out.println("Saved screen shot (" + image.getWidth() +
		" x " + image.getHeight() + " pixels) to file \"" +outFileName + "\".");
		// use System.exit if the program hangs after writing the file;
		// that's an old bug which got fixed only recently
		// System.exit(0); 
	}
}