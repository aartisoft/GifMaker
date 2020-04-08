package mainpackage;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GridBagConstraintsFactory extends GridBagConstraints{

	private final double compWeight = 0.1;
	private final int insetsInt = 2;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8336199443442025937L;
	
	public GridBagConstraintsFactory(int x, int y, boolean fill) {
		fillBasics(x, y, fill);
	}

	public GridBagConstraintsFactory(int x, int y, boolean fill, int width, int height) {
		fillBasics(x, y, fill);

		this.gridwidth = width;
		this.gridheight = height;
		
	}
	
	private void fillBasics(int x, int y, boolean fill) {
		this.gridx = x;
		this.gridy = y;
		
		if(fill)
			this.fill = GridBagConstraints.BOTH;
		
		this.insets = new Insets(insetsInt,insetsInt,insetsInt,insetsInt);
		
		this.weightx = compWeight;
		this.weighty = compWeight;
		
		this.ipadx = 1;
		this.ipady = 1;
	}
}
