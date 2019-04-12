package theme;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class Panel extends JPanel {

	public Panel(int rows, int cols) {
		//super(Panel.makeLayout(rows, cols));
		this.setBackground(Theme.COLOR_ACCENT1);
		this.setFont(Theme.FONT);
		this.setBorder(Theme.BORDER);
	}

	private static GridLayout makeLayout(int rows, int cols) {
		GridLayout gl = new GridLayout(rows, cols);
		//gl.setVgap(20);
		return gl;
	}
}
