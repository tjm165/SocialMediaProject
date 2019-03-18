import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class User {
	private final static String BASE_NAME = "board_directory/File";
	private String userId;
	private Board board;

	public User(String userId) throws IOException {
		this.userId = userId;
		this.board = new Board();
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

	public static void main(String[] args) throws IOException {
		User user = new User("rocketman57");
		// user.createTextPost("hello world", false);

		user.addComment(0, "I like that post");
	}

}
