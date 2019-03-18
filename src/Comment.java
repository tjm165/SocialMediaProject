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

	//this is a getter method
	public Date getDateCreated() {
		return dateCreated;
	}

	//this is another getter method
	public String getUserId() {
		return userId;
	}
	
	public static Comment parse(String[] commentLines) throws ParseException {
		Content content = new Content("TEXT", commentLines[0]);
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
		Date dateCreatedObj = sdf.parse(commentLines[1]);
		String userId = commentLines[2];

		return new Comment(content, dateCreatedObj, userId);
	}

}
