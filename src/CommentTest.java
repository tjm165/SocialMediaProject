import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.util.Date;

import org.junit.jupiter.api.Test;

class CommentTest {

	private final Content content1 = new Content("TEXT", "Here is text");
	private final Date date1 = new Date(1545872400000L);
	private final String user1 = "user1";

	private final Comment comment1 = new Comment(content1, date1, user1);

	@Test
	void toFileNotation() {
		Comment comment = new Comment(content1, date1, user1);
		String expectedFileNotation = ("" + content1.getContent() + "\n" + date1 + "\n" + user1);
		
		//The comments file notation should be equal to the expected file notation
		assertEquals(comment.toFileNotation(), (expectedFileNotation));
	}
	
	@Test
	void getContent() {
		Comment comment = new Comment(content1, date1, user1);

		//The content should be the same as what was passed through the constructor
		assertEquals(comment.getContent(), content1);
	}
	
	@Test
	void getDateCreated() {
		Comment comment = new Comment(content1, date1, user1);

		//The date should be the same as the date passed through the constructor
		assertEquals(comment.getDateCreated(), date1);
	}
	
	@Test
	void getUserId() {
		Comment comment = new Comment(content1, date1, user1);

		//The userId should be the same as the userId passed through the constructor
		assertEquals(comment.getUserId(), user1);
	}
	
	@Test
	void parse() throws ParseException {
		Comment expectedComment = new Comment(content1, date1, user1);
		String[] toParse = new String[] {"Here is text", "Mon Mar 18 18:30:20 EDT 2019", user1};
		Comment parsed = Comment.parse(toParse);
		
		assertEquals(parsed, toParse);
	}
	
	

}
