package mainpackage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class SkipLengthInput extends JTextField{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5021038592885261950L;
	private int skipTime;
	public SkipLengthInput(CreatorGui creatorGui) {
		JTextField dis = this;
		this.setColumns(6);
		this.setToolTipText("Skip Length");
		
		this.setMaximumSize(creatorGui.getPlayerButtonDims());
		this.setMinimumSize(creatorGui.getPlayerButtonDims());
		this.setPreferredSize(creatorGui.getPlayerButtonDims());
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
//				Alert.consoleLog(dis.getText());
				if(Utils.IsInteger(dis.getText())) {
					skipTime = Integer.valueOf(dis.getText());
				}
				else if(dis.getText().equalsIgnoreCase("N")) {
					skipTime = 30;
				}
				else if(dis.getText().equalsIgnoreCase("K")) {
					skipTime = 1000;
				}
				else if(dis.getText().equalsIgnoreCase("M")) {
					skipTime = 60000;
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public int getSkipTime() {
		return skipTime;
	}
//	public void setSkipTime(int skipTime) {
//		this.skipTime = skipTime;
//	}
	
}
