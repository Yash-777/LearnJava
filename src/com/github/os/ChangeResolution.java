package com.github.os;

import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;

import com.github.xuggle.RecordVideoThread;


public class ChangeResolution {
	
	static boolean isFileReadStarted = false;
	
	public static void main(String[] args) {
		PojoData obj = new PojoData( "1024x768", new File("E:\\Test.mp4"), new File("E:\\Test4.mp4") );
		
		FileActions file = new FileActions(obj);
		Thread fileRead = new Thread( file );
		
		new ChangeResolution().displayResoution(obj, fileRead);
		
		try {
			if( fileRead.isAlive() ) {
				System.out.println(" = Thrad has started so joining it to main thread.");
				fileRead.join();
			} else {
				System.out.println("Thrad has not started to join.");
			}
		} catch (InterruptedException e) {
			System.out.println("Thread Exception.");
			e.printStackTrace();
		}
		System.out.println("Write Operation completed. End of main method.");
	}
	
	public void displayResoution( PojoData obj, Thread fileRead ) {
		String[] res  = obj.resolution.split("x");
		int width = Integer.parseInt(res[0]);
		int height = Integer.parseInt(res[1]);
		
		java.awt.GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int current_W = graphicsDevice.getDisplayMode().getWidth();
		int current_H = graphicsDevice.getDisplayMode().getHeight();
		System.out.println("Current Resolution : "+current_W+" * "+current_H);
		
		//java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		javax.swing.JFrame frame = null;
		boolean isResolutionChanged = false;
		try {
			if (current_W == width && current_H == height) {
				/* Perform action after changing the Resolution */
				getChangedResolutionDimentions(obj, fileRead);
				
			} else {
				java.awt.GraphicsConfiguration graphConfig = graphicsDevice.getDefaultConfiguration();
				java.awt.DisplayMode displayMode = new DisplayMode(width, height, 32,DisplayMode.REFRESH_RATE_UNKNOWN);
				
				frame = new JFrame("", graphConfig);
				graphicsDevice.setFullScreenWindow(frame);
		
				if( graphicsDevice.isDisplayChangeSupported() ) {
					System.out.println("Resolution of screen is Modifying");
					graphicsDevice.setDisplayMode(displayMode);
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					
					isResolutionChanged = true;
					/* Perform action after changing the Resolution */
					getChangedResolutionDimentions(obj, fileRead);
				} else {
					System.out.println("Display changes are not supported.");
				}
			}
		} catch(IllegalArgumentException resolution){
			resolution.printStackTrace();
			String errorMesage = "Resolution : "+width+" * "+height+" is not Supported.";
			try {
				System.out.println( errorMesage );
				throw new Exception(errorMesage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if( isResolutionChanged ) frame.dispose(); //program terminates. screen resolution changes to previous resolution.
			System.out.println("Closing the window and clean everything");
		}
	}

	private void getChangedResolutionDimentions(PojoData obj, Thread fileRead) {
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		
		String[] res  = obj.resolution.split("x");
		int width = Integer.parseInt(res[0]);
		int height = Integer.parseInt(res[1]);
		
		if ( screenSize.width == width && screenSize.height == height ) {
			
			RecordVideoThread recordVideoThread = new RecordVideoThread( obj.inputFile.getAbsolutePath() );
			Thread video = new Thread( recordVideoThread );
			video.start();
			
			// Some action to print numbers
			for (int i = 0; i < 10; i++) {
				sleepThread( 1000 * 1 );
				System.out.println("i : "+i);
			}
			
			new RecordVideoThread().sleepThread( 1000 * 15  * 1 );
			
			RecordVideoThread.record = false;
			System.out.println("record : "+RecordVideoThread.record);
			
			try {
				video.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("File Read has Started.");
			fileRead.start();
			
			while( ! isFileReadStarted ) {
				System.out.println("Sleep 100 millis : "+ChangeResolution.isFileReadStarted);
				sleepThread( 100 * 1 );
			}
		}
	}
	
	/**
	 * Causes the currently executing thread to sleep for the specified number of milliseconds.
	 * @param time	the length of time to sleep in milliseconds
	 */
	public static void sleepThread(long millis) {
		try {
			Thread.sleep( millis );
		} catch (InterruptedException e) {
			System.out.println("Sleep Exception:"+ e.getMessage());
		}
	}
}


class PojoData {
	String resolution;
	File inputFile, outputFile;
	
	public PojoData( String resolution, File inputFile, File outputFile ) {
		this.resolution = resolution;
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}
}