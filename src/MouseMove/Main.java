package MouseMove;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Main {

	public static volatile boolean running = false;

	public static void mouseMover(int number) throws AWTException, InterruptedException {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) dim.getWidth() / 2;
		int height = (int) dim.getHeight() / 2;

		Robot r = new Robot();
		r.mouseMove(width, height);

		while (running) {
			for (int i = 0; i < 200; i++) {
				if(!running) return;
				r.mouseMove(width + i, height);
				Thread.sleep(10);
			}
			for (int i = 0; i < 200; i++) {
				if(!running) return;
				r.mouseMove(width - i + 200, height);
				Thread.sleep(10);
			}
			Thread.sleep(number * 1000);
		}

	}
}
