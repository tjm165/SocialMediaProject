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

	public static Comment parse(String[] commentLines) {
		Content content = new Content("TEXT", commentLines[0]);
		Date dateCreated = new Date();// NOT DONE ************
		String userId = commentLines[2];

		return new Comment(content, dateCreated, userId);
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

}
