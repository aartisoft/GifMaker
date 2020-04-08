package mainpackage;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.math3.geometry.spherical.oned.ArcsSet.Split;

public class Utils {
	protected static boolean IsInteger(String str) {
		return str.matches("^-?\\d+$");
	}

	protected static File promptForOutputFile() {
		// TBD: this may return null
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Webm, Mp4, Gif", "Webm", "Mp4", "Gif");
		JFileChooser outputFileDialog = new JFileChooser(Configs.getSaveDir());
		outputFileDialog.setAcceptAllFileFilterUsed(false);
		// filter.accept()
		outputFileDialog.setFileFilter(filter);
		File outputFile = null;
		if (outputFileDialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			outputFile = outputFileDialog.getSelectedFile();
		}
		return outputFile;
	}

	protected static String millisToHMS(long millis) {
//		Alert.consoleLog(millis);
		String hms = String.format("%02d:%02d:%02d.%02d", TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
				TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1),
				TimeUnit.MILLISECONDS.toMillis(millis) % TimeUnit.SECONDS.toMillis(1));
//		Alert.consoleLog(hms);
		return hms;
	}

	protected static long hmsToMillis(String hms) {
		long r = 0;

		r += Long.valueOf(Long.parseLong(hms.substring(3, 5)) * 60000);
		r += Long.valueOf(Long.parseLong(hms.substring(6, 8)) * 1000);
		r += Long.valueOf(Long.parseLong(hms.substring(9, 11)) * 10);

		// Alert.consoleLog("string in: " + hms + " long out: " + r);

		return r;
	}

	protected static Boolean stringBufferContains(StringBuffer sb, String findString) {
		return sb.indexOf(findString) > -1;
	}

	static void stringBufferToFile(StringBuffer sbf) {
		BufferedWriter bwr;
		try {
			// bwr = new BufferedWriter(
			// new FileWriter(new File(new
			// SimpleDateFormat("HHmmss-ddMMyyyy'.txt'").format(new Date()))));
			bwr = new BufferedWriter(new FileWriter(new File("log.txt")));
			// write contents of StringBuffer to a file
			bwr.write(sbf.toString());

			// flush the stream
			bwr.flush();

			// close the stream
			bwr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void openGifInBrowser() {
		String video = "media\\GIFs\\" + ExportParameters.getOutputFile().getName();
		String audio = "media\\audio\\151018_02.mp3";
		String html = "<!DOCTYPE html> \r\n" + "<html lang=\"en\"> \r\n" + "	<body> \r\n" + "		<head>\r\n"
				+ "			<meta content=\"text/html;charset=utf-8\" http-equiv=\"Content-Type\">\r\n"
				+ "			<meta content=\"utf-8\" http-equiv=\"encoding\">\r\n"
				+ "			<script src=\"index2.js\"></script>            \r\n"
				+ "			<link rel=\"stylesheet\" type=\"text/css\" href=\"index2.css\">\r\n" + "		</head>\r\n"
				+ "		\r\n" + "		<div class=\"GifDiv\">\r\n" + "			<video src=\"" + video
				+ "\" id=\"mainGif\" onclick=\"playPause(id)\" controls autoplay loop></video>\r\n" + "		</div>\r\n"
				+ "		<input type=\"range\" id=\"speedSlider\" oninput=\"sliderCtrl(this.value)\" min=\"0.1\" max=\"1.0\" step=\"0.01\" value=\"1.0\">\r\n"
				+ "		<audio src=\"" + audio + "\" controls id=\"audioPlayer\" autoplay= loop=\"\">\r\n"
				+ "	</body> \r\n" + "</html>";
		Alert.consoleLog(html);
		File htmlFile = new File("C:\\Users\\joelh\\Projects\\WebMs\\public_html\\videoPage.html");
		try {
			Files.write(Paths.get(htmlFile.getPath()), html.getBytes());
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			Alert.consoleLog("html file problem");
		}
	}

	protected static void openGif() {
		openGifInBrowser();
		// openGifInDefaultApp();
	}

	private static void openGifInDefaultApp() {
		try {
			Alert.consoleLog(new File(ExportParameters.getOutputFile().getAbsolutePath()).toString());
			Desktop.getDesktop().open(new File(ExportParameters.getOutputFile().getAbsolutePath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected static String generateOutputFilename() {
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
		Date date = new Date();
		String today = formatter.format(date);
		Integer gifNumber = 1;
		Pattern pat = Pattern.compile("^([0-9]{6})_([0-9]{2})$");

		try (Stream<Path> walk = Files.walk(Paths.get(Configs.getSaveDir().getPath()))) {

			List<String> result = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());

			// result.forEach(Alert::consoleLog);
			for (int i = 0; i < result.size(); i++) {
				// System.out.println(formatter.format(date));
				if (result.size() > 8
						&& getFileNameWithoutExtension(new File(result.get(i).toString())).contains(today)) {
					String s = getFileNameWithoutExtension(new File(result.get(i).toString()));
					// Alert.consoleLog("today gif found - " +
					// (result.get(i).toString()));
					Matcher matcher = pat.matcher(s);
					// Alert.consoleLog(s + " match? " + matcher.matches());
					if (matcher.matches() && Integer.valueOf(s.substring(7, 9)) >= gifNumber) {
						gifNumber = Integer.valueOf(s.substring(7, 9)) + 1;
					}
				}

				// String str = result.get(i).toString();
				// Alert.consoleLog(str);
				// String[] split = str.split(":");
				// Alert.consoleLog("L: " + split.length);
				// if(spit.length-1 >
				// 0)Alert.consoleLog(spit[spit.length-1].toString());
				// else Alert.consoleLog("-"+spit.toString());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (gifNumber < 10) {
			return formatter.format(date) + "_0" + gifNumber.toString();
		} else
			return formatter.format(date) + "_" + gifNumber.toString();
	}

	private static String getFileNameWithoutExtension(File file) {
		String fileName = "";

		try {
			if (file != null && file.exists()) {
				String name = file.getName();
				fileName = name.replaceFirst("[.][^.]+$", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fileName = "";
		}

		return fileName;

	}

	protected static void gifAcceptancePrompt() {
		String[] options = {"Keep it", "Delete it"};
		int x = JOptionPane.showOptionDialog(null, "Cesar?", "Yea or nay", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		Boolean keep = x == 0;
		if (!keep) {
			deleteFile(ExportParameters.getOutputFile());
		}
	}

	public static void deleteFile(File f) {
		Boolean deleteSuccess = f.delete();
		// Alert.timePrompt(f.getName() + " deleted: " + deleteSuccess);
		Alert.timePrompt(deleteSuccess ? f.getName() + " deleted" : "Deletion of " + f.getName() + " failed.");
	}

	public static void exportGif(CreatorGui cGui) {
		// Alert.consoleLog(".");
		// g.setOutputFile(Utils.promptForOutputFile());
		ExportParameters.setOutputFile(new File(Configs.getSaveDir() + "\\" + Utils.generateOutputFilename() + "."
				+ ExportParameters.getOutputFormat().toLowerCase()));
		// g.getCommandsArray();
		// gq.add(xp);
		Runnable r = new ExportThread(cGui);
		new Thread(r).start();
	}

	static class ExportThread implements Runnable {
		private CreatorGui cGui = null;
		ExportThread(CreatorGui cg) {
			cGui = cg;
			// Alert.consoleLog("..");
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// Alert.consoleLog("...");
			Boolean gifExportSuccess = executeCommand();
			Alert.consoleLog("Gif export successful: " + gifExportSuccess.toString());
			cGui.getOutputButton().toggleOutputButton(Configs.Status.IDLE);

			if (gifExportSuccess) {
				Utils.openGif();
				Utils.gifAcceptancePrompt();
			}
		}

		protected Boolean executeCommand() {
			ArrayList<String> commands = ExportParameters.getCommandsArray();
			// Alert.consoleLog(".");

			Alert.consoleLog(commands.toString());
			StringBuffer exeLog = new StringBuffer();
			for (String s : commands)
				exeLog.append(s + "\n");
			for (String command : commands) {
				try {
					Process p = Runtime.getRuntime().exec(command);
					BufferedReader in = new BufferedReader(new InputStreamReader(p.getErrorStream()));
					String line = null;
					while ((line = in.readLine()) != null) {
						exeLog.append(line + "\n");

						Pattern pat = Pattern.compile("time=([0-9]{2}):(?:[012345]\\d):(?:[012345]\\d)[.]([0-9]{2})");
						Matcher matcher = pat.matcher(line);
						if (matcher.find()) {
							// Alert.consoleLog("match");
							// Alert.consoleLog(matcher.group());
							// gui.
							// Utils.hmsToMillis(matcher.group().substring(5));
							Double per = ((double) Utils.hmsToMillis(matcher.group().substring(5))
									/ ExportParameters.getGifLength()) * 100;
							cGui.getOutputButton().updateOutputButton(String.format("%.0f", per) + "%");
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			Utils.stringBufferToFile(exeLog);
			if (Utils.stringBufferContains(exeLog, ": No such file or directory")) {
				Alert.consoleLog("ffmpeg: No such file or directory");
				return false;
			} else if (Utils.stringBufferContains(exeLog, "-to value smaller than -ss")) {
				Alert.consoleLog("-to value smaller than -ss; aborting.");
				return false;

			} else if (!Utils.stringBufferContains(exeLog, "video:")) {
				Alert.consoleLog("FFMPEG error.");
				return false;
			} else {
				// Alert.consoleLog(exeLog.toString());
				return true;
			}
		}
	}

}
