package mainpackage;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class FileSelectorButton extends JButton{
	private BufferedImage fileSelectImg = null;
	
	public FileSelectorButton(CreatorGui creatorGui) throws IOException {
		try {
			fileSelectImg = ImageIO.read(getClass().getClassLoader().getResource("folder.png"));
			this.setIcon(new ImageIcon(fileSelectImg));
		} catch (IllegalArgumentException e1) {
			// e1.printStackTrace();
			this.setText("SF");
		}

		// fileSelectButton.setBorderPainted(false);
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// open file chooser
				FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
				dialog.setMode(FileDialog.LOAD);
				dialog.setVisible(true);
//				currentParameters = new ExportParameters(new File(dialog.getDirectory() + dialog.getFile()));
				File inputFile = new File(dialog.getDirectory() + dialog.getFile());
				if(inputFile.isFile()) {
					if(ExportParameters.setInputFile(inputFile) == 0)
						creatorGui.openVideo();
//					Alert.consoleLog("MediaInfo \"" + ExportParameters.getInputFilePath() + "\"");
				}
				
				// gifs.put(Instant.now(), currentGif);
				// Alert.consoleLog(currentFile);
				// Alert.consoleLog(file);

				
			}
		});
		
		this.setMaximumSize(creatorGui.getPlayerButtonDims());
		this.setMinimumSize(creatorGui.getPlayerButtonDims());
		this.setPreferredSize(creatorGui.getPlayerButtonDims());
//		this.setToolTipText("Select File");
	}
	
}
