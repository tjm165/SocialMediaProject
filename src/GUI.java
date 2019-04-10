import java.awt.Component.*;
import java.util.concurrent.CountDownLatch;

import javax.swing.*;

import theme.Button;
import theme.Panel;
import theme.TextArea;

public class GUI extends JFrame {

	User user;
	CountDownLatch signedIn;
	JPanel hotPanel;

	public GUI(String title) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 300);
		this.hotPanel = null;
		this.user = null;
		this.signedIn = new CountDownLatch(1);
	}

	//I think this will be a very important method
	private void display(JPanel panel) {
		if (hotPanel != null)
			this.remove(hotPanel);
		
		this.hotPanel = panel;
		this.add(hotPanel);
		
		this.setVisible(true);//need to call this everytime to update the jframe
	}

	/* You might think that this method could be in user.toPanel()
	 * However, this method calls new User("username"). so maybe it can be in the User class
	 * But it would have to be static
	 * The only thing is we would need to work with that latch..
	 */
	private JPanel signIn() {
		// Initialize what we need
		Panel signIn = new Panel(1, 2);
		TextArea textbox = new TextArea();
		Button submit = new Button("submit");

		// Set any properties

		submit.addActionListener(e -> {
			this.user = new User(textbox.getText());
			System.out.println(user);
			
			signedIn.countDown();
		});

		// add to panel
		signIn.add(textbox);
		signIn.add(submit);
		
		return signIn;
	}
	
	public static void LETSGO() throws InterruptedException {
		GUI gui = new GUI("Social Media App"); // make the GUI
		gui.setSize(1600, 2000);

		gui.display(gui.signIn()); // add the signin panel
		gui.signedIn.await(); // wait for gui.user to be set
		gui.user.getBoard().sortPosts();
		Panel boardPanel = gui.user.getBoard().toPanel(gui.user, 0);
		boardPanel.setFont(boardPanel.getFont().deriveFont(72));
		
		gui.display(boardPanel); // now that we have a user we can show te board
		
		
		boolean running = true;
		while(running) {
			gui.user.refresh.await();
			gui.display(boardPanel);
		}
	}

	public static void main(String[] main) throws InterruptedException {
		LETSGO();
	}

}
