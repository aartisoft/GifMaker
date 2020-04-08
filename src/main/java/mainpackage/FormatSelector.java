package mainpackage;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

import mainpackage.Configs.OutputFormat;

public class FormatSelector extends JComboBox<String>{
	public FormatSelector(CreatorGui creatorGui) {
		String[] format = {"webm", "mp4"};
		for (String s : format) {
			this.addItem(s);		
		}
		this.setSelectedIndex(0);
		this.setMaximumSize(creatorGui.getEditButtonDims());
		this.setMinimumSize(creatorGui.getEditButtonDims());
		this.setPreferredSize(creatorGui.getEditButtonDims());
		this.setBorder(null);
//		Alert.consoleLog(String.valueOf(this.getAlignmentY()));
		this.setAlignmentX(LEFT_ALIGNMENT);
//		Alert.consoleLog(String.valueOf(this.getAlignmentY()));
		JComboBox<String> dis = this;
		
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				Alert.consoleLog(dis.getSelectedItem().toString().compareToIgnoreCase(OutputFormat.WEBM.toString() ));
				switch (dis.getSelectedItem().toString()) {
					case "mp4" :
						ExportParameters.setOutputFormat(OutputFormat.MP4);
						break;
					case "webm" :
						ExportParameters.setOutputFormat(OutputFormat.WEBM);
						break;

					default :
						break;
				}
				Alert.consoleLog("OutputFormat set to: " + ExportParameters.getOutputFormat());
//				gui.getCurrentGif().setOutputFormat(OutputFormat.MP4);
//				dis.getSelectedItem().toString() == "MP4" ? gui.getCurrentGif().setOutputFormat(OutputFormat.MP4) : gui.getCurrentGif().setOutputFormat(OutputFormat.WEBM);
			}
		});
	}
}
