
public class Comment {
	private Content content;
	private Date dateCreated;
	private String userId;

	public Comment(Content content, Date datecreated, String userId){
		this.content = content;
		this.datecreated = datecreated;
		this.userId = userId;
	}
	
	public static Comment parse(String[] commentLines) {
		Content content = new Content("TEXT", commentLines[0]);
		Date dateCreated = new Date();//NOT DONE ************
		String userId = commentLines[2];
		
		return new Comment(content, dateCreated, userId);
	}
}
