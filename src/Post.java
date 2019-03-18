import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Post implements Comparable<Post>{
	private String pathname;
	private Content content;
	private Date dateCreated;
	private int netVote;
	private String userId;
	private int interestLevel;
	private List<Comment> comments;

	public Post(String pathname, Content content, Date dateCreated, int netVote, String userId, int interestLevel,
			List<Comment> comments) {
		this.pathname = pathname;
		this.content = content;
		this.dateCreated = dateCreated;
		this.netVote = netVote;
		this.userId = userId;
		this.interestLevel = interestLevel;
		this.comments = comments;
	}
	
	public int numComments() {
		return comments.size();
	}

	public void addComment(Comment comment) {
		comments.add(comment);
	}

	public String getPathname() {
		return pathname;
	}

	public Content getContent() {
		return content;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void addVote(int vote) {
		netVote += vote;
	}

	public int getNetVote() {
		return netVote;
	}

	public String getUserId() {
		return userId;
	}

	public int getInterestLevel() {
		return interestLevel;
	}
	
	@Override
	public int compareTo(Post post) {
		return this.getInterestLevel() - post.getInterestLevel();
	}

	public int calculateInterestLevel() {
		// From SRS: interest level = (24 + # of comments + net vote) - age
		return 24 + numComments() + getNetVote() - getAge();
	}
	
	private int getAge() {
		Date currentDate = Calendar.getInstance().getTime();
		long ageInMillis = currentDate.getTime() - dateCreated.getTime();
		int ageInHours = (int) TimeUnit.MILLISECONDS.toHours(ageInMillis);
		
		return ageInHours;
	}

	public String toFileNotation() {
		// probably need to calculate interest level first
		StringBuilder fileNotation = new StringBuilder();

		fileNotation.append(getContent().getType() + "\n" + getContent().getContent() + "\n"
				+ getDateCreated().toString() + "\n" + getNetVote() + "\n" + getUserId() + "\n" + getInterestLevel());

		for (Comment comment : comments) {
			fileNotation.append("\n" + comment.toFileNotation());
		}
		return fileNotation.toString();
	}

	public void saveToFile() throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(this.pathname);
		writer.print(this.toFileNotation());
		writer.close();
	}

	// helper method
	private static Post parsePost(String pathname, String contentType, String content, String dateCreated,
			String netVote, String userId, String interestLevel, List<Comment> commentsObj) {
		Content contentObj = new Content(contentType, content);
		Date dateCreatedObj = new Date();// not done
		int netVoteObj = Integer.parseInt(netVote);
		String userIdObj = userId;
		int interestLevelObj = Integer.parseInt(interestLevel);

		return new Post(pathname, contentObj, dateCreatedObj, netVoteObj, userIdObj, interestLevelObj, commentsObj);
	}

	// http://www.avajava.com/tutorials/lessons/how-do-i-read-a-string-from-a-file-line-by-line.html
	public static Post parsePost(String pathname) throws IOException {
		File file = new File(pathname);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		String contentType = br.readLine();
		String content = br.readLine();
		String dateCreated = br.readLine();
		String netVote = br.readLine();
		String userId = br.readLine();
		String interestLevel = br.readLine();
		LinkedList<Comment> commentsObj = new LinkedList<Comment>();

		Comment commentObj;
		String[] comment = new String[3];

		while ((comment[0] = br.readLine()) != null) {// while has next
			comment[1] = br.readLine();
			comment[2] = br.readLine();
			commentObj = Comment.parse(comment);

			commentsObj.add(commentObj);
		}
		fr.close();

		// System.out.println(content + " " + dateCreated + " " + netVote + " " + userId
		// + " " + interestLevel);

		return parsePost(pathname, contentType, content, dateCreated, netVote, userId, interestLevel, commentsObj);
	}


}
