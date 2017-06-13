package com.github.xuggle;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;

/**
 * The class RecordVideoThread will capture number of frames per given frame rate and combines and makes video stream.
 * then save that stream to given file.
 * <pre>
 * {@code
 * writer.addVideoStream(0, 0,ICodec.ID.CODEC_ID_H264, screenBounds.width, screenBounds.height);
 * }
 * </pre>
 * 
 * <ul>
 * For more example related to 
 * <a href="https://github.com/artclarke/xuggle-xuggler/blob/master/src/com/xuggle/mediatool/demos/">
 * xuggle see the below classes:</a>
 * 
 * <li>CaptureScreenToFile.java</li>
 * <li>DecodeAndCaptureFrames.java</li>
 * </ul>
 * @author yashwanth.m
 *
 */
public class RecordVideoThread implements Runnable {
	
	public RecordVideoThread() {
	}
	
	String outputFile;
	public RecordVideoThread( String outputFile ) {
		this.outputFile = outputFile;
	}
	
	public static boolean capturestared = false, isExceptionOccured = false;
	public static boolean record = true;

	public static void main(String[] args) {
		String filePath = "E:\\Test.mp4";
		
		RecordVideoThread recordVideoThread = new RecordVideoThread( new File( filePath ).getAbsolutePath() );
		new Thread( recordVideoThread ).start();
		
		new RecordVideoThread().sleepThread( 1000 * 15  * 1 );
		
		record = false;
		System.out.println("record : "+record);
	}
	
	public static BufferedImage convertToType(BufferedImage sourceImage, int targetType) {
		BufferedImage image;
		// if the source image is already the target type, return the source image
		if (sourceImage.getType() == targetType) {
			image = sourceImage;
		}
		// otherwise create a new image of the target type and draw the new image
		else {
			image = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), targetType);
			image.getGraphics().drawImage(sourceImage, 0, 0, null);
		}
		return image;
	}
	
	@Override
	public void run() {
		try {
			Dimension screenBounds = Toolkit.getDefaultToolkit().getScreenSize();
			double FRAME_RATE = 50;
			
			Robot robot = new Robot();
			Rectangle captureSize = new Rectangle(screenBounds);
			
			// let's make a IMediaWriter to write the file.
			IMediaWriter writer = ToolFactory.makeWriter( new File( outputFile ).getAbsolutePath() );
			
			// We tell it we're going to add one video stream, with id 0,
			// at position 0, and that it will have a fixed frame rate of FRAME_RATE. CODEC_ID_H264,CODEC_ID_MPEG4
			writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, screenBounds.width, screenBounds.height);
			long startTime = System.nanoTime();
			while( record ) {
				capturestared = true;
				// take the screen shot
				BufferedImage screen = robot.createScreenCapture( captureSize );
				// convert to the right image type
				BufferedImage bgrScreen = convertToType(screen, BufferedImage.TYPE_3BYTE_BGR);
				// encode the image to stream #0
				writer.encodeVideo(0, bgrScreen, System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
				sleepThread( (long)(1/FRAME_RATE) ); // millisec 1000 = 1 sec
			}
			// tell the writer to close and write the trailer if  needed
			writer.flush();
			writer.close();
			System.out.println("Recoding completed and saved into file.");
		
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Causes the currently executing thread to sleep for the specified number of seconds.
	 * @param time	the length of time to sleep in seconds
	 */
	public void sleepThread(long sec) {
		try {
			java.util.concurrent.TimeUnit.SECONDS.sleep(5);
			//Thread.sleep( millis );
		} catch (InterruptedException e) {
			System.out.println("Sleep Exception:"+ e.getMessage());
		}
	}
}