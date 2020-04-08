package mainpackage;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

public class GifUtils {

	private void printJsonArr(JsonArray gifsJsonArr) {
		if(gifsJsonArr != null)
			System.out.println(gifsJsonArr.toString());
	}

	private File[] checkAudioFiles() {
		File[] audioFiles = new File(Configs.getAudioFolderPath()).listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".mp3");
			}
		});

		return audioFiles;
	}

	public void createAudioFilesJsonFile() {
		File[] audioFiles = checkAudioFiles();

		JsonArray audioFilesJson = new JsonArray();

		for (File f : audioFiles) {
			if (!(f.getName().contains("260718_06")) && !(f.getName().contains("MayaDee_02")))
				audioFilesJson.add(Configs.getShortAudioFolderPath() + f.getName());
		}

		JsonObject mainJson = new JsonObject();
		JsonArray gifsJsonArr = new JsonArray();

			FileReader reader = null;
			try {
				reader = new FileReader(Configs.getGifsJsonPath());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

//			mainJson = (JsonObject) parser.parse(reader);
			// System.out.println(mainJson);
//			gifsJsonArr = (JsonArray) mainJson.get("GIFs");
			try {
				gifsJsonArr = Jsoner.deserializeMany(reader);
			} catch (JsonException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				reader.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// System.out.println("file has been
			// read-------------------------------------");

			for (Object i : gifsJsonArr) {
				if (i instanceof JsonObject) {
					JsonObject ji = (JsonObject) i;
					JsonArray tagsJArr = (JsonArray) ji.get("tags");
					for (Object o : tagsJArr) {
						if (o.toString().contains("waa")) {
							// Alert.consoleLog("waa - " +
							// ji.get("GIFpath").toString());
							if (!(ji.get("GIFpath").toString().contains("260718_06"))
									&& !(ji.get("GIFpath").toString().contains("MayaDee_02"))) {
								audioFilesJson.add(ji.get("GIFpath").toString());
							}
						}
					}
				}
			}


		try {
			File file = new File(Configs.getAudioJsonPath());
			if (file.canRead()) {

				FileWriter cout = new FileWriter(file, false);// false to
																// overwrite,
																// true to
																// append
				cout.write(audioFilesJson.toString());
				Alert.consoleLog(
						audioFilesJson.size() + " audioFiles written to " + Configs.getAudioJsonPath());
				cout.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Write error.");
			e.printStackTrace();
		}

	}

	static boolean createThumbnail(String filename, String extension) {
//		creatorGui.getMediaPlayerComponent().mediaPlayer().snapshots().save(new File("bla.png"));
		
		String imgName = filename + ".png";
		String lastLine = null;
		// System.out.println(filename);
		String command = "ffmpeg -ss 00:00:00.10 -i " + Configs.getGifsFolderPath() + filename + "." + extension
				+ " -vframes 1 -s 400:223 -q:v 2 " + Configs.getImgsFolderPath()
				+ imgName/*
							 * , "ffmpeg -i " + Configs.getImgsFolderPath() +
							 * imgName + " -y -vf scale=400:223 " +
							 * Configs.getImgsFolderPath() + imgName
							 */;
		try {
			Alert.consoleLog(command);
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				lastLine = new String(line);
//				Alert.consoleLog(lastLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (lastLine.contains("Output file is empty") || lastLine.contains("No such file or directory")) {
			System.out.println("Thumbnail creation error.");
			return false;
		} else {
			makeBwCopy(new File(Configs.getImgsFolderPath() + imgName));
			return true;
		}
	}

	private static void makeBwCopy(File orgFile) {
		try {
			BufferedImage grayScale = ImageIO.read(orgFile);
			ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
			op.filter(grayScale, grayScale);

			// File resFile = new File(imgSelFolderPath + orgFile.getName());
			File resFile = new File(Configs.getImgSelFolder() + orgFile.getName());
			// System.out.println(resFile.getAbsolutePath());
			ImageIO.write(grayScale, "PNG", resFile);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public boolean renewImg(String fileName) {
		String absoluteFilePath = Configs.getPublicFolder() + fileName;
		File file = new File(absoluteFilePath);
		// Alert.prompt(String.valueOf(absoluteFilePath + " exists: " +
		// file.exists()));
		// System.out.println(absoluteFilePath + " exists: " + file.exists());
		if (file.exists())
			renameImg(Configs.getPublicFolder(), fileName);
		return false;
	}

	private boolean renameImg(String filePath, String filePathName) {
		// absolute path rename file
		File file = new File(filePath + filePathName);
		File newFile = new File(filePath + "");
		// System.out.println(filePathName);

		String[] splitString1 = filePathName.split("/");
		String nameExt = splitString1[splitString1.length - 1];
		// String path2 = "";
		StringBuffer path2 = new StringBuffer();
		for (int i = 0; i < splitString1.length - 1; i++)
			path2.append(splitString1[i] + "/");

		System.out.println("path2: " + path2);
		// for
		// Arrays.toString(Arrays.copyOf(splitString1, splitString1.length-1));
		// System.out.println("path2:"+path2);
		String[] splitString2 = nameExt.split("\\.");
		String name = splitString2[0];
		String ext = splitString2[1];
		// System.out.println(nameExt+"\n"
		// +name+"\n"
		// +ext);

		// System.out.println(filePath + fileName + "\n" +
		// filePath + splitString1[0]);
		// return file.renameTo(newFile);
		return true;
	}
	
	/*private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return null;
    }*/
	
	public Optional<String> getFileExtension(String filename) {
//		Alert.consoleLog(filename);
	    return Optional.ofNullable(filename)
	      .filter(f -> f.contains("."))
	      .map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}
}
