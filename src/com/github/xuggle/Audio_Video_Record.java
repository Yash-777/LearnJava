package com.github.xuggle;

import java.io.File;
import java.io.IOException;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;

/**
 * <a href="https://github.com/lsamayoa/java-screen-recorder">java screen recorder-eclipse-ws</a>
 * 
 * <p> FFmpeg is the leading multimedia framework to decode, encode, transcode, mux, demux, stream, filter and play.
 * <a href="https://github.com/adaptlearning/adapt_authoring/wiki/Installing-FFmpeg">Installing FFmpeg</a>
 * </p>
 * 
 * <ul>Formats
 * <li>Audio Formats .mp3 .m4a .aac .ogg .wma .flac .wav</li>
 * <li>Video Formats .mp4 .m4v .mov .avi .flv .mpg .wmv</li>
 * </ul>
 * @author yashwanth.m
 *
 */
public class Audio_Video_Record {
	public static boolean recordVideo = true, recordAudio = false;
	
	// https://ffmpeg.zeranoe.com/builds/
	public static String FFmpegLocation = "E:\\ffmpeg-win64-static\\bin\\ffmpeg";
	
	public static void main(String[] args) {
		
		//String[] audioVideoFiles = recordAudioVideo();
		
		String audioFile = "E:/TestAudio777_1517233864544.mp4",
				videoFile = "E:/TestVideo777_1517233866685.mp4",
				mergedFile = "E:/TestMerge777_X.mp4";
		//mergeAudioVideo_FFmpeg(audioFile, videoFile, mergedFile);
		
		mergeAudioVideo_xuggle(audioFile, videoFile, mergedFile);
		/*if( audioVideoFiles != null ) {
			String mergedFile = new Audio_Video_Record().getPageName("TestMerge", "");
			System.out.println("Merged File : "+mergedFile);
			mergeAudioVideo_FFmpeg( audioVideoFiles[0], audioVideoFiles[1], mergedFile);
		}*/
	}
	
