package mainpackage;

import java.io.File;
import java.util.HashMap;

public class Configs {

	private static String publicFolder = "C:\\Users\\joelh\\Projects\\WebMs\\public_html\\";
	private static String mediaFolder = "media\\";
	private static String gifsJsonPath = publicFolder + "webms.json";
	private static String audioFilesJsonPath = publicFolder + "audioFiles.json";
	private static String imgSelFolder = publicFolder + mediaFolder + "imgs\\sel\\";
	private static String gifsFolderPath = publicFolder + mediaFolder + "GIFs\\";
	private static String audioFolderPath = publicFolder + mediaFolder + "audio\\";
	private static String imgsFolderPath = publicFolder + mediaFolder + "imgs\\";
	private static final File saveDir = new File("C:\\Users\\joelh\\Projects\\WebMs\\public_html\\media\\GIFs");

	private static final String firstPassConstantQualityNoAudio = " -c:v libvpx-vp9 -pass 1 -b:v 0 -crf 33 -threads 8 -speed 4 "
			+ "-tile-columns 6 -frame-parallel 1 -an -f webm NUL -y";
	private static final String secondPassConstantQualityNoAudio = " -c:v libvpx-vp9 -pass 2 -b:v 0 -crf 33 -threads 8 -speed 2 "
			+ "-tile-columns 6 -frame-parallel 1 -auto-alt-ref 1 -lag-in-frames 25 -an -f webm";
	private static final String firstPassConstantQualityWithAudio = " -c:v libvpx-vp9 -pass 1 -b:v 0 -crf 33 -threads 8 -speed 4 "
			+ "-tile-columns 6 -frame-parallel 1 -c:a libopus -b:a 192k -f webm NUL -y";
	private static final String secondPassConstantQualityWithAudio = " -c:v libvpx-vp9 -pass 2 -b:v 0 -crf 33 -threads 8 -speed 2 "
			+ "-tile-columns 6 -frame-parallel 1 -auto-alt-ref 1 -lag-in-frames 25 -c:a libopus -b:a 192k -f webm";
	private static final String mp4TrimWithAudio = " -c:v copy -c:a copy";
	private static final String mp4TrimNoAudio = " -c:v copy -an";
	
	@SuppressWarnings("serial")
	private static HashMap<String, String> settings = new HashMap<String, String>() {
		{
			put(OutputFormat.WEBM.name() + Pass.PASS1.name() + AudioMode.NOAUDIO, firstPassConstantQualityNoAudio);
			put(OutputFormat.WEBM.name() + Pass.PASS2.name() + AudioMode.NOAUDIO, secondPassConstantQualityNoAudio);
			put(OutputFormat.WEBM.name() + Pass.PASS1.name() + AudioMode.WITHAUDIO, firstPassConstantQualityWithAudio);
			put(OutputFormat.WEBM.name() + Pass.PASS2.name() + AudioMode.WITHAUDIO, secondPassConstantQualityWithAudio);
			put(OutputFormat.MP4.name() + Pass.NA + AudioMode.WITHAUDIO, mp4TrimWithAudio);
			put(OutputFormat.MP4.name() + Pass.NA + AudioMode.NOAUDIO, mp4TrimNoAudio);
		}
	};

	public static String getPublicFolder() {
		return publicFolder;
	}

	public static String getImgSelFolder() {
		return imgSelFolder;
	}

	public static String getGifsJsonPath() {
		return gifsJsonPath;
	}

	public static String getGifsFolderPath() {
		return gifsFolderPath;
	}

	public static String getImgsFolderPath() {
		return imgsFolderPath;
	}
	
	public static String getMediaFolderPath() {
		return mediaFolder;
	}
	
    public static String getAudioFolderPath(){
    	return audioFolderPath;
    }

    public static String getShortAudioFolderPath(){
    	return mediaFolder + "audio\\";
    }
    
    public static String getAudioJsonPath(){
    	return audioFilesJsonPath;
    }

	public enum Status {
		WORKING, IDLE
	}

	public enum OutputFormat {
		MP4, WEBM
	}

	public enum AudioMode {
		WITHAUDIO, NOAUDIO
	}

	public enum Pass {
		PASS1, PASS2, NA
	}

	private static String desktop = "C:\\Users\\joelh\\Desktop\\";

	static String desktop() {
		return desktop;
	}

	public static String getQualitySettings(Configs.OutputFormat qMode, Configs.Pass pass, Configs.AudioMode audio) {
		return settings.get(qMode.name() + pass.name() + audio);
	}

	public static File getSaveDir() {
		return saveDir;
	}
	/*
	 * public static String getQualitySettings(Configs.OutputFormat qMode,
	 * Boolean audio) { return settings.get(qMode.name()+audio); }
	 */
}
