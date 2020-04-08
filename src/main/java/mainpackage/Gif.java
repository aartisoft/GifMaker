package mainpackage;
//import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import javax.swing.*;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Gif {
	private List<String> tags = null;
	// private JSONObject json = null;
//	private ImageIcon IMG = null;
//	private ImageIcon selIMG = null;
//	private String name = null;
	// private Configs configs = new Configs();
//	private String format = null;
	private JsonObject thiss = null;

	
	
	Gif(String fileName, String extension){
		thiss = new JsonObject();
		
		thiss.put("GIFpath", "media\\GIFs\\" + fileName + "." + extension);
		thiss.put("IMGpath", "media\\imgs\\" + fileName + ".png");
		thiss.put("audioPath", "NA");

		JsonArray tagsArr = new JsonArray();
		// tagsArr.addAll(getTagsFromUser(fileName));
		thiss.put("tags", tagsArr);

		thiss.put("timestamp", "\"" + new Timestamp(System.currentTimeMillis()) + "\"");
    }

	Gif(JsonObject jo) {
		thiss = jo;
		
//		Alert.consoleLog(jo.get("GIFpath").toString());
	}

	public String getFormat() {
		return thiss.get("GIFpath").toString().split("\\.")[1];
	}

	public String getName() {
		return thiss.get("IMGpath").toString().substring(13, 22);
	}
	
	public String getFullName() {
		return thiss.get("IMGpath").toString().substring(13);
	}

	public ImageIcon getSelIMG() {
		return new ImageIcon(Configs.getImgSelFolder() + new File(thiss.get("IMGpath").toString()).getName());
	}
	
	public String getSelImgPath() {
		return thiss.get("IMGpath").toString();
	}
	
	public void addTag(String tag) {
		JsonArray jarr = (JsonArray) thiss.get("tags");
		if (!(jarr.contains(tag)))
			jarr.add(tag);
	}

	public void addTagsSet(TreeSet<String> tags) {
		JsonArray jarr = (JsonArray) thiss.get("tags");
		for (String tag : tags) {
			if (!(jarr.contains(tag)))
				jarr.add(tag);
		}
	}

	public void removeTag(String tag) {
		JsonArray jarr = (JsonArray) thiss.get("tags");
		if (jarr.contains(tag))
			jarr.remove(tag);
	}

	public Timestamp getTimeStamp() {
		String tmp = thiss.get("timestamp").toString();
		return Timestamp.valueOf(tmp.substring(1, tmp.length() - 1));
	}

	/*public void setTimeStamp() {
		thiss.put("timestamp", "\"" + new Timestamp(System.currentTimeMillis()) + "\"");
	}*/

	public ImageIcon getIMG() {
		return new ImageIcon(Configs.getPublicFolder() + thiss.get("IMGpath"));
	}

	public String getGifPath() {
		return thiss.get("GIFpath").toString();
	}

	public String getIMGpath() {
		return thiss.get("IMGpath").toString();
	}

	public List<String> getTags() {
		JsonArray jarr = (JsonArray) thiss.get("tags");
		List<String> list = new ArrayList<String>();
		jarr.forEach((t) -> list.add((String) t));
		return list;
	}

	public JsonObject getJson() {
		return thiss;
	}

	/*
	 * public String getName(){ return name; }
	 */
}
