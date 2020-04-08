package mainpackage;

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

public class Gui extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2566453456548036405L;

	private JTabbedPane tabs = null;
	private Gui thiss = this;
	private CreatorGui creatorPanel = null;
	private ManagerGui managerPanel = null;


	Gui() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
		} catch (Exception ex) {
			Alert.consoleLog("UIManager problem.");
		}

		this.setBackground(Color.GREEN);

		
		creatorPanel = new CreatorGui(this);
		managerPanel = new ManagerGui();
		tabs=new JTabbedPane();
		tabs.addTab("Create", creatorPanel);
		tabs.addTab("Manage", managerPanel);
		
		// frame stuff
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentResized(ComponentEvent e) {
				creatorPanel.triggerScaledVideoDimsCalc();
				repaintGui();
				// calcScaledVideoDims();
				// Alert.consoleLog("frame resize");
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}
		});
		this.setTitle("GifMaker");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				creatorPanel.getMediaPlayerComponent().release();
				System.exit(0);
			}
		});
		// Runtime.getRuntime().addShutdownHook(new Exiter(ctrl.getGifQueue()));
		this.setContentPane(tabs);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setResizable(false);
		this.setVisible(true);
	}

	
	
	private void listFiles() {
		// create a file that is really a directory
		File aDirectory = new File(".");
		
		// get a listing of all files in the directory
		String[] filesInDir = aDirectory.list();
		
		// sort the list of files (optional)
		// Arrays.sort(filesInDir);
		
		// have everything i need, just print it now
		for (int i = 0; i < filesInDir.length; i++) {
			Alert.consoleLog("file: " + filesInDir[i]);
		}
	}

	public JFrame getFrame() {
		return this;
	}
	
	protected void repaintGui() {
		this.revalidate();
		this.repaint();
	}

	/*

	 */
	
	/*
	 * private void readGifsLog() { // Deserialization try { // Reading the
	 * object from a file FileInputStream gifsLogFile = new
	 * FileInputStream("GifsLog.ser"); ObjectInputStream in = new
	 * ObjectInputStream(gifsLogFile);
	 * 
	 * // Method for deserialization of object gifs = (Map<Instant, Gif>)
	 * in.readObject();
	 * 
	 * in.close(); gifsLogFile.close();
	 * 
	 * Alert.consoleLog("Object has been deserialized.");
	 * Alert.consoleLog("gifs size: " + gifs.size());
	 * Alert.consoleLog(gifs.values().iterator().next().getInputFilePath()); //
	 * System.out.println("a = " + object1.a); // System.out.println("b = " +
	 * object1.b); }
	 * 
	 * catch (IOException | ClassNotFoundException ex) {
	 * Alert.consoleLog("IOException is caught"); } }
	 */
}