	// https://stackoverflow.com/a/11878372/5081877
	@SuppressWarnings("deprecation")
	public static void mergeAudioVideo_xuggle(String audioFile, String videoFile, String mergedFile) {
		IMediaWriter mWriter = ToolFactory.makeWriter( mergedFile );
		
		IContainer containerVideo = IContainer.make();
		IContainer containerAudio = IContainer.make();
		
		if (containerVideo.open(videoFile, IContainer.Type.READ, null) < 0)
			throw new IllegalArgumentException("Cant find " + videoFile);
		
		if (containerAudio.open(audioFile, IContainer.Type.READ, null) < 0)
			throw new IllegalArgumentException("Cant find " + audioFile);
		
		int numStreamVideo = containerVideo.getNumStreams();
		int numStreamAudio = containerAudio.getNumStreams();
		
		System.out.println("Number of video streams: "+numStreamVideo + "\n" + "Number of audio streams: "+numStreamAudio );
		
		int videostreamt = -1, audiostreamt = -1;
		
		IStreamCoder  videocoder = null;
		
		for( int i=0; i<numStreamVideo; i++ ) {
			IStream stream = containerVideo.getStream(i);
			IStreamCoder code = stream.getStreamCoder();
			
			if(code.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
				videostreamt = i;
				videocoder = code;
				break;
			}
		}
		
		for( int i=0; i<numStreamAudio; i++ ) {
			IStream stream = containerAudio.getStream(i);
			IStreamCoder code = stream.getStreamCoder();
			
			if(code.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
				audiostreamt = i;
				break;
			}
		}
		
		if (videostreamt == -1) throw new RuntimeException("No video steam found");
		if (audiostreamt == -1) throw new RuntimeException("No audio steam found");
		
		if(videocoder.open()<0 ) throw new RuntimeException("Cant open video coder");
		IPacket packetvideo = IPacket.make();
		
		IStreamCoder audioCoder = containerAudio.getStream(audiostreamt).getStreamCoder();
		
		if(audioCoder.open()<0 ) throw new RuntimeException("Cant open audio coder");
		mWriter.addAudioStream(1, 1, audioCoder.getChannels(), audioCoder.getSampleRate());
		
		mWriter.addVideoStream(0, 0, videocoder.getWidth(), videocoder.getHeight());
		
		IPacket packetaudio = IPacket.make();
		
		while(containerVideo.readNextPacket(packetvideo) >= 0 || containerAudio.readNextPacket(packetaudio) >= 0) {
			
			if( packetvideo.getStreamIndex() == videostreamt ) { //video packet
				IVideoPicture picture = IVideoPicture.make(
						videocoder.getPixelType(), videocoder.getWidth(), videocoder.getHeight());
				int offset = 0;
				
				while ( offset < packetvideo.getSize() ) {
					int bytesDecoded = videocoder.decodeVideo(picture, packetvideo, offset);
					
					if( bytesDecoded < 0 )
						throw new RuntimeException("bytesDecoded not working");
					
					offset += bytesDecoded;
					
					if( picture.isComplete() ) {
						System.out.println( picture.getPixelType() );
						mWriter.encodeVideo(0, picture);
					}
				}
			}
			
			if(packetaudio.getStreamIndex() == audiostreamt) { //audio packet
				
				IAudioSamples samples = IAudioSamples.make(
						512, audioCoder.getChannels(), IAudioSamples.Format.FMT_S32);
				int offset = 0;
				
				while( offset<packetaudio.getSize() ) {
					int bytesDecodedaudio = audioCoder.decodeAudio( samples, packetaudio, offset );
					
					if (bytesDecodedaudio < 0)
						throw new RuntimeException("could not detect audio");
					
					offset += bytesDecodedaudio;
					
					if ( samples.isComplete() ) {
						mWriter.encodeAudio(1, samples);
					}
				}
			}
			
		}
	}
	// https://github.com/adaptlearning/adapt_authoring/wiki/Installing-FFmpeg
	public static void mergeAudioVideo_FFmpeg(String audioFile, String videoFile, String mergedFile) {
		try {
			File finalMergedFile = new File( mergedFile );
			String commandPrompt = "cmd.exe /c start "; // Windows
					// "xterm -e "; // Non Wondows
			String runCommand = commandPrompt
					+ FFmpegLocation
					+ " -i "
					+ audioFile
					+ " -i "
					+ videoFile
					+" -c:v copy -c:a aac -strict experimental -y "
					+ finalMergedFile ;
			System.out.println("Run Command : \n "+ runCommand);
			Process process = Runtime.getRuntime().exec( runCommand );
			int waitFor = process.waitFor();
			System.out.println(" Wait for Command to Execute « " + waitFor);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	public static String[] recordAudioVideo() {
		RecordAudio obj_Audio = new RecordAudio();
		obj_Audio.setAudioFile( obj_Audio.getPageName("TestAudio777", "wav") );
		obj_Audio.startAudioRecording( );
		
		if ( recordAudio ) {
			boolean userInput = obj_Audio.getUserInput("please enter something to START recording.");
			if( userInput ) {
				try {
					
					ThreadGroup group = new ThreadGroup("ThreadGroup1");
					
					RecordVideo obj_Video = new RecordVideo( );
					obj_Video.setVideoFile( obj_Video.getPageName("TestVideo777", "") );
					
					System.out.println("Video File : "+ obj_Video.getVideoFile() );
					Thread videoThread = new Thread( group, obj_Video );
					videoThread.setPriority( Thread.MAX_PRIORITY );
					
					System.out.println("Audio File : "+ obj_Audio.getAudioFile() );
					Thread audioThread = new Thread( group, obj_Audio );
					audioThread.setPriority( Thread.MAX_PRIORITY );
					
					videoThread.start();
					audioThread.start();
					
					boolean userInput2 = obj_Audio.getUserInput("please enter something to STOP recording.");
					if( userInput2 ) {
						
						recordVideo = false;
						obj_Audio.stopRecording();
						// obj.convertToMP3( filePath );
						
						System.out.println("Recording has completed now its time to merge.");
					}
					System.out.println("record is completed.");
					
					String[] audioVideoFiles = new String[2];
					audioVideoFiles[0] = obj_Audio.getAudioFile();
					audioVideoFiles[1] = obj_Video.getVideoFile();
					return audioVideoFiles;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public String getPageName( String samplePageName, String extension ) {
		if( null == extension || extension == "" ) {
			extension = "mp4";
		}
		String tempDir = System.getProperty("java.io.tmpdir").replace("\\", "/");
		System.out.println("Temp Dir : "+ tempDir);
		// Temp Dir : C:/Users/YASHWA~1.M/AppData/Local/Temp/
		
		long currentTimeMillis = System.currentTimeMillis();
		System.out.println("currentTimeMillis : "+ currentTimeMillis);
		
		String fileName = tempDir+samplePageName+"_"+currentTimeMillis+"."+extension;
		System.out.println("File Path : "+fileName);
		
		return fileName;
	}
	
	public boolean getUserInput( String text ) {
		try {
			System.out.println( text );
			System.in.read();
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Causes the currently executing thread to sleep for the specified number of seconds.
	 * @param time	the length of time to sleep in seconds
	 */
	public void sleepThread(long min) {
		try {
			java.util.concurrent.TimeUnit.MINUTES.sleep( min );
			//Thread.sleep( millis );
		} catch (InterruptedException e) {
			System.out.println("Sleep Exception:"+ e.getMessage());
		}
	}
}