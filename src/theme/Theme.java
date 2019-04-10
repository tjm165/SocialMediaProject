package theme;

import java.awt.Color;
import java.awt.Font;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Theme {
	public static final Color COLOR_BACKGROUND = Color.BLACK;
	
	public static final Color COLOR_MAIN = new Color(36, 185, 226); //blue
	public static final Color COLOR_ACCENT1 = new Color(255, 173, 244); //pink
	public static final Color COLOR_ACCENT2 = new Color(164, 73, 255); //purple
	public static final Color COLOR_ACCENT3 = new Color(183, 218, 255); //blue 2
	public static final Color COLOR_ACCENT4 = new Color(193, 60, 60); //maroon
	
	public static final Color COLOR_ERROR = Color.RED;
	
	public static final Color COLOR_BUTTON_MAIN = COLOR_ACCENT3;

	public static final Font FONT = new Font("Serif", Font.PLAIN, 72);
	public static final LineBorder BORDER = new LineBorder(Color.black, 2, true);
	public static final Border BUTTON_PADDING = new EmptyBorder(80, 0, 0, 0);
	
}
