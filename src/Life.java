import java.awt.Container;
import javax.swing.Timer;

//import javax.sound.midi.ControllerEventListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class Life  extends JFrame{
	private Container content;
	private JPanel panel;
	private JFrame frame;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Life();
			}
		});
	}

	public Life(){
		super();
		frame = new JFrame("Life");  // Game Title
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		content = frame.getContentPane();
		GameEngine controller = new GameEngine(frame);
		frame.add(controller);
		frame.setVisible(true);
		int millisecs = 10;
		Timer timer = new Timer(millisecs, controller);
		timer.start();

	}

}
