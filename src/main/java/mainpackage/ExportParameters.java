package mainpackage;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;

import mainpackage.Configs.AudioMode;
import mainpackage.Configs.OutputFormat;

public class ExportParameters {
	private static File inputFile = null;
	private static File outputFile = null;
	private static String startTimeString = null;
	private static long startTimeLong = 0;
	private static String endTimeString = null;
	private static long endTimeLong = 0;
	private static Boolean reverse = false;
	// private Boolean audio = false;
	private static AudioMode audio = AudioMode.NOAUDIO;
	private static OutputFormat outputformat = OutputFormat.WEBM;
	// private Integer repeat = null;
	private static Dimension cropDimensions = null;
	private static Point pointA = null;
	private static Point pointB = null;

	/*
	 * ExportParameters(File input) { setInputFile(input);
	 * Alert.consoleLog(inputFile.getAbsolutePath()); }
	 */

	protected static void setOutputFormat(Configs.OutputFormat format) {
		outputformat = format;
		Alert.consoleLog("Output format set to " + format);
	}

	protected static String getOutputFormat() {
		return outputformat.toString();
	}

	public static ArrayList<String> getCommandsArray() {
//		printAllParameters();
		ArrayList<String> commands = new ArrayList<>();
		if (outputformat == OutputFormat.WEBM) {

			StringBuffer firstPass = new StringBuffer("ffmpeg");
			if (startTimeString != null && endTimeString != null)
				firstPass.append(" -ss " + getStartTimeString() + " -to " + getEndTimeString());
			firstPass.append(" -i \"" + getInputFilePath() + "\"");
			if (reverse && cropDimensions != null)
				firstPass.append(" -filter_complex \"reverse,fifo[r];[0:v][r]concat=n=2:v=1,crop="
						+ cropDimensions.getWidth() + ":" + cropDimensions.getHeight() + ":" + pointA.getX() + ":"
						+ pointA.getY() + "[v]\" -map \"[v]\"");
			else if (reverse && cropDimensions == null)
				firstPass.append(" -filter_complex \"reverse,fifo[r];[0:v][r]concat=n=2:v=1\"");
			else if (!reverse && cropDimensions != null)
				firstPass.append(" -filter:v \"crop=" + cropDimensions.getWidth() + ":" + cropDimensions.getHeight()
						+ ":" + pointA.getX() + ":" + pointA.getY() + "\" ");

			firstPass.append(Configs.getQualitySettings(Configs.OutputFormat.WEBM, Configs.Pass.PASS1, audio));
			commands.add(firstPass.toString());

			StringBuffer secondPass = new StringBuffer("ffmpeg");
			if (startTimeString != null && endTimeString != null)
				secondPass.append(" -ss " + getStartTimeString() + " -to " + getEndTimeString());
			secondPass.append(" -i \"" + getInputFilePath() + "\"");
			if (reverse && cropDimensions != null)
				secondPass.append(" -filter_complex \"reverse,fifo[r];[0:v][r]concat=n=2:v=1,crop="
						+ cropDimensions.getWidth() + ":" + cropDimensions.getHeight() + ":" + pointA.getX() + ":"
						+ pointA.getY() + "[v]\" -map \"[v]\"");
			else if (reverse && cropDimensions == null)
				secondPass.append(" -filter_complex \"reverse,fifo[r];[0:v][r]concat=n=2:v=1\"");
			else if (!reverse && cropDimensions != null)
				secondPass.append(" -filter:v \"crop=" + cropDimensions.getWidth() + ":" + cropDimensions.getHeight()
						+ ":" + pointA.getX() + ":" + pointA.getY() + "\" ");

			secondPass.append(Configs.getQualitySettings(Configs.OutputFormat.WEBM, Configs.Pass.PASS2, audio));
			secondPass.append(" \"" + outputFile.getAbsolutePath() + "\"");
			secondPass.append(" -y");
			commands.add(secondPass.toString());

			return commands;
		}

		else {
			StringBuffer commandBuff = new StringBuffer("ffmpeg");

			commandBuff.append(" -ss " + getStartTimeString() + " -to " + getEndTimeString());
			commandBuff.append(" -i \"" + getInputFilePath() + "\"");
			commandBuff.append(Configs.getQualitySettings(outputformat, Configs.Pass.NA, audio));
			commandBuff.append(" \"" + outputFile.getAbsolutePath() + "\"");
			commandBuff.append(" -y");

			commands.add(commandBuff.toString());

			return commands;
		}
	}

