
public class Content {
	private String type;
	private String content;

	public Content(String type, String content) {
		if(type.equals("TEXT") || type.equals("IMAGE")){
			this.type = type;
		}
		else{
			this.type = "TEXT";
		}
		this.content = content;
	}
	
	public getType(){
		return type;
	}
	
	public getContent(){
		return content;
	}
}
