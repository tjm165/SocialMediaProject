package system_classes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class Board {
	private List<Post> posts;

	public Board() {
		this(new ArrayList<Post>());
	}

	public Board(List<Post> posts) {
		this.posts = posts;
	}
	
	public int necessaryRows() {
		int rows = 2; //Starts with initial rows for the "refresh button" and the submit post panel
		rows = rows + this.numPosts()*2;
		for(int i = 0; i < this.numPosts(); i++) {
			rows = rows + this.getPost(i).numComments();
		}
		
		return rows;
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

	// keep in mind that it's the user's responsibility to delete the actual file -
	// T
	// just like its the user's responsibility to create and save the actual file -
	// T
	// though in this case, the user will choose to refresh the board. that will
	// include deletion - J
	public Post removePost(int i) {
		return posts.remove(i);
	}

	// Sorts from highest to lowest interest level
	public void sortPosts() {
		for (int i = 0; i < posts.size(); i++) {
			posts.get(i).calculateInterestLevel();
			if (posts.get(i).getInterestLevel() <= 0) {
				File file = new File(posts.get(i).getPathname());
				System.out.println(posts.get(i).getInterestLevel()); //For testing
				file.delete();
				removePost(i);
			}
		}
		Collections.sort(this.posts);
		Collections.reverse(this.posts);
	}

	public Post getPost(int index) {
		return posts.get(index);
	}

	public int numPosts() {
		return posts.size();
	}

	public static Board getBoardFromFile(String boardDirectory) throws IOException, ParseException {
		List<Post> posts = new ArrayList<Post>();

		File folder = new File(boardDirectory);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles)
			if (file.isFile())
				posts.add(Post.parsePost(file.getPath()));

		return new Board(posts);
	}
}