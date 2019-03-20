import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ContentTest {

	@Test
	void getType() {
		Content content = new Content("IMAGE", "http://image.com/");
		
		//getType() should return the type passed through the constructor
		assertEquals(content.getType(), "IMAGE");
	}
	
	@Test
	void getContent() {
		Content content = new Content("IMAGE", "http://image.com/");
		
		//getContent() should return the content passed through the constructor
		assertEquals(content.getContent(), "http://image.com/");
	}
	
	@Test
	void testEquals() {
		
	}

}
