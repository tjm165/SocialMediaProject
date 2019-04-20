package theme;

import javax.swing.JLabel;

public class Label extends JLabel{

	public Label(String text) {
		super(text);
		this.setFont(Theme.FONT);
		this.setForeground(Theme.COLOR_LABEL);
	}
}
