package mainpackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

public class MuteButton extends JButton {
	private ImageIcon muteImg = null;
	public MuteButton(CreatorGui creatorGui, EmbeddedMediaPlayerComponent mpc) {
		this.setMaximumSize(creatorGui.getPlayerButtonDims());
		this.setMinimumSize(creatorGui.getPlayerButtonDims());
		this.setPreferredSize(creatorGui.getPlayerButtonDims());
		
		try {
			muteImg = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("volume_on.png")));
		} catch (IOException | IllegalArgumentException e1) {
			Alert.consoleLog("Image loading problem.");
			Alert.consoleLog(e1.toString());
			this.setText("Mute");
		}
//		this.setToolTipText("Mute");
		this.setIcon(muteImg);
//		this.setBorderPainted(false);
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mpc.mediaPlayer().audio().mute();
			}
		});
	}
}
