package mainpackage;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Alert {

	static void threadMessage(String message) {
		String threadName = Thread.currentThread().getName();
		System.out.format("%s: %s%n", threadName, message);
	}

	public static void OkPrompt(String message) {
		// threadMessage(message);

		// Thread t = new Thread(new MessageLoop());
		// t.start();
		prompter(message);
	}

	public static void OkPrompt(int num) {
		String conv = Integer.toString(num);
		prompter(conv);
	}

	public static void timePrompt(String message) {
		Runnable r = new MessageLoop(message);
		new Thread(r).start();
	}

	public static void consoleLog(Point p, String name) {
		printTimeStamp();
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		System.out.println(element.getClassName() + " : " + element.getMethodName() + "\n");
		System.out.println(name + ": " + p.x + "," + p.y);
		System.out.println("");
		printSeperator();
	}
	
	public static void consoleLog(Point p) {
		printTimeStamp();
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		System.out.println(element.getClassName() + " : " + element.getMethodName() + "\n");
		System.out.println(p.x + "," + p.y);
		System.out.println("");
		printSeperator();
	}
	
	public static void consoleLog(Dimension d) {
		printTimeStamp();
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		System.out.println(element.getClassName() + " : " + element.getMethodName() + "\n");
		System.out.println("Height: " + d.height + "\nWidth: " + d.width);
		System.out.println("");
		printSeperator();
	}

	public static void consoleLog(String message) {
		printTimeStamp();
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		System.out.println(element.getClassName() + " : " + element.getMethodName() + "\n");
		System.out.println(message);
		System.out.println("");
		// StackTraceElement[] stackTrace =
		// Thread.currentThread().getStackTrace();
		printSeperator();
	}

	public static void consoleLog(String message[]) {
		printTimeStamp();
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		System.out.println(element.getClassName() + " : " + element.getMethodName() + "\n");
		for (String s : message)
			System.out.println(s);
		System.out.println("");
		// StackTraceElement[] stackTrace =
		// Thread.currentThread().getStackTrace();
		printSeperator();
	}

	public static void consoleLog(int num) {
		printTimeStamp();
		// StackTraceElement[] stackTrace =
		// Thread.currentThread().getStackTrace();
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		System.out.println(element.getClassName() + " : " + element.getMethodName() + "\n");
		System.out.println(num);
		System.out.println("");
		printSeperator();
	}

	public static void consoleLog(long num) {
		printTimeStamp();
		// StackTraceElement[] stackTrace =
		// Thread.currentThread().getStackTrace();
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		System.out.println(element.getClassName() + " : " + element.getMethodName() + "\n");
		System.out.println(num);
		System.out.println("");
		printSeperator();
	}

	private static void printSeperator() {
		for (int i = 0; i < 100; i++)
			System.out.print("-");
		System.out.println();
	}

	private static void printTimeStamp() {
		DateTimeFormatter timeStampFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
				.withZone(ZoneId.systemDefault());
		System.out.println(timeStampFormat.format(Instant.now()));
		printSeperator();
	}

	private static void prompter(String message) {
		String[] options = {"Ok, sound"};
		int x = JOptionPane.showOptionDialog(null, message, "Gif deletion", JOptionPane.OK_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
	}

	private static class MessageLoop implements Runnable {
		private String message;

		public MessageLoop(String msg) {
			message = msg;
		}

		public void run() {

			final JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);

			final JDialog dialog = new JDialog();
			dialog.setTitle("sup"); //
//			dialog.setModal(true);

			dialog.setContentPane(optionPane);

			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.pack();

			// create timer to dispose of dialog after 5 seconds
			Timer timer = new Timer(4000, new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent ae) {
					dialog.dispose();
				}
			});
			timer.setRepeats(false);
			// the timer should only go off once

			// start timer to close JDialog as dialog modal we must start the timer before its visible 
			timer.start();

			dialog.setVisible(true);
		}
	}
}
