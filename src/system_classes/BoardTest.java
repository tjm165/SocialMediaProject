package system_classes;
import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

class BoardTest {
	private final SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");

	private final Content content1 = new Content("TEXT", "Here is text");
	private final Content content2 = new Content("IMAGE", "image.com/");
	private final Content content3 = new Content("IMAGE", "image2.com/");

	private final Date date1 = new Date(1545872400000L);
	private final Date date2 = new Date(1545872400001L);
	private final Date date3 = new Date(1545872400002L);

	private final String user1 = "user1";
	private final String user2 = "user2";
	private final String user3 = "user3";

	private final Comment comment1 = new Comment(content1, date1, user1);
	private final Comment comment2 = new Comment(content2, date2, user2);
	private final String pathname1 = "pathname1";
	private final String pathname2 = "pathname2";
	private final String pathname3 = "pathname3";

	private final int netVote1 = 0;
	private final int netVote2 = 1;
	private final int netVote3 = 2;

	private final int interestLevel1 = 0;
	private final int interestLevel2 = 1;
	private final int interestLevel3 = 2;

	// helper that makes a new post
	private Post newPost1() {
		return new Post(pathname1, content1, date1, netVote1, user1, interestLevel1, new ArrayList<Comment>());
	}

	private Post newPost2() {
		return new Post(pathname2, content2, date2, netVote2, user2, interestLevel2, new ArrayList<Comment>());
	}

	private Post newPost3() {
		return new Post(pathname3, content3, date3, netVote3, user3, interestLevel3, new ArrayList<Comment>());
	}

	@Test
	void testToString() {
		Board board = new Board();
		Post post1 = newPost1();
		Post post2 = newPost2();
		Post post3 = newPost3();
		String expected;

		// when board has 0 posts
		expected = "";
		assertEquals(expected, board.toString());

		// when board has 1 post
		board.addPost(post1);
		expected = post1.toFileNotation();
		assertEquals(expected, board.toString());

		// when board has 2 post
		board.addPost(post2);
		expected = post1.toFileNotation() + "\n" + post2.toFileNotation();
		assertEquals(expected, board.toString());

		// when board has 3 post
		board.addPost(post3);
		expected = post1.toFileNotation() + "\n" + post2.toFileNotation() + "\n" + post3.toFileNotation();
		assertEquals(expected, board.toString());
	}

	@Test
	void addPost() {
		Board board = new Board();
		Post post1 = newPost1();
		Post post2 = newPost2();

		// Should increment numPosts
		board.addPost(post1);
		assertEquals(board.numPosts(), 1);
		// Should be able to retrieve that post
		assertEquals(board.getPost(0), post1);

		// Should increment numPosts
		board.addPost(post2);
		assertEquals(board.numPosts(), 2);
		// Should be still able to retrieve first post
		assertEquals(board.getPost(0), post1);
		// Should be able to retrieve the newly added post
		assertEquals(board.getPost(1), post2);
	}

	@Test
	void removePost() {
		Board board = new Board();
		Post post1 = newPost1();
		Post post2 = newPost2();

		board.addPost(post1);
		board.addPost(post2);

		// confirm the numPosts is correct
		assertEquals(board.numPosts(), 2);

		// remove the first post
		assertEquals(board.removePost(0), post1);
		// confirm numPosts is correct
		assertEquals(board.numPosts(), 1);

		// remove the second post
		assertEquals(board.removePost(0), post2);
		// confirm numPosts is correct
		assertEquals(board.numPosts(), 0);
	}

	@Test
	void sortPosts() {
		// Sorts from highest to lowest interest level

		Board board = new Board();
		Post post0 = newPost1();
		Post post1 = newPost1();
		Post post2 = newPost1();
		Post post3 = newPost1();
		Post post4 = newPost1();

		// make it so post2 < post4 <post1 <post0 < post3
		post3.addVote(1);
		post0.addVote(2);
		post2.addVote(3);
		post4.addVote(4);
		post2.addVote(5);

		// add the posts to the board
		board.addPost(post0);
		board.addPost(post1);
		board.addPost(post2);
		board.addPost(post3);
		board.addPost(post4);

		// confirm the posts are not sorted.
		// this can be done by confirming the expected order is not what we are getting
		assertFalse(post3.equals(board.getPost(0)));
		assertFalse(post0.equals(board.getPost(1)));
		assertFalse(post1.equals(board.getPost(2)));
		assertFalse(post4.equals(board.getPost(3)));
		assertFalse(post2.equals(board.getPost(4)));

		// sort the board
		board.sortPosts();

		// now confirm that the posts are sorted
		assertTrue(post3.equals(board.getPost(0)));
		assertTrue(post0.equals(board.getPost(1)));
		assertTrue(post1.equals(board.getPost(2)));
		assertTrue(post4.equals(board.getPost(3)));
		assertTrue(post2.equals(board.getPost(4)));
	}

	@Test
	void getPost() {
		Board board = new Board();
		Post post1 = newPost1();
		Post post2 = newPost2();

		board.addPost(post1);
		board.addPost(post2);

		assertEquals(board.getPost(0), post1);
		assertEquals(board.getPost(1), post2);
	}

	@Test
	void numPosts() {
		Board board = new Board();

		// confirm the numPosts is 0
		assertEquals(board.numPosts(), 0);

		// Add a post and confirm numPosts incremented
		board.addPost(newPost1());
		assertEquals(board.numPosts(), 1);
		
		//Again, Add a post and confirm numPosts incremented
		board.addPost(newPost1());
		assertEquals(board.numPosts(), 2);
		
		//Remove a post and confirm numPosts decremented
		board.removePost(0);
		assertEquals(board.numPosts(), 1);
		
		//Again, Remove a post and confirm numPosts decremented
		board.removePost(0);
		assertEquals(board.numPosts(), 0);
	}

}
