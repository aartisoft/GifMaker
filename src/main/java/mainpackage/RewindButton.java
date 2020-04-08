package mainpackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

public class RewindButton extends JButton{
	private BufferedImage rewindImg = null;
	public RewindButton(CreatorGui creatorGui) {
		// TODO Auto-generated constructor stub
		try {
			rewindImg = ImageIO.read(getClass().getClassLoader().getResource("rewind.png"));
			this.setIcon(new ImageIcon(rewindImg));
		} catch (IOException | IllegalArgumentException e1) {
			Alert.consoleLog("Image loading problem.");
			Alert.consoleLog(e1.toString());
			this.setText("Rewind");
		}
//		this.setToolTipText("Rewind");
		this.setMaximumSize(creatorGui.getPlayerButtonDims());
		this.setMinimumSize(creatorGui.getPlayerButtonDims());
		this.setPreferredSize(creatorGui.getPlayerButtonDims());
		// rewindButton.setBorderPainted(false);
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				creatorGui.getMediaPlayerComponent().mediaPlayer().controls().skipTime(-(creatorGui.getSkipLengthInput().getSkipTime()));
				creatorGui.showCurrentTime();
			}
		});
	}
}
