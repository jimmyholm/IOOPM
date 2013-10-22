package roguelike;
import javax.swing.JFrame;;
public class Game extends JFrame {
	
	private Dungeon D;
	
	public Game() {
		add(new javax.swing.JPanel());
		setTitle("Test");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800,800);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}
	
	static final long serialVersionUID = 958L;

	public static void main(String[] args) {
		Game G = new Game();
		G.D = new Dungeon(50, 50, System.currentTimeMillis(), 20, 30, 2, 16);
		System.out.println(G.D);
	}

}
