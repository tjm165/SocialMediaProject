package system_classes;
import javax.swing.JPanel;

import theme.Label;
import theme.Cell;

public class Content {
	private String type;
	private String content;
	public static String TYPE_TEXT = "TEXT";
	public static String TYPE_IMAGE = "IMAGE";

	public Content(String type, String content) {
		if (type.equals(TYPE_IMAGE) || type.equals(TYPE_TEXT)) {
			this.type = type;
		} else {
			this.type = TYPE_TEXT; // maybe we should throw an error instead?
		}
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public String getContent() {
		return content;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Content))
			return false;
		Content content = (Content) o;

		if (!this.getType().equals(content.getType()))
			return false;
		if (!this.getContent().equals(content.getContent()))
			return false;
		return true;

	}

}
