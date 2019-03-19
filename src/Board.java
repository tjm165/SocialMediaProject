import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
	private List<Post> posts;

	// https://stackoverflow.com/questions/1844688/how-to-read-all-files-in-a-folder-from-java
	/*public Board() throws IOException, ParseException {
		posts = new ArrayList<Post>();

		File folder = new File(Board.BOARD_DIRECTORY);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles)
			if (file.isFile())
				posts.add(Post.parsePost(file.getPath()));

	}
	*/

	public Board(List<Post> posts) {
		this.posts = posts;
	}

	public String toString() {
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < posts.size(); i++) {
			string.append(posts.get(i).toFileNotation() + "\n\n\n");
		}
		return string.toString();
	}

	public void addPost(Post post) {
		posts.add(post);
	}

	/*
	 * ================Here is the delete helper method; I'm not quite sure about
	 * the filename string I frankensteined together, but it's pretty simple public
	 * void deleteFile(String fileName){ File file = new File(Board.BOARD_DIRECTORY
	 * + fileName); file.delete(); }
	 */

	public void sortPosts() {
		Collections.sort(this.posts);
	}

	public Post getPost(int index) {
		return posts.get(index);
	}
	
	public static Board getBoardFromFile(String boardDirectory) throws IOException, ParseException{
		List<Post> posts = new ArrayList<Post>();

		File folder = new File(boardDirectory);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles)
			if (file.isFile())
				posts.add(Post.parsePost(file.getPath()));
		
		return new Board(posts);
	}
}
