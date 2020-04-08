package mainpackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class OutputButton extends JButton{
	private ImageIcon outputImg = null;
	private CreatorGui cGui = null;
	OutputButton(CreatorGui creatorGui){
		cGui = creatorGui;
		
		this.setMaximumSize(cGui.getEditButtonDims());
		this.setMinimumSize(cGui.getEditButtonDims());
		this.setPreferredSize(cGui.getEditButtonDims());
		this.setBorderPainted(false);
		this.setText("Output");
//		this.setToolTipText("Output");
		
		try {
			outputImg = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("output.png")));
			this.setIcon(outputImg);
		} catch (IOException | IllegalArgumentException e1) {
			Alert.consoleLog("Image loading problem.");
			Alert.consoleLog(e1.toString());
		}
		// outputButton.setBorderPainted(false);
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Utils.exportGif(cGui);
				toggleOutputButton(Configs.Status.WORKING);
			}
		});
	}
	
	protected void toggleOutputButton(Configs.Status status) {

		if (status == Configs.Status.WORKING) {
			this.setIcon(null);
			this.setEnabled(false);
			// updateOutputButton("test");
		}

		else if (status == Configs.Status.IDLE)
			try {
//				Alert.consoleLog("Output button toggled.");
				// outputImg =
				// ImageIO.read(getClass().getClassLoader().getResource("output.png"));
				this.setText(null);
				this.setIcon(outputImg);
				this.setEnabled(true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}

	protected void updateOutputButton(String string) {
		this.setText(string.toString());
//		cGui.repaintGui();
	}
}
