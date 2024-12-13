package second;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

public class Main {
    private JFrame frame;
    private JTextField textField;
    public static volatile boolean running = false;
    private boolean keyEventAdded = false;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
            	Main window = new Main();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Main() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 243, 181);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("         Which Interval ( Kaç Saniyede Bir)");
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
        btnNewButton.addActionListener(e -> {
            String textFieldValue = textField.getText();
            int interval;
            try {
                interval = Integer.parseInt(textFieldValue);
                if (interval < 0) {
                    JOptionPane.showMessageDialog(null, "Cant be Smaller than 0! (0 dan küçük olamaz!)");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a number. (Lütfen geçerli bir sayı giriniz!)");
                return;
            }

            running = true;

            // Swing Timer
            Timer timer = new Timer(interval * 10, new ActionListener() {
                int direction = 1; // 1: Right, -1: Left
                int step = 0;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!running) {
                        ((Timer) e.getSource()).stop();
                        return;
                    }
                    try {
                        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                        int width = (int) dim.getWidth() / 2;
                        int height = (int) dim.getHeight() / 2;

                        Robot r = new Robot();
                        r.mouseMove(width + direction * step, height);

                        step += 5; // Move by 5 pixels
                        if (step >= 200) {
                            direction *= -1; // Reverse direction
                            step = 0;
                        }
                    } catch (AWTException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            timer.start();
            if (!keyEventAdded) {
                keyEventAdded = true;
            // Key Listener for 'S' Key
            new Thread(() -> {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e1 -> {
                    if (e1.getID() == KeyEvent.KEY_PRESSED && (e1.getKeyCode() == KeyEvent.VK_S || e1.getKeyCode()== KeyEvent.VK_ESCAPE)) {
                        running = false;
                        JOptionPane.showMessageDialog(null, "Process stopped! (İşlem durduruldu!)");
                    }
                    return false;
                });
            }).start();
            }
        });
        btnNewButton.setBounds(72, 67, 89, 23);
        frame.getContentPane().add(btnNewButton);
        
        frame.getRootPane().setDefaultButton(btnNewButton);
    }

}
