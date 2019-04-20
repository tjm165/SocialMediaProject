package theme;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Image extends JLabel {

	public Image(String path) throws IOException {
		super(new ImageIcon(ImageIO.read(new File(path))));
	}

}
