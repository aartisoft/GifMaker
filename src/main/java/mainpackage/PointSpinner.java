package mainpackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PointSpinner extends JPanel {
	private JSpinner xSpinner = null;
	private JSpinner ySpinner = null;
	private Dimension spinnerDims = new Dimension(60, 30);
	private Dimension panelDims = new Dimension(120, 30);

	public PointSpinner(char name) {
		SpinnerNumberModel modelA = new SpinnerNumberModel();
		modelA.setMinimum(0);
		xSpinner = new JSpinner(modelA);
		xSpinner.setToolTipText(name + ".x");
		// axSpinner.setBounds(10,10,5,3);
		xSpinner.setMaximumSize(spinnerDims);
		xSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if ((name == 'a') || (name == 'A')) {
					ExportParameters.setPointA(new Point(Integer.valueOf(xSpinner.getValue().toString()),
							Integer.valueOf(ySpinner.getValue().toString())));
				}
				else if((name == 'b') || (name == 'B')) {
					ExportParameters.setPointB(new Point(Integer.valueOf(xSpinner.getValue().toString()),
							Integer.valueOf(ySpinner.getValue().toString())));
				}
			}
		});

		SpinnerNumberModel modelB = new SpinnerNumberModel();
		modelB.setMinimum(0);
		ySpinner = new JSpinner(modelB);
		ySpinner.setToolTipText(name + ".y");
		// aySpinner.setBounds(10,10,5,3);
		ySpinner.setMaximumSize(spinnerDims);
		ySpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if ((name == 'a') || (name == 'A')) {
					ExportParameters.setPointA(new Point(Integer.valueOf(xSpinner.getValue().toString()),
							Integer.valueOf(ySpinner.getValue().toString())));
				}
				else if((name == 'b') || (name == 'B')) {
					ExportParameters.setPointB(new Point(Integer.valueOf(xSpinner.getValue().toString()),
							Integer.valueOf(ySpinner.getValue().toString())));
				}
			}
		});

		BoxLayout bl = new BoxLayout(this, BoxLayout.X_AXIS);
		this.setLayout(bl);
		this.add(xSpinner);
		this.add(ySpinner);
		this.setMaximumSize(panelDims);
		this.setMinimumSize(panelDims);
		this.setPreferredSize(panelDims);
		this.setAlignmentX(LEFT_ALIGNMENT);
	}

	public void resetSpinner() {
		updateSpinner(new Point(0, 0));
	}

	public void updateSpinner(Point p) {
		xSpinner.setValue(p.x);
		ySpinner.setValue(p.y);
	}

}
