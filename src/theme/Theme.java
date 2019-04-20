package theme;

import java.awt.Color;
import java.awt.Font;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Theme {
	// https://www.awwwards.com/trendy-web-color-palettes-and-material-design-color-schemes-tools.html
	// Quay Restaurant

	public static final Color COLOR_BACKGROUND = Color.BLACK;

	public static final Color COLOR_MAIN = Color.decode("#e3e3e3");
	public static final Color COLOR_ACCENT1 = Color.decode("#9ad3de");
	public static final Color COLOR_ACCENT2 = Color.decode("#89bdd3");

	public static final Color COLOR_BUTTON = COLOR_ACCENT2;
	public static final Color COLOR_LABEL = Color.BLACK;
	public static final Color COLOR_ERROR = Color.RED;

	public static final Font FONT = new Font("Serif", Font.PLAIN, 72);
	public static final LineBorder BORDER = new LineBorder(Color.black, 20, true);
	public static final Border BUTTON_PADDING = new EmptyBorder(80, 0, 0, 0);

}
