package mainpackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

public class AudioCheckBox extends JCheckBox{
	
	public AudioCheckBox(CreatorGui creatorGui) {		
		this.setText("Audio");
		this.setMaximumSize(creatorGui.getEditButtonDims());
		this.setMinimumSize(creatorGui.getEditButtonDims());
		this.setPreferredSize(creatorGui.getEditButtonDims());
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JCheckBox cbLog = (JCheckBox) e.getSource();
				ExportParameters.toogleAudio(cbLog.isSelected());
				Alert.consoleLog("Audio mode: " + ExportParameters.getAudioMode());
			}
		});
	}
}
