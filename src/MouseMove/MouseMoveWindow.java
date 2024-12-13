package MouseMove;

import java.awt.EventQueue;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.AWTException;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class MouseMoveWindow {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MouseMoveWindow window = new MouseMoveWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MouseMoveWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 243, 181);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("        Which Interval ( Kaç Saniyede Bir)");
		lblNewLabel.setBounds(0, 0, 227, 38);
		frame.getContentPane().add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(72, 36, 89, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("                         To Stop Press S ");
		lblNewLabel_1.setBounds(0, 87, 227, 29);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("      (Durdurmak için S tuşuna basın)");
		lblNewLabel_2.setBounds(10, 117, 207, 14);
		frame.getContentPane().add(lblNewLabel_2);

		JButton btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String textFieldValue = textField.getText();
				int sayı = 0;
				try {
					sayı = Integer.parseInt(textFieldValue);
					if (sayı < 0) {
						JOptionPane.showMessageDialog(null, "Cant be Smaller then 0! (0 Dan küçük olamaz!)");
						return;
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Please enter a number. (Lütfen geçerli bir sayı giriniz!)");
					return;
				}
				try {

					Main.running = true;
					Thread inputThread = new Thread(() -> {
						KeyboardFocusManager.getCurrentKeyboardFocusManager()
								.addKeyEventDispatcher(new KeyEventDispatcher() {
									@Override
									public boolean dispatchKeyEvent(KeyEvent e) {
										if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_S) {
											Main.running = false; //
											JOptionPane.showMessageDialog(null, "Process stopped! (İşlem durduruldu!)");
										}
										return false;
									}
								});

					});

					inputThread.start();
					Main.mouseMover(sayı);

				} catch (AWTException | InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(72, 67, 89, 23);
		frame.getContentPane().add(btnNewButton);
	}
}