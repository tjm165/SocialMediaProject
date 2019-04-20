package gui;

import java.awt.Component.*;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.util.concurrent.CountDownLatch;

import javax.swing.*;

import system_classes.Board;
import system_classes.Post;
import system_classes.User;
import theme.Button;
import theme.Cell;
import theme.TextArea;

public class GUI extends JFrame {

	User user;
	CountDownLatch nextState;
	JPanel hotPanel;
	CountDownLatch newUpdates;

	public GUI(String title) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1600, 2000);
		this.hotPanel = null;
		this.user = null;
		this.nextState = new CountDownLatch(1);
		this.newUpdates = new CountDownLatch(1);
	}

	// I think this will be a very important method
	private void display(JPanel panel) {
		if (hotPanel != null)
			this.remove(hotPanel);

		this.hotPanel = panel;
		this.add(hotPanel);

		this.setVisible(true);// need to call this everytime to update the jframe
	}

	private Cell signInPanel() {
		// Initialize what we need
		Cell signIn = new Cell(1, 2);
		TextArea textbox = new TextArea();
		Button submit = new Button("submit");

		submit.addActionListener(e -> {
			this.user = new User(textbox.getText());
			System.out.println(user);
			nextState.countDown();
		});

		signIn.setCell(textbox, 1, 1);
		signIn.setCell(submit, 1, 2);

		return signIn;
	}

	private Cell homePagePanel() {
		//user = new User(this.user.getUserId());
		Board board = this.user.testBoard(); //NOTE: update
		board.sortPosts();
		int numRows = this.determineNumRows();
		Cell homePage = new Cell(numRows, 1);

		Button refresh = new Button("refresh");
		refresh.addActionListener( e -> {
			nextState.countDown();
		});
		
		homePage.setCell(refresh, 1, 1);
		homePage.setCell(createPostCell(), 2, 1);
		for (int i = 0; i < board.numPosts(); i++)
			homePage.setCell(this.postCell(board.getPost(i), i), i + 3, 1);

		return homePage;
	}

	private Cell postCell(Post post, int index) {
		Cell cell = new Cell(1, 3);
		Cell left = new Cell(3, 1); // the content, details, and add comment
		Cell mid = new Cell(2, 1); // upvote, downvote
		Cell right = new Cell(post.numComments(), 1); // comments

		left.setCell(post.getContent().getContent(), 1, 1); // NOTE: need to change for images
		left.setCell(post.getInterestLevel() + "", 2, 1);

		Button upvote = new Button("Upvote");
		upvote.addActionListener(e -> {
			try {
				user.upVote(index);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			nextState.countDown();
		});

		Button downvote = new Button("Downvote");
		downvote.addActionListener(e -> {
			try {
				user.downVote(index);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			nextState.countDown();
		});

		mid.setCell(upvote, 1, 1);
		mid.setCell(downvote, 2, 1);

		cell.setCell(left, 1, 1);
		cell.setCell(mid, 1, 2);
		cell.setCell(right, 1, 3);

		return cell;
	}

	private Cell createPostCell() {
		Cell cell = new Cell(2, 1);
		Cell top = new Cell(1, 2); // textbox and submit
		Cell bottom = new Cell(1, 2); // details

		TextArea content = new TextArea();
		Button submit = new Button("Create Post");

		submit.addActionListener(e -> {
			try {
				user.createTextPost(content.getText(), false);// Anon
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			nextState.countDown();
		});

		top.setCell(content, 1, 1);
		top.setCell(submit, 1, 2);

		cell.setCell(top, 1, 1);
		cell.setCell(bottom, 2, 1);
		return cell;
	}

	private int determineNumRows() {
		Board board = this.user.getBoard();
		int numRows = 3 + board.numPosts();
		return numRows;
	}

	public static void runGUI() throws InterruptedException {
		GUI gui = new GUI("Social Media App"); // make the GUI

		gui.display(gui.signInPanel()); // add the signin panel

		boolean running = true;
		while (running) {
			gui.nextState.await();
			gui.display(gui.homePagePanel());
			gui.nextState = new CountDownLatch(1);
		}
	}

	/*
	 * JPanel boardPanel = gui.user.getBoard().toPanel(gui.user, 0);
	 * boardPanel.setFont(boardPanel.getFont().deriveFont(72));
	 * 
	 * gui.display(boardPanel); // now that we have a user we can show the board
	 * 
	 * 
	 * boolean running = true; while(running) { gui.user.refresh.await();
	 * gui.display(gui.user.getBoard().toPanel(gui.user, 0));
	 * System.out.println("refresh"); gui.user.refresh = new CountDownLatch(1); }
	 */

	public static void main(String[] main) throws InterruptedException {
		runGUI();
	}

}