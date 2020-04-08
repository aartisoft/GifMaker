package mainpackage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
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
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

public class GifsCollection {

	private TreeMap<Timestamp, Gif> gifsMap = new TreeMap<Timestamp, Gif>(Collections.reverseOrder());
	public TreeSet<String> tags = new TreeSet<String>();

	public GifsCollection() {
		// readJsonFile();
		// sortJSON();
		// fillJsonMap();
		fillJsonMap(JsonIO.getGifsJsonArr());
	}

	private void newGif(String gifName, String extension) {
		if (GifUtils.createThumbnail(gifName, extension)) {
			addGif(new Gif(gifName, extension));
			// gja.writeJson();
		}
	}

	public Gif getGif(Timestamp ts) {
		return gifsMap.get(ts);
	}

	public boolean addGif(Gif gif) {
		gifsMap.put(gif.getTimeStamp(), gif);
		// addToGifsMap(bob.getTimeStamp(), bob);
		// JsonArray ja = (JsonArray) masterJsonObject.get("GIFs");
		// ja.add(bob.getJson());
		return true;
	}

	public TreeMap getGifsMap() {
		return gifsMap;
	}

	public int getNumberOfGifs() {
		return gifsMap.size();
	}

	private void fillJsonMap(JsonArray gifsJsonArr) {
		Alert.consoleLog(gifsJsonArr.toString());
		
//		it comes in a nested JsonArray for some reason, must investigate
		gifsJsonArr = (JsonArray) gifsJsonArr.get(0);
		
		Iterator it = gifsJsonArr.iterator();
		Alert.consoleLog(gifsJsonArr.size());
		JsonObject tmpo = null;
		while (it.hasNext()) {
			tmpo = (JsonObject) it.next();
			String tsStr = tmpo.get("timestamp").toString().substring(1);
			Timestamp ts = Timestamp.valueOf(tsStr.substring(0, tsStr.length() - 1));
			gifsMap.put(ts, new Gif(tmpo));
		}
		populateTags(gifsJsonArr);
	}

	private void populateTags(JsonArray gifsJsonArr) {
		for (Object i : gifsJsonArr) {
			if (i instanceof JsonObject) {
				JsonObject ji = (JsonObject) i;
				JsonArray tagsJArr = (JsonArray) ji.get("tags");
				for (Object o : tagsJArr) {
					// System.out.println(o.toString());
					tags.add(o.toString());
				}
			}
		}
//		printGifsMap();
//		printTagsTree();
	}
	
	public void printGifsMap() {
		for (Gif g : this.gifsMap.values()) {
//			File f = new File(g.getGifPath());
			Alert.consoleLog(g.getGifPath());
		}
	}
	
	private void printTagsTree() {
		for(String s: this.tags)
			Alert.consoleLog(s);
	}

	private boolean listContains(String search, List<String> list) {
		return list.stream().anyMatch(str -> str.equals(search));
	}

	public Optional<String> getFileExtension(String filename) {
		return Optional.ofNullable(filename).filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}

	public Map<String, String> checkFiles() {
		// Alert.consoleLog(".");
		Map<String, String> newFiles = new HashMap<>();
		String gifsFolderPath = Configs.getGifsFolderPath();
		String imgsFolderPath = Configs.getImgsFolderPath();
		File[] gifFiles = new File(gifsFolderPath).listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".webm") || name.toLowerCase().endsWith(".mp4")
						|| name.toLowerCase().endsWith(".gif");
			}
		});
		// List<String> GifFilesList = Arrays.asList(gifFiles).parallelStream()
		// .map(file -> file.getName().substring(0, file.getName().length() -
		// 5)).collect(Collectors.toList());

		File[] imgFilesList = new File(imgsFolderPath).listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".png");
			}
		});
		List<String> imgList = Arrays.asList(imgFilesList).parallelStream()
				.map(file -> file.getName().substring(0, file.getName().length() - 4)).collect(Collectors.toList());

		// for (String file : GifFilesList) {
		// System.out.println(file);
		// Alert.consoleLog("fil:" + file);

		// }
		for (File f : gifFiles) {
			// Alert.consoleLog(f.getName());
			String filename = f.getName().split("\\.")[0];
			String ext = getFileExtension(f.getName()).get();
			if (!(listContains(filename, imgList))) {
				newFiles.put(filename, ext);
				newGif(filename, ext);
				Alert.consoleLog("new file:" + filename + " " + ext);
			}

		}
		Alert.consoleLog("number of new files: " + newFiles.size());
		return newFiles;
	}

}
