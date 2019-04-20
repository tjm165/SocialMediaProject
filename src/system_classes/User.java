package system_classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JFrame;

public class User {
	private final static String BASE_NAME = "board_directory/File";
	public final static String BOARD_DIRECTORY = "board_directory/";
	private String userId;
	private Board board;

	public User(String userId) {
		this.userId = userId;
		try {
			this.board = Board.getBoardFromFile(BOARD_DIRECTORY);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Board testBoard() {
		try {
		return Board.getBoardFromFile(BOARD_DIRECTORY);
		}
		catch(Exception e) {
			return null;
		}
	}

	public Board getBoard() {
		return this.board;
	}

	/*
	public void setUserId(String ID) {
		this.userId = ID;
	}
	*/

	public String getUserId() {
		return userId;
	}

	public void refreshBoard() {
		board.sortPosts();
	}


	public void upVote(int postIndex) throws FileNotFoundException {
		Post post = board.getPost(postIndex);
		post.addVote(1);
		savePostToFile(post);
	}

	public void downVote(int postIndex) throws FileNotFoundException {
		Post post = board.getPost(postIndex);
		post.addVote(-1);
		savePostToFile(post);
	}

	public void addComment(int postIndex, String comment) throws FileNotFoundException {
		Post post = board.getPost(postIndex);
		Content content = new Content("TEXT", comment);
		Date dateCreated = Calendar.getInstance().getTime();

		Comment commentObj = new Comment(content, dateCreated, userId);
		post.addComment(commentObj);
		savePostToFile(post);
	}

	public void createTextPost(String text, boolean anon) throws FileNotFoundException {
		this.createPost("TEXT", text, anon);
	}

	public void createImagePost(String imgURL, boolean anon) throws FileNotFoundException {
		this.createPost("IMAGE", imgURL, anon);
	}

	// Jared can you see if this works when you do the demo
	public void deletePost(int i) {
		Post toDelete = board.removePost(i);
		File file = new File(toDelete.getPathname());
		file.delete();
	}

	private void createPost(String contentType, String content, boolean anon) throws FileNotFoundException {
		Date dateCreated = Calendar.getInstance().getTime();
		String pathname = User.BASE_NAME + dateCreated.getTime() + ".post";

		Content contentObj = new Content(contentType, content);
		String userId = this.userId;
		if (anon)
			userId = null;
		int interestLevel = 0;// might need to fix
		List<Comment> comments = new LinkedList<Comment>();

		Post post = new Post(pathname, contentObj, dateCreated, 0, userId, interestLevel, comments);
		board.addPost(post);
		savePostToFile(post);
	}

	private void savePostToFile(Post post) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(post.getPathname());
		writer.print(post.toFileNotation());
		writer.close();
	}

}
