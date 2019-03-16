import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class User {
	private final static String BASE_NAME = "board_directory/File";
	private String userId;

	public User(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void addComment(Post post, String comment) throws FileNotFoundException {
		
		post.saveToFile();
	}
	
	private void createPost(String contentType, String content, boolean anon) throws FileNotFoundException {
		Date dateCreated = Calendar.getInstance().getTime();
		String pathname = User.BASE_NAME + dateCreated.getTime() + ".post";
		
		Content contentObj = new Content(contentType, content);
		String userId = this.userId;
		if (anon)
			userId = null;
		int interestLevel = 0;//might need to fix
		List<Comment> comments = new LinkedList<Comment>();
		
		Post post = new Post(pathname, contentObj, dateCreated, 0, userId, interestLevel, comments);
		post.saveToFile();
	}

	public void createTextPost(String text, boolean anon) throws FileNotFoundException {
		this.createPost("TEXT", text, anon);
	}

	public void createImagePost(String imgURL, boolean anon) throws FileNotFoundException {
		this.createPost("IMAGE", imgURL, anon);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		User user = new User("rocketman57");
		user.createTextPost("hello world", false);
	}


}
