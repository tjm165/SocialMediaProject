package system_classes;
import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

class PostTest {

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
	void numComments() {
		Post post = newPost1();

		// Confirm that numComments is 0
		assertEquals(post.numComments(), 0);

		// Add 1 comment and confirm numComments incremented
		post.addComment(comment1);
		assertEquals(post.numComments(), 1);
	}

	@Test
	void addComment() {
		Post post = newPost1();
		Comment comment1 = this.comment1;
		Comment comment2 = this.comment2;
		String[] postFileNotation;
		String[] commentFileNotation1;
		String[] commentFileNotation2;

		// add comment1
		post.addComment(comment1);

		// adding a comment affects the post's numComments
		assertEquals(post.numComments(), 1);

		// adding a comment affects the post's file notation
		postFileNotation = post.toFileNotation().split("\n");
		commentFileNotation1 = comment1.toFileNotation().split("\n");

		// postFileNotation lines 6, 7, 8 should correspond to commentFileNotation1
		// lines 0,1,2
		assertEquals(postFileNotation[6], commentFileNotation1[0]);
		assertEquals(postFileNotation[7], commentFileNotation1[1]);
		assertEquals(postFileNotation[8], commentFileNotation1[2]);

		// add comment2
		post.addComment(comment2);

		// adding a comment affects the post's numComments
		assertEquals(post.numComments(), 2);

		// adding a comment affects the post's file notation
		postFileNotation = post.toFileNotation().split("\n");
		commentFileNotation2 = comment2.toFileNotation().split("\n");

		// postFileNotation lines 6, 7, 8 should *STILL* correspond to
		// commentFileNotation1 lines 0,1,2
		assertEquals(postFileNotation[6], commentFileNotation1[0]);
		assertEquals(postFileNotation[7], commentFileNotation1[1]);
		assertEquals(postFileNotation[8], commentFileNotation1[2]);

		// postFileNotation lines 9, 10, 11 should correspond to commentFileNotation2
		// lines 0, 1, 2
		assertEquals(postFileNotation[9], commentFileNotation2[0]);
		assertEquals(postFileNotation[10], commentFileNotation2[1]);
		assertEquals(postFileNotation[11], commentFileNotation2[2]);
	}

	@Test
	void getPathname() {
		// Confirm that the pathname is the same as what was passed to its constructor
		assertEquals(newPost1().getPathname(), pathname1);
		assertEquals(newPost2().getPathname(), pathname2);
	}

	@Test
	void getContent() {
		// Confirm that the content is the same as what was passed to its constructor
		assertEquals(newPost1().getContent(), content1);
		assertEquals(newPost2().getContent(), content2);
	}

	@Test
	void getDateCreated() {
		// Confirm that the date created is the same as what was passed to its
		// constructor
		assertEquals(newPost1().getDateCreated(), date1);
		assertEquals(newPost2().getDateCreated(), date2);
	}

	@Test
	void addVote() {
		Post post = newPost1();

		int add1 = 3;
		int expected1 = add1 + post.getNetVote();

		// add1 to the netVote
		post.addVote(add1);
		assertEquals(post.getNetVote(), expected1);

		int add2 = 20;
		int expected2 = add2 + post.getNetVote();

		// add1 to the netVote
		post.addVote(add2);
		assertEquals(post.getNetVote(), expected2);
	}

	@Test
	void getNetVote() {
		// Confirm that the netVote is the same as what was passed to its constructor
		assertEquals(newPost1().getNetVote(), netVote1);
		assertEquals(newPost2().getNetVote(), netVote2);
	}

	@Test
	void getUserId() {
		// Confirm that the userId is the same as what was passed to its constructor
		assertEquals(newPost1().getUserId(), user1);
		assertEquals(newPost2().getUserId(), user2);
	}

	@Test
	void getInterestLevel() {
		// Confirm that the interestLevel is the same as what was passed to its
		// constructor
		assertEquals(newPost1().getInterestLevel(), interestLevel1);
		assertEquals(newPost2().getInterestLevel(), interestLevel2);
	}

