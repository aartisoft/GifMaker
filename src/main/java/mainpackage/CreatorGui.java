package mainpackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.media.MediaRef;
import uk.co.caprica.vlcj.media.TrackType;
import uk.co.caprica.vlcj.player.base.Marquee;
import uk.co.caprica.vlcj.player.base.MarqueePosition;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

public class CreatorGui extends JPanel{
	private static EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private OutputButton outputButton = null;
	private SkipLengthInput skipLengthInput = null;
	private JSlider seekBar = null;
	private TimeDisplay timeDisplay = null;
	private PointSpinner aSpinner = null;
	private PointSpinner bSpinner = null;
	private Dimension mediaPlayerDim = null;
	private Dimension orgVideoDim = null;
	private Dimension scaledVideoDim = null;
	private PopMenu popMenu = new PopMenu(this);

	private JPanel editingPane = null;
	private JPanel controlsPane = null;
	private JPanel mediaPlayerPane = null;
//	private JPanel mainPane = null;
	private static final Dimension playerButtonDims = new Dimension(70, 30);
	private static final Dimension editButtonDims = new Dimension(120, 30);
	
	private JFrame mainFrame = null;
	private CreatorGui thiss = this;
	
	CreatorGui(JFrame frame){
		mainFrame = frame;
		mediaPlayerPane = new JPanel(new BorderLayout());
		// mediaPlayerPane.setLayout();
		mediaPlayerPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		mediaPlayerPane.setBackground(new Color(0, 247, 255));

		editingPane = new JPanel();
		BoxLayout editingLayout = new BoxLayout(editingPane, BoxLayout.Y_AXIS);
		editingPane.setLayout(editingLayout);
		editingPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		editingPane.setBackground(new Color(255, 0, 98));
		editingPane.setMaximumSize(new Dimension(120, 1000));

		controlsPane = new JPanel();
		BoxLayout controlsLayout = new BoxLayout(controlsPane, BoxLayout.X_AXIS);
		controlsPane.setLayout(controlsLayout);
		controlsPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		controlsPane.setBackground(new Color(252, 186, 3));

		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();

		mediaPlayerComponent.mediaPlayer().input().enableMouseInputHandling(false);
		mediaPlayerComponent.mediaPlayer().input().enableKeyInputHandling(false);
		// mediaPlayerComponent.setMinimumSize(new Dimension(800, 600));
		mediaPlayerComponent.mediaPlayer().events().addMediaPlayerEventListener(new MediaPlayerEventListener() {

			@Override
			public void volumeChanged(MediaPlayer mediaPlayer, float volume) {
				// TODO Auto-generated method stub

			}

			@Override
			public void videoOutput(MediaPlayer mediaPlayer, int newCount) {
				// TODO Auto-generated method stub

			}

			@Override
			public void titleChanged(MediaPlayer mediaPlayer, int newTitle) {
				// TODO Auto-generated method stub
			}

			@Override
			public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
				// TODO Auto-generated method stub
//				timeDisplay.setTime(Utils.millisToHMS(newTime));
			}

			@Override
			public void stopped(MediaPlayer mediaPlayer) {
				// TODO Auto-generated method stub

			}

			@Override
			public void snapshotTaken(MediaPlayer mediaPlayer, String filename) {
				// TODO Auto-generated method stub

			}

			@Override
			public void seekableChanged(MediaPlayer mediaPlayer, int newSeekable) {
				// TODO Auto-generated method stub

			}

			@Override
			public void scrambledChanged(MediaPlayer mediaPlayer, int newScrambled) {
				// TODO Auto-generated method stub

			}

			@Override
			public void positionChanged(MediaPlayer mediaPlayer, float newPosition) {
				seekBar.setValue(Math.round(newPosition * 100));
			}

			@Override
			public void playing(MediaPlayer mediaPlayer) {
				// TODO Auto-generated method stub

			}

			@Override
			public void paused(MediaPlayer mediaPlayer) {
				// TODO Auto-generated method stub

			}

			@Override
			public void pausableChanged(MediaPlayer mediaPlayer, int newPausable) {
				// TODO Auto-generated method stub

			}

			@Override
			public void opening(MediaPlayer mediaPlayer) {
				// TODO Auto-generated method stub

			}

			@Override
			public void muted(MediaPlayer mediaPlayer, boolean muted) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mediaPlayerReady(MediaPlayer mediaPlayer) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mediaChanged(MediaPlayer mediaPlayer, MediaRef media) {
				// TODO Auto-generated method stub

			}

			@Override
			public void lengthChanged(MediaPlayer mediaPlayer, long newLength) {
				// TODO Auto-generated method stub

			}

			@Override
			public void forward(MediaPlayer mediaPlayer) {
				// TODO Auto-generated method stub

			}

			@Override
			public void finished(MediaPlayer mediaPlayer) {
				// TODO Auto-generated method stub

			}

			@Override
			public void error(MediaPlayer mediaPlayer) {
				// TODO Auto-generated method stub

			}

			@Override
			public void elementaryStreamSelected(MediaPlayer mediaPlayer, TrackType type, int id) {
				// TODO Auto-generated method stub

			}

			@Override
			public void elementaryStreamDeleted(MediaPlayer mediaPlayer, TrackType type, int id) {
				// TODO Auto-generated method stub

			}

			@Override
			public void elementaryStreamAdded(MediaPlayer mediaPlayer, TrackType type, int id) {
				// TODO Auto-generated method stub

			}

			@Override
			public void corked(MediaPlayer mediaPlayer, boolean corked) {
				// TODO Auto-generated method stub

			}

			@Override
			public void chapterChanged(MediaPlayer mediaPlayer, int newChapter) {
				// TODO Auto-generated method stub

			}

			@Override
			public void buffering(MediaPlayer mediaPlayer, float newCache) {
				// TODO Auto-generated method stub

			}

			@Override
			public void backward(MediaPlayer mediaPlayer) {
				// TODO Auto-generated method stub

			}

			@Override
			public void audioDeviceChanged(MediaPlayer mediaPlayer, String audioDevice) {
				// TODO Auto-generated method stub

			}
		});

