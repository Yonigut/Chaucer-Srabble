

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Game Main class that specifies the frame and widgets of the Scrabble GUI
 */
public class Game implements Runnable {
    public void run() {
    	// creates frame
        final JFrame frame = new JFrame("Scrabble");
        frame.setLocation(300, 300);
        
        // creates the rest of the JButtons and JComponents in the game
        final GameScreen screen = new GameScreen(frame);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
