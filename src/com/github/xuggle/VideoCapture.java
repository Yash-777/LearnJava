package com.github.xuggle;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

import com.github.jdbc.MongoDBFiles;
import com.github.os.threads.ThreadsUtil;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IRational;

public class VideoCapture implements Runnable {
	public String outFile;
	public static String driverSessionId;
	static boolean isSTOPRecording = false;
	
	public static void main(String[] args) {
		VideoCapture recordVideoFile = new VideoCapture("TempVidoeFile.mp4");
		Thread thread = new Thread(recordVideoFile);
		thread.start();
		ThreadsUtil.sleepThread_Milli( 1000 * 10 );
		recordVideoFile.stopRecording();
	}
	
	public VideoCapture(String fileName) {
		outFile = fileName;
	}
	
	public static String getDriverSessionId() {
		return driverSessionId;
	}
	
	public static void setDriverSessionId(String sessionId) {
		driverSessionId = sessionId;
	}

	public void run() {
		StartRecording();
	}
	
	public void setScenarioName(String scenarioName) {
		outFile = scenarioName;
	}
	
	public void StartRecording() {
		try {
			System.out.println("Start Recoring.................................");
			String baseTempPath = System.getProperty("java.io.tmpdir");
			File tempDir = new File(baseTempPath + File.separator + outFile);
			System.out.println("Start tempDir >> " + tempDir.getAbsolutePath());
			Robot robot = new Robot();
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Rectangle screenBounds = new Rectangle(toolkit.getScreenSize());
			
			IMediaWriter writer = ToolFactory.makeWriter(tempDir.getAbsolutePath());
			IRational FRAME_RATE = IRational.make(25, 1);
			System.out.println("MP4 File Location : " + outFile);
			//writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_MPEG4, screenBounds.width, screenBounds.height);
			writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, screenBounds.width, screenBounds.height);
			
			long startTime = System.nanoTime();
			
			while (!isSTOPRecording) {
				BufferedImage screen = robot.createScreenCapture(screenBounds);
				BufferedImage bgrScreen = convertToType(screen, 5);
				writer.encodeVideo(0, bgrScreen, System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
				Thread.sleep((long) (1000.0D / FRAME_RATE.getDouble()));
			}
			if (isSTOPRecording) {
				writer.flush();
				writer.close();
				//MongoDBFiles.upload(tempDir.getAbsolutePath(), new File(outFile).getName(), driverSessionId);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public void stopRecording() {
		System.out.println("Stop Recording...");
		isSTOPRecording = true;
	}
	
	public BufferedImage convertToType(BufferedImage sourceImage, int targetType) {
		if (sourceImage.getType() == targetType) {
			return sourceImage;
		}
		
		BufferedImage image = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), targetType);
		image.getGraphics().drawImage(sourceImage, 0, 0, null);
		
		return image;
	}
	
}