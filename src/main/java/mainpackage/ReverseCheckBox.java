package mainpackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

public class ReverseCheckBox extends JCheckBox{
	ReverseCheckBox(CreatorGui creatorGui){
		this.setText("Reverse");
		this.setMaximumSize(creatorGui.getEditButtonDims());
		this.setMinimumSize(creatorGui.getEditButtonDims());
		this.setPreferredSize(creatorGui.getEditButtonDims());
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JCheckBox cbLog = (JCheckBox) e.getSource();
				ExportParameters.setReverse(cbLog.isSelected());
				Alert.consoleLog(".");
				Alert.consoleLog("Reverse parameter set to: " + ExportParameters.getReverse().toString());
			}
		});
	}
}
