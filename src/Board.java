import java.awt.BorderLayout;
import java.awt.Scrollbar;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.JScrollPane;

import theme.Button;
import theme.Panel;
import theme.TextArea;
import theme.Theme;

public class Board implements Panelable {
	private List<Post> posts;

	public Board() {
		this(new ArrayList<Post>());
	}

	public Board(List<Post> posts) {
		this.posts = posts;
	}

	private Panel makeCreatePostPanel(User user) throws FileNotFoundException {
		Panel panel = new Panel(2, 2);
		TextArea createPostContent = new TextArea();
		Button submit = new Button("create post");

		panel.add(createPostContent);
		panel.add(submit);
		//panel.add(refresh);
		
		submit.addActionListener(e -> {
			try {
				user.createTextPost(createPostContent.getText(), false);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		return panel;
	}

	public Panel toPanel(User user, int index) {
		Panel panel = new Panel(4, 4); // shouldn't leave (4, 4)
		
		
		
		panel.setBackground(Theme.COLOR_BACKGROUND);

		Button refresh = new Button("Click to refresh");
		refresh.addActionListener(e -> {
			user.refreshGUI();
			System.out.println("click");
		});
		panel.add(refresh);

		//Panel posts = new Panel(this.numPosts(), 1); //not using because we are doing panel.add instead

		int i = 0;
		Iterator<Post> iter = this.posts.iterator();
		while (iter.hasNext())
			panel.add(iter.next().toPanel(user, i++), BorderLayout.PAGE_END);

		try {
			panel.add(makeCreatePostPanel(user), BorderLayout.PAGE_END);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//panel.add(posts);


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