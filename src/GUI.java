import java.awt.Component.*;
import java.util.concurrent.CountDownLatch;

import javax.swing.*;

import theme.Button;
import theme.Panel;

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
	public void display(JPanel panel) {
		if (hotPanel != null)
			this.remove(hotPanel);
		
		this.hotPanel = panel;
		this.add(hotPanel);
		
		this.setVisible(true);//need to call this everytime to update the jframe
	}

	/* perhaps this method could be in user.toPanel()
	 * The only thing is we would need to work with that latch..
	 */
	public JPanel signIn() {
		// Initialize what we need
		Panel signIn = new Panel(1, 2);
		JTextArea textbox = new JTextArea(1, 20);
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

	public static void main(String[] main) throws InterruptedException {
		GUI gui = new GUI("Demo Social Media App"); // make the GUI

		gui.display(gui.signIn()); // add the signin panel
		gui.signedIn.await(); // wait for gui.user to be set
		gui.display(gui.user.getBoard().toPanel()); // now that we have a user we can show te board

		System.out.println("hey");

	}

}
