package theme;

import javax.swing.JButton;

public class Button extends JButton{

	public Button(String text){
		super(text);
		this.setSize(200, 100);
		this.setBackground(Theme.ACCENT_COLOR);
		this.setOpaque(true);
	}
}
