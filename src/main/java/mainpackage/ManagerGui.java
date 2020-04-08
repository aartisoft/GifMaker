package mainpackage;

//import mainpackage.Alert;

//import javax.rmi.CORBA.Util;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class ManagerGui extends JPanel{
	private Configs cfg = new Configs();
//	private final JFrame f = new JFrame("JSONmanager");
//	private GifsJsonArray gja = new GifsJsonArray();
	private GifsCollection gifsCollection = new GifsCollection();
	private JPanel panel = new JPanel();
	private Integer indexer = 1;
//	private GifUtils gUtils = new GifUtils(gja);
	private List<String> selectedIMGs = new ArrayList<>();
	private List<String> selectedTags = new ArrayList<>();
	private TreeSet<String> tagsCopy = new TreeSet<>();
	private JTextField textInput = new JTextField(20);
	// private AutoCompleteTextArea acta = new AutoCompleteTextArea();
	// private JTextArea textInput = acta.getJTextArea();
	private JButton addBtn = new JButton("add");
	private JButton rmvBtn = new JButton("remove");
	private JButton rfreshBtn = new JButton("refresh");
	private JButton copyBtn = new JButton("copy");
	private JButton pasteBtn = new JButton("paste");
	private JButton renewImgBtn = new JButton("renew img");
	private JButton expBtn = new JButton("export");
	private int rows = (gifsCollection.getNumberOfGifs() / 4) + 1;
	private GridLayout imgsGridLayout = new GridLayout(rows, 4, 3, 3);
	private JPanel imgsPanel = imgsPanel = new JPanel(imgsGridLayout);;
	private Map<String, String> newFiles = null;
	private JPanel newGIFsPanel = null;
	private List<JLabel> listOfLabels = new ArrayList<JLabel>();
	private List<JTextField> listOfTextFields = new ArrayList<JTextField>();

	// private int tempCounter = 2;

	// private java.util.List<JLabel> listOfLabels = new ArrayList<JLabel>();
	// static List<JTextField> listOfTextFields = new ArrayList<JTextField>();

	// private TreeMap<String, Gif> gifsMap = gja.getGifsMap();


	ManagerGui() {
		// int rowNumber = (gja.gifsMap.size() / 4) + 1;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
		}

		setupIMGpanel();
		setupButtonsAndTextField();
		maintainButtonsEnabledStatus();
		// setupNewFilesList();
		setupPanels();
		// utils.createAudioFilesJsonFile();
	}

	private void checkForNewFiles() {
		newFiles = gifsCollection.checkFiles();
		// System.out.println(newFiles.size());
		// JOptionPane.showMessageDialog(f, newFiles.size());
		// alert(Integer.toString(newFiles.size()));
	}

	private void setupIMGpanel() {
		// System.out.println("setupIMGpanel");

		if (newFiles != null) {
			imgsGridLayout.setRows(rows + ((newFiles.size()+1) / 4));
			Alert.consoleLog("new files");
		}
		// System.out.println(gja.getGifsMap().size());
		Alert.consoleLog("Rows: " + imgsGridLayout.getRows() + 
				"\nGifs: " + gifsCollection.getNumberOfGifs());
		setupIMGs();
		maintainButtonsEnabledStatus();
	}

	private void setupPanels() {

		JScrollPane scroller = new JScrollPane(imgsPanel);
		// speed up the scroll
		scroller.getVerticalScrollBar().setUnitIncrement(22);
//		JTabbedPane tabbedPane = new JTabbedPane();
//		tabbedPane.add("imgs", scroller);
//		tabbedPane.add("new gifs", newGIFsPanel);

		JPanel topPanel = new JPanel();
		topPanel.add(textInput);
		topPanel.add(addBtn);
		topPanel.add(rmvBtn);
		topPanel.add(renewImgBtn);
		topPanel.add(rfreshBtn);
		topPanel.add(copyBtn);
		topPanel.add(pasteBtn);
		topPanel.add(expBtn);

		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.add(scroller);
		
//		f.setContentPane(metaPanel);
//		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.setVisible(true);
	}

	private boolean textIsIn() {
		return (!(textInput.getText().equals("")));
	}

	private boolean selectionMade() {
		return (!(selectedIMGs.isEmpty()));
	}

	private void setupButtonsAndTextField() {
		addBtn.setActionCommand("add");
		rmvBtn.setActionCommand("rmv");
		expBtn.setActionCommand("exp");
		rfreshBtn.setActionCommand("rfresh");
		copyBtn.setActionCommand("copy");
		pasteBtn.setActionCommand("paste");
		renewImgBtn.setActionCommand("renewImg");
		ActionListener norris = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().toString().equals("exp")) {
//					gifsCollection.printGifsMap();
					JsonIO.exportMasterJson(gifsCollection);
//					enough to fix tagcopy/paste??
					selectedTags.clear();
					selectedIMGs.clear();
				} else if ((e.getActionCommand().toString().equals("add"))
						|| (e.getActionCommand().toString().equals("rmv"))) {
					Gif jif = null;
					if (!(selectedIMGs.isEmpty())) {
						for (String gifKey : selectedIMGs) {
							jif = (Gif) gifsCollection.getGif(Timestamp.valueOf(gifKey));
							// jif = gja.gifsMap.get(Timestamp.valueOf(gifKey));
							if (e.getActionCommand().toString().equals("add"))
								jif.addTag(textInput.getText());
							else
								jif.removeTag(textInput.getText());
						}
					}
				} else if (e.getActionCommand().toString().equals("rfresh")) {
					/*
					 * if(tempCounter%2==0){ imgsPanel.removeAll();
					 * imgsPanel.revalidate(); } else{ setupIMGpanel();
					 * imgsPanel.revalidate(); }
					 */
					imgsPanel.removeAll();
					checkForNewFiles();
					setupIMGpanel();
					imgsPanel.revalidate();
					// tempCounter++;
				} else if (e.getActionCommand().toString().equals("copy")) {
					tagsCopy = new TreeSet<String>(selectedTags);
//					Alert.timePrompt(tagsCopy.toString());
				} else if (e.getActionCommand().toString().equals("paste")) {
					if (!(selectedIMGs.isEmpty())) {
						for (String gifKey : selectedIMGs) {
							Gif tempGif = (Gif) gifsCollection.getGif(Timestamp.valueOf(gifKey));
							tempGif.addTagsSet(tagsCopy);
							// gja.gifsMap.get(Timestamp.valueOf(gifKey)).addTagsSet(tagsCopy);
						}
					}
					// Alert.prompt(tagsCopy.toString());
				} else if (e.getActionCommand().toString().equals("renewImg")) {
					// Alert.prompt("renewImg()");
					// System.out.println(e.getActionCommand());
					if (!(selectedIMGs.isEmpty())) {
						for (String gifKey : selectedIMGs) {
							Gif bob = (Gif) gifsCollection.getGif(Timestamp.valueOf(gifKey));
//							gUtils.renewImg(bob.getIMGpath());
						}
					}
				}
			};
		};
		addBtn.addActionListener(norris);
		rmvBtn.addActionListener(norris);
		expBtn.addActionListener(norris);
		rfreshBtn.addActionListener(norris);
		copyBtn.addActionListener(norris);
		pasteBtn.addActionListener(norris);
		renewImgBtn.addActionListener(norris);
		textInput.setToolTipText("enter tag");
		textInput.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
				maintainButtonsEnabledStatus();
			}
		});

	}

	private void setupIMGs() {
		// System.out.println("setupIMGs()");
		Alert.consoleLog(".");
		// Alert.consoleLog("cleanModeIsOn: " + cfg.isCleanModeOn());
		TreeMap<Timestamp, Gif> tempGifsMap = gifsCollection.getGifsMap();
		for (Gif g : tempGifsMap.values()) {
			JToggleButton btn = new JToggleButton(g.getIMG());
			Timestamp tempTs = g.getTimeStamp();
//			Date d = new Date(1527855018000L);
			btn.setBorder(BorderFactory.createEmptyBorder());
			btn.setSelectedIcon(g.getSelIMG());
			// System.out.println(btn.getIcon());
			btn.setActionCommand(g.getTimeStamp().toString());
			btn.setToolTipText(g.getTags().toString());

			btn.addMouseListener(new MouseListener() {

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
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					if (e.getButton() == 3) {
						String video = g.getGifPath();
						String audio = "media\\audio\\100718_3.mp3";
						String html = "<!DOCTYPE html> \r\n" + "<html lang=\"en\"> \r\n" + "	<body> \r\n"
								+ "		<head>\r\n"
								+ "			<meta content=\"text/html;charset=utf-8\" http-equiv=\"Content-Type\">\r\n"
								+ "			<meta content=\"utf-8\" http-equiv=\"encoding\">\r\n"
								+ "			<script src=\"index2.js\"></script>            \r\n"
								+ "			<link rel=\"stylesheet\" type=\"text/css\" href=\"index2.css\">\r\n"
								+ "		</head>\r\n" + "		\r\n" + "		<div class=\"GifDiv\">\r\n"
								+ "			<video src=\"" + video
								+ "\" id=\"mainGif\" onclick=\"playPause(id)\" autoplay loop muted=\"\"></video>\r\n"
								+ "		</div>\r\n"
								+ "		<input type=\"range\" id=\"speedSlider\" oninput=\"sliderCtrl(this.value)\" min=\"0.1\" max=\"1.0\" step=\"0.01\" value=\"1.0\">\r\n"
								+ "		<audio src=\"" + audio
								+ "\" controls id=\"audioPlayer\" autoplay= loop=\"\">\r\n" + "	</body> \r\n"
								+ "</html>";
						File htmlFile = new File("C:\\Users\\joelh\\Projects\\WebMs\\public_html\\videoPage.html");
						try {
							Files.write(Paths.get(htmlFile.getPath()), html.getBytes());
							Desktop.getDesktop().browse(htmlFile.toURI());
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				}
			});
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// System.out.println(e.getActionCommand());

					if (btn.isSelected()) {
						selectedIMGs.add(e.getActionCommand());
						// Alert.prompt(g.getTags().toString());
						for (String tag : g.getTags()) {
							selectedTags.add(tag);
						}
						// alert(new TreeSet(selectedTags).toString());
						// System.out.println("si size:"+selectedIMGs.size() + "
						// isEmpty:"+selectedIMGs.isEmpty());
					} else {
						int index = selectedIMGs.indexOf(e.getActionCommand());
						selectedIMGs.remove(index);
						for (String tag : g.getTags()) {
							selectedTags.remove(tag);
						}
						// alert(new TreeSet(selectedTags).toString());
						// System.out.println("si size:"+selectedIMGs.size() + "
						// isEmpty:"+selectedIMGs.isEmpty());
					}
					maintainButtonsEnabledStatus();
					// for (String s : selectedIMGs)
					// System.out.println(s);
					// System.out.println("\n");
					Gif jif = (Gif) gifsCollection.getGif(Timestamp.valueOf(e.getActionCommand()));
					/*
					 * List<String> tss = jif.getTags(); for(String s: tss) {
					 * System.out.println(s); }
					 */
				}
			});
			imgsPanel.add(btn);
		}
	}

	private void maintainButtonsEnabledStatus() {
		// System.out.println("sm:"+selectionMade() + " tii:"+textIsIn());
		if (selectionMade() && textIsIn()) {
			toggleButtonsEnabledStatus(true);
		} else
			toggleButtonsEnabledStatus(false);

	}

	private void toggleButtonsEnabledStatus(boolean status) {
		addBtn.setEnabled(status);
		rmvBtn.setEnabled(status);
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			// Clear panel
			panel.removeAll();

			// ImageIcon img = new
			// ImageIcon("C:\\Users\\joelh\\Projects\\WebMs\\public_html\\imgs\\Bey05.png");

			// Create label and text field
			JTextField jTextField = new JTextField();
			jTextField.setSize(100, 200);
			listOfTextFields.add(jTextField);
			// listOfLabels.add(new JLabel(img));

			// Create constraints
			GridBagConstraints textFieldConstraints = new GridBagConstraints();
			GridBagConstraints labelConstraints = new GridBagConstraints();

			// Add labels and text fields
			for (int i = 0; i < indexer; i++) {
				// Text field constraints
				textFieldConstraints.gridx = 1;
				textFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
				textFieldConstraints.weightx = 0.5;
				textFieldConstraints.insets = new Insets(10, 10, 10, 10);
				textFieldConstraints.gridy = i;

				// Label constraints
				labelConstraints.gridx = 0;
				labelConstraints.gridy = i;
				labelConstraints.insets = new Insets(10, 10, 10, 10);

				// Add them to panel
				panel.add(listOfLabels.get(i), labelConstraints);
				panel.add(listOfTextFields.get(i), textFieldConstraints);
			}

			// Align components top-to-bottom
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = indexer;
			c.weighty = 1;
			panel.add(new JLabel(), c);

			// Increment indexer
			indexer++;
			panel.updateUI();
		}
	}

}