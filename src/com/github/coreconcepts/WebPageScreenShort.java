package com.github.coreconcepts;

/*
 * Screenshot.java (requires Java 1.4+)
 */
  
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.JFrame;
  
public class WebPageScreenShort {
	
	
	public static void main(String[] args) throws Exception {
		
		String outFileName = "D:\\Screenshots\\webout.jpg";
		
		// String[] arguments = new String[] {"/path/to/executable", "arg0", "arg1", "etc"};
		/*String Url = "http://www.coderanch.com/t/344449/GUI/java/Screen-capture-web-page";
		String[] cmd = new String[5];
			cmd[0] = "cmd.exe";
			cmd[1] = "/C";
			cmd[2] = "rundll32";
			cmd[3] = "url.dll,FileProtocolHandler";
			cmd[4] = Url;
		Process process = Runtime.getRuntime().exec( cmd );  // Opens Default Browser using CommandPrompt.
		 */
		String browserPath = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
		String url = "http://docs.spring.io/spring/docs/3.0.x/spring-framework-reference/html/mvc.html";
		Process process = Runtime.getRuntime().exec(new String[] {browserPath, url});
		
		sleepThread();
		Robot robot = new Robot();
		
		// determine current screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		System.out.format("Screen [W: %d, H: %d \n", screenSize.width, screenSize.height);
		Rectangle screenRect = new Rectangle(0, 0, screenSize.width, screenSize.height);
		BufferedImage image = robot.createScreenCapture(screenRect);
		ImageIO.write(image, "png", new File(outFileName));
		
		System.out.println("Saved screen shot (" + image.getWidth() +" x " + image.getHeight() + " pixels) to file \"" +outFileName + "\".");
		// use System.exit if the program hangs after writing the file; that's an old bug which got fixed only recently

		//screenSHot();
		
		//System.exit(0);
		process.destroy();
	}
	public static void screenSHot() throws AWTException, IOException, UnsupportedFlavorException{
		System.out.println("Alt + PrtScr");
		JFrame frame = new JFrame();
		frame.setSize(200, 200);
		frame.setVisible(true);
		
		sleepThread();
		
		Robot robot = new Robot();
		
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_PRINTSCREEN);
		robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
		robot.keyRelease(KeyEvent.VK_ALT);
		
		sleepThread();
		
		Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
		RenderedImage image = (RenderedImage) t.getTransferData(DataFlavor.imageFlavor);
		
		boolean isSuccess = ImageIO.write(image, "png", new File("D:\\Screenshots\\ZZZZZaltScreen.png"));
		
		System.out.println(isSuccess);
	}
	public static void sleepThread(){
		try {
			Thread.sleep(1000 * 2);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}