	protected static File getInputFile() {
		return inputFile;
	}

	protected static String getInputFilePath() {
		return inputFile.getAbsolutePath();
	}

	protected static int setInputFile(File fi) {
		inputFile = fi;
		Alert.consoleLog(inputFile.getAbsolutePath());
		return inputFile.compareTo(fi);
	}

	protected static String getStartTimeString() {
		return startTimeString;
	}

	public static long getStartTimeLong() {
		return startTimeLong;
	}

	protected static void setStartTime(long time) {
		startTimeLong = time;
		startTimeString = Utils.millisToHMS(time);
		// Alert.consoleLog(time);
	}

	protected static String getEndTimeString() {
		return endTimeString;
	}

	protected static void setEndTime(long time) {
		endTimeLong = time;
		endTimeString = Utils.millisToHMS(time);
		// Alert.consoleLog(endTime);
	}

	public static long getEndTimeLong() {
		return endTimeLong;
	}

	protected static Boolean getReverse() {
		return reverse;
	}

	protected static void setReverse(Boolean r) {
		reverse = r;
	}

	protected static Dimension getCropDimensions() {
		return cropDimensions;
	}

	protected static void setCropDimensions(Dimension cd) {
		cropDimensions = cd;
		Alert.consoleLog(cd);
	}
	
	protected static void resetCropDimensions() {
		cropDimensions = null;
	}

	protected static Point getPointA() {
		return pointA;
	}

	protected static void setPointA(Point p) {
		pointA = p;
		
		if (readyForCropDims()) {
			setCropDimensions(new Dimension(Math.abs(pointA.x - pointB.x), Math.abs(pointA.y - pointB.y)));

		}
		
		Alert.consoleLog(pointA);
	}

	protected static void setPointB(Point p) {
		pointB = p;
		
		if (readyForCropDims()) {
			setCropDimensions(new Dimension(Math.abs(pointA.x - pointB.x), Math.abs(pointA.y - pointB.y)));
		}
		
		Alert.consoleLog(pointB);
	}

	protected static void resetPointA() {
		pointA = null;

		Alert.consoleLog("Point A reset.");
	}

	protected static void resetPointB() {
		pointB = null;

		Alert.consoleLog("Point B reset.");
	}

	protected static Point getPointB() {
		return pointB;
	}

	// protected static void setPointB(Point pb) {
	// pointB = pb;
	// if (readyForCropDims())
	// setCropDimensions(new Dimension(Math.abs(pointA.x - pointB.x),
	// Math.abs(pointA.y - pointB.y)));
	// Alert.prompt(pb.toString());
	// }

	protected static void setOutputFile(File f) {
		outputFile = f;
	}

	protected static File getOutputFile() {
		return outputFile;
	}

	private static boolean readyForCropDims() {
		Alert.consoleLog(Boolean.valueOf(pointA != null && pointB != null).toString());
		return pointA != null && pointB != null;
	}

	static long getGifLength() {
		if (startTimeLong > 0 && endTimeLong > 0)
			if (reverse)
				return (endTimeLong - startTimeLong) * 2;
			else
				return endTimeLong - startTimeLong;
		else
			return 0;
	}

	protected static AudioMode getAudioMode() {
		return audio;
	}

	protected static void toogleAudio(boolean isSelected) {
		if (isSelected)
			audio = AudioMode.WITHAUDIO;
		else
			audio = AudioMode.NOAUDIO;

		// Alert.consoleLog("Audio set to: " + audio.toString());
	}

	public static boolean isInputFileSet() {
		return inputFile != null;
	}

	public static void printAllParameters() {
		if (inputFile != null)
			Alert.consoleLog("InputFile: " + inputFile.getName());
		if (outputFile != null)
			Alert.consoleLog("OutputFile: " + outputFile.getName());
		if (startTimeString != null)
			Alert.consoleLog("StartTimeString: " + startTimeString);
		if (endTimeString != null)
			Alert.consoleLog("EndTimeString: " + endTimeString);
		if (reverse != null)
			Alert.consoleLog("Reverse: " + reverse.toString());
		if (audio != null)
			Alert.consoleLog("Audio: " + audio.toString());
		if (outputformat != null)
			Alert.consoleLog("OutputFormat: " + outputformat);
		if (cropDimensions != null)
			Alert.consoleLog("CropDimensions: " + cropDimensions.toString());
		if (pointA != null)
			Alert.consoleLog("PointA: " + pointA.toString());
		if (pointB != null)
			Alert.consoleLog("PointB: " + pointB.toString());
	}

}
