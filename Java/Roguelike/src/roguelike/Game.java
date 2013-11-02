package roguelike;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.util.Random;
public class Game extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = -3442912778777128627L;
	private static Random Randomizer = null;
	private Dungeon D;
	public void actionPerformed(ActionEvent e)
	{
		
	}
	
	public static Random GetRandomizer()
	{
		return Randomizer;
	}
	
	public static void SetRandomizer(long Seed) {
		if(Randomizer == null)
			Randomizer = new Random(Seed);
		else
			Randomizer.setSeed(Seed);
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
		pack();
	}
	
	public void DoNothing() { }

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				long Seed = System.currentTimeMillis();
				Game.SetRandomizer(Seed);
				TileSet.GetInstance().Load("Tileset.png", 20, 20, 16, 16);
				EntryRepo.GetInstance().Load("Entries.txt");
				for(int i = 0; i < 10; i++) {
					ItemEntry e = EntryRepo.GetInstance().GetRandomOfType("Weapon");
					System.out.println(e.GetStats().GetString("Name"));
				}
				Game G = new Game();
				G.DoNothing();
			}
		});
	}

}
