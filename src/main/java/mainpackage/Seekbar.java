package mainpackage;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JSlider;

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

public class Seekbar extends JSlider{
	public Seekbar(EmbeddedMediaPlayerComponent mediaPlayerComponent, CreatorGui creatorGui) {
		JSlider js = this;
		this.setPreferredSize(new Dimension(1000, 30));
		this.addMouseMotionListener(new MouseMotionAdapter() {
			
			public void mouseDragged(MouseEvent e) {
				if (js.getValue() / 100 < 1) {
					mediaPlayerComponent.mediaPlayer().controls().setPosition((float) js.getValue() / 100);
					creatorGui.showMarquee(Utils.millisToHMS(mediaPlayerComponent.mediaPlayer().status().time()));
				}
			}
		});
	}
}
