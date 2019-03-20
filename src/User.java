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

public class User {
	private final static String BASE_NAME = "board_directory/File";
	public final static String BOARD_DIRECTORY = "board_directory/";
	private String userId;
	private Board board;

	public User(String userId) throws IOException, ParseException {
		this.userId = userId;
		this.board = Board.getBoardFromFile(BOARD_DIRECTORY);
	}

	public Board getBoard() {
		return this.board;
	}

	public String getUserId() {
		return userId;
	}

	public void refreshBoard() {
		board.sortPosts();
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
	}

	private void savePostToFile(Post post) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(post.getPathname());
		writer.print(post.toFileNotation());
		writer.close();
	}

	public static void demo1() throws IOException, ParseException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Sign in or create a new user (enter your username)");
		User user = new User(scan.nextLine());
		String contentType = "";
		System.out.println(
				"Now create your first post! do you want it to be text or an image?\n0 for text; 1 for an image");
		int contentTypeInt = scan.nextInt();
		if (contentTypeInt == 1) {
			contentType = "IMAGE";
			scan.nextLine();
			System.out.println("What is the link to your image?");
		} else {
			contentType = "TEXT";
			scan.nextLine();
			System.out.println("What do you want the post to say?");
		}
		String content = scan.nextLine();
		System.out.println("Do you want to post anonymously? type 1 if so, 0 if not");
		if (contentTypeInt == 1) {
			user.createPost(contentType, content, true);
		} else {
			user.createPost(contentType, content, false);
		}
		System.out.println("You've made your first post! Here is how it is stored:"
				+ user.getBoard().getPost(0).toFileNotation() + "\n\nHere is the board with some more posts added:");

		user.createTextPost("This is a computer created post", true);
		user.createTextPost("This is a computer created post", true);
		System.out.println(user.getBoard().toString());

		scan.close();
	}

	public static void main(String[] args) throws IOException, ParseException {
		demo1();
		// User user = new User("rocketman57");
		// user.createTextPost("hello world", false);

		// user.addComment(0, "I like that post");
	}

}
