package system_classes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Comment {
	private Content content;
	private Date dateCreated;
	private String userId;

	public Comment(Content content, Date datecreated, String userId) {
		this.content = content;
		this.dateCreated = datecreated;
		this.userId = userId;
	}

	public String toFileNotation() {
		return ("" + content.getContent() + "\n" + dateCreated.toString() + "\n" + userId);
	}

	public Content getContent() {
		return content;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	//this is another getter method
	public String getUserId() {
		return userId;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Comment)) 
			return false;
		
		Comment comment = (Comment) o;
		if (!this.getContent().equals(comment.getContent()))
			return false;
		if (!this.getDateCreated().equals(comment.getDateCreated()))
			return false;
		if (!this.getUserId().equals(comment.getUserId()))
			return false;
		
		return true;
	}
	
	public static Comment parse(String[] commentLines) throws ParseException {
		Content content = new Content("TEXT", commentLines[0]);
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
		Date dateCreatedObj = sdf.parse(commentLines[1]);
		String userId = commentLines[2];

		return new Comment(content, dateCreatedObj, userId);
	}
}
