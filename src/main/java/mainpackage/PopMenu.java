package mainpackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class PopMenu extends JPopupMenu {
	private MouseEvent click = null;
	private JPopupMenu dis = this;

	PopMenu(CreatorGui creatorGui) {
		JMenuItem pauseMenuItem = new JMenuItem("Pause");
		JMenuItem pointAMenuItem = new JMenuItem("Set point A");
		JMenuItem pointBMenuItem = new JMenuItem("Set point B");
		JMenuItem resetPointsMenuItem = new JMenuItem("Reset points");

		pointAMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (click != null) {
					// Alert.prompt(click.getPoint().toString());
					ExportParameters.setPointA(creatorGui.scalePoint(click.getPoint()));
					creatorGui.getAspinner().updateSpinner(creatorGui.scalePoint(click.getPoint()));
					dis.setVisible(false);
				}
			}
		});

		pointBMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (click != null) {
					// Alert.prompt(click.getPoint().toString());
					ExportParameters.setPointB(creatorGui.scalePoint(click.getPoint()));
					creatorGui.getBspinner().updateSpinner(creatorGui.scalePoint(click.getPoint()));
					dis.setVisible(false);
				}
			}
		});

		pauseMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				creatorGui.getMediaPlayerComponent().mediaPlayer().controls().pause();
			}
		});

		resetPointsMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				creatorGui.getAspinner().resetSpinner();
				creatorGui.getBspinner().resetSpinner();
				ExportParameters.resetPointA();
				ExportParameters.resetPointB();
				ExportParameters.resetCropDimensions();
			}
		});

//		this.add(pauseMenuItem);
		this.add(pointAMenuItem);
		this.add(pointBMenuItem);
		this.add(resetPointsMenuItem);
	}

	public void setMouseEvent(MouseEvent e) {
		this.click = e;
	}
}
