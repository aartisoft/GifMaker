package mainpackage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.Instant;
import java.util.Map;
import java.util.TreeMap;

public class Exiter extends Thread {
//	private Map<Instant, Gif> gifsLog;
//	private GifQueue gq = null;
	public Exiter() {
//		this.gq = gq;
	}

	public void run() {
		// Alert.consoleLog("gifsLog class: " +
		// gifsLog.getClass().toString());
		// Alert.consoleLog("GifsLog saved: " +
		// serializeGifsLog().toString());
	}

	private Boolean serializeGifsLog() {
		/*
		FileOutputStream outFile;
		ObjectOutputStream out;
		try {
			outFile = new FileOutputStream("GifsLog.ser");
			out = new ObjectOutputStream(outFile);
			out.writeObject(gq);
			out.close();
			outFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert.consoleLog("Problem with gifsLog export");
			return false;
		}
		*/
		return true;
	}
}
