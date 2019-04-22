package user_interface;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

import javax.swing.*;
import system_classes.*;
import theme.*;

public class GUI extends JFrame {

	User user;
	boolean hasSignedIn;
	CountDownLatch nextState;
	JScrollPane hotPanel;
	CountDownLatch newUpdates;

	public GUI(String title) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1600, 2000);
		this.hotPanel = null;
		this.user = null;
		this.nextState = new CountDownLatch(1);
		this.newUpdates = new CountDownLatch(1);
		this.hasSignedIn = false;

		WindowFocusListener l = new WindowFocusListener() {
			@Override
			public void windowGainedFocus(WindowEvent e) {
				if (hasSignedIn)
					refresh();
			}

			@Override
			public void windowLostFocus(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

		};

		this.addWindowFocusListener(l);

	}

	// I think this will be a very important method
	private void display(JPanel panel) {
		if (hotPanel != null)
			this.remove(hotPanel);

		this.hotPanel = new JScrollPane(panel);

		// JScrollPane scrPane = new JScrollPane(hotPanel);
		// this.add(scrPane);
		this.add(hotPanel); // take out for scrolling

		this.setVisible(true);// need to call this everytime to update the jframe
	}

	private JScrollPane getHotPanel() {
		return this.hotPanel;
	}

	private void refresh() {
		nextState.countDown();
	}

	private Cell signInPanel() {
		Cell signIn = new Cell(2, 1);
		signIn.setBackground(Theme.COLOR_MAIN);
		Cell form = new Cell(1, 2);

		TextArea textbox = new TextArea();
		Button submit = new Button("submit");
		Label prompt = new Label("Please enter a username!");

		submit.addActionListener(e -> {
			if (textbox.getText().equals("")) {
				prompt.setForeground(Theme.COLOR_ERROR);
			} else {
				System.out.println(textbox.getText());
				this.user = new User(textbox.getText());
				System.out.println(user);
				refresh();
			}
		});

		form.setCell(textbox, 1, 1);
		form.setCell(submit, 1, 2);
		signIn.setCell(prompt, 1, 1);
		signIn.setCell(form, 2, 1);
		return signIn;
	}

	private Cell homePagePanel() {
		// user = new User(this.user.getUserId());
		Board board = this.user.getBoard(); // NOTE: update
		board.sortPosts();
		int numRows = this.determineNumRows(board);
		Cell header = new Cell(1, 2);
		Cell homePage = new Cell(numRows, 1);
		homePage.setBackground(Theme.COLOR_MAIN);

		Button refresh = new Button("refresh");
		refresh.addActionListener(e -> {
			this.refresh();
		});

		header.setCell(createPostCell(), 1, 1);
		header.setCell(refresh, 1, 2);

		homePage.setCell(header, 1, 1);
		for (int i = 0; i < board.numPosts(); i++)
			homePage.setCell(this.postCell(board.getPost(i), i), i + 2, 1);

		return homePage;
	}

	private Cell postCell(Post post, int index) {
		Cell cell = new Cell(1, 4);
		cell.setMaximumSize(new Dimension(100, 100));
		Cell info = new Cell(3, 1); // the content and detials
		Cell vote = new Cell(2, 1); // upvote, downvote
		Cell addComment = new Cell(2, 1);
		Cell comments = new Cell(post.numComments(), 1);

		// Info
		Content content = post.getContent();
		if (content.getType().equals(Content.TYPE_TEXT))
			info.setCell(post.getContent().getContent(), 1, 1);
		else
			try {
				info.setCell(new Image(post.getContent().getContent()), 1, 1);
			} catch (IOException e2) {
				System.out.println("type was: " + post.getContent().getType());
				e2.printStackTrace();
			}
		info.setCell("Interest Level: " + post.getInterestLevel(), 2, 1);
		info.setCell("User: " + ((post.getUserId().equals("null")) ? "N/A" : post.getUserId()), 3, 1);

		// Vote
		Button upvote = new Button("Upvote");
		upvote.addActionListener(e -> {
			try {
				user.upVote(index);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			refresh();
		});

		Button downvote = new Button("Downvote");
		downvote.addActionListener(e -> {
			try {
				user.downVote(index);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			refresh();
		});

		vote.setCell(upvote, 1, 1);
		vote.setCell(downvote, 2, 1);

		// Add Comment
		TextArea comment = new TextArea();
		Button submit = new Button("Add Comment");

		submit.addActionListener(e -> {
			try {
				user.addComment(index, comment.getText());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			refresh();
		});
		addComment.setCell(comment, 1, 1);
		addComment.setCell(submit, 2, 1);

		// Comments
		Iterator<Comment> iter = post.getIterator();
		int i = 1;
		while (iter.hasNext()) {
			comments.setCell(iter.next().getContent().getContent(), i, 1);
			comments.getPanel(i++,  1).setBorder(Theme.HALF);
		}

		cell.setCell(info, 1, 1);
		cell.setCell(vote, 1, 2);
		cell.setCell(addComment, 1, 3);
		JScrollPane scrollComments = new JScrollPane(comments, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JPanel jComments = new JPanel();
		jComments.add(scrollComments);
		cell.setCell(jComments, 1, 4);
		return cell;
	}

	private Cell createPostCell() {
		Cell cell = new Cell(2, 1);
		Cell top = new Cell(1, 2); // textbox and submit
		Cell bottom = new Cell(1, 2); // details

		TextArea content = new TextArea();
		Button submit = new Button("Create Post");
		CheckBox imageCheck = new CheckBox("Image");
		CheckBox anonCheck = new CheckBox("Anonymous");

		submit.addActionListener(e -> {
			try {
				if (imageCheck.isSelected()) {
					System.out.println("CREATE IMAGE");
					user.createImagePost(content.getText(), anonCheck.isSelected());
				} else {
					System.out.println("CRETE TEXT");
					user.createTextPost(content.getText(), anonCheck.isSelected());
				}

			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			refresh();
		});

		top.setCell(content, 1, 1);
		top.setCell(submit, 1, 2);

		bottom.setCell(anonCheck, 1, 1);
		bottom.setCell(imageCheck, 1, 2);

		cell.setCell(top, 1, 1);
		cell.setCell(bottom, 2, 1);
		return cell;
	}

	private int determineNumRows(Board board) {
		int numRows = 1 + board.numPosts();
		return numRows;
	}

	public static void runGUI() throws InterruptedException {
		GUI gui = new GUI("Social Media App - Sign In"); // make the GUI

		gui.display(gui.signInPanel()); // add the signin panel

		// simply dealing with submit button
		gui.nextState.await();
		gui.hasSignedIn = true;
		gui.setTitle("Scial Media App - " + gui.user.getUserId());
		gui.getHotPanel().removeAll();
		gui.display(gui.homePagePanel());

		// dealing with board interactions
		boolean running = true;
		while (running) {
			gui.nextState.await();
			gui.display(gui.homePagePanel());
			gui.nextState = new CountDownLatch(1);
		}
	}

	public static void main(String[] main) throws InterruptedException {
		runGUI();
	}

}