		mediaPlayerPane.add(mediaPlayerComponent);
		
		this.setLayout(new BorderLayout());
		this.add(mediaPlayerPane, BorderLayout.CENTER);
		this.setBackground(new Color(255, 165, 0));

		setupButtons();

		this.add(controlsPane, BorderLayout.SOUTH);
		this.add(editingPane, BorderLayout.EAST);

		mediaPlayerComponent.mediaPlayer().controls().setRepeat(true);
		mediaPlayerComponent.videoSurfaceComponent().addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// Alert.consoleLog("mouse exited");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// Alert.consoleLog("mouse entered");
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// SwingUtilities.isMiddleMouseButton(e)
				if (SwingUtilities.isRightMouseButton(e)) {
					popMenu.setMouseEvent(e);
//					popMenu.setLocation(e.getPoint());
					popMenu.show(thiss, e.getPoint().x, e.getPoint().y);
					mainFrame.repaint();
					Alert.consoleLog(scalePoint(e.getPoint()).toString());
				} else if (SwingUtilities.isLeftMouseButton(e) && popMenu.isShowing() ) {
					popMenu.setVisible(false);
					mainFrame.repaint();
//					Alert.consoleLog(scalePoint(e.getPoint()).toString());
				} else if (SwingUtilities.isLeftMouseButton(e)) {
					mediaPlayerComponent.mediaPlayer().controls().pause();
				}
			}

		});
	}

	private void setupButtons() {
		try {
			addToControlsPane(new FileSelectorButton(this));
			addToControlsPane(new PlayPauseButton(this));
			addToControlsPane(new RewindButton(this));
			skipLengthInput = new SkipLengthInput(this);
			addToControlsPane(skipLengthInput);
			addToControlsPane(new ForwardButton(this));
			addToControlsPane(new MuteButton(this, mediaPlayerComponent));
			addToControlsPane(new AbLoopButton(this));
			addToControlsPane(new FastButton(this));
			addToControlsPane(new SlowButton(this));
			addToControlsPane(new CloseButton(this));
			seekBar = new Seekbar(mediaPlayerComponent, this);
			addToControlsPane(seekBar);
			timeDisplay = new TimeDisplay(this);
//			addToControlsPane(timeDisplay);

			aSpinner = new PointSpinner('a');
			addToEditingPane(aSpinner);
			bSpinner = new PointSpinner('b');
			addToEditingPane(bSpinner);
			addToEditingPane(new TimerButton(this, "Start Time", "start"));
			addToEditingPane(new TimerButton(this, "End Time", "end"));
//			addToEditingPane(new AbLoopButton(this));
			addToEditingPane(new ReverseCheckBox(this));
			addToEditingPane(new AudioCheckBox(this));
			addToEditingPane(new FormatSelector(this));
			outputButton = new OutputButton(this);
			addToEditingPane(outputButton);

		} catch (IOException ex) {
			Alert.consoleLog("image problem->\n" + ex.toString());
		}
	}
	
	private void addToEditingPane(Component com) {
		editingPane.add(com);
		editingPane.add(Box.createVerticalStrut(2));
	}
	
	private void addToControlsPane(Component com) {
		controlsPane.add(com);
		controlsPane.add(Box.createHorizontalStrut((2)));
	}

	public void calcScaledVideoDims() {
		// video dims: (wi, hi) and define ri = wi / hi
		// mediaPlayer dims: (ws, hs) and define rs = ws / hs
		// rs > ri ? (wi * hs/hi, hs) : (ws, hi * ws/wi)
		if (mediaPlayerComponent.getSize() != null) {
			mediaPlayerDim = mediaPlayerComponent.getSize();

			Double rs = mediaPlayerDim.getWidth() / mediaPlayerDim.getHeight();
			Double ri = orgVideoDim.getWidth() / orgVideoDim.getHeight();

			Dimension result = new Dimension();

			if (rs > ri)
				result.setSize(orgVideoDim.getWidth() * mediaPlayerDim.getHeight() / orgVideoDim.getHeight(),
						mediaPlayerDim.getHeight());
			else if (ri > rs)
				result.setSize(mediaPlayerDim.getWidth(),
						orgVideoDim.getHeight() * mediaPlayerDim.getWidth() / orgVideoDim.getWidth());
			else {
				Alert.consoleLog("rs == ri");
			}

			scaledVideoDim = result;

			printMPDims();
			printVDims();
			printScaledVideoDims();
		}
	}

	public SkipLengthInput getSkipLengthInput() {
		return skipLengthInput;
	}

	public EmbeddedMediaPlayerComponent getMediaPlayerComponent() {
		return mediaPlayerComponent;
	}

	protected OutputButton getOutputButton() {
		return outputButton;
	}

	public void openVideo() {
		if (ExportParameters.isInputFileSet()) {
//			this.setTitle(ExportParameters.getInputFile().getName().toString());
			mediaPlayerComponent.mediaPlayer().media().play(ExportParameters.getInputFilePath());
//			mediaPlayerComponent.mediaPlayer().media().options().add(":start-time=7", ":stop-time=10", "--repeat");

			timeDisplay.setTime(Utils.millisToHMS(3333l));
			Alert.consoleLog("Video duration: " + String.valueOf(mediaPlayerComponent.mediaPlayer().status().length()));
			
			showMarquee(ExportParameters.getInputFile().toString());
			mainFrame.setTitle(ExportParameters.getInputFile().getName());
			triggerScaledVideoDimsCalc();
		}
	}

	public void triggerScaledVideoDimsCalc() {
		if(ExportParameters.isInputFileSet()) {
			class orgVideoDimsFetch implements Runnable {
				public void run() {
					long startTime = System.currentTimeMillis();
					do {
						orgVideoDim = mediaPlayerComponent.mediaPlayer().video().videoDimension();
						Alert.consoleLog("orgVideoDim attempt");
						try {
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e1) {
							Alert.consoleLog(e1.toString());
							e1.printStackTrace();
						}
					} while (orgVideoDim == null && (System.currentTimeMillis() - startTime) < 10000);
					if (orgVideoDim != null)
						calcScaledVideoDims();
				}
			}
			Runnable r = new orgVideoDimsFetch();
			new Thread(r).start();
		}
	}

	void showMarquee(String text) {
		Marquee marquee = Marquee.marquee().text(text).size(30).colour(Color.WHITE).timeout(3000)
				.position(MarqueePosition.BOTTOM).opacity(0.8f).enable();
		mediaPlayerComponent.mediaPlayer().marquee().set(marquee);
	}
	
	void showCurrentTime() {
		showMarquee(Utils.millisToHMS(mediaPlayerComponent. mediaPlayer().status().time()));
	}

	private void printVDims() {
		Alert.consoleLog("Original video dims: " + orgVideoDim.toString());
	}

	private void printMPDims() {
		Alert.consoleLog("Mediaplayer dims: " + mediaPlayerDim.toString());
	}

	private void printScaledVideoDims() {
		Alert.consoleLog("SO scaledDims: " + scaledVideoDim.toString());
	}

	
	public Dimension getPlayerButtonDims() {
		return playerButtonDims;
	}
	
	public Dimension getEditButtonDims() {
		return editButtonDims;
	}
	
	public PointSpinner getAspinner() {
		return aSpinner;
	}

	public PointSpinner getBspinner() {
		return bSpinner;
	}
	
	public Point scalePoint(Point clickPoint) {
		int blackAreaX = ((int) Math.round(mediaPlayerDim.getWidth() - scaledVideoDim.getWidth())) / 2;
		int blackAreaY = ((int) Math.round(mediaPlayerDim.getHeight() - scaledVideoDim.getHeight()) / 2);
		
//		Alert.consoleLog("{A} BlackAreaX/2 : " + blackAreaX);
//		Alert.consoleLog("{A} BlackAreaY/2 : " + blackAreaY);
		
		double x = clickPoint.getX() - blackAreaX;
		double y = clickPoint.getY() - blackAreaY;
		
		Double xScaledMpToSv = Double.valueOf(x);
		Double yScaledMpToSv = Double.valueOf(y);
		
		Alert.consoleLog("Original ax: " + clickPoint.getX() + "\tOriginal ay: " + clickPoint.getY());
		Alert.consoleLog("Adjusted ax: " + xScaledMpToSv + "\tAdjusted ay: " + yScaledMpToSv);
		
		Double xScaledSvToOv = xScaledMpToSv / scaledVideoDim.getWidth() * orgVideoDim.getWidth();
		Double yScaledSvToOv = yScaledMpToSv / scaledVideoDim.getHeight() * orgVideoDim.getHeight();
		
		// Alert.consoleLog("Final point A: " + xScaledSvToOv + "," +
		// yScaledSvToOv);
		
		// pointAbutton.setText((int) Math.round(xScaledSvToOv) + "," +
		// (int) Math.round(yScaledSvToOv));
		Point returnPoint = new Point();
		returnPoint.setLocation(xScaledSvToOv, yScaledSvToOv);
		return returnPoint;
	}
}
