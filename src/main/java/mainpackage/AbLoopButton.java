package mainpackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;

public class AbLoopButton extends JToggleButton {
	private BufferedImage abLoopImg = null;

	AbLoopButton(CreatorGui gui) throws IOException {
//		abLoopImg = ImageIO.read(getClass().getClassLoader().getResource("repeat.png"));

		 this.setMaximumSize(gui.getPlayerButtonDims());
		 this.setMinimumSize(gui.getPlayerButtonDims());
		 this.setPreferredSize(gui.getPlayerButtonDims());
		 this.setText("AB");
//		 this.setToolTipText("AB");
//		this.setIcon(new ImageIcon(abLoopImg));
		// forwardButton.setBorderPainted(false);
		this.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					Double a = (((double) ExportParameters.getStartTimeLong()) / 1000);
					Double b = (((double) ExportParameters.getEndTimeLong()) / 1000);

					Alert.consoleLog(":start-time=" + a + " :stop-time=" + b);
					gui.getMediaPlayerComponent().mediaPlayer().media().play(ExportParameters.getInputFilePath(),
							":start-time=" + a, ":stop-time=" + b);
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					long currentTime = gui.getMediaPlayerComponent().mediaPlayer().status().time();
					gui.getMediaPlayerComponent().mediaPlayer().media().play(ExportParameters.getInputFilePath());
					gui.getMediaPlayerComponent().mediaPlayer().controls().setTime(currentTime);
				}
			}
		});

		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// double a = ExportParameters.getStartTimeLong() / 1000;
				// double b = ExportParameters.getEndTimeLong() / 1000;
				// Alert.consoleLog(a + " " + b);
				// Alert.consoleLog(":start-time="+a + " :stop-time="+b);
				// gui.getMediaPlayerComponent().mediaPlayer().media().play(ExportParameters.getInputFilePath(),
				// ":start-time="+a, ":stop-time="+b);
				// gui.getMediaPlayerComponent().mediaPlayer().controls().setTime(ExportParameters.getStartTimeLong());

				
			}
		});
	}
}
