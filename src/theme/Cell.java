package theme;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class Cell extends JPanel {

	// cool trick from
	// https://stackoverflow.com/questions/2510159/can-i-add-a-component-to-a-specific-grid-cell-when-a-gridlayout-is-used
	private JPanel[][] grid;

	public Cell(int rows, int cols) {
		this.setLayout(new GridLayout(rows, cols));
		this.setBackground(Theme.COLOR_ACCENT1);
		this.setFont(Theme.FONT);
		this.setBorder(Theme.BORDER);

		grid = new JPanel[rows][cols];

		for (int m = 0; m < rows; m++) {
			for (int n = 0; n < cols; n++) {
				JPanel p = new JPanel();
				grid[m][n] = p;
				add(p);
			}
		}
	}

	public void setCell(JComponent comp, int rows, int cols) {
		rows -=1;
		cols -=1;
		
		grid[rows][cols].add(comp);
	}
	
	public void setCell(String string, int rows, int cols) {
		Label label = new Label(string);
		this.setCell(label, rows, cols);
	}

}
