import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class User {
	private final static String BASE_NAME = "board_directory/File";
	private String userId;
	private Board board;

	public User(String userId) throws IOException {
		this.userId = userId;
		this.board = new Board();
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
		post.saveToFile();
	}

	public void createTextPost(String text, boolean anon) throws FileNotFoundException {
		this.createPost("TEXT", text, anon);
	}

	public void createImagePost(String imgURL, boolean anon) throws FileNotFoundException {
		this.createPost("IMAGE", imgURL, anon);
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
		post.saveToFile();
	}

	public static void demo1() throws IOException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Sign in or create a new user (enter your username)");
		User user = new User(scan.nextLine());
		String contentType = "";
		System.out.println(
				"Now create your first post! do you want it to be text or an image?\n0 for text; 1 for an image");
		int contentTypeInt = scan.nextInt();
		if (contentTypeInt == 1) {
			contentType = "IMAGE";
			System.out.println("What is the link to your image?");
		} else {
			contentType = "TEXT";
			System.out.println("What do you want the post to say?");
		}
		String content = scan.nextLine();
		System.out.println("Do you want to post anonymously? type 1 if so, 0 if not");
		if (contentTypeInt == 1) {
			user.createPost(contentType, content, true);
		} else {
			user.createPost(contentType, content, false);
		}
		System.out.println(
				"You've made your first post! Here is how it is stored:" + user.getBoard().getPost(0).toFileNotation());
		scan.close();
	}

	public static void main(String[] args) throws IOException {
		User user = new User("rocketman57");
		// user.createTextPost("hello world", false);

		user.addComment(0, "I like that post");
	}

}
