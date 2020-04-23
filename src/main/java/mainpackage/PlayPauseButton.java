package mainpackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

public class PlayPauseButton extends JToggleButton{
	private ImageIcon playImg = null;
	private ImageIcon pauseImg = null;
	
	public PlayPauseButton(CreatorGui creatorGui) {
		
		
//		this.setToolTipText("Play/Pause");
		this.setMinimumSize(creatorGui.getPlayerButtonDims());
		this.setMaximumSize(creatorGui.getPlayerButtonDims());
		this.setPreferredSize(creatorGui.getPlayerButtonDims());
		try {
//			BufferedImage test = ImageIO.read(getClass().getResource("play.png"));
			playImg = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("play.png")));
			pauseImg = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("pause.png")));
			this.setIcon(pauseImg);
			this.setSelectedIcon(playImg);
		} catch (IOException | IllegalArgumentException e1) {
			Alert.consoleLog("Image loading problem.");
			Alert.consoleLog(e1.toString());
			this.setText("PP");
		}
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				creatorGui.getMediaPlayerComponent().mediaPlayer().controls().pause();

//				MediaInfo mediaInfo = MediaInfo.mediaInfo(ExportParameters.getInputFilePath());
				/*Process p = null;
				try {
					p = Runtime.getRuntime().exec("MediaInfo \"" + ExportParameters.getInputFilePath() + "\"");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				BufferedReader in = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				
				Alert.consoleLog(in.toString());*/
			}
		});
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(creatorGui.getMediaPlayerComponent().mediaPlayer().status().isSeekable() && creatorGui.getMediaPlayerComponent().mediaPlayer().status().state().toString() == "PAUSED") {
					if(e.getKeyCode()==KeyEvent.VK_LEFT) {
						creatorGui.getMediaPlayerComponent().mediaPlayer().controls().skipTime(-(creatorGui.getSkipLengthInput().getSkipTime()));
					}
					else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
						creatorGui.getMediaPlayerComponent().mediaPlayer().controls().skipTime(creatorGui.getSkipLengthInput().getSkipTime());
					}
				}
			}
		});
	}
	
	public void resetIcon() {
		this.setSelectedIcon(playImg);
	}
}
