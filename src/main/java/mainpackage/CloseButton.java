package mainpackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.component.MediaPlayerComponent;

public class CloseButton extends JButton {
	private ImageIcon closeImg = null;
	public CloseButton(CreatorGui creatorGui) {
		EmbeddedMediaPlayerComponent mpc = creatorGui.getMediaPlayerComponent();
		try {
			closeImg = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("close.png")));
			this.setIcon(closeImg);
		} catch (IOException | IllegalArgumentException e1) {
			Alert.consoleLog("Image loading problem.");
			Alert.consoleLog(e1.toString());
			this.setText("Close");
		}
		this.setBorderPainted(false);
		this.setPreferredSize(creatorGui.getPlayerButtonDims());
		this.setMaximumSize(creatorGui.getPlayerButtonDims());
		this.setMinimumSize(creatorGui.getPlayerButtonDims());
//		this.setToolTipText("Close");
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File f = ExportParameters.getInputFile();
				mpc.mediaPlayer().controls().stop();
//				mpc.release();
				inputFileDeletePrompt(f);
			}
		});
	}
	
	private void inputFileDeletePrompt(File f) {
		String[] options = {"Delete it.", "No, keep it."};
		int x = JOptionPane.showOptionDialog(null, "Delete " + ExportParameters.getInputFile().getName() + "?", "Yea or nay", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		Boolean delete = x == 0;
		if (delete) {
//			Alert.consoleLog("Sending file to deletion.");
			Utils.deleteFile(f);
		}
	}
}
