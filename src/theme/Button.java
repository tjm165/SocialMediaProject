package theme;

import javax.swing.JButton;

public class Button extends JButton{

	public Button(String text){
		super(text);
		this.setSize(200, 100);
		this.setBackground(Theme.COLOR_BUTTON_MAIN);
		this.setOpaque(true);
		this.setFont(Theme.FONT);
		this.setBorder(Theme.BUTTON_PADDING);
		

	}
}
