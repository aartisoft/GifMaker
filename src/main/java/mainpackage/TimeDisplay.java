package mainpackage;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTextField;

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

public class TimeDisplay extends JLabel{
	private static final Dimension labelDims = new Dimension(90, 30);
	public TimeDisplay(CreatorGui creatorGui) {
		this.setText("00:00:00.000");
		
		this.setMinimumSize(labelDims);
		this.setMaximumSize(labelDims);
		this.setPreferredSize(labelDims);
	}
	
	public void setTime(String time) {
		Alert.consoleLog("Setting timeDisplay to: " + time);
		this.setText(time);
	}
}
