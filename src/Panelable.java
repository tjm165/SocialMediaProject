import javax.swing.JPanel;

import theme.Panel;

public interface Panelable {
	public Panel toPanel(User user, int index);
}
