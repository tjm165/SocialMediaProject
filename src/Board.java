import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Board {
	public final static String BOARD_DIRECTORY = "board_directory/";
	private ArrayList<Post> posts;

	// https://stackoverflow.com/questions/1844688/how-to-read-all-files-in-a-folder-from-java
	public Board() throws IOException {
		posts = new ArrayList<Post>();

		File folder = new File(Board.BOARD_DIRECTORY);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles)
			if (file.isFile())
				posts.add(Post.parsePost(file.getPath()));
		
		
	}

	public void addPost(Post post) {
		posts.add(post);
	}

	public Post getPost(int index) {
		return posts.get(index);
	}}