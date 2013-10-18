package roguelike;
public class Game {
	
	private Dungeon D;

	public static void main(String[] args) {
		Game G = new Game();
		G.D = new Dungeon(30, 30);
		System.out.println(G.D);
	}

}
