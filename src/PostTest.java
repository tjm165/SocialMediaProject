import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

class PostTest {

	private final String pathname1 = "pathname1";
	private final Content content1 = new Content("TEXT", "Here is text");
	private final Date date1 = new Date(1545872400000L);
	private final int netVote1 = 0;
	private final String user1 = "user1";
	private final int interestLevel1 = 0;
	private final int interestLevel2 = 1;
	private final int interestLevel3 = 2;
	private final List<Comment> comments1 = new ArrayList<Comment>();

	private final Comment comment1 = new Comment(content1, date1, user1);

	// helper that makes a new post
	private Post newPost1() {
		return new Post(pathname1, content1, date1, netVote1, user1, interestLevel1, comments1);
	}
	
	private Post newPost2() {
		return new Post(pathname1, content1, date1, netVote1, user1, interestLevel2, comments1);
	}
	
	private Post newPost3() {
		return new Post(pathname1, content1, date1, netVote1, user1, interestLevel3, comments1);

	}
	

	@Test
	void numComments() {
		Post post = newPost1();
		
		//Confirm that numComments is 0
		assertEquals(post.numComments(), 0);
		
		//Add 1 comment and confirm numComments incremented
		post.addComment(comment1);
		assertEquals(post.numComments(), 1);
	}
	
	@Test
	void getPathname() {
		Post post = newPost1();
		
		//Confirm that the pathname is the same as what was passed to its constructor
		assertEquals(post.getPathname(), pathname1);
	}
	
	@Test
	void getContent() {
		Post post = newPost1();
		
		//Confirm that the content is the same as what was passed to its constructor
		assertEquals(post.getContent(), content1);
	}
	
	@Test
	void getDateCreated() {
		
	}
	
	@Test
	void addVote() {
		
	}
	
	@Test
	void getNetVote() {
		
	}
	
	@Test
	void getUserId(){
		
	}
	
	@Test
	void getInterestLevel(){
		
	}

	@Test
	void compareTo() {
		Post post0 = newPost1(); //Post0 and Post1 have equal interestLevels
		Post post1 = newPost1();
		Post post2 = newPost2();
		Post post3 = newPost3();
		
		//post0 and post1 are be equal
		boolean result = post0.compareTo(post1) == 0;
		assertTrue(result);
		
		//post2 is greater than post1
		result = post2.compareTo(post1) > 0;
		assertTrue(result);
		
		//post2 is less than post3
		result = post2.compareTo(post3) < 0;
		assertTrue(result);	
	}
}
