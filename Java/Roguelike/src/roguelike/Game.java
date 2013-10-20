package roguelike;
public class Game {
	
	private Dungeon D;

	public static void main(String[] args) {
		Game G = new Game();
		G.D = new Dungeon(100, 50);
		System.out.println(G.D);
	}

}
