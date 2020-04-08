package mainpackage;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

public class JsonUtils {
	
	

	public static void sortJSON(JsonArray masterJsonObject) {
		// JsonArray jarr = gifsJsonArr;
//		JsonArray jarr = (JsonArray) masterJsonObject.get("GIFs");
		JsonArray jarr = masterJsonObject;
		JsonArray sortedJsonArray = new JsonArray();

		Iterator i = jarr.iterator();

		List<JsonObject> jsonValues = new ArrayList<JsonObject>();

		while (i.hasNext()) {
			jsonValues.add((JsonObject) i.next());
		}
		Collections.sort(jsonValues, new Comparator<JsonObject>() {
			// You can change "Name" with "ID" if you want to sort by ID
			private static final String KEY_NAME = "timestamp";

			@Override
			public int compare(JsonObject a, JsonObject b) {
				Timestamp valA = null;
				Timestamp valB = null;
				String aStr = a.get("timestamp").toString().substring(1);
				String bStr = b.get("timestamp").toString().substring(1);
				// Alert.consoleLog("first:"+aStr.substring(0,aStr.length()-1));

				try {
					// Alert.consoleLog(a.get("timestamp"));
					valA = Timestamp.valueOf(aStr.substring(0, aStr.length() - 1));
					valB = Timestamp.valueOf(bStr.substring(0, bStr.length() - 1));
					// Alert.consoleLog("\n"+valA.toString()+"\n"+valB.toString()+"\n"+valA.compareTo(valB)+"\n");
				} catch (Exception e) {
					// do something
					Alert.consoleLog("problem in sortJSON()\n" + "a:" + a.toString() + " " + valA + " b:"
							+ b.toString() + " " + valB);
				}

				return -valA.compareTo(valB);
				// if you want to change the sort order, simply use the
				// following:
				// return -valA.compareTo(valB);
			}
		});

		// Alert.consoleLog(jsonValues.toString());
		// Iterator i = jarr.iterator();
		for (int p = 0; p < jarr.size(); p++) {
			// Alert.consoleLog(jsonValues.get(p));
			sortedJsonArray.add(jsonValues.get(p));
		}
		// obj.get("GIFs").add(sortedJsonArray);
		// gifsJsonArr = sortedJsonArray;
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		// Alert.consoleLog(element.getClassName() + " : " +
		// element.getMethodName() + "\n");
		Alert.consoleLog("sorted for " + element.getClassName() + " : " + element.getMethodName());
		
		
//		masterJsonObject.put("GIFs", sortedJsonArray);
		masterJsonObject = sortedJsonArray;
	}
}
