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

	public void upVote(int postIndex) {
		Post post = board.getPost(postIndex);
		post.addVote(1);
	}

	public void downVote(int postIndex) {
		Post post = board.getPost(postIndex);
		post.addVote(-1);
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
		int anon = scan.nextInt();
		if (anon == 1) {
			user.createPost(contentType, content, true);
		} else {
			user.createPost(contentType, content, false);
		}
		System.out.println("You've made your first post! Here is how it is stored:\n"
				+ user.getBoard().getPost(0).toFileNotation() + "\n\nHit enter for more posts to be added =>");

		scan.nextLine();
		user.createTextPost("This is a computer created post", true);
		user.createTextPost("This is a second computer created post", true);
		System.out.println(user.getBoard().toString());

		// Tests Up votes, down votes, deleting a post
		System.out.println("Now hit enter to upvote your post =>");
		scan.nextLine();
		user.upVote(0);
		System.out.println("Now your post has a higher interest level!\n" + user.getBoard().getPost(0).toFileNotation()
				+ "\n\nNow hit enter to downvote a computer generated post =>");

		scan.nextLine();
		user.downVote(1);
		System.out.println("Now the first computer post has a lower interest level!\n"
				+ user.getBoard().getPost(1).toFileNotation());
		System.out.println("\n\nNow if the post drops to an interest level of 0 or below,\nit will be deleted. Hit enter to see =>");

		scan.nextLine();
		user.getBoard().getPost(1).addVote(-30);
		user.getBoard().sortPosts();
		System.out.println(
				"Here is the board with the changed interest levels and deletion:\n" + user.getBoard().toString());

		System.out.println("Hit enter so see what happens if your post gets 5 downvotes =>");
		user.getBoard().getPost(0).addVote(-5);
		user.getBoard().sortPosts();

		System.out.println(
				"You will see the board has been resorted in order of interest:\n" + user.getBoard().toString());

		System.out.println("\n\nHow about you add a comment to the top post? What should it say?");
		user.addComment(0, scan.nextLine());

		System.out.println("\nNow the post has a higher interest level, and the comment is stored in the post.\n\n"
				+ user.getBoard().getPost(0).toFileNotation() + "\n\nYour Demo Is Complete!!! \nHave a GREAT Day!");

		scan.close();
	}

	public static void main(String[] args) throws IOException, ParseException {
		demo1();
		// User user = new User("rocketman57");
		// user.createTextPost("hello world", false);

		// user.addComment(0, "I like that post");
	}

}
