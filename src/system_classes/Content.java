package system_classes;
import javax.swing.JPanel;

import theme.Label;
import theme.Panel;

public class Content {
	private String type;
	private String content;

	public Content(String type, String content) {
		if (type.equals("TEXT") || type.equals("IMAGE")) {
			this.type = type;
		} else {
			this.type = "TEXT"; // maybe we should throw an error instead?
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
