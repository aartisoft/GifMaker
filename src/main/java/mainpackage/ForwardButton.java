package mainpackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ForwardButton extends JButton{
	private BufferedImage forwardImg = null;
	
	ForwardButton(CreatorGui creatorGui) {
		try {
			forwardImg = ImageIO.read(getClass().getClassLoader().getResource("forward.png"));
			this.setIcon(new ImageIcon(forwardImg));
		} catch (IOException | IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			Alert.consoleLog("Image loading problem.");
			Alert.consoleLog(e1.toString());
			this.setText("Forward");
		}
		
		this.setMaximumSize(creatorGui.getPlayerButtonDims());
		this.setMinimumSize(creatorGui.getPlayerButtonDims());
		this.setPreferredSize(creatorGui.getPlayerButtonDims());
//		this.setToolTipText("Forward");
		// forwardButton.setBorderPainted(false);
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(creatorGui.getSkipLengthInput().getSkipTime()==30)
					creatorGui.getMediaPlayerComponent().mediaPlayer().controls().nextFrame();
				
				else 
					creatorGui.getMediaPlayerComponent().mediaPlayer().controls().skipTime(creatorGui.getSkipLengthInput().getSkipTime());
				
				creatorGui.showCurrentTime();
			}
		});
	}
}
