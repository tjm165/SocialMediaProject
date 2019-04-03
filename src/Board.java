import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

public class Board implements Panelable{
	private List<Post> posts;

	// https://stackoverflow.com/questions/1844688/how-to-read-all-files-in-a-folder-from-java
	/*
	 * public Board() throws IOException, ParseException { posts = new
	 * ArrayList<Post>();
	 * 
	 * File folder = new File(Board.BOARD_DIRECTORY); File[] listOfFiles =
	 * folder.listFiles();
	 * 
	 * for (File file : listOfFiles) if (file.isFile())
	 * posts.add(Post.parsePost(file.getPath()));
	 * 
	 * }
	 */

	public Board() {
		this(new ArrayList<Post>());
	}

	public Board(List<Post> posts) {
		this.posts = posts;
	}
	
	public JPanel toPanel() {
		JPanel panel = new JPanel();
		JPanel createPost = new JPanel(); //still need to make
		
		panel.add(createPost);
		for (Post post : posts)
			panel.add(post.toPanel());
		
		return panel;
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

	//keep in mind that it's the user's responsibility to delete the actual file - T
	//just like its the user's responsibility to create and save the actual file - T
	//though in this case, the user will choose to refresh the board. that will include deletion - J
	public Post removePost(int i) {
		return posts.remove(i);
	}

	//Sorts from highest to lowest interest level
	public void sortPosts() {
		for(int i = 0; i < posts.size(); i++) {
			posts.get(i).calculateInterestLevel();
			if (posts.get(i).getInterestLevel() <= 0) {
				File file = new File(posts.get(i).getPathname());
				//System.out.println(posts.get(i).getInterestLevel());   //For testing
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