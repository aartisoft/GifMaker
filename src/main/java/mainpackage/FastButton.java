package mainpackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class FastButton extends JButton{
	private ImageIcon plusImg = null;
	FastButton(CreatorGui creatorGui) {
		try {
			plusImg = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("plus.png")));
			this.setIcon(plusImg);
		} catch (IOException | IllegalArgumentException e1) {
			Alert.consoleLog("Image loading problem.");
			Alert.consoleLog(e1.toString());
			this.setText("Fast");
		}
		this.setBorderPainted(false);
		this.setPreferredSize(creatorGui.getPlayerButtonDims());
		this.setMaximumSize(creatorGui.getPlayerButtonDims());
		this.setMinimumSize(creatorGui.getPlayerButtonDims());
//		this.setToolTipText("Fast");
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				float currentRate = creatorGui.getMediaPlayerComponent().mediaPlayer().status().rate();
				float changedRate = Float.sum(currentRate, 0.5f);
				creatorGui.getMediaPlayerComponent().mediaPlayer().controls().setRate(changedRate);
				creatorGui.showMarquee(String.valueOf(changedRate));
			}
		});
	}
}
