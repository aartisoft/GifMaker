package mainpackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.Jsoner;

public class JsonIO {

	public static JsonArray getGifsJsonArr() {
		return readJsonFile();
	}

	private static JsonArray readJsonFile() {
//		JsonObject masterJsonObject = new JsonObject();
		JsonArray gifsJsonArr = new JsonArray();
//		JSONParser parser = new JSONParser();

			FileReader reader = null;
			try {
				reader = new FileReader(Configs.getGifsJsonPath());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			masterJsonObject = (JsonObject) parser.parse(reader);
			// System.out.println(mainJson);
//			gifsJsonArr = (JsonArray) masterJsonObject.get("GIFs");
			try {
				gifsJsonArr = Jsoner.deserializeMany(reader);
			} catch (JsonException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println("file has been
			// read-------------------------------------");

			// System.out.println("tags# " + tags.size() + "\n"+ tags);
		return gifsJsonArr;
	}

	public static void exportMasterJson(GifsCollection gifs) {

		JsonArray ja = convertGifsCollectionToJsonArray(gifs);
		Alert.consoleLog(ja.toString());

//		JsonObject masterJsonObject = new JsonObject();
//		masterJsonObject.put("GIFs", ja);
		Alert.consoleLog(ja.toString());

		// System.out.println("mainJsonSize:" + ja.size() + " gifsMapSize:" +
		// gifsMap.size());

		// JsonUtils.sortJSON(masterJsonObject);

		try {
			File file = new File(Configs.getGifsJsonPath());
			if (file.canRead()) {

				// System.out.println(obj.toJSONString());
				FileWriter cout = new FileWriter(file, false);// false to
																// overwrite,
																// true to
																// append
				cout.write(ja.toJson());
				cout.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Write error.");
			e.printStackTrace();
		}
	}

	private static JsonArray convertGifsCollectionToJsonArray(GifsCollection gifs) {
		JsonArray jarr = new JsonArray();

		/*for (Map.Entry<K,V> entry : treeMap.entrySet()) {
	        V value = entry.getValue();
	        K key = entry.getKey();
	   }*/
		
		TreeMap<Timestamp, Gif> tempGifsMap = gifs.getGifsMap();
		Collection c = tempGifsMap.values();
		Iterator itr = c.iterator();
		while (itr.hasNext()) {
			Gif g = (Gif) itr.next();
//			Alert.consoleLog(g.getGifPath());
			jarr.add(g.getJson());
		}
		

		return jarr;
	}
}
