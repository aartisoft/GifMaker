package mainpackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class TimerButton extends JButton {
	public TimerButton(CreatorGui creatorGui, String buttonText, String type) {
		TimerButton thiss = this;
		this.setText(buttonText);
		this.setMaximumSize(creatorGui.getEditButtonDims());
		this.setMinimumSize(creatorGui.getEditButtonDims());
		this.setPreferredSize(creatorGui.getEditButtonDims());
		this.setBorderPainted(false);
		// startTimeButton.setBorderPainted(false);
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// mediaPlayerComponent.setStandardMediaOptions(":start-time=100",
				// ":stop-time=200");

				// mediaPlayerComponent.mediaPlayer().media().
				// mediaPlayerComponent.mediaPlayer().media().options().add(":start-time=5",
				// ":stop-time=10");

				
			}
		});
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					if(type.equalsIgnoreCase("start")) {
						ExportParameters.setStartTime(Long.valueOf(creatorGui.getMediaPlayerComponent().mediaPlayer().status().time()));
						thiss.setText(ExportParameters.getStartTimeString());
					}
					else if(type.equalsIgnoreCase("end")) {
						ExportParameters.setEndTime(Long.valueOf(creatorGui.getMediaPlayerComponent().mediaPlayer().status().time()));
						thiss.setText(ExportParameters.getEndTimeString());
					}
				}
				else if(e.getButton() == MouseEvent.BUTTON3) {
					if(type.equalsIgnoreCase("start")) {
						creatorGui.getMediaPlayerComponent().mediaPlayer().controls().setTime(ExportParameters.getStartTimeLong());
						creatorGui.showCurrentTime();
					}
					else if(type.equalsIgnoreCase("end")) {
						creatorGui.getMediaPlayerComponent().mediaPlayer().controls().setTime(ExportParameters.getEndTimeLong());
						creatorGui.showCurrentTime();
					}
				}
				
//				Alert.consoleLog(e.getButton());
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}