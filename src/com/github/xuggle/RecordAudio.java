package com.github.xuggle;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IAddStreamEvent;
import com.xuggle.xuggler.IStreamCoder;

/**
 * RecordAudio is class developed based on the class SimpleAudioRecorder.
 * https://github.com/marytts/marytts/blob/master/marytts-signalproc/src/main/java/marytts/util/data/audio/SimpleAudioRecorder.java
 * <P>Sounds may have different numbers of audio channels: one for mono, two for stereo. The sample rate measures how many "snapshots" (samples) of the sound pressure are taken per second, per channel.
 * <ol>Audio channel - left, right channel
 * <li><b>Mono</b> - In monophonic sound has audio in a single channel. It can be reproduced through several speakers,
 * but all speakers are still reproducing the same copy of the signal.</li>
 * <li><b>stereo</b> - In stereophonic the reproduction of sound using two or more independent audio channels
 * in a way that creates the impression of sound heard from various directions, as in natural hearing.
 * Audio signals are routed through 2 or more channels to simulate depth/direction perception, like in the real world.</li>
 *</ol>
 * In addition to the encoding, the audio format includes other properties that further specify the exact arrangement
 * of the data. These include the number of channels, sample rate, sample size, byte order, frame rate, and frame size.
 * <ul>Link to visit
 * <li><a href="https://www.image-line.com/support/FLHelp/html/recording_audio.html">Image Line</a></li>
 * </ul>
 * @author yashwanth.m
 *
 */
public class RecordAudio extends Audio_Video_Record implements Runnable {

	public RecordAudio() {
	}
	
	/*public RecordAudio(TargetDataLine line, AudioFileFormat.Type targetType, File file) {
		dataLine = line;
		audioStream = new AudioInputStream( line );
		fileType = targetType;
		writeToFile = file;
	}*/
	
	private TargetDataLine dataLine;
	private AudioFileFormat.Type fileType;
	private AudioInputStream audioStream;
	private File writeToFile;
	private String storeAudioStreamFile;
	
	public static void main(String[] args) {
		
		String filePath = "E:\\TestAudio.wav";
		
		RecordAudio obj = new RecordAudio();
		obj.setAudioFile( filePath );
		obj.startAudioRecording( );
		
		System.out.println("File : "+filePath);
		if ( recordAudio ) {
			
			boolean userInput = obj.getUserInput("please enter something to START recording.");
			if( userInput ) {
				try {
					new Thread( obj ).start();
					obj.sleepThread( 1/2 );
					
					boolean userInput2 = obj.getUserInput("please enter something to STOP recording.");
					if( userInput2 ) {
						
						obj.stopRecording();
						// obj.convertToMP3( filePath );
					}
					System.out.println("record is completed.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} else {
			System.out.println("Some thing went wrong please check it.");
		}
	}
	
	/**
	 * Start audio recording.
	 *
	 * @param filename the filename
	 * @return the simple audio recorder
	 */
	public void startAudioRecording( ) {
		
		File outputFile = new File( getAudioFile() );
		
		/* For simplicity, the audio data format used for recording is hard coded here.
		 * We use PCM 44.1 kHz, 16 bit signed, stereo.
		 */
		Encoding encodingAudio = AudioFormat.Encoding.PCM_SIGNED; // pulse-code modulation - PCM_SIGNED 44100.0 Hz
		int sampleSizeInBits = 16, // the number of bits in each sample
				channels = 2, // Mono[1], Stereo[2]- Audio channel [left, right channel's]
				frameSize = 4; // bytes/frame - the number of bytes in each frame
		float frameRate = 44100.0F, sampleRate = 44100.0F; // the number of frames,samples per second
		
		AudioFormat	audioFormat = new AudioFormat(encodingAudio, sampleRate,
				sampleSizeInBits, channels, frameSize, frameRate, false);
		
		/* Now, we are trying to get a TargetDataLine. The TargetDataLine is used later to read audio data from it.
		 * If requesting the line was successful, we are opening it (important!).
		 * 
		 * A target data line is a type of DataLine from which audio data can be read.
		 * The most common example is a data line that gets its data from an audio capture device.
		 */
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
		TargetDataLine targetDataLine = null;
		try {
			targetDataLine = (TargetDataLine) AudioSystem.getLine( info );
			targetDataLine.open(audioFormat);
			
			AudioFileFormat.Type targetType = AudioFileFormat.Type.WAVE;
			
			setDataLine(targetDataLine);
			setFileType(targetType);
			setWriteToFile(outputFile);
			
			recordAudio = true;
		} catch ( LineUnavailableException e ) {
			System.err.println("Audio interface - Please plugin audio line and start recording again.");
			e.printStackTrace();
		} catch ( IllegalArgumentException e ) {
			System.err.println("Audio interface - "
					+ "Using a USB microphone or headset does't contains mouth to read audio stream.");
			e.printStackTrace();
		}
	}
	
	public void stopRecording() {
		dataLine.stop();
		dataLine.flush();
		dataLine.close();
	}

	@Override
	public void run() {
		try {
			dataLine.start();
				
			// Writes a stream of bytes representing an audio file of the specified file type to the external file provided.
			AudioSystem.write(audioStream, fileType, writeToFile);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			
			Thread.interrupted();
		}
	}
	
	public void convertToMP3(String fileName) {
		// create a media reader
		IMediaReader mediaReader = ToolFactory.makeReader( fileName );
		
		File newFile = new File( fileName + ".mp3" );
		// create a media writer
		IMediaWriter mediaWriter = ToolFactory.makeWriter(newFile.getAbsolutePath(), mediaReader);

		// add a writer to the reader, to create the output file
		mediaReader.addListener(mediaWriter);

		// add a IMediaListner to the writer to change bit rate
		mediaWriter.addListener(new MediaListenerAdapter() {
			@Override
			public void onAddStream(IAddStreamEvent event) {
				IStreamCoder streamCoder = event.getSource().getContainer()
						.getStream(event.getStreamIndex()).getStreamCoder();
				streamCoder.setFlag(IStreamCoder.Flags.FLAG_QSCALE, false);
				// setting it to 32kbps
				streamCoder.setBitRate(320000);
				streamCoder.setBitRateTolerance(0);
			}
		});

		// read and decode packets from the source file and
		// and dispatch decoded audio and video to the writer
		while (mediaReader.readPacket() == null);
	}
	
	public TargetDataLine getDataLine() {
		return dataLine;
	}
	public void setDataLine(TargetDataLine dataLine) {
		this.dataLine = dataLine;
		this.audioStream = new AudioInputStream( dataLine );
	}

	public AudioFileFormat.Type getFileType() {
		return fileType;
	}
	public void setFileType(AudioFileFormat.Type fileType) {
		this.fileType = fileType;
	}
	
	public AudioInputStream getAudioStream() {
		return audioStream;
	}
	public void setAudioStream(AudioInputStream audioStream) {
		this.audioStream = audioStream;
	}

	public File getWriteToFile() {
		return writeToFile;
	}
	public void setWriteToFile(File writeToFile) {
		this.writeToFile = writeToFile;
	}
	
	public String getAudioFile() {
		return storeAudioStreamFile;
	}
	public void setAudioFile(String outputFile) {
		String absolutePath = new File( outputFile ).getAbsolutePath();
		this.storeAudioStreamFile = absolutePath;
	}
}
