package roguelike;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.util.Random;
public class Game extends JFrame implements KeyListener{
	
	private static final long serialVersionUID = -3442912778777128627L;
	private static Random Randomizer = null;
	private static Game G = null;
	private Dungeon D;
	private MessageList ML;
	private InfoPanel IP;
	enum State { PLAYING, DEAD};
	State GameState = State.PLAYING;
	public static Game GetInstance() {
		return G;
	}
	public static Random GetRandomizer()
	{
		return Randomizer;
	}
	
	public void GameOver() {
		GameState = State.DEAD;
	}
	
	public static void SetRandomizer(long Seed) {
		if(Randomizer == null)
			Randomizer = new Random(Seed);
		else
			Randomizer.setSeed(Seed);
		DiceRoller.GetInstance().SeedDice(Seed*2);
	}
	
	public Game() {
		setLayout(null);
		setPreferredSize(new Dimension(800, 800));
		setTitle("Test");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		getContentPane().setBackground(Color.black);
		D = new Dungeon(50, 50, 20, 30, 2, 16);
		add(D);
		D.setBounds(0, 0, 26*TileSet.GetInstance().GetTileWidth(), 26*TileSet.GetInstance().GetTileHeight());		//D.setPreferredSize(new Dimension(32*TileSet.GetInstance().GetTileWidth(), 32*TileSet.GetInstance().GetTileHeight()));
		D.setBorder(BorderFactory.createLineBorder(Color.gray));
		D.setBackground(java.awt.Color.black);
		D.setVisible(true);
		ML = MessageList.GetInstance();
		add(ML);
		ML.setBounds(0, 26*TileSet.GetInstance().GetTileHeight()+1, 797, 800 - 26*TileSet.GetInstance().GetTileHeight()+1);
		ML.setBorder(BorderFactory.createLineBorder(Color.gray));
		ML.setBackground(java.awt.Color.black);
		ML.setVisible(true);
		D.setFocusable(false);
		ML.setFocusable(false);
		ML.setEnabled(false);
		IP = InfoPanel.GetInstance();
		add(IP);
		IP.setBounds(26*TileSet.GetInstance().GetTileHeight()+1, 0, 800 - 26*TileSet.GetInstance().GetTileHeight()+1, 26*TileSet.GetInstance().GetTileHeight());
		IP.setBorder(BorderFactory.createLineBorder(Color.gray));
		IP.setBackground(java.awt.Color.black);
		IP.setVisible(true);
		IP.setFocusable(false);
		IP.setEnabled(false);
		D.setEnabled(false);
		setFocusable(true);
		addKeyListener(this);
		pack();
		IP.Update();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 1000; i++) {
					int d = DiceRoller.GetInstance().Roll("1d4");
					if(d == 4) {
						System.out.println("4!");
					}
					if(d == 0) {
						System.out.println("0!");
					}
				}
				long Seed = System.currentTimeMillis();
				Game.SetRandomizer(Seed);
				TileSet.GetInstance().Load("Tileset.png", 20, 20, 16, 16);
				EntryRepo.GetInstance().Load("Entries.txt");
				G = new Game();
			}
		});
	}
	
	public Dungeon GetDungeon() {
		return D;
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
		if(GameState == State.PLAYING)
			D.KeyDown(key.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent key) {
		
	}

	@Override
	public void keyTyped(KeyEvent key) {
		
	}

}
