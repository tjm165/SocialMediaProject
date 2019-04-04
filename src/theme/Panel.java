package theme;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class Panel extends JPanel{

	public Panel(int rows, int cols) {
		super(new GridLayout(rows, cols));
		this.setBackground(Theme.COLOR_ACCENT);
		this.setBorder(Theme.BORDER);
	}
}
