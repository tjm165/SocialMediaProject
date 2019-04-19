import javax.swing.JPanel;

import theme.Panel;

public interface Panelable {
	public JPanel toPanel(User user, int index);
}
