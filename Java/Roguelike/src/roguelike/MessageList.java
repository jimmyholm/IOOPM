package roguelike;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
public class MessageList extends JPanel {

	private static final long serialVersionUID = -7135499256484684751L;
	private JList<String> Messages;
	private DefaultListModel<String> ListModel;
	private JScrollPane Scroll;
	private static MessageList Instance = null;
	
	public static MessageList GetInstance() {
		if(Instance == null)
			Instance = new MessageList();
		return Instance;
	}
	
	public void AddMessage(String Message) {
		if(ListModel.getSize() >= 20) {
			ListModel.remove(0);
		}
		ListModel.addElement(Message);
	}
	
	public MessageList() {
		ListModel = new DefaultListModel<String>();
		Messages = new JList<String>(ListModel);
		Messages.setBackground(java.awt.Color.black);
		Messages.setForeground(java.awt.Color.white);
		Messages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Scroll = new JScrollPane();
		Scroll.setPreferredSize(new Dimension(797, 760-26*TileSet.GetInstance().GetTileHeight()));
		Scroll.getViewport().setView(Messages);
		Scroll.setHorizontalScrollBar(null);
		add(Scroll);
		Scroll.setFocusable(false);
		Scroll.setEnabled(false);
		Messages.setFocusable(false);
		Messages.setEnabled(false);
	}
}