	@Test
	void compareTo() {
		Post post0 = newPost1(); // Post0 and Post1 have equal interestLevels
		Post post1 = newPost1();
		Post post2 = newPost2();
		Post post3 = newPost3();

		// post0 and post1 are be equal
		boolean result = post0.compareTo(post1) == 0;
		assertTrue(result);

		// post2 is greater than post1
		result = post2.compareTo(post1) > 0;
		assertTrue(result);

		// post2 is less than post3
		result = post2.compareTo(post3) < 0;
		assertTrue(result);
	}

	@Test
	void calculateInterestLevel() {
		// From SRS: interest level = (24 + # of comments + net vote) - age
		// NOTE: need to test
	}

	@Test
	void toFileNotation() {
		/*
		 * File notation is: 
		 * content type,
		 * content,
		 * date created in format: "EEE MMM d HH:mm:ss zzz yyyy",
		 * net vote,
		 * user ID,
		 * interest level
		 * comment[0].toFileNotation(),
		 * comment[1].toFileNotation(),
		 * ....
		 * comment[post.numComments() - 1]
		 */
		
		Post post1 = newPost1();
		Post post2 = newPost2();
		Post post3 = newPost3();
		Post post4 = newPost3();
		
		//post1 will have 0 comments
		
		//post2 will have 1 comment. only comment1.
		post2.addComment(comment1);

		//post3 will have 2 comments. first comment2 and then comment1
		post3.addComment(comment2);
		post3.addComment(comment1);
		
		//post4 will have 2 comments. first comment1 and then comment2
		post4.addComment(comment1);
		post4.addComment(comment2);
		
		String expectedFileNotation1 = post1.getContent().getType() + "\n" + post1.getContent().getContent() + "\n" + sdf.format(post1.getDateCreated()) + "\n" + post1.getNetVote() + "\n" + post1.getUserId() + "\n" + post1.getInterestLevel();
		String expectedFileNotation2 = post2.getContent().getType() + "\n" + post2.getContent().getContent() + "\n" + sdf.format(post2.getDateCreated()) + "\n" + post2.getNetVote() + "\n" + post2.getUserId() + "\n" + post2.getInterestLevel() + "\n" + comment1.toFileNotation();
		String expectedFileNotation3 = post3.getContent().getType() + "\n" + post3.getContent().getContent() + "\n" + sdf.format(post3.getDateCreated()) + "\n" + post3.getNetVote() + "\n" + post3.getUserId() + "\n" + post3.getInterestLevel() + "\n" + comment2.toFileNotation() + "\n" + comment1.toFileNotation();
		String expectedFileNotation4 = post4.getContent().getType() + "\n" + post4.getContent().getContent() + "\n" + sdf.format(post4.getDateCreated()) + "\n" + post4.getNetVote() + "\n" + post4.getUserId() + "\n" + post4.getInterestLevel() + "\n" + comment1.toFileNotation() + "\n" + comment2.toFileNotation();
		
		assertEquals(post1.toFileNotation(), expectedFileNotation1);
		assertEquals(post2.toFileNotation(), expectedFileNotation2);
		assertEquals(post3.toFileNotation(), expectedFileNotation3);
		assertEquals(post4.toFileNotation(), expectedFileNotation4);
	}

	@Test
	void parsePost() {
		//NOTE: need to test
	}
	
	@Test
	void equalsTest() {
		Post post1A = newPost1();
		Post post1B = newPost1();
		Post post2 = newPost2();
		Object obj = "hello";
		
		//Two equal posts
		assertTrue(post1A.equals(post1B));
		assertTrue(post1B.equals(post1A));
		
		//Two unequal posts
		assertFalse(post1A.equals(post2));
		assertFalse(post2.equals(post1A));
		
		//An object other than a post
		assertFalse(post1A.equals(obj));
		assertFalse(obj.equals(post1A));
	}
}
